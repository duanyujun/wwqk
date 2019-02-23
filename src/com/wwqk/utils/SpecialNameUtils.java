package com.wwqk.utils;

import java.util.HashMap;
import java.util.Map;

public class SpecialNameUtils {

	private static Map<String, String> map = new HashMap<>();
	static{
		map.put("曼彻斯特城", "曼城");
		map.put("曼彻斯特联", "曼联");
		map.put("门兴格拉德巴赫", "门兴");
		map.put("拜仁慕尼黑", "拜仁");
	}
	
	public static String getStdName(String teamName){
		if(map.get(teamName)!=null){
			return map.get(teamName);
		}
		
		return teamName;
	}
	
}
