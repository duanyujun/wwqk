package com.wwqk.service;

import java.sql.Timestamp;
import java.text.ParseException;
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
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.StringUtils;

/** 
 * @author
 * @date 
 */

public class AllMatchService {

	public static Map<Object, Object> matchData(Controller controller){
		String nowDateStr = DateTimeUtils.formatDateTime(new Timestamp(new Date().getTime()));
		String sumSql = "select count(*) from all_live_match where 1 = 1 and match_datetime > '"+nowDateStr+"'";
		String sql = "select * from all_live_match where 1 = 1 and match_datetime > '"+nowDateStr+"'";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search =search.replaceAll("'", "").trim();
			whereSql = " and ( home_team_name like '%"+search+"%'"+" OR away_team_name like '%"+search+"%') ";
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by match_datetime "+sortType;
			break;
		case 2:
			orderSql = " order by home_team_name "+sortType;
			break;
		case 3:
			orderSql = " order by away_team_name "+sortType;
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
		List<AllLiveMatch> lstHistory = new ArrayList<AllLiveMatch>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstHistory = AllLiveMatch.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstHistory.size()];
			for(int i=0; i<lstHistory.size(); i++){
				Object[] obj = new Object[5];
				AllLiveMatch history = lstHistory.get(i);
				obj[0] = history.get("id");
				obj[1] = history.get("match_datetime");
				obj[2] = history.get("home_team_name");
				obj[3] = history.get("away_team_name");
				String info = history.getStr("info");
				if(StringUtils.isNotBlank(info)){
					info = info.replaceAll("\\s+", "");
					info = info.replaceAll("<.*?>", "");
					if(info.length()>15){
						info = info.substring(0,15);
					}
				}
				obj[4] = info;
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
		
		AllLiveMatch match = AllLiveMatch.dao.findById(id);
		
		try {
			match.set("match_datetime", DateTimeUtils.parseDate(CommonUtils.formatDateStr(controller.getPara("match_datetime")), 
					DateTimeUtils.ISO_DATETIME_FORMAT_ARRAY));
		} catch (ParseException e) {
			
		}
		
		if(StringUtils.isNotBlank(controller.getPara("info"))){
			LeagueMatchHistory history = null;
			// 更新五大联赛情报
			if(StringUtils.isNotBlank(match.getStr("league_id"))){
				history = LeagueMatchHistory.dao.findFirst(
						"select * from league_match_history home_team_id = ? and away_team_id =? and year_show =?",
						match.getStr("home_team_id"), match.getStr("away_team_id"), match.getStr("year_show"));
			}
			String infoStr = controller.getPara("info").replaceAll("\\s+", "");
			if("<p><br></p>".equals(infoStr)){
				match.set("info", null);
			}else{
				match.set("info", controller.getPara("info"));
			}
			if(history!=null){
				history.set("info", match.get("info"));
				history.update();
			}
			
		}
		
		match.set("home_team_name", controller.getPara("home_team_name"));
		match.set("away_team_name", controller.getPara("away_team_name"));
		
		match.update();
	}
	
	
}
