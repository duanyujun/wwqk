package com.wwqk.utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.Videos;
import com.wwqk.model.VideosRealLinks;

public class VideosZuqiulaUtils {
	
	private static final String MAIN_SITE = "http://www.zuqiu.la";
	private static final Pattern ALL_COUNT_PATTERN = Pattern.compile("总共(\\d+)个");
	private static final Pattern DATE_PATTERN = Pattern.compile("(\\d+月\\d+日)");
	
	public static void formatVideo() throws ParseException{
		CommonUtils.initNameIdMap();
		
		List<LeagueMatchHistory> lstHistory = LeagueMatchHistory.dao.find("select id, match_date, home_team_name, away_team_name from league_match_history");
		Map<String, LeagueMatchHistory> homeMap = new HashMap<String, LeagueMatchHistory>();
		Map<String, LeagueMatchHistory> awayMap = new HashMap<String, LeagueMatchHistory>();
		for(LeagueMatchHistory history : lstHistory){
			String dateStr = DateTimeUtils.formatDate(history.getDate("match_date"));
			homeMap.put(dateStr+"-"+history.getStr("home_team_id"), history);
			awayMap.put(dateStr+"-"+history.getStr("away_team_id"), history);
		}
		
		List<Videos> lstVideos = Videos.dao.find("select * from videos");
		for(Videos videos : lstVideos){
			if(StringUtils.isNotBlank(videos.getStr("home_team")) 
					&& StringUtils.isNotBlank(videos.getStr("away_team"))
					&& videos.get("match_date")==null){
				String title = videos.getStr("match_title");
				System.err.println("title："+title+"  url："+videos.getStr("source_url"));
				String dateStr = CommonUtils.matcherString(DATE_PATTERN, title);
				if(StringUtils.isNotBlank(dateStr) && !"0".equals(dateStr)){
					String[] yearPatterns = {"yyyy年M月d日"};
					Date lastYearDate = DateTimeUtils.parseDate("2016年"+dateStr, yearPatterns);
					Date thisYearDate = DateTimeUtils.parseDate("2017年"+dateStr, yearPatterns);
					
					String lastYearDateStr = DateTimeUtils.formatDate(lastYearDate);
					String thisYearDateStr = DateTimeUtils.formatDate(thisYearDate);
					
					LeagueMatchHistory oneHistory = homeMap.get(thisYearDateStr+"-"+CommonUtils.nameIdMap.get(videos.getStr("home_team")));
					if(oneHistory==null){
						oneHistory = awayMap.get(thisYearDateStr+"-"+CommonUtils.nameIdMap.get(videos.getStr("away_team")));
						if(oneHistory==null){
							oneHistory = homeMap.get(lastYearDateStr+"-"+CommonUtils.nameIdMap.get(videos.getStr("home_team")));
							if(oneHistory==null){
								oneHistory = awayMap.get(lastYearDateStr+"-"+CommonUtils.nameIdMap.get(videos.getStr("away_team")));
							}
						}
					}
					if(oneHistory!=null){
						videos.set("match_date", oneHistory.get("match_date"));
						videos.set("match_history_id", oneHistory.get("id"));
						title = title.replace(dateStr, DateTimeUtils.formatDate(oneHistory.get("match_date"), "yyyy年MM月dd日"));
						videos.set("match_title", title);
					}
				}
			}
			
			videos.set("match_title", videos.getStr("match_title").replace(" 录像 集锦", " 视频"));
		}
		
		Db.batchUpdate(lstVideos, lstVideos.size());
	}
	
