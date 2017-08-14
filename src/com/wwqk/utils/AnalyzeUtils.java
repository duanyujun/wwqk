package com.wwqk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
		//循环英超最近几个赛季，后面加上全部联赛
		for(String url : EN_URL){
			//循环英超38轮比赛
			for(int i=1;i<39;i++){
				getOdds(EN_REFEL_URL, url+"/1-1-"+i+"/");
			}
			
		}
		
	}
	
	private void getOdds(String refelURL, String url){
		//1、赛季首页（含第一轮比赛）
		String content = MatchUtils.getHtmlContent(httpClient, refelURL, url);
		//2、欧赔页
		Matcher matcher = matchPattern.matcher(content);
		while(matcher.find()){
			String matchLine = matcher.group();
			String matchId = matcher.group(1);
			String html = "<html><head></head><body><table>"+matchLine+"</table</body></html>";
			Document document = Jsoup.parse(html);
			Elements tds = document.select("td");
			
//					System.err.println("id："+matchId+" time："+tds.get(0).text()+" round："+tds.get(1).text()
//							+" home："+tds.get(2).text()+" result："+tds.get(3).text()+" away："+tds.get(4).text()
//							+" oddsLink："+tds.get(8).select("a").get(0).attr("href"));
//					id：608791 time：05-11 22:00 round：38 home：诺维奇 result：0-2 away：阿森纳 oddsLink：/soccer/match/608791/odds/
			String oddsLink = tds.get(8).select("a").get(0).attr("href");
			String euRefel = MAIN_SITE+oddsLink;
			String euLink = "http://www.okooo.com/soccer/match/"+matchId+"/odds/ajax/?page=0&trnum=0&companytype=BaijiaBooks&type=1";
			getEUOdds(euRefel, euLink);
			String ahRefel = euRefel.replace("odds", "ah");
			String ahLink = "http://www.okooo.com/soccer/match/"+matchId+"/ah/ajax/?page=0&trnum=0&companytype=BaijiaBooks";
			getAHOdds(refelURL, url);
		}
	}
	
	private void getEUOdds(String refelURL, String url){
		String oddsHtml = MatchUtils.getHtmlContent(httpClient, refelURL, url);
		System.err.println(oddsHtml);
		
		Document oddsDoc = Jsoup.parse("<html><head></head><body><table><tbody>"+oddsHtml+"</tbody></table></body></html>");
		Elements trs = oddsDoc.select("tr");
		for(Element tr:trs){
			Elements cks = tr.select("input");
			if(cks.size()>0){
				String pid = cks.get(0).val();
				Elements oddsTds = trs.select("td");
				String startHomeOdds = oddsTds.get(2).text();
				String startDrawOdds = oddsTds.get(3).text();
				String startAwayOdds = oddsTds.get(4).text();
				
				String endHomeOdds = oddsTds.get(5).text();
				String endDrawOdds = oddsTds.get(6).text();
				String endAwayOdds = oddsTds.get(7).text();
				
				//最新概率
				String homePercent = oddsTds.get(9).text();
				String drawPercent = oddsTds.get(10).text();
				String awayPercent = oddsTds.get(11).text();
				
				//最新凯利指数
				String homeKL = oddsTds.get(12).text();
				String drawKL = oddsTds.get(13).text();
				String awayKL = oddsTds.get(14).text();
				
				//赔付率
				String repay = oddsTds.get(15).text();
			}
		}
	}
	
	private void getAHOdds(String refelURL, String url){
		
	}
	
	
	public static void main(String[] args) {
		new AnalyzeUtils().getLeagueOdds();
	}
	
}
