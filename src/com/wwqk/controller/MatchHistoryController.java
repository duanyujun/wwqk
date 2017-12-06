package com.wwqk.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.LeagueENEnum;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.MenuEnum;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.PageUtils;
import com.wwqk.utils.StringUtils;

public class MatchHistoryController extends Controller {
	
	public void index(){
		String sourceParam = getPara("id");
		String id = getPara("id");
		int pageNumber = getParaToInt("pageNumber", 1);
		//联赛id
		String leagueId = null;
		//获取轮数
		int currentRound = 0;
		//赛季
		int year = 0;
		if(StringUtils.isNotBlank(id)){
			if(id.contains("-page-")){
				pageNumber = CommonUtils.getPageNo(id);
				//去掉pageNo段
				id = id.replaceAll("-page-\\d+", "");
				sourceParam = id;
			}
			String roundStr = CommonUtils.getRealParam("r", id);
			if(StringUtils.isNotBlank(roundStr)){
				currentRound = Integer.valueOf(roundStr);
				sourceParam = sourceParam.replaceAll("-r\\d+", "");
			}
			String yearStr = CommonUtils.getRealParam("y", id);
			if(StringUtils.isNotBlank(yearStr)){
				year = Integer.valueOf(yearStr);
				sourceParam = sourceParam.replaceAll("-y\\d+", "");
			}
			id = CommonUtils.getRewriteId(id);
		}
		
		boolean isFromLeague = false;
		//判断是从league还是team来的
		if(CommonUtils.isFromLeague(id)){
			isFromLeague = true;
		}
		
		if(StringUtils.isBlank(sourceParam)){
			sourceParam = LeagueENEnum.YC.getValue();
		}else{
			int lastIdx = sourceParam.lastIndexOf("-");
			sourceParam = sourceParam.substring(0, lastIdx);
			if(currentRound!=0){
				sourceParam = sourceParam.replaceAll("-r\\d+", "");
				sourceParam+="-r"+currentRound;
			}
			if(year!=0){
				sourceParam = sourceParam.replaceAll("-y\\d+", "");
				sourceParam+="-y"+year;
			}
		}
		setAttr("filter", sourceParam);
		
		//赛季
		List<LeagueMatchHistory> lstYear = LeagueMatchHistory.dao.find("SELECT DISTINCT(year), year_show FROM league_match_history order by year desc");
		for(LeagueMatchHistory history : lstYear){
			String yearShow = history.getStr("year_show");
			yearShow = yearShow.substring(0,2)+"/"+yearShow.substring(2)+"赛季";
			history.set("year_show", yearShow);
		}
		if(year==0){
			year = lstYear.get(0).getInt("year");
		}
		setAttr("year", year);
		setAttr("currentRound", currentRound);
		
		String whereSql = "";
		String area = "全部";
		if(isFromLeague){
			leagueId = id;
			if(StringUtils.isBlank(leagueId)){
				leagueId = LeagueEnum.YC.getKey();	
			}
			whereSql = " and league_id = " + leagueId;
			if(year!=0){
				whereSql += " and year = "+year;
			}
			if(currentRound!=0){
				whereSql += " and match_round = "+currentRound;
			}
			area = EnumUtils.getValue(LeagueEnum.values(), id);
			setAttr("leagueId", leagueId);
			setAttr("id", leagueId);
		}else{
			Team team = Team.dao.findById(id);
			leagueId = team.getStr("league_id");
			whereSql = " and league_id = " + leagueId;
			if(year!=0){
				whereSql += " and year = "+year;
			}
			if(currentRound!=0){
				whereSql += " and match_round = "+currentRound;
			}
			whereSql += " and (home_team_id = " + id+" or away_team_id = "+id+")";
			area = team.getStr("name");
			setAttr("id", team.get("id"));
		}
		setAttr("leagueId", leagueId);
		
		int pageSize = 50;
		if(LeagueEnum.DJ.getKey().equals(leagueId)){
			pageSize = 54;
		}
		Page<LeagueMatchHistory> matchPage = LeagueMatchHistory.dao.paginate(pageNumber, pageSize, whereSql);
		setWinLoseColor(area, matchPage);
		setAttr("matchPage", matchPage);
		setAttr("pageUI", PageUtils.calcStartEnd(matchPage));
		setAttr("area", area);
		
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
		
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.DATA.getKey());
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
