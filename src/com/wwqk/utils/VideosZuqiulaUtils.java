package com.wwqk.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.constants.PlayerEnum;
import com.wwqk.model.Videos;
import com.wwqk.model.VideosRealLinks;

public class VideosZuqiulaUtils {
	
	private static final String MAIN_SITE = "http://www.zuqiu.la";
	
	public static void collect(boolean isInit){
		for(int i=2; i<3; i++){
			String url = "http://www.zuqiu.la/video/index.php?p=1&type="+i;
			Connection connect = Jsoup.connect(url).ignoreContentType(true);
			for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
				connect.header(entry.getKey(), entry.getValue());
			}
			Connection data = connect.data();
		    String resultStr = "";
			try {
				Document document = data.get();
				handleOneUrl(url, document);
			} catch (IOException e) {
				System.err.println();
			}
			
			
		}
	}
	
	public static void handleOneUrl(String referUrl, Document document){
		Elements elements = document.select(".col_01");
		if(elements.size()!=0){
			Elements liElements = elements.get(0).select("li");
			for(Element element:liElements){
				Elements aElements = element.select("a");
				String matchTitle = aElements.get(0).text();
				String videoUrl = MAIN_SITE+aElements.get(0).attr("href");
				//查询数据库中是否存在
				VideosRealLinks existLinks = VideosRealLinks.dao.findFirst("select * from videos_real_links where source_url = ? ", videoUrl);
				if(existLinks!=null){
					continue;
				}
				Videos videos = new Videos();
				
				Connection connect = Jsoup.connect(videoUrl).ignoreContentType(true);
				for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
					connect.header(entry.getKey(), entry.getValue());
				}
				connect.header("Referer", referUrl);
				Connection data = connect.data();
				try {
					Document videoDoc = data.get();
					Elements luxiangElements = videoDoc.select("#r_luxiang");
					if(luxiangElements.size()>0){
						Elements aLxElements = luxiangElements.get(0).select("a");
						for(Element aLx:aLxElements){
							//  www.zuqiu.la/play_video.php?cid=80874&preview=1
							String playUrl = MAIN_SITE + aLx.attr("href");
							//  [PPTV全场集锦] 英超-莫拉塔头球制胜 切尔西1-0击败曼联
							String title = aLx.attr("title");
							
							Connection videoConnect = Jsoup.connect(playUrl).ignoreContentType(true);
							for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
								connect.header(entry.getKey(), entry.getValue());
							}
							connect.header("Referer", videoUrl);
							Connection playerData = connect.data();
							Document playDoc = playerData.get();
							String playDocHtml = playDoc.html();
							String realUrl = null;
							String playerType = "";
							if(playDocHtml.contains("type=qq")){
								playerType = PlayerEnum.QQ.getKey();
								realUrl = getQQSrc(playDocHtml);
							}else if(playDocHtml.contains("pptv")){
								playerType = PlayerEnum.PPTV.getKey();
								realUrl = getPPTVSrc(playDocHtml);
							}else if(playDocHtml.contains("ssports")){
								playerType = PlayerEnum.SSPORTS.getKey();
								realUrl = getSsportsSrc(playDocHtml);
							}
						}
					}
					Elements jijingElements = videoDoc.select("#r_jijing");
					if(luxiangElements.size()>0){
						
					}
				} catch (IOException e) {
					System.err.println();
				}
			}
		}
		
	}
	
	private static final Pattern URL_PATTERN = Pattern.compile("src=\"(.*?)\"");
	private static final Pattern SS_PATTERN = Pattern.compile("data=\"(.*?)\"");
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
	
	@Before(Tx.class)
	private static void saveOneMatchVideos(Videos videos, List<VideosRealLinks> lstLink){
		
	}
	
	public static void main(String[] args) {
		collect(false);
	}
	
	
}
