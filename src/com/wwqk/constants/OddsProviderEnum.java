package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum OddsProviderEnum implements IEnum{
	WH("14", "威廉希尔"), 
	BET365("27", "Bet365"),
	LB("82", "立博"),
	ML("84", "澳门彩票"),
	BWIN("94", "Bwin"),
	
	AV99("24", "99家平均"),
	JC("2", "竞彩官方"),
	IW("24", "Interwetten"),
	SNAI("24", "SNAI"),
	WD("24", "伟德国际"),
	YSB("24", "易胜博"),
	EX("24", "Expekt"),
	UN("24", "Unibet"),
	BTT("24", "博天堂"),
	BF("24", "必发"),
	AC("24", "澳门彩票"),
	CB("24", "Centrebet"),
	CORAL("24", "Coral"),
	GB("24", "gamebookers"),
	IL("24", "Iceland"),
	NW("24", "Norway"),
	OS("24", "Oddset"),
	PP("24", "Paddy Power"),
	SB("24", "Skybet"),
	STS("24", "STS"),
	SD("24", "Sweden"),
	TT("24", "Toto"),
	TS("24", "TotoSi"),
	M88("24", "Mansion 88"),
	SBET("24", "皇冠(Singbet)"),
	IB("24", "沙巴(IBCBET)");
	
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
