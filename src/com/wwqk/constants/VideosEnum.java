package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum VideosEnum implements IEnum{
	YC("2", "英超"), 
	XJ("3", "西甲"),
	YJ("4", "意甲"),
	DJ("5", "德甲"),
	FJ("6", "法甲"),
	OG("7", "欧冠"),
	ZC("8", "中超"),
	QT("9", "其他");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private VideosEnum(String key, String value) {
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
