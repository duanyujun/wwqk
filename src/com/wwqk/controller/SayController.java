package com.wwqk.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.MenuEnum;
import com.wwqk.model.Fun;
import com.wwqk.model.Player;
import com.wwqk.model.Say;
import com.wwqk.model.Transfer;
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
		int pageNumber = 1;
		if(playerId.contains("_")){
			pageNumber = Integer.valueOf(CommonUtils.getRewriteMatchKey(playerId));
		}
		playerId = CommonUtils.getRewriteId(playerId);
		if(StringUtils.isBlank(playerId)){
			redirect("/say");
			return;
		}
		
		Player player = Player.dao.findByIdWithTeamName(playerId);
		if(player==null){
			player = Player.dao.findById(playerId);
			if(player==null){
				redirect("/say");
				return;
			}
		}
		setAttr("enname", player.getStr("en_url").replaceAll("-", " "));
		if(player.getStr("nationality").contains("波斯尼亚和黑塞哥维那")){
			player.set("nationality", "波黑");
		}
		
		setAttr("player", player);
		
		//说说
		Page<Say> sayPage = Say.dao.paginate(pageNumber, 10, "and player_id = '"+playerId+"'");
		if(sayPage.getTotalRow()==0){
			if(StringUtils.isNotBlank(player.getStr("league_id"))){
				setAttr("NO_SAY", "1");
				sayPage = Say.dao.paginate(pageNumber, 5, "AND EXISTS (SELECT * FROM team t, player p WHERE t.`id` = p.`team_id` AND p.id = say.player_id AND t.`league_id` = '"+player.getStr("league_id")+"')");
			}
		}
		if(StringUtils.isNotBlank(player.getStr("league_id"))){
			setAttr("leagueName", EnumUtils.getValue(LeagueEnum.values(), player.getStr("league_id")));
		}
		setAttr("sayPage", sayPage);
		setAttr("pageUI", PageUtils.calcStartEnd(sayPage));
		setAttr("initCount", sayPage.getList().size());
		
		//转会
		List<Transfer> lstTransfer = Transfer.dao.find("select * from transfer where player_id = ? order by date desc",playerId);
		setAttr("lstTransfer", lstTransfer);
		
		//最近的新闻
		List<Fun> lstNews = Fun.dao.find("select id, title, create_time, title_en from fun where type = 1 and player_id = ? order by create_time desc", playerId);
		setAttr("lstNews", lstNews);
		
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.DATA.getKey());
		render("sayList.jsp");
	}
	
	public void listPlayerMore(){
		String playerId = getPara("id");
		playerId = CommonUtils.getRewriteId(playerId);
		//球员的说说
		Page<Say> sayPage = Say.dao.paginate(getParaToInt("pageNo", 1), 10, "and player_id = '"+playerId+"'");
		renderJson(sayPage.getList());
	}
	
	public void detail(){
		String id = getPara("id");
		id = CommonUtils.getRewriteId(id);
		if(StringUtils.isBlank(id)){
			redirect("/say");
			return;
		}
		Say say = Say.dao.findById(id);
		setAttr("say", say);
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.INDEX.getKey());
		render("sayDetail.jsp");
	}
}
