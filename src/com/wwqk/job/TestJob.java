package com.wwqk.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.CoachCareer;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.StringUtils;

public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<CoachCareer> lstCoachCareer = new ArrayList<CoachCareer>();
		for(int i=0; i<3; i++){
			CoachCareer coachCareer = new CoachCareer();
			//coachCareer.set("team_id", teamId);
			coachCareer.set("team_name", "team "+i);
			coachCareer.set("start_date", CommonUtils.getCNDateMonth("三月 2016"));
			String toDateStr = "五月 2016";
			if(StringUtils.isNotBlank(toDateStr)){
				coachCareer.set("end_date", CommonUtils.getCNDateMonth(toDateStr));
			}
			coachCareer.set("coach_id", "5000");
			coachCareer.set("update_time", new Date());
			lstCoachCareer.add(coachCareer);
		}
		Db.update("delete from coach_career where coach_id = ?", "5000");
		Db.batchSave(lstCoachCareer, lstCoachCareer.size());

	}

}
