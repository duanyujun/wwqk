package com.wwqk.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jfinal.core.Controller;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.LeagueENEnum;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.MenuEnum;
import com.wwqk.constants.OddsProviderEnum;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchGuess;
import com.wwqk.model.MatchLive;
import com.wwqk.model.Team;
import com.wwqk.model.TipsAll;
import com.wwqk.model.TipsMatch;
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
		//过滤相同比赛
		//filterSameMatch(lstAllLiveMatch);
		
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
			if(!"英超".equals(match.getStr("league_name"))
					&& !"西甲".equals(match.getStr("league_name"))
					&& !"德甲".equals(match.getStr("league_name")) 
					&& !"意甲".equals(match.getStr("league_name")) 
					&& !"法甲".equals(match.getStr("league_name"))){
				match.set("league_id", "");
			}
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
		
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.LIVE.getKey());
		render("live.jsp");
	}
	
	public void detail(){
		String id = getPara("id");
		id = CommonUtils.getRewriteId(id);
		if(StringUtils.isBlank(id)){
			redirect("/live");
			return;
		}
		AllLiveMatch match = AllLiveMatch.dao.findById(id);
		if(match==null ){
			redirect("/live");
			return;
		}
		List<MatchLive> lstMatchLive = MatchLive.dao.find("select * from match_live where match_key = ?", match.getStr("match_key"));
		if(lstMatchLive.size()>0){
			setAttr("lstMatchLive", lstMatchLive);
		}
		
		//网友推荐
		List<MatchGuess> lstMatchGuesses = MatchGuess.dao.find("select * from match_guess where live_match_id = ? ", id);
		setAttr("lstGuess", lstMatchGuesses);
		if(lstMatchGuesses.size()>0){
			setAttr("guessId", lstMatchGuesses.get(0).get("id"));
		}
		
		//处理杯赛中两联赛中的队伍问题
		if(!"英超".equals(match.getStr("league_name"))
				&& !"西甲".equals(match.getStr("league_name"))
				&& !"德甲".equals(match.getStr("league_name")) 
				&& !"意甲".equals(match.getStr("league_name")) 
				&& !"法甲".equals(match.getStr("league_name"))){
			match.set("league_id", "");
		}
		
		if(StringUtils.isBlank(match.getStr("league_id"))){
			List<TipsAll> lstTips =TipsAll.dao.find("select * from tips_all where game_id = ? order by is_home_away asc", match.getStr("game_id"));
			if(lstTips.size()!=0){
				TipsMatch tipsMatch = TipsMatch.dao.findFirst("select * from tips_match where live_match_id = ?", id);
				if(tipsMatch!=null){
					if(StringUtils.isNotBlank(tipsMatch.getStr("home_absence"))){
						TipsAll tipsAllHome = new TipsAll();
						tipsAllHome.set("is_home_away", "0");
						tipsAllHome.set("is_good_bad", "1");
						tipsAllHome.set("news", "伤停情况："+tipsMatch.getStr("home_absence"));
						lstTips.add(0, tipsAllHome);
					}
					if(StringUtils.isNotBlank(tipsMatch.getStr("away_absence"))){
						TipsAll tipsAllAway = new TipsAll();
						tipsAllAway.set("is_home_away", "1");
						tipsAllAway.set("is_good_bad", "1");
						tipsAllAway.set("news", "伤停情况："+tipsMatch.getStr("away_absence"));
						lstTips.add(tipsAllAway);
					}
				}
				setAttr("lstTips", lstTips);
			}
			setAttr("match", match);
			
			setAttr(CommonConstants.MENU_INDEX, MenuEnum.LIVE.getKey());
			render("liveDetail.jsp");
		}else{
			LeagueMatchHistory history = LeagueMatchHistory.dao.findFirst(
					"select * from league_match_history where leauge_id = ? and home_team_id = ? and away_team_id = ? and year_show = ? ",
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
			
			setAttr(CommonConstants.MENU_INDEX, MenuEnum.LIVE.getKey());
			render("matchDetail.jsp");
		}
		
	}
	
	/**
	 * 
	 * @param lstAllLiveMatch
	 */
	private static void filterSameMatch(List<AllLiveMatch> lstAllLiveMatch){
		Map<String, AllLiveMatch> homeMap = new HashMap<String, AllLiveMatch>();
		Map<String, AllLiveMatch> awayMap = new HashMap<String, AllLiveMatch>();
		Set<AllLiveMatch> needDelSet = new HashSet<AllLiveMatch>();
		for(int i=0; i<lstAllLiveMatch.size(); i++){
			AllLiveMatch oldMatch = homeMap.get(lstAllLiveMatch.get(i).getStr("home_team_name"));
			if(oldMatch!=null){
				//判断比赛时间
				if(DateTimeUtils.isSameDate(oldMatch.getDate("match_datetime"), 
						lstAllLiveMatch.get(i).getDate("match_datetime"))){
					needDelSet.add(lstAllLiveMatch.get(i));
				}
			}else{
				//判断客队
				oldMatch = awayMap.get(lstAllLiveMatch.get(i).getStr("away_team_name"));
				if(oldMatch!=null){
					if(DateTimeUtils.isSameDate(oldMatch.getDate("match_datetime"), 
							lstAllLiveMatch.get(i).getDate("match_datetime"))){
						needDelSet.add(lstAllLiveMatch.get(i));
					}
				}
			}
			homeMap.put(lstAllLiveMatch.get(i).getStr("home_team_name"), lstAllLiveMatch.get(i));
			awayMap.put(lstAllLiveMatch.get(i).getStr("away_team_name"), lstAllLiveMatch.get(i));
		}
		if(needDelSet.size()>0){
			for(AllLiveMatch match:needDelSet){
				lstAllLiveMatch.remove(match);
			}
		}
	}
	
}
