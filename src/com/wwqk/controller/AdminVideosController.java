package com.wwqk.controller;

import java.util.Map;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.Videos;
import com.wwqk.service.VideosService;
import com.wwqk.utils.StringUtils;

public class AdminVideosController extends Controller {

	public void listVideos(){
		render("admin/videosList.jsp");
	}
	
	public void videosData(){
		Map<Object, Object> map = VideosService.videosData(this);
		renderJson(map);
	}
	
	public void editVideos(){
		String id = getPara("id");
		if(id!=null){
			Videos videos = Videos.dao.findById(id);
			setAttr("videos", videos);
		}
		render("admin/videosForm.jsp");
	}
	
	public void saveVideos(){
		VideosService.saveVideos(this);
		renderJson(1);
	}
	
	public void deleteVideos(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from videos "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
}
