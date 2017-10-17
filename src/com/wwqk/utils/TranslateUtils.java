package com.wwqk.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.TipsAll;
import com.wwqk.model.TipsMatch;

public class TranslateUtils {

	public static void translate(){
		Date nowDate = DateTimeUtils.addHours(new Date(), -2);
		List<AllLiveMatch> lstLiveMatch = AllLiveMatch.dao.find("select * from all_live_match where match_datetime > ?", nowDate);
		Map<String, AllLiveMatch> liveMap = new HashMap<String, AllLiveMatch>();
		for(AllLiveMatch liveMatch:lstLiveMatch){
			liveMap.put(liveMatch.getStr("home_team_name")+"vs"+liveMatch.getStr("away_team_name"), liveMatch);
		}
		List<TipsMatch> lstMatch = TipsMatch.dao.find("select * from tips_match where match_time > ? and live_match_id = 0", nowDate);
		for(TipsMatch match:lstMatch){
			AllLiveMatch existLiveMatch = liveMap.get(match.getStr("home_name")+"vs"+match.getStr("away_name"));
			handleOneMatch(existLiveMatch, match);
		}
	}
	
	@Before(Tx.class)
	private static void handleOneMatch(AllLiveMatch existLiveMatch, TipsMatch match){
		if(existLiveMatch!=null){
			match.set("live_match_id", existLiveMatch.get("id"));
			match.set("match_key", existLiveMatch.get("match_key"));
			match.set("home_team_id", existLiveMatch.get("home_team_id"));
			match.set("away_team_id", existLiveMatch.get("away_team_id"));
			match.set("home_team_enname", existLiveMatch.get("home_team_enname"));
			match.set("away_team_enname", existLiveMatch.get("away_team_enname"));
			match.set("year_show", existLiveMatch.get("year_show"));
			match.set("home_team_img", existLiveMatch.get("home_team_img"));
			match.set("away_team_img", existLiveMatch.get("away_team_img"));
			List<TipsAll> lstTipsAll = TipsAll.dao.find("select * from tips_all where game_id = ?", match.getStr("game_id"));
			for(TipsAll tipsAll : lstTipsAll){
				tipsAll.set("live_match_id", existLiveMatch.get("id"));
				tipsAll.set("match_key", existLiveMatch.get("match_key"));
				tipsAll.set("home_team_id", existLiveMatch.get("home_team_id"));
				tipsAll.set("away_team_id", existLiveMatch.get("away_team_id"));
				tipsAll.set("home_team_enname", existLiveMatch.get("home_team_enname"));
				tipsAll.set("away_team_enname", existLiveMatch.get("away_team_enname"));
				tipsAll.set("year_show", existLiveMatch.get("year_show"));
				tipsAll.set("home_team_img", existLiveMatch.get("home_team_img"));
				tipsAll.set("away_team_img", existLiveMatch.get("away_team_img"));
			}
			match.update();
			Db.batchUpdate(lstTipsAll, lstTipsAll.size());
		}
		
	}
	
}
