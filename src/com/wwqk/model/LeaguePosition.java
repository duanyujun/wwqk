package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class LeaguePosition extends Model<LeaguePosition> {
	
	private static final long serialVersionUID = 1L;
	public static final LeaguePosition dao = new LeaguePosition();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
