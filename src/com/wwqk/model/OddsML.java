package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class OddsML extends Model<OddsML> {
	
	private static final long serialVersionUID = 1L;
	
	public static final OddsML dao = new OddsML();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
}
