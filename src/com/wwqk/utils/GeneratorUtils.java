package com.wwqk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.wwqk.model.Fun;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.Player;
import com.wwqk.model.Say;
import com.wwqk.model.Team;

public class GeneratorUtils{

	public static void generateSitemap(){
		try {
			FileInputStream fis = new FileInputStream(FileUtils.getWebDiskPath()+File.separator+"common"+File.separator+"sitemap.html");
			Document document = Jsoup.parse(fis, "UTF-8", "");
			//球员
			Elements playerTds = document.select("#players_td");
			if(playerTds.size()>0){
				List<Player> lstPlayer = Player.dao.find("select id, name, en_url from player");
				StringBuilder sb = new StringBuilder();
				for(Player player:lstPlayer){
					sb.append("<a href=\"http://www.yutet.com/player-"+player.getStr("en_url")+"-"+player.getStr("id")+".html\" target=\"_blank\">"+player.getStr("name")+"</a>&nbsp;");
				}
				playerTds.get(0).html(sb.toString());
				lstPlayer = null;
			}
			
			//球队
			Elements teamTds = document.select("#teams_td");
			if(teamTds.size()>0){
				List<Team> lstTeam = Team.dao.find("select * from team");
				StringBuilder sb = new StringBuilder();
				for(Team team : lstTeam){
					sb.append("<a href=\"http://www.yutet.com/team-"+team.getStr("name_en")+"-"+team.getStr("id")+".html\" target=\"_blank\">"+team.getStr("name")+"</a>&nbsp;");
				}
				teamTds.get(0).html(sb.toString());
			}
			
			//说说
			Elements sayTds = document.select("#says_td");
			if(sayTds.size()>0){
				List<Say> lstSay = Say.dao.find("select s.id, p.en_url from say s, player p where s.player_id = p.id ");
				StringBuilder sb = new StringBuilder();
				for(Say say : lstSay){
					sb.append("<a href=\"http://www.yutet.com/sdetail-"+say.getStr("en_url")+"-"+say.get("id")+".html\" target=\"_blank\">s"+say.get("id")+"</a>&nbsp;");
				}
				sayTds.get(0).html(sb.toString());
				lstSay = null;
			}
			
			//趣点
			Elements funTds = document.select("#funs_td");
			if(funTds.size()>0){
				List<Fun> lstFun = Fun.dao.find("select id, create_time from fun where type = 1");
				StringBuilder sb = new StringBuilder();
				for(Fun fun : lstFun){
					sb.append("<a href=\"http://www.yutet.com/fdetail-"+DateTimeUtils.formatDate(fun.getDate("create_time"))+"-"+fun.get("id")+".html\" target=\"_blank\">f"+fun.get("id")+"</a>&nbsp;");
				}
				funTds.get(0).html(sb.toString());
			}
			
			//比赛
			Elements matchTds = document.select("#matches_td");
			if(matchTds.size()>0){
				List<LeagueMatchHistory> lstMatch = LeagueMatchHistory.dao.find("select home_team_id,home_team_name, away_team_id,away_team_name, match_date, home_team_en_name, away_team_en_name from league_match_history");
				StringBuilder sb = new StringBuilder();
				for(LeagueMatchHistory match : lstMatch){
					sb.append("<a href=\"http://www.yutet.com/match-"+match.getStr("home_team_en_name")+"-vs-"+match.getStr("away_team_en_name")+"_"+DateTimeUtils.formatDate(match.getDate("match_date"))+"-"+match.getStr("home_team_id")+"vs"+match.getStr("away_team_id")+".html\" target=\"_blank\">"+match.getStr("home_team_name")+"vs"+match.getStr("away_team_name")+"</a>&nbsp;");
				}
				matchTds.get(0).html(sb.toString());
				lstMatch = null;
			}
			
			String filePathStr = FileUtils.getWebDiskPath()+File.separator+"sitemap.html";
			FileUtils.deleteFile(filePathStr);
			FileUtils.writeFile(document.html(), filePathStr);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}