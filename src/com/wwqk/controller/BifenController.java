package com.wwqk.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jfinal.core.Controller;
import com.wwqk.utils.MatchUtils;

public class BifenController extends Controller {

	public void index(){
		render("bifen.jsp");
	}
	
	public void mobile(){
		render("mobile.jsp");
	}
	
	public void mobileJson(){
		String url = "http://m.188bifen.com/json/zuqiu.htm?k=0."+String.valueOf((int)(Math.random()*200))+System.currentTimeMillis();
		Connection connect = Jsoup.connect(url).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.getMobileBifenHeader());
	    String injureStr = "";
		try {
			Document injureDoc = data.get();
			if(injureDoc!=null){
				injureStr = injureDoc.text();
			}
		} catch (IOException e) {}
		Map<String, String> map = new HashMap<String, String>();
		map.put("data", injureStr);
		renderJson(map);
	}
	
}
