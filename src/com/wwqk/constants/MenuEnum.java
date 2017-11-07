package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum MenuEnum implements IEnum{
	INDEX("1", "首页"), 
	FUN("2", "趣点"),
	LIVE("3", "直播"),
	VIDEO("4", "集锦"),
	DATA("5", "数据");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private MenuEnum(String key, String value) {
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
