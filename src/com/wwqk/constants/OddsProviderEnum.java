package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum OddsProviderEnum implements IEnum{
	WH("14", "威廉希尔"), 
	BET365("27", "Bet365"),
	LB("82", "立博"),
	ML("84", "澳门彩票"),
	BWIN("94", "Bwin");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private OddsProviderEnum(String key, String value) {
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
