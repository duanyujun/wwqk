package com.wwqk.constants;

public class FlagMask {

	//球队相关
	/** 球队名称 */
	private static final int TEAM_NAME_MASK = 1<<0;
	
	/** 球队照片 */
	private static final int TEAM_IMG_MASK = 1<<1;
	
	/** 球场照片 */
	private static final int TEAM_VENUE_IMG_MASK = 1<<2;
	
	/** 球场名称（容量和地址都保存在一起） */
	private static final int TEAM_VENUE_NAME_MASK = 1<<3;
	
	//球员相关
	/** 球员名字 */
	private static final int PLAYER_NAME_MASK = 1<<0;
	
	/** 球员照片 */
	private static final int PLAYER_IMG_MASK = 1<<1;
	
	/** 球员号码 */
	private static final int PLAYER_NUMBER_MASK = 1<<2;
	
	/** 球员出生国家 */
	private static final int PLAYER_COUNTRY_MASK = 1<<3;
	
	
}
