package com.wwqk.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.Player;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.ImageUtils;

/**
 * 
 * TODO 1、对已经同步的比赛进行归档
 * 		2、获取直播地址
 * 	    3、替换小图片
 * 在ProductJob运行完后执行
 */
public class ProductMatchJob implements Job {
	
	private HttpClient client;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("ProductMatchJob start");
		archiveMatch();
		getLiveWebsite();
		replaceEmptyImage();
		System.err.println("ProductMatchJob end");
	}
	
	private void archiveMatch(){
		List<LeagueMatchHistory> lstNeedInsert = new ArrayList<LeagueMatchHistory>();
		List<LeagueMatchHistory> lstNeedUpdate = new ArrayList<LeagueMatchHistory>();
		List<LeagueMatch> lstMatch = LeagueMatch.dao.find("select * from league_match");
		String matchKey = null; //格式：2016-12-17-1210vs1100
		for(LeagueMatch match : lstMatch){
			matchKey = DateTimeUtils.formatDate(match.getDate("match_date"))+"-"+match.getStr("home_team_id")
					+"vs"+match.getStr("away_team_id");
			LeagueMatchHistory matchHistory = LeagueMatchHistory.dao.findById(matchKey);
			if(matchHistory==null){
				matchHistory = new LeagueMatchHistory();
				matchHistory.set("id", matchKey);
				matchHistory.set("match_date", match.getDate("match_date"));
				matchHistory.set("match_weekday", match.get("match_weekday"));
				matchHistory.set("home_team_id", match.get("home_team_id"));
				matchHistory.set("home_team_name", match.get("home_team_name"));
				matchHistory.set("away_team_id", match.get("away_team_id"));
				matchHistory.set("away_team_name", match.get("away_team_name"));
				matchHistory.set("result", match.get("result"));
				matchHistory.set("league_id", match.getStr("league_id"));
				matchHistory.set("league_name", EnumUtils.getValue(LeagueEnum.values(), match.getStr("league_id")));
				matchHistory.set("round_id", match.getStr("round_id"));
				matchHistory.set("update_time", new Date());
				lstNeedInsert.add(matchHistory);
			}else{
				if(match.getStr("result").contains("-")){
					matchHistory.set("result", match.getStr("result"));
					lstNeedUpdate.add(matchHistory);
				}
			}
		}
		if(lstNeedInsert.size()>0){
			Db.batchSave(lstNeedInsert, lstNeedInsert.size());
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
	private void getLiveWebsite(){
		
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


