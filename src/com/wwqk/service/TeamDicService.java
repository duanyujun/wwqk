package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.TeamDic;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.StringUtils;

public class TeamDicService {

	public static Map<Object, Object> teamDicData(Controller controller){
		String sumSql = "select count(*) from team_dic where 1 = 1  ";
		String sql = "select * from team_dic where 1 = 1 ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search =search.replaceAll("'", "").trim();
			whereSql = " and (team_name like '%"+search+"%'" +" OR std_name like '%"+search+"%'" +" OR std_name_py like '%"+search+"%'" +" OR league_name like '%"+search+"%')"; 
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
			case 1:
              orderSql = " order by team_name "+sortType;
              break;
			case 2:
              orderSql = " order by std_name "+sortType;
              break;
			case 3:
              orderSql = " order by std_name_py "+sortType;
              break;
			case 4:
              orderSql = " order by ok_id "+sortType;
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
		List<TeamDic> lstTeamDic = new ArrayList<TeamDic>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstTeamDic = TeamDic.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstTeamDic.size()];
			for(int i=0; i<lstTeamDic.size(); i++){
				Object[] obj = new Object[8];
				TeamDic teamDic = lstTeamDic.get(i);
				obj[0] = teamDic.get("id");
				
				obj[1] = teamDic.get("team_name");
				obj[2] = teamDic.get("std_name");
				obj[3] = teamDic.get("std_name_py");
				obj[4] = teamDic.get("ok_id");
				obj[5] = teamDic.get("md5");
				obj[6] = teamDic.get("std_md5");
				obj[7] = teamDic.get("league_name");

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
	
	public static void saveTeamDic(Controller controller){
	
		String id = controller.getPara("id");
		TeamDic teamDic = null;
		if(StringUtils.isBlank(id)){
			teamDic = new TeamDic();
		}else{
			teamDic = TeamDic.dao.findById(id);
		}
		
		String okIdParam = controller.getPara("ok_id");
		if(StringUtils.isBlank(okIdParam)){
			teamDic.set("ok_id", 0);
		}else{
			teamDic.set("ok_id", Integer.valueOf(controller.getPara("ok_id")));
		}
		
		String stdMd5 = controller.getPara("std_md5");
		if(StringUtils.isBlank(stdMd5)){
			teamDic.set("std_md5", "0");
		}else{
			teamDic.set("std_md5", stdMd5);
		}
		
		if(teamDic.getInt("ok_id")==0 
				&& StringUtils.isNotBlank(controller.getPara("std_name"))
				&& "0".equals(teamDic.getStr("std_md5"))){ 
			TeamDic teamDicDB = TeamDic.dao.findFirst("select * from team_dic where std_name = ?", controller.getPara("std_name"));
			teamDic.set("std_md5", teamDicDB.get("std_md5"));
	        teamDic.set("std_name", teamDicDB.get("std_name"));
	        teamDic.set("std_name_py", teamDicDB.get("std_name_py"));
	        teamDic.set("league_name", teamDicDB.get("league_name"));
	        teamDic.set("std_name_en", teamDicDB.get("std_name_en"));
		}else{
	        teamDic.set("std_name", controller.getPara("std_name"));
	        teamDic.set("std_name_py", controller.getPara("std_name_py"));
	        teamDic.set("league_name", controller.getPara("league_name"));
	        teamDic.set("std_name_en", controller.getPara("std_name_en"));
		}
		
        teamDic.set("team_name", controller.getPara("team_name"));
        teamDic.set("md5", controller.getPara("md5"));
        if(StringUtils.isBlank(controller.getPara("md5"))){
        	teamDic.set("md5", CommonUtils.md5(controller.getPara("md5")));
        }else{
        	teamDic.set("md5", controller.getPara("md5"));
        }

		save(teamDic);
	}
	
	public static void save(TeamDic teamDic){
		if(teamDic.get("id")==null){
			teamDic.save();
		}else{
			teamDic.update();
		}
	}
	
	
}
