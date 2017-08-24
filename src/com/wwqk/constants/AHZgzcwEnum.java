package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum AHZgzcwEnum implements IEnum{
	PS("1", "平手"), 
	PSBQ("2", "平/半"),
	BQ("3", "半球"),
	BQYQ("4", "半/一"),
	YQ("5", "一球"),
	YQQB("6", "一/球半"), 
	QB("7", "球半"),
	QBLQ("8", "球半/两"),
	LQ("9", "两球"),
	LQQB("10", "两/两半"),
	LQB("11", "两半"),
	LQSQ("12", "两半/三"),
	SQ("13", "三球"), 
	SSB("14", "三/三半"), 
	SB("15", "三半"), 
	
	MPSBQ("102", "受平/半"),
	MBQ("103", "受半球"),
	MBQYQ("104", "受半/一"),
	MYQ("105", "受一球"),
	MYQQB("106", "受一/球半"), 
	MQB("107", "受球半"),
	MQBLQ("108", "受球半/两"),
	MLQ("109", "受两球"),
	MLQQB("110", "受两/两半"),
	MLQB("111", "受两半"),
	MLQSQ("112", "受两半/三"),
	MSQ("113", "受三球"),
	MSSB("114", "受三/三半"),
	MSB("115", "受三半"), 
	
	OTHERS("1000","其他");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private AHZgzcwEnum(String key, String value) {
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
