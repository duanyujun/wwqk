package com.wwqk.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jfinal.plugin.activerecord.Db;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.WireFeedOutput;
import com.rometools.rome.io.XmlReader;
import com.wwqk.model.MatchGuess;

public class MatchGuessUtils {

	public static void collect(){
		List<MatchGuess> lstGuessesAdd = new ArrayList<MatchGuess>();
		List<MatchGuess> lstGuesses = new ArrayList<MatchGuess>();
		try {
			URL url = new URL("https://www.bettingexpert.com/tips.rss");
			URLConnection conn = url.openConnection();
			configConn(conn, "/tips.rss");
			String content_encoding = conn.getHeaderField("Content-Encoding");
			if (content_encoding != null && content_encoding.contains("gzip")) {
				System.out.println("conent encoding is gzip");
				GZIPInputStream gzin = new GZIPInputStream(
						conn.getInputStream());
				lstGuesses = staxParse(gzin);
			} else {
				lstGuesses = staxParse(conn.getInputStream());
			}
			for(MatchGuess guess:lstGuesses){
				if(MatchGuess.dao.findFirst("select * from match_guess where source_url = ? ", guess.getStr("source_url"))==null){
					guess.getAttrs().remove("sport");
					collectOneTips(guess);
					if(StringUtils.isNotBlank(guess.getStr("bet_title")) && StringUtils.isNotBlank(guess.getStr("content"))){
						lstGuessesAdd.add(guess);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(lstGuessesAdd.size()>0){
			Db.batchSave(lstGuessesAdd, lstGuessesAdd.size());
		}
		
	}
	
	private static void collectOneTips(MatchGuess guess) throws Exception{
		String sourceUrl = guess.getStr("source_url");
		Connection connection = Jsoup.connect(sourceUrl);
		String path = sourceUrl.replace("https://www.bettingexpert.com", "");
		Map<String, String> map = MatchUtils.getBetHeader(path);
		for(Entry<String, String> entry :map.entrySet()){
			connection.header(entry.getKey(), entry.getValue());
		}
		try {
			Document doc = connection.get();
			String title = doc.select(".clashOneliner").get(0).text();
			String content = doc.select(".tipArticle").get(0).text();
			guess.set("bet_title", title);
			guess.set("content", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据链接地址得到数据
	 * 
	 * @param url
	 *            RSS形式的xml文件
	 * @throws IllegalArgumentException
	 * @throws FeedException
	 */
	public static List<SyndEntry> parseXml(URL url) throws IllegalArgumentException,
			FeedException {
		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		try {
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = null;
			URLConnection conn;
			conn = url.openConnection();
			configConn(conn, "/tips.rss");
			String content_encoding = conn.getHeaderField("Content-Encoding");

			if (content_encoding != null && content_encoding.contains("gzip")) {
				System.out.println("conent encoding is gzip");
				GZIPInputStream gzin = new GZIPInputStream(
						conn.getInputStream());
				feed = input.build(new XmlReader(gzin));
			} else {
				feed = input.build(new XmlReader(conn.getInputStream()));
			}
			entries = feed.getEntries();// 得到所有的标题<title></title>
		} catch (IOException e) {
			e.printStackTrace();
		}

		return entries;
	}
	
	public static List<MatchGuess> staxParse(InputStream in){
		List<MatchGuess> lstGuesses = new ArrayList<MatchGuess>();
		XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader reader = factory.createXMLStreamReader(in);
            MatchGuess oneItem = null;
            boolean isFirst = false;
            boolean isTipsterName = false;
            while(reader.hasNext()) {
                int type = reader.next();
                //判断节点类型是否是开始或者结束或者文本节点,之后根据情况及进行处理
                if(type==XMLStreamConstants.START_ELEMENT) {
                    if("item".equals(reader.getName().toString())) {
                    	oneItem = new MatchGuess();
                    	isFirst = true;
                    }else if("link".equals(reader.getName().toString())){
                    	if(oneItem!=null && isFirst){
                    		oneItem.set("source_url", reader.getElementText());
                    		isFirst = false;
                    	}
                    }else if("country".equals(reader.getName().toString())){
                    	oneItem.set("country", reader.getElementText());
                    }else if("league".equals(reader.getName().toString())){
                    	oneItem.set("league", reader.getElementText());
                    }else if("sport".equals(reader.getName().toString())){
                    	oneItem.getAttrs().put("sport", reader.getElementText());
                    }else if("pubDate".equals(reader.getName().toString())){
                    	oneItem.set("publish_time", DateTimeUtils.getTimeZoneDate(reader.getElementText()));
                    }else if("kickoffDate".equals(reader.getName().toString())){
                    	oneItem.set("match_time", DateTimeUtils.getTimeZoneDate(reader.getElementText()));
                    }else if("tipster".equals(reader.getName().toString())){
                    	isTipsterName = true;
                    }else if("name".equals(reader.getName().toString())){
                    	if(isTipsterName){
                    		oneItem.set("tipster_name", reader.getElementText());
                    		isTipsterName = false;
                    	}
                    }
                }else if(type==XMLStreamConstants.END_ELEMENT){
                	if("item".equals(reader.getName().toString())){
                		if("Football".equals(oneItem.getAttrs().get("sport"))){
                			lstGuesses.add(oneItem);
                		}
                	}
                }
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(in!=null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lstGuesses;
	}

	public static void createXml() throws Exception {
		/*
		 * 根据Channel源码提供的英文,Channel对象有两个构造器，一个默认的无参构造器用于clone对象，一个是有参的
		 * 我们自己指定的必须使用有参数的
		 * （因为我们需要许可证），指构造方法必须要创建一个type（版本），这个type不能随便写，必须要以rss_开头的版本号 Licensed
		 * under the Apache License, Version 2.0 (the "License");
		 * 因为当前版本是2.0，所以就是rss_2.0，必须是rss_2.0否则会抛异常，该源码中写的已经很明白。
		 */
		Channel channel = new Channel("rss_2.0");
		channel.setTitle("channel标题");// 网站标题
		channel.setDescription("channel的描述");// 网站描述
		channel.setLink("www.shlll.net");// 网站主页链接
		channel.setEncoding("utf-8");// RSS文件编码
		channel.setLanguage("zh-cn");// RSS使用的语言
		channel.setTtl(5);// time to live的简写，在刷新前当前RSS在缓存中可以保存多长时间（分钟）
		channel.setCopyright("版权声明");// 版权声明
		channel.setPubDate(new Date());// RSS发布时间
		List<Item> items = new ArrayList<Item>();// 这个list对应rss中的item列表
		Item item = new Item();// 新建Item对象，对应rss中的<item></item>
		item.setAuthor("hxliu");// 对应<item>中的<author></author>
		item.setTitle("新闻标题");// 对应<item>中的<title></title>
		item.setGuid(new Guid());// GUID=Globally Unique Identifier
									// 为当前新闻指定一个全球唯一标示，这个不是必须的
		item.setPubDate(new Date());// 这个<item>对应的发布时间
		item.setComments("注释");// 代表<item>节点中的<comments></comments>
		// 新建一个Description，它是Item的描述部分
		Description description = new Description();
		description.setValue("新闻主题");// <description>中的内容
		item.setDescription(description);// 添加到item节点中
		items.add(item);// 代表一个段落<item></item>，
		channel.setItems(items);
		// 用WireFeedOutput对象输出rss文本
		WireFeedOutput out = new WireFeedOutput();
		try {
			System.out.println(out.outputString(channel));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置请求头
	 */
	private static void configConn(URLConnection conn, String path){
		conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		conn.setRequestProperty("accept-encoding", "gzip, deflate, br");
		conn.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
		conn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
		conn.setRequestProperty(":authority", "www.bettingexpert.com");
		conn.setRequestProperty(":method", "GET");
		conn.setRequestProperty(":path", path);
		conn.setRequestProperty(":scheme", "https");
	}
	
	public static void main(String[] args) throws Exception {
		//collect();
		
	}

}
