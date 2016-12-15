package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import com.wwqk.model.Fun;
import com.wwqk.model.Player;
import com.wwqk.model.Say;
import com.wwqk.utils.ImageUtils;
import com.wwqk.utils.StringUtils;

public class SayService {

	public static Map<Object, Object> sayData(Controller controller){
		String sumSql = "select count(*) from say ";
		String sql = "select * from say ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			whereSql = " where (player_id like '%"+search+"%'"+" OR player_name like '%"+search+"%'"+" OR content like '%"+search+"%' )";
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by player_id "+sortType;
			break;
		case 2:
			orderSql = " order by player_name "+sortType;
			break;
		case 3:
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
				Object[] obj = new Object[5];
				Say say = lstSay.get(i);
				obj[0] = say.get("id");
				obj[1] = say.get("player_id");
				obj[2] = say.get("player_name");
				obj[3] = say.get("create_time");
				String content = say.get("content");
				if(StringUtils.isNotBlank(content) && content.length()>10){
					content = content.substring(0,10);
				}
				obj[4] = content;
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
	
	public static void saveSay(Controller controller){
		List<UploadFile> files = new ArrayList<UploadFile>();
		try {
			files = controller.getFiles();
        }catch (Exception e){
            e.printStackTrace();
        }
		
		String player_id = controller.getPara("player_id");
		if(StringUtils.isBlank(player_id)){
			return;
		}
		Player player = Player.dao.findById(player_id);
		String content = controller.getPara("content");
		String create_time = controller.getPara("create_time");
		String image_small = "";
		String image_big = "";
		long millis = System.currentTimeMillis();
		for(UploadFile file : files){
			if("image_small".equals(file.getParameterName())){
				image_small = ImageUtils.getInstance().saveFiles(file, "say", "220x140", player_id+"-"+millis, true);
			}else{
				image_big = ImageUtils.getInstance().saveFiles(file, "say", "610x410", player_id+"-"+millis, true);
			}
		}
		
		String id = controller.getPara("id");
		Say say = null;
		if(StringUtils.isBlank(id)){
			say = new Say();
		}else{
			say = Say.dao.findById(id);
		}
		
		say.set("player_id", player_id);
		say.set("player_name", player.get("name"));
		say.set("player_img_local", player.get("img_small_local"));
		say.set("content", content);
		if(StringUtils.isNotBlank(image_small)){
			say.set("image_small", image_small);
		}
		if(StringUtils.isNotBlank(image_big)){
			say.set("image_big", image_big);
		}
		
		if(StringUtils.isNotBlank(create_time)){
			say.set("create_time", create_time);
		}
		
		if(StringUtils.isNotBlank(image_small)){
			say.set("image_small", image_small);
		}
		if(StringUtils.isNotBlank(image_big)){
			say.set("image_big", image_big);
		}
		
		String oldSayId = null;
		if(say.get("id")!=null){
			oldSayId = say.get("id").toString();
		}
		save(say);
		
		Fun fun = null;
		if(StringUtils.isNotBlank(oldSayId)){
			fun = Fun.dao.findFirst("select * from fun where source_id = ? ", oldSayId);
		}else{
			fun = new Fun();
		}
		
		String contentOld = content;
		if(StringUtils.isNotBlank(content) && content.length()>15){
			content = content.substring(0, 15);
		}
		fun.set("title", content);
		fun.set("summary", contentOld);
		fun.set("type", 2);
		fun.set("source_id", say.get("id"));
		if(StringUtils.isNotBlank(image_small)){
			fun.set("image_small", image_small);
		}
		if(StringUtils.isNotBlank(image_big)){
			fun.set("image_big", image_big);
		}
		fun.set("player_id",player_id);
		fun.set("player_name",player.get("name"));
		fun.set("player_image",player.get("img_small_local"));
		if(StringUtils.isNotBlank(create_time)){
			fun.set("create_time", create_time);
		}
		
		
		FunService.save(fun);
	}
	
	
	public static void save(Say say){
		if(say.get("id")==null){
			say.save();
		}else{
			say.update();
		}
	}
}
