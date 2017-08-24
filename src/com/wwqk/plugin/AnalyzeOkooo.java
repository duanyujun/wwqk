package com.wwqk.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.constants.OddsProviderEnum;
import com.wwqk.model.OddsAH;
import com.wwqk.model.OddsEuro;
import com.wwqk.model.OddsMatches;
import com.wwqk.model.OddsSource;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.StringUtils;

public class AnalyzeOkooo {
	
	private HttpClient httpClient = new DefaultHttpClient();
	private static final String MAIN_SITE = "http://www.okooo.com";
	private Map<String, String> ahNameKeyMap = new HashMap<String, String>();
	private Pattern matchPattern = Pattern.compile("<tr matchid=\"(\\d+)\".*?</tr>");
	private static AnalyzeOkooo instance = null;
	
	public static AnalyzeOkooo getInstance(){
		if(instance==null){
			instance = new AnalyzeOkooo();
		}
		return instance;
	}
	
	private void initAHNameKeyMap(){
		 for(OddsProviderEnum enumObj:OddsProviderEnum.values()){
			ahNameKeyMap.put(enumObj.getValue(), enumObj.getKey()); 
		 }
	}
	
	public void getLeagueOdds(){
		initAHNameKeyMap();
		List<OddsSource> lstOddsSources = OddsSource.dao.find("select * from odds_source");
		for(OddsSource source:lstOddsSources){
			int startIdx = source.getInt("current_round");
			for(int i=startIdx; i<=source.getInt("max_round"); i++){
				//用于重复执行
				if(isCurrentRoundMatchesAllEnd(source, i)){
					continue;
				}
				getOdds(source, source.getStr("referer_url"), source.getStr("odds_url")+"/1-"+source.getStr("ok_cycle_id")+"-"+i+"/");
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
	
	private void getOdds(OddsSource source, String refelURL, String url){
		//1、赛季首页（含第一轮比赛）
		String content = MatchUtils.getHtmlContent(httpClient, refelURL, url);
		//2、欧赔页
		Matcher matcher = matchPattern.matcher(content);
		while(matcher.find()){
			String matchLine = matcher.group();
			String matchId = matcher.group(1);
			String html = "<html><head></head><body><table>"+matchLine+"</table</body></html>";
			Document document = Jsoup.parse(html);
			handleOneMatch(document, matchId, source);
		}
	}
	
	@Before(Tx.class)
	private void handleOneMatch(Document document, String matchId, OddsSource source){
		Elements tds = document.select("td");
		
		//		System.err.println("id："+matchId+" time："+tds.get(0).text()+" round："+tds.get(1).text()
		//				+" home："+tds.get(2).text()+" result："+tds.get(3).text()+" away："+tds.get(4).text()
		//				+" oddsLink："+tds.get(8).select("a").get(0).attr("href"));
		//		id：608791 time：05-11 22:00 round：38 home：诺维奇 result：0-2 away：阿森纳 oddsLink：/soccer/match/608791/odds/
		OddsMatches match = OddsMatches.dao.findFirst("select * from odds_matches where match_id = ?", matchId);
		if(match==null){
			match = new OddsMatches();
		}
		match.set("match_time", StringUtils.trim(tds.get(0).text()));
		match.set("round", StringUtils.trim(tds.get(1).text()));
		match.set("home_name", StringUtils.trim(tds.get(2).text()));
		String result = StringUtils.trim(tds.get(3).text());
		match.set("result", result);
		match.set("away_name", StringUtils.trim(tds.get(4).text()));
		match.set("odds_link", StringUtils.trim(tds.get(8).select("a").get(0).attr("href")));
		if(result.contains("-")){
			match.set("status", 1);
			String[] scores = result.split("-");
			match.set("home_score", Integer.valueOf(StringUtils.trim(scores[0])));
			match.set("away_score", Integer.valueOf(StringUtils.trim(scores[1])));
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
		
		
		String oddsLink = tds.get(8).select("a").get(0).attr("href");
		String euRefel = MAIN_SITE+oddsLink;
		String euLink = "http://www.okooo.com/soccer/match/"+matchId+"/odds/ajax/?page=0&trnum=0&companytype=BaijiaBooks&type=1";
		getEUOdds(matchId,euRefel, euLink);
		String ahRefel = euRefel.replace("odds", "ah");
		String ahLink = "http://www.okooo.com/soccer/match/"+matchId+"/ah/ajax/?page=0&trnum=0&companytype=BaijiaBooks";
		getAHOdds(matchId, ahRefel, ahLink);
		System.err.println("done");
	}
	
	private void getEUOdds(String matchId, String refelURL, String url){
		String oddsHtml = MatchUtils.getHtmlContent(httpClient, refelURL, url);
		System.err.println(oddsHtml);
		
		Document oddsDoc = Jsoup.parse("<html><head></head><body><table><tbody>"+oddsHtml+"</tbody></table></body></html>");
		Elements trs = oddsDoc.select("tr");
		List<OddsEuro> lstOdds = new ArrayList<OddsEuro>();
		for(Element tr:trs){
			Elements cks = tr.select("input");
			if(cks.size()>0){
				OddsEuro oddsEuro = new OddsEuro();
				oddsEuro.set("match_id", matchId);
				String pid = StringUtils.trim(cks.get(0).val());
				oddsEuro.set("pid", pid);
				oddsEuro.set("pname", EnumUtils.getValue(OddsProviderEnum.values(), pid));
				Elements oddsTds = trs.select("td");
				String startHomeOdds = oddsTds.get(2).text();
				String startDrawOdds = oddsTds.get(3).text();
				String startAwayOdds = oddsTds.get(4).text();
				oddsEuro.set("start_home_odds", startHomeOdds);
				oddsEuro.set("start_draw_odds", startDrawOdds);
				oddsEuro.set("start_away_odds", startAwayOdds);
				
				String endHomeOdds = oddsTds.get(5).text();
				String endDrawOdds = oddsTds.get(6).text();
				String endAwayOdds = oddsTds.get(7).text();
				oddsEuro.set("end_home_odds", endHomeOdds);
				oddsEuro.set("end_draw_odds", endDrawOdds);
				oddsEuro.set("end_away_odds", endAwayOdds);
				
				//最新概率
				String homePercent = oddsTds.get(9).text();
				String drawPercent = oddsTds.get(10).text();
				String awayPercent = oddsTds.get(11).text();
				oddsEuro.set("home_percent", homePercent);
				oddsEuro.set("draw_percent", drawPercent);
				oddsEuro.set("away_percent", awayPercent);
				
				//最新凯利指数
				String homeKL = oddsTds.get(12).text();
				String drawKL = oddsTds.get(13).text();
				String awayKL = oddsTds.get(14).text();
				oddsEuro.set("home_kl", homeKL);
				oddsEuro.set("draw_kl", drawKL);
				oddsEuro.set("away_kl", awayKL);
				
				//赔付率
				String repay = oddsTds.get(15).text();
				oddsEuro.set("repay", repay);
				oddsEuro.set("update_time", new Date());
				
				lstOdds.add(oddsEuro);
			}
		}
		if(lstOdds.size()>0){
			Db.update("delete from odds_euro where match_id = ?", matchId);
			Db.batchSave(lstOdds, lstOdds.size());
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
	
	
	public static void main(String[] args) {
		
	}
	
}
