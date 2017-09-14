package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Transfer extends Model<Transfer> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Transfer dao = new Transfer();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
