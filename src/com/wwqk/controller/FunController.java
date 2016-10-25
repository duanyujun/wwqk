package com.wwqk.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;

public class FunController extends Controller {

	public void index(){
		List<String> list = new ArrayList<String>();
		for(int i=0; i<10; i++){
			list.add(i+"");
		}
		setAttr("list", list);
		render("fun.jsp");
	}
	
	public void detail(){
		render("funDetail.jsp");
	}
	
}
