package com.wwqk.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.League;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;

public class ChangePlayerJob implements Job {
	
	//TODO　球员小图片 http://cache.images.core.optasports.com/soccer/players/50x50/79495.png
	private HttpClient httpClient = new DefaultHttpClient();
	String clearString = "<.*?/>";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//updateTeamEnName();
		updateMatch();
	}

	
	private void updateTeamEnName(){
		List<Team> lstTeam = Team.dao.find("select * from team");
		for(Team team : lstTeam){
			team.set("name_en", CommonUtils.getEnName(team.getStr("team_url")));
		}
		Db.batchUpdate(lstTeam, lstTeam.size());
	}
	
	private void updatePlayer(){
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		List<Player> lstPlayers = Player.dao.find("select * from player order by id+0 asc");
		int count = 1;
		for(Player player : lstPlayers){
//			System.err.println("player id：" + player.getStr("id")+"__player name："+player.getStr("name")+"__count:"+count++);
//			try {
//				Thread.sleep(300);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			if("左".equals(player.get("first_name"))
//		       || "右".equals(player.get("first_name"))
//		       || "双".equals(player.get("first_name"))
//		       || "0".equals(player.get("first_name"))
//		       ){
//				String playerContent = FetchHtmlUtils.getHtmlContent(httpClient, player.getStr("player_url"));
//				player.set("first_name", CommonUtils.matcherString(CommonUtils.getPatternByName("名字"), playerContent));
//				lstNeedUpdate.add(player);
//			}
			System.err.println(count++);
			player.set("en_url", CommonUtils.getEnName(player.getStr("player_url")));
			lstNeedUpdate.add(player);
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
	private void updateMatch(){
		Map<String, String> nameENNameMap = new HashMap<String, String>();
		List<Team> lstTeams = Team.dao.find("select id,name,name_en from team");
		for(Team team : lstTeams){
			nameENNameMap.put(team.getStr("name"), team.getStr("name_en"));
		}
		List<LeagueMatch> lstMatch = LeagueMatch.dao.find("select * from league_match");
		for(LeagueMatch match : lstMatch){
			match.set("home_team_en_name", nameENNameMap.get(match.getStr("home_team_name")));
			match.set("away_team_en_name", nameENNameMap.get(match.getStr("away_team_name")));
		}
		Db.batchUpdate(lstMatch, lstMatch.size());
		
		List<LeagueMatchHistory> lstHistory = LeagueMatchHistory.dao.find("select * from league_match_history ");
		for(LeagueMatchHistory history : lstHistory){
			history.set("home_team_en_name", nameENNameMap.get(history.getStr("home_team_name")));
			history.set("away_team_en_name", nameENNameMap.get(history.getStr("away_team_name")));
		}
		
		Db.batchUpdate(lstHistory, lstHistory.size());
	}
	
}






