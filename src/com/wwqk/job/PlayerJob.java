package com.wwqk.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.wwqk.model.Career;
import com.wwqk.model.Injury;
import com.wwqk.model.League;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.model.Transfer;
import com.wwqk.model.Trophy;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.StringUtils;

public class PlayerJob implements Job {
	
	String clearString = "<.*?/>";
	private static final Pattern CAREER_PATTERN = Pattern.compile("class=\"season\">.*?>(.*?)</a>.*?href=\"(.*?)\".*?title=\"(.*?)\".*?<span class=\"(.*?)\".*?href=\"(.*?)\".*?title=\"(.*?)\".*?game-minutes available\">(.*?)</td>.*?appearances available\">(.*?)</td>.*?lineups available\">(.*?)</td>.*?subs-in available\">(.*?)</td>.*?subs-out available\">(.*?)</td>.*?subs-on-bench available\">(.*?)</td>.*?goals available\">(.*?)</td>.*?yellow-cards available\">(.*?)</td>.*?2nd-yellow-cards available\">(.*?)</td>.*?red-cards available\">(.*?)</td>");
	private static final Pattern TROPHY_TABLE_PATTERN = Pattern.compile("trophies-table\">(.*?)</table>");
	private static final Pattern TROPHY_TITLE_PATTERN = Pattern.compile("<th.*?>(.*?)</th>");
	private static final Pattern TROPHY_COMPETITION_PATTERN = Pattern.compile("class=\"competition\">.*?>(.*?)</td>.*?label\">(.*?)</td>.*?total\">(.*?)</td>.*?seasons\">(.*?)</td>");
	private static final Pattern TROPHY_SEASON_PATTERN = Pattern.compile("<a.*?>(.*/)</a>");
	private static final Pattern INJURY_PATTERN = Pattern.compile("icon injury.*?<td>(.*?)</td>.*?<span.*?>(.*?)</span>.*?<span.*?>(.*?)</span>");
	private static final Pattern TRANSFER_TABLE_PATTERN = Pattern.compile("transfers-container.*?</table>");
	private static final Pattern TRANSFER_PATTERN = Pattern.compile("<span.*?>(.*?)</span>.*?<a.*?>(.*?)</a>.*?<a.*?>(.*?)</a>.*?<td.*?>(.*?)</td>");
	private static final String SITE_PROFIX = "http://cn.soccerway.com";
	private static final String COUCH_STRING = "教练";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<Team> lstTeam = Team.dao.find("select * from team order by id+0 asc ");
		System.err.println("handle player start!!!");
		try {
			for(Team team : lstTeam){
				handlePlayerUrl(team.getStr("team_url"), team.getStr("id"));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		System.err.println("handle player end!!!");
	}

	private void handlePlayerUrl(String teamUrl, String teamId) throws IOException {
		
		List<Player> lstNeedInsert = new ArrayList<Player>();
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		Map<String, String> map = new HashMap<String, String>();
		Set<String> idSet = new HashSet<String>();
		Document document = Jsoup.connect(teamUrl).get();
		Elements elements = document.select(".squad-container");
		if(elements.size()>0){
			Elements theadElements = elements.get(0).select("thead");
			Elements tbodyElements = elements.get(0).select("tbody");
			if(theadElements.size()==tbodyElements.size() && theadElements.size()>0){
				for(int i=0; i<theadElements.size(); i++){
					if(COUCH_STRING.equals(StringUtils.trim(theadElements.get(i).child(0).child(0).text()))){
						//TODO add couch
					}else{
						Elements aElements = tbodyElements.get(i).select("a");
						for(Element element : aElements){
							String url = element.attr("href");
							String id = CommonUtils.getId(url);
							String imgSmall = null;
							String playerName = null;
							if(idSet.contains(id)){
								//获取姓名
								playerName = element.text();
								continue;
							}else{
								//获取小头像
								imgSmall = element.child(0).attr("src");
								idSet.add(id);
							}
							
							Player playerDB = Player.dao.findById(id);
							if(playerDB==null){
								Player player = new Player();
								player.set("id", id);
								player.set("name", playerName);
								//TODO add column
								player.set("img_small", imgSmall);
								player.set("player_url", SITE_PROFIX+url);
								player.set("team_id", teamId);
								player.set("update_time", new Date());
								lstNeedInsert.add(player);
							}else{
								playerDB.set("name", playerName);
								//TODO add column
								playerDB.set("img_small", imgSmall);
								playerDB.set("player_url", SITE_PROFIX+url);
								playerDB.set("team_id", teamId);
								playerDB.set("update_time", new Date());
								playerDB.update();
								lstNeedUpdate.add(playerDB);
							}
							
							map.put(id, SITE_PROFIX+url);
						}
					}
					
					//更新球员球队id信息
					updatePlayerTeamInfo(lstNeedInsert, lstNeedUpdate, teamId);
				}
			}
		}
		
		for(Entry<String, String> entry : map.entrySet()){
			handlePlayerDetail(entry);
		}
		
	}
	
	@Before(Tx.class)
	private void updatePlayerTeamInfo(List<Player> lstNeedInsert, List<Player> lstNeedUpdate, String teamId){
		
		if(lstNeedInsert.size()!=0 || lstNeedUpdate.size()!=0){
			//解除关联
			Db.update("update player set team_id = '' where team_id = ? ", teamId);
			Db.batchSave(lstNeedInsert, lstNeedInsert.size());
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
	private void handlePlayerDetail(Entry<String, String> entry) throws IOException{
		Player player = Player.dao.findById(entry.getKey());
		System.err.println("handle player： "+player.getStr("name")+" ing!!!");
		Document document = Jsoup.connect(entry.getValue()).get();
		Elements elements = document.select(".yui-gc");
		if(elements.size()>0){
			String imgBig = elements.get(0).child(1).child(0).attr("src");
			Element infoElement = elements.get(0).child(0).child(0).child(0);
			player.set("first_name", infoElement.child(1).text());
			player.set("last_name", infoElement.child(3).text());
			player.set("nationality", infoElement.child(5).text());
			player.set("birthday", CommonUtils.getCNDate(infoElement.child(7).text()));
			player.set("age", infoElement.child(9).text());
			player.set("birth_country", infoElement.child(11).text());
			player.set("birth_place", infoElement.child(13).text());
			player.set("position", infoElement.child(15).text());
			player.set("height", infoElement.child(17).text());
			player.set("weight", infoElement.child(19).text());
			player.set("foot", infoElement.child(21).text());
			//TODO add column
			player.set("img_big", imgBig);
			player.set("update_time", new Date());
			player.update();
		}
		
		//职业生涯
		handleCareer(document.html(), entry.getKey());
		//所获荣誉
		handleTrophy(document.html(), entry.getKey());
		//受伤情况
		handleInjury(document.html(), entry.getKey());
		//转会情况
		handleTransfer(document.html(), entry.getKey());
	}
	
	private void handleCareer(String playerContent, String playerId){
		Matcher matcher = CAREER_PATTERN.matcher(playerContent);
		List<Career> lstCareer = new ArrayList<Career>();
		while(matcher.find()){
			Career career = new Career();
			career.set("season", matcher.group(1));
			career.set("team_id", CommonUtils.getId(matcher.group(2)));
			career.set("team_name", matcher.group(3));
			career.set("league_img_css", matcher.group(4));
			career.set("league_id", getLeagueId(matcher.group(5)));
			career.set("league_name", matcher.group(6));
			career.set("play_time", matcher.group(7));
			career.set("play_count", CommonUtils.getDefaultZero(matcher.group(8)));
			career.set("first_team", CommonUtils.getDefaultZero(matcher.group(9)));
			career.set("substitute", CommonUtils.getDefaultZero(matcher.group(10)));
			career.set("substituted", CommonUtils.getDefaultZero(matcher.group(11)));
			career.set("substitute_count", CommonUtils.getDefaultZero(matcher.group(12)));
			career.set("goal", CommonUtils.getDefaultZero(matcher.group(13)));
			//career.set("assist_goal", matcher.group(1));
			career.set("yellow_count", CommonUtils.getDefaultZero(matcher.group(14)));
			career.set("double_yellow_count", CommonUtils.getDefaultZero(matcher.group(15)));
			career.set("red_count", CommonUtils.getDefaultZero(matcher.group(16)));
			career.set("player_id", playerId);
			career.set("update_time", new Date());
			
			lstCareer.add(career);
		}
		if(lstCareer.size()>0){
			Db.update("delete from career where player_id = ?", playerId);
			Db.batchSave(lstCareer, lstCareer.size());
		}
	}
	
	private void handleTrophy(String playerContent, String playerId){
		Matcher matcher = TROPHY_TABLE_PATTERN.matcher(playerContent);
		if(matcher.find()){
			List<Trophy> lstTrophy = new ArrayList<Trophy>();
			String trophyContent = matcher.group(1);
			String[] groupArray = trophyContent.split(" <tr class=\"group-head\">");
			for(String group : groupArray){
				Matcher groupMatcher = TROPHY_TITLE_PATTERN.matcher(group);
				if(groupMatcher.find()){
					String groupTitle = groupMatcher.group(1);
					Matcher cmpMatcher = TROPHY_COMPETITION_PATTERN.matcher(group);
					while(cmpMatcher.find()){
						String cmpTitle = cmpMatcher.group(1);
						String trophyName = cmpMatcher.group(2);
						String trophyCount = cmpMatcher.group(3);
						Matcher seasonMatcher = TROPHY_SEASON_PATTERN.matcher(cmpMatcher.group(4));
						StringBuilder sb = new StringBuilder();
						while(seasonMatcher.find()){
							sb.append(seasonMatcher.group(1)).append(",");
						}
						if(sb.length()!=0){
							sb.deleteCharAt(sb.length()-1);
						}
						Trophy trophy = new Trophy();
						trophy.set("trophy_area", groupTitle);
						trophy.set("league_name", cmpTitle);
						trophy.set("trophy_name", trophyName);
						trophy.set("times", trophyCount);
						trophy.set("season", sb.toString());
						trophy.set("player_id", playerId);
						trophy.set("update_time", new Date());
						
						lstTrophy.add(trophy);
					}
				}
			}
			if(lstTrophy.size()>0){
				Db.update("delete from trophy where player_id = ?", playerId);
				Db.batchSave(lstTrophy, lstTrophy.size());
			}
		}
	}
	
	private void handleInjury(String playerContent, String playerId){
		List<Injury> lstInjury = new ArrayList<Injury>();
		Matcher matcher = INJURY_PATTERN.matcher(playerContent);
		while(matcher.find()){
			Injury injury = new Injury();
			injury.set("type", matcher.group(1));
			injury.set("start_time", CommonUtils.getDateByString(matcher.group(2)));
			injury.set("end_time", CommonUtils.getDateByString(matcher.group(3)));
			injury.set("player_id", playerId);
			injury.set("update_time", new Date());
			lstInjury.add(injury);
		}
		if(lstInjury.size()>0){
			Db.update("delete from injury where player_id = ?", playerId);
			Db.batchSave(lstInjury, lstInjury.size());
		}
	}
	
	private void handleTransfer(String playerContent, String playerId){
		List<Transfer> lstTransfer = new ArrayList<Transfer>();
		Matcher matcher = TRANSFER_TABLE_PATTERN.matcher(playerContent);
		if(matcher.find()){
			String transferHTML = matcher.group();
			Matcher transferMatcher = TRANSFER_PATTERN.matcher(transferHTML);
			while(transferMatcher.find()){
				String timeStr = transferMatcher.group(1);
				String fromStr = transferMatcher.group(2);
				String toStr = transferMatcher.group(3);
				String valueStr = transferMatcher.group(4);
				Transfer transfer = new Transfer();
				transfer.set("date", CommonUtils.getDateByString(timeStr));
				transfer.set("from_team", fromStr);
				transfer.set("to_team", toStr);
				transfer.set("value", CommonUtils.getCNValue(valueStr));
				transfer.set("player_id", playerId);
				transfer.set("update_time", new Date());
				lstTransfer.add(transfer);
			}
		}
		if(lstTransfer.size()>0){
			Db.update("delete from transfer where player_id = ?", playerId);
			Db.batchSave(lstTransfer, lstTransfer.size());
		}
	}
	
	private String getLeagueId(String leagueURL){
		if(StringUtils.isBlank(leagueURL)){
			return null;
		}
		String id = null;
		List<League> lstLeague = League.dao.find("select * from league");
		for(League league : lstLeague){
			if(leagueURL.contains(league.getStr("name_en"))){
				id = league.getStr("id");
				break;
			}
		}
		
		return id;
	}
	
}






