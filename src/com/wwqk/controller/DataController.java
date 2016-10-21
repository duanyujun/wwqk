package com.wwqk.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;

public class DataController extends Controller {

	public void index(){
		List<String> positionlist = new ArrayList<String>();
		for(int i=0; i<20; i++){
			positionlist.add(i+"");
		}
		setAttr("positionlist", positionlist);
		
		List<String> shooterlist = new ArrayList<String>();
		for(int i=0; i<15; i++){
			shooterlist.add(i+"");
		}
		setAttr("shooterlist", shooterlist);
		
		List<String> assistlist = new ArrayList<String>();
		for(int i=0; i<15; i++){
			assistlist.add(i+"");
		}
		setAttr("assistlist", assistlist);
		
		render("data.jsp");
	}
	
}
