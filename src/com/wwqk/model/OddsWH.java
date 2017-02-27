package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class OddsWH extends Model<OddsWH> {
	
	private static final long serialVersionUID = 1L;
	
	public static final OddsWH dao = new OddsWH();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
}
