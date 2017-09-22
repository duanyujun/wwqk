package com.wwqk.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.constants.LeagueENEnum;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.PageUtils;
import com.wwqk.utils.StringUtils;

public class MatchHistoryController extends Controller {

	public void index(){
		String filter = null;
		String leagueId = null;
		//获取轮数
		int currentRound = 0;
		//赛季
		int year = 0;
		String area = "全部";
		String whereSql = "";
		String id = getPara("id");
		String finalId = null;
		int pageNumber = getParaToInt("pageNumber", 1);
		if(StringUtils.isNotBlank(id)){
			String roundStr = CommonUtils.getRealParam("r", id);
			if(StringUtils.isNotBlank(roundStr)){
				currentRound = Integer.valueOf(roundStr);
			}
			String yearStr = CommonUtils.getRealParam("y", id);
			if(StringUtils.isNotBlank(yearStr)){
				year = Integer.valueOf(yearStr);
			}
			if(id.contains("-page-")){
				pageNumber = CommonUtils.getPageNo(id);
				//去掉pageNo段
				id = id.replaceAll("-page-\\d+", "");
			}
			filter = id;
			boolean isFromLeague = false;
			//判断是从league还是team来的
			if(CommonUtils.isFromLeague(id)){
				isFromLeague = true;
			}
			
			id = CommonUtils.getRewriteId(id);
			finalId = id;
			if(StringUtils.isNotBlank(id)){
				if(isFromLeague){
					leagueId = id;
					whereSql = " and league_id = " + id;
					if(year!=0){
						whereSql += " and year = "+year;
					}
					if(currentRound!=0){
						whereSql += " and match_round = "+currentRound;
					}
					area = EnumUtils.getValue(LeagueEnum.values(), id);
					setAttr("leagueId", leagueId);
				}else{
					if(year!=0){
						whereSql += " and year = "+year;
					}
					if(currentRound!=0){
						whereSql += " and match_round = "+currentRound;
					}
					whereSql = " and (home_team_id = " + id+" or away_team_id = "+id+")";
					Team team = Team.dao.findById(id);
					area = team.getStr("name");
					leagueId = team.getStr("league_id");
				}
			}
		}else{
			if(leagueId==null){
				leagueId = LeagueEnum.YC.getKey();	
			}
			finalId = leagueId;
			whereSql = " and league_id = " + leagueId;
			setAttr("leagueId", leagueId);
			filter = EnumUtils.getValue(LeagueENEnum.values(), leagueId)+"-"+leagueId;
		}
		
		setAttr("filter", filter);
		setAttr("finalId", finalId);
		
		Page<LeagueMatchHistory> matchPage = LeagueMatchHistory.dao.paginate(pageNumber, 50, whereSql);
		setWinLoseColor(area, matchPage);
		setAttr("matchPage", matchPage);
		setAttr("pageUI", PageUtils.calcStartEnd(matchPage));
		setAttr("area", area);
		
		
		//赛季
		List<LeagueMatchHistory> lstYear = LeagueMatchHistory.dao.find("SELECT DISTINCT(year), year_show FROM league_match_history order by year desc");
		for(LeagueMatchHistory history : lstYear){
			String yearShow = history.getStr("year_show");
			yearShow = yearShow.substring(0,2)+"/"+yearShow.substring(2)+"赛季";
			history.set("year_show", yearShow);
		}
		if(year==0){
			year = lstYear.get(0).getInt("year");
			setAttr("year", year);
		}
		if(currentRound!=0){
			setAttr("currentRound", year);
		}
		
		List<String> lstRound = new ArrayList<String>();
		int maxRound = 38;
		//轮数
		if(LeagueEnum.DJ.getKey().equals(leagueId)){
			maxRound = 34;
		}
		for(int i=1; i<=maxRound; i++){
			lstRound.add(String.valueOf(i));
		}
		setAttr("lstRound", lstRound);
		
		setAttr("lstYear", lstYear);
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
