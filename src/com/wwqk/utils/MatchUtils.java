package com.wwqk.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

public class MatchUtils {

	//http://www.okooo.com/livecenter/football/?date=2017-02-24
	public static final String MATCH_REFER_URL = "http://www.okooo.com/livecenter/football/";
	
	private static HttpClient httpclient = new DefaultHttpClient();
	
	public static String ODDS_REFER_DETAIL_URL = "http://www.okooo.com/soccer/match/#okMatchId/odds/stat/#providerId/?type=start&range=all";
	public static String ODDS_TARGET_URL =       "http://www.okooo.com/soccer/match/#okMatchId/odds/stat/#providerId?type=start&range=top5&value=all";
	
	public static void main(String[] args) {
		String content = getHtmlContent(httpclient, MATCH_REFER_URL, "http://www.okooo.com/livecenter/football/?date=2017-02-24");
		//FileUtils.appendFile(content);
	}
	
	public static String getHtmlContent(HttpClient httpclient, String refererUrl, String url){
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("Accept", "text/html, */*");
		httpget.setHeader("Accept-Charset", "gb2312");
		//httpget.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpget.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpget.setHeader("Cache-Control", "no-cache");
		//httpget.setHeader("Connection", "keep-alive");
		httpget.setHeader("Host", "www.okooo.com");
		httpget.setHeader("Pragma", "no-cache");
		httpget.setHeader("Referer", refererUrl);
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		httpget.setHeader("X-Requested-With", "XMLHttpRequest");
		
		HttpResponse response;
		StringBuffer result = new StringBuffer();
		try {
			response = httpclient.execute(httpget);
			Integer statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return "";
			}
			HttpEntity entity = response.getEntity();
			BufferedReader rd = null;				
			if(entity != null) {
				try {
					rd = new BufferedReader(new InputStreamReader(
							entity.getContent(), "gb2312"));
					String tempLine = rd.readLine();
					while (tempLine != null) {
						result.append(tempLine);
						tempLine = rd.readLine();
					}
				} catch (IOException e) {
					throw e;
				} finally {						
					if (httpget != null) {
						httpget.abort();
					}

					if (rd != null) {
						rd.close();
					}
				}
				
			}	
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	
}
