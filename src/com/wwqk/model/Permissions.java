package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;


/**
 * 权限（资源）
 * 
 * @author java
 * 
 */
public class Permissions extends Model<Permissions> {
	private static final long serialVersionUID = 1L;

	public static Permissions dao = new Permissions();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
}
