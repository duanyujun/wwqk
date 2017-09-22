package com.wwqk.job;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.SinaMatchStatusEnum;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.plugin.MatchSina;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.StringUtils;

/**
 * 
 */
public class InitAllMatchJob implements Job {
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("InitAllMatchJob start");
		//初始化球队名称ID源
		CommonUtils.initNameIdMap();
		//使用sina的比赛源
		try {
			initAll(CommonUtils.nameIdMap, CommonUtils.nameENNameMap);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.err.println("InitAllMatchJob end");
	}
	
	public static void initAll(Map<String, String> nameIdMap, Map<String, String> nameENNameMap) throws InterruptedException{
		List<MatchSourceSina> lstMatchSources = MatchSourceSina.dao.find("select * from match_source_sina where league_id != 1");
		//http://platform.sina.com.cn/sports_all/client_api?_sport_t_=livecast&_sport_a_=matchesByType&callback=jQuery191029773931918148344_1502525791216&app_key=3571367214&type=4&rnd=2&season=2017&_=1502525791219
		for(MatchSourceSina source:lstMatchSources){
			for(int i=source.getInt("current_round");i<=source.getInt("round_max");i++){
				Thread.sleep(2000);
				List<LeagueMatchHistory> lstNeedInsert = new ArrayList<LeagueMatchHistory>();
				List<LeagueMatchHistory> lstNeedUpdate = new ArrayList<LeagueMatchHistory>();
				System.err.println("league_id："+source.getStr("league_id")+"  round："+i);
				LeagueMatchHistory historyExist = LeagueMatchHistory.dao.findFirst("select * from league_match_history where league_id =? and year=? and round=? order by match_date desc", source.getStr("league_id"), source.getInt("year"), i);
				long startMills = System.currentTimeMillis();
				long endMills = startMills + getRandNum(1, 4);
				String oneRoundUrl = "http://platform.sina.com.cn/sports_all/client_api?_sport_t_=livecast&_sport_a_=matchesByType&callback=jQuery1910"+get17Length()
				+"_"+startMills+"&app_key=3571367214&type="+source.getStr("league_id_sina")+"&rnd="+i+"&season="+source.get("year")+"&_="+endMills;
				List<LeagueMatchHistory> currentRoundMatches = getOneRoundMatch(oneRoundUrl, source, nameIdMap, nameENNameMap);
				if(historyExist==null){
					lstNeedInsert.addAll(currentRoundMatches);
				}else{
					lstNeedUpdate.addAll(currentRoundMatches);
				}
				
				saveOneRound(lstNeedInsert, lstNeedUpdate);
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


