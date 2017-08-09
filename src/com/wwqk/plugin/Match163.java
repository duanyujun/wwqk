package com.wwqk.plugin;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchSource;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.EnumUtils;

public class Match163 {
	
	public static void archiveMatch(Map<String, String> nameIdMap, Map<String, String> nameENNameMap){
		List<MatchSource> lstMatchSources = MatchSource.dao.find("select * from match_source");
		//http://goal.sports.163.com/39/schedule/team/0_17_2016.html
		for(MatchSource source : lstMatchSources){
			Map<String, LeagueMatchHistory> currentMap = null;
			for(int i=1; i<=source.getInt("current_round"); i++){
				List<LeagueMatchHistory> lstNeedInsert = new ArrayList<LeagueMatchHistory>();
				List<LeagueMatchHistory> lstNeedUpdate = new ArrayList<LeagueMatchHistory>();
				LeagueMatchHistory historyExist = LeagueMatchHistory.dao.findFirst("select * from league_match_history where league_id =? and year=? and round=?", source.getStr("league_id"), source.getInt("year"), i);
				String matchUrl = "http://goal.sports.163.com/"+source.getStr("league_id_163")+"/schedule/team/0_"+i+"_"+source.getInt("year")+".html";
				if(historyExist==null){
					Map<String, LeagueMatchHistory> map = getMatchHistory(matchUrl, source, nameIdMap, nameENNameMap);
					lstNeedInsert.addAll(map2List(map));
					if(i == source.getInt("current_round")){
						currentMap = map;
					}
				}else{
					//更新当前轮历史数据
					if(i == source.getInt("current_round")){
						currentMap = getMatchHistory(matchUrl, source, nameIdMap, nameENNameMap);
						lstNeedUpdate.addAll(map2List(currentMap));
					}
				}
				
				saveOneRound(lstNeedInsert, lstNeedUpdate);
			}
			
			//处理当前轮次：1、更新联赛当前赛事；2、是否需要将当前轮次+1；
			if(currentMap!=null){
				List<LeagueMatch> lstMatch = new ArrayList<LeagueMatch>();
				for(Entry<String, LeagueMatchHistory> entry : currentMap.entrySet()){
					LeagueMatch match = new LeagueMatch();
					match.set("match_date", entry.getValue().getDate("match_date"));
					match.set("home_team_id", entry.getValue().getStr("home_team_id"));
					match.set("home_team_name", entry.getValue().getStr("home_team_name"));
					match.set("away_team_id", entry.getValue().getStr("away_team_id"));
					match.set("away_team_name", entry.getValue().getStr("away_team_name"));
					match.set("home_team_en_name", nameENNameMap.get(entry.getValue().getStr("home_team_name")));
					match.set("away_team_en_name", nameENNameMap.get(entry.getValue().getStr("away_team_name")));
					match.set("match_weekday", DateTimeUtils.formatDate2WeekDay(entry.getValue().getDate("match_date")) );
					match.set("result", entry.getValue().getStr("result"));
					match.set("league_id", entry.getValue().getStr("league_id"));
					match.set("round", entry.getValue().getStr("round"));
					match.set("status", entry.getValue().getStr("status"));
					match.set("update_time", new Date());
					lstMatch.add(match);
				}
				
				Db.update("delete from league_match where league_id = ? ", source.getStr("league_id"));
				Db.batchSave(lstMatch, lstMatch.size());
				
				//是否需要将当前轮次+1；
				boolean updateCurrentRound = true;
				for(LeagueMatch match : lstMatch){
					if(!match.getStr("result").contains("-") && match.getDate("match_date").after(new Date())){
						updateCurrentRound = false;
					}
				}
				if(updateCurrentRound){
					int currentRound = source.getInt("current_round");
					source.set("current_round", currentRound+1>source.getInt("round_max")?source.getInt("round_max"):currentRound+1);
					source.update();
				}
			}
		}
	}
	
