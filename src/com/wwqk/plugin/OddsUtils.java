package com.wwqk.plugin;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.OddsProviderEnum;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.OddsBet365;
import com.wwqk.model.OddsBwin;
import com.wwqk.model.OddsLB;
import com.wwqk.model.OddsML;
import com.wwqk.model.OddsWH;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.StringUtils;

public class OddsUtils {

	private static final Pattern START_PATTERN = Pattern.compile("初指</td>.*?>(.*?)</td>.*?>(.*?)</td>.*?>(.*?)</td>");
	private static final Pattern END_PATTERN = Pattern.compile("终指</td>.*?>(.*?)</td>.*?>(.*?)</td>.*?>(.*?)</td>");
	private static final Pattern MATCH_ID_PATTERN = Pattern.compile("match/(\\d+)/history/");
	
	public static void initHistoryOdds(){
		HttpClient httpClient = new DefaultHttpClient();
		System.err.println("...........odds job start.....");
		Set<String> leagueSet = new HashSet<String>();
		leagueSet.add("英超");
		leagueSet.add("西甲");
		leagueSet.add("德甲");
		leagueSet.add("意甲");
		leagueSet.add("法甲");
		CommonUtils.initNameIdMap();
		//得到所有的比赛日期
		List<Record> lstDateStr = Db.find("SELECT DATE_FORMAT(match_date, '%Y-%m-%d') date_str FROM league_match_history WHERE odds_wh_end IS NULL and match_date > ?  GROUP BY DATE_FORMAT(match_date, '%Y-%m-%d') ORDER BY date_str ASC", new Date());
		for(Record record : lstDateStr){
			String dateStr = record.getStr("date_str");
			String content = MatchUtils.getHtmlContent(httpClient, MatchUtils.MATCH_REFER_URL, MatchUtils.MATCH_REFER_URL+"?date="+dateStr);
			Document document = Jsoup.parse(content);
			List<Element> lstTrElement = document.select(".each_match");
			for(Element tr : lstTrElement){
				if(!leagueSet.contains(tr.attr("type"))){
					continue;
				}
				String okMatchId = tr.attr("matchid");
				String homeTeamName = StringUtils.trim(tr.select(".ctrl_homename").eq(0).text());
				String awayTeamName = StringUtils.trim(tr.select(".ctrl_awayname").eq(0).text());
				String homeTeamId = CommonUtils.nameIdMap.get(homeTeamName);
				String awayTeamId = CommonUtils.nameIdMap.get(awayTeamName);
				if(StringUtils.isNotBlank(homeTeamId) && StringUtils.isNotBlank(awayTeamId)){
					//查询数据库中的比赛
					LeagueMatchHistory matchHistory = LeagueMatchHistory.dao.findFirst("select * from league_match_history where home_team_id = ? and away_team_id = ? and DATE_FORMAT(match_date, '%Y-%m-%d') = ? ", homeTeamId, awayTeamId, dateStr);
					if(matchHistory!=null){
						matchHistory.set("ok_match_id", okMatchId);
						matchHistory.update();
						//查询各个odds provider的赔率
						initProviderOdds(okMatchId, OddsProviderEnum.WH.getKey(), matchHistory, httpClient);
						initProviderOdds(okMatchId, OddsProviderEnum.BET365.getKey(), matchHistory, httpClient);
						initProviderOdds(okMatchId, OddsProviderEnum.LB.getKey(), matchHistory, httpClient);
						initProviderOdds(okMatchId, OddsProviderEnum.ML.getKey(), matchHistory, httpClient);
						initProviderOdds(okMatchId, OddsProviderEnum.BWIN.getKey(), matchHistory, httpClient);
					}
				}
			}
		}
		
		httpClient.getConnectionManager().shutdown();
		System.err.println("...........odds job end.....");
	}
	
