package com.wwqk.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
	private static final BigDecimal HUNDRED = new BigDecimal(100);
	private static final Pattern VALUE_PATTERN_1 = Pattern.compile("\\d+\\.\\d+");
	private static final Pattern VALUE_PATTERN_2 = Pattern.compile("\\d+");
	
	
	
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
	
	public static final Map<String, String> MONTH_TEN_MAP = new HashMap<String, String>();
	static{
		MONTH_TEN_MAP.put("一月", "01");
		MONTH_TEN_MAP.put("二月", "02");
		MONTH_TEN_MAP.put("三月", "03");
		MONTH_TEN_MAP.put("四月", "04");
		MONTH_TEN_MAP.put("五月", "05");
		MONTH_TEN_MAP.put("六月", "06");
		MONTH_TEN_MAP.put("七月", "07");
		MONTH_TEN_MAP.put("八月", "08");
		MONTH_TEN_MAP.put("九月", "09");
		MONTH_TEN_MAP.put("十月", "10");
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
		return "0";
	}
	
	/**
	 * 得到正则匹配的结果（查找一次）
	 * @param pattern
	 * @param source
	 * @return
	 */
	public static String matcherStringAll(Pattern pattern, String source){
		if(StringUtils.isBlank(source)){
			return "";
		}
		Matcher matcher = pattern.matcher(source);
		if(matcher.find()){
			return matcher.group();
		}
		return "0";
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
	 *  二月 2016
	 * @param dateStr
	 * @return
	 * @throws ParseException 
	 */
	public static Date getCNDateMonth(String dateStr){
		if(StringUtils.isBlank(dateStr)){
			return null;
		}
		if(dateStr.contains("十二月")){
			dateStr = dateStr.replace("十二月", "12");
		}else if(dateStr.contains("十一月")){
			dateStr = dateStr.replace("十一月", "11");
		}else{
			for(Entry<String, String> entry : CommonUtils.MONTH_TEN_MAP.entrySet()){
				dateStr = dateStr.replace(entry.getKey().trim(), entry.getValue());
			}
		}
		dateStr = "01 " + dateStr;
		String[] patterns ={"dd MM yyyy"};
		Date date = null;
		try {
			date = DateTimeUtils.parseDate(dateStr, patterns);
		} catch (ParseException e) {
			System.err.println("/////////"+e.getMessage());
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
	 * @param euValue
	 * @return
	 */
	public static String getCNValue(String euValue){
		if(StringUtils.isBlank(euValue)){
			return NO_INFO;
		}
		Matcher matcher1 = VALUE_PATTERN_1.matcher(euValue);
		if(matcher1.find()){
			euValue = String.valueOf(new BigDecimal(matcher1.group()).multiply(HUNDRED))+"万欧元";
		}else{
			Matcher matcher2 = VALUE_PATTERN_2.matcher(euValue);
			if(matcher2.find()){
				String value = matcher2.group();
				if(euValue.contains("K")){
					if(value.length()>1){
						euValue = Integer.valueOf(value).intValue()/10+ "万欧元";
					}else{
						euValue = Integer.valueOf(value).intValue() * 1000 + "欧元";
					}
				}else{
					euValue = String.valueOf(new BigDecimal(value).multiply(HUNDRED))+"万欧元";
				}
			}else{
				euValue = NO_INFO;
			}
		}
		return euValue;
	}
	
	/**
	 * 得到默认0
	 * @param source
	 * @return
	 */
	public static final String getDefaultZero(String source){
		if(StringUtils.isBlank(source)){
			return "0";
		}
		return source;
	}
	
	public static final boolean clothNeedBgColor(String teamId){
		Set<String> set = new HashSet<String>();
		String[] needArrayId = {"738","967","895","1000","2015","2016","2021","13410","971"};
 		for(String id:needArrayId){
 			set.add(id);
 		}
 		if(set.contains(teamId)){
 			return true;
 		}
 		
 		return false;
	}
	
	public static final String getEnName(String sourceUrl){
		if(StringUtils.isBlank(sourceUrl)){
			return "";
		}
		sourceUrl = sourceUrl.substring(0, sourceUrl.length() - 1);
		sourceUrl = sourceUrl.substring(0, sourceUrl.lastIndexOf("/"));
		sourceUrl = sourceUrl.substring(sourceUrl.lastIndexOf("/")+1);
		return sourceUrl;
	}
	
	/**
	 * 
	 * @param rewriteId 格式：aissa-mandi-100878
	 * @return
	 */
	public static final String getRewriteId(String rewriteId){
		if(StringUtils.isBlank(rewriteId)){
			return "";
		}
		if(rewriteId.contains("-")){
			rewriteId = rewriteId.substring(rewriteId.lastIndexOf("-")+1);
		}
		
		if(!ValidateUtils.validatePlayerId(rewriteId)){
			return "";
		}else{
			return rewriteId;
		}
		
	}
	
}
