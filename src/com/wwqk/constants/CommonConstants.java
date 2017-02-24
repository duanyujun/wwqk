package com.wwqk.constants;

import java.util.HashMap;
import java.util.Map;

public class CommonConstants {

	/** 上传图片标记  */
	public static final String UPLOAD_FILE_FLAG = "upedit-";
	
	public static final int DEFAULT_RANK_SIZE = 50;
	
	/** 小图片需替换 大小1675 */
	public static final int SMALL_IMG_LENGTH = 4500;
	
	/** 大图片需替换 大小8624 */
	public static final int BIG_IMG_LENGTH = 10000;
	
	/** 默认头像（小）路径 */
	public static final String HEAD_SMALL_PATH = "assets/pages/img/head-d-small.png";
	
	/** 默认头像（大）路径 */
	public static final String HEAD_BIG_PATH = "assets/pages/img/head-d-big.png";
	
	public static Map<String, String> DIFF_MAP = new HashMap<String, String>();
	static{
		DIFF_MAP.put("热刺", "托特纳姆热刺");
		DIFF_MAP.put("莱切斯特城", "莱斯特城");
		DIFF_MAP.put("毕尔巴鄂", "毕尔巴鄂竞技");
		DIFF_MAP.put("巴伦西亚", "瓦伦西亚");
		DIFF_MAP.put("雷加利斯", "莱加内斯");
		DIFF_MAP.put("拜仁", "拜仁慕尼黑");
		DIFF_MAP.put("莱比锡红牛", "莱比锡RB");
		DIFF_MAP.put("门兴", "门兴格拉德巴赫");
		DIFF_MAP.put("美因茨05", "美因茨");
		DIFF_MAP.put("不莱梅", "云达不莱梅");
		DIFF_MAP.put("莎索罗", "萨索洛");
	}
}
