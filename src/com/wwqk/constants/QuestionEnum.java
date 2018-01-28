package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum QuestionEnum implements IEnum{
	Q1("1", "文史"), 
	Q2("2", "自然"),
	Q3("3", "娱乐"),
	Q4("4", "生活常识"),
	Q5("5", "理科"),
	Q6("6", "趣味"),
	Q7("7", "冷知识");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private QuestionEnum(String key, String value) {
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
