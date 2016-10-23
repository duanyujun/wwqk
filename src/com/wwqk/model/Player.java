package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Player extends Model<Player> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Player dao = new Player();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
