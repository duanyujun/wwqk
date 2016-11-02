package com.wwqk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.League;
import com.wwqk.utils.StringUtils;

public class AdminController extends Controller {

	public void index(){
		
		render("login.jsp");
	}
	
	public void listLeague(){
		String sumSql = "select count(*) from league";
		String sql = "select * from league";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			whereSql = " where name like '%"+search+"%'"+" OR name_en like '%"+search+"%'";
		}
		
		int sortColumn = getParaToInt("order[0][column]");
		String sortType = getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by name "+sortType;
			break;
		case 2:
			orderSql = " order by name_en "+sortType;
			break;
		case 3:
			orderSql = " order by online "+sortType;
			break;
		default:
			break;
		}
		
		int start = getParaToInt("start");
		int length = getParaToInt("length");
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
				Object[] obj = new Object[5];
				League roles = lstLeagues.get(i);
				obj[0] = roles.get("id");
				obj[1] = roles.get("name");
				obj[2] = roles.get("name_en");
				obj[3] = roles.get("online");
				obj[4] = roles.get("league_url");
				data[i] = obj;
			}
		}
		if(data==null){
			data = new Object[0];
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("draw", getParaToInt("draw"));
		map.put("recordsTotal", recordsTotal);
		map.put("recordsFiltered", recordsTotal);
		map.put("data", data);
		
		renderJson(map);
	}
	
	public void editLeague(){
		String id = getPara("id");
		if(id!=null){
			League league = League.dao.findById(id);
			setAttr("league", league);
		}
		
		render("admin/leagueForm.jsp");
	}
	
	public void deleteLeague(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from users "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
}
