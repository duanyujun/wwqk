package com.wwqk.plugin;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.wwqk.utils.MatchUtils;

/** 
 * 
 *    
 */

public class News7M {
	
	private static final String SITE_URL = "http://report.7m.cn/data/index/gb.js?";
	
	//http://report.7m.cn/data/game/gb/2100312.js?1507515205545&_=1507515205047
	
	public static void crawl(){
		Connection connect = Jsoup.connect(SITE_URL+System.currentTimeMillis()).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.get7MHeader());
		try {
			Document document = data.get();
			
			System.err.println(document.html());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		crawl();
	}
	
}
