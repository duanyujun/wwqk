package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class TaobaoAlliance extends Model<TaobaoAlliance> {
	
	private static final long serialVersionUID = 1L;
	
	public static final TaobaoAlliance dao = new TaobaoAlliance();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
	public Page<TaobaoAlliance> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select * ", "from taobao_alliance order by promotion_percent desc");
	}
		
}
