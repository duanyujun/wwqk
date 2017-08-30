package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum ZgzcwAHProviderEnum implements IEnum{
	PJOP("1", "澳门彩票"), 
	WLXE("3", "皇冠"),
	BET365("8", "Bet365"),
	LB("12", "易胜博"),
	WD("14", "伟德"),
	MS("17", "明升"),
	AMCP("22", "10bet"),
	JBB("23", "金宝博"),
	HG("24", "沙巴"),
	BWIN("255", "Bwin"),
	LJ("31", "利记"),
	YH("35", "盈禾"),
	LL("41","marathonbet")
	;
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private ZgzcwAHProviderEnum(String key, String value) {
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
