package com.wwqk.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import com.wwqk.model.League;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.service.LeagueService;
import com.wwqk.service.PlayerService;
import com.wwqk.service.TeamService;
import com.wwqk.utils.StringUtils;

public class AdminController extends Controller {
	
	public void index(){
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			redirect("/home");
		}else{
			redirect("/home");
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
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from league "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
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
		String id = getPara("id");
		String name = getPara("name");
		String setup_time = getPara("setup_time");
		String venue_name = getPara("venue_name");
		String team_url = getPara("team_url");
		UploadFile file = getFile(getPara("venue_img_local"), );
		
	}
	
	public void deleteTeam(){
		String ids = getPara("ids");
		
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from team "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
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
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from player "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
	/**
	 * H:\workspace\wwqk\WebRoot\assets\image\soccer\players
	 * @param type soccer / venues
	 * @param size 150x150
	 * @param id   
	 * @return
	 */
	private String getPicPath(String type, String size, String id)
	  {
	    String pathStr = getClass().getClassLoader().getResource("").getPath();

	    if ("\\".equals(File.separator)) {
	      pathStr = pathStr.substring(1).replaceAll("/", "\\\\");
	    }
	    pathStr = pathStr.substring(0, pathStr.indexOf("WEB-INF"));
	    pathStr = pathStr + "static" + File.separator + "pdf";
	    File pdfPath = new File(pathStr);
	    if (!pdfPath.exists()) {
	      pdfPath.mkdirs();
	    }
	    File pdfFile = new File(pathStr + File.separator + pdfName);
	    if (!pdfFile.exists())
	      try {
	        pdfFile.createNewFile();
	        Map fileInfo = this.uploadFileService.getFileInfo(pdfName);
	        if (fileInfo != null) {
	          byte[] info = (byte[])fileInfo.get("fileInfo");
	          FileOutputStream out = new FileOutputStream(pdfFile, false);
	          out.write(info);
	          if (out != null) {
	            out.flush();
	          }
	          out.close();
	          info = null;
	        }
	      } catch (IOException e) {
	        System.err.println(e.getMessage());
	      }
	  }
}
