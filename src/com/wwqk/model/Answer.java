package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Answer extends Model<Answer> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Answer dao = new Answer();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
		
}
