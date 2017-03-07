package com.wwqk.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.Player;
import com.wwqk.model.Say;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.PageUtils;
import com.wwqk.utils.StringUtils;

public class SayController extends Controller {

	public void index(){
		Page<Say> sayPage = Say.dao.paginate(getParaToInt("pageNumber", 1), 10, "");
		setAttr("sayPage", sayPage);
		setAttr("pageUI", PageUtils.calcStartEnd(sayPage));
		setAttr("initCount", sayPage.getList().size());
		render("say.jsp");
	}
	
	public void listMore(){
		Page<Say> sayPage = Say.dao.paginate(getParaToInt("pageNo", 1), 10, "");
		renderJson(sayPage.getList());
	}
	
	public void list(){
		String playerId = getPara("id");
		playerId = CommonUtils.getRewriteId(playerId);
		if(StringUtils.isBlank(playerId)){
			redirect("/say");
		}
		
		Player player = Player.dao.findByIdWithTeamName(playerId);
		if(player==null){
			redirect("/say");
		}
		setAttr("player", player);
		
		//说说
		Page<Say> sayPage = Say.dao.paginate(getParaToInt("pageNumber", 1), 10, "and player_id = '"+playerId+"'");
		if(sayPage.getTotalRow()==0){
			setAttr("NO_SAY", "1");
			sayPage = Say.dao.paginate(getParaToInt("pageNumber", 1), 5, "AND EXISTS (SELECT * FROM team t, player p WHERE t.`id` = p.`team_id` AND p.id = say.player_id AND t.`league_id` = '"+player.getStr("league_id")+"')");
		}
		setAttr("leagueName", EnumUtils.getValue(LeagueEnum.values(), player.getStr("league_id")));
		setAttr("sayPage", sayPage);
		setAttr("pageUI", PageUtils.calcStartEnd(sayPage));
		
		render("sayList.jsp");
	}
	
	public void detail(){
		String id = getPara("id");
		id = CommonUtils.getRewriteId(id);
		if(StringUtils.isBlank(id)){
			redirect("/say");
		}
		Say say = Say.dao.findById(id);
		setAttr("say", say);
		
		render("sayDetail.jsp");
	}
}
