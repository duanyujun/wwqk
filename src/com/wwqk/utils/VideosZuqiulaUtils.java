package com.wwqk.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.constants.PlayerEnum;
import com.wwqk.constants.VideosLeagueEnum;
import com.wwqk.model.Videos;
import com.wwqk.model.VideosRealLinks;

public class VideosZuqiulaUtils {
	
	private static final String MAIN_SITE = "http://www.zuqiu.la";
	private static final Pattern ALL_COUNT_PATTERN = Pattern.compile("总共(\\d+)个");
	
	public static void collect(boolean isInit){
		//for(int i=9; i>1; i--){
		for(int i=2; i<10; i++){
			String url = "http://www.zuqiu.la/video/index.php?p=1&type="+i;
			System.err.println("page："+url);
			Connection connect = Jsoup.connect(url).ignoreContentType(true);
			for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
				connect.header(entry.getKey(), entry.getValue());
			}
			Connection data = connect.data();
		    String resultStr = "";
			try {
				Document document = data.get();
				resultStr = document.html();
				handleOneUrl(i, url, document);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(isInit){
				String allCountStr = CommonUtils.matcherString(ALL_COUNT_PATTERN, resultStr);
				if(StringUtils.isNotBlank(allCountStr)){
					int allCount = Integer.valueOf(allCountStr);
					int pageCount = allCount/50+(allCount%50==0?0:1);
					for(int j=2;j<=pageCount;j++){
						url = "http://www.zuqiu.la/video/index.php?p="+j+"&type="+i;
						System.err.println("page："+url);
						connect = Jsoup.connect(url).ignoreContentType(true);
						for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
							connect.header(entry.getKey(), entry.getValue());
						}
						data = connect.data();
						try {
							Document document = data.get();
							resultStr = document.html();
							handleOneUrl(i, url, document);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public static void handleOneUrl(int sourceLeagueId, String referUrl, Document document){
		Elements elements = document.select(".col_01");
		if(elements.size()!=0){
			Elements liElements = elements.get(0).select("li");
			for(Element element:liElements){
				Elements aElements = element.select("a");
				String matchTitle = aElements.get(0).text();
				String videoUrl = MAIN_SITE+aElements.get(0).attr("href");
				System.err.println(">>>> handle："+matchTitle+" url："+videoUrl);
				Videos videos = Videos.dao.findFirst("select * from videos where source_url = ?", videoUrl);
				if(videos==null){
					continue;
				}
				
				Connection connect = Jsoup.connect(videoUrl).ignoreContentType(true);
				for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
					connect.header(entry.getKey(), entry.getValue());
				}
				connect.header("Referer", referUrl);
				Connection data = connect.data();
				try {
					Document videoDoc = data.get();
					Elements contentElements = videoDoc.select(".content[id!='tab_content']");
					videos.set("summary", contentElements.get(0).html());
					videos.update();
				}catch(Exception e){
					
				}
			}
		}
		
	}
	
	private static final Pattern SUMMARY_PATTERN = Pattern.compile("<div class=\"content\">(.*?)</div>");
	private static final Pattern URL_PATTERN = Pattern.compile("src=\"(.*?)\"");
	private static final Pattern SS_PATTERN = Pattern.compile("flashvars.*?=\"(.*?)\">");
	private static String getPPTVSrc(String html){
		String result = "";
		Matcher matcher = URL_PATTERN.matcher(html);
		if(matcher.find()){
			result = matcher.group(1);
		}
		
		return result;
	}
	
	private static String getQQSrc(String html){
		String result = getPPTVSrc(html);
		int lastEqualIdx = result.lastIndexOf("=");
		result = result.substring(lastEqualIdx+1);
		return result;
	}
	
	private static String getSsportsSrc(String html){
		String result = "";
		Matcher matcher = SS_PATTERN.matcher(html);
		if(matcher.find()){
			result = matcher.group(1);
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		Connection connect = Jsoup.connect("http://www.zuqiu.la/video/17526.html").ignoreContentType(true);
		for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
			connect.header(entry.getKey(), entry.getValue());
		}
		connect.header("Referer", "http://www.zuqiu.la/video/index.php?p=9&type=2");
		Connection data = connect.data();
		Document videoDoc;
		try {
			videoDoc = data.get();
			System.err.println(videoDoc.html());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
