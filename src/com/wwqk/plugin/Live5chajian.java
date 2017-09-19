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
import com.wwqk.model.League;
import com.wwqk.model.MatchLive;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.PinyinUtils;
import com.wwqk.utils.StringUtils;

public class Live5chajian {
	
	private static final String SITE_URL = "http://www.5chajian.com";
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
		CommonUtils.initNameIdMap();
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
				Elements matchTrs = matchDoc.select(".against");
				for(Element tr:matchTrs){
					if(!"足球".equals(tr.child(0).attr("title"))){
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
					String leagueName = null;
					String homeTeamName = null;
					String awayTeamName = null;
					if("3".equals(tr.child(4).attr("colspan"))){
						String tempStr = StringUtils.trim(tr.child(4).text());
						if(!tempStr.contains("vs")){
							continue;
						}
						int spaceIndex = tempStr.indexOf(" ");
						leagueName = tempStr.substring(0, spaceIndex);
						String homeAwayTeamStr = StringUtils.trim(tempStr.substring(spaceIndex+1));
						homeTeamName = StringUtils.trim(homeAwayTeamStr.split("vs")[0]);
						awayTeamName = StringUtils.trim(homeAwayTeamStr.split("vs")[1]);
					}else{
						leagueName = StringUtils.trim(tr.child(1).text());
						homeTeamName = StringUtils.trim(tr.child(4).text());
						awayTeamName = StringUtils.trim(tr.child(6).text());
					}
					
					List<MatchLive> lstMatchLives = new ArrayList<MatchLive>();
					String homeTeamId = CommonUtils.nameIdMap.get(homeTeamName);
					String awayTeamId = CommonUtils.nameIdMap.get(awayTeamName);
					AllLiveMatch allLiveMatch = AllLiveMatch.dao.findFirst(
							"select * from all_live_match where home_team_name = ? and away_team_name = ? and year_show = ? and match_datetime > ? ",
							homeTeamName, awayTeamName, yearShow, nowDate);
					boolean isNeedInsert = false;
					if(allLiveMatch==null){
						if(StringUtils.isNotBlank(homeTeamId) && StringUtils.isNotBlank(awayTeamId)){
							//因为本系统已经替换了主客队名称，需再次查询一下主客队id
							allLiveMatch = AllLiveMatch.dao.findFirst(
									"select * from all_live_match where home_team_id = ? and away_team_id = ? and year_show = ? and match_datetime > ? ",
									homeTeamId, awayTeamId, yearShow, nowDate);
							if(allLiveMatch==null){
								isNeedInsert = true;
								allLiveMatch = new AllLiveMatch();
							}
						}else{
							isNeedInsert = true;
							allLiveMatch = new AllLiveMatch();
						}
					}
					allLiveMatch.set("match_date_week", dateStr+" "+weekStr);
					allLiveMatch.set("weekday", weekStr);
					allLiveMatch.set("match_datetime", matchDateTime);
					allLiveMatch.set("league_name", leagueName);
					allLiveMatch.set("home_team_name", homeTeamName);
					allLiveMatch.set("away_team_name", awayTeamName);
					allLiveMatch.set("year_show", yearShow);
					String leagueId = leagueMap.get(leagueName);
					if(StringUtils.isNotBlank(leagueId)){
						if(StringUtils.isNotBlank(homeTeamId) && StringUtils.isNotBlank(awayTeamId)){
							Team home = Team.dao.findById(homeTeamId);
							Team away = Team.dao.findById(awayTeamId);
							allLiveMatch.set("home_team_id", homeTeamId);
							allLiveMatch.set("away_team_id", awayTeamId);
							allLiveMatch.set("league_id", leagueId);
							League league = League.dao.findById(leagueId);
							allLiveMatch.set("league_enname", league.getStr("name_en"));
							//使用本系统球队名称
							allLiveMatch.set("home_team_name", home.getStr("name"));
							allLiveMatch.set("away_team_name", away.getStr("name"));
							allLiveMatch.set("home_team_enname", home.getStr("name_en"));
							allLiveMatch.set("away_team_enname", away.getStr("name_en"));
							allLiveMatch.set("match_key", yearShow+"-"+homeTeamId+"vs"+awayTeamId);
						}
					}else{
						allLiveMatch.set("home_team_enname", PinyinUtils.getPingYin(homeTeamName));
						allLiveMatch.set("away_team_enname", PinyinUtils.getPingYin(awayTeamName));
					}
					
					allLiveMatch.set("update_time", new Date());
					if(isNeedInsert){
						allLiveMatch.save();
						if(StringUtils.isBlank(allLiveMatch.getStr("match_key"))){
							allLiveMatch.set("match_key", allLiveMatch.get("id"));
						}
					}
					allLiveMatch.update();
					
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
							if(CommonUtils.nameIdMap.get(homeTeamName)!=null && CommonUtils.nameIdMap.get(awayTeamName)!=null){
								matchLive.set("match_key", yearShow+"-"+CommonUtils.nameIdMap.get(homeTeamName)+"vs"+CommonUtils.nameIdMap.get(awayTeamName));
								matchLive.set("home_team_id", CommonUtils.nameIdMap.get(homeTeamName));
								matchLive.set("away_team_id", CommonUtils.nameIdMap.get(awayTeamName));
								matchLive.set("league_id", leagueMap.get(leagueName));
							}else{
								matchLive.set("match_key", String.valueOf(allLiveMatch.get("match_key")));
							}
							matchLive.set("home_team_name", homeTeamName);
							matchLive.set("away_team_name", awayTeamName);
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
			List<MatchLive> lstDBLive = MatchLive.dao.find("select * from match_live where match_key = ?", lstMatchLive.get(0).getStr("match_key"));
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