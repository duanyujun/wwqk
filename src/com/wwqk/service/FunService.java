package com.wwqk.service;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import com.wwqk.model.Article;
import com.wwqk.model.Fun;
import com.wwqk.model.Player;
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
			search =search.replaceAll("'", "").trim();
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
		String player_id = controller.getPara("player_id");
		fun.set("title", controller.getPara("title"));
		fun.set("title_en", controller.getPara("title_en"));
		fun.set("summary", controller.getPara("summary")); 
		String create_time = controller.getPara("create_time");
		String content = addClass4Img(controller.getPara("content"));
		if(StringUtils.isBlank(player_id)){
			fun.set("content", content);
		}
		fun.set("keyword", controller.getPara("keyword"));
		fun.set("source_name", controller.getPara("source_name"));
		fun.set("source_url", controller.getPara("source_url"));
		if(StringUtils.isNotBlank(player_id)){
			Player player = Player.dao.findById(player_id);
			fun.set("player_id", player_id);
			fun.set("player_name", player.get("name"));
			fun.set("player_name_en", player.get("en_url"));
			fun.set("image_small", player.get("img_small_local"));
		}
		if(StringUtils.isNotBlank(create_time)){
			fun.set("create_time", create_time);
		}
		if(StringUtils.isNotBlank(image_small)){
			fun.set("image_small", image_small);
		}
		if(StringUtils.isNotBlank(image_big)){
			fun.set("image_big", image_big);
			String imagePath = ImageUtils.getInstance().getDiskPath() + image_big;
			InputStream is = null;
			try {
				is = new FileInputStream(imagePath);
				BufferedImage bi = ImageIO.read(is);
				int width = bi.getWidth();  
			    int height = bi.getHeight();  
			    fun.set("image_big_width", width);
			    fun.set("image_big_height", height);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		save(fun);
		//将文本内容保存到article表里面
		if(fun.get("id")!=null && fun.get("player_id")!=null){
			Article article = Article.dao.findFirst("select * from article where fun_id = ? ", fun.getLong("id"));
			if(article==null){
				article = new Article();
			}
			article.set("title", controller.getPara("title"));
			article.set("content", content);
			article.set("player_id", fun.get("player_id"));
			article.set("fun_id", fun.get("id"));
			if(article.get("id")==null){
				article.save();
			}else{
				article.update();
			}
		}
	}
	
	public static void save(Fun fun){
		if(fun.get("id")==null){
			fun.save();
		}else{
			fun.update();
		}
	}
	
	private static String addClass4Img(String content){
		if(StringUtils.isBlank(content)){
			return content;
		}
		content = content.replaceAll("<img", "<img class='img-responsive' ");
		return content;
	}
	
}
