package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.utils.StringUtils;

public class MatchHistoryService {

	public static Map<Object, Object> matchHistoryData(Controller controller){
		String sumSql = "select count(*) from league_match_history where status != '完场' ";
		String sql = "select * from league_match_history where status != '完场' ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			whereSql = " and ( home_team_name like '%"+search+"%'"+" OR away_team_name like '%"+search+"%') ";
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by match_date "+sortType;
			break;
		case 2:
			orderSql = " order by home_team_name "+sortType;
			break;
		case 3:
			orderSql = " order by away_team_name "+sortType;
			break;
		case 4:
			orderSql = " order by status "+sortType;
			break;
		default:
			break;
		}
		
		int start = controller.getParaToInt("start");
		int length = controller.getParaToInt("length");
		if(length!=-1){
			limitSql = " limit "+start+","+length;
		}
		Long recordsTotal = Db.queryLong(sumSql+whereSql);
		List<LeagueMatchHistory> lstHistory = new ArrayList<LeagueMatchHistory>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstHistory = LeagueMatchHistory.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstHistory.size()];
			for(int i=0; i<lstHistory.size(); i++){
				Object[] obj = new Object[6];
				LeagueMatchHistory history = lstHistory.get(i);
				obj[0] = history.get("id");
				obj[1] = history.get("match_date");
				obj[2] = history.get("home_team_name");
				obj[3] = history.get("away_team_name");
				obj[4] = history.get("status");
				obj[5] = history.get("round");
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
	
}
