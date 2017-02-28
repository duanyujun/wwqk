package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class OddsBwin extends Model<OddsBwin> {
	
	private static final long serialVersionUID = 1L;
	
	public static final OddsBwin dao = new OddsBwin();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
}
