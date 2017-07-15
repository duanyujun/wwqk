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
import com.wwqk.constants.FlagMask;
import com.wwqk.model.League;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.LeagueShooter;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.FetchHtmlUtils;
import com.wwqk.utils.StringUtils;

public class TeamJob implements Job {
	
	String clearString = "<.*?/>";
	String tagString = "<.*?>";
	private static final String SITE_PROFIX = "http://cn.soccerway.com";
	private HttpClient client;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		client = new DefaultHttpClient();  
//	    ClientConnectionManager mgr = client.getConnectionManager();  
//	    HttpParams params = client.getParams();  
//	    params.setParameter("http.socket.timeout",6000);
//	    params.setParameter("http.connection.timeout",6000);
//	    params.setParameter("http.connection-manager.timeout",100000000L);
//	   
//	    client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);  
	    
		System.err.println("handle team start!!!");
		List<League> leagues = League.dao.find("select * from league where id in (3,5)");
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
		List<Team> lstNeedInsert = new ArrayList<Team>();
		List<Team> lstNeedUpdate = new ArrayList<Team>();
		Map<String, String> map = new HashMap<String, String>();
		Set<String> idSet = new HashSet<String>();
		String html = FetchHtmlUtils.getHtmlContent(client, leagueUrl);
		Document document = Jsoup.parse(html);
		//Document document = Jsoup.connect(leagueUrl).get(); 
		Elements bodyElement = document.select(".detailed-table");
		String roundId = null;
		if(bodyElement.size()>0){
			roundId = bodyElement.get(0).attr("data-round_id");
			Elements teamLinks = bodyElement.get(0).select(".team_rank");
			for(Element element : teamLinks){
				String teamId = element.attr("data-team_id");
				//防止重复
				if(idSet.contains(teamId)){
					continue;
				}else{
					idSet.add(teamId);
				}
				Elements aElements = element.select("a");
				String teamName = null;
				String teamUrl = null;
				if(aElements.size()>0){
					teamName = aElements.get(0).attr("title");
					teamUrl = SITE_PROFIX + aElements.get(0).attr("href");
				}
				//判断数据库中是否存在
				Team teamDB = Team.dao.findById(teamId);
				if(teamDB==null){
					Team team = new Team();
					team.set("id", teamId);
					team.set("name", teamName);
					team.set("team_url", teamUrl);
					team.set("league_id", leagueId);
					team.set("update_time", new Date());
					lstNeedInsert.add(team);
				}else{
					teamDB.set("name", teamName);
					teamDB.set("team_url", teamUrl);
					teamDB.set("league_id", leagueId);
					teamDB.set("update_time", new Date());
	 				lstNeedUpdate.add(teamDB);
				}
				map.put(teamId, teamUrl);
			}
		}
		
		updateTeamLeagueInfo(lstNeedInsert, lstNeedUpdate, leagueId);
		
		//最佳射手
		//handleBestShooter(document, leagueId, roundId);
		//助攻榜
		//TODO
		//排名
		//handleLeaguePosition(document, leagueId, roundId);
		//比赛
		//handleMatch(document, leagueId, roundId);
		
