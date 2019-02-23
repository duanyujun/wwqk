package com.wwqk.plugin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wwqk.model.TeamDic;
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
				System.err.println(leagueName);
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
					System.err.println("std_name"+stdTeamName);
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
	
	public static void main(String[] args) {
		collectTeams();
		//System.err.println(md5("12345"));
	}
	
}