	private static void initProviderOdds(String okMatchId, String oddsProviderId, LeagueMatchHistory matchHistory, HttpClient httpClient){

		String refererUrl = MatchUtils.ODDS_REFER_DETAIL_URL.replace("#okMatchId", okMatchId).replace("#providerId", oddsProviderId);
		String targetUrl = MatchUtils.ODDS_TARGET_URL.replace("#okMatchId", okMatchId).replace("#providerId", oddsProviderId);
		String oddsContent = MatchUtils.getHtmlContent(httpClient, refererUrl, targetUrl);
		Matcher matcher = START_PATTERN.matcher(oddsContent);
		if(matcher.find()){
			if(OddsProviderEnum.WH.getKey().equals(oddsProviderId)){
				matchHistory.set("odds_wh_start", matcher.group(1)+","+matcher.group(2)+","+matcher.group(3));
			}else if(OddsProviderEnum.BET365.getKey().equals(oddsProviderId)){
				matchHistory.set("odds_bet365_start", matcher.group(1)+","+matcher.group(2)+","+matcher.group(3));
			}else if(OddsProviderEnum.LB.getKey().equals(oddsProviderId)){
				matchHistory.set("odds_lb_start", matcher.group(1)+","+matcher.group(2)+","+matcher.group(3));
			}else if(OddsProviderEnum.ML.getKey().equals(oddsProviderId)){
				matchHistory.set("odds_ml_start", matcher.group(1)+","+matcher.group(2)+","+matcher.group(3));
			}else if(OddsProviderEnum.BWIN.getKey().equals(oddsProviderId)){
				matchHistory.set("odds_bwin_start", matcher.group(1)+","+matcher.group(2)+","+matcher.group(3));
			}
			matchHistory.update();
		}
		if("完场".equals(matchHistory.getStr("status"))){
			Matcher endMatcher = END_PATTERN.matcher(oddsContent);
			if(endMatcher.find()){
				if(OddsProviderEnum.WH.getKey().equals(oddsProviderId)){
					matchHistory.set("odds_wh_end", endMatcher.group(1)+","+endMatcher.group(2)+","+endMatcher.group(3));
				}else if(OddsProviderEnum.BET365.getKey().equals(oddsProviderId)){
					matchHistory.set("odds_bet365_end", endMatcher.group(1)+","+endMatcher.group(2)+","+endMatcher.group(3));
				}else if(OddsProviderEnum.LB.getKey().equals(oddsProviderId)){
					matchHistory.set("odds_lb_end", endMatcher.group(1)+","+endMatcher.group(2)+","+endMatcher.group(3));
				}else if(OddsProviderEnum.ML.getKey().equals(oddsProviderId)){
					matchHistory.set("odds_ml_end", endMatcher.group(1)+","+endMatcher.group(2)+","+endMatcher.group(3));
				}else if(OddsProviderEnum.BWIN.getKey().equals(oddsProviderId)){
					matchHistory.set("odds_bwin_end", endMatcher.group(1)+","+endMatcher.group(2)+","+endMatcher.group(3));
				}
				matchHistory.update();
			}
		}
		
		Document document = Jsoup.parse(oddsContent);
		List<Element> lstTr = document.select(".sjbg01");
		for(Element tr : lstTr){
			Model<?> model = null;
			String tableName = null;
			if(OddsProviderEnum.WH.getKey().equals(oddsProviderId)){
				model = new OddsWH();
				tableName = "odds_wh";
			}else if(OddsProviderEnum.BET365.getKey().equals(oddsProviderId)){
				model = new OddsBet365();
				tableName = "odds_bet365";
			}else if(OddsProviderEnum.LB.getKey().equals(oddsProviderId)){
				model = new OddsLB();
				tableName = "odds_lb";
			}else if(OddsProviderEnum.ML.getKey().equals(oddsProviderId)){
				model = new OddsML();
				tableName = "odds_ml";
			}else if(OddsProviderEnum.BWIN.getKey().equals(oddsProviderId)){
				model = new OddsBwin();
				tableName = "odds_bwin";
			}
			model.set("league_name", tr.child(0).text());
			try {
				model.set("match_date_time", DateTimeUtils.parseDate(tr.child(1).text(), DateTimeUtils.ISO_DATETIME_FORMAT_ARRAY));
			} catch (ParseException e) {
			}
			String[] array = getOddsArray(tr.child(2).text());
			model.set("odds_home_start", array[0]);
			model.set("odds_draw_start", array[1]);
			model.set("odds_away_start", array[2]);
			
			String[] arrayEnd = getOddsArray(tr.child(3).text());
			model.set("odds_home_end", arrayEnd[0]);
			model.set("odds_draw_end", arrayEnd[1]);
			model.set("odds_away_end", arrayEnd[2]);
			
			if("0.00".equals(array[0]) || "0.00".equals(arrayEnd[0])){
				continue;
			}
			//主队
			String homeTeamName = tr.child(4).text();
			homeTeamName = CommonConstants.DIFF_MAP.get(homeTeamName)!=null?CommonConstants.DIFF_MAP.get(homeTeamName):homeTeamName;
			model.set("home_team_name", homeTeamName);
			//结果
			model.set("result", tr.child(5).text());
			//客队
			String awayTeamName = tr.child(6).text();
			awayTeamName = CommonConstants.DIFF_MAP.get(awayTeamName)!=null?CommonConstants.DIFF_MAP.get(awayTeamName):awayTeamName;
			model.set("away_team_name", awayTeamName);
			
			String commonResult = tr.child(7).text();
			if("负".equals(commonResult)){
				commonResult = "<span class='green_result'>"+commonResult+"</span>";
			}else if("胜".equals(commonResult)){
				commonResult = "<span class='red_result'>"+commonResult+"</span>";
			}
			model.set("common_result", commonResult);
			String matchIdStr = CommonUtils.matcherString(MATCH_ID_PATTERN, tr.child(1).html());
			model.set("ok_match_id", matchIdStr);
			if(StringUtils.isNotBlank(matchIdStr)){
				List<Record> lstExist = Db.find("select * from "+tableName+" where ok_match_id = ?", matchIdStr);
				if(lstExist.size()==0){
					model.save();
				}
			}
		}
		
		
	}
	