		for(Entry<String, String> entry : map.entrySet()){
			//获取球队信息
			handleTeamDetail(entry);
			//获取球场信息
			handleTeamVenue(entry);
		}
	}
	
	@Before(Tx.class)
	private void updateTeamLeagueInfo(List<Team> lstNeedInsert, List<Team> lstNeedUpdate, String leagueId){
		
		if(lstNeedInsert.size()!=0 || lstNeedUpdate.size()!=0){
			//解除关联
			Db.update("update team set league_id = '' where league_id = ? ", leagueId);
			Db.batchSave(lstNeedInsert, lstNeedInsert.size());
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
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
	private void handleBestShooter(Document document, String leagueId, String roundId){
		List<LeagueShooter> lstShooter = new ArrayList<LeagueShooter>();
		Elements elements = document.select(".playerstats");
		if(elements.size()>0){
			Elements trElements = elements.get(0).select("tbody > tr");
			int i=1;
			for(Element element : trElements){
				String playerId = element.attr("data-people_id");
				String teamId = element.attr("data-team_id");
				String playerName = null;
				String playerUrl = null;
				String teamName = null;
				String teamUrl = null;
				Elements playElements = element.select("a");
				if(playElements.size()>1){
					playerName = playElements.get(0).html();
					playerUrl = SITE_PROFIX + playElements.get(0).attr("href");
					teamName = playElements.get(1).attr("title");
					teamUrl = SITE_PROFIX + playElements.get(1).attr("href");
				}
				String goalStr = element.child(2).html();
				String penaltyStr = element.child(3).html();
				String firstGoalStr = element.child(4).html();
				
				LeagueShooter shooter = new LeagueShooter();
				shooter.set("player_id", playerId);
				shooter.set("player_name", playerName);
				shooter.set("player_url", playerUrl);
				shooter.set("rank", i);
				shooter.set("team_id", teamId);
				shooter.set("team_name", teamName);
				//TODO add column
				shooter.set("team_url", teamUrl);
				shooter.set("goal_count", Integer.valueOf(goalStr));
				shooter.set("penalty_count", Integer.valueOf(penaltyStr));
				shooter.set("first_goal_count", Integer.valueOf(firstGoalStr));
				shooter.set("league_id", leagueId);
				shooter.set("round_id", roundId);
				shooter.set("update_time", new Date());
				lstShooter.add(shooter);
				
				i++;
			}
		}
		
		if(lstShooter.size()>0){
			Db.update("delete from league_shooter where league_id = ?", leagueId);
			Db.batchSave(lstShooter, lstShooter.size());
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
	
	@Before(Tx.class)
	private void handleTeamDetail(Entry<String, String> entry) throws IOException{
		Team team = Team.dao.findById(entry.getKey());
		System.err.println("handle team： "+team.getStr("name")+" ing!!!");
		String teamContent = FetchHtmlUtils.getHtmlContent(client, entry.getValue());
		Document document = Jsoup.parse(teamContent);
		
		Elements teamImgElements = document.select(".logo");
		if(teamImgElements.size()>0){
			team.set("team_img", teamImgElements.get(0).child(0).attr("src"));
		}
		team.set("setup_time", CommonUtils.matcherString(CommonUtils.getPatternByName("成立于"), teamContent));
		team.set("address", CommonUtils.matcherString(CommonUtils.getPatternByName("地址"), teamContent).replaceAll(clearString, ""));
		team.set("country", CommonUtils.matcherString(CommonUtils.getPatternByName("国家"), teamContent));
		team.set("telphone", CommonUtils.matcherString(CommonUtils.getPatternByName("电话"), teamContent));
		team.set("fax", CommonUtils.matcherString(CommonUtils.getPatternByName("传真"), teamContent));
		team.set("email", CommonUtils.matcherString(CommonUtils.getPatternByName("电子邮件"), teamContent).replaceAll(tagString, ""));
		team.set("name_en", CommonUtils.getEnName(entry.getValue()));
		
		Elements venueImgElements = document.select(".block_team_venue");
		if(venueImgElements.size()>0){
			//TODO add column
			team.set("venue_small_img", venueImgElements.get(0).child(0).child(0).attr("src"));
		}
		team.set("update_time", new Date());
		team.update();
	}
	
	@Before(Tx.class)
	private void handleTeamVenue(Entry<String, String> entry) throws IOException{
		Team team = Team.dao.findById(entry.getKey());
		String venueContent = FetchHtmlUtils.getHtmlContent(client, entry.getValue()+"venue/");
		Document document = Jsoup.parse(venueContent);
		Elements venueElements = document.select(".block_venue_info-wrapper");
		if(venueElements.size()>0){
			String venueName = venueElements.get(0).child(0).html();
			if(FlagMask.isEditable(team.getInt("edit_flag"), FlagMask.TEAM_VENUE_NAME_MASK)){
				team.set("venue_name", venueName);
			}
			team.set("venue_name_en", venueName);
			if(FlagMask.isEditable(team.getInt("edit_flag"), FlagMask.TEAM_VENUE_CITY_MASK)){
				team.set("venue_address", CommonUtils.matcherString(CommonUtils.getPatternByName("城市:"), venueContent));
			}
			
			team.set("venue_capacity", CommonUtils.matcherString(CommonUtils.getPatternByName("容量:"), venueContent));
			Elements imgElements = venueElements.get(0).select("img");
			if(imgElements.size()>0){
				String venue_img = imgElements.get(0).attr("src");
				team.set("venue_img", venue_img);
			}
			team.set("update_time", new Date());
			team.update();
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

	public static void main(String[] args) {
		HttpClient client = new DefaultHttpClient();  
		String html = FetchHtmlUtils.getHtmlContent(client, "http://cn.soccerway.com/teams/england/stoke-city-fc/690/");
		System.err.println(html);
	}
	
}


