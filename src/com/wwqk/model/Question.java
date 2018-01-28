package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Question extends Model<Question> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Question dao = new Question();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
		
}
