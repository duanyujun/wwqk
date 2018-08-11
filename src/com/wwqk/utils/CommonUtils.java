package com.wwqk.utils;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.plugin.activerecord.Model;
import com.wwqk.constants.InjuryTypeEnum;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.Team;

/**
 * 常用Id
 * @author Administrator
 *
 */
public class CommonUtils {
	
	private static final String NO_INFO = "无信息";
	private static final String FREE = "免签";
	private static final String LOAN = "租借";
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
		return "";
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
		return Pattern.compile("<dt>"+patternName+"</dt>.*?<dd.*?>(.*?)</dd>");
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
	 * 返回数组:前面数量，后面是否带M
	 * @param euValue
	 * @return
	 */
	public static String[] getCNValueFromEuro(String euValue){
		String[] result = new String[2];
		result[0] = NO_INFO;
		if(StringUtils.isBlank(euValue)){
			return result;
		}
		if(euValue.contains("Free")){
			result[0] = FREE;
			return result;
		}
		if(euValue.contains("Loan")){
			result[0] = LOAN;
			return result;
		}
		euValue = euValue.replace("&euro;", "");
		euValue = euValue.replace("€", "");
		if(euValue.contains("M")){
			result[0] = StringUtils.trim(euValue.replace("M", ""));
			result[1] = "M";
		}else if(euValue.contains("K")){
			result[0] = StringUtils.trim(euValue.replace("K", ""));
			result[0] = result[0].substring(0, result[0].length()-1);
		}
		
		return result;
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
			if(rewriteId.contains("_")){
				rewriteId = rewriteId.substring(0, rewriteId.indexOf("_"));
			}
		}
		
