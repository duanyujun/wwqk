package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class MatchSourceSina extends Model<MatchSourceSina> {
	
	private static final long serialVersionUID = 1L;
	
	public static final MatchSourceSina dao = new MatchSourceSina();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
		
}
