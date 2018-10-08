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
import com.wwqk.model.League;
import com.wwqk.model.MatchLive;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.MatchUtils;
import com.wwqk.utils.PinyinUtils;
import com.wwqk.utils.StringUtils;

public class LiveZhibome {

	private static final String SITE_URL = "http://www.zhibo.me";
	private static final Pattern datePattern = Pattern
			.compile("(\\d+年\\d+月\\d+日 \\d+点\\d+分)");

	public static void main(String[] args) {
		getLiveSource();
	}

	public static void getLiveSource() {
		// 初始化已经存在的直播
		Map<String, Object> mapAll = CommonUtils.getHALiveMatchMap();
		Map<String, AllLiveMatch> homeMap = (Map<String, AllLiveMatch>) mapAll.get("home");
		Map<String, AllLiveMatch> awayMap = (Map<String, AllLiveMatch>) mapAll.get("away");

		Map<String, String> leagueMap = new HashMap<String, String>();
		leagueMap.put("英超", "1");
		leagueMap.put("西甲", "2");
		leagueMap.put("德甲", "3");
		leagueMap.put("意甲", "4");
		leagueMap.put("法甲", "5");
		CommonUtils.initNameIdMap();
		Connection connect = Jsoup.connect(SITE_URL).ignoreContentType(true);
		try {
			Document document = connect.get();
			MatchSourceSina matchSourceSina = MatchSourceSina.dao
					.findFirst("select * from match_source_sina");
			String yearShow = matchSourceSina.getStr("year_show");
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
				// 获取联赛和主客队名称
				matchInfoEle = detailDocument.select(".hotmore").first();
				leagueName = matchInfoEle.child(0).text();
				if(leagueName.contains("篮") || leagueName.contains("排") || "冰球".equals(leagueName) || "MLB".equals(leagueName)
						|| "格斗".equals(leagueName) || "摩托艇".equals(leagueName) || "羽毛球".equals(leagueName) || "游戏".equals(leagueName)
						|| "斯诺克".equals(leagueName) || "橄榄球".equals(leagueName) || "节目".equals(leagueName) || "自行车".equals(leagueName) 
						|| "足球".equals(leagueName)){
					continue;
				}
				
				if(matchInfoEle.children().size()<5){
					continue;
				}
				
				homeTeamName = matchInfoEle.child(1).text();
				awayTeamName = matchInfoEle.child(4).text();

				List<MatchLive> lstMatchLives = new ArrayList<MatchLive>();
				String homeTeamId = CommonUtils.nameIdMap.get(homeTeamName);
				String awayTeamId = CommonUtils.nameIdMap.get(awayTeamName);
				AllLiveMatch allLiveMatch = homeMap.get(DateTimeUtils
						.formatDate(matchDateTime) + homeTeamName);
				if (allLiveMatch == null) {
					allLiveMatch = awayMap.get(DateTimeUtils
							.formatDate(matchDateTime) + awayTeamName);
				}
				boolean isNeedInsert = false;
				if (allLiveMatch == null) {
					if (StringUtils.isNotBlank(homeTeamId)
							&& StringUtils.isNotBlank(awayTeamId)) {
						// 因为本系统已经替换了主客队名称，需再次查询一下主客队id
						allLiveMatch = AllLiveMatch.dao
								.findFirst(
										"select * from all_live_match where home_team_id = ? and away_team_id = ? and year_show = ? and match_datetime > ? ",
										homeTeamId, awayTeamId, yearShow,
										nowDate);
						if (allLiveMatch == null) {
							isNeedInsert = true;
							allLiveMatch = new AllLiveMatch();
						}
					} else {
						isNeedInsert = true;
						allLiveMatch = new AllLiveMatch();
					}
				}
				weekStr = DateTimeUtils.formatDate2WeekDay(matchDateTime);
				weekStr = weekStr.replace("周", "星期");
				dateAndWeekStr = dateString.substring(5, 11) + " " + weekStr;
				allLiveMatch.set("match_date_week", dateAndWeekStr);
				allLiveMatch.set("weekday", weekStr);
				allLiveMatch.set("match_datetime", matchDateTime);
				allLiveMatch.set("league_name", leagueName);
				allLiveMatch.set("home_team_name", homeTeamName);
				allLiveMatch.set("away_team_name", awayTeamName);
				allLiveMatch.set("year_show", yearShow);
				String leagueId = leagueMap.get(leagueName);
				if (StringUtils.isNotBlank(leagueId)) {
					if (StringUtils.isNotBlank(homeTeamId)
							&& StringUtils.isNotBlank(awayTeamId)) {
						Team home = Team.dao.findById(homeTeamId);
						Team away = Team.dao.findById(awayTeamId);
						allLiveMatch.set("home_team_id", homeTeamId);
						allLiveMatch.set("away_team_id", awayTeamId);
						allLiveMatch.set("league_id", leagueId);
						League league = League.dao.findById(leagueId);
						allLiveMatch.set("league_enname",
								league.getStr("name_en"));
						// 使用本系统球队名称
						allLiveMatch.set("home_team_name", home.getStr("name"));
						allLiveMatch.set("away_team_name", away.getStr("name"));
						allLiveMatch.set("home_team_enname",
								home.getStr("name_en"));
						allLiveMatch.set("away_team_enname",
								away.getStr("name_en"));
						allLiveMatch.set("match_key", yearShow + "-"
								+ homeTeamId + "vs" + awayTeamId);
					}
				} else {
					allLiveMatch.set("home_team_enname",
							PinyinUtils.getPingYin(homeTeamName));
					allLiveMatch.set("away_team_enname",
							PinyinUtils.getPingYin(awayTeamName));
				}

				allLiveMatch.set("update_time", new Date());
				if (isNeedInsert) {
					allLiveMatch.save();
					if (StringUtils.isBlank(allLiveMatch.getStr("match_key"))) {
						allLiveMatch.set("match_key", allLiveMatch.get("id"));
					}
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
						if (CommonUtils.nameIdMap.get(homeTeamName) != null
								&& CommonUtils.nameIdMap.get(awayTeamName) != null) {
							matchLive.set(
									"match_key",
									yearShow
											+ "-"
											+ CommonUtils.nameIdMap
													.get(homeTeamName)
											+ "vs"
											+ CommonUtils.nameIdMap
													.get(awayTeamName));
							matchLive.set("home_team_id",
									CommonUtils.nameIdMap.get(homeTeamName));
							matchLive.set("away_team_id",
									CommonUtils.nameIdMap.get(awayTeamName));
							matchLive.set("league_id",
									leagueMap.get(leagueName));
						} else {
							matchLive.set("match_key", String
									.valueOf(allLiveMatch.get("match_key")));
						}
						matchLive.set("home_team_name", homeTeamName);
						matchLive.set("away_team_name", awayTeamName);
						matchLive.set("live_name", liveName);
						matchLive.set("live_url", liveUrl);
						matchLive.set("match_date", matchDateTime);
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
					lstMatchLive.get(0).getStr("match_key"));
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

	private static Document getCompleteDocument(String matchHtml) {
		String completeHtml = "<html><head><head/><body><table><thead></thead><tbody><tr class=\"date\">"
				+ matchHtml + "</tbody></table></body></html>";
		Document document = Jsoup.parse(completeHtml);
		return document;
	}

}
