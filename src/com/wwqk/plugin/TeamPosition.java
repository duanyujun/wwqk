package com.wwqk.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.League;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.Team;
import com.wwqk.utils.FetchHtmlUtils;

/** 
 * @date 创建时间：2017-8-26 下午10:22:11    
 */

public class TeamPosition {
	
	private static final String SITE_PROFIX = "http://cn.soccerway.com";

	public static void getPosition(){
		System.err.println("handle team positon and match start!!!");
		HttpClient client = new DefaultHttpClient();  
		List<League> leagues = League.dao.find("select * from league");
		try {
			for(League league : leagues){
				handleTeamsUrl(league.getStr("league_url"), league.getStr("id"), client);
			}
		} catch (Exception e) {
			System.err.println("^^^^^^^"+e.getMessage()+" +++++"+e);
		}
		client.getConnectionManager().shutdown();
		System.err.println("handle team positon and match end!!!");
	}
	
	private static void handleTeamsUrl(String leagueUrl, String leagueId, HttpClient client) throws IOException{
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
	private static void handleLeaguePosition(Document document, String leagueId, String roundId){
		List<Team> lstTeams = new ArrayList<Team>();
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
				teamDB.set("rank", rank);
				lstTeams.add(teamDB);
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
		if(lstTeams.size()>0){
			Db.batchUpdate(lstTeams, lstTeams.size());
		}
	}
	
}
