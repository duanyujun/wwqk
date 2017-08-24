package com.wwqk.constants;

import com.wwqk.utils.IEnum;

public enum ZgzcwProviderEnum implements IEnum{
	PJOP("0", "平均欧赔"), 
	WLXE("115", "威廉希尔"),
	WD("81", "伟德"),
	BET365("281", "Bet365"),
	LB("82", "立博"),
	AMCP("80", "澳门彩票"),
	HG("545", "皇冠"),
	BWIN("255", "Bwin"),
	MS("517", "明升"),
	LJ("474", "利记"),
	JBB("499", "金宝博"),
	BET10("16", "10BET"),
	CORAL("88", "Coral"),
	IW("104", "Interwetten"),
	NDB("4", "Nordicbet"),
	YSB("90", "易胜博"),
	SNAI("110", "SNAI"),
	ODDS("370", "Oddset"),
	SING("1019", "Singbet"),
	EURO("71", "Eurobet"),
	PINN("177", "Pinnacle"),
	UNIBET("386", "Unibet"),
	SPTB("422", "Sportingbet"),
	BV("1017", "BetVictor"),
	BETFAIR("2", "必发"),
	BETDAQ("54", "BETDAQ"),
	BETCRIS("56", "BETCRIS"),
	BETFRED("156", "Betfred"),
	MATCHBOOK("352", "Matchbook"),
	BETSAFE("462", "Betsafe"),
	BETWAY("482", "Betway"),
	BETSSON("665", "Betsson"),
	CENT("9", "Centrebet"),
	EXPEKT("70", "Expekt"),
	NIKE("97", "Nike"),
	STAN("100", "Stan James"),
	TOTE("128", "Tote"),
	ADMIRAL("134", "Admiral"),
	BODOG("136", "BoDog"),
	TIPSPORT("145", "Tipsport"),
	GAMEBOOKERS("158", "gamebookers"),
	SKYBET("167", "skybet"),
	VICTORY("315", "Victory"),
	BOYLE("354", "Boylesports"),
	CHANCE("384", "Chance"),
	DIMES5("436", "5 Dimes"),
	TOTOSI("460", "TotoSi"),
	BETCLICK("463", "BetClick"),
	DIGIBET("512", "Digibet"),
	TIPICO("527", "Tipico"),
	BETUS("538", "BetUS"),
	EUROIT("580", "Eurobet.it"),
	TITAN("660", "Titanbet"),
	BETCLICIT("709", "BetClic.it"),
	BETCLICKFR("742", "betclick.fr"),
	INTRALOTIT("771", "Intralot Italia"),
	DAFABET("798", "Dafabet"),
	PP("826", "Paddy Power"),
	BETCLICFR("827", "BetClic.fr"),
	WH("850", "William Hill.it"),
	BWINES("933", "bwin.es"),
	PAFES("934", "PAF.es"),
	INTERES("939", "Interwetten.es"),
	BETFLAGIT("941", "Betflag.it"),
	BFES("1054", "Betfair ES Sportsbook"),
	BFUK("1057", "Betfair UK Sportsbook"),
	TITANES("1116","Titanbet.es");
	
	/** 键 */
	private String key;

	/** 值 */
	private String value;
	
	private ZgzcwProviderEnum(String key, String value) {
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
