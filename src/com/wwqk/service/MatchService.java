package com.wwqk.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.StringUtils;

/** 
 * @author
 * @date 
 */

public class MatchService {

	public static Map<Object, Object> matchData(Controller controller){
		String sumSql = "select count(*) from league_match_history where 1 = 1 ";
		String sql = "select * from league_match_history where 1 = 1 ";
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
			orderSql = " order by result "+sortType;
			break;
		case 5:
			orderSql = " order by status "+sortType;
			break;
		case 6:
			orderSql = " order by round "+sortType;
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
				Object[] obj = new Object[7];
				LeagueMatchHistory history = lstHistory.get(i);
				obj[0] = history.get("id");
				obj[1] = history.get("match_date");
				obj[2] = history.get("home_team_name");
				obj[3] = history.get("away_team_name");
				obj[4] = history.get("result");
				obj[5] = history.get("status");
				obj[6] = history.get("round");
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
	public static void updateMatch(Controller controller){
		
		String id = controller.getPara("id");
		if(StringUtils.isBlank(id)){
			return;
		}
		
		LeagueMatchHistory match = LeagueMatchHistory.dao.findById(id);
		
		try {
			match.set("match_date", DateTimeUtils.parseDate(CommonUtils.formatDateStr(controller.getPara("match_date")), 
					DateTimeUtils.ISO_DATETIME_FORMAT_ARRAY));
		} catch (ParseException e) {
			
		}
		
		if(StringUtils.isNotBlank(controller.getPara("analysis"))){
			String analysisStr = controller.getPara("analysis").replaceAll("\\s+", "");
			if("<p><br></p>".equals(analysisStr)){
				match.set("analysis", null);
			}else{
				match.set("analysis", controller.getPara("analysis"));
			}
		}
		
		
		if(StringUtils.isNotBlank(controller.getPara("info"))){
			String infoStr = controller.getPara("info").replaceAll("\\s+", "");
			if("<p><br></p>".equals(infoStr)){
				match.set("info", null);
			}else{
				match.set("info", controller.getPara("info"));
			}
		}
		
		if(StringUtils.isNotBlank(controller.getPara("team"))){
			String teamStr = controller.getPara("team").replaceAll("\\s+", "");
			if("<p><br></p>".equals(teamStr)){
				match.set("team", null);
			}else{
				match.set("team", controller.getPara("team"));
			}
		}
		
		
		match.set("home_team_name", controller.getPara("home_team_name"));
		match.set("away_team_name", controller.getPara("away_team_name"));
		match.set("result", controller.getPara("result"));
		match.set("status", controller.getPara("status"));
		match.set("round", controller.getPara("round"));
		
		match.update();
	}
	
	
}
