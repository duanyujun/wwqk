package com.wwqk.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wwqk.utils.VideosZuqiulaUtils;

public class VideosJob implements Job {
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		VideosZuqiulaUtils.collect(false);
		VideosZuqiulaUtils.updateDesc();
		VideosZuqiulaUtils.updateRed();
	}
	
}






