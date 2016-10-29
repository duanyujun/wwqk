package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Team extends Model<Team> {
	
	
	private static final long serialVersionUID = 1L;
	public static final Team dao = new Team();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
