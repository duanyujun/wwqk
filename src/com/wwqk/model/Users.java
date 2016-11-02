package com.wwqk.model;

import java.util.Collection;
import java.util.Map;

import com.jfinal.plugin.activerecord.Model;


/**
 * 用户
 * 
 * @author java
 * 
 */
public class Users extends Model<Users> {
	private static final long serialVersionUID = 2920278340134539131L;

	public static Users dao = new Users();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
	public Collection<String> getRoleNameList() {
		return null;
	}
}
