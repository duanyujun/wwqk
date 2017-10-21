package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.LeagueAssists163;
import com.wwqk.model.Player;
import com.wwqk.utils.StringUtils;

public class Assists163Service {

	public static Map<Object, Object> assists163Data(Controller controller){
		String sumSql = "select count(*) from league_assists_163 ";
		String sql = "select * from league_assists_163 ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search =search.replaceAll("'", "").trim();
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
			orderSql = " order by assists_count "+sortType;
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
		List<LeagueAssists163> lstAssists163 = new ArrayList<LeagueAssists163>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstAssists163 = LeagueAssists163.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstAssists163.size()];
			for(int i=0; i<lstAssists163.size(); i++){
				Object[] obj = new Object[8];
				LeagueAssists163 assists163 = lstAssists163.get(i);
				obj[0] = assists163.get("id");
				obj[1] = assists163.get("player_name_163");
				obj[2] = assists163.get("team_name_163");
				obj[3] = assists163.get("rank");
				obj[4] = assists163.get("player_name");
				obj[5] = assists163.get("team_name");
				obj[6] = assists163.get("assists_count");
				obj[7] = assists163.get("player_img");
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
	public static void saveAssists163(Controller controller){
		
		String id = controller.getPara("id");
		if(StringUtils.isBlank(id)){
			return;
		}
		LeagueAssists163 assists163  = LeagueAssists163.dao.findById(id);
		String player_id = controller.getPara("player_id");
		if(StringUtils.isBlank(player_id)){
			return;
		}
		
		Player player = Player.dao.findFirst("SELECT p.*, t.id team_id, t.name team_name FROM player p, team t WHERE p.team_id = t.id and p.id = ? ", player_id);
		assists163.set("player_id", player.get("id"));
		assists163.set("player_name", player.get("name"));
		assists163.set("team_id", player.get("team_id"));
		assists163.set("team_name", player.get("team_name"));
		assists163.set("player_img", player.get("img_small_local"));
		
		save(assists163);
	}
	
	public static void save(LeagueAssists163 assists163){
		if(assists163.get("id")==null){
			assists163.save();
		}else{
			assists163.update();
		}
	}
	
}
