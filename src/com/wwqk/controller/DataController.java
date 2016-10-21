package com.wwqk.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.wwqk.utils.StringUtils;

public class DataController extends Controller {

	public void index(){
		String leagueId = getPara("leagueId");
		if(StringUtils.isBlank(leagueId)){
			setAttr("leagueId", 1);
		}else{
			setAttr("leagueId", leagueId);
		}
		List<String> positionlist = new ArrayList<String>();
		for(int i=0; i<20; i++){
			positionlist.add(i+"");
		}
		setAttr("positionlist", positionlist);
		
		List<String> shooterlist = new ArrayList<String>();
		for(int i=0; i<15; i++){
			shooterlist.add(i+"");
		}
		setAttr("shooterlist", shooterlist);
		
		List<String> assistlist = new ArrayList<String>();
		for(int i=0; i<15; i++){
			assistlist.add(i+"");
		}
		setAttr("assistlist", assistlist);
		
		render("data.jsp");
	}
	
}
