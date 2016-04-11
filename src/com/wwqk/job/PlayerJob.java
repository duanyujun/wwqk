package com.wwqk.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.Career;
import com.wwqk.model.Injury;
import com.wwqk.model.League;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.model.Transfer;
import com.wwqk.model.Trophy;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.FetchHtmlUtils;
import com.wwqk.utils.StringUtils;

public class PlayerJob implements Job {
	
	private HttpClient httpClient = new DefaultHttpClient();
	String clearString = "<.*?/>";
	private static final Pattern PLAYER_URL_PATTERN = Pattern.compile("class=\"flag_16 right_16.*?<a href=\"(.*?)\".*?>(.*?)</a>");
	private static final Pattern CAREER_PATTERN = Pattern.compile("class=\"season\">.*?>(.*?)</a>.*?href=\"(.*?)\".*?title=\"(.*?)\".*?<span class=\"(.*?)\".*?href=\"(.*?)\".*?title=\"(.*?)\".*?game-minutes available\">(.*?)</td>.*?appearances available\">(.*?)</td>.*?lineups available\">(.*?)</td>.*?subs-in available\">(.*?)</td>.*?subs-out available\">(.*?)</td>.*?subs-on-bench available\">(.*?)</td>.*?goals available\">(.*?)</td>.*?yellow-cards available\">(.*?)</td>.*?2nd-yellow-cards available\">(.*?)</td>.*?red-cards available\">(.*?)</td>");
	private static final Pattern TROPHY_TABLE_PATTERN = Pattern.compile("trophies-table\">(.*?)</table>");
	private static final Pattern TROPHY_TITLE_PATTERN = Pattern.compile("<th.*?>(.*?)</th>");
	private static final Pattern TROPHY_COMPETITION_PATTERN = Pattern.compile("class=\"competition\">.*?>(.*?)</td>.*?label\">(.*?)</td>.*?total\">(.*?)</td>.*?seasons\">.*?</td>");
	private static final Pattern TROPHY_SEASON_PATTERN = Pattern.compile("<a.*?>(.*/)</a>");
	private static final Pattern INJURY_PATTERN = Pattern.compile("icon injury.*?<td>(.*?)</td>.*?<span.*?>(.*?)</span>.*?<span.*?>(.*?)</span>");
	private static final Pattern TRANSFER_TABLE_PATTERN = Pattern.compile("transfers-container.*?</table>");
	private static final Pattern TRANSFER_PATTERN = Pattern.compile("<span.*?>(.*?)</span>.*?<a.*?>(.*?)</a>.*?<a.*?>(.*?)</a>.*?<td.*?>(.*?)</td>");
	private static final String SITE_PROFIX = "http://cn.soccerway.com";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<Team> lstTeam = Team.dao.find("select * from team");
		String htmlTeam = null;
		for(Team team : lstTeam){
			htmlTeam = FetchHtmlUtils.getHtmlContent(httpClient, team.getStr("team_url"));
			handlePlayerUrl(htmlTeam, team.getStr("id"));
		}
	}

	private void handlePlayerUrl(String htmlTeam, String teamId) {
		System.err.println("handle player ing!!!");
		Map<String, String> map = new HashMap<String, String>();
		Set<String> idSet = new HashSet<String>();
		Matcher matcher = PLAYER_URL_PATTERN.matcher(htmlTeam);
		while(matcher.find()){
			String url = matcher.group(1);
			String id = CommonUtils.getId(url);
			if(idSet.contains(id)){
				continue;
			}else{
				idSet.add(id);
			}
			
			Player playerDB = Player.dao.findById(id);
			if(playerDB==null){
				Player player = new Player();
				player.set("id", id);
				player.set("name", matcher.group(2));
				player.set("player_url", url);
				player.set("team_id", teamId);
				player.save();
			}else{
				playerDB.set("name", matcher.group(2));
				playerDB.set("player_url", url);
				playerDB.set("team_id", teamId);
				playerDB.update();
			}
			
			map.put(id, url);
		}
		
		for(Entry<String, String> entry : map.entrySet()){
			handlePlayerDetail(entry);
		}
	}

	private void handlePlayerDetail(Entry<String, String> entry){
		Player player = Player.dao.findById(entry.getKey());
		System.err.println("handle team： "+player.getStr("name")+" ing!!!");
		String playerContent = FetchHtmlUtils.getHtmlContent(httpClient, SITE_PROFIX+entry.getValue());
		player.set("first_name", CommonUtils.matcherString(CommonUtils.getPatternByName("名字"), playerContent));
		player.set("last_name", CommonUtils.matcherString(CommonUtils.getPatternByName("姓氏"), playerContent));
		player.set("nationality", CommonUtils.matcherString(CommonUtils.getPatternByName("国籍"), playerContent));
		player.set("birthday", CommonUtils.getCNDate(CommonUtils.matcherString(CommonUtils.getPatternByName("出生日期"), playerContent)));
		player.set("age", CommonUtils.matcherString(CommonUtils.getPatternByName("年龄"), playerContent));
		player.set("birth_country", CommonUtils.matcherString(CommonUtils.getPatternByName("出生国家"), playerContent));
		player.set("birth_place", CommonUtils.matcherString(CommonUtils.getPatternByName("出生地"), playerContent));
		player.set("position", CommonUtils.matcherString(CommonUtils.getPatternByName("位置"), playerContent));
		player.set("height", CommonUtils.matcherString(CommonUtils.getPatternByName("高度"), playerContent));
		player.set("weight", CommonUtils.matcherString(CommonUtils.getPatternByName("体重"), playerContent));
		player.set("foot", CommonUtils.matcherString(CommonUtils.getPatternByName("脚"), playerContent));
		player.save();
		
		//职业生涯
		handleCareer(playerContent, entry.getKey());
		//所获荣誉
		handleTrophy(playerContent, entry.getKey());
		//受伤情况
		handleInjury(playerContent, entry.getKey());
		//转会情况
		handleTransfer(playerContent, entry.getKey());
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
			career.set("play_count", matcher.group(8));
			career.set("first_team", matcher.group(9));
			career.set("substitute", matcher.group(10));
			career.set("substituted", matcher.group(11));
			career.set("substitute_count", matcher.group(12));
			career.set("goal", matcher.group(13));
			//career.set("assist_goal", matcher.group(1));
			career.set("yellow_count", matcher.group(14));
			career.set("double_yellow_count", matcher.group(15));
			career.set("red_count", matcher.group(16));
			career.set("player_id", playerId);
			
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
			injury.set("type", CommonUtils.getCNInjury(matcher.group(1)));
			injury.set("start_time", CommonUtils.getDateByString(matcher.group(2)));
			injury.set("estimate_time", CommonUtils.getDateByString(matcher.group(3)));
			injury.set("end_time", CommonUtils.getDateByString(matcher.group(4)));
			injury.set("player_id", playerId);
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
			String transferHTML = matcher.group(1);
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






