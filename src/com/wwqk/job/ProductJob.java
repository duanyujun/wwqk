package com.wwqk.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
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
import com.wwqk.model.League;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeaguePosition;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.FetchHtmlUtils;
import com.wwqk.utils.StringUtils;

/**
 * 
 * @author Administrator
 * TODO 1、同步比赛
 */
public class ProductJob implements Job {
	
	String clearString = "<.*?/>";
	String tagString = "<.*?>";
	private static final String SITE_PROFIX = "http://cn.soccerway.com";
	private HttpClient client;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		client = new DefaultHttpClient();  
		System.err.println("handle team start!!!");
		List<League> leagues = League.dao.find("select * from league");
		try {
			for(League league : leagues){
				handleTeamsUrl(league.getStr("league_url"), league.getStr("id"));
			}
		} catch (Exception e) {
			System.err.println("^^^^^^^"+e.getMessage()+" +++++"+e);
		}
		System.err.println("handle team end!!!");
		client.getConnectionManager().shutdown();
	}
	
	private void handleTeamsUrl(String leagueUrl, String leagueId) throws IOException{
		String html = FetchHtmlUtils.getHtmlContent(client, leagueUrl);
		Document document = Jsoup.parse(html);
		//Document document = Jsoup.connect(leagueUrl).get(); 
		Elements bodyElement = document.select(".detailed-table");
		String roundId = null;
		if(bodyElement.size()>0){
			roundId = bodyElement.get(0).attr("data-round_id");
		}
		//排名
		handleLeaguePosition(document, leagueId, roundId);
		//比赛
		handleMatch(document, leagueId, roundId);
		
	}
	
	@Before(Tx.class)
	private void handleMatch(Document document, String leagueId, String roundId){
		List<LeagueMatch> lstMatch = new ArrayList<LeagueMatch>();
		Elements elements = document.select(".no-date-repetition");
		for(Element element : elements){
			String matchWeekday = element.child(0).text();
			String matchDate = element.child(1).text();
			String homeTeamId = CommonUtils.getId(element.child(2).child(0).attr("href"));
			String homeTeamName = element.child(2).child(0).attr("title");
			//TODO add column
			String homeTeamUrl = element.child(2).child(0).attr("href");
			String matchURL = element.child(3).child(0).attr("href");
			String matchPoints = StringUtils.trim(element.child(3).text());
			String awayTeamId = CommonUtils.getId(element.child(4).child(0).attr("href"));
			String awayTeamName = element.child(4).child(0).attr("title");
			//TODO add column
			String awayTeamUrl = element.child(4).child(0).attr("href");
			LeagueMatch match = new LeagueMatch();
			match.set("match_date", CommonUtils.getDateByString(matchDate));
			match.set("match_weekday", matchWeekday);
			match.set("home_team_id", homeTeamId);
			match.set("home_team_name", homeTeamName);
			match.set("home_team_url", homeTeamUrl);
			match.set("away_team_id", awayTeamId);
			match.set("away_team_name", awayTeamName);
			match.set("away_team_url", awayTeamUrl);
			match.set("result", filterMatchPoints(matchPoints));
			match.set("league_id", leagueId);
			match.set("round_id", roundId);
			match.set("match_url", matchURL);
			match.set("update_time", new Date());
			
			lstMatch.add(match);
		}
		
		if(lstMatch.size()>0){
			Db.update("delete from league_match where league_id = ? ", leagueId);
			Db.batchSave(lstMatch, lstMatch.size());
		}
	}
	
	@Before(Tx.class)
	private void handleLeaguePosition(Document document, String leagueId, String roundId){
		List<LeaguePosition> lstPositions = new ArrayList<LeaguePosition>();
		Elements bodyElement = document.select(".detailed-table");
		Elements teamLinks = bodyElement.get(0).select(".team_rank");
		for(Element element : teamLinks){
			String teamId = element.attr("data-team_id");
			String rank = element.child(0).html();
			String teamName = element.child(2).text();
			//TODO add column
			String teamUrl = SITE_PROFIX + element.child(2).child(0).attr("href");
			String roundCount = element.child(3).text();
			String winCount = element.child(4).text();
			String evenCount = element.child(5).text();
			String loseCount = element.child(6).text();
			String winGoalCount = element.child(7).text();
			String loseGoalCount = element.child(8).text();
			String goalCount = element.child(9).text();
			String points = element.child(10).text();
			
			LeaguePosition leaguePosition = new LeaguePosition();
			leaguePosition.set("league_id", leagueId);
			leaguePosition.set("round_id", roundId);
			leaguePosition.set("rank", rank);
			leaguePosition.set("team_id", teamId);
			leaguePosition.set("team_name", teamName);
			leaguePosition.set("team_url", teamUrl);
			leaguePosition.set("round_count", roundCount);
			leaguePosition.set("win_count", winCount);
			leaguePosition.set("even_count", evenCount);
			leaguePosition.set("lose_count", loseCount);
			leaguePosition.set("win_goal_count", winGoalCount);
			leaguePosition.set("lose_goal_count", loseGoalCount);
			leaguePosition.set("goal_count", goalCount);
			leaguePosition.set("points", points);
			leaguePosition.set("update_time", new Date());
			
			lstPositions.add(leaguePosition);
		}
		if(lstPositions.size()>0){
			Db.update("delete from league_position where league_id = ?", leagueId);
			Db.batchSave(lstPositions, lstPositions.size());
		}
	}
	
	private final Pattern TimePattern = Pattern.compile("<span.*?>(.*?)</span>");
	
	/**
	 * 球赛未开打
	 * @return String
	 */
	private String filterMatchPoints(String matchPoints){
		if(matchPoints.contains(" : ")){
			Matcher matcher = TimePattern.matcher(matchPoints);
			if(matcher.find()){
				matchPoints = matcher.group(1);
			}
		}
		return matchPoints;
	}

}


