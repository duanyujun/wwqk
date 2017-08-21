package com.wwqk.plugin;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.StringUtils;

public class LiveZhibo7 {
	
	private static final String LIVE_SITE_PROFIX = "http://www.zhibo7.com";

	public static void getLiveSource(){

		Map<String, String> leagueMap = new HashMap<String, String>();
		leagueMap.put("英超", "1");
		leagueMap.put("西甲", "2");
		leagueMap.put("德甲", "3");
		leagueMap.put("意甲", "4");
		leagueMap.put("法甲", "5");
		try {
			Document document = Jsoup.connect("http://www.zhibo7.com/").get();
			//Document document =  Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
			Elements elements = document.select(".live_content");
			if(elements.size()>0){
				for(Element element : elements){
					String dateStr = null;
					Elements h1 = element.select("h1");
					if(h1.size()>0){
						dateStr = h1.get(0).child(0).text().substring(0, 10);
					}
					//获取赛季
					MatchSourceSina matchSourceSina = MatchSourceSina.dao.findFirst("select * from match_source_sina");
					
					Elements matches = element.select("tr");
					for(Element match : matches){
						if(match.children().size()<3){
							continue;
						}
						if(leagueMap.get(match.child(1).text())!=null){
							List<MatchLive> lstMatchLive = new ArrayList<MatchLive>();
							String matchStr = match.child(2).text(); //水晶宫vs切尔西
							String homeTeamName = matchStr.split("vs")[0];
							String awayTeamName = matchStr.split("vs")[1];
							if(CommonUtils.nameIdMap.get(homeTeamName)!=null && CommonUtils.nameIdMap.get(awayTeamName)!=null){
								Elements liveLinks = match.child(3).select("a");
								for(Element liveLink : liveLinks){
									if("其它直播".equals(liveLink.text())){
										continue;
									}
									MatchLive matchLive = new MatchLive();
									
									matchLive.set("match_key", matchSourceSina.getStr("year_show")+"-"+CommonUtils.nameIdMap.get(homeTeamName)+"vs"+CommonUtils.nameIdMap.get(awayTeamName));
									matchLive.set("live_name", liveLink.text());
									if(liveLink.attr("href").startsWith("http")){
										matchLive.set("live_url", liveLink.attr("href"));
									}else{
										matchLive.set("live_url", getRealLink(LIVE_SITE_PROFIX+liveLink.attr("href")));
									}
									matchLive.set("league_id", leagueMap.get(match.child(1).text()));
									matchLive.set("home_team_id", CommonUtils.nameIdMap.get(homeTeamName));
									matchLive.set("home_team_name", homeTeamName);
									matchLive.set("away_team_id", CommonUtils.nameIdMap.get(awayTeamName));
									matchLive.set("away_team_name", awayTeamName);
									matchLive.set("match_date", DateTimeUtils.parseDate(dateStr+" "+match.child(0).text(), DateTimeUtils.ISO_DATETIME_NOSEC_FORMAT_ARRAY));
									lstMatchLive.add(matchLive);
								}
							}
							
							saveOneMatchLive(lstMatchLive);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	
	private static String getRealLink(String link){
		if(StringUtils.isBlank(link)){
			return link;
		}
		try {
			Document document = Jsoup.connect(link).get();
			List<Element> lstLinks = document.select(".col-change");
			if(lstLinks.size()>0){
				List<Element> aLink = lstLinks.get(0).select("a");
				if(aLink.size()>0){
					return aLink.get(0).attr("href");
				}
			}
		} catch (IOException e) {
			
		}
		return link;
	}
}
