package com.wwqk.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.wwqk.model.LiveBifen;
import com.wwqk.utils.MatchUtils;

public class BifenController extends Controller {

	public void index(){
		render("bifen.jsp");
	}
	
	public void mobile(){
		String resultJson = MatchUtils.getMobileBifenStr();
		List<LiveBifen> lstBifen = new ArrayList<LiveBifen>();
		JSONArray jsonArray = JSON.parseArray(resultJson);
		for(int i=0; i<jsonArray.size(); i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			LiveBifen liveBifen = new LiveBifen();
			liveBifen.setHomeName(jsonObject.getString("team_a"));
			liveBifen.setAwayName(jsonObject.getString("team_b"));
			liveBifen.setLeagueName(jsonObject.getString("league"));
			liveBifen.setStartTimeStr(jsonObject.getString("start_time"));
			liveBifen.setLiveTimeStr(jsonObject.getString("match_mins"));
			liveBifen.setHandicapStr(jsonObject.getString("odds"));
			liveBifen.setHalfBifen(jsonObject.getString("half_bifen"));
			liveBifen.setHomeRed(jsonObject.getString("red_a"));
			liveBifen.setHomeYellow(jsonObject.getString("yellow_a"));
			liveBifen.setAwayRed(jsonObject.getString("red_b"));
			liveBifen.setAwayYellow(jsonObject.getString("yellow_b"));
			liveBifen.setHomeBifen(jsonObject.getString("bifen_a"));
			liveBifen.setAwayBifen(jsonObject.getString("bifen_b"));
			lstBifen.add(liveBifen);
		}
		setAttr("contentHeight", lstBifen.size()*80+140);
		setAttr("lstBifen", lstBifen);
		render("mobile.jsp");
	}
	
	public void mobileJson(){
		String url = "http://m.188bifen.com/json/zuqiu.htm?k=0."+String.valueOf((int)(Math.random()*200))+System.currentTimeMillis();
		Connection connect = Jsoup.connect(url).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.getMobileBifenHeader());
	    String injureStr = "";
		try {
			Document injureDoc = data.get();
			if(injureDoc!=null){
				injureStr = injureDoc.text();
			}
		} catch (IOException e) {}
		Map<String, String> map = new HashMap<String, String>();
		map.put("data", injureStr);
		renderJson(map);
	}
	
}
