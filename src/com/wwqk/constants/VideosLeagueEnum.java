package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum VideosLeagueEnum implements IEnum{
	YC("2", "1"), 
	XJ("3", "2"),
	YJ("4", "4"),
	DJ("5", "3"),
	FJ("6", "5"),
	OG("7", "6"),
	ZC("8", "7"),
	QT("9", "8");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private VideosLeagueEnum(String key, String value) {
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
