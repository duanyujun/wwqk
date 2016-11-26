package com.wwqk.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.LeagueAssists163;
import com.wwqk.model.LeagueShooter163;
import com.wwqk.model.Player;
import com.wwqk.utils.DateTimeUtils;

public class CalcJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("handle calc start!!!");
		calcAge();
		calcGoalAssistsNumber();
		System.err.println("handle calc end!!!");
	}
	
	private void calcAge(){
		String calcAge = "";
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		List<Player> lstPlayer = Player.dao.find("select * from player");
		for(Player player : lstPlayer){
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
	
	private void calcGoalAssistsNumber(){
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		List<LeagueShooter163> lstShooter163 = LeagueShooter163.dao.find("SELECT * FROM league_shooter_163 WHERE player_id IS NOT NULL AND player_id!=''");
		for(LeagueShooter163 shooter163 : lstShooter163){
			Player player = Player.dao.findById(shooter163.getStr("player_id"));
			if(player.getInt("goal_count")!=shooter163.getInt("goal_count")){
				player.set("goal_count", shooter163.getInt("goal_count"));
				player.set("update_time", new Date());
				lstNeedUpdate.add(player);
			}
		}
		if(lstNeedUpdate.size()!=0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
		lstNeedUpdate.clear();
		
		List<LeagueAssists163> lstAssists163 = LeagueAssists163.dao.find("SELECT * FROM league_assists_163 WHERE player_id IS NOT NULL AND player_id!=''");
		for(LeagueAssists163 assists163 : lstAssists163){
			Player player = Player.dao.findById(assists163.getStr("player_id"));
			if(player.getInt("assists_count")!=assists163.getInt("assists_count")){
				player.set("assists_count", assists163.getInt("assists_count"));
				player.set("update_time", new Date());
				lstNeedUpdate.add(player);
			}
		}
		if(lstNeedUpdate.size()!=0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}

}
