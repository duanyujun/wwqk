package com.wwqk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.Fun;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.Say;
import com.wwqk.model.Team;

public class GeneratorUtils{

	public static void generateSitemap(){
		try {
			FileInputStream fis = new FileInputStream(FileUtils.getWebDiskPath()+File.separator+"common"+File.separator+"sitemap.html");
			Document document = Jsoup.parse(fis, "UTF-8", "");
//			//球员
//			Elements playerTds = document.select("#players_td");
//			if(playerTds.size()>0){
//				List<Player> lstPlayer = Player.dao.find("select id, name, en_url from player");
//				StringBuilder sb = new StringBuilder();
//				for(Player player:lstPlayer){
//					sb.append("<a href=\"http://www.yutet.com/player-"+player.getStr("en_url")+"-"+player.getStr("id")+".html\" target=\"_blank\">"+player.getStr("name")+"</a>&nbsp;");
//				}
//				playerTds.get(0).html(sb.toString());
//				lstPlayer = null;
//			}
			
			Date nowDate = DateTimeUtils.addDays(new Date(), -10);
			
			//球队
			Elements teamTds = document.select("#teams_td");
			if(teamTds.size()>0){
				List<Team> lstTeam = Team.dao.find("select * from team ");
				StringBuilder sb = new StringBuilder();
				for(Team team : lstTeam){
					sb.append("<a href=\"http://www.yutet.com/team-"+team.getStr("name_en")+"-"+team.getStr("id")+".html\" target=\"_blank\">"+team.getStr("name")+"</a>&nbsp;");
				}
				teamTds.get(0).html(sb.toString());
			}
			
			//说说
			Elements sayTds = document.select("#says_td");
			if(sayTds.size()>0){
				List<Say> lstSay = Say.dao.find("select s.id, p.en_url from say s, player p where s.player_id = p.id and s.create_time > ?", nowDate);
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
				List<Fun> lstFun = Fun.dao.find("select id, title, create_time from fun where type = 1 ");
				StringBuilder sb = new StringBuilder();
				for(Fun fun : lstFun){
					sb.append("<a href=\"http://www.yutet.com/fdetail-"+DateTimeUtils.formatDate(fun.getDate("create_time"))+"-"+fun.get("id")+".html\" target=\"_blank\">"+fun.get("title")+"</a>&nbsp;");
				}
				funTds.get(0).html(sb.toString());
			}
			
			//比赛
			Elements matchTds = document.select("#matches_td");
			if(matchTds.size()>0){
				List<LeagueMatchHistory> lstMatch = LeagueMatchHistory.dao.find("select year_show,home_team_id,home_team_name, away_team_id,away_team_name, match_date, home_team_en_name, away_team_en_name from league_match_history where match_date > ?", nowDate);
				StringBuilder sb = new StringBuilder();
				for(LeagueMatchHistory match : lstMatch){
					sb.append("<a href=\"http://www.yutet.com/match-"+match.getStr("home_team_en_name")+"-vs-"+match.getStr("away_team_en_name")+"_"+match.getStr("year_show")+"-"+match.getStr("home_team_id")+"vs"+match.getStr("away_team_id")+".html\" target=\"_blank\">"+match.getStr("home_team_name")+"vs"+match.getStr("away_team_name")+"</a>&nbsp;");
				}
				matchTds.get(0).html(sb.toString());
				lstMatch = null;
			}
			
			//直播
			Elements liveTds = document.select("#live_td");
			if(matchTds.size()>0){
				List<AllLiveMatch> lstMatch = AllLiveMatch.dao.find("select * from all_live_match where match_datetime > ?", nowDate);
				StringBuilder sb = new StringBuilder();
				for(AllLiveMatch match : lstMatch){
					if(StringUtils.isNotBlank(match.getStr("league_id"))){
						sb.append("<a href=\"http://www.yutet.com/match-"+match.getStr("home_team_enname")+"-vs-"+match.getStr("away_team_enname")+"_"+match.getStr("year_show")+"-"+match.getStr("home_team_id")+"vs"+match.getStr("away_team_id")+".html\" target=\"_blank\">"+match.getStr("home_team_name")+"vs"+match.getStr("away_team_name")+"</a>&nbsp;");
					}else{
						sb.append("<a href=\"http://www.yutet.com/live-"+DateTimeUtils.formatDate(match.getDate("match_datetime"))+"-"+match.get("id")+".html\" target=\"_blank\">"+match.getStr("home_team_name")+"vs"+match.getStr("away_team_name")+"</a>&nbsp;");
						
					}
				}
				liveTds.get(0).html(sb.toString());
				lstMatch = null;
			}
			
			//球队列表
			Elements teamMatchTds = document.select("#team_match_td");
			if(teamMatchTds.size()>0){
				List<Team> lstTeam = Team.dao.find("select id, name, name_en from team");
				StringBuilder sb = new StringBuilder();
				for(Team team : lstTeam){
					sb.append("<a target=\"_blank\" href=\"http://www.yutet.com/history-"+team.getStr("name_en")+"-"+team.getStr("id")+".html\">"+team.getStr("name")+"的比赛</a>&nbsp;");
				}
				teamMatchTds.get(0).html(sb.toString());
			}
			
			
			String filePathStr = FileUtils.getWebDiskPath()+File.separator+"sitemap.html";
			FileUtils.deleteFile(filePathStr);
			FileUtils.writeFile(document.html(), filePathStr);
			
			//生成site.txt
			Elements elements = document.select("a");
			StringBuilder sb = new StringBuilder();
			for(Element element : elements){
				sb.append(element.attr("href")).append("\n");
			}
			String txtPath = FileUtils.getWebDiskPath()+File.separator+"site.txt";
			FileUtils.deleteFile(txtPath);
			FileUtils.writeFile(sb.toString(), txtPath);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
