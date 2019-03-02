package com.wwqk.plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchLive;
import com.wwqk.model.TeamDic;
import com.wwqk.model.Videos;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.PinyinUtils;
import com.wwqk.utils.SpecialNameUtils;
import com.wwqk.utils.StringUtils;

public class OkoooTeam {

	private static final String SITE_URL = "http://www.okooo.com/soccer/";
	private static final Pattern TEAM_PATTERN = Pattern.compile("/soccer/team/(\\d+)/.*?>(.*?)</a>");
	private static final String HOST = "http://www.okooo.com";
	
	public static void collectTeams(){
		
		Connection connect = Jsoup.connect(SITE_URL).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.getOkoooHeader("http://www.okooo.com/"));
		try {
			Document document = data.get();
			//allHtml = allHtml.substring(0, allHtml.indexOf("<!--   洲际赛事"));
			String leagueUrl = "";
			String leagueName = "";
			String teamsHtml = "";
			String teamId = "";
			String teamName = "";
			String stdTeamName = "";
			Matcher teamMatcher = null;
			Set<String> teamIdSet = new HashSet<>();
			
			Elements elements = document.select(".MatchShowOff");
			for(Element teamElement : elements){
				leagueUrl = HOST + teamElement.attr("onClick").replace("window.open('", "").replace("');", "");
				leagueName = teamElement.text();
				if(StringUtils.isBlank(leagueName)){
					continue;
				}
				
				connect = Jsoup.connect(leagueUrl).ignoreContentType(true);
				data = connect.data(MatchUtils.getOkoooHeader("http://www.okooo.com/soccer/"));
				document = data.get();
				Element teamsElement = document.select(".LotteryList_Data").last();
				teamsHtml = teamsElement.html();
				teamMatcher = TEAM_PATTERN.matcher(teamsHtml);
				while (teamMatcher.find()) {
					teamId = teamMatcher.group(1);
					if(teamIdSet.contains(teamId)){
						continue;
					}else{
						teamIdSet.add(teamId);
					}
					
					teamName = teamMatcher.group(2).replace("\\s+", "");
					TeamDic teamDic = new TeamDic();
					teamDic.set("ok_id", teamId);
					teamDic.set("team_name", teamName);
					teamDic.set("md5", CommonUtils.md5(teamName));
					stdTeamName = SpecialNameUtils.getStdName(teamName);
					
					if(stdTeamName.contains("img")){
						continue;
					}
					teamDic.set("std_name", stdTeamName);
					teamDic.set("std_md5", CommonUtils.md5(stdTeamName));
					teamDic.set("std_name_py", PinyinUtils.getPingYin(stdTeamName));
					teamDic.set("league_name", leagueName);
					TeamDic teamDicDB = TeamDic.dao.findFirst("select * from team_dic where ok_id = ?", teamId);
					
					if(teamDicDB!=null){
						teamDic.set("id", teamDicDB.get("id"));
						teamDic.update();
					}else{
						teamDic.save();
					}
				}
				Thread.sleep(800);
			}
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
	
	public static void setOtherStdMd5(){
		//setLeagueMatchStdMd5();
		setMatchLiveStdMd5();
		//setVideosStdMd5();
		//setTipsMatchStdMd5();
		//setAllLiveMatchStdMd5();
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
		List<LeagueMatchHistory> listHistory = LeagueMatchHistory.dao.find("select home_team_name, away_team_name, home_std_md5, away_std_md5 from league_match_history where home_std_md5 is null or away_std_md5 is null ");
		for(LeagueMatchHistory history : listHistory){
			commonAddTeamDic(history.getStr("home_std_md5"), history.getStr("home_team_name"));
			commonAddTeamDic(history.getStr("away_std_md5"), history.getStr("away_team_name"));
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
		List<MatchLive> listHistory = MatchLive.dao.find("select home_team_name, away_team_name, home_std_md5, away_std_md5 from match_live where home_std_md5 is null or away_std_md5 is null ");
		for(MatchLive history : listHistory){
			commonAddTeamDic(history.getStr("home_std_md5"), history.getStr("home_team_name"));
			commonAddTeamDic(history.getStr("away_std_md5"), history.getStr("away_team_name"));
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
			commonAddTeamDic(history.getStr("home_std_md5"), history.getStr("home_team"));
			commonAddTeamDic(history.getStr("away_std_md5"), history.getStr("away_team"));
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
		List<Videos> listHistory = Videos.dao.find("select home_name, away_name, home_std_md5, away_std_md5 from tips_match where home_std_md5 is null or away_std_md5 is null ");
		for(Videos history : listHistory){
			commonAddTeamDic(history.getStr("home_std_md5"), history.getStr("home_team"));
			commonAddTeamDic(history.getStr("away_std_md5"), history.getStr("away_team"));
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
		List<MatchLive> listHistory = MatchLive.dao.find("select home_team_name, away_team_name, home_std_md5, away_std_md5 from all_live_match where home_std_md5 is null or away_std_md5 is null ");
		for(MatchLive history : listHistory){
			commonAddTeamDic(history.getStr("home_std_md5"), history.getStr("home_team_name"));
			commonAddTeamDic(history.getStr("away_std_md5"), history.getStr("away_team_name"));
		}
	}
	
	/**
	 * 通用新增TeamDic字典
	 * @param stdMd5
	 * @param teamName
	 */
	private static void commonAddTeamDic(String stdMd5, String teamName){
		if(StringUtils.isBlank(stdMd5)){
			TeamDic teamDicDB = TeamDic.dao.findFirst("select * from team_dic where team_name = ?", teamName);
			if(teamDicDB==null){
				TeamDic teamDic = new TeamDic();
				teamDic.set("team_name", teamName);
				teamDic.set("md5", CommonUtils.md5(teamName));
				teamDic.save();
			}
		}
	}
	
	public static void main(String[] args) {
		collectTeams();
		//System.err.println(md5("12345"));
	}
	
}
