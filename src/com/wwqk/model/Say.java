package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Say extends Model<Say> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Say dao = new Say();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
