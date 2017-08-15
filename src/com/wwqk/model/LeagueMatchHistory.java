package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class LeagueMatchHistory extends Model<LeagueMatchHistory> {
	
	private static final long serialVersionUID = 1L;
	
	public static final LeagueMatchHistory dao = new LeagueMatchHistory();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
	public Page<LeagueMatchHistory> paginate(int pageNumber, int pageSize, String whereSql) {
		return paginate(pageNumber, pageSize, "select *", "from league_match_history where 1=1 " + whereSql +" order by year desc, round desc, match_date desc");
	}
}
