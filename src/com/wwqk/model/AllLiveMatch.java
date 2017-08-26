package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class AllLiveMatch extends Model<AllLiveMatch> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final AllLiveMatch dao = new AllLiveMatch();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
}
