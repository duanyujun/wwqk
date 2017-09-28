package com.wwqk.utils;

import java.math.BigDecimal;
import java.util.List;

import com.wwqk.model.OddsMatches;

/** 
 * @date 创建时间：2017-9-9 下午4:24:54    
 */

public class MatchesStaticGenerator {

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
		
		return sb.toString();
	}

}
