package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.PageUtils;
import com.wwqk.utils.StringUtils;

public class MatchHistoryController extends Controller {

	public void index(){
		String area = "全部";
		String whereSql = "";
		String id = getPara("id");
		if(StringUtils.isNotBlank(id)){
			boolean isFromLeague = false;
			//判断是从league还是team来的
			if(CommonUtils.isFromLeague(id)){
				isFromLeague = true;
			}
			
			id = CommonUtils.getRewriteId(id);
			if(StringUtils.isNotBlank(id)){
				if(isFromLeague){
					whereSql = " and league_id = " + id;
					area = EnumUtils.getValue(LeagueEnum.values(), id);
				}else{
					whereSql = " and team_id = " + id;
					Team team = Team.dao.findById(id);
					area = team.getStr("name");
				}
			}else{
				whereSql ="";
			}
		}
		
		Page<LeagueMatchHistory> matchPage = LeagueMatchHistory.dao.paginate(getParaToInt("pageNumber", 1), 50, whereSql);
		setAttr("matchPage", matchPage);
		setAttr("pageUI", PageUtils.calcStartEnd(matchPage));
		setAttr("area", area);
		render("history.jsp");
	}
	
}
