package com.wwqk.job;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.League;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.MatchLive;
import com.wwqk.model.Team;
import com.wwqk.plugin.Match163;
import com.wwqk.plugin.MatchSina;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.FetchHtmlUtils;
import com.wwqk.utils.StringUtils;

/**
 * 
 * TODO 1、对已经同步的比赛进行归档
 * 		2、获取直播地址
 * 	    3、替换小图片
 * 在ProductJob运行完后执行
 */
public class ProductMatchJob implements Job {
	
	private static final String SITE_PROFIX = "http://cn.soccerway.com";
	private static final String LIVE_SITE_PROFIX = "http://www.zhibo7.com";
	private HttpClient client;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		client = new DefaultHttpClient();  
		System.err.println("ProductMatchJob start");
		//球队排名任务
		teamPositionTask();
		//初始化球队名称ID源
		CommonUtils.initNameIdMap();
		//使用163的比赛源
		MatchSina.archiveMatch(CommonUtils.nameIdMap, CommonUtils.nameENNameMap);
		getLiveWebsite();
		System.err.println("ProductMatchJob end");
	}
	
	private void getLiveWebsite(){
		Map<String, String> leagueMap = new HashMap<String, String>();
		leagueMap.put("英超", "1");
		leagueMap.put("西甲", "2");
		leagueMap.put("德甲", "3");
		leagueMap.put("意甲", "4");
		leagueMap.put("法甲", "5");
		try {
			Document document = Jsoup.connect("http://www.zhibo7.com/").get();
			//Document document =  Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
			Elements elements = document.select(".live_content");
			if(elements.size()>0){
				for(Element element : elements){
					String dateStr = null;
					Elements h1 = element.select("h1");
					if(h1.size()>0){
						dateStr = h1.get(0).child(0).text().substring(0, 10);
					}
					Elements matches = element.select("tr");
					for(Element match : matches){
						if(match.children().size()<3){
							continue;
						}
						if(leagueMap.get(match.child(1).text())!=null){
							List<MatchLive> lstMatchLive = new ArrayList<MatchLive>();
							String matchStr = match.child(2).text(); //水晶宫vs切尔西
							String homeTeamName = matchStr.split("vs")[0];
							String awayTeamName = matchStr.split("vs")[1];
							if(CommonUtils.nameIdMap.get(homeTeamName)!=null && CommonUtils.nameIdMap.get(awayTeamName)!=null){
								Elements liveLinks = match.child(3).select("a");
								for(Element liveLink : liveLinks){
									if("其它直播".equals(liveLink.text())){
										continue;
									}
									MatchLive matchLive = new MatchLive();
									matchLive.set("match_key", dateStr+"-"+CommonUtils.nameIdMap.get(homeTeamName)+"vs"+CommonUtils.nameIdMap.get(awayTeamName));
									matchLive.set("live_name", liveLink.text());
									if(liveLink.attr("href").startsWith("http")){
										matchLive.set("live_url", liveLink.attr("href"));
									}else{
										matchLive.set("live_url", getRealLink(LIVE_SITE_PROFIX+liveLink.attr("href")));
									}
									matchLive.set("league_id", leagueMap.get(match.child(1).text()));
									matchLive.set("home_team_id", CommonUtils.nameIdMap.get(homeTeamName));
									matchLive.set("home_team_name", homeTeamName);
									matchLive.set("away_team_id", CommonUtils.nameIdMap.get(awayTeamName));
									matchLive.set("away_team_name", awayTeamName);
									matchLive.set("match_date", DateTimeUtils.parseDate(dateStr+" "+match.child(0).text(), DateTimeUtils.ISO_DATETIME_NOSEC_FORMAT_ARRAY));
									lstMatchLive.add(matchLive);
								}
							}
							
							saveOneMatchLive(lstMatchLive);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getRealLink(String link){
		if(StringUtils.isBlank(link)){
			return link;
		}
		try {
			Document document = Jsoup.connect(link).get();
			List<Element> lstLinks = document.select(".col-change");
			if(lstLinks.size()>0){
				List<Element> aLink = lstLinks.get(0).select("a");
				if(aLink.size()>0){
					return aLink.get(0).attr("href");
				}
			}
		} catch (IOException e) {
			
		}
		return link;
	}
	
	public static final String bytesToHexString(byte[] bArray) {   
	    StringBuffer sb = new StringBuffer(bArray.length);   
	    String sTemp;   
	    for (int i = 0; i < bArray.length; i++) {   
	     sTemp = Integer.toHexString(0xFF & bArray[i]);   
	     if (sTemp.length() < 2)   
	      sb.append(0);   
	     sb.append(sTemp.toUpperCase());   
	    }   
	    return sb.toString();   
	}  
	
	@Before(Tx.class)
	private void saveOneMatchLive(List<MatchLive> lstMatchLive){
		if(lstMatchLive.size()>0){
			List<MatchLive> lstDBLive = MatchLive.dao.find("select * from match_live where match_key = ?", lstMatchLive.get(0).getStr("match_key"));
			if(lstDBLive.size()==0){
				Db.batchSave(lstMatchLive, lstMatchLive.size());
			}else{
				//进行判断，频道名称或者链接相同的一律不添加
				Set<String> existSet = new HashSet<String>();
				for(MatchLive liveDB : lstDBLive){
					existSet.add(liveDB.getStr("live_name"));
					existSet.add(liveDB.getStr("live_url"));
				}
				List<MatchLive> lstNeedInsert = new ArrayList<MatchLive>();
				for(MatchLive matchLive : lstMatchLive){
					if(existSet.contains(matchLive.getStr("live_name")) || existSet.contains(matchLive.getStr("live_url"))){
						continue;
					}
					lstNeedInsert.add(matchLive);
				}
				if(lstNeedInsert.size()>0){
					Db.batchSave(lstNeedInsert, lstNeedInsert.size());
				}
			}
		}
	}
	
	private void teamPositionTask(){
		System.err.println("handle team positon and match start!!!");
		List<League> leagues = League.dao.find("select * from league");
		try {
			for(League league : leagues){
				handleTeamsUrl(league.getStr("league_url"), league.getStr("id"));
			}
		} catch (Exception e) {
			System.err.println("^^^^^^^"+e.getMessage()+" +++++"+e);
		}
		System.err.println("handle team positon and match end!!!");
	}
	
	private void handleTeamsUrl(String leagueUrl, String leagueId) throws IOException{
		String html = FetchHtmlUtils.getHtmlContent(client, leagueUrl);
		Document document = Jsoup.parse(html);
		//Document document = Jsoup.connect(leagueUrl).get(); 
		Elements bodyElement = document.select(".detailed-table");
		String roundId = null;
		if(bodyElement.size()>0){
			roundId = bodyElement.get(0).attr("data-round_id");
		}
		//排名
		handleLeaguePosition(document, leagueId, roundId);
		
	}
	
	
	@Before(Tx.class)
	private void handleLeaguePosition(Document document, String leagueId, String roundId){
		List<LeaguePosition> lstPositions = new ArrayList<LeaguePosition>();
		Elements bodyElement = document.select(".detailed-table");
		Elements teamLinks = bodyElement.get(0).select(".team_rank");
		for(Element element : teamLinks){
			String teamId = element.attr("data-team_id");
			String rank = element.child(0).html();
			String teamName = element.child(2).text();
			//TODO add column
			String teamUrl = SITE_PROFIX + element.child(2).child(0).attr("href");
			String roundCount = element.child(3).text();
			String winCount = element.child(4).text();
			String evenCount = element.child(5).text();
			String loseCount = element.child(6).text();
			String winGoalCount = element.child(7).text();
			String loseGoalCount = element.child(8).text();
			String goalCount = element.child(9).text();
			String points = element.child(10).text();
			
			LeaguePosition leaguePosition = new LeaguePosition();
			leaguePosition.set("league_id", leagueId);
			leaguePosition.set("round_id", roundId);
			leaguePosition.set("rank", rank);
			leaguePosition.set("team_id", teamId);
			Team teamDB = Team.dao.findById(teamId);
			if(teamDB!=null){
				teamName = teamDB.get("name");
				leaguePosition.set("team_name_en", teamDB.getStr("name_en"));
			}
			leaguePosition.set("team_name", teamName);
			leaguePosition.set("team_url", teamUrl);
			leaguePosition.set("round_count", roundCount);
			leaguePosition.set("win_count", winCount);
			leaguePosition.set("even_count", evenCount);
			leaguePosition.set("lose_count", loseCount);
			leaguePosition.set("win_goal_count", winGoalCount);
			leaguePosition.set("lose_goal_count", loseGoalCount);
			leaguePosition.set("goal_count", goalCount);
			leaguePosition.set("points", points);
			leaguePosition.set("update_time", new Date());
			
			lstPositions.add(leaguePosition);
		}
		if(lstPositions.size()>0){
			Db.update("delete from league_position where league_id = ?", leagueId);
			Db.batchSave(lstPositions, lstPositions.size());
		}
	}

	
}


