package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.utils.PageUtils;

public class MatchHistoryController extends Controller {

	public void index(){
		Page<LeagueMatchHistory> matchPage = LeagueMatchHistory.dao.paginate(getParaToInt("pageNumber", 1), 50, "");
		setAttr("matchPage", matchPage);
		setAttr("pageUI", PageUtils.calcStartEnd(matchPage));
		
		render("history.jsp");
	}
	
}
