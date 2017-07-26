package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.LeagueShooter163;
import com.wwqk.model.Player;
import com.wwqk.utils.StringUtils;

public class Shooter163Service {

	public static Map<Object, Object> shooter163Data(Controller controller){
		String sumSql = "select count(*) from league_shooter_163 ";
		String sql = "select * from league_shooter_163 ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search = search.trim();
			whereSql = " where (player_name_163 like '%"+search+"%'"+" OR team_name_163 like '%"+search+"%'"+" )";
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by player_name_163 "+sortType;
			break;
		case 2:
			orderSql = " order by team_name_163 "+sortType +", rank asc ";
			break;
		case 3:
			orderSql = " order by rank "+sortType;
			break;
		case 4:
			orderSql = " order by player_name "+sortType;
			break;
		case 5:
			orderSql = " order by team_name "+sortType;
			break;
		case 6:
			orderSql = " order by goal_count "+sortType;
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
		List<LeagueShooter163> lstShooter163 = new ArrayList<LeagueShooter163>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstShooter163 = LeagueShooter163.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstShooter163.size()];
			for(int i=0; i<lstShooter163.size(); i++){
				Object[] obj = new Object[8];
				LeagueShooter163 shooter163 = lstShooter163.get(i);
				obj[0] = shooter163.get("id");
				obj[1] = shooter163.get("player_name_163");
				obj[2] = shooter163.get("team_name_163");
				obj[3] = shooter163.get("rank");
				obj[4] = shooter163.get("player_name");
				obj[5] = shooter163.get("team_name");
				obj[6] = shooter163.get("goal_count");
				obj[7] = shooter163.get("player_img");
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
	
	//仅用于修改
	public static void saveShooter163(Controller controller){
		
		String id = controller.getPara("id");
		if(StringUtils.isBlank(id)){
			return;
		}
		LeagueShooter163 shooter163  = LeagueShooter163.dao.findById(id);
		String player_id = controller.getPara("player_id");
		if(StringUtils.isBlank(player_id)){
			return;
		}
		
		Player player = Player.dao.findFirst("SELECT p.*, t.id team_id, t.name team_name FROM player p, team t WHERE p.team_id = t.id and p.id = ? ", player_id);
		shooter163.set("player_id", player.get("id"));
		shooter163.set("player_name", player.get("name"));
		shooter163.set("team_id", player.get("team_id"));
		shooter163.set("team_name", player.get("team_name"));
		shooter163.set("player_img", player.get("img_small_local"));
		
		save(shooter163);
	}
	
	public static void save(LeagueShooter163 shooter163){
		if(shooter163.get("id")==null){
			shooter163.save();
		}else{
			shooter163.update();
		}
	}
	
}
