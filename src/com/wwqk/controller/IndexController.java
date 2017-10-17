package com.wwqk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.Fun;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.TipsMatch;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.PageUtils;

public class IndexController extends Controller {

	public void index(){
		Page<Fun> funPage = Fun.dao.paginate(getParaToInt("pageNumber", 1), 10, 0);
		for(Fun fun:funPage.getList()){
			String summary = fun.getStr("summary").replaceAll("<.*?>", "");
			fun.set("summary", summary);
		}
		setAttr("funPage", funPage);
		setAttr("pageUI", PageUtils.calcStartEnd(funPage));
		setAttr("initCount", funPage.getList().size());
		getRecomMatches();
		CommonUtils.initLeagueNameMap();
		getMatchNews();
		render("index.jsp");
	}
	
	public void listMore(){
		Page<Fun> funPage = Fun.dao.paginate(getParaToInt("pageNo", 1), 10, 0);
		renderJson(funPage.getList());
	}
	
	private void getMatchNews(){
		Date nowDate = DateTimeUtils.addHours(new Date(), -2);
		List<TipsMatch> lstMatch = TipsMatch.dao.find("select * from tips_match where match_time > ? order by match_time asc limit 0, 50", nowDate);
		formatMsg(lstMatch);
		String jsonStr = JSON.toJSONString(lstMatch);
		setAttr("jsonStr", jsonStr);
	}
	
	private void formatMsg(List<TipsMatch> lstMatch){
		for(TipsMatch match:lstMatch){
			String leagueName = match.getStr("league_name");
			leagueName = CommonUtils.leagueNameIdMap.get(leagueName);
			match.set("league_name", leagueName==null?match.getStr("league_name"):leagueName);
			int descLength = match.getStr("prediction_desc").length();
			if(descLength>75){
				match.getAttrs().put("prediction_all", match.getStr("prediction_desc"));
				match.set("prediction_desc", match.getStr("prediction_desc").substring(0,75)+"...");
			}
		}
	}
	
	/**
	 * 获取推荐比赛
	 */
	private void getRecomMatches(){
		List<LeagueMatch> lstResult = new ArrayList<LeagueMatch>();
		
		List<LeaguePosition> lstPosition = LeaguePosition.dao.find("SELECT team_id, rank FROM league_position");
		Map<String, Integer> teamRankMap = new HashMap<String, Integer>();
		
		for(LeaguePosition position : lstPosition){
			teamRankMap.put(position.getStr("team_id"), position.getInt("rank"));
		}
		
		List<LeagueMatch> lstMatch = LeagueMatch.dao.find("select * from league_match");
		Map<String, List<LeagueMatch>> leagueMatchMap = new HashMap<String, List<LeagueMatch>>();
		for(LeagueMatch match : lstMatch){
			if(leagueMatchMap.get(match.getStr("league_id"))==null){
				List<LeagueMatch> lstLeagueMatch = new ArrayList<LeagueMatch>();
				leagueMatchMap.put(match.getStr("league_id"), lstLeagueMatch);
			}
			if(teamRankMap.get(match.getStr("home_team_id"))==null || teamRankMap.get(match.getStr("away_team_id"))==null){
				continue;
			}
			int rankTotal = teamRankMap.get(match.getStr("home_team_id"))+teamRankMap.get(match.getStr("away_team_id"));
			match.getAttrs().put("rankTotal", rankTotal);
			leagueMatchMap.get(match.getStr("league_id")).add(match);
		}
		//英超
		String[] plArray = {"661","675","676","663","660","662"};
		if(leagueMatchMap.get(LeagueEnum.YC.getKey())!=null){
			for(LeagueMatch chooseMatch:getMostAttentionMatch(plArray,leagueMatchMap.get(LeagueEnum.YC.getKey()),LeagueEnum.YC.getKey())){
				lstResult.add(chooseMatch);
			}
		}
		
		//西甲
		String[] pdArray = {"2017","2016","2020"};
		if(leagueMatchMap.get(LeagueEnum.XJ.getKey())!=null){
			for(LeagueMatch chooseMatch:getMostAttentionMatch(pdArray,leagueMatchMap.get(LeagueEnum.XJ.getKey()),LeagueEnum.XJ.getKey())){
				lstResult.add(chooseMatch);
			}
		}
		
		//德甲
		String[] blArray = {"961","964","13410"};
		if(leagueMatchMap.get(LeagueEnum.DJ.getKey())!=null){
			for(LeagueMatch chooseMatch:getMostAttentionMatch(blArray,leagueMatchMap.get(LeagueEnum.DJ.getKey()),LeagueEnum.DJ.getKey())){
				lstResult.add(chooseMatch);
			}
		}
		
		//意甲
		String[] saArray = {"1242","1270","1241","1244","1240"};
		if(leagueMatchMap.get(LeagueEnum.YJ.getKey())!=null){
			for(LeagueMatch chooseMatch:getMostAttentionMatch(saArray,leagueMatchMap.get(LeagueEnum.YJ.getKey()),LeagueEnum.YJ.getKey())){
				lstResult.add(chooseMatch);
			}
		}
		
		setAttr("lstRecomMatches", lstResult);
	}
	
	private List<LeagueMatch> getMostAttentionMatch(String[] teamIdArray, List<LeagueMatch> lstLeagueMatch, String leagueId){
		List<LeagueMatch> lstResult = new ArrayList<LeagueMatch>();
		//排序
		Collections.sort(lstLeagueMatch, new Comparator<LeagueMatch>(){  
            public int compare(LeagueMatch o1, LeagueMatch o2) {  
                if(o1.getInt("rankTotal") > o2.getInt("rankTotal")){  
                    return 1;  
                }  
                if(o1.getInt("rankTotal") == o2.getInt("rankTotal")){  
                    return 0;  
                }  
                return -1;  
            }  
        });   
		Set<String> set = new HashSet<String>();
		for(String teamId : teamIdArray){
			set.add(teamId);
		}
		List<LeagueMatch> lstChooseMatch = new ArrayList<LeagueMatch>();
		for(LeagueMatch match : lstLeagueMatch){
			if(!set.contains(match.getStr("home_team_id")) && !set.contains(match.getStr("away_team_id"))){
				continue;
			}
			lstChooseMatch.add(match);
		}
		if(lstChooseMatch.size()>1){
			for(LeagueMatch match : lstChooseMatch){
				int maxCount = 1;
				if(LeagueEnum.YC.getKey().equals(leagueId)){
					maxCount = 2;
				}
				
				if(lstResult.size()==maxCount){
					break;
				}
				
				if(!"完场".equals(match.getStr("status"))){
					lstResult.add(match);
				}
			}
			if(lstResult.size()==0){
				lstResult.add(lstChooseMatch.get(0));
			}
		}else{
			lstResult.addAll(lstChooseMatch);
		}
	
		
		return lstResult;
	}
	
}
