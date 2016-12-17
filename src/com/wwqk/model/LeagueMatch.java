package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class LeagueMatch extends Model<LeagueMatch> implements Comparable<LeagueMatch>{
	
	private static final long serialVersionUID = 1L;
	
	public static final LeagueMatch dao = new LeagueMatch();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}

	@Override
	public int compareTo(LeagueMatch o) {
		if(this.getDate("match_date").after(o.getDate("match_date")))
		{
			return 1;
		}else if(this.getDate("match_date").before(o.getDate("match_date"))){
			return -1;
		}
		
		return 0;
	}
	
}
