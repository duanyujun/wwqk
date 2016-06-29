package com.wwqk.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wwqk.model.Team;

public class ImageJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		handleTeamImage();
		handlePlayerImage();
	}
	
	private void handleTeamImage(){
		List<Team> lstTeam = Team.dao.find("select * from team order by id+0 asc ");
		for(Team team : lstTeam){
			
		}
	}
	
	private void handlePlayerImage(){
		
	}

}
