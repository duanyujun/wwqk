package com.wwqk.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.StringUtils;

public class Live24zbw {
	
	private static final String SITE_URL = "http://www.24zbw.com/";
	

	public static void getLiveSource() {
		Map<String, String> leagueMap = new HashMap<String, String>();
		leagueMap.put("英超", "1");
		leagueMap.put("西甲", "2");
		leagueMap.put("德甲", "3");
		leagueMap.put("意甲", "4");
		leagueMap.put("法甲", "5");
		CommonUtils.initNameIdMap();
		Connection connect = Jsoup.connect(SITE_URL).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.get24zbwHeader());
		try {
			Document document = data.get();
			Elements matchBody = document.select(".match-body");
			if(matchBody.size()>0){
				Element body = matchBody.get(0);
				String[] groupMatches = body.html().split("match-turns\">");
				for(String group:groupMatches){
					if(!group.contains("match-item")){
						continue;
					}
					//08月25日 星期五
					String dateAllStr = group.substring(2,group.indexOf("</div>"));
					String dateStr = dateAllStr.split(" ")[0];
					String weekStr = dateAllStr.split(" ")[1];
					group = "<div>"+group;
					Document matchDoc = Jsoup.parse(group);
					Elements matchItems = matchDoc.select(".match-item");
					if(matchItems.size()>0){
						//获取赛季
						MatchSourceSina matchSourceSina = MatchSourceSina.dao.findFirst("select * from match_source_sina");
						String yearShow = matchSourceSina.getStr("year_show");
						for(Element element : matchItems){
							if(element.select(".match-competition").size()==0){
								continue;
							}
							if(element.text().contains("已结束")){
								continue;
							}
							
							String leagueName = element.select(".match-competition").get(0).text();
							if(leagueName.contains("抽签")){
								continue;
							}
							
							String timeStr = StringUtils.trim(element.child(0).text());
							Date matchDateTime = DateTimeUtils.getMatchDate(dateStr+" "+timeStr);
							if(matchDateTime.before(new Date())){
								continue;
							}
							List<MatchLive> lstMatchLives = new ArrayList<MatchLive>();
							Elements homeAwayNames = element.select(".match-title");
							String homeTeamName = StringUtils.trim(homeAwayNames.get(0).text());
							String awayTeamName = StringUtils.trim(homeAwayNames.get(1).text());
							String homeTeamId = CommonUtils.nameIdMap.get(homeTeamName);
							String awayTeamId = CommonUtils.nameIdMap.get(awayTeamName);
							Date dateVilidate = DateTimeUtils.addDays(new Date(), -2);
							AllLiveMatch allLiveMatch = AllLiveMatch.dao.findFirst(
									"select * from all_live_match where home_team_name = ? and away_team_name = ? and year_show = ? and match_datetime > ? ",
									homeTeamName, awayTeamName, yearShow, dateVilidate);
							boolean isNeedInsert = false;
							if(allLiveMatch==null){
								if(StringUtils.isNotBlank(homeTeamId) && StringUtils.isNotBlank(awayTeamId)){
									//因为本系统已经替换了主客队名称，需再次查询一下主客队id
									allLiveMatch = AllLiveMatch.dao.findFirst(
											"select * from all_live_match where home_team_id = ? and away_team_id = ? and year_show = ? and match_datetime > ? ",
											homeTeamId, awayTeamId, yearShow, dateVilidate);
									if(allLiveMatch==null){
										isNeedInsert = true;
										allLiveMatch = new AllLiveMatch();
									}
								}else{
									isNeedInsert = true;
									allLiveMatch = new AllLiveMatch();
								}
							}
							allLiveMatch.set("match_date_week", dateAllStr);
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
							}
							allLiveMatch.set("update_time", new Date());
							if(isNeedInsert){
								allLiveMatch.save();
								if(StringUtils.isBlank(allLiveMatch.getStr("match_key"))){
									allLiveMatch.set("match_key", allLiveMatch.get("id"));
								}
							}
							allLiveMatch.update();
							
							Elements lives = element.select(".live-item-source");
							if(lives.size()>0){
								for(Element elementLive:lives){
									MatchLive matchLive = new MatchLive();
									String liveName = StringUtils.trim(elementLive.text().replace("(推荐)", ""));
									String liveUrl = elementLive.attr("href");
									if(liveName.contains(" ") || "http://www.24zbw.com".equals(liveUrl)){
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
	
}
