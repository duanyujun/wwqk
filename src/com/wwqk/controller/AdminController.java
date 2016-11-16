package com.wwqk.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import com.wwqk.constants.FlagMask;
import com.wwqk.model.Fun;
import com.wwqk.model.League;
import com.wwqk.model.LeagueAssists163;
import com.wwqk.model.LeagueShooter163;
import com.wwqk.model.Player;
import com.wwqk.model.Say;
import com.wwqk.model.Team;
import com.wwqk.service.Assists163Service;
import com.wwqk.service.FunService;
import com.wwqk.service.LeagueService;
import com.wwqk.service.PlayerService;
import com.wwqk.service.SayService;
import com.wwqk.service.Shooter163Service;
import com.wwqk.service.TeamService;
import com.wwqk.utils.ImageUtils;
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
		Team team = Team.dao.findById(id);
		
		String name = getPara("name");
		String setup_time = getPara("setup_time");
		String venue_name = getPara("venue_name");
		FlagMask.setModelFlag(team, "venue_name", venue_name, FlagMask.TEAM_VENUE_NAME_MASK);
		String team_url = getPara("team_url");
		String venue_small_img_local = ImageUtils.getInstance().saveFiles(file, "venues", "300x225", id, false);
		FlagMask.setModelFlag(team, "venue_small_img_local", venue_small_img_local, FlagMask.TEAM_VENUE_IMG_MASK);
		String venue_address = getPara("venue_address");
		FlagMask.setModelFlag(team, "venue_address", venue_address, FlagMask.TEAM_VENUE_CITY_MASK);
		
		team.set("name", name);
		team.set("setup_time", setup_time);
		team.set("venue_name", venue_name);
		team.set("venue_address", venue_address);
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
		String id = getPara("id");
		if(id == null){
			return;
		}
		PlayerService.updatePlayer(this);
		renderJson(1);
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
		//全部player
		List<Player> lstPlayer = Player.dao.find("select p.*, t.name team_name from player p, team t where p.team_id = t.id ");
		setAttr("lstPlayer", lstPlayer);
		render("admin/sayForm.jsp");
	}
	
	public void saveSay(){
		SayService.saveSay(this);
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
	
	public void listFun(){
		render("admin/funList.jsp");
	}
	
	public void funData(){
		Map<Object, Object> map = FunService.funData(this);
		renderJson(map);
	}
	
	public void editFun(){
		String id = getPara("id");
		if(id!=null){
			Fun fun = Fun.dao.findById(id);
			setAttr("fun", fun);
		}
		
		render("admin/funForm.jsp");
	}
	
	public void saveFun(){
		FunService.saveFun(this);
		renderJson(1);
	}
	
	public void deleteFun(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from fun "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
	
	
	public void listShooter163(){
		render("admin/shooter163List.jsp");
	}
	
	public void shooter163Data(){
		Map<Object, Object> map = Shooter163Service.shooter163Data(this);
		renderJson(map);
	}
	
	public void editShooter163(){
		String id = getPara("id");
		if(id!=null){
			LeagueShooter163 shooter163 = LeagueShooter163.dao.findById(id);
			setAttr("shooter163", shooter163);
		}
		List<Player> lstPlayer = Player.dao.find("select p.*, t.name team_name from player p, team t where p.team_id = t.id ");
		setAttr("lstPlayer", lstPlayer);
		
		render("admin/shooter163Form.jsp");
	}
	
	public void saveShooter163(){
		Shooter163Service.saveShooter163(this);
		renderJson(1);
	}
	
	public void deleteShooter163(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from league_shooter_163 "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
	public void listAssists163(){
		render("admin/assists163List.jsp");
	}
	
	public void assists163Data(){
		Map<Object, Object> map = Assists163Service.assists163Data(this);
		renderJson(map);
	}
	
	public void editAssists163(){
		String id = getPara("id");
		if(id!=null){
			LeagueAssists163 assists163 = LeagueAssists163.dao.findById(id);
			setAttr("assists163", assists163);
		}
		List<Player> lstPlayer = Player.dao.find("select p.*, t.name team_name from player p, team t where p.team_id = t.id ");
		setAttr("lstPlayer", lstPlayer);
		
		render("admin/assists163Form.jsp");
	}
	
	public void saveAssists163(){
		Assists163Service.saveAssists163(this);
		renderJson(1);
	}
	
	public void deleteAssists163(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from league_assists_163 "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
}
