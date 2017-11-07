package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class VideosRealLinks extends Model<VideosRealLinks> {
	
	private static final long serialVersionUID = 1L;
	
	public static final VideosRealLinks dao = new VideosRealLinks();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
		
}
