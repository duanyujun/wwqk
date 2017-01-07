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
import org.apache.http.protocol.HTTP;

public class FetchHtmlUtils {

	public static String getHtmlContent(HttpClient httpclient, String url){
		HttpGet httpget = new HttpGet(url);
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
							entity.getContent(), HTTP.UTF_8));
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
