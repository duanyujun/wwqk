package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class LeagueMatchHistory extends Model<LeagueMatchHistory> {
	
	private static final long serialVersionUID = 1L;
	
	public static final LeagueMatchHistory dao = new LeagueMatchHistory();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
}
