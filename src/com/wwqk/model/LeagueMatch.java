package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class LeagueMatch extends Model<LeagueMatch> {
	
	private static final long serialVersionUID = 1L;
	
	public static final LeagueMatch dao = new LeagueMatch();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
}
