package com.wwqk.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.model.Player;
import com.wwqk.model.Say;
import com.wwqk.utils.PageUtils;

public class SayController extends Controller {

	public void index(){
		Page<Say> sayPage = Say.dao.paginate(getParaToInt("pageNumber", 1), 10);
		setAttr("sayPage", sayPage);
		setAttr("pageUI", PageUtils.calcStartEnd(sayPage));
		render("say.jsp");
	}
	
	public void list(){
		String playerId = getPara("id");
		if(playerId==null){
			playerId = "3051";
		}
		Player player = Player.dao.findFirst("select p.*, t.name team_name from player p, team t where p.team_id = t.id and p.id = ? ", playerId);
		setAttr("player", player);
		
		List<String> list = new ArrayList<String>();
		for(int i=0; i<10; i++){
			list.add(i+"");
		}
		setAttr("list", list);
		render("sayList.jsp");
	}
	
}
