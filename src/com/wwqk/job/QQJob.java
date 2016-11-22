package com.wwqk.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.QQEmail;

public class QQJob implements Job {
	
	private static Pattern pattern = Pattern.compile("member_id\">\\((.*?)\\)");

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("handle QQ Email start!!!");
		extractQQ();
		System.err.println("handle QQ Email end!!!");
	}
	
	private void extractQQ(){
		List<QQEmail> lstQQAdd = new ArrayList<QQEmail>();
		File path = new File("F:/qq");
		File[] files = path.listFiles();
		int count = 1;
		for(File file : files){
			String content = read(file.getAbsolutePath());
			Matcher matcher = pattern.matcher(content);
			while(matcher.find()){
				String qq = matcher.group(1);
				QQEmail qqEmail = QQEmail.dao.findFirst("select * from qq_email where qqemail = ?",qq+"@qq.com");
				if(qqEmail==null){
					count++;
					qqEmail = new QQEmail();
					qqEmail.set("qqemail", qq+"@qq.com");
					lstQQAdd.add(qqEmail);
					if(lstQQAdd.size()%500==0){
						System.err.println("save count："+count);
						Db.batchSave(lstQQAdd, lstQQAdd.size());
						lstQQAdd.clear();
					}
				}
			}
		}
	}
	
	public String read(String filePath){
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
		    String line = null;
		    while((line= br.readLine()) != null) {
		        sb.append(line);
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	    
	    return sb.toString();
	}

}
