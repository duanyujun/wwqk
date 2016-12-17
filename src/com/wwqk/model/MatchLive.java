package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class MatchLive extends Model<MatchLive> {
	
	private static final long serialVersionUID = 1L;
	
	public static final MatchLive dao = new MatchLive();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
		
}
