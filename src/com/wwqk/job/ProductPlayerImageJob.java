package com.wwqk.job;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
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

/**
 * 
 * @author Administrator
 * TODO 1、更新players image
 */
public class ProductPlayerImageJob implements Job {
	
	private HttpClient client;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		client = new DefaultHttpClient();  
		handlePlayerImage();
		handleCoachImage();
		client.getConnectionManager().shutdown();
	}
	
	private void handlePlayerImage(){
		List<Team> lstTeam = Team.dao.find("select * from team where league_id = 1 or league_id = 3 order by id+0 asc ");
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


