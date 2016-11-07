package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.Say;
import com.wwqk.utils.StringUtils;

public class SayService {

	public static Map<Object, Object> sayData(Controller controller){
		String sumSql = "select count(*) from say ";
		String sql = "select * from say ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			whereSql = " and (player_id like '%"+search+"%'"+" OR player_name like '%"+search+"%'"+" OR l.content like '%"+search+"%' )";
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by t.player_id "+sortType;
			break;
		case 2:
			orderSql = " order by t.player_name "+sortType;
			break;
		case 3:
			orderSql = " order by t.create_time "+sortType;
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
		List<Say> lstSay = new ArrayList<Say>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstSay = Say.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstSay.size()];
			for(int i=0; i<lstSay.size(); i++){
				Object[] obj = new Object[5];
				Say say = lstSay.get(i);
				obj[0] = say.get("id");
				obj[1] = say.get("player_id");
				obj[2] = say.get("player_name");
				obj[3] = say.get("create_time");
				String content = say.get("content");
				if(StringUtils.isNotBlank(content) && content.length()>10){
					content = content.substring(0,10);
				}
				obj[4] = content;
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
