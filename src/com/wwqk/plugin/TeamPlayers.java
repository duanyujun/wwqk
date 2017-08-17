package com.wwqk.plugin;

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
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.FlagMask;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.FetchHtmlUtils;
import com.wwqk.utils.ImageUtils;
import com.wwqk.utils.StringUtils;

public class TeamPlayers {

	String clearString = "<.*?/>";
	private static final Pattern PLAYER_URL_PATTERN = Pattern.compile("class=\"flag_16 right_16.*?<a href=\"(.*?)\".*?>(.*?)</a>");
	private static final Pattern PLAYER_IMG_PATTERN = Pattern.compile("class=\"yui-u\">.*?<img src=\"(.*?)\"");
	private static final String SITE_PROFIX = "http://cn.soccerway.com";
	
	public static void syncTeamPlayers(String teamId){
		HttpClient client = new DefaultHttpClient();  
		Team team = Team.dao.findById(teamId);
		if(team!=null){
		   String htmlTeam = FetchHtmlUtils.getHtmlContent(client, team.getStr("team_url"));
		   try {
				updatePlayersInTeam(htmlTeam, team.getStr("id"), client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		   updatePlayersImage(team.getStr("id"));
		}
		client.getConnectionManager().shutdown();
	}
	


	private static void updatePlayersInTeam(String htmlTeam, String teamId, HttpClient client) throws IOException {
		List<Player> lstNeedInsert = new ArrayList<Player>();
		List<Player> lstNeedUpdate = new ArrayList<Player>();
		Map<String, String> map = new HashMap<String, String>();
		Set<String> idSet = new HashSet<String>();
		Matcher matcher = PLAYER_URL_PATTERN.matcher(htmlTeam);
		while(matcher.find()){
			String url = matcher.group(1);
			System.err.println("^^^^handle　player："+url);
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

		
		for(Entry<String, String> entry : map.entrySet()){
			handlePlayerDetail(entry, client);
		}
	}
	
	@Before(Tx.class)
	private static void updatePlayerTeamInfo(List<Player> lstNeedInsert, List<Player> lstNeedUpdate, String teamId){
		
		if(lstNeedInsert.size()!=0 || lstNeedUpdate.size()!=0){
			//解除关联
			Db.update("update player set team_id = '' where team_id = ? ", teamId);
			Db.batchSave(lstNeedInsert, lstNeedInsert.size());
			Db.batchUpdate(lstNeedUpdate, lstNeedUpdate.size());
		}
	}
	
	private static void handlePlayerDetail(Entry<String, String> entry, HttpClient client) throws IOException{
		Player player = Player.dao.findById(entry.getKey());
		
		String playerContent = FetchHtmlUtils.getHtmlContent(client, entry.getValue());
		if(StringUtils.isBlank(player.getStr("first_name"))){
			player.set("first_name", CommonUtils.matcherString(CommonUtils.getPatternByName("名字"), playerContent));
		}
		if(StringUtils.isBlank(player.getStr("last_name"))){
			player.set("last_name", CommonUtils.matcherString(CommonUtils.getPatternByName("姓氏"), playerContent));
		}
		if(StringUtils.isBlank(player.getStr("nationality"))){
			player.set("nationality", CommonUtils.matcherString(CommonUtils.getPatternByName("国籍"), playerContent));
		}
		if(player.get("birthday")==null){
			player.set("birthday", CommonUtils.getCNDate(CommonUtils.matcherString(CommonUtils.getPatternByName("出生日期"), playerContent)));
		}
		if(StringUtils.isBlank(player.getStr("age"))){
			player.set("age", CommonUtils.matcherString(CommonUtils.getPatternByName("年龄"), playerContent));
		}
		if(StringUtils.isBlank(player.getStr("birth_country"))){
			player.set("birth_country", CommonUtils.matcherString(CommonUtils.getPatternByName("出生国家"), playerContent));
		}
		if(StringUtils.isBlank(player.getStr("birth_place"))){
			player.set("birth_place", CommonUtils.matcherString(CommonUtils.getPatternByName("出生地"), playerContent));
		}
		
		player.set("position", CommonUtils.matcherString(CommonUtils.getPatternByName("位置"), playerContent));
		player.set("foot", CommonUtils.matcherString(CommonUtils.getPatternByName("脚"), playerContent));
		
		if(StringUtils.isBlank(player.getStr("height"))){
			player.set("height", CommonUtils.matcherString(CommonUtils.getPatternByName("高度"), playerContent));
		}
		if(StringUtils.isBlank(player.getStr("weight"))){
			player.set("weight", CommonUtils.matcherString(CommonUtils.getPatternByName("重量"), playerContent));
		}
		
		player.set("update_time", new Date());
		player.set("img_big", CommonUtils.matcherString(PLAYER_IMG_PATTERN, playerContent));
		player.set("en_url", CommonUtils.getEnName(player.getStr("player_url")));
		player.update();
		
	}
	
	private static void updatePlayersImage(String teamId) {
		List<Player> lstPlayers = Player.dao.find("select * from player where team_id = ? ", teamId);
		for(Player player : lstPlayers){
			String imgStr = player.getStr("img_big");
			if(StringUtils.isNotBlank(imgStr)){
				//如果本地有上传过，则不处理
				String img_small_local = player.getStr("img_small_local");
//				if(StringUtils.isNotBlank(img_small_local) && img_small_local.contains(CommonConstants.UPLOAD_FILE_FLAG)){
//					continue;
//				}
				//只处理新增的，数据库中已有的暂时不管
				if(StringUtils.isNotBlank(img_small_local)){
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
