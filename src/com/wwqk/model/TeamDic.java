package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class TeamDic extends Model<TeamDic> {
	
	private static final long serialVersionUID = 1L;
	
	public static final TeamDic dao = new TeamDic();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
		
}
