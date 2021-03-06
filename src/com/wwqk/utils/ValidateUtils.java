package com.wwqk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	private static final Pattern PLAYER_ID_PATTERN = Pattern.compile("\\d+");
	
	public static final boolean validateId(String id){
		if(StringUtils.isBlank(id)){
			return false;
		}
		Matcher matcher = PLAYER_ID_PATTERN.matcher(id);
		if(matcher.find() && id.equals(matcher.group())){
			return true;
		}
		
		return false;
	}
	
}
