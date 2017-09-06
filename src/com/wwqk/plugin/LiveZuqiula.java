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
import com.wwqk.model.MatchLive;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.StringUtils;

public class LiveZuqiula {
	
	private static final String SITE_URL = "http://www.zuqiu.la";

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
		leagueMap.put("中甲", "6");
		leagueMap.put("世预赛", "7");
		leagueMap.put("世亚预", "8");
		leagueMap.put("世欧预", "9");
		leagueMap.put("篮球", "10");
		leagueMap.put("排球", "11");
		
		//CommonUtils.initNameIdMap();
		Connection connect = Jsoup.connect(SITE_URL).ignoreContentType(true);
		for(Map.Entry<String, String> entry:MatchUtils.getZuqiulaHeader().entrySet()){
			connect.header(entry.getKey(), entry.getValue());
		}
		Connection data = connect.data();
		try {
			Document document = data.get();
			//System.err.println(document.html());
			Elements groupElements = document.select(".col_02");
			for(Element element : groupElements){
				String timeGroupStr = StringUtils.trim(element.child(0).text());
				if(!timeGroupStr.contains("星期") && !timeGroupStr.contains("直播节目列表")){
					continue;
				}
				int firstWhiteSpace = timeGroupStr.indexOf(" ");
				//08-28
				String dateStr = StringUtils.trim(timeGroupStr.substring(0,firstWhiteSpace));
				//星期一
				String weekDayStr = StringUtils.trim(timeGroupStr.substring(firstWhiteSpace+1,timeGroupStr.lastIndexOf(" ")));
				Elements matchItems = element.select("li");
				for(Element item:matchItems){
					String leagueName = StringUtils.trim(item.select("a").get(0).text());
					if(leagueMap.get(leagueName)!=null 
							|| item.text().contains("篮球") 
							|| item.text().contains("排球")
							|| item.text().contains("美网")){
						continue;
					}
					String timeStr = StringUtils.trim(item.select("em").get(0).text());
					String teamName = StringUtils.trim(item.select("a").get(1).text());
					if(!teamName.contains("vs")){
						continue;
					}
					if(teamName.contains(" ")){
						leagueName = StringUtils.trim(teamName.substring(0, teamName.indexOf(" ")));
						teamName = StringUtils.trim(teamName.substring(teamName.indexOf(" ")+1));
					}
					String homeTeamName = teamName.split("vs")[0];
					String awayTeamName = teamName.split("vs")[1];
					MatchSourceSina matchSourceSina = MatchSourceSina.dao.findFirst("select * from match_source_sina");
					String yearShow = matchSourceSina.getStr("year_show");
					Date matchDateTime = DateTimeUtils.getZuqiulaMatchDate(dateStr+" "+timeStr);
					if(matchDateTime.before(new Date())){
						continue;
					}
					List<MatchLive> lstMatchLives = new ArrayList<MatchLive>();
					Date dateVilidate = DateTimeUtils.addDays(new Date(), -2);
					AllLiveMatch allLiveMatch = AllLiveMatch.dao.findFirst(
							"select * from all_live_match where home_team_name = ? and away_team_name = ? and year_show = ? and match_datetime > ? ",
							homeTeamName, awayTeamName, yearShow, dateVilidate);
					boolean isNeedInsert = false;
					if(allLiveMatch==null){
						isNeedInsert = true;
						allLiveMatch = new AllLiveMatch();
					}
					String dateAllStr = dateStr.replace("-", "月")+"日 "+weekDayStr;
					allLiveMatch.set("match_date_week", dateAllStr);
					allLiveMatch.set("weekday", weekDayStr);
					allLiveMatch.set("match_datetime", matchDateTime);
					allLiveMatch.set("league_name", leagueName);
					allLiveMatch.set("home_team_name", homeTeamName);
					allLiveMatch.set("away_team_name", awayTeamName);
					allLiveMatch.set("year_show", yearShow);
					allLiveMatch.set("update_time", new Date());
					if(isNeedInsert){
						allLiveMatch.save();
						if(StringUtils.isBlank(allLiveMatch.getStr("match_key"))){
							allLiveMatch.set("match_key", allLiveMatch.get("id"));
						}
					}
					allLiveMatch.update();
					
					Elements liveGroup = item.select(".con");
					if(liveGroup.size()>0){
						Elements liveItems = liveGroup.get(0).select("a");
						for(Element live:liveItems){
							String liveName = StringUtils.trim(live.text());
							if(liveName.contains("足球比分直播")){
								continue;
							}
							MatchLive matchLive = new MatchLive();
							matchLive.set("match_key", String.valueOf(allLiveMatch.get("match_key")));
							matchLive.set("home_team_name", homeTeamName);
							matchLive.set("away_team_name", awayTeamName);
							matchLive.set("live_name", liveName);
							matchLive.set("live_url",SITE_URL+live.attr("href"));
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
	
}