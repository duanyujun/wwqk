package com.wwqk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.StringUtils;

public class TeamController extends Controller {

	public void index(){
		String teamId = getPara("id");
		if(StringUtils.isNotBlank(teamId)){
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
				List<List<Player>> lstGroup = new ArrayList<List<Player>>();
				lstGroup.add(groupMap.get("前锋"));
				lstGroup.add(groupMap.get("中场"));
				lstGroup.add(groupMap.get("后卫"));
				lstGroup.add(groupMap.get("守门员"));
				setAttr("lstGroup", lstGroup);
			}
			
			Team team = Team.dao.findById(teamId);
			setAttr("team", team);
			
			//最近五场比赛
			List<LeagueMatchHistory> lstMatchHistory = LeagueMatchHistory.dao.find("select * from league_match_history where home_team_id = ? or away_team_id = ? order by round desc limit 0,5 ", teamId, teamId);
			setAttr("lstMatchHistory", lstMatchHistory);
			
			//衣服是否需要背景色
			if(CommonUtils.clothNeedBgColor(teamId)){
				setAttr("clothBg", 1);
			}
			
			//联赛排名
			List<LeaguePosition> positionList = LeaguePosition.dao.find("select * from league_position where league_id = ? ORDER BY rank ASC ", team.getStr("league_id"));
			setAttr("positionList", positionList);
			
		}else{
			redirect("/match");
		}
		
		render("team.jsp");
	}
	
}
