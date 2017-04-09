package com.wwqk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import com.wwqk.constants.FlagMask;
import com.wwqk.model.LeagueAssists;
import com.wwqk.model.LeagueAssists163;
import com.wwqk.model.LeagueShooter;
import com.wwqk.model.LeagueShooter163;
import com.wwqk.model.Player;
import com.wwqk.utils.ImageUtils;
import com.wwqk.utils.StringUtils;

public class PlayerService {

	public static Map<Object, Object> playerData(Controller controller){
		String sumSql = "select count(*) from player p, team t where p.team_id = t.id ";
		String sql = "select p.*, t.name team_name from player p, team t where p.team_id = t.id ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			whereSql = " and (p.name like '%"+search+"%'"+" OR t.name like '%"+search+"%'"+" OR p.first_name like '%"+search+"%'"+" OR p.last_name like '%"+search+"%'"+" OR p.nationality like '%"+search+"%' )";
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by p.name "+sortType;
			break;
		case 2:
			orderSql = " order by t.name "+sortType;
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
		List<Player> lstPalyer = new ArrayList<Player>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstPalyer = Player.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstPalyer.size()];
			for(int i=0; i<lstPalyer.size(); i++){
				Object[] obj = new Object[13];
				Player player = lstPalyer.get(i);
				obj[0] = player.get("id");
				obj[1] = player.get("name");
				obj[2] = player.get("team_name");
				obj[3] = player.get("first_name");
				obj[4] = player.get("last_name");
				obj[5] = player.get("nationality");
				obj[6] = player.get("age");
				obj[7] = player.get("height");
				obj[8] = player.get("weight");
				obj[9] = player.get("foot");
				obj[10] = player.get("number");
				obj[11] = player.get("position");
				obj[12] = player.get("img_small_local");
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
	
	@Before(Tx.class)
	public static void updatePlayer(Controller controller){
		List<UploadFile> files = new ArrayList<UploadFile>();
		try {
			files = controller.getFiles();
        }catch (Exception e){
            e.printStackTrace();
        }
		
		String id = controller.getPara("id");
		if(StringUtils.isBlank(id)){
			return;
		}
		
		String img_small_local = "";
		String img_big_local = "";
		
		for(UploadFile file : files){
			if("file_small".equals(file.getParameterName())){
				img_small_local = ImageUtils.getInstance().saveFiles(file, "players", "50x50", id, false);
			}else{
				img_big_local = ImageUtils.getInstance().saveFiles(file, "players", "150x150", id, false);
			}
		}
		
		Player player = Player.dao.findById(id);
		updateShooterAssistsName(player, controller.getPara("name"));
		player.set("name", controller.getPara("name"));
		FlagMask.setModelFlag(player, "name", controller.getPara("name"), FlagMask.PLAYER_NAME_MASK);
		
		
		player.set("height", controller.getPara("height"));
		FlagMask.setModelFlag(player, "height", controller.getPara("height"), FlagMask.PLAYER_HEIGHT_MASK);
		
		player.set("weight", controller.getPara("weight"));
		FlagMask.setModelFlag(player, "weight", controller.getPara("weight"), FlagMask.PLAYER_WEIGHT_MASK);
		
		player.set("foot", controller.getPara("foot"));
		FlagMask.setModelFlag(player, "foot", controller.getPara("foot"), FlagMask.PLAYER_FOOT_MASK);
		
		if(StringUtils.isNotBlank(controller.getPara("number"))){
			player.set("number", controller.getPara("number"));
		}
		FlagMask.setModelFlag(player, "number", controller.getPara("number"), FlagMask.PLAYER_NUMBER_MASK);
		
		
		if(StringUtils.isNotBlank(controller.getPara("first_name"))){
			player.set("first_name", controller.getPara("first_name"));
		}
		FlagMask.setModelFlag(player, "first_name", controller.getPara("first_name"), FlagMask.PLAYER_FIRST_NAME_MASK);
		
		if(StringUtils.isNotBlank(controller.getPara("last_name"))){
			player.set("last_name", controller.getPara("last_name"));
		}
		FlagMask.setModelFlag(player, "last_name", controller.getPara("last_name"), FlagMask.PLAYER_LAST_NAME_MASK);
		
		if(StringUtils.isNotBlank(controller.getPara("nationality"))){
			player.set("nationality", controller.getPara("nationality"));
		}
		FlagMask.setModelFlag(player, "nationality", controller.getPara("nationality"), FlagMask.PLAYER_NATIONALITY_MASK);
		
		if(StringUtils.isNotBlank(img_small_local)){
			player.set("img_small_local", img_small_local);
		}
		FlagMask.setModelFlag(player, "img_small_local", img_small_local, FlagMask.PLAYER_SMALL_IMG_MASK);
		
		if(StringUtils.isNotBlank(img_big_local)){
			player.set("img_big_local", img_big_local);
		}
		FlagMask.setModelFlag(player, "img_big_local", img_big_local, FlagMask.PLAYER_BIG_IMG_MASK);
		
		player.set("update_time", new Date());
		player.update();
	}
	
	private static void updateShooterAssistsName(Player playerDB, String nameNew){
		if(StringUtils.isNotBlank(nameNew) && !nameNew.equals(playerDB.get("name"))){
			LeagueShooter163 shooter163 = LeagueShooter163.dao.findFirst("select * from league_shooter_163 where player_id = ?", playerDB.getStr("id"));
			if(shooter163!=null){
				shooter163.set("player_name", nameNew);
				shooter163.update();
			}
			LeagueShooter shooter = LeagueShooter.dao.findFirst("select * from league_shooter where player_id = ?", playerDB.getStr("id"));
			if(shooter!=null){
				shooter.set("player_name", nameNew);
				shooter.update();
			}
			LeagueAssists163 assists163 = LeagueAssists163.dao.findFirst("select * from league_assists_163 where player_id = ?", playerDB.getStr("id"));
			if(assists163!=null){
				assists163.set("player_name", nameNew);
				assists163.update();
			}
			LeagueAssists assists = LeagueAssists.dao.findFirst("select * from league_assists where player_id = ?", playerDB.getStr("id"));
			if(assists!=null){
				assists.set("player_name", nameNew);
				assists.update();
			}
		}
	}
	
	public static Map<Object, Object>  allPlayerData(){
		String sumSql = "select count(*) from player p, team t where p.team_id = t.id ";
		String sql = "select p.id, p.name, t.name team_name from player p, team t where p.team_id = t.id ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
	
		Long recordsTotal = Db.queryLong(sumSql+whereSql);
		List<Player> lstPalyer = new ArrayList<Player>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstPalyer = Player.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstPalyer.size()];
			for(int i=0; i<lstPalyer.size(); i++){
				Object[] obj = new Object[3];
				Player player = lstPalyer.get(i);
				obj[0] = player.get("id");
				obj[1] = player.get("name");
				obj[2] = player.get("team_name");
				data[i] = obj;
			}
		}
		if(data==null){
			data = new Object[0];
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("data", data);
		
		return map;
	}
}
