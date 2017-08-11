package com.wwqk.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AnalyzeUtils {
	
	private HttpClient httpClient = new DefaultHttpClient();
	private static final String MAIN_SITE = "http://www.okooo.com";
	private static final String EN_REFEL_URL = "http://www.okooo.com/soccer/league/17/schedule/10912/";
	private static final String[] EN_URL = {"http://www.okooo.com/soccer/league/17/schedule/11367/",
		"http://www.okooo.com/soccer/league/17/schedule/8186/",
		"http://www.okooo.com/soccer/league/17/schedule/12084/",
		"http://www.okooo.com/soccer/league/17/schedule/12651/"};
	
	private Pattern matchPattern = Pattern.compile("<tr matchid=\"(\\d+)\".*?</tr>");
	
	public void getLeagueOdds(){
		//1、赛季首页（含第一轮比赛）
		String content = MatchUtils.getHtmlContent(httpClient, EN_REFEL_URL, EN_URL[0]);
		//2、欧赔页
		Matcher matcher = matchPattern.matcher(content);
		while(matcher.find()){
			String matchLine = matcher.group();
			String matchId = matcher.group(1);
			String html = "<html><head></head><body><table>"+matchLine+"</table</body></html>";
			Document document = Jsoup.parse(html);
			Elements tds = document.select("td");
			
//			System.err.println("id："+matchId+" time："+tds.get(0).text()+" round："+tds.get(1).text()
//					+" home："+tds.get(2).text()+" result："+tds.get(3).text()+" away："+tds.get(4).text()
//					+" oddsLink："+tds.get(8).select("a").get(0).attr("href"));
//			id：608791 time：05-11 22:00 round：38 home：诺维奇 result：0-2 away：阿森纳 oddsLink：/soccer/match/608791/odds/
			String oddsLink = tds.get(8).select("a").get(0).attr("href");
			String oddsHtml = MatchUtils.getHtmlContent(httpClient, MAIN_SITE+oddsLink, 
					"http://www.okooo.com/soccer/match/"+matchId+"/odds/ajax/?page=0&trnum=0&companytype=BaijiaBooks&type=1");
			System.err.println(oddsHtml);
		}
	}
	
	public static void main(String[] args) {
		new AnalyzeUtils().getLeagueOdds();
	}
	
}
