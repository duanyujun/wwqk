package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class Videos extends Model<Videos> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Videos dao = new Videos();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
	public Page<Videos> paginate(int pageNumber, int pageSize, String whereSql) {
		return paginate(pageNumber, pageSize, "select * ", "from videos where 1=1 " + whereSql +" order by match_date desc");
	}
		
}
