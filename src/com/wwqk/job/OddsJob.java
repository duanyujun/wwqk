package com.wwqk.job;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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

public class OddsJob implements Job {
	
	private HttpClient httpClient = new DefaultHttpClient();
	private static final Pattern START_PATTERN = Pattern.compile("初指</td>.*?>(.*?)</td>.*?>(.*?)</td>.*?>(.*?)</td>");
	private static final Pattern END_PATTERN = Pattern.compile("终指</td>.*?>(.*?)</td>.*?>(.*?)</td>.*?>(.*?)</td>");
	private static final Pattern MATCH_ID_PATTERN = Pattern.compile("match/(\\d+)/history/");
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		initHistoryOdds();
	}

	private void initHistoryOdds(){
		System.err.println("...........odds job start.....");
		Set<String> leagueSet = new HashSet<String>();
		leagueSet.add("英超");
		leagueSet.add("西甲");
		leagueSet.add("德甲");
		leagueSet.add("意甲");
		leagueSet.add("法甲");
		//得到所有的比赛日期
		List<Record> lstDateStr = Db.find("SELECT DATE_FORMAT(match_date, '%Y-%m-%d') date_str FROM league_match_history WHERE odds_wh_end IS NULL  GROUP BY DATE_FORMAT(match_date, '%Y-%m-%d') ORDER BY date_str ASC");
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
				String homeTeamName = tr.select(".ctrl_homename").eq(0).text();
				homeTeamName = CommonConstants.DIFF_MAP.get(homeTeamName)!=null?CommonConstants.DIFF_MAP.get(homeTeamName):homeTeamName;
				String awayTeamName = tr.select(".ctrl_awayname").eq(0).text();
				awayTeamName = CommonConstants.DIFF_MAP.get(awayTeamName)!=null?CommonConstants.DIFF_MAP.get(awayTeamName):awayTeamName;
				//查询数据库中的比赛
				LeagueMatchHistory matchHistory = LeagueMatchHistory.dao.findFirst("select * from league_match_history where home_team_name = ? and away_team_name = ? and DATE_FORMAT(match_date, '%Y-%m-%d') = ? ", homeTeamName, awayTeamName, dateStr);
				if(matchHistory!=null){
					matchHistory.set("ok_match_id", okMatchId);
					matchHistory.update();
					//查询各个odds provider的赔率
					initProviderOdds(okMatchId, OddsProviderEnum.WH.getKey(), matchHistory);
					initProviderOdds(okMatchId, OddsProviderEnum.BET365.getKey(), matchHistory);
					initProviderOdds(okMatchId, OddsProviderEnum.LB.getKey(), matchHistory);
					initProviderOdds(okMatchId, OddsProviderEnum.ML.getKey(), matchHistory);
					initProviderOdds(okMatchId, OddsProviderEnum.BWIN.getKey(), matchHistory);
				}
			}
		}
		
		System.err.println("...........odds job end.....");
	}
	
	private void initProviderOdds(String okMatchId, String oddsProviderId, LeagueMatchHistory matchHistory){

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
			
			model.set("home_team_name", tr.child(4).text());
			model.set("result", tr.child(5).text());
			model.set("away_team_name", tr.child(6).text());
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
	
	private String[] getOddsArray(String arrayStr){
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
	
}






