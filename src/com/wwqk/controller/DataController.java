package com.wwqk.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.LeagueAssists;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.LeagueShooter;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.StringUtils;

public class DataController extends Controller {

	public void index(){
		String leagueId = getPara("leagueId");
		if(StringUtils.isBlank(leagueId)){
			leagueId = "1";
			setAttr("leagueId", leagueId);
		}else{
			setAttr("leagueId", leagueId);
		}
		setAttr("leagueName", EnumUtils.getValue(LeagueEnum.values(), leagueId));
		
		List<LeaguePosition> positionList = LeaguePosition.dao.find("select * from league_position where league_id = ? ORDER BY rank ASC ", leagueId);
		setAttr("positionList", positionList);
		
		List<LeagueShooter> shooterList = LeagueShooter.dao.find("select * from league_shooter where league_id = ? ORDER BY goal_count desc, penalty_count asc ", leagueId);
		setAttr("shooterList", shooterList);
		
		List<LeagueAssists> assistsList = LeagueAssists.dao.find("select * from league_assists where league_id = ? ORDER BY rank ASC ", leagueId);
		setAttr("assistsList", assistsList);
		
		render("data.jsp");
	}
	
}
