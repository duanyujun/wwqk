package com.wwqk.controller;

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
		renderJson(MatchUtils.getMobileBifenStr());
	}
	
}
