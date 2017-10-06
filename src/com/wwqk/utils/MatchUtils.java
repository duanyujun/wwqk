package com.wwqk.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.wwqk.constants.LeagueEnum;

public class MatchUtils {

	//http://www.okooo.com/livecenter/football/?date=2017-02-24
	public static final String MATCH_REFER_URL = "http://www.okooo.com/livecenter/football/";
	
	public static String ODDS_REFER_DETAIL_URL = "http://www.okooo.com/soccer/match/#okMatchId/odds/stat/#providerId/?type=start&range=all";
	public static String ODDS_TARGET_URL =       "http://www.okooo.com/soccer/match/#okMatchId/odds/stat/#providerId?type=start&range=top5&value=all";
	
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
	
	
	public static String getAjaxContent(HttpClient httpclient, String url, String leagueId){
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("Accept", "*/*");
		//httpget.setHeader("Accept-Charset", "gb2312");
		httpget.setHeader("Accept-Encoding", "gzip, deflate");
		httpget.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpget.setHeader("Cache-Control", "no-cache");
		//httpget.setHeader("Connection", "keep-alive");
		httpget.setHeader("Host", "platform.sina.com.cn");
		if(LeagueEnum.YC.getKey().equals(leagueId)){
			httpget.setHeader("Referer","http://sports.sina.com.cn/g/pl/fixtures.html");
		}else if(LeagueEnum.XJ.getKey().equals(leagueId)){
			httpget.setHeader("Referer","http://sports.sina.com.cn/g/laliga/fixtures.html");
		}else if(LeagueEnum.DJ.getKey().equals(leagueId)){
			httpget.setHeader("Referer","http://sports.sina.com.cn/g/bundesliga/fixtures.html");
		}else if(LeagueEnum.YJ.getKey().equals(leagueId)){
			httpget.setHeader("Referer","http://sports.sina.com.cn/g/seriea/fixtures.html");
		}else if(LeagueEnum.FJ.getKey().equals(leagueId)){
			httpget.setHeader("Referer","http://sports.sina.com.cn/g/ligue1/fixtures.html");
		}
		httpget.setHeader("Pragma", "no-cache");
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
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
							entity.getContent(), "UTF-8"));
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
	
	public static String getContentWithJsoupHeader(String refererUrl, String url){
	   Connection connect = Jsoup.connect(url).ignoreContentType(true);  
       Map<String, String> header = new HashMap<String, String>();  
       header.put("Accept", "*/*");
       header.put("Accept-Encoding", "gzip, deflate");  
       header.put("Accept-Language", "zh-CN,zh;q=0.8");
       header.put("Host", "platform.sina.com.cn");
       header.put("Referer", refererUrl);  
       header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
       header.put("Connection", "keep-alive");  
       Connection data = connect.data(header);  
       Document document = null;
	   try {
			document = data.get();
	   } catch (IOException e) {
			e.printStackTrace();
	   }  
       return document.text();
	}
	
	public static Map<String, String> get24zbwHeader(){
	   Map<String, String> header = new HashMap<String, String>();  
       header.put("Accept", "*/*");
       header.put("Accept-Encoding", "gzip, deflate");  
       header.put("Accept-Language", "zh-CN,zh;q=0.8");
       header.put("Host", "www.24zbw.com");
       header.put("Cache-Control", "no-cache");  
       header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
       header.put("Referer", "http://www.24zbw.com/match/hot/");  
       header.put("Connection", "keep-alive");  
       header.put("Pragma", "no-cache");  
       
       return header;
	}
	
	public static Map<String, String> getZuqiulaHeader(){
		   Map<String, String> header = new HashMap<String, String>();  
	       header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
	       header.put("Accept-Encoding", "gzip, deflate");  
	       header.put("Accept-Language", "zh-CN,zh;q=0.8");
	       header.put("Host", "www.zuqiu.la");
	       header.put("Cache-Control", "no-cache");  
	       header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
	       header.put("Connection", "keep-alive");  
	       header.put("Pragma", "no-cache");  
	       
	       return header;
		}
	
	public static Map<String, String> getZgzcwHeader(String refererURL){
		   Map<String, String> header = new HashMap<String, String>();  
		   header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
	       header.put("Accept-Encoding", "gzip, deflate");  
	       header.put("Accept-Language", "zh-CN,zh;q=0.8");
	       header.put("Cache-Control", "no-cache");
	       header.put("Host", "fenxi.zgzcw.com");
	       header.put("Pragma", "no-cache");  
	       header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
	       header.put("Referer", refererURL);  
	       return header;
	}
	
	public static Map<String, String> postZgzcwHeader(String refererURL){
		   Map<String, String> header = new HashMap<String, String>();  
		   header.put("Accept", "text/html, */*; q=0.01");
	       header.put("Accept-Encoding", "gzip, deflate");  
	       header.put("Accept-Language", "zh-CN,zh;q=0.8");
	       header.put("Content-Type", "application/x-www-form-urlencoded");
	       header.put("Host", "saishi.zgzcw.com");
	       header.put("Origin", "http://saishi.zgzcw.com");
	       header.put("X-Requested-With", "XMLHttpRequest");
	       header.put("Pragma", "no-cache");  
	       header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
	       header.put("Referer", refererURL);  
	       return header;
	}
	
	public static Map<String, String> get7MHeader(){
		   Map<String, String> header = new HashMap<String, String>();  
	       header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
	       header.put("Accept-Encoding", "gzip, deflate");  
	       header.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
	       header.put("Host", "report.7m.cn");
	       header.put("Cache-Control", "no-cache");
	       header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
	       //header.put("Referer", "http://news.7m.cn/report/index_gb.shtml");  
	       header.put("Connection", "keep-alive"); 
	       header.put("Pragma", "no-cache"); 
	       
	       return header;
	}
	
	public static String getZgzcwHtmlContent(HttpClient httpclient, String refererUrl, String url){
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("Accept", "text/html, */*");
		httpget.setHeader("Accept-Charset", "utf-8");
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
							entity.getContent(), "utf-8"));
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
	
	
	public static void main(String[] args) throws IOException {
		String SIET_URL = "http://fenxi.zgzcw.com/2247970/bjop";
		String refererUrl = "http://saishi.zgzcw.com/soccer/league/36/2017-2018/";
		Connection connect = Jsoup.connect(SIET_URL).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.getZgzcwHeader(refererUrl));
		Document document = data.get();
		System.err.println(document.html());
		
		//赛事
//		String refererURL = "http://saishi.zgzcw.com/soccer/league/11/2017-2018/";
//		String matchURL = "http://saishi.zgzcw.com/summary/liansaiAjax.action";
//		Connection con = Jsoup.connect(matchURL);
//		con.data("source_league_id", "11");
//		con.data("currentRound", "1");
//		con.data("season", "2017-2018");
//		con.data("seasonType", "");
//		//con.data(postZgzcwHeader(refererURL));
//		for(Map.Entry<String, String> entry:postZgzcwHeader(refererURL).entrySet()){
//			con.header(entry.getKey(), entry.getValue());
//		}
//		Document document = con.post();
//		System.err.println(document.html());
	}
	
}
