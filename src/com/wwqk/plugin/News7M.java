package com.wwqk.plugin;

import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.StringUtils;

/** 
 * 
 *    
 */

public class News7M {
	
	private static final String SITE_URL = "http://report.7m.cn/data/index/gb.js?";
	
	private static final String MATCH_URL = "http://report.7m.cn/data/game/gb/";
	
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
				long startMills = System.currentTimeMillis();
				JSONObject jsonObject = (JSONObject)jsonArray.get(i);
				String matchId = jsonObject.getString("GameId");
				connect = Jsoup.connect(MATCH_URL+matchId+".js?"+startMills+"&_="+(startMills-400)).ignoreContentType(true);
				data = connect.data(MatchUtils.get7MMatchHeader(matchId));
				String gameEventStr = data.get().text();
				gameEventStr = gameEventStr.substring(gameEventStr.indexOf("gameEvent =")+11);
				gameEventStr = StringUtils.trim(gameEventStr.substring(0, gameEventStr.indexOf(";")));
				System.err.println(gameEventStr);
			}
			//System.err.println(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		crawl();
	}
	
}
