package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum LeagueENEnum implements IEnum{
	YC("1", "premier-league"), 
	XJ("2", "primera-division"),
	DJ("3", "bundesliga"),
	YJ("4", "sesie-a"),
	FJ("5", "ligue-1");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private LeagueENEnum(String key, String value) {
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
