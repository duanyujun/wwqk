package com.wwqk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchLive;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.StringUtils;

public class MatchController extends Controller {

	public void index(){
		List<LeagueMatch> lstMatch = LeagueMatch.dao.find("select m.*,l.id league_id, l.name league_name from league_match m, league l where m.league_id = l.id order by m.id asc ");
		Map<String, List<LeagueMatch>> groupMap = new HashMap<String, List<LeagueMatch>>();
		if(lstMatch.size()>0){
			for(LeagueMatch match : lstMatch){
				if(groupMap.get(match.getStr("league_name"))==null){
					List<LeagueMatch> oneGroupList = new ArrayList<LeagueMatch>();
					groupMap.put(match.getStr("league_name"), oneGroupList);
				}
				groupMap.get(match.getStr("league_name")).add(match);
			}
			//进行排序
			List<List<LeagueMatch>> lstGroup = new ArrayList<List<LeagueMatch>>();
			List<LeagueMatch> lstYC = groupMap.get("英超");
			List<LeagueMatch> lstXJ = groupMap.get("西甲");
			List<LeagueMatch> lstDJ = groupMap.get("德甲");
			List<LeagueMatch> lstYJ = groupMap.get("意甲");
			List<LeagueMatch> lstFJ = groupMap.get("法甲");
			Collections.sort(lstYC);
			Collections.sort(lstXJ);
			Collections.sort(lstDJ);
			Collections.sort(lstYJ);
			Collections.sort(lstFJ);
			
			lstGroup.add(lstYC);
			lstGroup.add(lstXJ);
			lstGroup.add(lstDJ);
			lstGroup.add(lstYJ);
			lstGroup.add(lstFJ);
			setAttr("lstGroup", lstGroup);
		}
		render("match.jsp");
	}
	
	public void detail(){
		String matchKey = getPara("matchKey");
		matchKey = CommonUtils.getRewriteMatchKey(matchKey);
		if(StringUtils.isBlank(matchKey)){
			redirect("/match");
		}
		List<MatchLive> lstMatchLive = MatchLive.dao.find("select * from match_live where match_key = ?", matchKey);
		if(lstMatchLive.size()>0){
			setAttr("lstMatchLive", lstMatchLive);
		}
		//主队球场
		LeagueMatchHistory history = LeagueMatchHistory.dao.findById(matchKey);
		Team homeTeam = Team.dao.findById(history.getStr("home_team_id"));
		//Team awayTeam = Team.dao.findById(history.getStr("away_team_id"));
		setAttr("homeTeam", homeTeam);
		//setAttr("awayTeam", awayTeam);
		setAttr("history", history);
		setAttr("leagueName", EnumUtils.getValue(LeagueEnum.values(), homeTeam.getStr("league_id")));
		render("matchDetail.jsp");
	}
	
}
