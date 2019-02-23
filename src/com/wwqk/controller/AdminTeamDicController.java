package com.wwqk.controller;

import java.util.Map;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.TeamDic;
import com.wwqk.service.TeamDicService;
import com.wwqk.utils.StringUtils;

public class AdminTeamDicController extends Controller {

	public void listTeamDic(){
		render("admin/teamDicList.jsp");
	}
	
	public void teamDicData(){
		Map<Object, Object> map = TeamDicService.teamDicData(this);
		renderJson(map);
	}
	
	public void editTeamDic(){
		String id = getPara("id");
		if(id!=null){
			TeamDic teamDic = TeamDic.dao.findById(id);
			setAttr("teamDic", teamDic);
		}
		render("admin/teamDicForm.jsp");
	}
	
	public void saveTeamDic(){
		TeamDicService.saveTeamDic(this);
		renderJson(1);
	}
	
	public void deleteTeamDic(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from team_dic "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
}
