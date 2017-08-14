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
	IW("43", "Interwetten"),
	SNAI("25", "SNAI"),
	WD("65", "伟德国际"),
	YSB("35", "易胜博"),
	UB("36", "Eurobet"),
	EX("37", "Expekt"),
	UN("180", "Unibet"),
	BTT("159", "博天堂"),
	BF("19", "必发"),
	AC("84", "澳门彩票"),
	CB("17", "Centrebet"),
	CORAL("116", "Coral"),
	GB("126", "gamebookers"),
	IL("134", "Iceland"),
	NW("150", "Norway"),
	OS("18", "Oddset"),
	PP("49", "Paddy Power"),
	SB("157", "Skybet"),
	STS("168", "STS"),
	SD("170", "Sweden"),
	TT("285", "Toto"),
	TS("286", "TotoSi"),
	M88("307", "Mansion 88"),
	SBET("250", "皇冠(Singbet)"),
	IB("220", "沙巴(IBCBET)"),
	SBO("280", "利记(sbobet)"),
	HKH("131", "香港马会"),
	JBB("322", "金宝博(188bet)"),
	OTBet("406", "12bet.com"),
	OTE("805", "138sungame"),
	OE("578", "18Bet"),
	HB("197", "互博(Hooball)"),
	ONSZ("871", "1960bet"),
	OBET("516", "1Bet"),
	OX("744", "1xbet");
	
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
