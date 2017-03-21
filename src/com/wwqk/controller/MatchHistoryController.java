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
		int pageNumber = getParaToInt("pageNumber", 1);
		if(StringUtils.isNotBlank(id)){
			if(id.contains("-page-")){
				pageNumber = CommonUtils.getPageNo(id);
				//去掉pageNo段
				id = id.replaceAll("-page-\\d+", "");
			}
			setAttr("filter", id);
			
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
					whereSql = " and (home_team_id = " + id+" or away_team_id = "+id+")";
					Team team = Team.dao.findById(id);
					area = team.getStr("name");
				}
			}else{
				whereSql ="";
			}
		}
		
		Page<LeagueMatchHistory> matchPage = LeagueMatchHistory.dao.paginate(pageNumber, 50, whereSql);
		setWinLoseColor(area, matchPage);
		setAttr("matchPage", matchPage);
		setAttr("pageUI", PageUtils.calcStartEnd(matchPage));
		setAttr("area", area);
		render("history.jsp");
	}
	
	private void setWinLoseColor(String area, Page<LeagueMatchHistory> matchPage){
		for(LeagueMatchHistory match : matchPage.getList()){
			if(match.getStr("result").contains("-")){
				String[] pointsArray = match.getStr("result").split("-");
				if(match.getStr("home_team_name").equals(area)){
					if(Integer.valueOf(pointsArray[0].trim())>Integer.valueOf(pointsArray[1].trim())){
						match.set("home_team_name", "<span class='red_result'><b>"+match.getStr("home_team_name")+"</b></span>");
					}else if(Integer.valueOf(pointsArray[0].trim())<Integer.valueOf(pointsArray[1].trim())){
						match.set("home_team_name", "<span class='lose_result'><b>"+match.getStr("home_team_name")+"</b></span>");
					}
				}else if(match.getStr("away_team_name").equals(area)){
					if(Integer.valueOf(pointsArray[0].trim())<Integer.valueOf(pointsArray[1].trim())){
						match.set("away_team_name", "<span class='red_result'><b>"+match.getStr("away_team_name")+"</b></span>");
					}else if(Integer.valueOf(pointsArray[0].trim())>Integer.valueOf(pointsArray[1].trim())){
						match.set("away_team_name", "<span class='lose_result'><b>"+match.getStr("away_team_name")+"</b></span>");
					}
				}
			}
		}
	}
	
}
