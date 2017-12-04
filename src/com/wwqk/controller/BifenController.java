package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.MenuEnum;
import com.wwqk.utils.MatchUtils;

public class BifenController extends Controller {

	public void index(){
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.BIFEN.getKey());
		render("bifen.jsp");
	}
	
	public void mobile(){
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.BIFEN.getKey());
		render("mobile.jsp");
	}
	
	public void mobileJson(){
		renderJson(MatchUtils.getMobileBifenStr());
	}
	
}
