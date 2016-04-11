package com.wwqk.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wwqk.constants.InjuryTypeEnum;

/**
 * 常用Id
 * @author Administrator
 *
 */
public class CommonUtils {
	
	private static final String NO_INFO = "无信息";
	private static final String MILLION_STRING = "M";
	
	
	public static final Map<String, String> MONTH_MAP = new HashMap<String, String>();
	static{
		MONTH_MAP.put(" 一月", " 01");
		MONTH_MAP.put(" 二月", " 02");
		MONTH_MAP.put(" 三月", " 03");
		MONTH_MAP.put(" 四月", " 04");
		MONTH_MAP.put(" 五月", " 05");
		MONTH_MAP.put(" 六月", " 06");
		MONTH_MAP.put(" 七月", " 07");
		MONTH_MAP.put(" 八月", " 08");
		MONTH_MAP.put(" 九月", " 09");
		MONTH_MAP.put(" 十月", " 10");
		MONTH_MAP.put(" 十一月", " 11");
		MONTH_MAP.put(" 十二月", " 12");
	}
	
	public static String getCNInjury(String injuryStr){
		for(InjuryTypeEnum typeEnum : InjuryTypeEnum.values()){
			if(typeEnum.getKey().equals(injuryStr)){
				return typeEnum.getValue();
			}
		}
		return injuryStr;
	}

	/**
	 * 得到Id
	 * @param url /teams/england/aston-villa-football-club/665/
	 * @return
	 */
	public static String getId(String url){
		if(StringUtils.isBlank(url)){
			return null;
		}
		url = url.substring(0, url.length()-1);
		int lastSlashIdx = url.lastIndexOf("/");
		String id = url.substring(lastSlashIdx+1);
		return id;
	}
	
	/**
	 * 得到正则匹配的结果（查找一次）
	 * @param pattern
	 * @param source
	 * @return
	 */
	public static String matcherString(Pattern pattern, String source){
		if(StringUtils.isBlank(source)){
			return "";
		}
		Matcher matcher = pattern.matcher(source);
		if(matcher.find()){
			return matcher.group(1);
		}
		return "";
	}
	
	/**
	 * 转换类似dd/mm/yy  02/04/16
	 * @param dateStr
	 * @return Date
	 */
	public static Date getDateByString(String dateStr){
		if(StringUtils.isBlank(dateStr)){
			return null;
		}
		String[] dmyArray = dateStr.split("/");
		Calendar calendar = Calendar.getInstance();
		String yearStr = calendar.get(Calendar.YEAR)+"";
		String yearPrefix = yearStr.substring(0,2);
		String[] parsePatterns = {DateTimeUtils.ISO_DATE_FORMAT};
		Date date = null;
		try {
			date = DateTimeUtils.parseDate(yearPrefix+dmyArray[2]+"-"+dmyArray[1]+"-"+dmyArray[0], parsePatterns);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 21 二月 1991
	 * @param dateStr
	 * @return
	 * @throws ParseException 
	 */
	public static Date getCNDate(String dateStr){
		if(StringUtils.isBlank(dateStr)){
			return null;
		}
		for(Entry<String, String> entry : CommonUtils.MONTH_MAP.entrySet()){
			dateStr = dateStr.replace(entry.getKey(), entry.getValue());
		}
		
		String[] patterns ={"dd MM yyyy"};
		Date date = null;
		try {
			date = DateTimeUtils.parseDate(dateStr, patterns);
		} catch (ParseException e) {
			
		}
		
		return date;
	}
	
	/**
	 * 得到常用的Pattern
	 * @param patternName
	 * @return
	 */
	public static Pattern getPatternByName(String patternName){
		return Pattern.compile("<dt>"+patternName+"</dt>.*?<dd>(.*?)</dd>");
	}
	
	/**
	 * &euro; 24M -> 2400万欧元
	 * @param EUValue
	 * @return
	 */
	public static String getCNValue(String EUValue){
		if(StringUtils.isBlank(EUValue)){
			return NO_INFO;
		}
		EUValue = EUValue.replace("&euro;", "").trim();
		if(EUValue.contains(MILLION_STRING)){
			EUValue = EUValue.replace(MILLION_STRING, "00万");
		}
		EUValue = EUValue+"欧元";
		return EUValue;
	}
	
}
