package com.wwqk.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jfinal.core.Controller;
import com.wwqk.constants.LeagueENEnum;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.OddsProviderEnum;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchLive;
import com.wwqk.model.Team;
import com.wwqk.plugin.OddsUtils;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.StringUtils;

public class LiveController extends Controller {

	public void index(){
		
		Date nowDate = DateTimeUtils.addHours(new Date(), -2);
		StringBuilder sb = new StringBuilder("(");
		List<AllLiveMatch> lstResult = new ArrayList<AllLiveMatch>();
		List<AllLiveMatch> lstAllLiveMatch = AllLiveMatch.dao.find("select * from all_live_match where match_datetime > ? order by match_datetime asc", nowDate);
		for(AllLiveMatch match:lstAllLiveMatch){
			sb.append("'").append(match.getStr("match_key")).append("',");
		}
		if(sb.length()>1){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(")");
		List<MatchLive> lstMatchLive = MatchLive.dao.find("select match_key, live_name, live_url  from match_live where match_key in "+sb.toString());
		Map<String, List<MatchLive>> liveMap = new HashMap<String, List<MatchLive>>();
		for(MatchLive live : lstMatchLive){
			if(liveMap.get(live.getStr("match_key"))==null){
				List<MatchLive> list = new ArrayList<MatchLive>();
				liveMap.put(live.getStr("match_key"), list);
			}
			liveMap.get(live.getStr("match_key")).add(live);
		}
		
		Set<String> set = new HashSet<String>();
		for(AllLiveMatch match : lstAllLiveMatch){
			String dateWeek = match.getStr("match_date_week").replaceAll("\\s+", " ");
			dateWeek = StringUtils.trim(dateWeek.replace("星期", "周").replace("天", "日"));
			if(!set.contains(dateWeek)){
				set.add(dateWeek);
				AllLiveMatch group = new AllLiveMatch();
				group.set("match_date_week", dateWeek);
				lstResult.add(group);
			}
			match.getAttrs().put("liveList", liveMap.get(match.getStr("match_key")));
			lstResult.add(match);
		}
		setAttr("lstMatch", lstResult);
		
		render("live.jsp");
	}
	
	public void detail(){
		String id = getPara("id");
		id = CommonUtils.getRewriteId(id);
		if(StringUtils.isBlank(id)){
			redirect("/live");
		}
		AllLiveMatch match = AllLiveMatch.dao.findById(id);
		if(match==null){
			redirect("/live");
		}
		List<MatchLive> lstMatchLive = MatchLive.dao.find("select * from match_live where match_key = ?", match.getStr("match_key"));
		if(lstMatchLive.size()>0){
			setAttr("lstMatchLive", lstMatchLive);
		}
		
		if(StringUtils.isBlank(match.getStr("league_id"))){
			setAttr("match", match);
			render("liveDetail.jsp");
		}else{
			LeagueMatchHistory history = LeagueMatchHistory.dao.findFirst(
					"select * from league_match_history where leauge_id = ? home_team_id = ? and away_team_id = ? and year_show = ? ",
					match.getStr("league_id"),match.getStr("home_team_id"),match.getStr("away_team_id"),match.getStr("year_show"));
			
			if(history!=null){
				setAttr("lstOddsWH", OddsUtils.findOdds(history, OddsProviderEnum.WH.getKey(),this));
				setAttr("lstOddsBet365", OddsUtils.findOdds(history, OddsProviderEnum.BET365.getKey(),this));
				setAttr("lstOddsLB", OddsUtils.findOdds(history, OddsProviderEnum.LB.getKey(),this));
				setAttr("lstOddsML", OddsUtils.findOdds(history, OddsProviderEnum.ML.getKey(),this));
				setAttr("lstOddsBwin", OddsUtils.findOdds(history, OddsProviderEnum.BWIN.getKey(),this));
				OddsUtils.setStartEndOdds(history,this);
			}
			
			Team homeTeam = Team.dao.findById(history.getStr("home_team_id"));
			setAttr("homeTeam", homeTeam);
			String yearShow = history.getStr("year_show").substring(0,2)+"/"+history.getStr("year_show").substring(2);
			history.set("year_show", yearShow);
			setAttr("history", history);
			setAttr("leagueName", EnumUtils.getValue(LeagueEnum.values(), homeTeam.getStr("league_id")));
			setAttr("leagueENName", EnumUtils.getValue(LeagueENEnum.values(), homeTeam.getStr("league_id")));
			render("matchDetail.jsp");
		}
		
	}
	
}
