package com.wwqk.plugin;

import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchSource;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.utils.MatchUtils;

public class MatchSina {
	
	private static HttpClient httpclient = new DefaultHttpClient();

	public static void archiveMatch(Map<String, String> nameIdMap, Map<String, String> nameENNameMap){
		List<MatchSourceSina> lstMatchSources = MatchSourceSina.dao.find("select * from match_source_sina");
		//http://platform.sina.com.cn/sports_all/client_api?_sport_t_=livecast&_sport_a_=matchesByType&callback=jQuery191029773931918148344_1502525791216&app_key=3571367214&type=4&rnd=2&season=2017&_=1502525791219
		for(MatchSourceSina source:lstMatchSources){
			long startMills = System.currentTimeMillis();
			long endMills = startMills + getRandNum(1, 4);
			String oneRoundUrl = "http://platform.sina.com.cn/sports_all/client_api?_sport_t_=livecast&_sport_a_=matchesByType&callback=jQuery1910"+get17Length()
			+"_"+startMills+"&app_key=3571367214&type="+source.getStr("league_id_sina")+"&rnd="+source.get("current_round")+"&season="+source.get("year")+"&_="+endMills;
			getOneRoundMatch(oneRoundUrl, source, nameIdMap, nameENNameMap);
		}
	}
	
	private static void getOneRoundMatch(String matchUrl, MatchSourceSina source, 
			Map<String, String> nameIdMap, Map<String, String> nameENNameMap){
		String content = MatchUtils.getAjaxContent(httpclient, null, matchUrl);
		
	}
	
	private static String get17Length(){
		return String.valueOf(getRandNum(1, 999999999))+String.valueOf(getRandNum(1, 99999999));
	}
	
	private static int getRandNum(int min, int max) {
	    int randNum = min + (int)(Math.random() * ((max - min) + 1));
	    return randNum;
	}
	
}
