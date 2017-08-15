package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum SinaMatchStatusEnum implements IEnum{
	UNSTART("1", "未开赛"), 
	GOING("2", "进行中"),
	END("3", "完场");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private SinaMatchStatusEnum(String key, String value) {
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
