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

import com.wwqk.model.League;
import com.wwqk.model.Team;
import com.wwqk.utils.FetchHtmlUtils;

public class TeamJob implements Job {
	
	String clearString = "<.*?/>";
	Pattern TEAM_URL_PATTERN = Pattern.compile("text team large-link.*?href=\"(.*?)\".*?title=\"(.*?)\"");
	Pattern VENUE_NAME_PATTERN = Pattern.compile("block_venue_info-wrapper.*?<h2>(.*?)</h2");
	Pattern VENUE_IMG_PATTERN = Pattern.compile("http://cache.images.core.optasports.com/soccer/venues/600x450/.*?\\.jpg");
	
	private static final String SITE_PROFIX = "http://cn.soccerway.com";
	private HttpClient httpClient = new DefaultHttpClient();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<League> leagues = League.dao.find("select * from league");
		
		String htmlLeague = null;
		for(League league : leagues){
			htmlLeague = FetchHtmlUtils.getHtmlContent(httpClient, league.getStr("league_url"));
			handleTeamsUrl(htmlLeague, league.getStr("id"));
		}
		
		httpClient.getConnectionManager().shutdown();
	}
	
	private void handleTeamsUrl(String htmlContent, String leagueId){
		System.err.println("handle team ing!!!");
		Map<String, String> map = new HashMap<String, String>();
		Set<String> idSet = new HashSet<String>();
		Matcher matcher = TEAM_URL_PATTERN.matcher(htmlContent);
		while(matcher.find()){
			String url = matcher.group(1);
			String id = getId(url);
			if(idSet.contains(id)){
				continue;
			}
			Team team = new Team();
			team.set("id", id);
			team.set("name", matcher.group(2));
			team.set("team_url", url);
			team.set("league_id", leagueId);
			team.save();
			map.put(id, url);
		}
		
		for(Entry<String, String> entry : map.entrySet()){
			//获取球队信息
			handleTeamDetail(entry);
			//获取球场信息
			handleTeamVenue(entry);
		}
		
	}
	
	private void handleTeamDetail(Entry<String, String> entry){
		Team team = Team.dao.findById(entry.getKey());
		String teamContent = FetchHtmlUtils.getHtmlContent(httpClient, SITE_PROFIX+entry.getValue());
		team.set("setup_time", matcherString(getPatternByName("成立于"), teamContent));
		team.set("address", matcherString(getPatternByName("地址"), teamContent).replaceAll(clearString, ""));
		team.set("country", matcherString(getPatternByName("国家"), teamContent));
		team.set("telphone", matcherString(getPatternByName("电话"), teamContent));
		team.set("fax", matcherString(getPatternByName("传真"), teamContent));
		team.set("email", matcherString(getPatternByName("电子邮件"), teamContent));
	}
	
	private Pattern getPatternByName(String patternName){
		return Pattern.compile("<dt>"+patternName+"</dt>.*?<dd>(.*?)</dd>");
	}
	
	private void handleTeamVenue(Entry<String, String> entry){
		Team team = Team.dao.findById(entry.getKey());
		String venueContent = FetchHtmlUtils.getHtmlContent(httpClient, SITE_PROFIX+entry.getValue()+"venue/");
		String venueName = matcherString(VENUE_NAME_PATTERN, venueContent);
		team.set("venue_name", venueName);
		team.set("venue_name_en", venueName);
		team.set("venue_address", matcherString(getPatternByName("地址"), venueContent));
		team.set("venue_capacity", matcherString(getPatternByName("容量"), venueContent));
		team.set("venue_img", matcherString(VENUE_IMG_PATTERN, venueContent));
		team.save();
	}
	
	private String matcherString(Pattern pattern, String source){
		Matcher matcher = pattern.matcher(source);
		if(matcher.find()){
			return matcher.group(1);
		}
		return "";
	}
	
	private String getId(String url){
		url = url.substring(0, url.length()-1);
		int lastSlashIdx = url.lastIndexOf("/");
		String id = url.substring(lastSlashIdx+1);
		return id;
	}
}
