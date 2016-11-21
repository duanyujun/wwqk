package com.wwqk.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.LeagueAssists163;
import com.wwqk.model.LeagueShooter163;
import com.wwqk.model.Player;
import com.wwqk.model.ShooterAssistsSource;
import com.wwqk.utils.FetchHtmlUtils;

public class SyncShooterAssistsJob implements Job {
	
	private HttpClient httpClient = new DefaultHttpClient();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("handle SyncShooterAssistsJob start!!!");
		syncShooter();
		syncAssists();
		translateShooter();
		translateAssists();
		//copy to league_shooter， league_assists
		System.err.println("handle SyncShooterAssistsJob end!!!");
	}
	
	private void syncShooter(){
		List<ShooterAssistsSource> lstSources = ShooterAssistsSource.dao.find("select * from shooter_assists_source where type = 1");
		for(ShooterAssistsSource source : lstSources){
			System.err.println("handle shooter start!!!"+" league: "+source.getStr("league_id")+" url:"+source.getStr("url"));
			String shooterHtml = FetchHtmlUtils.getHtmlContent(httpClient, source.getStr("url"));
			Document document = Jsoup.parse(shooterHtml);
			Elements dataTableElements = document.select(".daTb01");
			if(dataTableElements.size()>0){
				List<LeagueShooter163> lstShooter163 = new ArrayList<LeagueShooter163>();
				Elements playerElements = dataTableElements.get(0).select("tr");
				//去掉第一个和最后一个tr
				for(int i=1; i<playerElements.size()-1; i++){
					Element trElement = playerElements.get(i);
					String rank = trElement.child(0).text();
					String playerName = trElement.child(1).text();
					String teamName = trElement.child(2).text();
					String goalCount = trElement.child(5).text();
					String penaltyCount = trElement.child(6).text();
					LeagueShooter163 shooter163 = new LeagueShooter163();
					shooter163.set("rank", rank);
					shooter163.set("player_name_163", playerName);
					shooter163.set("team_name_163", teamName);
					shooter163.set("goal_count", goalCount);
					shooter163.set("penalty_count", penaltyCount);
					lstShooter163.add(shooter163);
				}
				//查询数据库
				List<LeagueShooter163> lstShooter163DB = LeagueShooter163.dao.find("select * from league_shooter_163 where league_id = ? ", source.getStr("league_id"));
				//key： playerName_teamName
				Map<String, LeagueShooter163> map = new HashMap<String, LeagueShooter163>();
				for(LeagueShooter163 db:lstShooter163DB){
					map.put(db.getStr("player_name_163")+"_"+db.getStr("team_name_163"), db);
				}
				
				List<LeagueShooter163> needUpdateList = new ArrayList<LeagueShooter163>();
				List<LeagueShooter163> needInsertList = new ArrayList<LeagueShooter163>();
				for(LeagueShooter163 shooter163:lstShooter163){
					String key = shooter163.getStr("player_name_163")+"_"+shooter163.getStr("team_name_163");
					if(map.get(key)!=null){
						LeagueShooter163 shooterDB = map.get(key);
						shooterDB.set("rank", shooter163.get("rank"));
						shooterDB.set("goal_count", shooter163.get("goal_count"));
						shooterDB.set("penalty_count", shooter163.get("penalty_count"));
						shooterDB.set("update_time", new Date());
						needUpdateList.add(shooterDB);
					}else{
						shooter163.set("league_id", source.getStr("league_id"));
						shooter163.set("update_time", new Date());
						needInsertList.add(shooter163);
					}
				}
				if(needInsertList.size()>0){
					Db.batchSave(needInsertList, needInsertList.size());
				}
				if(needUpdateList.size()>0){
					Db.batchUpdate(needUpdateList, needUpdateList.size());
				}
			}
		}
	}
	
	private void syncAssists(){
		List<ShooterAssistsSource> lstSources = ShooterAssistsSource.dao.find("select * from shooter_assists_source where type = 2");
		for(ShooterAssistsSource source : lstSources){
			System.err.println("handle assists start!!!"+" league: "+source.getStr("league_id")+" url:"+source.getStr("url"));
			String assistsHtml = FetchHtmlUtils.getHtmlContent(httpClient, source.getStr("url"));
			Document document = Jsoup.parse(assistsHtml);
			Elements dataTableElements = document.select(".daTb01");
			if(dataTableElements.size()>0){
				List<LeagueAssists163> lstAssists163 = new ArrayList<LeagueAssists163>();
				Elements playerElements = dataTableElements.get(0).select("tr");
				//去掉第一个和最后一个tr
				for(int i=1; i<playerElements.size()-1; i++){
					Element trElement = playerElements.get(i);
					String rank = trElement.child(0).text();
					String playerName = trElement.child(1).text();
					String teamName = trElement.child(2).text();
					String assistsCount = trElement.child(7).text();
					LeagueAssists163 assists163 = new LeagueAssists163();
					assists163.set("rank", rank);
					assists163.set("player_name_163", playerName);
					assists163.set("team_name_163", teamName);
					assists163.set("assists_count", assistsCount);
					lstAssists163.add(assists163);
				}
				//查询数据库
				List<LeagueAssists163> lstAssists163DB = LeagueAssists163.dao.find("select * from league_assists_163 where league_id = ? ", source.getStr("league_id"));
				//key： playerName_teamName
				Map<String, LeagueAssists163> map = new HashMap<String, LeagueAssists163>();
				for(LeagueAssists163 db:lstAssists163DB){
					map.put(db.getStr("player_name_163")+"_"+db.getStr("team_name_163"), db);
				}
				
				List<LeagueAssists163> needUpdateList = new ArrayList<LeagueAssists163>();
				List<LeagueAssists163> needInsertList = new ArrayList<LeagueAssists163>();
				for(LeagueAssists163 shooter163:lstAssists163){
					String key = shooter163.getStr("player_name_163")+"_"+shooter163.getStr("team_name_163");
					if(map.get(key)!=null){
						LeagueAssists163 shooterDB = map.get(key);
						shooterDB.set("rank", shooter163.get("rank"));
						shooterDB.set("assists_count", shooter163.get("assists_count"));
						shooterDB.set("update_time", new Date());
						needUpdateList.add(shooterDB);
					}else{
						shooter163.set("league_id", source.getStr("league_id"));
						shooter163.set("update_time", new Date());
						needInsertList.add(shooter163);
					}
				}
				if(needInsertList.size()>0){
					Db.batchSave(needInsertList, needInsertList.size());
				}
				if(needUpdateList.size()>0){
					Db.batchUpdate(needUpdateList, needUpdateList.size());
				}
			}
		}
	}

	private void translateShooter(){
		List<LeagueShooter163> lstShooter = LeagueShooter163.dao.find("select * from league_shooter_163");
		for(LeagueShooter163 shooter163:lstShooter){
			Player player = Player.dao.findFirst("select p.*, t.name team_name from player p, team t where p.team_id = t.id and p.name = ? and t.name = ?",
					shooter163.getStr("player_name_163"), shooter163.getStr("team_name_163"));
			if(player!=null){
				shooter163.set("player_id", player.get("id"));
				shooter163.set("player_name", player.get("name"));
				shooter163.set("team_id", player.get("team_id"));
				shooter163.set("team_name", player.get("team_name"));
			}
		}
		Db.batchUpdate(lstShooter, lstShooter.size());
	}
	
	private void translateAssists(){
		List<LeagueAssists163> lstAssists = LeagueAssists163.dao.find("select * from league_assists_163");
		for(LeagueAssists163 assists163:lstAssists){
			Player player = Player.dao.findFirst("select p.*, t.name team_name from player p, team t where p.team_id = t.id and p.name = ? and t.name = ?",
					assists163.get("player_name_163"), assists163.get("team_name_163"));
			if(player!=null){
				assists163.set("player_id", player.get("id"));
				assists163.set("player_name", player.get("name"));
				assists163.set("team_id", player.get("team_id"));
				assists163.set("team_name", player.get("team_name"));
			}
		}
		Db.batchUpdate(lstAssists, lstAssists.size());
	}
	
}
