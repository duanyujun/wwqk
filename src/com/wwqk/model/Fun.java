package com.wwqk.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class Fun extends Model<Fun> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Fun dao = new Fun();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}
	
	public Page<Fun> paginate(int pageNumber, int pageSize, int type) {
		if(type==0){
			return paginate(pageNumber, pageSize, "select *", "from fun where image_small is not null order by create_time desc");
		}else{
			//TODO 记得加类型条件
			return paginate(pageNumber, pageSize, "select *", "from fun where type = ? order by create_time desc", type);
		}
		
	}
}
