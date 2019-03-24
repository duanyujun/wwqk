package com.wwqk.plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchLive;
import com.wwqk.model.TeamDic;
import com.wwqk.model.TipsMatch;
import com.wwqk.model.Videos;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.PinyinUtils;
import com.wwqk.utils.SpecialNameUtils;
import com.wwqk.utils.StringUtils;

public class CP500Team {

	private static final String SITE_URL = "https://liansai.500.com/";
	private static final String HOST = "https://liansai.500.com";
	
	public static void collectTeams(){
		
		Connection connect = Jsoup.connect(SITE_URL).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.getCP500Header("https://www.500.com/"));
		try {
			Document document = data.get();
			
			String leagueUrl = "";
			String leagueName = "";
			String teamUrl = "";
			String teamId = "";
			String teamName = "";
			String stdTeamName = "";
			Set<String> teamIdSet = new HashSet<>();
			
			//联赛和国际赛事
			Elements elements = document.select(".lallrace_main_list");
			//各大洲杯赛
			elements.addAll(document.select(".lrace_bei"));
			for(Element areaElement : elements){
				Elements leagueElements = areaElement.select("a");
				for(Element leagueElement : leagueElements){
					if(!leagueElement.attr("href").contains("zuqiu-")) {
						continue;
					}
					leagueName = leagueElement.text();
					leagueUrl = HOST + leagueElement.attr("href");
					System.err.println(leagueName+"："+leagueUrl);
					Connection connectDetail = Jsoup.connect(leagueUrl+"teams/");
					Map<String, String> detailHeaderMap = MatchUtils.getCP500Header(leagueUrl);
					data = connectDetail.data(detailHeaderMap);
					document = data.get();
					Elements teamElements = document.select(".jTrInterval").select("a");
					for(Element teamEle:teamElements) {
						if(!teamEle.attr("href").contains("/team/")) {
							continue;
						}
						teamUrl = teamEle.attr("href");
						teamName = teamEle.text();
						teamId = teamUrl.substring(teamUrl.indexOf("/team/")+6);
						teamId = teamId.substring(0, teamId.length()-1);
						
						if(teamIdSet.contains(teamId)){
							continue;
						}else{
							teamIdSet.add(teamId);
						}
						
						TeamDic teamDic = new TeamDic();
						teamDic.set("ok_id", teamId);
						teamDic.set("team_name", teamName);
						teamDic.set("md5", CommonUtils.md5(teamName));
						stdTeamName = SpecialNameUtils.getStdName(teamName);
						
						teamDic.set("std_name", stdTeamName);
						teamDic.set("std_md5", CommonUtils.md5(stdTeamName));
						teamDic.set("std_name_py", PinyinUtils.getPingYin(stdTeamName));
						teamDic.set("league_name", leagueName);
						teamDic.set("team_url", teamUrl);
						TeamDic teamDicDB = TeamDic.dao.findFirst("select * from team_dic where ok_id = ?", teamId);
						
						if(teamDicDB!=null){
							teamDic.set("id", teamDicDB.get("id"));
							teamDic.update();
						}else{
							teamDic.save();
						}
					}
					
					Thread.sleep(200);
				}
				
//				connect = Jsoup.connect(leagueUrl).ignoreContentType(true);
//				data = connect.data(MatchUtils.getOkoooHeader("http://www.okooo.com/soccer/"));
//				document = data.get();
//				Element teamsElement = document.select(".LotteryList_Data").last();
//				teamsHtml = teamsElement.html();
//				teamMatcher = TEAM_PATTERN.matcher(teamsHtml);
//				while (teamMatcher.find()) {
//					teamId = teamMatcher.group(1);
//					if(teamIdSet.contains(teamId)){
//						continue;
//					}else{
//						teamIdSet.add(teamId);
//					}
//					
//					teamName = teamMatcher.group(2).replace("\\s+", "");
//					TeamDic teamDic = new TeamDic();
//					teamDic.set("ok_id", teamId);
//					teamDic.set("team_name", teamName);
//					teamDic.set("md5", CommonUtils.md5(teamName));
//					stdTeamName = SpecialNameUtils.getStdName(teamName);
//					
//					if(stdTeamName.contains("img")){
//						continue;
//					}
//					teamDic.set("std_name", stdTeamName);
//					teamDic.set("std_md5", CommonUtils.md5(stdTeamName));
//					teamDic.set("std_name_py", PinyinUtils.getPingYin(stdTeamName));
//					teamDic.set("league_name", leagueName);
//					TeamDic teamDicDB = TeamDic.dao.findFirst("select * from team_dic where ok_id = ?", teamId);
//					
//					if(teamDicDB!=null){
//						teamDic.set("id", teamDicDB.get("id"));
//						teamDic.update();
//					}else{
//						teamDic.save();
//					}
//				}
				
			}
			
//			//各大洲杯赛
//			elements = document.select(".lrace_bei");
//			for(Element areaElement : elements){
//				Elements leagueElements = areaElement.select("a");
//				for(Element leagueElement : leagueElements){
//					if(!leagueElement.attr("href").contains("zuqiu-")) {
//						continue;
//					}
//					leagueName = leagueElement.text();
//					leagueUrl = HOST + leagueElement.attr("href");
//					System.err.println(leagueName+":"+leagueUrl);
//				}
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 收集5大联赛标准球队名称
	 */
	public static void collectLeague5StdName(){
		String sql = "update team t, team_dic d set t.std_md5 = d.std_md5 where t.std_md5 is null and t.name = d.std_name and d.std_md5!='0' ";
		Db.update(sql);
	}
	
	public static void setOtherStdMd5(String stdType){
//		System.err.println("collectLeague5StdName 开始...");
//		collectLeague5StdName();
//		System.err.println("collectLeague5StdName 结束...");
		
		
//		System.err.println("setLeagueMatchStdMd5 开始...");
//		setLeagueMatchStdMd5();
//		System.err.println("setMatchLiveStdMd5 开始...");
//		setMatchLiveStdMd5();
//		System.err.println("setVideosStdMd5 开始...");
//		setVideosStdMd5();
//		System.err.println("setTipsMatchStdMd5 开始...");
//		setTipsMatchStdMd5();
//		System.err.println("setAllLiveMatchStdMd5 开始...");
//		setAllLiveMatchStdMd5();
//		System.err.println("setOtherStdMd5 结束...");
		
		if(StringUtils.isBlank(stdType) || "1".equals(stdType)){
			setLeagueMatchStdMd5();
		}else if("2".equals(stdType)){
			setMatchLiveStdMd5();
		}else if("3".equals(stdType)){
			setVideosStdMd5();
		}else if("4".equals(stdType)){
			setTipsMatchStdMd5();
		}else if("5".equals(stdType)){
			setAllLiveMatchStdMd5();
		}
	}
	
	/**
	 * 以team为标准,更新league_match和league_match_history的home_std_md5和away_std_md5
	 */
	public static void setLeagueMatchStdMd5(){
		//league_match
		String sqlLmHome = "update team t, league_match m set m.home_std_md5 = t.std_md5 where m.home_std_md5 is null and t.std_md5 is not null and t.id = m.home_team_id ";
		Db.update(sqlLmHome);
		String sqlLmAway = "update team t, league_match m set m.away_std_md5 = t.std_md5 where m.away_std_md5 is null and t.std_md5 is not null and t.id = m.away_team_id ";
		Db.update(sqlLmAway);
		
		//league_match_history
		String sqlLmhHome = "update team t, league_match_history m set m.home_std_md5 = t.std_md5 where m.home_std_md5 and t.std_md5 is not null is null and t.id = m.home_team_id ";
		Db.update(sqlLmhHome);
		String sqlLmhAway = "update team t, league_match_history m set m.away_std_md5 = t.std_md5 where m.away_std_md5 and t.std_md5 is not null is null and t.id = m.away_team_id ";
		Db.update(sqlLmhAway);
		
		//剩下的用team_dic表做映射
		//league_match
		String sqlLmHome2 = "update team_dic t, league_match m set m.home_std_md5 = t.std_md5 where m.home_std_md5 is null and t.std_md5!='0' and t.team_name = m.home_team_name";
		Db.update(sqlLmHome2);
		String sqlLmAway2 = "update team_dic t, league_match m set m.away_std_md5 = t.std_md5 where m.away_std_md5 is null and t.std_md5!='0' and t.team_name = m.away_team_name ";
		Db.update(sqlLmAway2);
		
		//league_match_history
		String sqlLmhHome2 = "update team_dic t, league_match_history m set m.home_std_md5 = t.std_md5 where m.home_std_md5 is null and t.std_md5!='0' and t.team_name = m.home_team_name ";
		Db.update(sqlLmhHome2);
		String sqlLmhAway2 = "update team_dic t, league_match_history m set m.away_std_md5 = t.std_md5 where m.away_std_md5 is null and t.std_md5!='0' and t.team_name = m.away_team_name ";
		Db.update(sqlLmhAway2);
		
		//剩下没有的插入到team_dic表中
		List<LeagueMatchHistory> listHistory = LeagueMatchHistory.dao.find("select home_team_name, away_team_name, home_std_md5, away_std_md5, league_name from league_match_history where home_std_md5 is null or away_std_md5 is null ");
		for(LeagueMatchHistory history : listHistory){
			commonAddTeamDic(history.getStr("home_std_md5"), history.getStr("home_team_name"), history.getStr("league_name"));
			commonAddTeamDic(history.getStr("away_std_md5"), history.getStr("away_team_name"), history.getStr("league_name"));
		}
	}
	
	/**
	 * 以team_dic为标准,更新match_live中的home_std_md5和away_std_md5
	 */
	public static void setMatchLiveStdMd5(){
	
		String sqlLmHome = "update team_dic t, match_live m set m.home_std_md5 = t.std_md5 where m.home_std_md5 is null and t.std_md5!='0' and t.team_name = m.home_team_name";
		Db.update(sqlLmHome);
		String sqlLmAway = "update team_dic t, match_live m set m.away_std_md5 = t.std_md5 where m.away_std_md5 is null and t.std_md5!='0' and t.team_name = m.away_team_name ";
		Db.update(sqlLmAway);
		
		//剩下没有的插入到team_dic表中
		List<MatchLive> listHistory = MatchLive.dao.find("select home_team_name, away_team_name, home_std_md5, away_std_md5, match_key from match_live where home_std_md5 is null or away_std_md5 is null ");
		String leagueName = null;
		AllLiveMatch allLiveMatch = null;
		for(MatchLive history : listHistory){
			allLiveMatch = AllLiveMatch.dao.findFirst("select * from all_live_match where match_key = ?", history.getStr("match_key"));
			if(allLiveMatch!=null) {
				leagueName = allLiveMatch.getStr("league_name");
			}
			commonAddTeamDic(history.getStr("home_std_md5"), history.getStr("home_team_name"), leagueName);
			commonAddTeamDic(history.getStr("away_std_md5"), history.getStr("away_team_name"), leagueName);
		}
	}
	
	/**
	 * 以team_dic为标准,更新videos中的home_std_md5和away_std_md5
	 */
	public static void setVideosStdMd5(){
	
		String sqlLmHome = "update team_dic t, videos m set m.home_std_md5 = t.std_md5 where m.home_std_md5 is null and t.std_md5!='0' and t.team_name = m.home_team";
		Db.update(sqlLmHome);
		String sqlLmAway = "update team_dic t, videos m set m.away_std_md5 = t.std_md5 where m.away_std_md5 is null and t.std_md5!='0' and t.team_name = m.away_team ";
		Db.update(sqlLmAway);
		
		//剩下没有的插入到team_dic表中
		List<Videos> listHistory = Videos.dao.find("select home_team, away_team, home_std_md5, away_std_md5 from videos where home_std_md5 is null or away_std_md5 is null ");
		for(Videos history : listHistory){
			if(StringUtils.isNotBlank(history.getStr("home_team")) && StringUtils.isNotBlank(history.getStr("away_team"))) {
				commonAddTeamDic(history.getStr("home_std_md5"), history.getStr("home_team"), history.getStr("match_title"));
				commonAddTeamDic(history.getStr("away_std_md5"), history.getStr("away_team"), history.getStr("match_title"));
			}
		}
	}
	
	
	/**
	 * 以team_dic为标准,更新tips_match中的home_std_md5和away_std_md5
	 */
	public static void setTipsMatchStdMd5(){
	
		String sqlLmHome = "update team_dic t, tips_match m set m.home_std_md5 = t.std_md5 where m.home_std_md5 is null and t.std_md5!='0' and t.team_name = m.home_name";
		Db.update(sqlLmHome);
		String sqlLmAway = "update team_dic t, tips_match m set m.away_std_md5 = t.std_md5 where m.away_std_md5 is null and t.std_md5!='0' and t.team_name = m.away_name ";
		Db.update(sqlLmAway);
		
		//剩下没有的插入到team_dic表中
		List<TipsMatch> listHistory = TipsMatch.dao.find("select home_name, away_name, home_std_md5, away_std_md5, league_name from tips_match where home_std_md5 is null or away_std_md5 is null ");
		for(TipsMatch history : listHistory){
			commonAddTeamDic(history.getStr("home_std_md5"), history.getStr("home_team"), history.getStr("league_name"));
			commonAddTeamDic(history.getStr("away_std_md5"), history.getStr("away_team"), history.getStr("league_name"));
		}
	}
	
	
	/**
	 * 以team_dic为标准,更新match_live中的home_std_md5和away_std_md5
	 */
	public static void setAllLiveMatchStdMd5(){
	
		String sqlLmHome = "update team_dic t, all_live_match m set m.home_std_md5 = t.std_md5 where m.home_std_md5 is null and t.std_md5!='0' and t.team_name = m.home_team_name";
		Db.update(sqlLmHome);
		String sqlLmAway = "update team_dic t, all_live_match m set m.away_std_md5 = t.std_md5 where m.away_std_md5 is null and t.std_md5!='0' and t.team_name = m.away_team_name ";
		Db.update(sqlLmAway);
		
		//剩下没有的插入到team_dic表中
		List<MatchLive> listHistory = MatchLive.dao.find("select home_team_name, away_team_name, home_std_md5, away_std_md5, league_name  from all_live_match where home_std_md5 is null or away_std_md5 is null ");
		for(MatchLive history : listHistory){
			commonAddTeamDic(history.getStr("home_std_md5"), history.getStr("home_team_name"), history.getStr("league_name"));
			commonAddTeamDic(history.getStr("away_std_md5"), history.getStr("away_team_name"), history.getStr("league_name"));
		}
	}
	
	/**
	 * 通用新增TeamDic字典
	 * @param stdMd5
	 * @param teamName
	 */
	private static void commonAddTeamDic(String stdMd5, String teamName, String leagueName){
		if(StringUtils.isBlank(stdMd5) && StringUtils.isNotBlank(teamName)){
			TeamDic teamDicDB = TeamDic.dao.findFirst("select * from team_dic where team_name = ?", teamName);
			if(teamDicDB==null){
				TeamDic teamDic = new TeamDic();
				teamDic.set("team_name", teamName);
				teamDic.set("md5", CommonUtils.md5(teamName));
				teamDic.set("league_name", leagueName);
				teamDic.save();
			}else {
				teamDicDB.set("league_name", leagueName);
				teamDicDB.update();
			}
		}
	}
	
	public static void main(String[] args) {
		collectTeams();
		
//		Connection connect = Jsoup.connect("https://liansai.500.com/zuqiu-3984/teams/");
//		Connection data = connect.data(MatchUtils.getCP500Header("https://liansai.500.com/zuqiu-3984/"));
//		try {
//			Document document = data.get();
//			System.err.println(document.html());
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
	
}
