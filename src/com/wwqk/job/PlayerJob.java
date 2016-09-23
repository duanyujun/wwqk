package com.wwqk.job;

import java.io.IOException;
import java.text.ParseException;
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
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.Career;
import com.wwqk.model.Coach;
import com.wwqk.model.CoachCareer;
import com.wwqk.model.CoachTrophy;
import com.wwqk.model.Injury;
import com.wwqk.model.League;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.model.Transfer;
import com.wwqk.model.Trophy;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.FetchHtmlUtils;
import com.wwqk.utils.StringUtils;

public class PlayerJob implements Job {
	
	//TODO　球员小图片 http://cache.images.core.optasports.com/soccer/players/50x50/79495.png
	private HttpClient httpClient = new DefaultHttpClient();
	String clearString = "<.*?/>";
	private static final Pattern CAREER_PATTERN = Pattern.compile("class=\"season\">.*?>(.*?)</a>.*?href=\"(.*?)\".*?title=\"(.*?)\".*?<span class=\"(.*?)\".*?href=\"(.*?)\".*?title=\"(.*?)\".*?game-minutes available\">(.*?)</td>.*?appearances available\">(.*?)</td>.*?lineups available\">(.*?)</td>.*?subs-in available\">(.*?)</td>.*?subs-out available\">(.*?)</td>.*?subs-on-bench available\">(.*?)</td>.*?goals available\">(.*?)</td>.*?yellow-cards available\">(.*?)</td>.*?2nd-yellow-cards available\">(.*?)</td>.*?red-cards available\">(.*?)</td>");
	private static final Pattern TROPHY_TABLE_PATTERN = Pattern.compile("trophies-table\">(.*?)</table>");
	private static final Pattern TROPHY_TITLE_PATTERN = Pattern.compile("<th.*?>(.*?)</th>");
	private static final Pattern TROPHY_COMPETITION_PATTERN = Pattern.compile("class=\"competition\">(.*?)</td>.*?label\">(.*?)</td>.*?total\">(.*?)</td>.*?seasons\">(.*?)</td>");
	private static final Pattern INJURY_PATTERN = Pattern.compile("icon injury.*?<td>(.*?)</td>.*?<span.*?>(.*?)</span>.*?<td class=\"enddate\">(.*?)</td>");
	private static final Pattern TRANSFER_TABLE_PATTERN = Pattern.compile("transfers-container.*?</table>");
	private static final Pattern TRANSFER_PATTERN = Pattern.compile("<span.*?>(.*?)</span>.*?<a.*?>(.*?)</a>.*?<a.*?>(.*?)</a>.*?<td.*?>(.*?)</td>");
	private static final Pattern PLAYER_URL_PATTERN = Pattern.compile("class=\"flag_16 right_16.*?<a href=\"(.*?)\".*?>(.*?)</a>");
	private static final Pattern PLAYER_IMG_PATTERN = Pattern.compile("class=\"yui-u\">.*?<img src=\"(.*?)\"");
	private static final Pattern COUCH_URL_PATTERN = Pattern.compile("教练</th>.*?href=\"(.*?)\"");
	private static final String SITE_PROFIX = "http://cn.soccerway.com";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<Team> lstTeam = Team.dao.find("select * from team where id>889 order by id+0 asc ");
		String htmlTeam = null;
		System.err.println("handle player start!!!");
		try {
			for(Team team : lstTeam){
				System.err.println("handle team:"+team.getStr("id")+"_"+team.getStr("name"));
				htmlTeam = FetchHtmlUtils.getHtmlContent(httpClient, team.getStr("team_url"));
				handlePlayerUrl(htmlTeam, team.getStr("id"));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		httpClient.getConnectionManager().shutdown();
		System.err.println("handle player end!!!");
	}

	private void handlePlayerUrl(String htmlTeam, String teamId) throws IOException {
		
		List<Player> lstNeedInsert = new ArrayList<Player>();
		List<Player> lstNeedUpdate = new ArrayList<Player>();
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
				player.set("player_url", SITE_PROFIX+url);
				player.set("team_id", teamId);
				player.set("update_time", new Date());
				lstNeedInsert.add(player);
			}else{
				playerDB.set("name", matcher.group(2));
				playerDB.set("player_url", SITE_PROFIX+url);
				playerDB.set("team_id", teamId);
				playerDB.set("update_time", new Date());
				playerDB.update();
				lstNeedUpdate.add(playerDB);
			}
			
			map.put(id, SITE_PROFIX+url);
		}
		