	public static void collect(boolean isInit){
		for(int i=2; i<10; i++){
		//for(int i=9; i>1; i--){
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
				Videos videos = null;
				//查询数据库中是否存在
				VideosRealLinks existLinks = VideosRealLinks.dao.findFirst("select * from videos_real_links where source_url = ? ", videoUrl);
				if(existLinks!=null){
					continue;
				}else{
					//查询该场比赛是否存在
					Videos videosDb = Videos.dao.findFirst("select * from videos where source_url = ?", videoUrl);
					if(videosDb!=null){
						videos = videosDb;
					}else{
						videos = new Videos();
					}
				}
				videos.set("league_id", EnumUtils.getValue(VideosLeagueEnum.values(), String.valueOf(sourceLeagueId)));
				videos.set("match_title", StringUtils.trim(matchTitle));
				if(matchTitle.contains("vs")){
					matchTitle = StringUtils.trim(matchTitle);
					matchTitle = matchTitle.replace("日 ", "日");
					matchTitle = matchTitle.replace("录像集锦", "");
					matchTitle = matchTitle.replace("录像 集锦", "");
					matchTitle = matchTitle.replace("英超亚洲杯决赛", "").replace("英超亚洲杯", "").replace("英超", " ").replace("西甲", " ").replace("德甲", " ")
							.replace("意甲", " ").replace("意甲", " ").replace("欧冠资格赛", " ").replace("欧冠", "");
					
					if(matchTitle.contains(" ")){
						if(matchTitle.contains("皇马vs拜仁欧冠最新宣传片") || matchTitle.contains("皇马vs马竞欧冠决赛劲爆预告片")){
							continue;
						}
						matchTitle = matchTitle.replaceAll("\\s+", " ");
						matchTitle =StringUtils.trim(matchTitle);
						
						matchTitle = StringUtils.trim(matchTitle.substring(matchTitle.indexOf(" ")+1));
						if(matchTitle.contains(" ")){
							matchTitle = StringUtils.trim(matchTitle.substring(0,matchTitle.indexOf(" ")));
						}
						String[] homeAndAway = matchTitle.split("vs");
						videos.set("home_team", homeAndAway[0]);
						videos.set("away_team", homeAndAway[1]);
					}
				}
				videos.set("source_url", videoUrl);
				
				List<VideosRealLinks> lstAllLinks = new ArrayList<VideosRealLinks>();
				
				Connection connect = Jsoup.connect(videoUrl).ignoreContentType(true);
				for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
					connect.header(entry.getKey(), entry.getValue());
				}
				connect.header("Referer", referUrl);
				Connection data = connect.data();
				try {
					Document videoDoc = data.get();
					String summary = CommonUtils.matcherString(SUMMARY_PATTERN, videoDoc.html());
					videos.set("summary", summary);
					if (videos.get("id")!=null) {
						videos.update();
					}else{
						videos.save();
					}
					Elements luxiangElements = videoDoc.select("#r_luxiang");
					Elements jijingElements = videoDoc.select("#r_jijing");
					int videoType = 1;
					Elements[] allLinks = {luxiangElements, jijingElements};
					for(Elements links:allLinks){
						if(links.size()>0){
							Elements aLxElements = links.get(0).select("a");
							for(Element aLx:aLxElements){
								//  www.zuqiu.la/play_video.php?cid=80874&preview=1
								String playUrl = MAIN_SITE + aLx.attr("href");
								//  [PPTV全场集锦] 英超-莫拉塔头球制胜 切尔西1-0击败曼联
								String title = aLx.attr("title");
								
								Connection videoConnect = Jsoup.connect(playUrl).ignoreContentType(true);
								for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
									videoConnect.header(entry.getKey(), entry.getValue());
								}
								videoConnect.header("Referer", videoUrl);
								Connection playerData = videoConnect.data();
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
								}else{
									continue;
								}
								
								System.err.println("---- realTitle："+title+" playLink："+playUrl+" realLink："+realUrl);
								
								VideosRealLinks realLink = new VideosRealLinks();
								realLink.set("videos_id", videos.get("id"));
								realLink.set("source_url", videoUrl);
								realLink.set("real_url", realUrl);
								realLink.set("title", title);
								realLink.set("player_type",playerType);
								realLink.set("video_type", String.valueOf(videoType));
								lstAllLinks.add(realLink);
							}
						}
						videoType++;
					}
					
				} catch (IOException e) {
					System.err.println();
				}
				
				Db.batchSave(lstAllLinks, lstAllLinks.size());
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
