package com.wwqk.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wwqk.model.Coach;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.utils.ImageUtils;
import com.wwqk.utils.StringUtils;

public class ImageJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		handleTeamImage();
		handlePlayerImage();
		handleCoachImage();
	}
	
	private void handleTeamImage(){
		List<Team> lstTeam = Team.dao.find("select * from team order by id+0 asc ");
		for(Team team : lstTeam){
			System.err.println("+++++++ handle team image："+ team.getStr("name"));
			if(StringUtils.isNotBlank(team.getStr("team_img"))){
				team.set("team_img_local", ImageUtils.getInstance().getImgName(team.getStr("team_img")));
			}
			if(StringUtils.isNotBlank(team.getStr("venue_small_img"))){
				team.set("venue_small_img_local", ImageUtils.getInstance().getImgName(team.getStr("venue_small_img")));
			}
			if(StringUtils.isNotBlank(team.getStr("venue_img"))){
				team.set("venue_img_local", ImageUtils.getInstance().getImgName(team.getStr("venue_img")));
			}
			team.update();
		}
	}
	
	private void handlePlayerImage(){
		List<Player> lstPlayers = Player.dao.find("select * from player order by id+0");
		for(Player player : lstPlayers){
			System.err.println("+++++++ handle player image："+ player.getStr("img_big"));
			String imgStr = player.getStr("img_big");
			if(StringUtils.isNotBlank(imgStr)){
				player.set("img_big_local", ImageUtils.getInstance().getImgName(imgStr));
				String imgSmallStr = imgStr.replace("150x150", "50x50");
				player.set("img_small_local", ImageUtils.getInstance().getImgName(imgSmallStr));
			}
		}
	}
	
	private void handleCoachImage(){
		List<Coach> lstCoachs = Coach.dao.find("select * from coach order by id+0");
		for(Coach coach : lstCoachs){
			System.err.println("+++++++ handle coach image："+ coach.getStr("img_big"));
			String imgStr = coach.getStr("img_big");
			if(StringUtils.isNotBlank(imgStr)){
				coach.set("img_big_local", ImageUtils.getInstance().getImgName(imgStr));
				String imgSmallStr = imgStr.replace("150x150", "50x50");
				coach.set("img_small_local", ImageUtils.getInstance().getImgName(imgSmallStr));
			}
			coach.update();
		}
	}

}
