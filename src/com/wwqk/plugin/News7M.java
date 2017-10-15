package com.wwqk.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.TipsAll;
import com.wwqk.model.TipsMatch;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.StringUtils;

/** 
 * 
 *    
 */

public class News7M {
	
	private static final String SITE_URL = "http://report.7m.cn/data/index/gb.js?";
	
	private static final String MATCH_URL = "http://report.7m.cn/data/game/gb/";
	
	private static final String INJURE_URL = "http://data.7m.com.cn/analyse/gb/nline_up_injure/";
	
	//http://report.7m.cn/data/game/gb/2100312.js?1507515205545&_=1507515205047
	
	public static void crawl(){
		Connection connect = Jsoup.connect(SITE_URL+System.currentTimeMillis()).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.get7MHeader());
		try {
			Document document = data.get();
			String resultStr = document.text();
			resultStr = resultStr.substring(resultStr.indexOf("=")+1);
			resultStr = StringUtils.trim(resultStr.substring(0,resultStr.lastIndexOf(";")));
			JSONArray jsonArray = (JSONArray)JSON.parse(resultStr);
			for(int i=0; i<jsonArray.size(); i++){
				JSONObject jsonObject = (JSONObject)jsonArray.get(i);
				saveOneMatchAndNews(jsonObject);
				Thread.sleep(200);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Before(Tx.class)
	private static void saveOneMatchAndNews(JSONObject jsonObject){
		String gameId = jsonObject.getString("GameId");
		String leagueName = jsonObject.getString("CompetitionName");
		String homeName = jsonObject.getString("HomeName");
		String awayName = jsonObject.getString("AwayName");
		String predictionDesc = jsonObject.getString("PredictionDesc");
		String matchTime = jsonObject.getString("StartTime");
		String ahAmount = jsonObject.getString("rang");
		TipsMatch tipsMatch = TipsMatch.dao.findFirst("select * from tips_match where game_id = ?", gameId);
		if(tipsMatch==null){
			tipsMatch = new TipsMatch();
		}
		tipsMatch.set("league_name", leagueName);
		tipsMatch.set("home_name", homeName);
		tipsMatch.set("away_name", awayName);
		tipsMatch.set("prediction_desc", predictionDesc);
		tipsMatch.set("match_time", matchTime);
		tipsMatch.set("ah_amount", ahAmount);
		if(StringUtils.isBlank(tipsMatch.getStr("game_id"))){
			tipsMatch.set("game_id", gameId);
			tipsMatch.save();
		}else{
			tipsMatch.update();
		}
		
		long startMills = System.currentTimeMillis();
		Connection connect = Jsoup.connect(MATCH_URL+gameId+".js?"+startMills+"&_="+(startMills-400)).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.get7MMatchHeader(gameId));
		String gameEventStr = null;
		try {
			gameEventStr = data.get().text();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gameEventStr = gameEventStr.substring(gameEventStr.indexOf("gameEvent =")+11);
		gameEventStr = StringUtils.trim(gameEventStr.substring(0, gameEventStr.indexOf(";")));
		//情报
		List<TipsAll> lstTips = new ArrayList<TipsAll>();
		JSONArray jsonArray = (JSONArray)JSON.parse(gameEventStr);
		for(int i=0; i<jsonArray.size(); i++){
			JSONObject newsObject = (JSONObject)jsonArray.get(i);
			TipsAll tips = new TipsAll();
			tips.set("game_id", gameId);
			tips.set("is_home_away", newsObject.getString("HomeAway"));
			tips.set("is_good_bad", newsObject.getString("UpDown"));
			tips.set("news", newsObject.getString("Title"));
			lstTips.add(tips);
		}
		if(lstTips.size()!=0){
			Db.update("delete from tips_all where game_id = ?", gameId);
			Db.batchSave(lstTips, lstTips.size());
		}
		//伤停情况
	    startMills = System.currentTimeMillis();
	    connect = Jsoup.connect(INJURE_URL+gameId+".js?"+startMills).ignoreContentType(true);
	    data = connect.data(MatchUtils.get7MInjuryHeader(gameId));
	    String injureStr = null;
		try {
			Document injureDoc = data.get();
			if(injureDoc!=null){
				injureStr = injureDoc.text();
			}
		} catch (IOException e) {
			
		}
		if(StringUtils.isNotBlank(injureStr)){
			int homeIndex = injureStr.indexOf("injure_an");
			if(homeIndex!=-1){
				String homeInjure = injureStr.substring(homeIndex+8);
				homeInjure = homeInjure.substring(homeInjure.indexOf("[")+1,homeInjure.indexOf("]"));
				homeInjure = StringUtils.trim(homeInjure.replaceAll("'", ""));
				homeInjure = homeInjure.replaceAll(" ", "-");
				tipsMatch.set("home_absence", homeInjure);
			}
			
			int awayIndex = injureStr.indexOf("injure_bn");
			if(awayIndex!=-1){
				String awayInjure = injureStr.substring(awayIndex+8);
				awayInjure = awayInjure.substring(awayInjure.indexOf("[")+1,awayInjure.indexOf("]"));
				awayInjure = StringUtils.trim(awayInjure.replaceAll("'", ""));
				awayInjure = awayInjure.replaceAll(" ", "-");
				tipsMatch.set("away_absence", awayInjure);
			}
			tipsMatch.update();
		}
	}
	
	public static void main(String[] args) {
		crawl();
	}
	
}
