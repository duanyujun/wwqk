package com.wwqk.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.FlagMask;
import com.wwqk.model.League;
import com.wwqk.model.Team;

/** 
 * @author duanyujun 
 * @date 创建时间：2018-7-4 下午11:03:35    
 */

public class NewSeasonUtils {
	
	private static String clearString = "<.*?/>";
	private static String tagString = "<.*?>";
	private static final String SITE_PROFIX = "http://cn.soccerway.com";

	public static void updateTeamsInLeague(String leagueId){
		HttpClient client = new DefaultHttpClient(); 
		League league = League.dao.findFirst("select * from league where id=?", leagueId);
		try {
			updateAllTeams(client, league.getStr("league_url"), leagueId);
			updateTeamAndVenueImage(leagueId);
		} catch (Exception e) {
			System.err.println("^^^^^^^"+e.getMessage()+" +++++"+e);
		}
		client.getConnectionManager().shutdown();
	}

	private static void updateAllTeams(HttpClient client, String leagueUrl, String leagueId) throws IOException{
		List<Team> lstNeedInsert = new ArrayList<Team>();
		List<Team> lstNeedUpdate = new ArrayList<Team>();
		Map<String, String> map = new HashMap<String, String>();
		Set<String> idSet = new HashSet<String>();
		String html = FetchHtmlUtils.getHtmlContent(client, leagueUrl);
		Document document = Jsoup.parse(html);
		Elements bodyElement = document.select(".detailed-table");
		if(bodyElement.size()>0){
			Elements teamLinks = bodyElement.get(0).select(".team_rank");
			for(Element element : teamLinks){
				String teamId = element.attr("data-team_id");
				//防止重复
				if(idSet.contains(teamId)){
					continue;
				}else{
					idSet.add(teamId);
				}
				Elements aElements = element.select("a");
				String teamName = null;
				String teamUrl = null;
				if(aElements.size()>0){
					teamName = aElements.get(0).attr("title");
					teamUrl = SITE_PROFIX + aElements.get(0).attr("href");
				}
				//判断数据库中是否存在
				Team teamDB = Team.dao.findById(teamId);
				if(teamDB==null){
					Team team = new Team();
					team.set("id", teamId);
					team.set("name", teamName);
					team.set("team_url", teamUrl);
					team.set("league_id", leagueId);
					team.set("update_time", new Date());
					lstNeedInsert.add(team);
				}else{
					//不更新球队名称
					//teamDB.set("name", teamName);
					teamDB.set("team_url", teamUrl);
					teamDB.set("league_id", leagueId);
					teamDB.set("update_time", new Date());
	 				lstNeedUpdate.add(teamDB);
				}
				map.put(teamId, teamUrl);
			}
		}
		updateTeamLeagueInfo(lstNeedInsert, lstNeedUpdate, leagueId);
		
		for(Entry<String, String> entry : map.entrySet()){
			//获取球队信息
			handleTeamDetail(client, entry);
			//获取球场信息
			handleTeamVenue(client, entry);
		}
	}
	
