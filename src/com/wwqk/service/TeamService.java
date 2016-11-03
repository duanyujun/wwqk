package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.League;
import com.wwqk.utils.StringUtils;

public class TeamService {

	public static Map<Object, Object> teamData(Controller controller){
		String sumSql = "select count(*) from team t, league l where t.league_id = l.id ";
		String sql = "select t.*, l.name league_name from team t, league l where t.league_id = l.id ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			whereSql = " and t.name like '%"+search+"%'"+" OR t.name_en like '%"+search+"%'";
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by t.name "+sortType;
			break;
		case 2:
			orderSql = " order by t.setup_time "+sortType;
			break;
		case 3:
			orderSql = " order by t.venue_name "+sortType;
			break;
		case 4:
			orderSql = " order by l.name "+sortType;
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
		List<League> lstLeagues = new ArrayList<League>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstLeagues = League.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstLeagues.size()];
			for(int i=0; i<lstLeagues.size(); i++){
				Object[] obj = new Object[6];
				League roles = lstLeagues.get(i);
				obj[0] = roles.get("id");
				obj[1] = roles.get("name");
				obj[2] = roles.get("setup_time");
				obj[3] = roles.get("venue_name");
				obj[4] = roles.get("league_name");
				obj[5] = roles.get("team_url");
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
