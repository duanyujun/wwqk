package com.wwqk.constants;

import com.jfinal.plugin.activerecord.Model;
import com.wwqk.utils.StringUtils;

public class FlagMask {

	//球队相关
	/** 球队名称 */
	public static final int TEAM_NAME_MASK = 0x00000001;
	
	/** 球场名称  */
	public static final int TEAM_VENUE_NAME_MASK = 0x00000002;
	
	/** 球场小照片 */
	public static final int TEAM_VENUE_IMG_MASK = 0x00000004;
	
	/** 球场大图片 */
	public static final int TEAM_VENUE_IMG_BIG_MASK = 0x00000008;
	
	/** 球场城市 */
	public static final int TEAM_VENUE_CITY_MASK = 0x00000010;
	
	//球员相关
	/** 球员名称 */
	public static final int PLAYER_NAME_MASK = 0x00000001;
	
	/** 球员身高 */
	public static final int PLAYER_HEIGHT_MASK = 0x00000002;
	
	/** 球员体重 */
	public static final int PLAYER_WEIGHT_MASK = 0x00000004;
	
	/** 球员惯用脚 */
	public static final int PLAYER_FOOT_MASK = 0x00000008;
	
	/** 球衣号码 */
	public static final int PLAYER_NUMBER_MASK = 0x00000010;
	
	/** 球员小图片 */
	public static final int PLAYER_SMALL_IMG_MASK = 0x00000020;
	
	/** 球员大图片 */
	public static final int PLAYER_BIG_IMG_MASK = 0x00000040;
	
	/** 球员国家 */
	public static final int PLAYER_NATIONALITY_MASK = 0x00000080;
	
	/** 球员first name */
	public static final int PLAYER_FIRST_NAME_MASK = 0x00000100;
	
	/** 球员last name */
	public static final int PLAYER_LAST_NAME_MASK = 0x00000200;
	
	/**
	 * 如果编辑位上有设置标记，则不允许修改
	 * @param editFlag 编辑位，从数据库中查询得来
	 * @param mask 标志
	 * @return boolean
	 */
	public static boolean isEditable(int editFlag, int mask){
		return !((editFlag & mask) == mask);
		
	}
	
	private static int setEditFlag(int editFlag, int mask){
		editFlag |= mask;
		return editFlag;
	}
	
	public static void setModelFlag(Model<?> model, String param, String newValue, int mask){
		if(StringUtils.isNotBlank(model.get(param)) && !model.get(param).equals(newValue)){
			model.set("edit_flag", FlagMask.setEditFlag(model.get("edit_flag"), mask));
		}
	}
}
