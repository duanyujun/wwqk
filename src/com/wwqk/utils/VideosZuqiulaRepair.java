package com.wwqk.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.PlayerEnum;
import com.wwqk.constants.VideosLeagueEnum;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.Team;
import com.wwqk.model.Videos;
import com.wwqk.model.VideosRealLinks;

public class VideosZuqiulaRepair {
	
	private static final String MAIN_SITE = "http://www.zuqiu.la";
	private static final Pattern ALL_COUNT_PATTERN = Pattern.compile("总共(\\d+)个");
	private static final Pattern DATE_PATTERN = Pattern.compile("(\\d+月\\d+日)");
	private static int allCount = 0;
	
	//http://www.zuqiu.la/video/index.php?p=40&type=9
	
	public static void collect(boolean isInit){
		boolean isFirst = true;
		for(int i=9; i<10; i++){
		//for(int i=9; i>1; i--){
			String url = "http://www.zuqiu.la/video/index.php?p=1&type="+i;
			if(isFirst){
				url = "http://www.zuqiu.la/video/index.php?p=25&type="+i;
			}
			
			System.err.println("~~~~~~page："+url);
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
					int pageStart = 2;
					if(isFirst){
						pageStart = 26;
						isFirst = false;
					}
					for(int j=pageStart;j<=pageCount;j++){
						url = "http://www.zuqiu.la/video/index.php?p="+j+"&type="+i;
						System.err.println("~~~~~~page："+url);
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
				System.err.println(">>>> handle："+matchTitle+" url："+videoUrl+"   count："+(++allCount));
				Videos videos = null;
				//查询数据库中是否存在
//				VideosRealLinks existLinks = VideosRealLinks.dao.findFirst("select * from videos_real_links where source_url = ? ", videoUrl);
//				if(existLinks!=null){
//					continue;
//				}else{
//					
//				}
				//查询该场比赛是否存在
				Videos videosDb = Videos.dao.findFirst("select * from videos where source_url = ?", videoUrl);
				if(videosDb!=null){
					videos = videosDb;
				}else{
					videos = new Videos();
				}
				if(videosDb==null){
					videos.set("league_id", EnumUtils.getValue(VideosLeagueEnum.values(), String.valueOf(sourceLeagueId)));
					videos.set("match_title", StringUtils.trim(matchTitle));
				}
				
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
						if(videosDb==null){
							videos.set("home_team", homeAndAway[0]);
							if(homeAndAway.length>1){
								videos.set("away_team", homeAndAway[1]);
							}
						}
					}
				}
				if(videosDb==null){
					videos.set("source_url", videoUrl);
				}
				
				List<VideosRealLinks> lstAllLinks = new ArrayList<VideosRealLinks>();
				
				Connection connect = Jsoup.connect(videoUrl).ignoreContentType(true);
				for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
					connect.header(entry.getKey(), entry.getValue());
				}
				connect.header("Referer", referUrl);
				Connection data = connect.data();
				try {
					Document videoDoc = data.get();
					String summary = videoDoc.select(".left_box").get(0).child(1).html();
					if(StringUtils.isBlank(videos.getStr("summary"))){
						videos.set("summary", summary);
					}
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
								if(playDocHtml.contains("type=qqnba")){
									playerType = PlayerEnum.QQNBA.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else if(playDocHtml.contains("type=qq")){
									playerType = PlayerEnum.QQ.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else if(playDocHtml.contains("pptv")){
									playerType = PlayerEnum.PPTV.getKey();
									realUrl = getPPTVSrc(playDocHtml);
								}else if(playDocHtml.contains("ssports")){
									playerType = PlayerEnum.SSPORTS.getKey();
									realUrl = getSsportsSrc(playDocHtml);
								}else if(playDocHtml.contains("type=letv")){
									playerType = PlayerEnum.LETV.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else if(playDocHtml.contains("type=cntv")){
									playerType = PlayerEnum.CNTV.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else if(playDocHtml.contains("type=sina2")){
									playerType = PlayerEnum.SINA2.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else if(playDocHtml.contains("type=sina")){
									playerType = PlayerEnum.SINA.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else if(playDocHtml.contains("youku")){
									playerType = PlayerEnum.YOUKU.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else if(playDocHtml.contains("tudou")){
									playerType = PlayerEnum.TUDOU.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else if(playDocHtml.contains("type=56")){
									playerType = PlayerEnum.V56.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else if(playDocHtml.contains("type=sohu")){
									playerType = PlayerEnum.SOHU.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else if(playDocHtml.contains("type=kandian")){
									playerType = PlayerEnum.KANDIAN.getKey();
									realUrl = getCommonSrc(playDocHtml);
								}else{
									continue;
								}
								
								//System.err.println("---- realTitle："+title+" playLink："+playUrl+" realLink："+realUrl);
								
								VideosRealLinks realLink = new VideosRealLinks();
								realLink.set("videos_id", videos.get("id"));
								realLink.set("source_url", videoUrl);
								realLink.set("real_url", realUrl);
								realLink.set("title", title);
								realLink.set("player_type",playerType);
								realLink.set("video_type", String.valueOf(videoType));
								VideosRealLinks dbLink = VideosRealLinks.dao.
										findFirst("select * from videos_real_links where player_type = ? and real_url = ?", playerType, realUrl);
								if(dbLink==null){
									lstAllLinks.add(realLink);
								}
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
	
	private static String getCommonSrc(String html){
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
	
	public static void updateDesc(){
		List<LeagueMatchHistory> lstMatches = LeagueMatchHistory.dao.find("select * from league_match_history where description is null");
		for(LeagueMatchHistory history : lstMatches){
			StringBuilder sb = new StringBuilder();
			Team homeTeam = Team.dao.findById(history.get("home_team_id"));
			String yearShow = history.getStr("year_show");
			yearShow = yearShow.substring(0, 2)+"/"+yearShow.substring(2);
			sb.append("北京时间 - ").append(DateTimeUtils.formatDate(history.getDate("match_date"), "yyyy年M月d日 H点m分，"))
			.append(EnumUtils.getValue(LeagueEnum.values(), history.getStr("league_id"))).append(yearShow)
			.append("赛季第").append(history.get("match_round")).append("轮，").append(history.getStr("home_team_name"))
			.append("坐镇主场").append(homeTeam.getStr("venue_name")).append("迎战").append(history.getStr("away_team_name")).append("。")
			.append("趣点足球网将为您提供直播信号导航，欢迎准时收看！");
			history.set("description", sb.toString());
		}
		if(lstMatches.size()>0){
			Db.batchUpdate(lstMatches, lstMatches.size());
		}
		
		List<Videos> lstVideos = Videos.dao.find("select * from videos where description is null");
		for(Videos videos : lstVideos){
			StringBuilder sb = new StringBuilder();
			String desc = null;
			if(Integer.valueOf(videos.getStr("league_id"))<6){
				if(StringUtils.isNotBlank(videos.getStr("match_history_id"))){
					LeagueMatchHistory history = LeagueMatchHistory.dao.findById(videos.getStr("match_history_id"));
					//2017年11月17日 - 北京时间11月19日0点,法甲联赛战火重燃,巴黎圣日耳曼将坐镇主场王子公园球场迎战南特。
					if(history!=null){
						Team homeTeam = Team.dao.findById(history.get("home_team_id"));
						String yearShow = history.getStr("year_show");
						yearShow = yearShow.substring(0, 2)+"/"+yearShow.substring(2);
						sb.append("北京时间 - ").append(DateTimeUtils.formatDate(history.getDate("match_date"), "yyyy年M月d日 H点m分，"))
						.append(EnumUtils.getValue(LeagueEnum.values(), videos.getStr("league_id"))).append(yearShow)
						.append("赛季第").append(history.get("match_round")).append("轮，").append(videos.getStr("home_team"))
						.append("坐镇主场").append(homeTeam.getStr("venue_name")).append("迎战").append(videos.getStr("away_team")).append("。");
						desc = sb.toString();
					}
				}
			}
			if(StringUtils.isBlank(desc)){
				if(videos.getDate("match_date")!=null){
					sb.append("北京时间 - ").append(DateTimeUtils.formatDate(videos.getDate("match_date"), "yyyy年M月d日 H点m分，"))
					.append(videos.getStr("match_title").replaceAll("\\d+年\\d+月\\d+日", ""));
					desc = sb.toString();
				}
			}
			videos.set("description", desc);
		}
		if(lstVideos.size()>0){
			Db.batchUpdate(lstVideos, lstVideos.size());
		}
	}
	
	/**
	 * 标红视频
	 */
	public static void updateRed(){
		CommonUtils.initNameIdMap();
		Set<String> redSet = new HashSet<String>();
		String[] redArray = {"676","662","661","663","660","675","2017","2020","2016","2015","2021","961","13410",
				"966","964","1001","971","1244","1270","1242","1241","1245","1240","886","884","885","890"};
		for(String redId:redArray){
			redSet.add(redId);
		}
		List<Videos> lstNeedUpdate = new ArrayList<Videos>();
		List<Videos> lstVideos = Videos.dao.find("select * from videos where is_red = '0' and match_history_id!='0'  ");
		for(Videos videos : lstVideos){
			String homeId = CommonUtils.nameIdMap.get(videos.getStr("home_team"));
			if(redSet.contains(homeId)){
				videos.set("is_red", "1");
			}else{
				String awayId = CommonUtils.nameIdMap.get(videos.getStr("away_team"));
				if(redSet.contains(awayId)){
					videos.set("is_red", "1");
				}
			}
			if("1".equals(videos.getStr("is_red"))){
				lstNeedUpdate.add(videos);
			}
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
		
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
