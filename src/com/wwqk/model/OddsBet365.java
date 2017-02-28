package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class OddsBet365 extends Model<OddsBet365> {
	
	private static final long serialVersionUID = 1L;
	
	public static final OddsBet365 dao = new OddsBet365();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
}
