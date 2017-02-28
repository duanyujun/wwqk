package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class OddsLB extends Model<OddsLB> {
	
	private static final long serialVersionUID = 1L;
	
	public static final OddsLB dao = new OddsLB();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
}
