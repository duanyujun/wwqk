package com.wwqk.job;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.wwqk.model.MatchSource;
import com.wwqk.model.Team;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.FetchHtmlUtils;

/**
 * 
 * TODO 1、对已经同步的比赛进行归档
 * 		2、获取直播地址
 * 	    3、替换小图片
 * 在ProductJob运行完后执行
 */
public class ProductMatchJob implements Job {
	
	private static final String SITE_PROFIX = "http://cn.soccerway.com";
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
					match.set("match_weekday", entry.getValue().getStr("match_weekday"));
					match.set("result", entry.getValue().getStr("result"));
					match.set("league_id", entry.getValue().getStr("league_id"));
					match.set("round", entry.getValue().getStr("round"));
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
	
	private Map<String, String> getTeamNameIdMap(){
		Map<String, String> map = new HashMap<String, String>();
		List<Team> lstTeams = Team.dao.find("select id,name from team");
		for(Team team : lstTeams){
			map.put(team.getStr("name"), team.getStr("id"));
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
						history.set("home_team_id", nameIdMap.get(tdElements.get(3).text()));
						history.set("home_team_name", tdElements.get(3).text());
						history.set("away_team_id", nameIdMap.get(tdElements.get(5).text()));
						history.set("away_team_name", tdElements.get(5).text());
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
			history.set("match_weekday", DateTimeUtils.formatDate(history.getDate("match_date"), DateTimeUtils.ISO_WEEK_FORMAT));
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


