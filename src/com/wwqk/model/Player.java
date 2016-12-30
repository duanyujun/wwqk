package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Player extends Model<Player> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Player dao = new Player();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
	public Player findByIdWithTeamName(String id){
		Player player = dao.findFirst("select p.*, t.name team_name, t.name_en team_name_en, t.cloth, l.id league_id from player p, team t, league l where p.team_id = t.id and t.league_id = l.id and p.id = ? ", id);
		return player;
	}
}
