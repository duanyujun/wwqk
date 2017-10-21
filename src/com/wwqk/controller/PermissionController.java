package com.wwqk.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.Permissions;
import com.wwqk.utils.StringUtils;

public class PermissionController extends Controller {

	public void index(){
		render("permission/pmList.jsp");
	}
	
	public void list(){
		String sumSql = "select count(*) from permissions";
		String sql = "select * from permissions";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search =search.replaceAll("'", "").trim();
			whereSql = " where name like '%"+search+"%'"+" OR pvalue like '%"+search+"%'"+" OR description like '%"+search+"%'";
		}
		
		int sortColumn = getParaToInt("order[0][column]");
		String sortType = getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by name "+sortType;
			break;
		case 2:
			orderSql = " order by ptype "+sortType;
			break;
		case 3:
			orderSql = " order by pvalue "+sortType;
			break;
		case 4:
			orderSql = " order by description "+sortType;
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
		List<Permissions> lstPerm = new ArrayList<Permissions>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstPerm = Permissions.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstPerm.size()];
			for(int i=0; i<lstPerm.size(); i++){
				Object[] obj = new Object[5];
				Permissions roles = lstPerm.get(i);
				obj[0] = roles.get("id");
				obj[1] = roles.get("name");
				obj[2] = roles.get("ptype");
				obj[3] = roles.get("pvalue");
				obj[4] = roles.get("description");
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
	
	public void edit(){
		String id = getPara("id");
		if(id!=null){
			Permissions permission = Permissions.dao.findById(id);
			setAttr("permission", permission);
		}
		
		render("permission/pmForm.jsp");
	}
	
	public void save() throws UnsupportedEncodingException{
		String name = StringUtils.decode(getPara("name"));
		String ptype = getPara("ptype");
		String pvalue = getPara("pvalue");
		String description = StringUtils.decode(getPara("description"));
		
		Permissions permissions = new Permissions();
		permissions.set("name", name);
		permissions.set("ptype", ptype);
		permissions.set("pvalue",pvalue);
		permissions.set("description",description);
		if(StringUtils.isNotBlank(getPara("id"))){
			permissions.set("id", getPara("id"));
			permissions.update();
		}else{
			permissions.save();
		}
		
		renderJson(1);
	}
	
	public void del(){
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
