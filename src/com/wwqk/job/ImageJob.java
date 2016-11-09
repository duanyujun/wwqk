package com.wwqk.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
		handlePlayerImage();
		handleCoachImage();
		System.err.println("handle image end!!!");
	}
	
	private void handleTeamImage(){
		List<Team> lstTeam = Team.dao.find("select * from team order by id+0 asc ");
		for(Team team : lstTeam){
			if(StringUtils.isNotBlank(team.getStr("team_img"))){
				team.set("team_img_local", ImageUtils.getInstance().getImgName(team.getStr("team_img")));
			}
			if(StringUtils.isNotBlank(team.getStr("venue_small_img"))){
				if(FlagMask.isEditable(team.get("edit_flag"), FlagMask.TEAM_VENUE_IMG_MASK)){
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
		List<Team> lstTeam = Team.dao.find("select * from team order by id+0 asc ");
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
					if(FlagMask.isEditable(player.get("edit_flag"), FlagMask.PLAYER_BIG_IMG_MASK)){
						player.set("img_big_local", ImageUtils.getInstance().getImgName(imgStr));
					}
					String imgSmallStr = imgStr.replace("150x150", "50x50");
					if(FlagMask.isEditable(player.get("edit_flag"), FlagMask.PLAYER_SMALL_IMG_MASK)){
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

}
