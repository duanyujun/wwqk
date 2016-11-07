package com.wwqk.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import com.wwqk.constants.CommonConstants;
import com.wwqk.model.League;
import com.wwqk.model.Player;
import com.wwqk.model.Say;
import com.wwqk.model.Team;
import com.wwqk.service.LeagueService;
import com.wwqk.service.PlayerService;
import com.wwqk.service.SayService;
import com.wwqk.service.TeamService;
import com.wwqk.utils.FileService;
import com.wwqk.utils.StringUtils;

public class AdminController extends Controller {
	
	public void index(){
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			redirect("/home");
		}else{
			redirect("/login");
		}
	}

	public void listLeague(){
		render("admin/leagueList.jsp");
	}
	
	public void leagueData(){
		
		Map<Object, Object> map = LeagueService.leagueData(this);
		renderJson(map);
	}
	
	public void editLeague(){
		String id = getPara("id");
		if(id!=null){
			League league = League.dao.findById(id);
			setAttr("league", league);
		}
		
		render("admin/leagueForm.jsp");
	}
	
	public void deleteLeague(){
		renderJson(1);
	}
	
	
	public void listTeam(){
		render("admin/teamList.jsp");
	}
	
	public void teamData(){
		Map<Object, Object> map = TeamService.teamData(this);
		renderJson(map);
	}
	
	public void editTeam(){
		String id = getPara("id");
		if(id!=null){
			Team team = Team.dao.findById(id);
			setAttr("team", team);
		}
		
		render("admin/teamForm.jsp");
	}
	
	public void saveTeam(){
		UploadFile file = null;
		try {
			file = getFile();
        }catch (Exception e){
            e.printStackTrace();
        }
		
		String id = getPara("id");
		if(id == null){
			return;
		}
		String name = getPara("name");
		String setup_time = getPara("setup_time");
		String venue_name = getPara("venue_name");
		String team_url = getPara("team_url");
		String venue_small_img_local = saveFiles(file, "venues", "300x225", id);
		
		Team team = Team.dao.findById(id);
		team.set("name", name);
		team.set("setup_time", setup_time);
		team.set("venue_name", venue_name);
		team.set("team_url", team_url);
		if(StringUtils.isNotBlank(venue_small_img_local)){
			team.set("venue_small_img_local", venue_small_img_local);
		}
		team.update();
		
		renderJson(1);
	}
	
	public void deleteTeam(){
		renderJson(1);
	}
	
	public void listPlayer(){
		render("admin/playerList.jsp");
	}
	
	public void playerData(){
		Map<Object, Object> map = PlayerService.playerData(this);
		renderJson(map);
	}
	
	public void editPlayer(){
		String id = getPara("id");
		if(id!=null){
			Player player = Player.dao.findById(id);
			setAttr("player", player);
		}
		
		render("admin/playerForm.jsp");
	}
	
	public void deletePlayer(){
		renderJson(1);
	}
	
	public void savePlayer(){
		List<UploadFile> files = new ArrayList<UploadFile>();
		try {
			files = getFiles();
        }catch (Exception e){
            e.printStackTrace();
        }
		
		String id = getPara("id");
		if(id == null){
			return;
		}
		String name = getPara("name");
		String height = getPara("height");
		String weight = getPara("weight");
		String foot = getPara("foot");
		String number = getPara("number");
		String img_small_local = "";
		String img_big_local = "";
		
		for(UploadFile file : files){
			if("file_small".equals(file.getParameterName())){
				img_small_local = saveFiles(file, "players", "50x50", id);
			}else{
				img_big_local = saveFiles(file, "players", "150x150", id);
			}
		}
		
		Player player = Player.dao.findById(id);
		player.set("name", name);
		player.set("height", height);
		player.set("weight", weight);
		player.set("foot", foot);
		player.set("number", number);
		player.set("img_small_local", img_small_local);
		player.set("img_big_local", img_big_local);
		player.update();
		
		renderJson(1);
	}
	
	/**
	 * H:\workspace\wwqk\WebRoot\assets\image\soccer\players
	 * 
	 * @param type players / venues
	 * @param size 150x150
	 * @return
	 */
	private String getPicPath(String type, String size) {
		String pathStr = getClass().getClassLoader().getResource("").getPath();

		if ("\\".equals(File.separator)) {
			pathStr = pathStr.substring(1).replaceAll("/", "\\\\");
		}
		pathStr = pathStr.substring(0, pathStr.indexOf("WEB-INF"));
		pathStr = pathStr + "assets" + File.separator + "image"
				+ File.separator + "soccer" + File.separator + type
				+ File.separator + size;
		File path = new File(pathStr);
		if(!path.exists()){
			path.mkdirs();
		}
		return pathStr;
	}
	
	private String saveFiles(UploadFile file, String type, String size, String id){
		String fileName = "";
		if(file!=null){
			String lastPrefix = file.getOriginalFileName().substring(file.getOriginalFileName().lastIndexOf("."));
			String picFilePath = getPicPath(type, size);
			fileName = picFilePath.substring(picFilePath.indexOf("assets"))+File.separator+CommonConstants.UPLOAD_FILE_FLAG+id+lastPrefix;
			File localFile = new File(picFilePath+File.separator+CommonConstants.UPLOAD_FILE_FLAG+id+lastPrefix);
			try {
				if(localFile.exists()){
					localFile.delete();
				}
				localFile.createNewFile();
				FileService fileService = new FileService();
				fileService.fileChannelCopy(file.getFile(), localFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fileName = fileName.replaceAll("\\\\", "/");
		return fileName;
	}
	
	
	
	public void listSay(){
		render("admin/sayList.jsp");
	}
	
	public void sayData(){
		Map<Object, Object> map = SayService.sayData(this);
		renderJson(map);
	}
	
	public void editSay(){
		String id = getPara("id");
		if(id!=null){
			Say say = Say.dao.findById(id);
			setAttr("say", say);
		}
		
		render("admin/sayForm.jsp");
	}
	
	public void saveSay(){
		UploadFile file = null;
		try {
			file = getFile();
        }catch (Exception e){
            e.printStackTrace();
        }
		
		String id = getPara("id");
		if(id == null){
			return;
		}
		String name = getPara("name");
		String setup_time = getPara("setup_time");
		String venue_name = getPara("venue_name");
		String team_url = getPara("team_url");
		String venue_small_img_local = saveFiles(file, "venues", "300x225", id);
		
		Team team = Team.dao.findById(id);
		team.set("name", name);
		team.set("setup_time", setup_time);
		team.set("venue_name", venue_name);
		team.set("team_url", team_url);
		if(StringUtils.isNotBlank(venue_small_img_local)){
			team.set("venue_small_img_local", venue_small_img_local);
		}
		team.update();
		
		renderJson(1);
	}
	
	public void deleteSay(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from say "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
}
