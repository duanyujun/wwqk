package com.wwqk.utils;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.Fun;
import com.wwqk.model.MatchGuess;
import com.wwqk.model.Say;
import com.wwqk.model.TipsMatch;
import com.wwqk.model.Videos;

/** 
 * @author duanyujun 
 * @date 创建时间：2018-5-26 下午8:52:22    
 */

public class IndexUtils {

	public static void init(Controller controller){
		Say say = Say.dao.findFirst("select * from say order by create_time desc limit 0,5");
		controller.setAttr("say", say);
		
		Date startDate = DateTimeUtils.addHours(new Date(), -1);
		List<AllLiveMatch> liveMatchList = AllLiveMatch.dao.find("select * from all_live_match where match_datetime > ? order by match_datetime asc limit 1, 7", startDate);
		controller.setAttr("liveMatchList", liveMatchList);
		// 趣点
		List<Fun> funList = Fun.dao.find("select * from fun where type = 1 order by create_time desc limit 0, 10");
		controller.setAttr("funList", funList);
		// 视频
		List<Videos> videoList = Videos.dao.find("select * from videos where recom=1 order by match_date desc limit 0,5");
		controller.setAttr("videoList", videoList);
		// 情报分析
		Date nowDate = DateTimeUtils.addHours(new Date(), -2);
		List<TipsMatch> lstTipsMatch = TipsMatch.dao.find("select * from tips_match where match_time > ? order by match_time asc limit 0, 10", nowDate);
		formatMsg(lstTipsMatch);
		controller.setAttr("lstTipsMatch", lstTipsMatch);
	}
	
	private static void formatMsg(List<TipsMatch> lstMatch){
		for(TipsMatch match:lstMatch){
			String leagueName = match.getStr("league_name");
			leagueName = CommonUtils.leagueNameIdMap.get(leagueName);
			match.set("league_name", leagueName==null?match.getStr("league_name"):leagueName);
			match.set("prediction_desc", match.getStr("prediction_desc").replaceAll("\\s+", "").replaceAll("\n", ""));
		}
	}
	
}
