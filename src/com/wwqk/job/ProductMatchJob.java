package com.wwqk.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.wwqk.plugin.Live24zbw;
import com.wwqk.plugin.Live5chajian;
import com.wwqk.plugin.LiveZuqiula;
import com.wwqk.plugin.MatchSina;
import com.wwqk.plugin.News7M;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.TranslateUtils;

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
		//足球啦直播源
		LiveZuqiula.getLiveSource();
		//24zbw直播源  暂时停掉
		//Live24zbw.getLiveSource();
		//5chajian直播源
		Live5chajian.getLiveSource();
		//情报
		News7M.crawl();
		TranslateUtils.translate();
		
		System.err.println("ProductMatchJob end");
	}
	
	
	
	

	
}


