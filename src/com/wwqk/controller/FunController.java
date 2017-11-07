package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.MenuEnum;
import com.wwqk.model.Article;
import com.wwqk.model.Fun;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.PageUtils;
import com.wwqk.utils.StringUtils;

public class FunController extends Controller {

	public void index(){
		Page<Fun> funPage = Fun.dao.paginate(getParaToInt("pageNumber", 1), 10, 1);
		setAttr("funPage", funPage);
		setAttr("pageUI", PageUtils.calcStartEnd(funPage));
		setAttr("initCount", funPage.getList().size());
		
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.FUN.getKey());
		render("fun.jsp");
	}
	
	public void listMore(){
		Page<Fun> funPage = Fun.dao.paginate(getParaToInt("pageNo", 1), 10, 1);
		renderJson(funPage.getList());
	}
	
	public void detail(){
		String id = getPara("id");
		id = CommonUtils.getRewriteId(id);
		if(StringUtils.isBlank(id)){
			redirect("/fun");
			return;
		}
		
		Fun fun = Fun.dao.findById(id);
		if(fun.get("player_id")!=null){
			Article article = Article.dao.findFirst("select * from article where fun_id = ? ", id);
			fun.set("content", article.get("content"));
		}
		setAttr("fun", fun);
		
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.FUN.getKey());
		render("funDetail.jsp");
	}
	
}
