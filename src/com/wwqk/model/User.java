package com.wwqk.model;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User> {
	
	public static final User dao = new User();
	public static final int SEX_MALE = 1;
	public static final int SEX_FEMALE = 2;
	
}
