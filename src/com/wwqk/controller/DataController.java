package com.wwqk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.LeagueAssists;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.LeagueShooter;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.StringUtils;

public class DataController extends Controller {

	public void index(){
		String leagueId = getPara("leagueId");
		leagueId = CommonUtils.getRewriteId(leagueId);
		if(StringUtils.isBlank(leagueId)){
			leagueId = "1";
		}
		
		setAttr("leagueId", leagueId);
		setAttr("leagueName", EnumUtils.getValue(LeagueEnum.values(), leagueId));
		
		List<LeaguePosition> positionList = LeaguePosition.dao.find("select * from league_position where league_id = ? ORDER BY rank ASC ", leagueId);
		setAttr("positionList", positionList);
		
		List<LeagueShooter> shooterList = LeagueShooter.dao.find("select * from league_shooter where league_id = ? and goal_count!=0 ORDER BY goal_count desc, penalty_count asc ", leagueId);
		setAttr("shooterList", shooterList);
		
		List<LeagueAssists> assistsList = LeagueAssists.dao.find("select * from league_assists where league_id = ? and assists_count!=0 ORDER BY rank ASC ", leagueId);
		setAttr("assistsList", assistsList);
		
		//查询比赛
		MatchSourceSina source = MatchSourceSina.dao.findFirst("select * from match_source_sina where league_id = ?", leagueId);
		List<LeagueMatchHistory> lstMatch = LeagueMatchHistory.dao.find(
				"select * from league_match_history where league_id = ? and year = ? and round = ? order by match_date desc",leagueId,source.get("year"),source.get("current_round"));
		//查询球队信息
		List<Team> lstTeam = Team.dao.find("select id,team_img_local from team where league_id = ?", leagueId);
		Map<String, String> teamMap = new HashMap<String, String>();
		for(Team team : lstTeam){
			teamMap.put(team.getStr("id"), team.getStr("team_img_local"));
		}
		for(LeagueMatchHistory match:lstMatch){
			match.getAttrs().put("home_team_img", teamMap.get(match.getStr("home_team_id")));
			match.getAttrs().put("away_team_img", teamMap.get(match.getStr("away_team_id")));
		}
		
		setAttr("lstMatch", lstMatch);
		render("data.jsp");
	}
	
}
