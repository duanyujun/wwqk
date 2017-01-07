package com.wwqk.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.CharUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * @author ThinkGem
 * @version 2013-05-22
 */
public abstract class StringUtil3 extends org.apache.commons.lang3.StringUtils {
	/** LT */
	public static final String LT = "!lt;";
	/** GT */
	public static final String GT = "!gt;";
	/** AND */
	public static final String AND = "!amp;";
	/** QUOT */
	public static final String QUOT = "!quot;";
	
	/**
	 * 不能创建对象
	 */
	private StringUtil3(){}
	
	/**
	 * 替换所有的标点符号
	 * @return
	 */
	public static String replaceP(String text, String replacement){
		if (StringUtil3.isNotBlank(text) && replacement != null) {
		    return text.replaceAll("\\pP", replacement);
		}
		return text;
	}
	
	/**
	 * 替换所有的空白
	 * @return
	 */
	public static String replaceZ(String text, String replacement){
		if (StringUtil3.isNotBlank(text) && replacement != null) {
		    return text.replaceAll("\\pZ", replacement);
		}
		return text;
	}
	
	/**
	 * 替换所有的空白
	 * @return
	 */
	public static String replaceAllSpace(String text){
		if (StringUtil3.isNotBlank(text)) {
		    return text.replaceAll("\\s", "");
		}
		return text;
	}
	
