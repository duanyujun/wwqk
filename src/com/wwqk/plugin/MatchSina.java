package com.wwqk.plugin;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.SinaMatchStatusEnum;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.StringUtils;

public class MatchSina {
	
	public static void archiveMatch(Map<String, String> nameIdMap, Map<String, String> nameENNameMap){
		List<MatchSourceSina> lstMatchSources = MatchSourceSina.dao.find("select * from match_source_sina");
		//http://platform.sina.com.cn/sports_all/client_api?_sport_t_=livecast&_sport_a_=matchesByType&callback=jQuery191029773931918148344_1502525791216&app_key=3571367214&type=4&rnd=2&season=2017&_=1502525791219
		for(MatchSourceSina source:lstMatchSources){
			List<LeagueMatchHistory> lstNeedInsert = new ArrayList<LeagueMatchHistory>();
			List<LeagueMatchHistory> lstNeedUpdate = new ArrayList<LeagueMatchHistory>();
			LeagueMatchHistory historyExist = LeagueMatchHistory.dao.findFirst("select * from league_match_history where league_id =? and year=? and round=? order by match_date desc", source.getStr("league_id"), source.getInt("year"), source.get("current_round"));
			long startMills = System.currentTimeMillis();
			long endMills = startMills + getRandNum(1, 4);
			String oneRoundUrl = "http://platform.sina.com.cn/sports_all/client_api?_sport_t_=livecast&_sport_a_=matchesByType&callback=jQuery1910"+get17Length()
			+"_"+startMills+"&app_key=3571367214&type="+source.getStr("league_id_sina")+"&rnd="+source.get("current_round")+"&season="+source.get("year")+"&_="+endMills;
			List<LeagueMatchHistory> currentRoundMatches = getOneRoundMatch(oneRoundUrl, source, nameIdMap, nameENNameMap);
			if(historyExist==null){
				lstNeedInsert.addAll(currentRoundMatches);
			}else{
				lstNeedUpdate.addAll(currentRoundMatches);
			}
			
			saveOneRound(lstNeedInsert, lstNeedUpdate);
			//处理当前轮次：1、更新联赛当前赛事；2、是否需要将当前轮次+1；
			if(currentRoundMatches.size()>0){
				Date lastDateInOneRound = null;
				List<LeagueMatch> lstMatch = new ArrayList<LeagueMatch>();
				for(LeagueMatchHistory history : currentRoundMatches){
					if(lastDateInOneRound==null || lastDateInOneRound.before(history.getDate("match_date"))){
						lastDateInOneRound = history.getDate("match_date");
					}
					LeagueMatch match = new LeagueMatch();
					match.set("match_date", history.getDate("match_date"));
					match.set("home_team_id", history.getStr("home_team_id"));
					match.set("home_team_name", history.getStr("home_team_name"));
					match.set("away_team_id", history.getStr("away_team_id"));
					match.set("away_team_name", history.getStr("away_team_name"));
					match.set("home_team_en_name", nameENNameMap.get(history.getStr("home_team_name")));
					match.set("away_team_en_name", nameENNameMap.get(history.getStr("away_team_name")));
					match.set("match_weekday", DateTimeUtils.formatDate2WeekDay(history.getDate("match_date")) );
					match.set("result", history.getStr("result"));
					match.set("league_id", history.getStr("league_id"));
					match.set("year", history.getInt("year"));
					match.set("year_show", source.getStr("year_show"));
					match.set("round", history.getInt("round"));
					match.set("status", history.getStr("status"));
					match.set("livecast_id", history.getStr("livecast_id"));
					match.set("update_time", new Date());
					lstMatch.add(match);
				}
				
				Db.update("delete from league_match where league_id = ? ", source.getStr("league_id"));
				Db.batchSave(lstMatch, lstMatch.size());
				
				//是否需要将当前轮次+1；
				boolean updateCurrentRound = true;
				for(LeagueMatch match : lstMatch){
					if(!SinaMatchStatusEnum.END.getValue().equals(match.getStr("status")) || match.getDate("match_date").after(new Date())){
						updateCurrentRound = false;
						break;
					}
				}
				if(updateCurrentRound){
					//该轮比赛已经全部打完了，在网站上保留一天
//					Timestamp ts = DateTimeUtils.addDays(lastDateInOneRound, 1);
//					if(ts.before(new Date())){
//						
//					}
					int currentRound = source.getInt("current_round");
					source.set("current_round", currentRound+1>source.getInt("round_max")?source.getInt("round_max"):currentRound+1);
					source.update();
				}
			}
		}
	}
	
