package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class MatchGuess extends Model<MatchGuess> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final MatchGuess dao = new MatchGuess();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
}
