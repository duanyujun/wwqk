package com.wwqk.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wwqk.model.League;

public class TeamJob implements Job {
	
	//private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<League> leagues = League.dao.find("select * from league");
		for(League league : leagues){
			System.err.println(league.getStr("name"));
		}
	}

}
