package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class TipsMatch extends Model<TipsMatch> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final TipsMatch dao = new TipsMatch();

	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
