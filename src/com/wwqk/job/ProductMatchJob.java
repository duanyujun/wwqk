package com.wwqk.job;

import java.io.IOException;
import java.text.ParseException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.MatchSource;
import com.wwqk.model.Team;
import com.wwqk.model.Player;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.EnumUtils;
import com.wwqk.utils.ImageUtils;

/**
 * 
 * TODO 1、对已经同步的比赛进行归档
 * 		2、获取直播地址
 * 	    3、替换小图片
 * 在ProductJob运行完后执行
 */
public class ProductMatchJob implements Job {
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("ProductMatchJob start");
		archiveMatch();
		getLiveWebsite();
		replaceEmptyImage();
		System.err.println("ProductMatchJob end");
	}
	
	private void archiveMatch(){
		List<MatchSource> lstMatchSources = MatchSource.dao.find("select * from match_source");
		Map<String, String> nameIdMap = getTeamNameIdMap();
		//http://goal.sports.163.com/39/schedule/team/0_17_2016.html
		for(MatchSource source : lstMatchSources){
			Map<String, LeagueMatchHistory> currentMap = null;
			for(int i=1; i<=source.getInt("current_round"); i++){
				List<LeagueMatchHistory> lstNeedInsert = new ArrayList<LeagueMatchHistory>();
				List<LeagueMatchHistory> lstNeedUpdate = new ArrayList<LeagueMatchHistory>();
				LeagueMatchHistory historyExist = LeagueMatchHistory.dao.findFirst("select * from league_match_history where league_id =? and year=? and round=?", source.getStr("league_id"), source.getInt("year"), i);
				String matchUrl = "http://goal.sports.163.com/"+source.getStr("league_id_163")+"/schedule/team/0_"+i+"_"+source.getInt("year")+".html";
				System.err.println("~~~~url:"+matchUrl);
				if(historyExist==null){
					Map<String, LeagueMatchHistory> map = getMatchHistory(matchUrl, source, nameIdMap);
					lstNeedInsert.addAll(map2List(map));
					if(i == source.getInt("current_round")){
						currentMap = map;
					}
				}else{
					//更新当前轮历史数据
					if(i == source.getInt("current_round")){
						currentMap = getMatchHistory(matchUrl, source, nameIdMap);
						lstNeedUpdate.addAll(map2List(currentMap));
					}
				}
				
				saveOneRound(lstNeedInsert, lstNeedUpdate);
			}
			
			//处理当前轮次：1、更新联赛当前赛事；2、是否需要将当前轮次+1；
			if(currentMap!=null){
				List<LeagueMatch> lstMatch = new ArrayList<LeagueMatch>();
				for(Entry<String, LeagueMatchHistory> entry : currentMap.entrySet()){
					LeagueMatch match = new LeagueMatch();
					match.set("match_date", entry.getValue().getDate("match_date"));
					match.set("home_team_id", entry.getValue().getStr("home_team_id"));
					match.set("home_team_name", entry.getValue().getStr("home_team_name"));
					match.set("away_team_id", entry.getValue().getStr("away_team_id"));
					match.set("away_team_name", entry.getValue().getStr("away_team_name"));
					match.set("match_weekday", entry.getValue().getStr("match_weekday"));
					match.set("result", entry.getValue().getStr("result"));
					match.set("league_id", entry.getValue().getStr("league_id"));
					match.set("round", entry.getValue().getStr("round"));
					match.set("update_time", new Date());
					lstMatch.add(match);
				}
				
				Db.update("delete from league_match where league_id = ? ", source.getStr("league_id"));
				Db.batchSave(lstMatch, lstMatch.size());
				
				//是否需要将当前轮次+1；
				boolean updateCurrentRound = true;
				for(LeagueMatch match : lstMatch){
					if(!match.getStr("result").contains("-") && match.getDate("match_date").after(new Date())){
						updateCurrentRound = false;
					}
				}
				if(updateCurrentRound){
					int currentRound = source.getInt("current_round");
					source.set("current_round", currentRound+1>source.getInt("round_max")?source.getInt("round_max"):currentRound+1);
					source.update();
				}
			}
		}
	}
	
	private void getLiveWebsite(){
		
	}
	
	@Before(Tx.class)
	private void saveOneRound(List<LeagueMatchHistory> lstNeedInsert, List<LeagueMatchHistory> lstNeedUpdate){
		if(lstNeedInsert.size()>0){
			Db.batchSave(lstNeedInsert, lstNeedInsert.size());
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
	private Map<String, String> getTeamNameIdMap(){
		Map<String, String> map = new HashMap<String, String>();
		List<Team> lstTeams = Team.dao.find("select id,name from team");
		for(Team team : lstTeams){
			map.put(team.getStr("name"), team.getStr("id"));
		}
		return map;
	}
	
	private Map<String, LeagueMatchHistory> getMatchHistory(String matchUrl, MatchSource source, Map<String, String> nameIdMap){
		Map<String, LeagueMatchHistory> map = new HashMap<String, LeagueMatchHistory>();
		try {
			Document document = Jsoup.connect(matchUrl).get();
			Elements matchAreaElements = document.select(".leftList4");
			if(matchAreaElements.size()>0){
				Elements trElements = matchAreaElements.get(0).select("tr");
				for(Element element : trElements){
					if(element.attr("id")!=null && element.attr("id").contains("hide_")){
						continue;
					}
					Elements tdElements = element.select("td");
					if(tdElements.size()>0){
						LeagueMatchHistory history = new LeagueMatchHistory();
						history.set("round", tdElements.get(0).text());
						history.set("match_date", DateTimeUtils.parseDate(tdElements.get(1).text(), DateTimeUtils.ISO_DATETIME_NOSEC_FORMAT_ARRAY));
						history.set("home_team_id", nameIdMap.get(tdElements.get(3).text()));
						history.set("home_team_name", tdElements.get(3).text());
						history.set("away_team_id", nameIdMap.get(tdElements.get(5).text()));
						history.set("away_team_name", tdElements.get(5).text());
						if(!tdElements.get(4).text().contains("-")){
							history.set("result", tdElements.get(1).text().substring(tdElements.get(1).text().indexOf(" ")+1));
						}else{
							history.set("result", tdElements.get(4).text().replace("-", " - "));
						}
						history.set("league_id", source.getStr("league_id"));
						history.set("league_name", EnumUtils.getValue(LeagueEnum.values(), source.getStr("league_id")));
						history.set("year", source.getInt("year"));
						if(map.get(tdElements.get(3).text())==null){
							map.put(tdElements.get(3).text(), history);
						}else{
							//判断是否有重复的
							if("完场".equals(tdElements.get(2).text())){
								map.put(tdElements.get(3).text(), history);
							}
						}
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private List<LeagueMatchHistory> map2List(Map<String, LeagueMatchHistory> map){
		List<LeagueMatchHistory> lstHistory = new ArrayList<LeagueMatchHistory>();
		for(Entry<String, LeagueMatchHistory> entry:map.entrySet()){
			LeagueMatchHistory history = entry.getValue();
			history.set("id", DateTimeUtils.formatDate(history.getDate("match_date"))+"-"+history.getStr("home_team_id")+"vs"+history.getStr("away_team_id"));
			history.set("match_weekday", DateTimeUtils.formatDate(history.getDate("match_date"), DateTimeUtils.ISO_WEEK_FORMAT));
			lstHistory.add(history);
		}
		return lstHistory;
	}

	private void replaceEmptyImage(){
		String path = ImageUtils.getInstance().getDiskPath();
		File imgSmallFile = null;
		File imgBigFile = null;
		List<Player> lstPlayer = Player.dao.find("select * from player");
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		try {
			for(Player player:lstPlayer){
				imgSmallFile = new File(path+"/"+player.getStr("img_small_local"));
				if(new Long(imgSmallFile.length()).intValue()<CommonConstants.SMALL_IMG_LENGTH){
					imgBigFile = new File(path+"/"+player.getStr("img_big_local"));
					if(imgBigFile.length()>CommonConstants.BIG_IMG_LENGTH){
						ImageUtils.resizeImage(new FileInputStream(imgBigFile), new FileOutputStream(imgSmallFile), 50, "png");
					}else{
						player.set("img_small_local", CommonConstants.HEAD_SMALL_PATH);
						player.set("img_big_local", CommonConstants.HEAD_BIG_PATH);
						player.set("update_time", new Date());
						lstNeedUpdate.add(player);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(lstNeedUpdate.size()>0){
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
}


