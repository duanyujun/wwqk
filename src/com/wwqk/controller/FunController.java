package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.model.Fun;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.PageUtils;
import com.wwqk.utils.StringUtils;

public class FunController extends Controller {

	public void index(){
		Page<Fun> funPage = Fun.dao.paginate(getParaToInt("pageNumber", 1), 10, 1);
		setAttr("funPage", funPage);
		setAttr("pageUI", PageUtils.calcStartEnd(funPage));
		
		render("fun.jsp");
	}
	
	public void detail(){
		String id = getPara("id");
		id = CommonUtils.getRewriteId(id);
		if(StringUtils.isBlank(id)){
			redirect("/fun");
		}
		
		Fun fun = Fun.dao.findById(id);
		setAttr("fun", fun);
		
		render("funDetail.jsp");
	}
	
}
