package com.wwqk.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.constants.AHZgzcwEnum;
import com.wwqk.constants.OddsProviderEnum;
import com.wwqk.constants.ZgzcwAHProviderEnum;
import com.wwqk.constants.ZgzcwProviderEnum;
import com.wwqk.model.OddsAH;
import com.wwqk.model.OddsEuro;
import com.wwqk.model.OddsMatches;
import com.wwqk.model.OddsSource;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.StringUtils;

public class AnalyzeZgzcw {
	
	private HttpClient httpClient = new DefaultHttpClient();
	private static final String MATCH_ACTION = "http://saishi.zgzcw.com/summary/liansaiAjax.action";
	private Map<String, String> ahNameKeyMap = new HashMap<String, String>();
	private Map<String, String> providerMap = new HashMap<String, String>();
	private Map<String, String> ahProviderMap = new HashMap<String, String>();
	private Pattern matchIdPattern = Pattern.compile("fenxi.zgzcw.com/(\\d+)/ypdb");
	private static AnalyzeZgzcw instance = null;
	
	public static AnalyzeZgzcw getInstance(){
		if(instance==null){
			instance = new AnalyzeZgzcw();
		}
		return instance;
	}
	
	private void initMap(){
		 for(AHZgzcwEnum enumObj:AHZgzcwEnum.values()){
			ahNameKeyMap.put(enumObj.getValue(), enumObj.getKey()); 
		 }
		 
		 for(ZgzcwProviderEnum enumObj:ZgzcwProviderEnum.values()){
			 providerMap.put(enumObj.getKey(), enumObj.getValue()) ;
		 }
		 
		 for(ZgzcwAHProviderEnum enumObj:ZgzcwAHProviderEnum.values()){
			 ahProviderMap.put(enumObj.getKey(), enumObj.getValue()) ;
		 }
	}
	
	public void getLeagueOdds(){
		initMap();
		List<OddsSource> lstOddsSources = OddsSource.dao.find("select * from odds_source");
		for(OddsSource source:lstOddsSources){
			int startIdx = source.getInt("current_round");
			for(int i=startIdx; i<=source.getInt("max_round"); i++){
				//用于重复执行
				if(isCurrentRoundMatchesAllEnd(source, i)){
					continue;
				}
				getOdds(source, i);
				//取完这一轮比赛后，再判断一次该轮比赛是否结束，如果未全部结束则记录到该轮，跳出当前循环
				if(!isCurrentRoundMatchesAllEnd(source, i)){
					source.set("current_round", i);
					source.update();
					break;
				}
			}
		}
		httpClient.getConnectionManager().shutdown();
	}
	
	private boolean isCurrentRoundMatchesAllEnd(OddsSource source, int currentRound){
		List<OddsMatches> lstMatches = OddsMatches.dao.find("select * from odds_matches where league_id = ? and year = ? and round = ? ",
				source.getInt("league_id"), source.getInt("year"), currentRound);
		if(lstMatches.size()==0){
			return false;
		}
		boolean result = true;
		for(OddsMatches match:lstMatches){
			if(1!=match.getInt("status")){
				result = false;
				break;
			}
		}
		return result;
	}
	
