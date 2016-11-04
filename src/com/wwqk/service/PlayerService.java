package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.Player;
import com.wwqk.utils.StringUtils;

public class PlayerService {

	public static Map<Object, Object> playerData(Controller controller){
		String sumSql = "select count(*) from player p, team t where p.team_id = t.id ";
		String sql = "select p.*, t.name team_name from player p, team t where p.team_id = t.id ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			whereSql = " and (p.name like '%"+search+"%'"+" OR t.name like '%"+search+"%'"+" OR t.first_name like '%"+search+"%'"+" OR t.last_name like '%"+search+"%'"+" OR p.nationality like '%"+search+"%' )";
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by p.name "+sortType;
			break;
		case 2:
			orderSql = " order by t.name "+sortType;
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
		List<Player> lstPalyer = new ArrayList<Player>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstPalyer = Player.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstPalyer.size()];
			for(int i=0; i<lstPalyer.size(); i++){
				Object[] obj = new Object[10];
				Player player = lstPalyer.get(i);
				obj[0] = player.get("id");
				obj[1] = player.get("name");
				obj[2] = player.get("team_name");
				obj[3] = player.get("first_name");
				obj[4] = player.get("last_name");
				obj[5] = player.get("nationality");
				obj[6] = player.get("age");
				obj[7] = player.get("height");
				obj[8] = player.get("weight");
				obj[9] = player.get("foot");
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
