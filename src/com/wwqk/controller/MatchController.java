package com.wwqk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.StringUtil;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;
import com.wwqk.constants.LeagueENEnum;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.OddsProviderEnum;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchLive;
import com.wwqk.model.OddsBet365;
import com.wwqk.model.OddsBwin;
import com.wwqk.model.OddsLB;
import com.wwqk.model.OddsML;
import com.wwqk.model.OddsWH;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
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
			redirect("/match");
		}
		List<MatchLive> lstMatchLive = MatchLive.dao.find("select * from match_live where match_key = ?", matchKey);
		if(lstMatchLive.size()>0){
			setAttr("lstMatchLive", lstMatchLive);
		}
		//主队球场
		LeagueMatchHistory history = LeagueMatchHistory.dao.findById(matchKey);
		//处理改时间的问题
		if(history==null){
			String matchDateStr = matchKey.substring(0, matchKey.lastIndexOf("-"));
			String homeAwayId = matchKey.substring(matchKey.lastIndexOf("-")+1);
			LeagueMatch leagueMatch = LeagueMatch.dao.findFirst("select * from league_match where home_team_id = ? and away_team_id = ? and date_format(match_date,'%Y-%m-%d') = ? ", 
					homeAwayId.split("vs")[0], homeAwayId.split("vs")[1], matchDateStr);
			if(leagueMatch!=null){
				history = LeagueMatchHistory.dao.findFirst("select * from league_match_history where home_team_id = ? and away_team_id = ? and round = ? ", 
						leagueMatch.getStr("home_team_id"), leagueMatch.getStr("away_team_id"), leagueMatch.getInt("round"));
				if(history!=null){
					history.set("match_date", leagueMatch.getDate("match_date"));
					history.set("match_weekday", leagueMatch.getStr("match_weekday"));
					history.set("status", leagueMatch.getStr("status"));
					LeagueMatchHistory newHistory = new LeagueMatchHistory();
					newHistory._setAttrs(history.getAttrs());
					newHistory.set("id", matchKey);
					newHistory.save();
					history.delete();
					history = newHistory;
				}
			}
		}
		
		setAttr("lstOddsWH", findOdds(history, OddsProviderEnum.WH.getKey()));
		setAttr("lstOddsBet365", findOdds(history, OddsProviderEnum.BET365.getKey()));
		setAttr("lstOddsLB", findOdds(history, OddsProviderEnum.LB.getKey()));
		setAttr("lstOddsML", findOdds(history, OddsProviderEnum.ML.getKey()));
		setAttr("lstOddsBwin", findOdds(history, OddsProviderEnum.BWIN.getKey()));
		setStartEndOdds(history);
		
		
		if(StringUtils.isNotBlank(history.getStr("team")) ){
			String teamStr = history.getStr("team").replaceAll("\\s+", "");
			if("<p><br></p>".equals(teamStr)){
				history.set("team", null);
			}
		}
		if(StringUtils.isNotBlank(history.getStr("info"))){
			String teamStr = history.getStr("info").replaceAll("\\s+", "");
			if("<p><br></p>".equals(teamStr)){
				history.set("info", null);
			}
		}
		
		Team homeTeam = Team.dao.findById(history.getStr("home_team_id"));
		//Team awayTeam = Team.dao.findById(history.getStr("away_team_id"));
		setAttr("homeTeam", homeTeam);
		//setAttr("awayTeam", awayTeam);
		setAttr("history", history);
		setAttr("leagueName", EnumUtils.getValue(LeagueEnum.values(), homeTeam.getStr("league_id")));
		setAttr("leagueENName", EnumUtils.getValue(LeagueENEnum.values(), homeTeam.getStr("league_id")));
		render("matchDetail.jsp");
	}
	
	private void setStartEndOdds(LeagueMatchHistory history){
		if(StringUtils.isNotBlank(history.getStr("odds_wh_start"))){
			setAttr("odds_wh_start", history.getStr("odds_wh_start").replaceAll(",", "&nbsp;&nbsp;"));
		}
		if(StringUtils.isNotBlank(history.getStr("odds_wh_end"))){
			setAttr("odds_wh_end", history.getStr("odds_wh_end").replaceAll(",", "&nbsp;&nbsp;"));
		}
		
		if(StringUtils.isNotBlank(history.getStr("odds_lb_start"))){
			setAttr("odds_lb_start", history.getStr("odds_lb_start").replaceAll(",", "&nbsp;&nbsp;"));
		}
		if(StringUtils.isNotBlank(history.getStr("odds_lb_end"))){
			setAttr("odds_lb_end", history.getStr("odds_lb_end").replaceAll(",", "&nbsp;&nbsp;"));
		}
		
		if(StringUtils.isNotBlank(history.getStr("odds_bet365_start"))){
			setAttr("odds_bet365_start", history.getStr("odds_bet365_start").replaceAll(",", "&nbsp;&nbsp;"));
		}
		if(StringUtils.isNotBlank(history.getStr("odds_bet365_end"))){
			setAttr("odds_bet365_end", history.getStr("odds_bet365_end").replaceAll(",", "&nbsp;&nbsp;"));
		}
		
		if(StringUtils.isNotBlank(history.getStr("odds_ml_start"))){
			setAttr("odds_ml_start", history.getStr("odds_ml_start").replaceAll(",", "&nbsp;&nbsp;"));
		}
		if(StringUtils.isNotBlank(history.getStr("odds_ml_end"))){
			setAttr("odds_ml_end", history.getStr("odds_ml_end").replaceAll(",", "&nbsp;&nbsp;"));
		}
		
		if(StringUtils.isNotBlank(history.getStr("odds_bwin_start"))){
			setAttr("odds_bwin_start", history.getStr("odds_bwin_start").replaceAll(",", "&nbsp;&nbsp;"));
		}
		if(StringUtils.isNotBlank(history.getStr("odds_bwin_end"))){
			setAttr("odds_bwin_end", history.getStr("odds_bwin_end").replaceAll(",", "&nbsp;&nbsp;"));
		}
	}
	
	/**
	 * 获取赔率
	 * @param history
	 * @param oddsProviderId
	 * @return
	 */
	private List<? extends Model<?>> findOdds(LeagueMatchHistory history, String oddsProviderId){
		List<? extends Model<?>> lstResult = null;
		if(OddsProviderEnum.WH.getKey().equals(oddsProviderId)){
			if(StringUtils.isNotBlank(history.getStr("odds_wh_start"))){
				String[] odds = history.getStr("odds_wh_start").split(",");
				lstResult = OddsWH.dao.find("select * from odds_wh where odds_home_start = ? and odds_draw_start = ? and odds_away_start = ? order by match_date_time desc ", 
						odds[0], odds[1], odds[2] );
			} 
		}else if(OddsProviderEnum.BET365.getKey().equals(oddsProviderId)){
			if(StringUtils.isNotBlank(history.getStr("odds_bet365_start"))){
				String[] odds = history.getStr("odds_bet365_start").split(",");
				lstResult = OddsBet365.dao.find("select * from odds_bet365 where odds_home_start = ? and odds_draw_start = ? and odds_away_start = ? order by match_date_time desc ", 
						odds[0], odds[1], odds[2] );
			} 
		}else if(OddsProviderEnum.LB.getKey().equals(oddsProviderId)){
			if(StringUtils.isNotBlank(history.getStr("odds_lb_start"))){
				String[] odds = history.getStr("odds_lb_start").split(",");
				lstResult = OddsLB.dao.find("select * from odds_lb where odds_home_start = ? and odds_draw_start = ? and odds_away_start = ? order by match_date_time desc ", 
						odds[0], odds[1], odds[2] );
			} 
		}else if(OddsProviderEnum.ML.getKey().equals(oddsProviderId)){
			if(StringUtils.isNotBlank(history.getStr("odds_ml_start"))){
				String[] odds = history.getStr("odds_ml_start").split(",");
				lstResult = OddsML.dao.find("select * from odds_ml where odds_home_start = ? and odds_draw_start = ? and odds_away_start = ? order by match_date_time desc ", 
						odds[0], odds[1], odds[2] );
			} 
		}else if(OddsProviderEnum.BWIN.getKey().equals(oddsProviderId)){
			if(StringUtils.isNotBlank(history.getStr("odds_bwin_start"))){
				String[] odds = history.getStr("odds_bwin_start").split(",");
				lstResult = OddsBwin.dao.find("select * from odds_bwin where odds_home_start = ? and odds_draw_start = ? and odds_away_start = ? order by match_date_time desc ", 
						odds[0], odds[1], odds[2] );
			} 
		}
		
		calcHDA(lstResult, oddsProviderId);
		
		return lstResult;
	}
	
	private void calcHDA(List<? extends Model<?>> lstResult, String oddsProviderId){
		if(lstResult==null || lstResult.size()==0){
			return;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("胜", 0);
		map.put("平", 0);
		map.put("负", 0);
		for(Model<?> model : lstResult){
			if(model.getStr("common_result").contains("胜")){
				map.put("胜", map.get("胜")+1);
			}else if(model.getStr("common_result").contains("负")){
				map.put("负", map.get("负")+1);
			}else{
				map.put("平", map.get("平")+1);
			}
		}
		setAttr("calcStr_"+oddsProviderId, "共"+lstResult.size()+"场相同初始赔率比赛：<span class='red_result'>"
				+map.get("胜")+"胜</span>&nbsp;"+map.get("平")+"平&nbsp;<span class='green_result'>"+map.get("负")+"负</span>");
	}
	
}
