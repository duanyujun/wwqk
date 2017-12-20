package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Sofifa extends Model<Sofifa> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Sofifa dao = new Sofifa();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
}
