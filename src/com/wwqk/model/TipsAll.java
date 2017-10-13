package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class TipsAll extends Model<TipsAll> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final TipsAll dao = new TipsAll();

	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
