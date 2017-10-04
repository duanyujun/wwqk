package com.wwqk.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wwqk.model.OddsAH;
import com.wwqk.model.OddsMatches;

/** 
 * @date 创建时间：2017-9-9 下午4:24:54    
 */

public class MatchesStaticGenerator {
	
	/**
	 * 
	 * @param lstMatches
	 * @param isHomeTeam
	 * @return
	 */
	public static String processAH(List<OddsMatches> lstMatches, boolean isHomeTeam, Map<String, String> ahAmountMap){
		StringBuilder sb = new StringBuilder();
		int winAH = 0;
		int drawAH = 0;
		int loseAH = 0;
		for(OddsMatches oddsMatches : lstMatches){
			OddsAH oddsAH = OddsAH.dao.findFirst("select * from odds_ah where match_id = ? and pid = 1 ", oddsMatches.getStr("match_id"));
			BigDecimal homeScore = new BigDecimal(oddsMatches.getInt("home_score"));
			BigDecimal awayScore = new BigDecimal(oddsMatches.getInt("away_score"));
			BigDecimal endAhAmount = new BigDecimal(ahAmountMap.get(oddsAH.getStr("end_ah_amount_enum")));
			homeScore = homeScore.add(endAhAmount);
			if(isHomeTeam){
				if(homeScore.compareTo(awayScore)==1){
					winAH++;
				}else if(homeScore.compareTo(awayScore)==0){
					drawAH++;
				}else{
					loseAH++;
				}
			}else{
				if(awayScore.compareTo(homeScore)==1){
					winAH++;
				}else if(awayScore.compareTo(homeScore)==0){
					drawAH++;
				}else{
					loseAH++;
				}
			}
		}
		if(isHomeTeam){
			sb.append("主队（联赛主场）：近").append(lstMatches.size()).append("场比赛赢盘情况：").append(winAH).append("赢").append(drawAH).append("走").append(loseAH).append("输。<br>");
		}else{
			sb.append("客队（联赛客场）：近").append(lstMatches.size()).append("场比赛赢盘情况：").append(winAH).append("赢").append(drawAH).append("走").append(loseAH).append("输。<br>");
		}
		
		return sb.toString();
	}

	/**
	 * 
	 * @param lstMatches
	 * @param isHomeTeam true:主队;  false:客队
	 * @return
	 */
	public static String process(List<OddsMatches> lstMatches, boolean isHomeTeam){
		StringBuilder sb = new StringBuilder();
		int winCount = 0;
		int drawCount = 0;
		int loseCount = 0;
		int halfWinCount = 0;
		int halfDrawCount = 0;
		int halfLoseCount = 0;
		//进球数
		int goalCount = 0;
		//输球数
		int loseGoalCount = 0;
		for(OddsMatches match:lstMatches){
			if(isHomeTeam){
				if(match.getInt("home_score")>match.getInt("away_score")){
					winCount++;
				}else if(match.getInt("home_score")==match.getInt("away_score")){
					drawCount++;
				}else{
					loseCount++;
				}
			}else{
				if(match.getInt("home_score")<match.getInt("away_score")){
					winCount++;
				}else if(match.getInt("home_score")==match.getInt("away_score")){
					drawCount++;
				}else{
					loseCount++;
				}
			}
			
			if(isHomeTeam){
				if(match.getInt("half_home_score")>match.getInt("half_away_score")){
					halfWinCount++;
				}else if(match.getInt("half_home_score")==match.getInt("half_away_score")){
					halfDrawCount++;
				}else{
					halfLoseCount++;
				}
			}else{
				if(match.getInt("half_home_score")<match.getInt("half_away_score")){
					halfWinCount++;
				}else if(match.getInt("half_home_score")==match.getInt("half_away_score")){
					halfDrawCount++;
				}else{
					halfLoseCount++;
				}
			}
			
			if(isHomeTeam){
				goalCount += match.getInt("home_score");
				loseGoalCount += match.getInt("away_score");
			}else{
				goalCount += match.getInt("away_score");
				loseGoalCount += match.getInt("home_score");
			}
		}
		
		if(lstMatches.size()!=0){
			BigDecimal evenGoalCount = new BigDecimal(goalCount).divide(new BigDecimal(lstMatches.size()), 2 , BigDecimal.ROUND_HALF_UP);
			BigDecimal evenLoseGoalCount = new BigDecimal(loseGoalCount).divide(new BigDecimal(lstMatches.size()), 2 , BigDecimal.ROUND_HALF_UP);
			if(isHomeTeam){
				sb.append("主队（联赛主场）：近").append(lstMatches.size()).append("场比赛，<span class='red_result'>").append(winCount).append("</span>赢")
				  .append(drawCount).append("平<span class='red_result'>").append(loseCount).append("</span>负。场均进")
				  .append(evenGoalCount).append("球，丢").append(evenLoseGoalCount).append("球<br>");
			}else{
				sb.append("客队（联赛客场）：近").append(lstMatches.size()).append("场比赛，<span class='red_result'>").append(winCount).append("</span>赢")
				  .append(drawCount).append("平<span class='red_result'>").append(loseCount).append("</span>负。场均进")
				  .append(evenGoalCount).append("球，丢").append(evenLoseGoalCount).append("球<br>");
			}
		}
		
		return sb.toString();
	}

}
