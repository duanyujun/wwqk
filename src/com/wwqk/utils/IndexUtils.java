package com.wwqk.utils;

import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.Fun;
import com.wwqk.model.Say;
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
		List<Videos> videoList = Videos.dao.find("select * from videos order by match_date desc limit 0,5");
		controller.setAttr("videoList", videoList);
	
	}
	
}
