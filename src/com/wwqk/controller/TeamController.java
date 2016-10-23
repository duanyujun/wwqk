package com.wwqk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.wwqk.model.Player;

public class TeamController extends Controller {

	public void index(){
		int teamId = getParaToInt("team_id");
		List<Player> lstPlayer = Player.dao.find("select * from player where team_id = ? ", teamId);
		Map<String, List<Player>> groupMap = new HashMap<String, List<Player>>();
		if(lstPlayer.size()!=0){
			for(Player player : lstPlayer){
				if(groupMap.get(player.get("position"))==null){
					List<Player> oneGroupList = new ArrayList<Player>();
					groupMap.put(player.get("position"), oneGroupList);
				}
				groupMap.get(player.get("position")).add(player);
			}
			//进行排序
			List<List<Player>> lstResult = new ArrayList<List<Player>>();
			lstResult.add(groupMap.get("前锋"));
			lstResult.add(groupMap.get("中场"));
			lstResult.add(groupMap.get("前锋"));
			lstResult.add(groupMap.get("前锋"));
		}
		List<String> playerlist = new ArrayList<String>();
		for(int i=0; i<7; i++){
			playerlist.add(i+"");
		}
		setAttr("playerlist", playerlist);
		render("team.jsp");
	}
	
}
