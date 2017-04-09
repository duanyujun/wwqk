package com.wwqk.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.FlagMask;
import com.wwqk.model.Coach;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.utils.ImageUtils;
import com.wwqk.utils.StringUtils;

public class ImageJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("handle image start!!!");
		handleTeamImage();
		//handlePlayerImage();
		//handleCoachImage();
		//replaceEmptyImage();
		System.err.println("handle image end!!!");
	}
	
	private void handleTeamImage(){
		List<Team> lstTeam = Team.dao.find("select * from team where name='拉齐奥' or name='汉堡' or name='乌迪内斯' or name='沙尔克04' order by id+0 asc ");
		for(Team team : lstTeam){
			if(StringUtils.isNotBlank(team.getStr("team_img"))){
				team.set("team_img_local", ImageUtils.getInstance().getImgName(team.getStr("team_img")));
			}
			if(StringUtils.isNotBlank(team.getStr("venue_small_img"))){
				if(FlagMask.isEditable(team.getInt("edit_flag"), FlagMask.TEAM_VENUE_IMG_MASK)){
					team.set("venue_small_img_local", ImageUtils.getInstance().getImgName(team.getStr("venue_small_img")));
				}
			}
			if(StringUtils.isNotBlank(team.getStr("venue_img"))){
				String venue_img_local = team.getStr("venue_img_local");
				if(StringUtils.isNotBlank(venue_img_local) && venue_img_local.contains(CommonConstants.UPLOAD_FILE_FLAG)){
					//do nothing
				}else{
					team.set("venue_img_local", ImageUtils.getInstance().getImgName(team.getStr("venue_img")));
				}
			}
			team.update();
		}
	}
	
	private void handlePlayerImage(){
		List<Team> lstTeam = Team.dao.find("select * from team where NAME ='拉齐奥' OR NAME = '汉堡' OR NAME ='乌迪内斯' OR NAME = '沙尔克04' order by id+0 asc ");
		for(Team team : lstTeam){
			List<Player> lstPlayers = Player.dao.find("select * from player where team_id = ? ", team.getStr("id"));
			for(Player player : lstPlayers){
				String imgStr = player.getStr("img_big");
				if(StringUtils.isNotBlank(imgStr)){
					//如果本地有上传过，则不处理
					String img_small_local = player.getStr("img_small_local");
					if(StringUtils.isNotBlank(img_small_local) && img_small_local.contains(CommonConstants.UPLOAD_FILE_FLAG)){
						continue;
					}
					if(FlagMask.isEditable(player.getInt("edit_flag"), FlagMask.PLAYER_BIG_IMG_MASK)){
						player.set("img_big_local", ImageUtils.getInstance().getImgName(imgStr));
					}
					String imgSmallStr = imgStr.replace("150x150", "50x50");
					if(FlagMask.isEditable(player.getInt("edit_flag"), FlagMask.PLAYER_SMALL_IMG_MASK)){
						player.set("img_small_local", ImageUtils.getInstance().getImgName(imgSmallStr));
					}
					player.update();
				}
			}
		}
	}
	
	private void handleCoachImage(){
		List<Coach> lstCoachs = Coach.dao.find("select * from coach order by id+0");
		for(Coach coach : lstCoachs){
			String imgStr = coach.getStr("img_big");
			if(StringUtils.isNotBlank(imgStr)){
				//如果本地有上传过，则不处理
				String img_big_local = coach.getStr("img_big_local");
				if(StringUtils.isNotBlank(img_big_local) && img_big_local.contains(CommonConstants.UPLOAD_FILE_FLAG)){
					continue;
				}
				coach.set("img_big_local", ImageUtils.getInstance().getImgName(imgStr));
				String imgSmallStr = imgStr.replace("150x150", "50x50");
				coach.set("img_small_local", ImageUtils.getInstance().getImgName(imgSmallStr));
			}
			coach.update();
		}
	}
	
	private void replaceEmptyImage(){
		String path = ImageUtils.getInstance().getDiskPath();
		File imgSmallFile = null;
		File imgBigFile = null;
		List<Player> lstPlayer = Player.dao.find("select * from player");
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		try {
			for(Player player:lstPlayer){
				imgSmallFile = new File(path+"/"+player.getStr("img_small_local"));
				if(new Long(imgSmallFile.length()).intValue()<CommonConstants.SMALL_IMG_LENGTH){
					imgBigFile = new File(path+"/"+player.getStr("img_big_local"));
					if(imgBigFile.length()>CommonConstants.BIG_IMG_LENGTH){
						ImageUtils.resizeImage(new FileInputStream(imgBigFile), new FileOutputStream(imgSmallFile), 50, "png");
					}else{
						player.set("img_small_local", CommonConstants.HEAD_SMALL_PATH);
						player.set("img_big_local", CommonConstants.HEAD_BIG_PATH);
						player.set("update_time", new Date());
						lstNeedUpdate.add(player);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}

}
