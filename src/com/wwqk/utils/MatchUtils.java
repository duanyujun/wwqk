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
import org.apache.http.impl.client.DefaultHttpClient;
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
	
	public static Map<String, String> get7MMatchHeader(String matchId){
		   Map<String, String> header = new HashMap<String, String>();  
	       header.put("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01");
	       header.put("Accept-Encoding", "gzip, deflate");  
	       header.put("Accept-Language", "zh-CN,zh;q=0.8");
	       header.put("Host", "report.7m.cn");
	       header.put("Cache-Control", "no-cache");
	       header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
	       header.put("Referer", "http://report.7m.cn/report/gb/"+matchId+".shtml");  
	       header.put("Connection", "keep-alive"); 
	       header.put("Pragma", "no-cache"); 
	       header.put("X-Requested-With", "XMLHttpRequest"); 
	       
	       return header;
	}
	
	
	public static Map<String, String> get7MInjuryHeader(String matchId){
		
		   Map<String, String> header = new HashMap<String, String>();  
	       header.put("Accept", "*/*");
	       header.put("Accept-Encoding", "gzip, deflate");  
	       header.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
	       header.put("Host", "data.7m.com.cn");
	       header.put("Cache-Control", "no-cache");
	       header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
	       header.put("Referer", "http://report.7m.cn/report/gb/"+matchId+".shtml");  
	       header.put("Connection", "keep-alive"); 
	       
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
	
	public static Map<String, String> getMobileBifenHeader(){
		   Map<String, String> header = new HashMap<String, String>();  
	       header.put("Accept", "*/*");
	       header.put("Accept-Encoding", "gzip, deflate");  
	       header.put("Accept-Language", "zh-CN,zh;q=0.8");
	       header.put("Connection", "keep-alive"); 
	       header.put("Host", "m.188bifen.com");
	       header.put("Referer", "http://m.188bifen.com/");  
	       header.put("X-Requested-With", "XMLHttpRequest");
	       header.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
	       
	       return header;
	}
	
	public static String getMobileBifenStr(){
		String url = "http://m.188bifen.com/json/zuqiu.htm?k=0."+String.valueOf((int)(Math.random()*200))+System.currentTimeMillis();
		Connection connect = Jsoup.connect(url).ignoreContentType(true);
		Connection data = connect.data(getMobileBifenHeader());
	    String resultStr = "";
		try {
			Document resultDoc = data.get();
			if(resultDoc!=null){
				resultStr = resultDoc.text();
			}
		} catch (IOException e) {}
		
		return resultStr;
	}
	
	public static Map<String, String> getSofifaHeader(){
		   Map<String, String> header = new HashMap<String, String>();  
	       header.put("Accept", "*/*");
	       header.put("Accept-Encoding", "gzip, deflate");  
	       header.put("Accept-Language", "zh-CN,zh;q=0.9");
	       header.put("Connection", "keep-alive"); 
	       header.put("Host", "sofifa.com");
	       header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36");
	       
	       return header;
	}
	
	public static Map<String, String> getBetHeader(String path) {
		Map<String, String> header = new HashMap<String, String>();

		header.put("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		header.put("accept-encoding", "gzip, deflate, br");
		header.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
		header.put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
		
		header.put("authority", "www.bettingexpert.com");
		header.put("method", "GET");
		header.put("path", path);
		header.put("scheme", "https");

		return header;
	}
	
	
	public static void main(String[] args) throws IOException {
		String SIET_URL = "http://m.188bifen.com/json/zuqiu.htm?k=0.61970021480676236";
//		HttpClient httpclient = new DefaultHttpClient();
//		String content = getMobileBifenContent(httpclient, SIET_URL);
//		Connection connect = Jsoup.connect(SIET_URL).ignoreContentType(true);
//		Connection data = connect.data(getMobileBifenHeader());
//	    String injureStr = null;
//		try {
//			Document injureDoc = data.get();
//			if(injureDoc!=null){
//				injureStr = injureDoc.text();
//				System.err.println(injureStr);
//			}
//		} catch (IOException e) {
//			
//		}
		
	}
	
}
