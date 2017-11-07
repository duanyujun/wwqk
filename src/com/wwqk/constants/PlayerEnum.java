package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum PlayerEnum implements IEnum{
	QQ("1", "QQ"), 
	PPTV("2", "PPTV"),
	SSPORTS("3", "SSPORTS");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private PlayerEnum(String key, String value) {
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
