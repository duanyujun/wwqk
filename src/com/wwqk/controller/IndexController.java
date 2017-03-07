package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.model.Fun;
import com.wwqk.utils.PageUtils;

public class IndexController extends Controller {

	public void index(){
		Page<Fun> funPage = Fun.dao.paginate(getParaToInt("pageNumber", 1), 10, 0);
		setAttr("funPage", funPage);
		setAttr("pageUI", PageUtils.calcStartEnd(funPage));
		setAttr("initCount", funPage.getList().size());
		render("index.jsp");
	}
	
	public void listMore(){
		Page<Fun> funPage = Fun.dao.paginate(getParaToInt("pageNo", 1), 10, 0);
		renderJson(funPage.getList());
	}
	
}
