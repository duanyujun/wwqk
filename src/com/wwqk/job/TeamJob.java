package com.wwqk.job;

import java.util.ArrayList;
import java.util.List;
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
	
	//private Logger logger = LoggerFactory.getLogger(getClass());
	Pattern TEAM_URL_PATTERN = Pattern.compile("text team large-link.*?href=\"(.*?)\".*?title=\"(.*?)\"");
	private static final String SITE_PROFIX = "http://cn.soccerway.com";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<League> leagues = League.dao.find("select * from league");
		HttpClient httpClient = new DefaultHttpClient();
		String htmlLeague = null;
		for(League league : leagues){
			htmlLeague = FetchHtmlUtils.getHtmlContent(httpClient, league.getStr("league_url"));
			getTeamsUrl(htmlLeague);
		}
		
		httpClient.getConnectionManager().shutdown();
	}
	
	private List<Team> getTeamsUrl(String htmlContent){
		System.err.println("handle team ing!!!");
		List<Team> lstTeamUrl = new ArrayList<Team>();
		Matcher matcher = TEAM_URL_PATTERN.matcher(htmlContent);
		while(matcher.find()){
			String url = matcher.group(1);
			Team team = new Team();
			team.set("name", matcher.group(2));
			team.set("team_url", url);
			team.set("id", getId(url));
			team.save();
		}
		return lstTeamUrl;
	}
	
	private String getId(String url){
		url = url.substring(0, url.length()-1);
		int lastSlashIdx = url.lastIndexOf("/");
		String id = url.substring(lastSlashIdx);
		return id;
	}
}
