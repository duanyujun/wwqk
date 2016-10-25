package com.wwqk.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.LeagueShooter;
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
		
		List<LeaguePosition> positionList = LeaguePosition.dao.find("select * from league_position where league_id = ? ORDER BY rank ASC ", leagueId);
		setAttr("positionList", positionList);
		
		List<LeagueShooter> shooterList = LeagueShooter.dao.find("select * from league_shooter where league_id = ? ORDER BY rank ASC ", leagueId);
		setAttr("shooterList", shooterList);
		
		List<String> assistlist = new ArrayList<String>();
		for(int i=0; i<15; i++){
			assistlist.add(i+"");
		}
		setAttr("assistlist", assistlist);
		
		render("data.jsp");
	}
	
}