	@Before(Tx.class)
	private static void updateTeamLeagueInfo(List<Team> lstNeedInsert, List<Team> lstNeedUpdate, String leagueId){
		if(lstNeedInsert.size()!=0 || lstNeedUpdate.size()!=0){
			//解除关联
			Db.update("update team set league_id = '' where league_id = ? ", leagueId);
			Db.batchSave(lstNeedInsert, lstNeedInsert.size());
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
	@Before(Tx.class)
	private static void handleTeamDetail(HttpClient client, Entry<String, String> entry) throws IOException{
		Team team = Team.dao.findById(entry.getKey());
		System.err.println("handle team： "+team.getStr("name")+" ing!!!");
		String teamContent = FetchHtmlUtils.getHtmlContent(client, entry.getValue());
		Document document = Jsoup.parse(teamContent);
		
		Elements teamImgElements = document.select(".logo");
		if(teamImgElements.size()>0){
			team.set("team_img", teamImgElements.get(0).child(0).attr("src"));
		}
		team.set("setup_time", CommonUtils.matcherString(CommonUtils.getPatternByName("成立于"), teamContent));
		team.set("address", CommonUtils.matcherString(CommonUtils.getPatternByName("地址"), teamContent).replaceAll(clearString, ""));
		team.set("country", CommonUtils.matcherString(CommonUtils.getPatternByName("国家"), teamContent));
		team.set("telphone", CommonUtils.matcherString(CommonUtils.getPatternByName("电话"), teamContent));
		team.set("fax", CommonUtils.matcherString(CommonUtils.getPatternByName("传真"), teamContent));
		team.set("email", CommonUtils.matcherString(CommonUtils.getPatternByName("电子邮件"), teamContent).replaceAll(tagString, ""));
		team.set("name_en", CommonUtils.getEnName(entry.getValue()));
		
		Elements venueImgElements = document.select(".block_team_venue");
		if(venueImgElements.size()>0){
			//TODO add column
			team.set("venue_small_img", venueImgElements.get(0).child(0).child(0).attr("src"));
		}
		team.set("update_time", new Date());
		team.update();
	}
	
	@Before(Tx.class)
	private static void handleTeamVenue(HttpClient client, Entry<String, String> entry) throws IOException{
		Team team = Team.dao.findById(entry.getKey());
		String venueContent = FetchHtmlUtils.getHtmlContent(client, entry.getValue()+"venue/");
		Document document = Jsoup.parse(venueContent);
		Elements venueElements = document.select(".block_venue_info-wrapper");
		if(venueElements.size()>0){
			String venueName = venueElements.get(0).child(0).html();
			if(FlagMask.isEditable(team.getInt("edit_flag"), FlagMask.TEAM_VENUE_NAME_MASK)){
				team.set("venue_name", venueName);
			}
			team.set("venue_name_en", venueName);
			if(FlagMask.isEditable(team.getInt("edit_flag"), FlagMask.TEAM_VENUE_CITY_MASK)){
				team.set("venue_address", CommonUtils.matcherString(CommonUtils.getPatternByName("城市:"), venueContent));
			}
			
			team.set("venue_capacity", CommonUtils.matcherString(CommonUtils.getPatternByName("容量:"), venueContent));
			Elements imgElements = venueElements.get(0).select("img");
			if(imgElements.size()>0){
				String venue_img = imgElements.get(0).attr("src");
				team.set("venue_img", venue_img);
			}
			team.set("update_time", new Date());
			team.update();
		}
	}
	
	/**
	 * 更新球队和球场图片
	 */
	private static void updateTeamAndVenueImage(String leagueId) {
		List<Team> lstTeam = Team.dao.find("select * from team where league_id = ? ", leagueId);
		for(Team team : lstTeam){
			if(StringUtils.isNotBlank(team.getStr("team_img")) && StringUtils.isBlank(team.getStr("team_img_local"))){
				team.set("team_img_local", ImageUtils.getInstance().getImgName(team.getStr("team_img")));
			}
			if(StringUtils.isNotBlank(team.getStr("venue_small_img")) && StringUtils.isBlank(team.getStr("venue_small_img_local"))){
				if(FlagMask.isEditable(team.getInt("edit_flag"), FlagMask.TEAM_VENUE_IMG_MASK)){
					team.set("venue_small_img_local", ImageUtils.getInstance().getImgName(team.getStr("venue_small_img")));
					
				}
			}
			if(StringUtils.isNotBlank(team.getStr("venue_img"))){
				String venue_img_local = team.getStr("venue_img_local");
				if(StringUtils.isNotBlank(venue_img_local) && venue_img_local.contains(CommonConstants.UPLOAD_FILE_FLAG)){
					//do nothing
				}else{
					team.set("venue_img_local", ImageUtils.getInstance().getImgName(team.getStr("venue_img")));
				}
			}
			team.update();
		}
	}
	
}
