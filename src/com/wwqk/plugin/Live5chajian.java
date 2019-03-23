package com.wwqk.plugin;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.MatchLive;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.model.Team;
import com.wwqk.model.TeamDic;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.PinyinUtils;
import com.wwqk.utils.StringUtils;

public class Live5chajian {
	
	private static final String SITE_URL = "http://www.wuchajian.net";
	private static final Pattern datePattern = Pattern.compile("\\d+年\\d+月\\d+日");
	
	public static void main(String[] args) {
		getLiveSource();
	}

	
	public static void getLiveSource() {
		Map<String, String> leagueMap = new HashMap<String, String>();
		leagueMap.put("英超", "1");
		leagueMap.put("西甲", "2");
		leagueMap.put("德甲", "3");
		leagueMap.put("意甲", "4");
		leagueMap.put("法甲", "5");
		
		//查询team_dic
		List<TeamDic> teamDics = TeamDic.dao.find("select team_name, ok_id, std_md5, std_name, std_name_py, league_name from team_dic where std_md5 != '0'");
		Map<String, TeamDic> teamDicMap = new HashMap<>();
		for(TeamDic teamDic : teamDics) {
			teamDicMap.put(teamDic.getStr("team_name"), teamDic);
		}
		//查询五大联赛
		List<Team> teams = Team.dao.find("select id, league_id, std_md5 from team");
		Map<String, Team> teamMap = new HashMap<>();
		for(Team team : teams) {
			teamMap.put(team.getStr("team_name"), team);
		}
		
		Connection connect = Jsoup.connect(SITE_URL).ignoreContentType(true);
		try {
			Document document = connect.get();
			MatchSourceSina matchSourceSina = MatchSourceSina.dao.findFirst("select * from match_source_sina");
			String yearShow = matchSourceSina.getStr("year_show");
			String[] matchArray = document.html().split("<tr class=\"date\">");
			for(String matchHtml:matchArray){
				Matcher matcher = datePattern.matcher(matchHtml);
				if(!matcher.find()){
					continue;
				}
				//获取赛季
				Document matchDoc = getCompleteDocument(matchHtml);
				String dateAndWeekStr = StringUtils.trim(matchDoc.select(".date").get(0).child(0).text());
				//2017年09月18日
				String dateStr = dateAndWeekStr.substring(0,11);
				dateStr = dateStr.substring(5);
				String weekStr = dateAndWeekStr.substring(dateAndWeekStr.length()-3);
				
				String homeId = null;
				String awayId = null;
				TeamDic homeTeamDic = null;
				TeamDic awayTeamDic = null;
				Team homeTeam = null;
				Team awayTeam = null;
				TeamDic teamDicDB = null;
				String homeStdMd5 = null;
				String awayStdMd5 = null;
				
				Elements matchTrs = matchDoc.select(".against");
				for(Element tr:matchTrs){
					if(!"足球".equals(tr.child(0).attr("title"))){
						continue;
					}
					if(tr.select(".status_close").size()!=0) {
						continue;
					}
					
					//<td class='tixing' name='2' t='2017-09-18 21:30'></td>
					String dateTimeStr = StringUtils.trim(tr.select(".tixing").get(0).attr("t"));
					Date matchDateTime = null;
					try {
						matchDateTime = DateTimeUtils.parseDate(dateTimeStr, DateTimeUtils.ISO_DATETIME_NOSEC_FORMAT_ARRAY);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date nowDate = DateTimeUtils.addHours(new Date(), -2);
					if(matchDateTime.before(nowDate)){
						continue;
					}
					
					//复位
					homeId = null;
					awayId = null;
					homeTeamDic = null;
					awayTeamDic = null;
					homeTeam = null;
					awayTeam = null;
					teamDicDB = null;
					String leagueName = null;
					String homeTeamName = null;
					String awayTeamName = null;
					homeStdMd5 = null;
					awayStdMd5 = null;
					
					if("3".equals(tr.child(4).attr("colspan"))){
						String[] tempArray = tr.child(4).text().split("vs");
						String[] leagueHomeArray = tempArray[0].split("\\s+");
						leagueName = StringUtils.trim(leagueHomeArray[0]);
						homeTeamName = StringUtils.trim(leagueHomeArray[1]);
						awayTeamName = StringUtils.trim(tempArray[1]);
					}else {
						leagueName = StringUtils.trim(tr.child(1).text());
						homeTeamName = StringUtils.trim(tr.child(4).text());
						awayTeamName = StringUtils.trim(tr.child(6).text());
					}
					
					//判断是否已经存在标准名称
					homeTeamDic = teamDicMap.get(homeTeamName);
					if(homeTeamDic!=null) {
						homeTeamName = homeTeamDic.getStr("std_name");
						homeStdMd5 = homeTeamDic.getStr("std_md5");
						homeTeam = teamMap.get(homeTeamDic.getStr("std_md5"));
						if(homeTeam!=null) {
							homeId = homeTeam.get("id").toString();
						}
					}else {
						teamDicDB = TeamDic.dao.findFirst("select * from team_dic where team_name = ?", homeTeamName);
						if(teamDicDB==null) {
							//插入teamDic表
							TeamDic teamDic = new TeamDic();
							teamDic.set("team_name", homeTeamName);
							teamDic.set("md5", CommonUtils.md5(homeTeamName));
							teamDic.set("league_name", leagueName);
							teamDic.save();
						}
					}
					
					awayTeamDic = teamDicMap.get(awayTeamName);
					if(awayTeamDic!=null) {
						awayTeamName = awayTeamDic.getStr("std_name");
						awayStdMd5 = awayTeamDic.getStr("std_md5");
						awayTeam = teamMap.get(awayTeamDic.getStr("std_md5"));
						if(awayTeam!=null) {
							awayId = homeTeam.get("id").toString();
						}
					}else {
						teamDicDB = TeamDic.dao.findFirst("select * from team_dic where team_name = ?", awayTeamName);
						if(teamDicDB==null) {
							//插入teamDic表
							TeamDic teamDic = new TeamDic();
							teamDic.set("team_name", awayTeamName);
							teamDic.set("md5", CommonUtils.md5(awayTeamName));
							teamDic.set("league_name", leagueName);
							teamDic.save();
						}
					}
					
				    boolean isNeedUpdate = false;
					//查询是否需要插入
					AllLiveMatch allLiveMatch = AllLiveMatch.dao.findFirst("select * from all_live_match where home_team_name = ? and match_datetime = ? ", homeTeamName, matchDateTime);
					if(allLiveMatch!=null) {
						isNeedUpdate = true;
					}else {
						allLiveMatch =new AllLiveMatch();
					}
					
					allLiveMatch.set("match_date_week", dateStr+" "+weekStr);
					allLiveMatch.set("weekday", weekStr);
					allLiveMatch.set("match_datetime", matchDateTime);
					allLiveMatch.set("league_name", leagueName);
					allLiveMatch.set("home_team_name", homeTeamName);
					allLiveMatch.set("away_team_name", awayTeamName);
					allLiveMatch.set("year_show", yearShow);
					allLiveMatch.set("home_team_enname", PinyinUtils.getPingYin(homeTeamName));
					allLiveMatch.set("away_team_enname", PinyinUtils.getPingYin(awayTeamName));
					allLiveMatch.set("home_std_md5", homeStdMd5);
					allLiveMatch.set("away_std_md5", awayStdMd5);
					allLiveMatch.set("update_time", new Date());
					if(isNeedUpdate) {
						allLiveMatch.update();
					}else {
						allLiveMatch.save();
					}
					
					String leagueId = leagueMap.get(leagueName);
					if(homeId!=null && awayId!=null && leagueId!=null) {
							allLiveMatch.set("home_team_id", homeId);
							allLiveMatch.set("away_team_id", awayId);
							allLiveMatch.set("league_id", leagueId);
							allLiveMatch.set("match_key", yearShow + "-"
									+ homeId + "vs" + awayId);
					}else {
						allLiveMatch.set("match_key", allLiveMatch.get("id"));
					}
					allLiveMatch.update();
					List<MatchLive> lstMatchLives = new ArrayList<MatchLive>();
					Elements lives = tr.select(".live_link");
					if(lives.size()>0){
						Elements aLinks = lives.get(0).select("a");
						for(Element elementLive:aLinks){
							MatchLive matchLive = new MatchLive();
							String liveName = StringUtils.trim(elementLive.text());
							String liveUrl = elementLive.attr("href");
							if(liveUrl.startsWith("..")){
								liveUrl = SITE_URL+liveUrl.substring(2);
							}else if(!liveUrl.startsWith("http")){
								liveUrl = SITE_URL+liveUrl;
							}
							if(liveName.contains("足球比分") || liveName.contains("更多")){
								continue;
							}
							
							matchLive.set("match_key", allLiveMatch.get("match_key"));
							matchLive.set("home_team_id", homeId);
							matchLive.set("away_team_id", awayId);
							matchLive.set("league_id", leagueId);
							
							matchLive.set("home_team_name", homeTeamName);
							matchLive.set("away_team_name", awayTeamName);
							matchLive.set("home_std_md5", homeStdMd5);
							matchLive.set("away_std_md5", awayStdMd5);
							matchLive.set("live_name", liveName);
							matchLive.set("live_url", liveUrl);
							matchLive.set("match_date", matchDateTime);
							lstMatchLives.add(matchLive);
						}
					}
					
					saveOneMatchLive(lstMatchLives);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Before(Tx.class)
	private static void saveOneMatchLive(List<MatchLive> lstMatchLive){
		if(lstMatchLive.size()>0){
			List<MatchLive> lstDBLive = MatchLive.dao.find("select * from match_live where match_key = ?", lstMatchLive.get(0).get("match_key").toString());
			if(lstDBLive.size()==0){
				Db.batchSave(lstMatchLive, lstMatchLive.size());
			}else{
				//进行判断，频道名称或者链接相同的一律不添加
				Set<String> existSet = new HashSet<String>();
				for(MatchLive liveDB : lstDBLive){
					existSet.add(liveDB.getStr("live_name"));
					existSet.add(liveDB.getStr("live_url"));
				}
				List<MatchLive> lstNeedInsert = new ArrayList<MatchLive>();
				for(MatchLive matchLive : lstMatchLive){
					if(existSet.contains(matchLive.getStr("live_name")) || existSet.contains(matchLive.getStr("live_url"))){
						continue;
					}
					lstNeedInsert.add(matchLive);
				}
				if(lstNeedInsert.size()>0){
					Db.batchSave(lstNeedInsert, lstNeedInsert.size());
				}
			}
		}
	}
	
	
	private static Document getCompleteDocument(String matchHtml){
		String completeHtml = "<html><head><head/><body><table><thead></thead><tbody><tr class=\"date\">"+matchHtml+"</tbody></table></body></html>";
		Document document = Jsoup.parse(completeHtml);
		return document;
	}
	
}
