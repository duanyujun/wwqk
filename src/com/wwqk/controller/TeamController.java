package com.wwqk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.LeagueENEnum;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.MenuEnum;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.StringUtils;

public class TeamController extends Controller {

	public void index(){
		String teamId = getPara("id");
		teamId = CommonUtils.getRewriteId(teamId);
		if(StringUtils.isBlank(teamId)){
			redirect("/match");
			return;
		}
		
		List<Player> lstPlayer = Player.dao.find("select * from player where team_id = ? ", teamId);
		Map<String, List<Player>> groupMap = new HashMap<String, List<Player>>();
		if(lstPlayer.size()!=0){
			for(Player player : lstPlayer){
				if(groupMap.get(player.get("position"))==null){
					List<Player> oneGroupList = new ArrayList<Player>();
					groupMap.put(player.getStr("position"), oneGroupList);
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
		if(StringUtils.isBlank(team.getStr("venue_img_local"))){
			team.set("venue_img_local", team.getStr("venue_small_img_local"));
		}
		setAttr("team", team);
		
		if(StringUtils.isNotBlank(team.getStr("league_id"))){
			MatchSourceSina source = MatchSourceSina.dao.findFirst("select * from match_source_sina where league_id = ?",team.get("league_id"));
			//最近五场比赛
			List<LeagueMatchHistory> lstMatchHistory = LeagueMatchHistory.dao.find("select * from league_match_history where year = ? and match_round <=? and (home_team_id = ? or away_team_id = ?) order by match_round desc limit 0,5 ",source.get("year"),source.get("current_round"), teamId, teamId);
			setAttr("lstMatchHistory", lstMatchHistory);
		}
		
		
		//衣服是否需要背景色
		if(CommonUtils.clothNeedBgColor(teamId)){
			setAttr("clothBg", 1);
		}
		
		setAttr("leagueName", EnumUtils.getValue(LeagueEnum.values(), team.getStr("league_id")));
		setAttr("leagueENName", EnumUtils.getValue(LeagueENEnum.values(), team.getStr("league_id")));
		
		//联赛排名
		List<LeaguePosition> positionList = LeaguePosition.dao.find("select * from league_position where league_id = ? ORDER BY rank ASC ", team.getStr("league_id"));
		int count = 0;
		for(LeaguePosition position:positionList){
			++count;
			if(position.getStr("team_id").equals(teamId)){
				setAttr("postion", count);
			}
		}
		setAttr("positionList", positionList);	
		
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.DATA.getKey());
		render("new/team.jsp");
	}
	
}
