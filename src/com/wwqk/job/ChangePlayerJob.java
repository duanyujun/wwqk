package com.wwqk.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;

public class ChangePlayerJob implements Job {
	
	//TODO　球员小图片 http://cache.images.core.optasports.com/soccer/players/50x50/79495.png
	private HttpClient httpClient = new DefaultHttpClient();
	String clearString = "<.*?/>";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		updateTeamEnName();
	}

	
	private void updateTeamEnName(){
		List<Team> lstTeam = Team.dao.find("select * from team");
		for(Team team : lstTeam){
			team.set("name_en", CommonUtils.getEnName(team.getStr("team_url")));
		}
		Db.batchUpdate(lstTeam, lstTeam.size());
	}
	
	private void updatePlayer(){
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		List<Player> lstPlayers = Player.dao.find("select * from player order by id+0 asc");
		int count = 1;
		for(Player player : lstPlayers){
//			System.err.println("player id：" + player.getStr("id")+"__player name："+player.getStr("name")+"__count:"+count++);
//			try {
//				Thread.sleep(300);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			if("左".equals(player.get("first_name"))
//		       || "右".equals(player.get("first_name"))
//		       || "双".equals(player.get("first_name"))
//		       || "0".equals(player.get("first_name"))
//		       ){
//				String playerContent = FetchHtmlUtils.getHtmlContent(httpClient, player.getStr("player_url"));
//				player.set("first_name", CommonUtils.matcherString(CommonUtils.getPatternByName("名字"), playerContent));
//				lstNeedUpdate.add(player);
//			}
			System.err.println(count++);
			player.set("en_url", CommonUtils.getEnName(player.getStr("player_url")));
			lstNeedUpdate.add(player);
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
}






