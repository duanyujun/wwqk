package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.model.Player;
import com.wwqk.model.Say;
import com.wwqk.utils.PageUtils;
import com.wwqk.utils.StringUtils;
import com.wwqk.utils.ValidateUtils;

public class SayController extends Controller {

	public void index(){
		Page<Say> sayPage = Say.dao.paginate(getParaToInt("pageNumber", 1), 10, "");
		setAttr("sayPage", sayPage);
		setAttr("pageUI", PageUtils.calcStartEnd(sayPage));
		render("say.jsp");
	}
	
	public void list(){
		String playerId = getPara("id");
		if(!ValidateUtils.validatePlayerId(playerId)){
			redirect("/say");
		}
		setAttr("player", Player.dao.findByIdWithTeamName(playerId));
		
		//说说
		Page<Say> sayPage = Say.dao.paginate(getParaToInt("pageNumber", 1), 10, "and player_id = '"+playerId+"'");
		if(sayPage.getTotalRow()==0){
			setAttr("NO_SAY", "1");
			sayPage = Say.dao.paginate(getParaToInt("pageNumber", 1), 5, "");
		}
		setAttr("sayPage", sayPage);
		setAttr("pageUI", PageUtils.calcStartEnd(sayPage));
		
		render("sayList.jsp");
	}
	
	public void detail(){
		String id = getPara("id");
		if(StringUtils.isNotBlank(id)){
			Say say = Say.dao.findById(id);
			setAttr("say", say);
		}else{
			redirect("/say");
		}
		render("sayDetail.jsp");
	}
}
