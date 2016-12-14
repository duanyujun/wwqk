package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum LeagueEnum implements IEnum{
	YC("1", "英超"), 
	XJ("2", "西甲"),
	DJ("3", "德甲"),
	YJ("4", "意甲"),
	FJ("5", "法甲");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private LeagueEnum(String key, String value) {
		this.key = key;
		this.value = value;	
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String getName() {
		return this.name();
	}
}