	private void getOdds(OddsSource source, int currentRound){
		Connection con = Jsoup.connect(MATCH_ACTION);
		con.data("source_league_id", source.getStr("ok_cycle_id"));
		con.data("currentRound", String.valueOf(currentRound));
		con.data("season", source.getStr("zgzcw_year_show"));
		con.data("seasonType", "");
		for(Map.Entry<String, String> entry : MatchUtils.postZgzcwHeader(source.getStr("referer_url")).entrySet()){
			con.header(entry.getKey(), entry.getValue());
		}
		try {
			Document document = con.post();
			Elements tbody = document.select("tbody");
			if(tbody.size()>0){
				Elements trMatches = tbody.get(0).select("tr");
				for(Element match:trMatches){
					handleOneMatch(match, source, currentRound);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Before(Tx.class)
	private void handleOneMatch(Element matchElement, OddsSource source, int currentRound){
		String matchId = getMatchId(matchElement.html());
		if(StringUtils.isBlank(matchId)){
			return;
		}
		Elements tds = matchElement.select("td");
		
		//		System.err.println("id："+matchId+" time："+tds.get(0).text()+" round："+tds.get(1).text()
		//				+" home："+tds.get(2).text()+" result："+tds.get(3).text()+" away："+tds.get(4).text()
		//				+" oddsLink："+tds.get(8).select("a").get(0).attr("href"));
		//		id：608791 time：05-11 22:00 round：38 home：诺维奇 result：0-2 away：阿森纳 oddsLink：/soccer/match/608791/odds/
		OddsMatches match = OddsMatches.dao.findFirst("select * from odds_matches where match_id = ?", matchId);
		if(match==null){
			match = new OddsMatches();
		}
		match.set("match_time", StringUtils.trim(tds.get(0).text()));
		match.set("round", currentRound);
		match.set("home_name", StringUtils.trim(tds.get(1).text()));
		String result = StringUtils.trim(tds.get(2).text());
		match.set("result", result);
		match.set("away_name", StringUtils.trim(tds.get(3).text()));
		match.set("odds_link", StringUtils.trim(tds.get(6).select("a").get(1).attr("href")));
		match.set("ah_odds_link", StringUtils.trim(tds.get(6).select("a").get(0).attr("href")));
		
		if(!result.contains("-:-")){
			match.set("status", 1);
			String[] scores = result.split("-");
			match.set("home_score", Integer.valueOf(StringUtils.trim(scores[0])));
			match.set("away_score", Integer.valueOf(StringUtils.trim(scores[1])));
			//half result
			String halfResult = StringUtils.trim(tds.get(4).text());
			match.set("half_result", halfResult);
			String[] halfScores = halfResult.split("-");
			match.set("half_home_score", Integer.valueOf(StringUtils.trim(halfScores[0])));
			match.set("half_away_score", Integer.valueOf(StringUtils.trim(halfScores[1])));
		}else{
			match.set("status", 2);
		}
		match.set("league_id", source.get("league_id"));
		match.set("year", source.get("year"));
		match.set("year_show", source.get("year_show"));
		match.set("update_time", new Date());
		if(match.get("id")!=null){
			match.update();
		}else{
			match.save();
		}
		
		getEUOdds(matchId,source.getStr("odds_url"), match.getStr("odds_link"));
		getAHOdds(matchId, source.getStr("odds_url"), match.getStr("ah_odds_link"));
		System.err.println("done!!!");
	}
	
	private void getEUOdds(String matchId, String refelURL, String url){
		Connection connect = Jsoup.connect(url).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.getZgzcwHeader(refelURL));
		try {
			Document document = data.get();
			Elements table = document.select(".bf-tab-02");
			Elements trs = table.get(0).select("tr");
			List<OddsEuro> lstOdds = new ArrayList<OddsEuro>();
			for(Element tr:trs){
				OddsEuro oddsEuro = new OddsEuro();
				Elements oddsTds = tr.select("td");
				Element tdCompany = oddsTds.get(5);
				String pid = StringUtils.trim(tdCompany.attr("cid"));
				if(providerMap.get(pid)==null){
					continue;
				}
				oddsEuro.set("match_id", matchId);
				oddsEuro.set("pid", pid);
				oddsEuro.set("pname", providerMap.get(pid));
				String startHomeOdds = StringUtils.trim(oddsTds.get(2).text());
				String startDrawOdds = StringUtils.trim(oddsTds.get(3).text());
				String startAwayOdds = StringUtils.trim(oddsTds.get(4).text());
				oddsEuro.set("start_home_odds", startHomeOdds);
				oddsEuro.set("start_draw_odds", startDrawOdds);
				oddsEuro.set("start_away_odds", startAwayOdds);
				
				String endHomeOdds = StringUtils.trim(oddsTds.get(5).text());
				String endDrawOdds = StringUtils.trim(oddsTds.get(6).text());
				String endAwayOdds = StringUtils.trim(oddsTds.get(7).text());
				oddsEuro.set("end_home_odds", endHomeOdds);
				oddsEuro.set("end_draw_odds", endDrawOdds);
				oddsEuro.set("end_away_odds", endAwayOdds);
				
				//最新概率
				String homePercent = StringUtils.trim(oddsTds.get(9).text());
				String drawPercent = StringUtils.trim(oddsTds.get(10).text());
				String awayPercent = StringUtils.trim(oddsTds.get(11).text());
				oddsEuro.set("home_percent", homePercent);
				oddsEuro.set("draw_percent", drawPercent);
				oddsEuro.set("away_percent", awayPercent);
				
				//最新凯利指数
				String homeKL = StringUtils.trim(oddsTds.get(12).text());
				String drawKL = StringUtils.trim(oddsTds.get(13).text());
				String awayKL = StringUtils.trim(oddsTds.get(14).text());
				oddsEuro.set("home_kl", homeKL);
				oddsEuro.set("draw_kl", drawKL);
				oddsEuro.set("away_kl", awayKL);
				
				//赔付率
				String repay = StringUtils.trim(oddsTds.get(15).text());
				oddsEuro.set("repay", repay);
				oddsEuro.set("update_time", new Date());
				
				lstOdds.add(oddsEuro);
			}
			if(lstOdds.size()>0){
				Db.update("delete from odds_euro where match_id = ?", matchId);
				Db.batchSave(lstOdds, lstOdds.size());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void getAHOdds(String matchId, String refelURL, String url){
		String oddsHtml = MatchUtils.getHtmlContent(httpClient, refelURL, url);
		System.err.println(oddsHtml);
		Document oddsDoc = Jsoup.parse("<html><head></head><body><table><tbody>"+oddsHtml+"</tbody></table></body></html>");
		Elements trs = oddsDoc.select("tr");
		List<OddsAH> lstOdds = new ArrayList<OddsAH>();
		for(Element tr:trs){
			Elements cks = tr.select("input");
			if(cks.size()>0){
				OddsAH oddsAH = new OddsAH();
				oddsAH.set("match_id", matchId);
				String pid = cks.get(0).val();
				oddsAH.set("pid", pid);
				oddsAH.set("pname", EnumUtils.getValue(OddsProviderEnum.values(), pid));
				Elements oddsTds = trs.select("td");
				String startAHHome = oddsTds.get(2).text();
				String startAHAmount = oddsTds.get(3).text();
				String startAHAway = oddsTds.get(4).text();
				oddsAH.set("start_ah_home", startAHHome);
				oddsAH.set("start_ah_amount", startAHAmount);
				oddsAH.set("start_ah_amount_enum", ahNameKeyMap.get(startAHAmount));
				oddsAH.set("start_ah_away", startAHAway);
				
				
				String endAHHome = oddsTds.get(5).text();
				String endAHAmount = oddsTds.get(6).text();
				String endAHAway = oddsTds.get(7).text();
				oddsAH.set("end_ah_home", endAHHome);
				oddsAH.set("end_ah_amount", endAHAmount);
				oddsAH.set("end_ah_amount_enum", ahNameKeyMap.get(endAHAmount));
				oddsAH.set("end_ah_away", endAHAway);
				
				String homeKL = oddsTds.get(10).text();
				String awayKL = oddsTds.get(11).text();
				String repay = oddsTds.get(12).text();
				oddsAH.set("home_kl", homeKL);
				oddsAH.set("away_kl", awayKL);
				oddsAH.set("repay", repay);
				oddsAH.set("update_time", new Date());
				
				lstOdds.add(oddsAH);
			}
		}
		
		if(lstOdds.size()>0){
			Db.update("delete from odds_ah where match_id = ?", matchId);
			Db.batchSave(lstOdds, lstOdds.size());
		}
	}
	
	private String getMatchId(String content){
		String matchId = null;
		Matcher matcher = matchIdPattern.matcher(content);
		if(matcher.find()){
			matchId = matcher.group(1);
		}
		return matchId;
	}
	
	
	public static void main(String[] args) {
		
	}
	
}