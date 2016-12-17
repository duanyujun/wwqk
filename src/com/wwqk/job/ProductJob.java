package com.wwqk.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.wwqk.constants.CommonConstants;
import com.wwqk.model.League;
import com.wwqk.model.LeagueAssists;
import com.wwqk.model.LeagueAssists163;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.LeagueShooter;
import com.wwqk.model.LeagueShooter163;
import com.wwqk.model.Player;
import com.wwqk.model.ShooterAssistsSource;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
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
		//球队排名，比赛
		teamPositionAndMatch();
		//同步射手和助攻王
		syncShooterAndAssists();
		//计算年龄
		calcAge();
		//计算每个球员的进球助攻数
		calcGoalAssistsNumber();
		
		//刷新射手助攻王
		copyShooter();
		copyAssists();
		
		client.getConnectionManager().shutdown();
	}
	
	private void syncShooterAndAssists(){
		syncShooter();
		syncAssists();
		translateShooter();
		translateAssists();
	}
	
	private void teamPositionAndMatch(){
		System.err.println("handle team positon and match start!!!");
		List<League> leagues = League.dao.find("select * from league");
		try {
			for(League league : leagues){
				handleTeamsUrl(league.getStr("league_url"), league.getStr("id"));
			}
		} catch (Exception e) {
			System.err.println("^^^^^^^"+e.getMessage()+" +++++"+e);
		}
		System.err.println("handle team positon and match end!!!");
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
		//handleMatch(document, leagueId, roundId);
		
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
			Team homeTeamDB = Team.dao.findById(homeTeamId);
			if(homeTeamDB!=null){
				homeTeamName = homeTeamDB.get("name");
			}
			match.set("home_team_id", homeTeamId);
			match.set("home_team_name", homeTeamName);
			match.set("home_team_url", homeTeamUrl);
			match.set("away_team_id", awayTeamId);
			Team awayTeamDB = Team.dao.findById(awayTeamId);
			if(awayTeamId!=null){
				awayTeamName = awayTeamDB.get("name");
			}
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
			Team teamDB = Team.dao.findById(teamId);
			if(teamDB!=null){
				teamName = teamDB.get("name");
			}
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
	
	private void syncShooter(){
		List<ShooterAssistsSource> lstSources = ShooterAssistsSource.dao.find("select * from shooter_assists_source where type = 1");
		for(ShooterAssistsSource source : lstSources){
			System.err.println("handle shooter start!!!"+" league: "+source.getStr("league_id")+" url:"+source.getStr("url"));
			String shooterHtml = FetchHtmlUtils.getHtmlContent(client, source.getStr("url"));
			Document document = Jsoup.parse(shooterHtml);
			Elements dataTableElements = document.select(".daTb01");
			if(dataTableElements.size()>0){
				List<LeagueShooter163> lstShooter163 = new ArrayList<LeagueShooter163>();
				Elements playerElements = dataTableElements.get(0).select("tr");
				//去掉第一个和最后一个tr
				for(int i=1; i<playerElements.size()-1; i++){
					Element trElement = playerElements.get(i);
					String rank = trElement.child(0).text();
					String playerName = trElement.child(1).text();
					String playerURL163 = trElement.child(1).child(0).attr("href");
					String teamName = trElement.child(2).text();
					String goalCount = trElement.child(5).text();
					String penaltyCount = trElement.child(6).text();
					LeagueShooter163 shooter163 = new LeagueShooter163();
					shooter163.set("rank", rank);
					shooter163.set("player_name_163", playerName);
					shooter163.set("team_name_163", teamName);
					shooter163.set("goal_count", goalCount);
					shooter163.set("penalty_count", penaltyCount);
					shooter163.set("player_url_163", playerURL163);
					lstShooter163.add(shooter163);
				}
				//查询数据库
				List<LeagueShooter163> lstShooter163DB = LeagueShooter163.dao.find("select * from league_shooter_163 where league_id = ? ", source.getStr("league_id"));
				//key： playerName_teamName
				Map<String, LeagueShooter163> map = new HashMap<String, LeagueShooter163>();
				for(LeagueShooter163 db:lstShooter163DB){
					map.put(db.getStr("player_name_163")+"_"+db.getStr("team_name_163"), db);
				}
				
				List<LeagueShooter163> needUpdateList = new ArrayList<LeagueShooter163>();
				List<LeagueShooter163> needInsertList = new ArrayList<LeagueShooter163>();
				for(LeagueShooter163 shooter163:lstShooter163){
					String key = shooter163.getStr("player_name_163")+"_"+shooter163.getStr("team_name_163");
					if(map.get(key)!=null){
						LeagueShooter163 shooterDB = map.get(key);
						shooterDB.set("rank", shooter163.get("rank"));
						shooterDB.set("goal_count", shooter163.get("goal_count"));
						shooterDB.set("penalty_count", shooter163.get("penalty_count"));
						shooterDB.set("update_time", new Date());
						shooterDB.set("player_url_163", shooter163.get("player_url_163"));
						needUpdateList.add(shooterDB);
					}else{
						shooter163.set("league_id", source.getStr("league_id"));
						shooter163.set("update_time", new Date());
						needInsertList.add(shooter163);
					}
				}
				if(needInsertList.size()>0){
					Db.batchSave(needInsertList, needInsertList.size());
				}
				if(needUpdateList.size()>0){
					Db.batchUpdate(needUpdateList, needUpdateList.size());
				}
			}
		}
	}
	
	private void syncAssists(){
		List<ShooterAssistsSource> lstSources = ShooterAssistsSource.dao.find("select * from shooter_assists_source where type = 2");
		for(ShooterAssistsSource source : lstSources){
			System.err.println("handle assists start!!!"+" league: "+source.getStr("league_id")+" url:"+source.getStr("url"));
			String assistsHtml = FetchHtmlUtils.getHtmlContent(client, source.getStr("url"));
			Document document = Jsoup.parse(assistsHtml);
			Elements dataTableElements = document.select(".daTb01");
			if(dataTableElements.size()>0){
				List<LeagueAssists163> lstAssists163 = new ArrayList<LeagueAssists163>();
				Elements playerElements = dataTableElements.get(0).select("tr");
				//去掉第一个和最后一个tr
				for(int i=1; i<playerElements.size()-1; i++){
					Element trElement = playerElements.get(i);
					String rank = trElement.child(0).text();
					String playerName = trElement.child(1).text();
					String playerURL163 = trElement.child(1).child(0).attr("href");
					String teamName = trElement.child(2).text();
					String assistsCount = trElement.child(7).text();
					LeagueAssists163 assists163 = new LeagueAssists163();
					assists163.set("rank", rank);
					assists163.set("player_name_163", playerName);
					assists163.set("team_name_163", teamName);
					assists163.set("assists_count", assistsCount);
					assists163.set("player_url_163", playerURL163);
					lstAssists163.add(assists163);
				}
				//查询数据库
				List<LeagueAssists163> lstAssists163DB = LeagueAssists163.dao.find("select * from league_assists_163 where league_id = ? ", source.getStr("league_id"));
				//key： playerName_teamName
				Map<String, LeagueAssists163> map = new HashMap<String, LeagueAssists163>();
				for(LeagueAssists163 db:lstAssists163DB){
					map.put(db.getStr("player_name_163")+"_"+db.getStr("team_name_163"), db);
				}
				
				List<LeagueAssists163> needUpdateList = new ArrayList<LeagueAssists163>();
				List<LeagueAssists163> needInsertList = new ArrayList<LeagueAssists163>();
				for(LeagueAssists163 assists163:lstAssists163){
					String key = assists163.getStr("player_name_163")+"_"+assists163.getStr("team_name_163");
					if(map.get(key)!=null){
						LeagueAssists163 assistsDB = map.get(key);
						assistsDB.set("rank", assists163.get("rank"));
						assistsDB.set("assists_count", assists163.get("assists_count"));
						assistsDB.set("update_time", new Date());
						assistsDB.set("player_url_163", assists163.get("player_url_163"));
						needUpdateList.add(assistsDB);
					}else{
						assists163.set("league_id", source.getStr("league_id"));
						assists163.set("update_time", new Date());
						needInsertList.add(assists163);
					}
				}
				if(needInsertList.size()>0){
					Db.batchSave(needInsertList, needInsertList.size());
				}
				if(needUpdateList.size()>0){
					Db.batchUpdate(needUpdateList, needUpdateList.size());
				}
			}
		}
	}

	private void translateShooter(){
		List<LeagueShooter163> lstShooter = LeagueShooter163.dao.find("select * from league_shooter_163");
		for(LeagueShooter163 shooter163:lstShooter){
			//判断 丹尼斯.苏亚雷斯
			if(StringUtils.isNotBlank(shooter163.getStr("player_url_163")) && shooter163.getStr("player_url_163").contains("/602708.html")){
				shooter163.set("player_name_163", "丹尼斯.苏亚雷斯");
			}
			
			//判断亚当史密斯
			if(StringUtils.isNotBlank(shooter163.getStr("player_url_163")) && shooter163.getStr("player_url_163").contains("/434972.html")){
				shooter163.set("player_name_163", "亚当·史密斯");
			}
			
			Player player = Player.dao.findFirst("select p.*, t.name team_name from player p, team t where p.team_id = t.id and p.name = ? and t.name = ?",
					shooter163.getStr("player_name_163"), shooter163.getStr("team_name_163"));
			if(player!=null){
				shooter163.set("player_id", player.get("id"));
				shooter163.set("player_name", player.get("name"));
				shooter163.set("team_id", player.get("team_id"));
				shooter163.set("team_name", player.get("team_name"));
			}else{
				LeagueAssists163 assists163 = LeagueAssists163.dao.findFirst("select * from league_assists_163 where player_name_163 = ? and team_name_163 = ? ", shooter163.get("player_name_163"), shooter163.get("team_name_163"));
				if(assists163!=null && StringUtils.isNotBlank(assists163.get("player_id"))){
					shooter163.set("player_id", assists163.get("player_id"));
					shooter163.set("player_name", assists163.get("player_name"));
					shooter163.set("team_id", assists163.get("team_id"));
					shooter163.set("team_name", assists163.get("team_name"));
				}
			}
		}
		Db.batchUpdate(lstShooter, lstShooter.size());
	}
	
	private void translateAssists(){
		List<LeagueAssists163> lstAssists = LeagueAssists163.dao.find("select * from league_assists_163");
		for(LeagueAssists163 assists163:lstAssists){
			//判断 丹尼斯.苏亚雷斯
			if(StringUtils.isNotBlank(assists163.getStr("player_url_163")) &&  assists163.getStr("player_url_163").contains("/602708.html")){
				assists163.set("player_name_163", "丹尼斯.苏亚雷斯");
			}
			//判断亚当史密斯
			if(StringUtils.isNotBlank(assists163.getStr("player_url_163")) && assists163.getStr("player_url_163").contains("/434972.html")){
				assists163.set("player_name_163", "亚当·史密斯");
			}
			
			Player player = Player.dao.findFirst("select p.*, t.name team_name from player p, team t where p.team_id = t.id and p.name = ? and t.name = ?",
					assists163.get("player_name_163"), assists163.get("team_name_163"));
			if(player!=null){
				assists163.set("player_id", player.get("id"));
				assists163.set("player_name", player.get("name"));
				assists163.set("team_id", player.get("team_id"));
				assists163.set("team_name", player.get("team_name"));
			}else{
				LeagueShooter163 shooter163 = LeagueShooter163.dao.findFirst("select * from league_shooter_163 where player_name_163 = ? and team_name_163 = ? ", assists163.get("player_name_163"), assists163.get("team_name_163"));
				if(shooter163!=null && StringUtils.isNotBlank(shooter163.get("player_id"))){
					assists163.set("player_id", shooter163.get("player_id"));
					assists163.set("player_name", shooter163.get("player_name"));
					assists163.set("team_id", shooter163.get("team_id"));
					assists163.set("team_name", shooter163.get("team_name"));
				}
			}
		}
		Db.batchUpdate(lstAssists, lstAssists.size());
	}
	
	private void calcAge(){
		String calcAge = "";
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		List<Player> lstPlayer = Player.dao.find("select * from player");
		for(Player player : lstPlayer){
			if(player.getDate("birthday")==null){
				continue;
			}
			calcAge = String.valueOf(DateTimeUtils.getAgeByBirthday(player.getDate("birthday")));
			if(!calcAge.equals(player.getStr("age"))){
				player.set("age", calcAge);
				player.set("update_time", new Date());
				lstNeedUpdate.add(player);
			}
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
	private void calcGoalAssistsNumber(){
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		List<LeagueShooter163> lstShooter163 = LeagueShooter163.dao.find("SELECT * FROM league_shooter_163 WHERE player_id IS NOT NULL AND player_id!=''");
		for(LeagueShooter163 shooter163 : lstShooter163){
			Player player = Player.dao.findById(shooter163.getStr("player_id"));
			if(player.getInt("goal_count")!=shooter163.getInt("goal_count")){
				player.set("goal_count", shooter163.getInt("goal_count"));
				player.set("update_time", new Date());
				lstNeedUpdate.add(player);
			}
		}
		if(lstNeedUpdate.size()!=0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
		lstNeedUpdate.clear();
		
		List<LeagueAssists163> lstAssists163 = LeagueAssists163.dao.find("SELECT * FROM league_assists_163 WHERE player_id IS NOT NULL AND player_id!=''");
		for(LeagueAssists163 assists163 : lstAssists163){
			Player player = Player.dao.findById(assists163.getStr("player_id"));
			if(player.getInt("assists_count")!=assists163.getInt("assists_count")){
				player.set("assists_count", assists163.getInt("assists_count"));
				player.set("update_time", new Date());
				lstNeedUpdate.add(player);
			}
		}
		if(lstNeedUpdate.size()!=0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}

	public void copyShooter(){
		List<League> lstLeagues = League.dao.find("select * from league ");
		for(League league:lstLeagues){
			List<LeagueShooter163> lstShooter163 = LeagueShooter163.dao.find("select * from league_shooter_163 where league_id = ? order by goal_count desc, penalty_count asc limit 0, ? ", league.get("id"), CommonConstants.DEFAULT_RANK_SIZE);
			List<LeagueShooter> lstShooter = new ArrayList<LeagueShooter>(CommonConstants.DEFAULT_RANK_SIZE);
			if(lstShooter163.size()==CommonConstants.DEFAULT_RANK_SIZE){
				for(LeagueShooter163 shooter163:lstShooter163){
					LeagueShooter shooter = new LeagueShooter();
					shooter.set("player_id", shooter163.get("player_id"));
					Player player = Player.dao.findById(shooter163.getStr("player_id"));
					shooter.set("player_img", player.get("img_small_local"));
					shooter.set("player_name", player.get("name"));
					shooter.set("rank", shooter163.get("rank"));
					shooter.set("team_id", shooter163.get("team_id"));
					shooter.set("team_name", shooter163.get("team_name"));
					shooter.set("goal_count", shooter163.get("goal_count"));
					shooter.set("penalty_count", shooter163.get("penalty_count"));
					shooter.set("league_id", shooter163.get("league_id"));
					shooter.set("update_time", new Date());
					lstShooter.add(shooter);
				}
				Db.update("delete from league_shooter where league_id = ? ", league.getStr("id"));
				Db.batchSave(lstShooter, lstShooter.size());
			}
		}
		
	}
	
	public void copyAssists(){
		List<League> lstLeagues = League.dao.find("select * from league ");
		for(League league:lstLeagues){
			List<LeagueAssists163> lstAssists163 = LeagueAssists163.dao.find("select * from league_assists_163 where league_id = ? order by rank asc limit 0, ? ", league.get("id"), CommonConstants.DEFAULT_RANK_SIZE);
			List<LeagueAssists> lstAssists = new ArrayList<LeagueAssists>(CommonConstants.DEFAULT_RANK_SIZE);
			if(lstAssists163.size()==CommonConstants.DEFAULT_RANK_SIZE){
				for(LeagueAssists163 assists163:lstAssists163){
					LeagueAssists assists = new LeagueAssists();
					assists.set("player_id", assists163.get("player_id"));
					Player player = Player.dao.findById(assists.getStr("player_id"));
					assists.set("player_img", player.get("img_small_local"));
					assists.set("player_name", assists163.get("player_name"));
					assists.set("rank", assists163.get("rank"));
					assists.set("team_id", assists163.get("team_id"));
					assists.set("team_name", assists163.get("team_name"));
					assists.set("assists_count", assists163.get("assists_count"));
					assists.set("league_id", assists163.get("league_id"));
					assists.set("update_time", new Date());
					lstAssists.add(assists);
				}
				Db.update("delete from league_assists where league_id = ? ", league.getStr("id"));
				Db.batchSave(lstAssists, lstAssists.size());
			}
		}
		
	}
	
}


