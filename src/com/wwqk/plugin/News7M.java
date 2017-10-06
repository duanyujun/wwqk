package com.wwqk.plugin;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.wwqk.utils.MatchUtils;

/** 
 * 
 * @date 创建时间：2017-10-5 下午9:30:56    
 */

public class News7M {
	
	private static final String SITE_URL = "http://report.7m.cn/data/index/gb.js?";
	
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