	private static List<LeagueMatchHistory> getOneRoundMatch(String matchUrl, MatchSourceSina source, 
			Map<String, String> nameIdMap, Map<String, String> nameENNameMap){
		List<LeagueMatchHistory> lstHistory = new ArrayList<LeagueMatchHistory>();
		HttpClient httpclient = new DefaultHttpClient();
		String content = MatchUtils.getContentWithJsoupHeader(source.getStr("referer_url"), matchUrl);
		if(StringUtils.isNotBlank(content)){
			try {
				content = content.substring(content.indexOf("(")+1);
				content = content.substring(0, content.length()-2);
				JSONObject jsonObj = JSON.parseObject(content);	
				JSONObject resultObj = jsonObj.getJSONObject("result");
				JSONArray dataArray = resultObj.getJSONArray("data");
				for(int i=0; i<dataArray.size(); i++){
					JSONObject object = (JSONObject)dataArray.get(i);
					
					LeagueMatchHistory history = new LeagueMatchHistory();
					history.set("round", object.getIntValue("Round"));
					history.set("match_date", DateTimeUtils.parseDate(object.getString("date")+" "+object.getString("time"), DateTimeUtils.ISO_DATETIME_NOSEC_FORMAT_ARRAY));
					history.set("home_team_id", nameIdMap.get(StringUtils.trim(object.getString("Team1"))));
					history.set("home_team_name", StringUtils.trim(object.getString("Team1")));
					history.set("away_team_id", nameIdMap.get(StringUtils.trim(object.getString("Team2"))));
					history.set("away_team_name", StringUtils.trim(object.getString("Team2")));
					history.set("home_team_en_name", nameENNameMap.get(object.getString("Team1")));
					history.set("away_team_en_name", nameENNameMap.get(object.getString("Team2")));
					
					LeagueMatchHistory historyDb = LeagueMatchHistory.dao.findFirst("select * from league_match_history where home_team_id=? and away_team_id=? and round=? and year=?",
							nameIdMap.get(nameIdMap.get(object.getString("Team1"))), nameIdMap.get(object.getString("Team2")), object.getString("Round"), source.getInt("year"));
					if(historyDb==null){
						history.set("status", EnumUtils.getValue(SinaMatchStatusEnum.values(), object.getString("match_status")));
						if(SinaMatchStatusEnum.UNSTART.getKey().equals(object.getString("match_status"))){
							history.set("result", object.getString("time"));
						}else{
							history.set("result", object.getString("Score1")+" - "+object.getString("Score2"));
						}
					}else if(historyDb!=null){
						if(!SinaMatchStatusEnum.END.getKey().equals(historyDb.get("status"))){
							if(SinaMatchStatusEnum.UNSTART.getKey().equals(object.getString("match_status"))){
								history.set("result", object.getString("time"));
							}else{
								history.set("result", object.getString("Score1")+" - "+object.getString("Score2"));
							}
						}else{
							history.set("status", historyDb.get("status"));
							history.set("result", historyDb.get("result"));
						}
					}
					
					history.set("league_id", source.getStr("league_id"));
					history.set("league_name", EnumUtils.getValue(LeagueEnum.values(), source.getStr("league_id")));
					history.set("year", source.getInt("year"));
					history.set("livecast_id", object.getString("livecast_id"));
					if(StringUtils.isNotBlank(object.getString("OptaId")) && object.getString("OptaId").startsWith("f")){
						history.set("opta_id", object.getString("OptaId").substring(1));
					}
					history.set("year_show", source.getStr("year_show"));
					history.set("id", source.getStr("year_show")+"-"+history.getStr("home_team_id")+"vs"+history.getStr("away_team_id"));
					history.set("match_weekday", DateTimeUtils.formatDate2WeekDay(history.getDate("match_date")));
					
					lstHistory.add(history);
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		httpclient.getConnectionManager().shutdown();
		return lstHistory;
	}
	
	private static String get17Length(){
		return String.valueOf(getRandNum(1, 999999999))+String.valueOf(getRandNum(1, 99999999));
	}
	
	private static int getRandNum(int min, int max) {
	    int randNum = min + (int)(Math.random() * ((max - min) + 1));
	    return randNum;
	}
	
	private static void saveOneRound(List<LeagueMatchHistory> lstNeedInsert, List<LeagueMatchHistory> lstNeedUpdate){
		if(lstNeedInsert.size()>0){
			Db.batchSave(lstNeedInsert, lstNeedInsert.size());
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
}
