package com.wwqk.job;

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

import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.FetchHtmlUtils;

public class PlayerJob implements Job {
	
	private HttpClient httpClient = new DefaultHttpClient();
	String clearString = "<.*?/>";
	Pattern PLAYER_URL_PATTERN = Pattern.compile("class=\"flag_16 right_16.*?<a href=\"(.*?)\".*?>(.*?)</a>");
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
		
		//所获荣誉
		
		//受伤情况
		
		//转会情况
		
	}
	
	private void handleCareer(String playerContent, String playerId){
		
	}
	
}






