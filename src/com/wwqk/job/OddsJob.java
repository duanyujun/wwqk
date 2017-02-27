package com.wwqk.job;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.OddsProviderEnum;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.utils.MatchUtils;

public class OddsJob implements Job {
	
	//TODO　球员小图片 http://cache.images.core.optasports.com/soccer/players/50x50/79495.png
	private HttpClient httpClient = new DefaultHttpClient();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
	}

	private void initHistoryOdds(){
		Set<String> leagueSet = new HashSet<String>();
		leagueSet.add("英超");
		leagueSet.add("西甲");
		leagueSet.add("德甲");
		leagueSet.add("意甲");
		leagueSet.add("法甲");
		//得到所有的比赛日期
		List<Record> lstDateStr = Db.find("SELECT DATE_FORMAT(match_date, '%Y-%m-%d') date_str FROM league_match_history GROUP BY DATE_FORMAT(match_date, '%Y-%m-%d');");
		for(Record record : lstDateStr){
			String dateStr = record.getStr("date_str");
			String content = MatchUtils.getHtmlContent(httpClient, MatchUtils.MATCH_REFER_URL, MatchUtils.MATCH_REFER_URL+"?date="+dateStr);
			Document document = Jsoup.parse(content);
			List<Element> lstTrElement = document.select(".each_match");
			for(Element tr : lstTrElement){
				if(!leagueSet.contains(tr.attr("type"))){
					continue;
				}
				String okMatchId = tr.attr("matchid");
				String homeTeamName = tr.select(".ctrl_homename").eq(0).text();
				homeTeamName = CommonConstants.DIFF_MAP.get(homeTeamName)!=null?CommonConstants.DIFF_MAP.get(homeTeamName):homeTeamName;
				String awayTeamName = tr.select(".ctrl_awayname").eq(0).text();
				awayTeamName = CommonConstants.DIFF_MAP.get(awayTeamName)!=null?CommonConstants.DIFF_MAP.get(awayTeamName):awayTeamName;
				//查询数据库中的比赛
				LeagueMatchHistory matchHistory = LeagueMatchHistory.dao.findFirst("select * from league_match_history where home_team_name = ? and away_team_name = ? and DATE_FORMAT(match_date, '%Y-%m-%d') = ? ", homeTeamName, awayTeamName, dateStr);
				if(matchHistory!=null){
					matchHistory.set("ok_match_id", okMatchId);
					matchHistory.update();
					//查询各个odds provider的赔率
					
					
				}
			}
		}
	}
	
	private void findOdds(String okMatchId, String oddsProvider){
		if(OddsProviderEnum.WH.getKey().equals(oddsProvider)){
			
		}
	}
	
}






