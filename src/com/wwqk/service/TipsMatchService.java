package com.wwqk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.TipsMatch;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.StringUtils;
import com.wwqk.utils.TranslateUtils;

public class TipsMatchService {

	public static Map<Object, Object> tipsMatchData(Controller controller){
		Date nowDate = DateTimeUtils.addHours(new Date(), -20);
		String sumSql = "select count(*) from tips_match where match_time > ? ";
		String sql = "select * from tips_match where match_time > ? ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search = search.replaceAll("'", "");
			whereSql = " and (home_name like '%"+search+"%'"+" OR away_name like '%"+search+"%'"+" )";
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by match_time "+sortType;
			break;
		case 2:
			orderSql = " order by home_name "+sortType;
			break;
		case 3:
			orderSql = " order by away_name "+sortType;
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
		List<TipsMatch> lstTipsMatch = new ArrayList<TipsMatch>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstTipsMatch = TipsMatch.dao.find(sql+whereSql+orderSql+limitSql, nowDate);
			data = new Object[lstTipsMatch.size()];
			for(int i=0; i<lstTipsMatch.size(); i++){
				Object[] obj = new Object[6];
				TipsMatch tipsMatch = lstTipsMatch.get(i);
				obj[0] = tipsMatch.get("id");
				obj[1] = tipsMatch.get("match_time");
				obj[2] = tipsMatch.get("home_name");
				obj[3] = tipsMatch.get("away_name");
				obj[4] = tipsMatch.get("league_name");
				obj[5] = tipsMatch.get("live_match_id");
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
	
	public static void saveTipsMatch(Controller controller){
		String id = controller.getPara("id");
		if(id == null){
			return;
		}
		TipsMatch tipsMatch = TipsMatch.dao.findById(id);
		String liveMatchId = controller.getPara("live_match_id");
		AllLiveMatch liveMatch = AllLiveMatch.dao.findById(liveMatchId);
		TranslateUtils.handleOneMatch(liveMatch, tipsMatch);
	}
	
}
