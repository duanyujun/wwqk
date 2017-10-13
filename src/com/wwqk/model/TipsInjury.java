package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class TipsInjury extends Model<TipsInjury> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final TipsInjury dao = new TipsInjury();

	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
