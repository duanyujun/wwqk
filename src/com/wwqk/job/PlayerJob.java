package com.wwqk.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wwqk.model.Team;

public class PlayerJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<Team> lstTeam = Team.dao.find("select * from team");
		for(Team team : lstTeam){
			String url = team.getStr("team_url");
			
		}
	}

}
