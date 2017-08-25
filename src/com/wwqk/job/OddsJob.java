package com.wwqk.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wwqk.plugin.OddsUtils;

public class OddsJob implements Job {
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		OddsUtils.initHistoryOdds();
		
	}
	
}






