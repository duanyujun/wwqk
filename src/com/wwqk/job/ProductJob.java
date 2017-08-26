package com.wwqk.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.constants.CommonConstants;
import com.wwqk.model.Player;
import com.wwqk.plugin.ShooterAssister163;
import com.wwqk.plugin.TeamPosition;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.GeneratorUtils;
import com.wwqk.utils.ImageUtils;

/**
 * 
 * @author Administrator
 * TODO 1、同步比赛
 */
public class ProductJob implements Job {
	
	private HttpClient client;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		client = new DefaultHttpClient();  
		
		//同步进球助攻
		ShooterAssister163.syncAll();
		
		//计算年龄
		calcAge();
		
		//更新球队排名
		TeamPosition.getPosition();
		
		//生成网站地图
		GeneratorUtils.generateSitemap();
		
		client.getConnectionManager().shutdown();
	}
	
	private void calcAge(){
		String calcAge = "";
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		List<Player> lstPlayer = Player.dao.find("SELECT * FROM player WHERE  DATE_FORMAT(birthday, '%m-%d') = DATE_FORMAT(NOW(), '%m-%d')");
		for(Player player : lstPlayer){
			if(player.getDate("birthday")==null){
				continue;
			}
			calcAge = String.valueOf(DateTimeUtils.getAgeByBirthday(player.getDate("birthday")));
			if(!calcAge.equals(player.getStr("age"))){
				player.set("age", calcAge);
				player.set("update_time", new Date());
				lstNeedUpdate.add(player);
			}
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
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


