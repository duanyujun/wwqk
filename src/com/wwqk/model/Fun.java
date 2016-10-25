package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Fun extends Model<Fun> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Fun dao = new Fun();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
