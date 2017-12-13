package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum PlayerEnum implements IEnum{
	QQ("1", "QQ"), 
	PPTV("2", "PPTV"),
	SSPORTS("3", "SSPORTS"),
	LETV("4", "LETV"),
	CNTV("5", "CNTV"),
	YOUKU("6","YOUKU"),
	SINA("7","SINA"),
	SINA2("8","SINA2"),
	TUDOU("9","TUDOU"),
	QQNBA("10","QQNBA"),
	V56("11","56"),
	SOHU("12","SOHU"),
	KANDIAN("13","KANDIAN");
	
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
