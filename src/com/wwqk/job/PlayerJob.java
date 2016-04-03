package com.wwqk.job;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.FetchHtmlUtils;
import com.wwqk.utils.StringUtils;

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
			String id = getId(url);
			if(idSet.contains(id)){
				continue;
			}else{
				idSet.add(id);
			}
			Player player = new Player();
			player.set("id", id);
			player.set("name", matcher.group(2));
			player.set("player_url", url);
			player.set("team_id", teamId);
			player.save();
			map.put(id, url);
		}
		
		for(Entry<String, String> entry : map.entrySet()){
			handlePlayerDetail(entry);
		}
	}

	private String getId(String url){
		url = url.substring(0, url.length()-1);
		int lastSlashIdx = url.lastIndexOf("/");
		String id = url.substring(lastSlashIdx+1);
		return id;
	}
	
	private void handlePlayerDetail(Entry<String, String> entry){
		Player player = Player.dao.findById(entry.getKey());
		System.err.println("handle team： "+player.getStr("name")+" ing!!!");
		String playerContent = FetchHtmlUtils.getHtmlContent(httpClient, SITE_PROFIX+entry.getValue());
		player.set("first_name", matcherString(getPatternByName("名字"), playerContent));
		player.set("last_name", matcherString(getPatternByName("姓氏"), playerContent));
		player.set("nationality", matcherString(getPatternByName("国籍"), playerContent));
		player.set("birthday", getDate(matcherString(getPatternByName("出生日期"), playerContent)));
		player.set("age", matcherString(getPatternByName("年龄"), playerContent));
		player.set("birth_country", matcherString(getPatternByName("出生国家"), playerContent));
		player.set("birth_place", matcherString(getPatternByName("出生地"), playerContent));
		player.set("position", matcherString(getPatternByName("位置"), playerContent));
		player.set("height", matcherString(getPatternByName("高度"), playerContent));
		player.set("weight", matcherString(getPatternByName("体重"), playerContent));
		player.set("foot", matcherString(getPatternByName("脚"), playerContent));
		player.save();
		
	}
	
	private String matcherString(Pattern pattern, String source){
		Matcher matcher = pattern.matcher(source);
		if(matcher.find()){
			return matcher.group(1);
		}
		return "";
	}
	
	private Pattern getPatternByName(String patternName){
		return Pattern.compile("<dt>"+patternName+"</dt>.*?<dd>(.*?)</dd>");
	}
	
	/**
	 * 21 二月 1991
	 * @param dateStr
	 * @return
	 * @throws ParseException 
	 */
	private Date getDate(String dateStr){
		if(StringUtils.isBlank(dateStr)){
			return null;
		}
		for(Entry<String, String> entry : monthMap.entrySet()){
			dateStr = dateStr.replace(entry.getKey(), entry.getValue());
		}
		
		String[] patterns ={"dd MM yyyy"};
		Date date = null;
		try {
			date = DateTimeUtils.parseDate(dateStr, patterns);
		} catch (ParseException e) {
			
		}
		
		return date;
	}
	
	private static final Map<String, String> monthMap = new HashMap<String, String>();
	static{
		monthMap.put(" 一月", " 01");
		monthMap.put(" 二月", " 02");
		monthMap.put(" 三月", " 03");
		monthMap.put(" 四月", " 04");
		monthMap.put(" 五月", " 05");
		monthMap.put(" 六月", " 06");
		monthMap.put(" 七月", " 07");
		monthMap.put(" 八月", " 08");
		monthMap.put(" 九月", " 09");
		monthMap.put(" 十月", " 10");
		monthMap.put(" 十一月", " 11");
		monthMap.put(" 十二月", " 12");
	}
	
}
