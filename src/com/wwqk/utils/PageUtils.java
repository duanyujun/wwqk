package com.wwqk.utils;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.wwqk.model.PageUI;

public class PageUtils {

	public static PageUI calcStartEnd(Page<?> sayPage){
		PageUI pageUI = new PageUI(); 
		int currentPage = sayPage.getPageNumber();
		int totalPage = sayPage.getTotalPage();
		int startPage = currentPage - 4;
		if(startPage < 1){
			startPage = 1;
		}

		int endPage = currentPage + 4;
		if(endPage > totalPage){
			endPage = totalPage;
		}
		
		if(currentPage <=8){
			startPage = 1;
		}
		
		if(totalPage - currentPage < 8){
			endPage = totalPage;
		}
		
		if(startPage != endPage){
			List<String> list = new ArrayList<String>();
			for(int i=startPage; i<endPage+1; i++){
				list.add(String.valueOf(i));
			}
			pageUI.set("list", list);
		}

		pageUI.set("startPage", startPage);
		pageUI.set("endPage", endPage);
		
		return pageUI;
	}
	
	/**
	 * 获取所有分页
	 * @param url
	 * @param page
	 * @param align：pull-left, pull-right, ""(center)
	 * @return
	 */
	public static String getPageContent(String url, Page<?> page, String align){
		int currentPage = page.getPageNumber();
		int totalPage = page.getTotalPage();
		int startPage = currentPage - 4;
		if(startPage < 1){
			startPage = 1;
		}

		int endPage = currentPage + 4;
		if(endPage > totalPage){
			endPage = totalPage;
		}
		
		if(currentPage <=8){
			startPage = 1;
		}
		
		if(totalPage - currentPage < 8){
			endPage = totalPage;
		}
		
		List<Integer> list = new ArrayList<Integer>();
		if(startPage != endPage){
			for(int i=startPage; i<endPage+1; i++){
				list.add(i);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"scott ").append(align).append("\">");
		sb.append("	<a href=\"").append(url).append("-page-1.html\" title=\"首页\"> &lt;&lt; </a>");
		if(currentPage==1){
			sb.append("		<span class=\"disabled\"> &lt; </span>");
		}
		if(currentPage!=1){
			sb.append("		<a href=\"").append(url).append("-page-").append(currentPage-1).append(".html\" > &lt; </a>");
		}
		if(currentPage>8){
			sb.append("		<a href=\"").append(url).append("-page-1.html\">1</a>");
			sb.append("		<a href=\"").append(url).append("-page-2.html\">2</a>");
			sb.append("		...");
		}
		for(int pageNo:list){
			if(currentPage==pageNo){
				sb.append(" <span class=\"current\">").append(pageNo).append("</span>");
			}else{
				sb.append(" <a href=\"").append(url).append("-page-").append(pageNo).append(".html\">").append(pageNo).append("</a>");
			}
		}
		
		if(totalPage-currentPage>=8){
			sb.append("...");
			sb.append("		<a href=\"").append(url).append("-page-").append(totalPage-1).append(".html\">").append(totalPage-1).append("</a>");
			sb.append("		<a href=\"").append(url).append("-page-").append(totalPage).append(".html\">").append(totalPage).append("</a>");
		}
		if(currentPage == totalPage){
			sb.append("		<span class=\"disabled\"> &gt; </span>");
		}else{
			sb.append("		<a href=\"").append(url).append("-page-").append(currentPage+1).append(".html\"> &gt; </a>");
		}
		sb.append("	<a href=\"").append(url).append("-page-").append(totalPage).append(".html\" title=\"尾页\"> &gt;&gt; </a>");
		sb.append("</div>");
		
		return sb.toString();
	}

}
