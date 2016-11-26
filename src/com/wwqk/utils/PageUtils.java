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
			List<String> list = new ArrayList<>();
			for(int i=startPage; i<endPage+1; i++){
				list.add(String.valueOf(i));
			}
			pageUI.set("list", list);
		}

		pageUI.set("startPage", startPage);
		pageUI.set("endPage", endPage);
		
		return pageUI;
	}

}
