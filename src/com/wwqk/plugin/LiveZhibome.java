package com.wwqk.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.MatchLive;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.model.Team;
import com.wwqk.model.TeamDic;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.PinyinUtils;
import com.wwqk.utils.StringUtils;

public class LiveZhibome {

	private static final String SITE_URL = "http://www.zhibo.me";
	private static final Pattern datePattern = Pattern.compile("(\\d+年\\d+月\\d+日 \\d+点\\d+分)");
	private static Set<String> filterSet = new HashSet<String>();

	public static void main(String[] args) {
		//getLiveSource();
	}
	
	public static void getLiveSource() {
		Map<String, String> leagueMap = new HashMap<String, String>();
		leagueMap.put("英超", "1");
		leagueMap.put("西甲", "2");
		leagueMap.put("德甲", "3");
		leagueMap.put("意甲", "4");
		leagueMap.put("法甲", "5");
		
		//查询team_dic
		List<TeamDic> teamDics = TeamDic.dao.find("select team_name, ok_id, std_md5, std_name, std_name_py, league_name from team_dic where std_md5 != '0'");
		Map<String, TeamDic> teamDicMap = new HashMap<>();
		for(TeamDic teamDic : teamDics) {
			teamDicMap.put(teamDic.getStr("team_name"), teamDic);
		}
		//查询五大联赛
		List<Team> teams = Team.dao.find("select id, league_id, std_md5 from team");
		Map<String, Team> teamMap = new HashMap<>();
		for(Team team : teams) {
			teamMap.put(team.getStr("team_name"), team);
		}
		MatchSourceSina matchSourceSina = MatchSourceSina.dao.findFirst("select * from match_source_sina");
		String yearShow = matchSourceSina.getStr("year_show");
		Connection connect = Jsoup.connect(SITE_URL).ignoreContentType(true);
		try {
			Document document = connect.get();
//			MatchSourceSina matchSourceSina = MatchSourceSina.dao
//					.findFirst("select * from match_source_sina");
//			String yearShow = matchSourceSina.getStr("year_show");
			Elements matchEles = document.select(".panel-body");
			String detailLink = null;
			Connection detailConn = null;
			Connection detailData = null;
			Elements lis = null;
			Element alink = null;
			Document detailDocument = null;
			String dateString = null;
			Date matchDateTime = null;
			String weekStr = null;
			String dateAndWeekStr = null;
			String leagueName = null;
			String homeTeamName = null;
			String awayTeamName = null;
			Element matchInfoEle = null;
			String homeId = null;
			String awayId = null;
			TeamDic homeTeamDic = null;
			TeamDic awayTeamDic = null;
			Team homeTeam = null;
			Team awayTeam = null;
			TeamDic teamDicDB = null;
			String homeStdMd5 = null;
			String awayStdMd5 = null;
			Date nowDate = DateTimeUtils.addHours(new Date(), -2);
			for (Element element : matchEles) {
				Thread.sleep(100);
				lis = element.select("li");
				if ("已结束".equals(lis.get(2).text()))
					continue;
				alink = element.select("a").first();
				detailLink = SITE_URL + alink.attr("href").substring(2);
				detailConn = Jsoup.connect(detailLink).ignoreContentType(true);
				detailData = detailConn.data(MatchUtils.getZhibomeHeader());
				detailDocument = detailData.get();

				// 获取时间
				dateString = CommonUtils.matcherString(datePattern,
						detailDocument.html());
				
				matchDateTime = DateTimeUtils.parseDate(dateString,
						DateTimeUtils.ISO_DATETIME_CN_FORMAT_ARRAY);
				if(nowDate.after(matchDateTime)) {
					continue;
				}
				
				// 获取联赛和主客队名称
				matchInfoEle = detailDocument.select(".hotmore").first();
				leagueName = matchInfoEle.child(0).text();
				if(filterSet.contains(leagueName)) {
					continue;
				}
				
				if(leagueName.contains("篮") || leagueName.contains("排") || "冰球".equals(leagueName) || "MLB".equals(leagueName)
						|| "格斗".equals(leagueName) || "摩托艇".equals(leagueName) || "羽毛球".equals(leagueName) || "游戏".equals(leagueName)
						|| "斯诺克".equals(leagueName) || "橄榄球".equals(leagueName) || "节目".equals(leagueName) || "自行车".equals(leagueName) 
						|| "足球".equals(leagueName) || "NBA".equals(leagueName)  || "CBA".equals(leagueName)  || "WCBA".equals(leagueName)
						|| "台球".equals(leagueName) || "赛车".equals(leagueName) || "NCAA".equals(leagueName)){
					filterSet.add(leagueName);
					continue;
				}
				
				Elements homeAwayEle = matchInfoEle.select("h1");
				if(homeAwayEle.size()<2) {
					continue;
				}
				
				//复位
				homeId = null;
				awayId = null;
				homeTeamDic = null;
				awayTeamDic = null;
				homeTeam = null;
				awayTeam = null;
				teamDicDB = null;
				homeStdMd5 = null;
				awayStdMd5 = null;
				
				homeTeamName = homeAwayEle.get(0).text();
				awayTeamName = homeAwayEle.get(1).text();
				//System.err.println(leagueName + "："+homeTeamName+" vs "+awayTeamName);
				
				//判断是否已经存在标准名称
				homeTeamDic = teamDicMap.get(homeTeamName);
				if(homeTeamDic!=null) {
					homeTeamName = homeTeamDic.getStr("std_name");
					homeStdMd5 = homeTeamDic.getStr("std_md5");
					homeTeam = teamMap.get(homeTeamDic.getStr("std_md5"));
					if(homeTeam!=null) {
						homeId = homeTeam.get("id").toString();
					}
				}else {
					teamDicDB = TeamDic.dao.findFirst("select * from team_dic where team_name = ?", homeTeamName);
					if(teamDicDB==null) {
						//插入teamDic表
						TeamDic teamDic = new TeamDic();
						teamDic.set("team_name", homeTeamName);
						teamDic.set("md5", CommonUtils.md5(homeTeamName));
						teamDic.set("league_name", leagueName);
						teamDic.save();
					}
				}
				
				awayTeamDic = teamDicMap.get(awayTeamName);
				if(awayTeamDic!=null) {
					awayTeamName = awayTeamDic.getStr("std_name");
					awayStdMd5 = awayTeamDic.getStr("std_md5");
					awayTeam = teamMap.get(awayTeamDic.getStr("std_md5"));
					if(awayTeam!=null) {
						awayId = homeTeam.get("id").toString();
					}
				}else {
					teamDicDB = TeamDic.dao.findFirst("select * from team_dic where team_name = ?", awayTeamName);
					if(teamDicDB==null) {
						//插入teamDic表
						TeamDic teamDic = new TeamDic();
						teamDic.set("team_name", awayTeamName);
						teamDic.set("md5", CommonUtils.md5(awayTeamName));
						teamDic.set("league_name", leagueName);
						teamDic.save();
					}
				}
				
				if(StringUtils.isBlank(homeTeamName) && StringUtils.isBlank(awayTeamName)) {
					continue;
				}
				
				 boolean isNeedUpdate = false;
				//查询是否需要插入
				AllLiveMatch allLiveMatch = AllLiveMatch.dao.findFirst("select * from all_live_match where home_team_name = ? and match_datetime = ? ", homeTeamName, matchDateTime);
				if(allLiveMatch!=null) {
					isNeedUpdate = true;
				}else {
					allLiveMatch =new AllLiveMatch();
				}
				
				List<MatchLive> lstMatchLives = new ArrayList<MatchLive>();
				weekStr = DateTimeUtils.formatDate2WeekDay(matchDateTime);
				weekStr = weekStr.replace("周", "星期");
				dateAndWeekStr = dateString.substring(5, 11) + " " + weekStr;
				allLiveMatch.set("match_date_week", dateAndWeekStr);
				allLiveMatch.set("weekday", weekStr);
				allLiveMatch.set("match_datetime", matchDateTime);
				allLiveMatch.set("league_name", leagueName);
				allLiveMatch.set("home_team_name", homeTeamName);
				allLiveMatch.set("away_team_name", awayTeamName);
				allLiveMatch.set("home_team_enname",PinyinUtils.getPingYin(homeTeamName));
				allLiveMatch.set("away_team_enname",PinyinUtils.getPingYin(awayTeamName));
				allLiveMatch.set("home_std_md5", homeStdMd5);
				allLiveMatch.set("away_std_md5", awayStdMd5);
				allLiveMatch.set("update_time", new Date());
				
				if(isNeedUpdate) {
					allLiveMatch.update();
				}else {
					allLiveMatch.save();
				}
				
				
				String leagueId = leagueMap.get(leagueName);
				if(homeId!=null && awayId!=null && leagueId!=null) {
						allLiveMatch.set("home_team_id", homeId);
						allLiveMatch.set("away_team_id", awayId);
						allLiveMatch.set("league_id", leagueId);
						allLiveMatch.set("match_key", yearShow + "-"
								+ homeId + "vs" + awayId);
				}else {
					allLiveMatch.set("match_key", allLiveMatch.get("id"));
				}
				
				allLiveMatch.update();

				Element liveUl = detailDocument.select(".hotpd").last();
				Elements lives = liveUl.select("a");
				if (lives.size() > 0) {
					for (Element elementLive : lives) {
						MatchLive matchLive = new MatchLive();
						String liveName = StringUtils.trim(elementLive.text());
						if(liveName.contains("足球比分") || liveName.contains("篮球比分")){
							continue;
						}
						String liveUrl = elementLive.attr("href");
						if (liveUrl.startsWith("..")) {
							liveUrl = SITE_URL + liveUrl.substring(2);
						} else if (!liveUrl.startsWith("http")) {
							liveUrl = SITE_URL + liveUrl;
						}
						
						matchLive.set("match_key", allLiveMatch.get("match_key"));
						matchLive.set("home_team_id", homeId);
						matchLive.set("away_team_id", awayId);
						matchLive.set("league_id", leagueId);
						
						matchLive.set("home_team_name", homeTeamName);
						matchLive.set("away_team_name", awayTeamName);
						matchLive.set("home_std_md5", homeStdMd5);
						matchLive.set("away_std_md5", awayStdMd5);
						matchLive.set("live_name", liveName);
						matchLive.set("live_url", liveUrl);
						matchLive.set("match_date", matchDateTime);
						matchLive.set("match_key", allLiveMatch.get("match_key"));
						lstMatchLives.add(matchLive);
					}
				}

				saveOneMatchLive(lstMatchLives);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	@Before(Tx.class)
	private static void saveOneMatchLive(List<MatchLive> lstMatchLive) {
		if (lstMatchLive.size() > 0) {
			List<MatchLive> lstDBLive = MatchLive.dao.find(
					"select * from match_live where match_key = ?",
					lstMatchLive.get(0).get("match_key").toString());
			if (lstDBLive.size() == 0) {
				Db.batchSave(lstMatchLive, lstMatchLive.size());
			} else {
				// 进行判断，频道名称或者链接相同的一律不添加
				Set<String> existSet = new HashSet<String>();
				for (MatchLive liveDB : lstDBLive) {
					existSet.add(liveDB.getStr("live_name"));
					existSet.add(liveDB.getStr("live_url"));
				}
				List<MatchLive> lstNeedInsert = new ArrayList<MatchLive>();
				for (MatchLive matchLive : lstMatchLive) {
					if (existSet.contains(matchLive.getStr("live_name"))
							|| existSet.contains(matchLive.getStr("live_url"))) {
						continue;
					}
					lstNeedInsert.add(matchLive);
				}
				if (lstNeedInsert.size() > 0) {
					Db.batchSave(lstNeedInsert, lstNeedInsert.size());
				}
			}
		}
	}

}
