package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Videos extends Model<Videos> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Videos dao = new Videos();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
		
}
