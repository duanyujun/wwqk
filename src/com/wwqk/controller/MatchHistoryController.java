package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.PageUtils;
import com.wwqk.utils.StringUtils;

public class MatchHistoryController extends Controller {

	public void index(){
		String whereSql = "";
		String id = getPara("id");
		if(StringUtils.isNotBlank(id)){
			id = CommonUtils.getRewriteId(id);
			if(StringUtils.isNotBlank(id)){
				whereSql = " and league_id = "+id;
			}
		}
		
		Page<LeagueMatchHistory> matchPage = LeagueMatchHistory.dao.paginate(getParaToInt("pageNumber", 1), 50, whereSql);
		setAttr("matchPage", matchPage);
		setAttr("pageUI", PageUtils.calcStartEnd(matchPage));
		
		render("history.jsp");
	}
	
}
