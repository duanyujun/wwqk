package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class MatchSource extends Model<MatchSource> {
	
	private static final long serialVersionUID = 1L;
	
	public static final MatchSource dao = new MatchSource();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
		
}
