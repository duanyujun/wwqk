package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class TaobaoAlliance extends Model<TaobaoAlliance> {
	
	private static final long serialVersionUID = 1L;
	
	public static final TaobaoAlliance dao = new TaobaoAlliance();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
		
}
