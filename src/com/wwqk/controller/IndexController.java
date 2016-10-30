package com.wwqk.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.wwqk.utils.PageTools;

public class IndexController extends Controller {

	public void index(){
		
		int totalCount = 10;
		int pageSize = 3;
		int pageNo = 1;
		int[] startEndPageNo = PageTools.getRows(pageNo, pageSize, totalCount);
		
		List<String> list = new ArrayList<String>();
		for(int i=0; i<10; i++){
			list.add(i+"");
		}
		setAttr("list", list);
		render("index.jsp");
	}
	
}