	private static Map<String, LeagueMatchHistory> getMatchHistory(String matchUrl, MatchSource source, 
			Map<String, String> nameIdMap, Map<String, String> nameENNameMap){
		Map<String, LeagueMatchHistory> map = new HashMap<String, LeagueMatchHistory>();
		try {
			Document document = Jsoup.connect(matchUrl).get();
			Elements matchAreaElements = document.select(".leftList4");
			if(matchAreaElements.size()>0){
				Elements trElements = matchAreaElements.get(0).select("tr");
				for(Element element : trElements){
					if(element.attr("id")!=null && element.attr("id").contains("hide_")){
						continue;
					}
					Elements tdElements = element.select("td");
					if(tdElements.size()>0){
						LeagueMatchHistory history = new LeagueMatchHistory();
						history.set("round", tdElements.get(0).text());
						history.set("match_date", DateTimeUtils.parseDate(tdElements.get(1).text(), DateTimeUtils.ISO_DATETIME_NOSEC_FORMAT_ARRAY));
						history.set("home_team_id", nameIdMap.get(tdElements.get(3).text()));
						history.set("home_team_name", tdElements.get(3).text());
						history.set("away_team_id", nameIdMap.get(tdElements.get(5).text()));
						history.set("away_team_name", tdElements.get(5).text());
						history.set("home_team_en_name", nameENNameMap.get(tdElements.get(3).text()));
						history.set("away_team_en_name", nameENNameMap.get(tdElements.get(5).text()));
						
						LeagueMatchHistory historyDb = LeagueMatchHistory.dao.findFirst("select * from league_match_history where home_team_id=? and away_team_id=? and round=? and year=?",
								nameIdMap.get(tdElements.get(3).text()), nameIdMap.get(tdElements.get(5).text()), tdElements.get(0).text(), source.getInt("year"));
						if(historyDb==null){
							history.set("status", tdElements.get(2).text());
							if(!tdElements.get(4).text().contains("-")){
								history.set("result", tdElements.get(1).text().substring(tdElements.get(1).text().indexOf(" ")+1));
							}else{
								history.set("result", tdElements.get(4).text().replace("-", " - "));
							}
						}else if(historyDb!=null){
							if(!"完场".equals(historyDb.get("status"))){
								history.set("status", tdElements.get(2).text());
								if(!tdElements.get(4).text().contains("-")){
									history.set("result", tdElements.get(1).text().substring(tdElements.get(1).text().indexOf(" ")+1));
								}else{
									history.set("result", tdElements.get(4).text().replace("-", " - "));
								}
							}else{
								history.set("status", historyDb.get("status"));
								history.set("result", historyDb.get("result"));
							}
						}
						
						history.set("league_id", source.getStr("league_id"));
						history.set("league_name", EnumUtils.getValue(LeagueEnum.values(), source.getStr("league_id")));
						history.set("year", source.getInt("year"));
						if(map.get(tdElements.get(3).text())==null){
							map.put(tdElements.get(3).text(), history);
						}else{
							//判断是否有重复的
							if("完场".equals(tdElements.get(2).text())){
								map.put(tdElements.get(3).text(), history);
							}
						}
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private static List<LeagueMatchHistory> map2List(Map<String, LeagueMatchHistory> map){
		List<LeagueMatchHistory> lstHistory = new ArrayList<LeagueMatchHistory>();
		for(Entry<String, LeagueMatchHistory> entry:map.entrySet()){
			LeagueMatchHistory history = entry.getValue();
			history.set("id", DateTimeUtils.formatDate(history.getDate("match_date"))+"-"+history.getStr("home_team_id")+"vs"+history.getStr("away_team_id"));
			history.set("match_weekday", DateTimeUtils.formatDate2WeekDay(history.getDate("match_date")));
			lstHistory.add(history);
		}
		return lstHistory;
	}
	
	@Before(Tx.class)
	private static void saveOneRound(List<LeagueMatchHistory> lstNeedInsert, List<LeagueMatchHistory> lstNeedUpdate){
		if(lstNeedInsert.size()>0){
			Db.batchSave(lstNeedInsert, lstNeedInsert.size());
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}

}
