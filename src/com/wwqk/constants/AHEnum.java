package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum AHEnum implements IEnum{
	PS("1", "平手"), 
	PSBQ("2", "平手/半球"),
	BQ("3", "半球"),
	BQYQ("4", "半球/一球"),
	YQ("5", "一球"),
	YQQB("6", "一球/球半"), 
	QB("7", "球半"),
	QBLQ("8", "球半/两球"),
	LQ("9", "两球"),
	LQQB("10", "两球/两球半"),
	LQB("11", "两球半"),
	LQSQ("12", "两球半/三球"),
	SQ("13", "三球"), 
	
	MPSBQ("102", "受平手/半球"),
	MBQ("103", "受半球"),
	MBQYQ("104", "受半球/一球"),
	MYQ("105", "受一球"),
	MYQQB("106", "受一球/球半"), 
	MQB("107", "受球半"),
	MQBLQ("108", "受球半/两球"),
	MLQ("109", "受两球"),
	MLQQB("110", "受两球/两球半"),
	MLQB("111", "受两球半"),
	MLQSQ("112", "受两球半/三球"),
	MSQ("113", "受三球"),
	
	OTHERS("1000","其他");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private AHEnum(String key, String value) {
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
