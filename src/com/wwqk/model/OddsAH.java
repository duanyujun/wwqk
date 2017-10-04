package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class OddsAH extends Model<OddsAH> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final OddsAH dao = new OddsAH();

	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