		if(!ValidateUtils.validateId(rewriteId)){
			return "";
		}else{
			return rewriteId;
		}
		
	}
	
	/**
	 * 
	 * @param rewriteId 格式：hull-city-afc-vs-everton-football-club_2016-12-31-725vs674
	 * @return
	 */
	public static final String getRewriteMatchKey(String matchKey){
		if(StringUtils.isBlank(matchKey)){
			return "";
		}
		if(matchKey.contains("_")){
			matchKey = matchKey.substring(matchKey.lastIndexOf("_")+1);
		}
		
		return matchKey;
		
	}
	
	/**
	 * 得到各个参数
	 * @param markStr 如下面一行o、w、c
	 * @param allParamStr p-o2-w6-c7.html格式
	 * @return String
	 */
	public static String getRealParam(String mark, String allParamStr){
		String result = null;
		if(StringUtils.isBlank(allParamStr)){
			return result;
		}
		String allMark = "-"+mark;
		if(allParamStr.lastIndexOf("-")==allParamStr.indexOf(allMark)){
			result = allParamStr.substring(allParamStr.indexOf(allMark)+allMark.length());
		}else{
			Pattern pattern = Pattern.compile("\\-"+mark+"(.*?)\\-");
			Matcher matcher = pattern.matcher(allParamStr);
			if(matcher.find()){
				result = matcher.group(1);
			}
		}
		
		return result;
	}
	
	/**
	 * 是否是从联赛过来
	 * @param param
	 * @return
	 */
	public static final boolean isFromLeague(String param){
		if(StringUtils.isBlank(param)){
			return true;
		}
		param = param.substring(param.lastIndexOf("-")+1);
		if(Integer.valueOf(param).intValue()<6){
			return true;
		}
		return false;
	}
	
	private static final Pattern PAGE_PATTERN = Pattern.compile("-page-(\\d+)");
	/**
	 * 获取页码
	 * @param param
	 * @return 
	 */
	public static final int getPageNo(String param){
		if(StringUtils.isBlank(param)){
			return 1;
		}
		Matcher matcher = PAGE_PATTERN.matcher(param);
		if(matcher.find()){
			String pageNO = matcher.group(1);
			return Integer.parseInt(pageNO);
		}
		
		return 1;
	}
	
	public static final String formatDateStr(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>19){
			dateStr = dateStr.substring(0,19);
		}
		return dateStr;
	}
	
	//球队名称ID常量
	public static Map<String, String> nameIdMap = new HashMap<String, String>();
	public static Map<String, String> nameENNameMap = new HashMap<String, String>();
	public static void initNameIdMap(){
		Map<String, String> idENNameMap = new HashMap<String, String>();
		List<Team> lstTeams = Team.dao.find("select id,name,name_en from team");
		for(Team team : lstTeams){
			nameIdMap.put(team.getStr("name"), team.getStr("id"));
			nameENNameMap.put(team.getStr("name"), team.getStr("name_en"));
			idENNameMap.put(team.getStr("id"), team.getStr("name_en"));
		}
		String[] specialNameArray = {
				"西布罗姆","西布罗姆维奇","西布朗维奇", "西布朗",  "不莱梅", "云达不莱梅", "纽卡斯尔联", "纽卡斯尔", "托特纳姆热刺", "热刺",
				"塞尔塔","维戈塞尔塔","莱比锡RB","莱比锡","门兴格拉德巴赫","门兴","斯帕尔","斯帕",  "RB莱比锡", "曼彻斯特城",
				"曼彻斯特联","莱切斯特","莱切斯特城","莱斯特城",  "毕尔巴鄂","毕尔巴鄂竞技","巴伦西亚","瓦伦西亚","雷加利斯","莱加内斯","拜仁","拜仁慕尼黑",
				"莱比锡红牛","美因茨05","美因茨","莎索罗","萨索洛","赫塔费","巴黎圣日尔曼","皇马", "巴萨","马竞","贝蒂斯","拉科",
				"比利亚雷","拉帕马斯","沃夫斯堡","不来梅","莱红牛",  "桑普","巴黎圣曼","维拉利尔","沙尔克","班尼云度",
				"士柏","西汉姆","南安普顿","南安普敦","哈德斯","汉诺威","布赖顿","哈德斯", "狼队", "巴拉多利德", "杜塞尔多夫", "弗罗西诺内"};
		String[] specialIdArray =  {
				"678","678",	"678",  	  "678",  "960","960",       "664","664",         "675","675",
				"2033","2033",  "13410","13410", "971","971",      "1287","1287", "13410",  "676",
				"662",    "682","682","682",           "2019", "2019",    "2015", "2015",  "2053", "2053",  "961","961",
				"13410",  "977",   "977", "5681","5681","2039","886",    "2016","2017","2020","2025","2018",
				"2023","2055",   "968",  "960", "13410","1247","886",   "2023", "966",           "1302",
				"1287","684",  "670",   "670",    "726",  "972",  "703",  "726", "680", "2031", "1029", "2981"};
		for(int i=0; i<specialNameArray.length; i++){
			nameIdMap.put(specialNameArray[i], specialIdArray[i]);
			nameENNameMap.put(specialNameArray[i], idENNameMap.get(specialIdArray[i]));
		}
	}
	
	//联赛名称map
	public static Map<String, String> leagueNameIdMap = new HashMap<String, String>();
	public static void initLeagueNameMap(){
		leagueNameIdMap.put("亚洲联赛冠军杯", "亚冠");
		leagueNameIdMap.put("欧洲联赛冠军杯", "欧冠");
		leagueNameIdMap.put("中国超级联赛", "中超");
		leagueNameIdMap.put("中国甲级联赛", "中甲");
		leagueNameIdMap.put("中国乙级联赛", "中乙");
		leagueNameIdMap.put("俄罗斯超级联赛", "俄超");
		leagueNameIdMap.put("俄罗斯甲组联赛", "俄甲");
		leagueNameIdMap.put("巴西甲组联赛", "巴甲");
		leagueNameIdMap.put("德国甲级联赛", "德甲");
		leagueNameIdMap.put("德国乙级联赛", "德乙");
		leagueNameIdMap.put("德国丙级联赛", "德丙");
		leagueNameIdMap.put("德国足协杯", "德国杯");
		leagueNameIdMap.put("德国超级杯", "德超杯");
		leagueNameIdMap.put("意大利甲级联赛", "意甲");
		leagueNameIdMap.put("意大利乙级联赛", "意乙");
		leagueNameIdMap.put("意大利超级杯", "意超杯");
		leagueNameIdMap.put("挪威超级联赛", "挪超");
		leagueNameIdMap.put("日本职业联赛", "J联赛");
		leagueNameIdMap.put("日本乙级联赛", "J2联赛");
		leagueNameIdMap.put("智利甲组联赛", "智利甲");
		leagueNameIdMap.put("智利乙组联赛", "智利乙");
		leagueNameIdMap.put("法国甲级联赛", "法甲");
		leagueNameIdMap.put("法国乙级联赛", "法乙");
		leagueNameIdMap.put("法国丙级联赛", "法丙");
		leagueNameIdMap.put("法国联赛杯", "法联杯");
		leagueNameIdMap.put("法国超级杯", "法超杯");
		leagueNameIdMap.put("澳大利亚甲级联赛", "澳甲");
		leagueNameIdMap.put("瑞典超级联赛", "瑞典超");
		leagueNameIdMap.put("瑞典甲组联赛", "瑞典甲");
		leagueNameIdMap.put("美国职业大联盟联赛", "美职联");
		leagueNameIdMap.put("苏格兰超级联赛", "苏超");
		leagueNameIdMap.put("苏格兰冠军联赛", "苏冠");
		leagueNameIdMap.put("英格兰超级联赛", "英超");
		leagueNameIdMap.put("英格兰冠军联赛", "英冠");
		leagueNameIdMap.put("英格兰甲级联赛", "英甲");
		leagueNameIdMap.put("英格兰乙级联赛", "英乙");
		leagueNameIdMap.put("英格兰足总杯", "英足总");
		leagueNameIdMap.put("英格兰联赛杯", "英联杯");
		leagueNameIdMap.put("荷兰甲级联赛", "荷甲");
		leagueNameIdMap.put("荷兰乙级联赛", "荷乙");
		leagueNameIdMap.put("荷兰超级杯", "荷超杯");
		leagueNameIdMap.put("西班牙甲级联赛", "西甲");
		leagueNameIdMap.put("西班牙乙级联赛", "西乙");
		leagueNameIdMap.put("西班牙国王杯", "西班牙杯");
		leagueNameIdMap.put("西班牙超级杯", "西超杯");
		leagueNameIdMap.put("阿根廷超级联赛", "阿甲");
		leagueNameIdMap.put("韩国职业联赛", "K联赛");
		leagueNameIdMap.put("墨西哥联赛", "墨联");
		leagueNameIdMap.put("罗马尼亚甲组联赛", "罗甲");
		leagueNameIdMap.put("罗马尼亚乙组联赛", "罗乙");
		leagueNameIdMap.put("土耳其超级联赛", "土超");
		leagueNameIdMap.put("土耳其甲级联赛", "土甲");
		leagueNameIdMap.put("乌克兰超级联赛", "乌克超");
		leagueNameIdMap.put("乌克兰甲级联赛", "乌克甲");
		leagueNameIdMap.put("芬兰超级联赛", "芬超");
		leagueNameIdMap.put("芬兰甲组联赛", "芬甲");
		leagueNameIdMap.put("挪威甲级联赛", "挪甲");
		leagueNameIdMap.put("波兰甲级联赛", "波甲");
		leagueNameIdMap.put("波兰乙级联赛", "波乙");
		leagueNameIdMap.put("葡萄牙超级联赛", "葡超");
		leagueNameIdMap.put("葡萄牙甲级联赛", "葡甲");
		leagueNameIdMap.put("克罗地亚甲组联赛", "克罗甲");
		leagueNameIdMap.put("丹麦超级联赛", "丹超");
		leagueNameIdMap.put("丹麦甲组联赛", "丹甲");
		
		leagueNameIdMap.put("比利时甲级联赛", "比甲");
		leagueNameIdMap.put("比利时乙级联赛", "比乙");
		leagueNameIdMap.put("以色列超级联赛", "以超");
		leagueNameIdMap.put("以色列甲级联赛", "以甲");
		leagueNameIdMap.put("威尔士超级联赛", "威超");
		leagueNameIdMap.put("乌拉圭甲组联赛", "乌拉甲");
		leagueNameIdMap.put("秘鲁甲组联赛", "秘鲁甲");
		leagueNameIdMap.put("哥伦比亚甲组联赛", "哥伦甲");
		leagueNameIdMap.put("哥伦比亚甲组联赛", "哥伦乙");
		leagueNameIdMap.put("玻利维亚甲组联赛", "玻利甲");
		leagueNameIdMap.put("巴拉圭甲级联赛", "巴拉甲");
		leagueNameIdMap.put("厄瓜多尔甲级联赛", "厄瓜甲");
		leagueNameIdMap.put("沙特阿拉伯职业联赛", "沙特联");
		leagueNameIdMap.put("沙特阿拉伯甲级联赛", "沙特甲");
		leagueNameIdMap.put("伊朗超级联赛", "伊朗超");
		leagueNameIdMap.put("泰国超级联赛", "泰超");
		leagueNameIdMap.put("乌兹别克斯坦超级联赛", "乌兹超");
	}
	
	public static Map<String, Object> getHALiveMatchMap(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, AllLiveMatch> homeMap = new HashMap<String, AllLiveMatch>();
		Map<String, AllLiveMatch> awayMap = new HashMap<String, AllLiveMatch>();
		Date nowDate = DateTimeUtils.addHours(new Date(), -2);
		List<AllLiveMatch> lstAllLiveMatch = AllLiveMatch.dao.find("select * from all_live_match where match_datetime > ? order by match_datetime asc", nowDate);
		for(AllLiveMatch liveMatch : lstAllLiveMatch){
			homeMap.put(DateTimeUtils.formatDate(liveMatch.getDate("match_datetime"))+liveMatch.getStr("home_team_name"), liveMatch);
			awayMap.put(DateTimeUtils.formatDate(liveMatch.getDate("match_datetime"))+liveMatch.getStr("away_team_name"), liveMatch);
		}
		resultMap.put("home", homeMap);
		resultMap.put("away", awayMap);
		return resultMap;
	}
	
	private static String toUppercase4FirstLetter(String... words){
        StringBuffer buffer = new StringBuffer();
        if(words != null && words.length > 0){
            for(int i=0;i<words.length;i++){
                String word = words[i];
                String firstLetter = word.substring(0, 1);
                String others = word.substring(1);
                String upperLetter = null;
                if(i != 0){
                    upperLetter = firstLetter.toUpperCase();
                } else {
                    upperLetter = firstLetter;
                }
                buffer.append(upperLetter).append(others);
            }
            return buffer.toString();
        }
        return "";
    }
	
	/**
	 * 表名转驼峰命名
	 * @param tableName
	 * @return
	 */
	public static String field2CamelString(String tableName){
		if(StringUtils.isBlank(tableName)){
			return "";
		}
		String[] words = tableName.split("_");
		String result = toUppercase4FirstLetter(words);
		return result;
	}
	
	public static String firstLetter2UpperCase(String upperLetter){
		if(StringUtils.isBlank(upperLetter)){
			return "";
		}
		String firstLetter = upperLetter.substring(0, 1);
		return firstLetter.toUpperCase()+upperLetter.substring(1);
	}
	
	public static String getTemplatePath(String templateName){
		StringBuilder sb = new StringBuilder(FileUtils.getWebDiskPath());
		sb.append(File.separator).append("common").append(File.separator).append("template")
		.append(File.separator).append(templateName);
		
		return sb.toString();
	}
	
	public static String getTargetPath(String packageName, String moduleName, String targetName){
		StringBuilder sb = new StringBuilder(System.getProperty("user.dir"));
		sb.append(File.separator).append("src").append(File.separator).append(packageName.replaceAll("\\.", "/"))
		.append(File.separator).append(moduleName).append(File.separator).append(targetName);
		
		return sb.toString();
	}
	
	public static String getAdminPath(){
		StringBuilder sb = new StringBuilder();
		sb.append(FileUtils.getWebDiskPath()).append(File.separator).append("web").append(File.separator).append("admin").append(File.separator);
		return sb.toString();
	}
	
	public static void setNullValue(Model<?> model, String field, String value){
		if(StringUtils.isNotBlank(value)){
			model.set(field, value);
		}
	}
}
