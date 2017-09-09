package com.wwqk.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.wwqk.plugin.Live24zbw;
import com.wwqk.plugin.LiveZuqiula;
import com.wwqk.plugin.MatchSina;
import com.wwqk.utils.CommonUtils;

/**
 * 
 * 在ProductJob运行完后执行
 */
public class ProductMatchJob implements Job {
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("ProductMatchJob start");
		//初始化球队名称ID源
		CommonUtils.initNameIdMap();
		//使用sina的比赛源
		MatchSina.archiveMatch(CommonUtils.nameIdMap, CommonUtils.nameENNameMap);
		//24zbw直播源
		Live24zbw.getLiveSource();
		//足球啦直播源
		LiveZuqiula.getLiveSource();
		System.err.println("ProductMatchJob end");
	}
	
	
	
	

	
}


