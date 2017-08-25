package com.wwqk.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jfinal.core.Controller;
import com.wwqk.model.AllLiveMatch;

public class LiveController extends Controller {

	public void index(){
		Date nowDate = new Date();
		List<AllLiveMatch> lstResult = new ArrayList<AllLiveMatch>();
		List<AllLiveMatch> lstAllLiveMatch = AllLiveMatch.dao.find("select * from all_live_match where match_datetime > ? order by match_datetime asc", nowDate);
		Set<String> set = new HashSet<String>();
		for(AllLiveMatch match : lstAllLiveMatch){
			String dateWeek = match.getStr("match_date_week");
			dateWeek = dateWeek.replace("星期", "周");
			if(!set.contains(dateWeek)){
				set.add(dateWeek);
				AllLiveMatch group = new AllLiveMatch();
				group.set("match_date_week", dateWeek);
				lstResult.add(group);
			}
			lstResult.add(match);
		}
		setAttr("lstMatch", lstResult);
		
		render("live.jsp");
	}
	
}