		//更新球员球队id信息
		updatePlayerTeamInfo(lstNeedInsert, lstNeedUpdate, teamId);
		
		//教练
		handleCouchInfo(htmlTeam, teamId);
		
		for(Entry<String, String> entry : map.entrySet()){
			handlePlayerDetail(entry);
		}
	}
	
	@Before(Tx.class)
	private void handleCouchInfo(String htmlTeam, String teamId) {
		String coachUrl = CommonUtils.matcherString(COUCH_URL_PATTERN, htmlTeam);
		if(StringUtils.isBlank(coachUrl) || !coachUrl.contains("coaches")){
			return;
		}
		
		Team team = Team.dao.findById(teamId);
		if(team!=null){
			team.set("coach_url", SITE_PROFIX+coachUrl);
			team.update();
		}
		
		String coachContent = FetchHtmlUtils.getHtmlContent(httpClient, SITE_PROFIX+coachUrl);
		String firstName = CommonUtils.matcherString(CommonUtils.getPatternByName("名字"), coachContent);
		String lastName = CommonUtils.matcherString(CommonUtils.getPatternByName("姓氏"), coachContent);
		String nationality = CommonUtils.matcherString(CommonUtils.getPatternByName("国籍"), coachContent);
		Date birthday = CommonUtils.getCNDate(CommonUtils.matcherString(CommonUtils.getPatternByName("出生日期"), coachContent));
		String age = CommonUtils.matcherString(CommonUtils.getPatternByName("年龄"), coachContent);
		String birthCountry = CommonUtils.matcherString(CommonUtils.getPatternByName("出生国家"), coachContent);
		String birthPlace =  CommonUtils.matcherString(CommonUtils.getPatternByName("出生地"), coachContent);
		String imgBig = CommonUtils.matcherString(PLAYER_IMG_PATTERN, coachContent);
		
		System.err.println("++===++ handle coach "+firstName+" start！");
		
		String coachId = CommonUtils.getId(coachUrl);
		boolean isNeedSave = false;
		Coach coach = Coach.dao.findById(coachId);
		if(coach==null){
			coach = new Coach();
			isNeedSave = true;
		}
		coach.set("id", coachId);
		coach.set("first_name", firstName);
		coach.set("last_name", lastName);
		coach.set("name", lastName);
		coach.set("nationality", nationality);
		coach.set("birthday", birthday);
		coach.set("age", age);
		coach.set("birth_country", birthCountry);
		coach.set("birth_place", birthPlace);
		coach.set("img_big", imgBig);
		coach.set("team_id", teamId);
		coach.set("coach_url", SITE_PROFIX+coachUrl);
		coach.set("update_time", new Date());
		if(isNeedSave){
			coach.save();
		}else{
			coach.update();
		}
		
		//coach career
		Document document = Jsoup.parse(coachContent);
		Elements careerElements = document.select(".block_coach_career");
		if(careerElements.size()>0){
			Elements tbodyElements = careerElements.get(0).select("tbody");
			if(tbodyElements.size()>0){
				Elements trElements = tbodyElements.get(0).select("tr");
				if(trElements.size()>0){
					List<CoachCareer> lstCoachCareer = new ArrayList<CoachCareer>();
					for(int i=0; i<trElements.size(); i++){
						Element element = trElements.get(i);
						CoachCareer coachCareer = new CoachCareer();
						coachCareer.set("team_name", element.select("a").get(0).text());
						String teamUrl = element.select("a").get(0).attr("href");
						coachCareer.set("team_url", teamUrl);
						coachCareer.set("team_id", CommonUtils.getId(teamUrl));
						coachCareer.set("start_date", getCNDateMonth(element.child(1).text()));
						if(StringUtils.isNotBlank(element.child(2).text())){
							coachCareer.set("end_date", getCNDateMonth(element.child(2).text()));
						}else{
							coachCareer.set("end_date",null);
						}
						coachCareer.set("coach_id", coachId);
						coachCareer.set("update_time", new Date());
						lstCoachCareer.add(coachCareer);
					}
				
					Db.update("delete from coach_career where coach_id = ?", coachId);
					Db.batchSave(lstCoachCareer, lstCoachCareer.size());
				}
			}
		}
		
		//coach trophy
		handleCoachTrophy(coachContent, coachId);
		
		System.err.println("++===++ handle coach "+firstName+" end！");
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
		String playerContent = FetchHtmlUtils.getHtmlContent(httpClient, entry.getValue());
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
		player.set("update_time", new Date());
		player.set("img_big", CommonUtils.matcherString(PLAYER_IMG_PATTERN, playerContent));
	
		player.update();
		
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
					String preTitle = null;
					while(cmpMatcher.find()){
						//String cmpCssStr = cmpMatcher.group(1);
						String cmpTitle = cmpMatcher.group(1);
						cmpTitle = Jsoup.clean(cmpTitle, Whitelist.none());
						if(StringUtils.isBlank(cmpTitle)){
							cmpTitle = preTitle;
						}else{
							preTitle = cmpTitle;
						}
						String trophyName = cmpMatcher.group(2);
						String trophyCount = cmpMatcher.group(3);
						String season = cmpMatcher.group(4);
						season = Jsoup.clean(cmpTitle, Whitelist.none());
						season = season.replaceAll("\\s", "");
						
						Trophy trophy = new Trophy();
						//trophy.set("league_css", cmpCssStr);
						trophy.set("trophy_area", groupTitle);
						trophy.set("league_name", cmpTitle);
						trophy.set("trophy_name", trophyName);
						trophy.set("times", trophyCount);
						trophy.set("season", season);
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
	
	private void handleCoachTrophy(String coachContent, String coachId){
		Matcher matcher = TROPHY_TABLE_PATTERN.matcher(coachContent);
		if(matcher.find()){
			List<CoachTrophy> lstTrophy = new ArrayList<CoachTrophy>();
			String trophyContent = matcher.group(1);
			String[] groupArray = trophyContent.split(" <tr class=\"group-head\">");
			for(String group : groupArray){
				Matcher groupMatcher = TROPHY_TITLE_PATTERN.matcher(group);
				if(groupMatcher.find()){
					String groupTitle = groupMatcher.group(1);
					Matcher cmpMatcher = TROPHY_COMPETITION_PATTERN.matcher(group);
					String preTitle = null;
					while(cmpMatcher.find()){
						//String cmpCssStr = cmpMatcher.group(1);
						String cmpTitle = cmpMatcher.group(1);
						cmpTitle = Jsoup.clean(cmpTitle, Whitelist.none());
						cmpTitle = cmpTitle.replaceAll("\\s", "");
						if(StringUtils.isBlank(cmpTitle)){
							cmpTitle = preTitle;
						}else{
							preTitle = cmpTitle;
						}
												
						String trophyName = cmpMatcher.group(2);
						String trophyCount = cmpMatcher.group(3);
						String seasons = cmpMatcher.group(4);
						
						seasons = Jsoup.clean(seasons, Whitelist.none());
						seasons = seasons.replaceAll("\\s", "");
						
						
						
						CoachTrophy trophy = new CoachTrophy();
						//trophy.set("league_css", cmpCssStr);
						trophy.set("trophy_area", groupTitle);
						trophy.set("league_name", cmpTitle);
						trophy.set("trophy_name", trophyName);
						trophy.set("times", trophyCount);
						trophy.set("season", seasons);
						trophy.set("coach_id", coachId);
						trophy.set("update_time", new Date());
						
						lstTrophy.add(trophy);
					}
				}
			}
			if(lstTrophy.size()>0){
				Db.update("delete from coach_trophy where coach_id = ?", coachId);
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
			injury.set("end_time", CommonUtils.getDateByString(matcher.group(3).replaceAll("<.*?>", "")));
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
	
	private Date getCNDateMonth(String dateStr){
		if(StringUtils.isBlank(dateStr)){
			return null;
		}
		if(dateStr.contains("十二月")){
			dateStr = dateStr.replace("十二月", "12");
		}else if(dateStr.contains("十一月")){
			dateStr = dateStr.replace("十一月", "11");
		}else{
			for(Entry<String, String> entry : CommonUtils.MONTH_TEN_MAP.entrySet()){
				dateStr = dateStr.replace(entry.getKey().trim(), entry.getValue());
			}
		}
		String[] patterns ={"MM yyyy"};
		Date date = null;
		try {
			date = DateTimeUtils.parseDate(dateStr, patterns);
		} catch (ParseException e) {
			
		}
		
		return date;
	}
	
}






