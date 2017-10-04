package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum AHZgzcwAmountEnum implements IEnum{
	PS("1", "0"), 
	PSBQ("2", "-0.25"),
	BQ("3", "-0.5"),
	BQYQ("4", "-0.75"),
	YQ("5", "-1"),
	YQQB("6", "-1.25"), 
	QB("7", "-1.5"),
	QBLQ("8", "-1.75"),
	LQ("9", "-2"),
	LQQB("10", "-2.25"),
	LQB("11", "-2.5"),
	LQSQ("12", "-2.75"),
	SQ("13", "-3"), 
	SSB("14", "-3.25"), 
	SB("15", "-3.5"), 
	SBSQ("16","-3.75"),
	SIQ("17","-4"),
	
	MPSBQ("102", "0.25"),
	MBQ("103", "0.5"),
	MBQYQ("104", "0.75"),
	MYQ("105", "1"),
	MYQQB("106", "1.25"), 
	MQB("107", "1.5"),
	MQBLQ("108", "1.75"),
	MLQ("109", "2"),
	MLQQB("110", "2.25"),
	MLQB("111", "2.5"),
	MLQSQ("112", "2.75"),
	MSQ("113", "3"),
	MSSB("114", "3.25"),
	MSB("115", "3.5"), 
	
	OTHERS("1000","其他");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private AHZgzcwAmountEnum(String key, String value) {
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
