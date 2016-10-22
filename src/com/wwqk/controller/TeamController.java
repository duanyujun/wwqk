package com.wwqk.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;

public class TeamController extends Controller {

	public void index(){
		List<String> playerlist = new ArrayList<String>();
		for(int i=0; i<7; i++){
			playerlist.add(i+"");
		}
		setAttr("playerlist", playerlist);
		render("team.jsp");
	}
	
}
