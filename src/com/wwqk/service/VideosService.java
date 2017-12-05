package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.Videos;
import com.wwqk.utils.ImageUtils;
import com.wwqk.utils.StringUtils;
import com.jfinal.upload.UploadFile;
import java.util.UUID;

public class VideosService {

	public static Map<Object, Object> videosData(Controller controller){
		String sumSql = "select count(*) from videos where 1 = 1  ";
		String sql = "select * from videos where 1 = 1 ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search =search.replaceAll("'", "").trim();
			whereSql = " and (home_team like '%"+search+"%'" +" OR away_team like '%"+search+"%'" +" OR match_title like '%"+search+"%'" +" OR keywords like '%"+search+"%')"; 
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
			case 1:
              orderSql = " order by league_id "+sortType;
              break;case 2:
              orderSql = " order by match_date "+sortType;
              break;case 3:
              orderSql = " order by home_team "+sortType;
              break;case 4:
              orderSql = " order by away_team "+sortType;
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
		List<Videos> lstVideos = new ArrayList<Videos>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstVideos = Videos.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstVideos.size()];
			for(int i=0; i<lstVideos.size(); i++){
				Object[] obj = new Object[8];
				Videos videos = lstVideos.get(i);
				obj[0] = videos.get("id");
				
				obj[1] = videos.get("league_id");
				obj[2] = videos.get("match_date");
				obj[3] = videos.get("home_team");
				obj[4] = videos.get("away_team");
				obj[5] = videos.get("match_title");
				obj[6] = videos.get("match_history_id");
				obj[7] = videos.get("keywords");

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
	
	public static void saveVideos(Controller controller){
	
		String id = controller.getPara("id");
		Videos videos = null;
		if(StringUtils.isBlank(id)){
			videos = new Videos();
		}else{
			videos = Videos.dao.findById(id);
		}
		
		videos.set("league_id", controller.getPara("league_id"));
        videos.set("match_date", controller.getPara("match_date"));
        videos.set("home_team", controller.getPara("home_team"));
        videos.set("away_team", controller.getPara("away_team"));
        videos.set("match_title", controller.getPara("match_title"));
        videos.set("source_url", controller.getPara("source_url"));
        videos.set("from_site", controller.getPara("from_site"));
        videos.set("match_history_id", controller.getPara("match_history_id"));
        videos.set("keywords", controller.getPara("keywords"));
        videos.set("description", controller.getPara("description"));

		save(videos);
	}
	
	public static void save(Videos videos){
		if(videos.get("id")==null){
			videos.save();
		}else{
			videos.update();
		}
	}
	
}
