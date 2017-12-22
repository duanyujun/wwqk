package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.Sofifa;
import com.wwqk.utils.StringUtils;

public class SofifaService {

	public static Map<Object, Object> sofifaData(Controller controller){
		String sumSql = "select count(*) from sofifa where 1 = 1  ";
		String sql = "select * from sofifa where 1 = 1 ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search =search.replaceAll("'", "").trim();
			whereSql = " and (fifa_name like '%"+search+"%')"; 
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
			case 1:
              orderSql = " order by fifa_name "+sortType;
              break;case 12:
              orderSql = " order by pac "+sortType;
              break;case 13:
              orderSql = " order by sho "+sortType;
              break;case 14:
              orderSql = " order by pas "+sortType;
              break;case 15:
              orderSql = " order by dri "+sortType;
              break;case 16:
              orderSql = " order by def "+sortType;
              break;case 17:
              orderSql = " order by phy "+sortType;
              break;case 18:
              orderSql = " order by overall_rate "+sortType;
              break;case 19:
              orderSql = " order by potential "+sortType;
              break;case 20:
              orderSql = " order by market_value "+sortType;
              break;case 21:
              orderSql = " order by wage "+sortType;
              break;case 22:
              orderSql = " order by player_id "+sortType;
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
		List<Sofifa> lstSofifa = new ArrayList<Sofifa>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstSofifa = Sofifa.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstSofifa.size()];
			for(int i=0; i<lstSofifa.size(); i++){
				Object[] obj = new Object[16];
				Sofifa sofifa = lstSofifa.get(i);
				obj[0] = sofifa.get("id");
				
				obj[1] = sofifa.get("fifa_name");
				obj[2] = sofifa.get("pac");
				obj[3] = sofifa.get("sho");
				obj[4] = sofifa.get("pas");
				obj[5] = sofifa.get("dri");
				obj[6] = sofifa.get("def");
				obj[7] = sofifa.get("phy");
				obj[8] = sofifa.get("overall_rate");
				obj[9] = sofifa.get("potential");
				obj[10] = sofifa.get("market_value");
				obj[11] = sofifa.get("wage");
				obj[12] = sofifa.get("player_id");
				obj[13] = sofifa.get("foot");
				obj[14] = sofifa.get("position");
				obj[15] = sofifa.get("number");

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
	
	public static void saveSofifa(Controller controller){
	
		String id = controller.getPara("id");
		Sofifa sofifa = null;
		if(StringUtils.isBlank(id)){
			sofifa = new Sofifa();
		}else{
			sofifa = Sofifa.dao.findById(id);
		}
		
		if(StringUtils.isNotBlank(controller.getPara("fifa_name"))){
			sofifa.set("fifa_name", controller.getPara("fifa_name"));
		}
		if(StringUtils.isNotBlank(controller.getPara("foot"))){
			sofifa.set("foot", controller.getPara("foot"));
		}
		if(StringUtils.isNotBlank(controller.getPara("inter_rep"))){
			sofifa.set("inter_rep", controller.getPara("inter_rep"));
		}
		if(StringUtils.isNotBlank(controller.getPara("unuse_foot"))){
			sofifa.set("unuse_foot", controller.getPara("unuse_foot"));
		}
		if(StringUtils.isNotBlank(controller.getPara("trick"))){
			sofifa.set("trick", controller.getPara("trick"));
		}
		if(StringUtils.isNotBlank(controller.getPara("work_rate"))){
			sofifa.set("work_rate", controller.getPara("work_rate"));
		}
		if(StringUtils.isNotBlank(controller.getPara("body_type"))){
			sofifa.set("body_type", controller.getPara("body_type"));
		}
		if(StringUtils.isNotBlank(controller.getPara("release_clause"))){
			sofifa.set("release_clause", controller.getPara("release_clause"));
		}
		if(StringUtils.isNotBlank(controller.getPara("position"))){
			sofifa.set("position", controller.getPara("position"));
		}
		if(StringUtils.isNotBlank(controller.getPara("number"))){
			sofifa.set("number", controller.getPara("number"));
		}
		if(StringUtils.isNotBlank(controller.getPara("contract"))){
			sofifa.set("contract", controller.getPara("contract"));
		}
		if(StringUtils.isNotBlank(controller.getPara("pac"))){
			sofifa.set("pac", controller.getPara("pac"));
		}
		if(StringUtils.isNotBlank(controller.getPara("sho"))){
			sofifa.set("sho", controller.getPara("sho"));
		}
		if(StringUtils.isNotBlank(controller.getPara("pas"))){
			sofifa.set("pas", controller.getPara("pas"));
		}
		if(StringUtils.isNotBlank(controller.getPara("dri"))){
			sofifa.set("dri", controller.getPara("dri"));
		}
		if(StringUtils.isNotBlank(controller.getPara("def"))){
			sofifa.set("def", controller.getPara("def"));
		}
		if(StringUtils.isNotBlank(controller.getPara("phy"))){
			sofifa.set("phy", controller.getPara("phy"));
		}
		if(StringUtils.isNotBlank(controller.getPara("overall_rate"))){
			sofifa.set("overall_rate", controller.getPara("overall_rate"));
		}
		if(StringUtils.isNotBlank(controller.getPara("potential"))){
			sofifa.set("potential", controller.getPara("potential"));
		}
		if(StringUtils.isNotBlank(controller.getPara("market_value"))){
			sofifa.set("market_value", controller.getPara("market_value"));
		}
		if(StringUtils.isNotBlank(controller.getPara("wage"))){
			sofifa.set("wage", controller.getPara("wage"));
		}
		if(StringUtils.isNotBlank(controller.getPara("player_id"))){
			sofifa.set("player_id", controller.getPara("player_id"));
		}

		save(sofifa);
	}
	
	public static void save(Sofifa sofifa){
		if(sofifa.get("id")==null){
			sofifa.save();
		}else{
			sofifa.update();
		}
	}
	
}
