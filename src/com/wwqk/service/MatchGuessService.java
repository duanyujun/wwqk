package com.wwqk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.MatchGuess;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.StringUtils;

public class MatchGuessService {

	public static Map<Object, Object> matchGuessData(Controller controller){
		Date nowDate = DateTimeUtils.addHours(new Date(), -2);
		String sumSql = "select count(*) from match_guess where match_time > ?  ";
		String sql = "select * from match_guess where match_time > ? ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search =search.replaceAll("'", "").trim();
			whereSql = " and (bet_title like '%"+search+"%'" +" OR bet_title_cn like '%"+search+"%')"; 
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 4:
			orderSql = " order by match_time "+sortType;
			break;
		default:
			break;
		}
		
		int start = controller.getParaToInt("start");
		int length = controller.getParaToInt("length");
		if(length!=-1){
			limitSql = " limit "+start+","+length;
		}
		Long recordsTotal = Db.queryLong(sumSql+whereSql, nowDate);
		List<MatchGuess> lstMatchGuess = new ArrayList<MatchGuess>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstMatchGuess = MatchGuess.dao.find(sql+whereSql+orderSql+limitSql, nowDate);
			data = new Object[lstMatchGuess.size()];
			for(int i=0; i<lstMatchGuess.size(); i++){
				Object[] obj = new Object[7];
				MatchGuess matchGuess = lstMatchGuess.get(i);
				obj[0] = matchGuess.get("id");
				obj[1] = matchGuess.getLong("id").toString() + "-" + matchGuess.get("live_match_id");
				obj[2] = matchGuess.get("bet_title");
				obj[3] = matchGuess.get("bet_title_cn");
				obj[4] = matchGuess.get("match_time");
				obj[5] = matchGuess.get("country");
				obj[6] = matchGuess.get("league");

				data[i] = obj;
			}
		}
		if(data==null){
			data = new Object[0];
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("draw", controller.getParaToInt("draw"));
		map.put("recordsTotal", recordsTotal);
		map.put("recordsFiltered", recordsTotal);
		map.put("data", data);
		
		return map;
	}
	
	@Before(Tx.class)
	public static void saveMatchGuess(Controller controller){
	
		String id = controller.getPara("id");
		MatchGuess matchGuess = null;
		if(StringUtils.isBlank(id)){
			matchGuess = new MatchGuess();
		}else{
			matchGuess = MatchGuess.dao.findById(id);
		}
		
		String liveMatchId = controller.getPara("live_match_id");
		if(StringUtils.isNotBlank(liveMatchId)){
			matchGuess.set("live_match_id", liveMatchId);
			AllLiveMatch liveMatch = AllLiveMatch.dao.findFirst("select * from all_live_match where id = ?", liveMatchId);
			if(StringUtils.isNotBlank(liveMatch.getStr("match_key"))){
				matchGuess.set("match_key", liveMatch.getStr("match_key"));
			}
			liveMatch.set("tips", "1");
			liveMatch.update();
		}
		
		
		if(StringUtils.isNotBlank(controller.getPara("bet_title"))){
			matchGuess.set("bet_title", controller.getPara("bet_title"));
		}
		if(StringUtils.isNotBlank(controller.getPara("content"))){
			matchGuess.set("content", controller.getPara("content"));
		}
        matchGuess.set("bet_title_cn", controller.getPara("bet_title_cn"));
        matchGuess.set("content_cn", controller.getPara("content_cn"));
        if(StringUtils.isNotBlank(controller.getPara("match_time"))){
        	matchGuess.set("match_time", controller.getPara("match_time"));
        }
        if(StringUtils.isNotBlank(controller.getPara("country"))){
        	matchGuess.set("country", controller.getPara("country"));
        }
        if(StringUtils.isNotBlank(controller.getPara("league"))){
        	matchGuess.set("league", controller.getPara("league"));
        }
        if(StringUtils.isNotBlank(controller.getPara("source_url"))){
        	matchGuess.set("source_url", controller.getPara("source_url"));
        }
       

		save(matchGuess);
	}
	
	public static void save(MatchGuess matchGuess){
		if(matchGuess.get("id")==null){
			matchGuess.save();
		}else{
			matchGuess.update();
		}
	}
	
}
