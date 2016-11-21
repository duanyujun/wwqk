package com.wwqk.job;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wwqk.utils.FetchHtmlUtils;

public class QQJob implements Job {
	
	private HttpClient httpClient = new DefaultHttpClient();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("handle QQ Email start!!!");
		extractQQ();
		System.err.println("handle QQ Email end!!!");
	}
	
	private void extractQQ(){
		String shooterHtml = FetchHtmlUtils.getHtmlContent(httpClient, "http://qun.qzone.qq.com/group#!/537579178/member");
		
	}
	

}
