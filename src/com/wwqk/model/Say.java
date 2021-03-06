package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class Say extends Model<Say> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Say dao = new Say();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
	public Page<Say> paginate(int pageNumber, int pageSize, String whereSql) {
		return paginate(pageNumber, pageSize, "select *", "from say where 1=1 " + whereSql +" order by create_time desc");
	}
}
