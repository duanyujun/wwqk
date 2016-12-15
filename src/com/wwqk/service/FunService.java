package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import com.wwqk.model.Fun;
import com.wwqk.model.Say;
import com.wwqk.utils.ImageUtils;
import com.wwqk.utils.StringUtils;

public class FunService {

	public static Map<Object, Object> funData(Controller controller){
		String sumSql = "select count(*) from fun where type = 1  ";
		String sql = "select * from fun where type = 1 ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			whereSql = " and (title like '%"+search+"%'"+" OR player_name like '%"+search+"%'"+" )";
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by title "+sortType;
			break;
		case 2:
			orderSql = " order by player_name "+sortType;
			break;
		case 4:
			orderSql = " order by create_time "+sortType;
			break;
		default:
			break;
		}
		
		int start = controller.getParaToInt("start");
		int length = controller.getParaToInt("length");
		if(length!=-1){
			limitSql = " limit "+start+","+length;
		}
		Long recordsTotal = Db.queryLong(sumSql+whereSql);
		List<Say> lstSay = new ArrayList<Say>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstSay = Say.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstSay.size()];
			for(int i=0; i<lstSay.size(); i++){
				Object[] obj = new Object[7];
				Say say = lstSay.get(i);
				obj[0] = say.get("id");
				obj[1] = say.get("title");
				obj[2] = say.get("type");
				obj[3] = say.get("create_time");
				String summary = say.get("summary");
				if(StringUtils.isNotBlank(summary) && summary.length()>10){
					summary = summary.substring(0,10);
				}
				obj[4] = summary;
				obj[5] = say.get("player_name");
				obj[6] = say.get("status");
				
				data[i] = obj;
			}
		}
		if(data==null){
			data = new Object[0];
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("draw", controller.getParaToInt("draw"));
		map.put("recordsTotal", recordsTotal);
		map.put("recordsFiltered", recordsTotal);
		map.put("data", data);
		
		return map;
	}
	
	public static void saveFun(Controller controller){
		List<UploadFile> files = new ArrayList<UploadFile>();
		try {
			files = controller.getFiles();
        }catch (Exception e){
            e.printStackTrace();
        }
		String image_small = "";
		String image_big = "";
		long millis = System.currentTimeMillis();
		for(UploadFile file : files){
			if("image_small".equals(file.getParameterName())){
				image_small = ImageUtils.getInstance().saveFiles(file, "fun", "220x140", millis+"", true);
			}else{
				image_big = ImageUtils.getInstance().saveFiles(file, "fun", "610x410", millis+"", true);
			}
		}
		
		String id = controller.getPara("id");
		Fun fun = null;
		if(StringUtils.isBlank(id)){
			fun = new Fun();
		}else{
			fun = Fun.dao.findById(id);
		}
		
		fun.set("title", controller.getPara("title"));
		fun.set("summary", controller.getPara("summary"));
		fun.set("content", controller.getPara("content"));
		fun.set("source_name", controller.getPara("source_name"));
		fun.set("source_url", controller.getPara("source_url"));
	
		
		if(StringUtils.isNotBlank(image_small)){
			fun.set("image_small", image_small);
		}
		if(StringUtils.isNotBlank(image_big)){
			fun.set("image_big", image_big);
		}
		
		save(fun);
	}
	
	public static void save(Fun fun){
		if(fun.get("id")==null){
			fun.save();
		}else{
			fun.update();
		}
	}
	
}
