package com.wwqk.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.FlagMask;
import com.wwqk.model.Coach;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.FetchHtmlUtils;
import com.wwqk.utils.ImageUtils;
import com.wwqk.utils.StringUtils;

/**
 * 新赛季更新球员信息，也可以单个球队更新球员信息
 * !!!!!!!!!!!先运行NewSeasonLeagueJob
 *
 */
public class NewSeasonPlayerJob implements Job {
	
	private HttpClient client;
	String clearString = "<.*?/>";
	private static final Pattern PLAYER_URL_PATTERN = Pattern.compile("class=\"flag_16 right_16.*?<a href=\"(.*?)\".*?>(.*?)</a>");
	private static final Pattern PLAYER_IMG_PATTERN = Pattern.compile("class=\"yui-u\">.*?<img src=\"(.*?)\"");
	private static final Pattern COUCH_URL_PATTERN = Pattern.compile("教练</th>.*?href=\"(.*?)\"");
	private static final String SITE_PROFIX = "http://cn.soccerway.com";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("NewSeasonPlayerJob start!!!");
		updatePlayers();
		System.err.println("NewSeasonPlayerJob end!!!");
	}
	
	private void updatePlayers(){
		client = new DefaultHttpClient();  
		List<Team> lstTeam = Team.dao.find("select * from team where league_id in (4) order by id+0 desc");
		String htmlTeam = null;
		
		try {
			for(Team team : lstTeam){
				System.err.println("handle team:"+team.getStr("id")+"_"+team.getStr("name"));
				htmlTeam = FetchHtmlUtils.getHtmlContent(client, team.getStr("team_url"));
				updatePlayersInTeam(htmlTeam, team.getStr("id"));
				//更新球员图片
				updatePlayersImage(team.getStr("id"));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		client.getConnectionManager().shutdown();
	}

	private void updatePlayersInTeam(String htmlTeam, String teamId) throws IOException {
		List<Player> lstNeedInsert = new ArrayList<Player>();
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		Map<String, String> map = new HashMap<String, String>();
		Set<String> idSet = new HashSet<String>();
		Matcher matcher = PLAYER_URL_PATTERN.matcher(htmlTeam);
		while(matcher.find()){
			String url = matcher.group(1);
			String id = CommonUtils.getId(url);
			if(idSet.contains(id)){
				continue;
			}else{
				idSet.add(id);
			}
			
			Player playerDB = Player.dao.findById(id);
			if(playerDB==null){
				Player player = new Player();
				player.set("id", id);
				player.set("name", matcher.group(2));
				player.set("player_url", SITE_PROFIX+url);
				player.set("team_id", teamId);
				player.set("update_time", new Date());
				lstNeedInsert.add(player);
			}else{
				playerDB.set("player_url", SITE_PROFIX+url);
				playerDB.set("team_id", teamId);
				playerDB.set("update_time", new Date());
				playerDB.update();
				lstNeedUpdate.add(playerDB);
			}
			
			map.put(id, SITE_PROFIX+url);
		}
		
		//更新球员球队id信息
		updatePlayerTeamInfo(lstNeedInsert, lstNeedUpdate, teamId);
		//教练
		//handleCouchInfo(htmlTeam, teamId);
		
		for(Entry<String, String> entry : map.entrySet()){
			handlePlayerDetail(entry);
		}
	}
	
	@Before(Tx.class)
	private void updatePlayerTeamInfo(List<Player> lstNeedInsert, List<Player> lstNeedUpdate, String teamId){
		
		if(lstNeedInsert.size()!=0 || lstNeedUpdate.size()!=0){
			//解除关联
			Db.update("update player set team_id = '' where team_id = ? ", teamId);
			Db.batchSave(lstNeedInsert, lstNeedInsert.size());
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
	private void handlePlayerDetail(Entry<String, String> entry) throws IOException{
		Player player = Player.dao.findById(entry.getKey());
		System.err.println("handle player： "+player.getStr("name")+" ing!!!");
		String playerContent = FetchHtmlUtils.getHtmlContent(client, entry.getValue());
		if(FlagMask.isEditable(player.getInt("edit_flag"), FlagMask.PLAYER_FIRST_NAME_MASK)){
			player.set("first_name", CommonUtils.matcherString(CommonUtils.getPatternByName("名字"), playerContent));
		}
		if(FlagMask.isEditable(player.getInt("edit_flag"), FlagMask.PLAYER_LAST_NAME_MASK)){
			player.set("last_name", CommonUtils.matcherString(CommonUtils.getPatternByName("姓氏"), playerContent));
		}
		if(FlagMask.isEditable(player.getInt("edit_flag"), FlagMask.PLAYER_NATIONALITY_MASK)){
			player.set("nationality", CommonUtils.matcherString(CommonUtils.getPatternByName("国籍"), playerContent));
		}
		player.set("birthday", CommonUtils.getCNDate(CommonUtils.matcherString(CommonUtils.getPatternByName("出生日期"), playerContent)));
		player.set("age", CommonUtils.matcherString(CommonUtils.getPatternByName("年龄"), playerContent));
		player.set("birth_country", CommonUtils.matcherString(CommonUtils.getPatternByName("出生国家"), playerContent));
		player.set("birth_place", CommonUtils.matcherString(CommonUtils.getPatternByName("出生地"), playerContent));
		player.set("position", CommonUtils.matcherString(CommonUtils.getPatternByName("位置"), playerContent));
		if(FlagMask.isEditable(player.getInt("edit_flag"), FlagMask.PLAYER_HEIGHT_MASK)){
			player.set("height", CommonUtils.matcherString(CommonUtils.getPatternByName("高度"), playerContent));
		}
		if(FlagMask.isEditable(player.getInt("edit_flag"), FlagMask.PLAYER_WEIGHT_MASK)){
			player.set("weight", CommonUtils.matcherString(CommonUtils.getPatternByName("重量"), playerContent));
		}
		
		player.set("update_time", new Date());
		player.set("img_big", CommonUtils.matcherString(PLAYER_IMG_PATTERN, playerContent));
		player.set("en_url", CommonUtils.getEnName(player.getStr("player_url")));
		player.update();
		
	}
	
	@Before(Tx.class)
	private void handleCouchInfo(String htmlTeam, String teamId) {
		String coachUrl = CommonUtils.matcherString(COUCH_URL_PATTERN, htmlTeam);
		if(StringUtils.isBlank(coachUrl) || !coachUrl.contains("coaches")){
			return;
		}
		
		Team team = Team.dao.findById(teamId);
		if(team!=null){
			team.set("coach_url", SITE_PROFIX+coachUrl);
			team.update();
		}
		
		String coachContent = FetchHtmlUtils.getHtmlContent(client, SITE_PROFIX+coachUrl);
		String firstName = CommonUtils.matcherString(CommonUtils.getPatternByName("名字"), coachContent);
		String lastName = CommonUtils.matcherString(CommonUtils.getPatternByName("姓氏"), coachContent);
		String nationality = CommonUtils.matcherString(CommonUtils.getPatternByName("国籍"), coachContent);
		Date birthday = CommonUtils.getCNDate(CommonUtils.matcherString(CommonUtils.getPatternByName("出生日期"), coachContent));
		String age = CommonUtils.matcherString(CommonUtils.getPatternByName("年龄"), coachContent);
		String birthCountry = CommonUtils.matcherString(CommonUtils.getPatternByName("出生国家"), coachContent);
		String birthPlace =  CommonUtils.matcherString(CommonUtils.getPatternByName("出生地"), coachContent);
		String imgBig = CommonUtils.matcherString(PLAYER_IMG_PATTERN, coachContent);
		
		System.err.println("++===++ handle coach "+firstName+" start！");
		
		String coachId = CommonUtils.getId(coachUrl);
		boolean isNeedSave = false;
		Coach coach = Coach.dao.findById(coachId);
		if(coach==null){
			coach = new Coach();
			isNeedSave = true;
		}
		coach.set("id", coachId);
		coach.set("first_name", firstName);
		coach.set("last_name", lastName);
		coach.set("name", lastName);
		coach.set("nationality", nationality);
		coach.set("birthday", birthday);
		coach.set("age", age);
		coach.set("birth_country", birthCountry);
		coach.set("birth_place", birthPlace);
		coach.set("img_big", imgBig);
		coach.set("team_id", teamId);
		coach.set("coach_url", SITE_PROFIX+coachUrl);
		coach.set("update_time", new Date());
		if(isNeedSave){
			coach.save();
		}else{
			coach.update();
		}
		
		System.err.println("++===++ handle coach "+firstName+" end！");
	}
	
	private void updatePlayersImage(String teamId) {
		List<Player> lstPlayers = Player.dao.find("select * from player where team_id = ? ", teamId);
		for(Player player : lstPlayers){
			String imgStr = player.getStr("img_big");
			if(StringUtils.isNotBlank(imgStr)){
				//如果本地有上传过，则不处理
				String img_small_local = player.getStr("img_small_local");
				if(StringUtils.isNotBlank(img_small_local) && img_small_local.contains(CommonConstants.UPLOAD_FILE_FLAG)){
					continue;
				}
				if(FlagMask.isEditable(player.getInt("edit_flag"), FlagMask.PLAYER_BIG_IMG_MASK)){
					player.set("img_big_local", ImageUtils.getInstance().getImgName(imgStr));
				}
				String imgSmallStr = imgStr.replace("150x150", "50x50");
				if(FlagMask.isEditable(player.getInt("edit_flag"), FlagMask.PLAYER_SMALL_IMG_MASK)){
					player.set("img_small_local", ImageUtils.getInstance().getImgName(imgSmallStr));
				}
				
				String path = ImageUtils.getInstance().getDiskPath();
				File imgSmallFile = null;
				File imgBigFile = null;
				try {
					//替换空白图片
					imgSmallFile = new File(path+"/"+player.getStr("img_small_local"));
					if(new Long(imgSmallFile.length()).intValue()<CommonConstants.SMALL_IMG_LENGTH){
						imgBigFile = new File(path+"/"+player.getStr("img_big_local"));
						if(imgBigFile.length()>CommonConstants.BIG_IMG_LENGTH){
							ImageUtils.resizeImage(new FileInputStream(imgBigFile), new FileOutputStream(imgSmallFile), 50, "png");
						}else{
							player.set("img_small_local", CommonConstants.HEAD_SMALL_PATH);
							player.set("img_big_local", CommonConstants.HEAD_BIG_PATH);
							player.set("update_time", new Date());
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				player.update();
			}
		}
	}

}