	private static String[] getOddsArray(String arrayStr){
		String[] array = {"0.00", "0.00", "0.00"};
		if(StringUtils.isBlank(arrayStr)){
			return array;
		}
		array = arrayStr.split("&nbsp;&nbsp;&nbsp;");
		if(array.length!=3){
			array = arrayStr.split("   ");
		}
		
		return array;
	}
	
	
	/**  ====比赛明细赔率==== */
	public static void setStartEndOdds(LeagueMatchHistory history, Controller controller){
		if(StringUtils.isNotBlank(history.getStr("odds_wh_start"))){
			controller.setAttr("odds_wh_start", history.getStr("odds_wh_start").replaceAll(",", "&nbsp;&nbsp;"));
		}
		if(StringUtils.isNotBlank(history.getStr("odds_wh_end"))){
			controller.setAttr("odds_wh_end", history.getStr("odds_wh_end").replaceAll(",", "&nbsp;&nbsp;"));
		}
		
		if(StringUtils.isNotBlank(history.getStr("odds_lb_start"))){
			controller.setAttr("odds_lb_start", history.getStr("odds_lb_start").replaceAll(",", "&nbsp;&nbsp;"));
		}
		if(StringUtils.isNotBlank(history.getStr("odds_lb_end"))){
			controller.setAttr("odds_lb_end", history.getStr("odds_lb_end").replaceAll(",", "&nbsp;&nbsp;"));
		}
		
		if(StringUtils.isNotBlank(history.getStr("odds_bet365_start"))){
			controller.setAttr("odds_bet365_start", history.getStr("odds_bet365_start").replaceAll(",", "&nbsp;&nbsp;"));
		}
		if(StringUtils.isNotBlank(history.getStr("odds_bet365_end"))){
			controller.setAttr("odds_bet365_end", history.getStr("odds_bet365_end").replaceAll(",", "&nbsp;&nbsp;"));
		}
		
		if(StringUtils.isNotBlank(history.getStr("odds_ml_start"))){
			controller.setAttr("odds_ml_start", history.getStr("odds_ml_start").replaceAll(",", "&nbsp;&nbsp;"));
		}
		if(StringUtils.isNotBlank(history.getStr("odds_ml_end"))){
			controller.setAttr("odds_ml_end", history.getStr("odds_ml_end").replaceAll(",", "&nbsp;&nbsp;"));
		}
		
		if(StringUtils.isNotBlank(history.getStr("odds_bwin_start"))){
			controller.setAttr("odds_bwin_start", history.getStr("odds_bwin_start").replaceAll(",", "&nbsp;&nbsp;"));
		}
		if(StringUtils.isNotBlank(history.getStr("odds_bwin_end"))){
			controller.setAttr("odds_bwin_end", history.getStr("odds_bwin_end").replaceAll(",", "&nbsp;&nbsp;"));
		}
	}
	
	/**
	 * 获取赔率
	 * @param history
	 * @param oddsProviderId
	 * @return
	 */
	public static List<? extends Model<?>> findOdds(LeagueMatchHistory history, String oddsProviderId, Controller controller){
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
		
		calcHDA(lstResult, oddsProviderId, controller);
		
		return lstResult;
	}
	
	public static void calcHDA(List<? extends Model<?>> lstResult, String oddsProviderId, Controller controller){
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
		controller.setAttr("calcStr_"+oddsProviderId, "共"+lstResult.size()+"场相同初始赔率比赛：<span class='red_result'>"
				+map.get("胜")+"胜</span>&nbsp;"+map.get("平")+"平&nbsp;<span class='green_result'>"+map.get("负")+"负</span>");
	}
}