	/**
	 * 去掉BOM字符
	 * @return
	 */
	public static String removeBom(String text){
		if (StringUtil3.isNotBlank(text)) {
		    return StringUtil3.trim(StringUtil3.replaceChars(text, (char)65279, ' '));
		}
		return text;
	}
	
	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) { return ""; }
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : str.toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 缩略字符串（不区分中英文字符） --- 先去掉html的格式
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbrHtml(String str, int length) {
		String html = str;
	    if (StringUtil3.isNotBlank(html)) {
	    	html = StringUtil3.replaceHtml(html);
			html = StringUtil3.remove(html, "&nbsp;");
			html = StringUtil3.replaceAllSpace(html);
	    }
		return abbr(html, length);
	}
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	/**
	 * StringBuffer .. append
	 */
	public static StringBuilder appendTo(Object... parts){
	    return appendTo(new StringBuilder(), Arrays.asList(parts));
	}
	
	/**
	 * StringBuffer .. append
	 */
	public static StringBuilder appendTo(StringBuilder appendable, Iterable<?> parts){
		return appendTo(appendable, parts.iterator());
	}
	
	/**
	 * StringBuffer .. append
	 */
	public static StringBuilder appendTo(StringBuilder appendable, Object... parts){
	    return appendTo(appendable, Arrays.asList(parts));
	}
	
	
	/**
	 * StringBuffer .. append
	 */
    public static StringBuilder appendTo(StringBuilder appendable, Iterator<?> parts){
		if (parts.hasNext()) {
			appendable.append(toString(parts.next()));
			while (parts.hasNext()) {
				appendable.append(toString(parts.next()));
			}
		}
		return appendable;
	}
    
    public static CharSequence toString(Object part) {
		return (part instanceof CharSequence) ? (CharSequence) part : part.toString();
	}
    
	/**
	 * 信息格式化
	 * @param template
	 * @param args
	 * @return
	 */
	public static String format(String template, Object... args) {
		template = String.valueOf(template); // null -> "null"
		StringBuilder builder = new StringBuilder(template.length() + 16* args.length);
		int templateStart = 0;
		int i = 0;
		while (i < args.length) {
			int placeholderStart = template.indexOf("%s", templateStart);
			if (placeholderStart == -1) {
				break;
			}
			builder.append(template.substring(templateStart, placeholderStart));
			builder.append(args[i++]);
			templateStart = placeholderStart + 2;
		}
		builder.append(template.substring(templateStart));

		if (i < args.length) {
			builder.append(" [");
			builder.append(args[i++]);
			while (i < args.length) {
				builder.append(", ");
				builder.append(args[i++]);
			}
			builder.append(']');
		}
		return builder.toString();
	}
	
	/**
	 * 在固定位置插入指定字符
	 * @param postion
	 * @param insert
	 * @return
	 */
	public static String insert(String src, int postion, String insert) {
		if (StringUtil3.isBlank(src) || StringUtil3.isEmpty(insert) || postion < 0){
			return src;
		}
		if (postion > StringUtil3.length(src)) {
			postion = StringUtil3.length(src);
		}
		return new StringBuilder(src).insert(postion, insert).toString();
	}
	
	/**
	 * 在每固定位置插入指定字符
	 * @param postion
	 * @param insert
	 * @return
	 */
	public static String insertEach(String src, int postion, String insert) {
		if (StringUtil3.isBlank(src) || StringUtil3.isEmpty(insert)){
			return src;
		}
		if (postion > StringUtil3.length(src)) {
			postion = StringUtil3.length(src);
		}
		if (postion == 0) {
			return insert(src,postion,insert);
		}
		int each = StringUtil3.length(src) / postion;
		StringBuffer sbSrc = new StringBuffer(src);
		for(int i =1, j = each; i<=j; i++ ){
			sbSrc.insert(i*postion+(i-1), insert);
		}
		return sbSrc.toString();
	}
	
	//字符串转义
	public static boolean containsKeyString(String str) {
		if (StringUtil3.isBlank(str)) {
			return false;
		}
		if (str.contains("'") || str.contains("\"") || str.contains("\r")
				|| str.contains("\n") || str.contains("\t")
				|| str.contains("\b") || str.contains("\f")) {
			return true;
		}
		return false;
	}
	
	// 将""和'转义
	public static String replaceKeyString(String str) {
		if (containsKeyString(str)) {
			return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r",
					"\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
					"\b", "\\b").replace("\f", "\\f");
		} else {
			return str;
		}
	}
		
	//单引号转化成双引号
	public static String replaceString(String str) {
		if (containsKeyString(str)) {
			return str.replace("'", "\"").replace("\"", "\\\"").replace("\r",
					"\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
					"\b", "\\b").replace("\f", "\\f");
		} else {
			return str;
		}
	}
	
	/**
	 * 首字母转小写 
	 * @param s
	 * @return
	 */
	public static String lowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 首字母转大写 
	 * @param s
	 * @return
	 */
	public static String upperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	/**
	 * 约定大于配置：
	 * userName --> USER_NAME
	 * @param property
	 * @return
	 */
	public static String convertProperty2Column(String property) {
		StringBuilder column = new StringBuilder();
		for(int i=0;i<property.length();i++){
			char c = property.charAt(i);
			if(Character.isUpperCase(c)){
				column.append("_");
			}
			column.append(Character.toUpperCase(c));
		}
		return StringUtil3.upperCase(column.toString());
	}
	
	/**
	 * 删除： utf-8 无法显示的字符(有问题)，直接将数据库的相关字段改为
	 * NAME VARCHAR(100) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_GENERAL_CI DEFAULT NULL COMMENT '昵称';
	 * 貌似不能解决所有问题，很多地方需要改，还不如把不能插入的字符过滤掉
	 * @param src
	 * @param replace
	 * @return
	 */
	public static String mb4Replace(String src, String replace) {
		replace = replace==null?"":replace;
		if (StringUtil3.isNotBlank(src)) {
			return src.replaceAll("[\\x{10000}-\\x{10FFFF}]", replace);
		}
		return src;
	}
	
	/**
	 * 转义数据库的特殊字符 --- 数据库的转义
	 * @param value
	 * @return
	 */
	public static Object escapeDb(String value) {
		if (value != null && value instanceof String
				&& StringUtil3.containsAny((String)value, '\\', '\'')) {
			return StringUtil3.replaceEach(value, new String[]{"\\", "\'"}, new String[]{"\\\\\\\\", "\\'"});
		}
		return value;
	}
	
	/**
	 * 不为空
	 * @param str
	 * @param defaultStr
	 * @return
	 */
    public static String defaultString(String str, String defaultStr) {
        return StringUtil3.isBlank(str) ? defaultStr : str;
    }
    
    

	
    /**
     * 136****9215
     * @param mobiles
     * @return
     */
	public static String hideTelphone(String tel){
		String strResult = tel;
		if(!isEmpty(tel)&&checkMobileNumber(tel)){
			strResult=tel.substring(0,3)+"****"+tel.substring(7);
		}			
		return strResult;
	}
	/**
	 * 得到各个参数
	 * @param markStr 如下面一行o、w、c
	 * @param allParamStr p-o2-w6-c7.html格式
	 * @return String
	 */
	public static String getRealParam(String mark, String allParamStr){
		String result = null;
		if(isBlank(allParamStr)){
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
	
	
	private static final Pattern IMG_PATTERN = Pattern.compile("<img(.+?)src=\"(.+?)\"");
	/**
	 * 返回内容中图片所在的位置
	 * @param lstAboutUs
	 * @return String
	 */
	public  static String findImage(String content,String deflaut){
		if(isBlank(content)) return deflaut; 
	    String strResult=null;
		Matcher matcher = IMG_PATTERN.matcher(content);
		if(matcher.find()){
			strResult=matcher.group(2);
		}else{
			strResult=deflaut;
		}
		return strResult;
	}
	
	/**
	 * 返回内容中图片所在的位置
	 * @param content
	 * @return String
	 */
	public static String findSummary(String content) {
		if (isBlank(content))
			return null;
		String strResult = null;
		try {
			Document htmlDoc = Jsoup.parse(content);
			Elements elemetnts = htmlDoc.select("p");
			for (Element e : elemetnts) {
				strResult = e.text();
				if (isNotBlank(strResult)) {
					break;
				}
			}
		} catch (Exception e) {

		}

		return strResult;
	}
	
	
	public static String replacePage(String param,String presuff){		
		if (isBlank(param)){
			return "";
		}		
		return param.replaceFirst(presuff+"(\\w*)-?", "-");				
	}
	

	

	/**
	 * 对象属性转换为字段 例如：userName to USER_NAME
	 * 
	 * @param property
	 *            字段名
	 * @return 数据库字段
	 */
	public static String propertyToField(String property) {

		if (null == property) {
			return "";
		}

		char[] chars = property.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			if (CharUtils.isAsciiAlphaUpper(c)) {
				sb.append("_" + lowerCase(CharUtils.toString(c)));
			} else {
				sb.append(c);
			}
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 字段转换成对象属性 例如：USER_NAME to userName
	 * 
	 * @param field
	 *            -数据库字段
	 * @return -属性名
	 */
	public static String fieldToProperty(String field) {
		if (null == field) {
			return "";
		}
		char[] chars = field.toLowerCase().toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '_') {
				int j = i + 1;
				if (j < chars.length) {
					sb.append(upperCase(CharUtils.toString(chars[j])));
					i++;
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}


	/**
	 * <p>
	 * Title: transform2Html
	 * </p>
	 * <p>
	 * Description:将转义后的html还原
	 * </p>
	 * 
	 * @param str
	 *            待转义的字符串
	 * @return 转义完成的字符串
	 */
	public static String transform2Html(String str) {
		str = str.replaceAll(LT, "<");
		str = str.replaceAll(GT, ">");
		str = str.replaceAll(AND, ">");
		str = str.replaceAll(QUOT, "\"");
		return str;
	}
	
	/** 
	 * z
	 * @param str
	 * @return
	 */
	public static char[] StringTochar(String str){		  
		  if(isEmpty(str)){
			  return null;
		  }
		  
		 return   str.toCharArray();
	  }


	/**
	 * 
	 * <p>
	 * Title: toUtf8String
	 * </p>
	 * <p>
	 * Description: 将字符串转为UTF-8编码
	 * </p>
	 * 
	 * @param s
	 *            字符串
	 * @return utf-8字符串
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 * 
	 * @param s
	 *            原字符串
	 * @return 去0后字符串
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			// 去掉多余的0
			s = s.replaceAll("0+?$", "");
			// 如最后一位是.则去掉
			s = s.replaceAll("[.]$", "");
		}
		return s;
	}

	/**
	 * 替换文本中的标签
	 * 
	 * @param s
	 *            原字符
	 * @return 替换后的字符串
	 */
	public static String replaceTextTags(String s) {
		if (isBlank(s)) {
			return "";
		}
		return s.replaceAll("<[^>]+>", "").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replace("&nbsp;", " ");
	}
	
	/**
     * 验证手机号码
     * @param mobiles
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber){
        boolean flag = false;
        try{
                Pattern regex = Pattern.compile("^((13[0-9]|15[0-9]|18[0-9]|14[57]|17[0-9])\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
                Matcher matcher = regex.matcher(mobileNumber);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
}