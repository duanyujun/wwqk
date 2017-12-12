package com.wwqk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.LeagueENEnum;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.MenuEnum;
import com.wwqk.constants.OddsProviderEnum;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchLive;
import com.wwqk.model.Team;
import com.wwqk.model.TipsAll;
import com.wwqk.model.TipsMatch;
import com.wwqk.model.Videos;
import com.wwqk.plugin.OddsUtils;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.StringUtils;

public class MatchController extends Controller {

	public void index(){
		List<LeagueMatch> lstMatch = LeagueMatch.dao.find("select m.*,l.id league_id, l.name league_name, l.name_en league_name_en from league_match m, league l where m.league_id = l.id order by m.id asc ");
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
			redirect("/live");
			return;
		}
		//处理以前的样式2017-08-12-679vs726
		if(matchKey.indexOf("-") != matchKey.lastIndexOf("-")){
			String year = matchKey.substring(0,4);
			year = year.replace("2016", "1617").replace("2017", "1718");
			String teamIds = matchKey.substring(matchKey.lastIndexOf("-"));
			matchKey = year + teamIds;
		}
		
		List<MatchLive> lstMatchLive = MatchLive.dao.find("select * from match_live where match_key = ?", matchKey);
		if(lstMatchLive.size()>0){
			setAttr("lstMatchLive", lstMatchLive);
		}
		//主队球场
		LeagueMatchHistory history = LeagueMatchHistory.dao.findById(matchKey);
		
		if(history!=null){
			setAttr("lstOddsWH", OddsUtils.findOdds(history, OddsProviderEnum.WH.getKey(),this));
			setAttr("lstOddsBet365", OddsUtils.findOdds(history, OddsProviderEnum.BET365.getKey(),this));
			setAttr("lstOddsLB", OddsUtils.findOdds(history, OddsProviderEnum.LB.getKey(),this));
			setAttr("lstOddsML", OddsUtils.findOdds(history, OddsProviderEnum.ML.getKey(),this));
			setAttr("lstOddsBwin", OddsUtils.findOdds(history, OddsProviderEnum.BWIN.getKey(),this));
			OddsUtils.setStartEndOdds(history,this);
		}else{
			redirect("/live");
			return;
		}
		
		Team homeTeam = Team.dao.findById(history.getStr("home_team_id"));
		setAttr("homeTeam", homeTeam);
		Team awayTeam = Team.dao.findById(history.getStr("away_team_id"));
		setAttr("awayTeam", awayTeam);
		String yearShow = history.getStr("year_show").substring(0,2)+"/"+history.getStr("year_show").substring(2);
		history.set("year_show", yearShow);
		setAttr("history", history);
		setAttr("leagueName", EnumUtils.getValue(LeagueEnum.values(), homeTeam.getStr("league_id")));
		setAttr("leagueENName", EnumUtils.getValue(LeagueENEnum.values(), homeTeam.getStr("league_id")));
		
		if(!"0".equals(history.getStr("game_id"))){
			List<TipsAll> lstTips =TipsAll.dao.find("select * from tips_all where game_id = ? order by is_home_away asc", history.getStr("game_id"));
			if(lstTips.size()!=0){
				TipsMatch tipsMatch = TipsMatch.dao.findFirst("select * from tips_match where game_id = ?", history.getStr("game_id"));
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
		}
		//视频
		Videos videos = Videos.dao.findFirst("select * from videos where match_history_id = ? ", matchKey);
		if(videos!=null){
			//vdetail-2017-12-11-6716.html
			String videosUrl = "vdetail-"+DateTimeUtils.formatDate(videos.getDate("match_date"))+"-"+videos.get("id")+".html";
			setAttr("videosUrl", videosUrl);
		}
		
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.LIVE.getKey());
		render("matchDetail.jsp");
	}
	
}
