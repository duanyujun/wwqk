package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.MenuEnum;

public class GoodsController extends Controller {

	public void index(){
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.GOODS.getKey());
		
		render("goods.jsp");
	}
	
}
