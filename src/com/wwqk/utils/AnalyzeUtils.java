package com.wwqk.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class AnalyzeUtils {
	
	private HttpClient httpClient = new DefaultHttpClient();
	private static final String EN_REFEL_URL = "http://www.okooo.com/soccer/league/17/schedule/10912/";
	private static final String[] EN_URL = {"http://www.okooo.com/soccer/league/17/schedule/11367/",
		"http://www.okooo.com/soccer/league/17/schedule/8186/",
		"http://www.okooo.com/soccer/league/17/schedule/12084/",
		"http://www.okooo.com/soccer/league/17/schedule/12651/"};
	
	public void getLeagueOdds(){
		String content = MatchUtils.getHtmlContent(httpClient, EN_REFEL_URL, EN_URL[0]);
		System.err.println(content);
	}
	
	public static void main(String[] args) {
		new AnalyzeUtils().getLeagueOdds();
	}
	
}
