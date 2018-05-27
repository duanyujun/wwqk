package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.MenuEnum;
import com.wwqk.model.TaobaoAlliance;
import com.wwqk.utils.PageUtils;

public class GoodsController extends Controller {

	public void index(){
				
		Page<TaobaoAlliance> goodsPage = TaobaoAlliance.dao.paginate(getParaToInt("pageNumber", 1), 36);
		setAttr("goodsPage", goodsPage);
		setAttr("initCount", goodsPage.getList().size());		
		
		setAttr("pageContent", PageUtils.getPageContent("/goods", goodsPage, ""));
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.GOODS.getKey());
		render("new/goods.jsp");
	}
	
}
