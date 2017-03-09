package com.wwqk.job;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.League;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.MatchLive;
import com.wwqk.model.MatchSource;
import com.wwqk.model.Team;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.FetchHtmlUtils;
import com.wwqk.utils.StringUtils;

/**
 * 
 * TODO 1、对已经同步的比赛进行归档
 * 		2、获取直播地址
 * 	    3、替换小图片
 * 在ProductJob运行完后执行
 */
public class ProductMatchJob2 implements Job {
	
	private static final String SITE_PROFIX = "http://cn.soccerway.com";
	private static final String LIVE_SITE_PROFIX = "http://www.zhibo7.com";
	private HttpClient client;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		client = new DefaultHttpClient();  
		System.err.println("ProductMatchJob start");
		//球队排名任务
		teamPositionTask();
		archiveMatch();
		getLiveWebsite();
		System.err.println("ProductMatchJob end");
	}
	
	private void archiveMatch(){
		List<MatchSource> lstMatchSources = MatchSource.dao.find("select * from match_source");
		Map<String, String> nameIdMap = getTeamNameIdMap();
		//http://goal.sports.163.com/39/schedule/team/0_17_2016.html
		for(MatchSource source : lstMatchSources){
			Map<String, LeagueMatchHistory> currentMap = null;
			for(int i=1; i<=source.getInt("current_round"); i++){
				List<LeagueMatchHistory> lstNeedInsert = new ArrayList<LeagueMatchHistory>();
				List<LeagueMatchHistory> lstNeedUpdate = new ArrayList<LeagueMatchHistory>();
				LeagueMatchHistory historyExist = LeagueMatchHistory.dao.findFirst("select * from league_match_history where league_id =? and year=? and round=?", source.getStr("league_id"), source.getInt("year"), i);
				String matchUrl = "http://goal.sports.163.com/"+source.getStr("league_id_163")+"/schedule/team/0_"+i+"_"+source.getInt("year")+".html";
				if(historyExist==null){
					Map<String, LeagueMatchHistory> map = getMatchHistory(matchUrl, source, nameIdMap);
					lstNeedInsert.addAll(map2List(map));
					if(i == source.getInt("current_round")){
						currentMap = map;
					}
				}else{
					//更新当前轮历史数据
					if(i == source.getInt("current_round")){
						currentMap = getMatchHistory(matchUrl, source, nameIdMap);
						lstNeedUpdate.addAll(map2List(currentMap));
					}
				}
				
				saveOneRound(lstNeedInsert, lstNeedUpdate);
			}
			
			//处理当前轮次：1、更新联赛当前赛事；2、是否需要将当前轮次+1；
			if(currentMap!=null){
				List<LeagueMatch> lstMatch = new ArrayList<LeagueMatch>();
				for(Entry<String, LeagueMatchHistory> entry : currentMap.entrySet()){
					LeagueMatch match = new LeagueMatch();
					match.set("match_date", entry.getValue().getDate("match_date"));
					match.set("home_team_id", entry.getValue().getStr("home_team_id"));
					match.set("home_team_name", entry.getValue().getStr("home_team_name"));
					match.set("away_team_id", entry.getValue().getStr("away_team_id"));
					match.set("away_team_name", entry.getValue().getStr("away_team_name"));
					match.set("home_team_en_name", nameENNameMap.get(entry.getValue().getStr("home_team_name")));
					match.set("away_team_en_name", nameENNameMap.get(entry.getValue().getStr("away_team_name")));
					match.set("match_weekday", DateTimeUtils.formatDate2WeekDay(entry.getValue().getDate("match_date")) );
					match.set("result", entry.getValue().getStr("result"));
					match.set("league_id", entry.getValue().getStr("league_id"));
					match.set("round", entry.getValue().getStr("round"));
					match.set("status", entry.getValue().getStr("status"));
					match.set("update_time", new Date());
					lstMatch.add(match);
				}
				
				Db.update("delete from league_match where league_id = ? ", source.getStr("league_id"));
				Db.batchSave(lstMatch, lstMatch.size());
				
				//是否需要将当前轮次+1；
				boolean updateCurrentRound = true;
				for(LeagueMatch match : lstMatch){
					if(!match.getStr("result").contains("-") && match.getDate("match_date").after(new Date())){
						updateCurrentRound = false;
					}
				}
				if(updateCurrentRound){
					int currentRound = source.getInt("current_round");
					source.set("current_round", currentRound+1>source.getInt("round_max")?source.getInt("round_max"):currentRound+1);
					source.update();
				}
			}
		}
	}
	
	private void getLiveWebsite(){
		Map<String, String> leagueMap = new HashMap<String, String>();
		leagueMap.put("英超", "1");
		leagueMap.put("西甲", "2");
		leagueMap.put("德甲", "3");
		leagueMap.put("意甲", "4");
		leagueMap.put("法甲", "5");
		Map<String, String> nameIdMap = getTeamNameIdMap();
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
							if(nameIdMap.get(homeTeamName)!=null && nameIdMap.get(awayTeamName)!=null){
								Elements liveLinks = match.child(3).select("a");
								for(Element liveLink : liveLinks){
									if("其它直播".equals(liveLink.text())){
										continue;
									}
									MatchLive matchLive = new MatchLive();
									matchLive.set("match_key", dateStr+"-"+nameIdMap.get(homeTeamName)+"vs"+nameIdMap.get(awayTeamName));
									matchLive.set("live_name", liveLink.text());
									if(liveLink.attr("href").startsWith("http")){
										matchLive.set("live_url", liveLink.attr("href"));
									}else{
										matchLive.set("live_url", getRealLink(LIVE_SITE_PROFIX+liveLink.attr("href")));
									}
									matchLive.set("league_id", leagueMap.get(match.child(1).text()));
									matchLive.set("home_team_id", nameIdMap.get(homeTeamName));
									matchLive.set("home_team_name", homeTeamName);
									matchLive.set("away_team_id", nameIdMap.get(awayTeamName));
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
	
	@Before(Tx.class)
	private void saveOneRound(List<LeagueMatchHistory> lstNeedInsert, List<LeagueMatchHistory> lstNeedUpdate){
		if(lstNeedInsert.size()>0){
			Db.batchSave(lstNeedInsert, lstNeedInsert.size());
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
	private Map<String, String> nameENNameMap = new HashMap<String, String>();
	private Map<String, String> getTeamNameIdMap(){
		Map<String, String> map = new HashMap<String, String>();
		List<Team> lstTeams = Team.dao.find("select id,name,name_en from team");
		for(Team team : lstTeams){
			map.put(team.getStr("name"), team.getStr("id"));
			nameENNameMap.put(team.getStr("name"), team.getStr("name_en"));
		}
		return map;
	}
	
	private Map<String, LeagueMatchHistory> getMatchHistory(String matchUrl, MatchSource source, Map<String, String> nameIdMap){
		Map<String, LeagueMatchHistory> map = new HashMap<String, LeagueMatchHistory>();
		try {
			Document document = Jsoup.connect(matchUrl).get();
			Elements matchAreaElements = document.select(".leftList4");
			if(matchAreaElements.size()>0){
				Elements trElements = matchAreaElements.get(0).select("tr");
				for(Element element : trElements){
					if(element.attr("id")!=null && element.attr("id").contains("hide_")){
						continue;
					}
					Elements tdElements = element.select("td");
					if(tdElements.size()>0){
						LeagueMatchHistory history = new LeagueMatchHistory();
						history.set("round", tdElements.get(0).text());
						history.set("match_date", DateTimeUtils.parseDate(tdElements.get(1).text(), DateTimeUtils.ISO_DATETIME_NOSEC_FORMAT_ARRAY));
						history.set("status", tdElements.get(2).text());
						history.set("home_team_id", nameIdMap.get(tdElements.get(3).text()));
						history.set("home_team_name", tdElements.get(3).text());
						history.set("away_team_id", nameIdMap.get(tdElements.get(5).text()));
						history.set("away_team_name", tdElements.get(5).text());
						history.set("home_team_en_name", nameENNameMap.get(tdElements.get(3).text()));
						history.set("away_team_en_name", nameENNameMap.get(tdElements.get(5).text()));
						
						if(!tdElements.get(4).text().contains("-")){
							history.set("result", tdElements.get(1).text().substring(tdElements.get(1).text().indexOf(" ")+1));
						}else{
							history.set("result", tdElements.get(4).text().replace("-", " - "));
						}
						history.set("league_id", source.getStr("league_id"));
						history.set("league_name", EnumUtils.getValue(LeagueEnum.values(), source.getStr("league_id")));
						history.set("year", source.getInt("year"));
						if(map.get(tdElements.get(3).text())==null){
							map.put(tdElements.get(3).text(), history);
						}else{
							//判断是否有重复的
							if("完场".equals(tdElements.get(2).text())){
								map.put(tdElements.get(3).text(), history);
							}
						}
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private List<LeagueMatchHistory> map2List(Map<String, LeagueMatchHistory> map){
		List<LeagueMatchHistory> lstHistory = new ArrayList<LeagueMatchHistory>();
		for(Entry<String, LeagueMatchHistory> entry:map.entrySet()){
			LeagueMatchHistory history = entry.getValue();
			history.set("id", DateTimeUtils.formatDate(history.getDate("match_date"))+"-"+history.getStr("home_team_id")+"vs"+history.getStr("away_team_id"));
			history.set("match_weekday", DateTimeUtils.formatDate2WeekDay(history.getDate("match_date")));
			lstHistory.add(history);
		}
		return lstHistory;
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


