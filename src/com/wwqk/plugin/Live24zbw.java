package com.wwqk.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.MatchLive;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.MatchUtils;

public class Live24zbw {
	
	private static final String SITE_URL = "http://www.24zbw.com/match/all/";
	

	public static void getLiveSource() {
		Map<String, String> leagueMap = new HashMap<String, String>();
		leagueMap.put("英超", "1");
		leagueMap.put("西甲", "2");
		leagueMap.put("德甲", "3");
		leagueMap.put("意甲", "4");
		leagueMap.put("法甲", "5");

		Connection connect = Jsoup.connect(SITE_URL).ignoreContentType(true);
		Connection data = connect.data(MatchUtils.get24zbwHeader());
		try {
			Document document = data.get();
			Elements matchItems = document.select(".match-item");
			if(matchItems.size()>0){
				//获取赛季
				MatchSourceSina matchSourceSina = MatchSourceSina.dao.findFirst("select * from match_source_sina");
				for(Element element : matchItems){
					String leagueName = element.select(".match-competition").get(0).text();
					if(leagueMap.get(leagueName)==null || element.text().contains("已结束")){
						continue;
					}
					List<MatchLive> lstMatchLives = new ArrayList<MatchLive>();
					Elements homeAwayNames = element.select(".match-title");
					String homeTeamName = homeAwayNames.get(0).text();
					String awayTeamName = homeAwayNames.get(1).text();
					if(CommonUtils.nameIdMap.get(homeTeamName)!=null && CommonUtils.nameIdMap.get(awayTeamName)!=null){
						Elements lives = element.select(".live-item-source");
						if(lives.size()>0){
							for(Element elementLive:lives){
								MatchLive matchLive = new MatchLive();
								matchLive.set("match_key", matchSourceSina.getStr("year_show")+"-"+CommonUtils.nameIdMap.get(homeTeamName)+"vs"+CommonUtils.nameIdMap.get(awayTeamName));
								matchLive.set("home_team_id", CommonUtils.nameIdMap.get(homeTeamName));
								matchLive.set("home_team_name", homeTeamName);
								matchLive.set("away_team_id", CommonUtils.nameIdMap.get(awayTeamName));
								matchLive.set("away_team_name", awayTeamName);
								matchLive.set("league_id", leagueMap.get(leagueName));
								matchLive.set("live_name", elementLive.text());
								matchLive.set("live_url", elementLive.attr("href"));
								lstMatchLives.add(matchLive);
							}
						}
					}
					saveOneMatchLive(lstMatchLives);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Before(Tx.class)
	private static void saveOneMatchLive(List<MatchLive> lstMatchLive){
		if(lstMatchLive.size()>0){
			List<MatchLive> lstDBLive = MatchLive.dao.find("select * from match_live where match_key = ?", lstMatchLive.get(0).getStr("match_key"));
			if(lstDBLive.size()==0){
				Db.batchSave(lstMatchLive, lstMatchLive.size());
			}else{
				//进行判断，频道名称或者链接相同的一律不添加
				Set<String> existSet = new HashSet<String>();
				for(MatchLive liveDB : lstDBLive){
					existSet.add(liveDB.getStr("live_name"));
					existSet.add(liveDB.getStr("live_url"));
				}
				List<MatchLive> lstNeedInsert = new ArrayList<MatchLive>();
				for(MatchLive matchLive : lstMatchLive){
					if(existSet.contains(matchLive.getStr("live_name")) || existSet.contains(matchLive.getStr("live_url"))){
						continue;
					}
					lstNeedInsert.add(matchLive);
				}
				if(lstNeedInsert.size()>0){
					Db.batchSave(lstNeedInsert, lstNeedInsert.size());
				}
			}
		}
	}
}
