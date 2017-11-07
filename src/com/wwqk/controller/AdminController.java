package com.wwqk.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.FlagMask;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.Article;
import com.wwqk.model.Fun;
import com.wwqk.model.League;
import com.wwqk.model.LeagueAssists;
import com.wwqk.model.LeagueAssists163;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.LeagueShooter;
import com.wwqk.model.LeagueShooter163;
import com.wwqk.model.MatchLive;
import com.wwqk.model.Player;
import com.wwqk.model.Say;
import com.wwqk.model.Team;
import com.wwqk.model.TipsMatch;
import com.wwqk.model.Videos;
import com.wwqk.plugin.AnalyzeZgzcw;
import com.wwqk.plugin.Live5chajian;
import com.wwqk.plugin.LiveZuqiula;
import com.wwqk.plugin.MatchSina;
import com.wwqk.plugin.News7M;
import com.wwqk.plugin.OddsUtils;
import com.wwqk.plugin.PlayerInfoPlugin;
import com.wwqk.plugin.ShooterAssister163;
import com.wwqk.plugin.TeamPlayers;
import com.wwqk.plugin.TeamPosition;
import com.wwqk.service.AllMatchService;
import com.wwqk.service.Assists163Service;
import com.wwqk.service.FunService;
import com.wwqk.service.LeagueService;
import com.wwqk.service.MatchHistoryService;
import com.wwqk.service.MatchService;
import com.wwqk.service.PlayerService;
import com.wwqk.service.SayService;
import com.wwqk.service.Shooter163Service;
import com.wwqk.service.TeamService;
import com.wwqk.service.TipsMatchService;
import com.wwqk.service.VideosService;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.DateTimeUtils;
import com.wwqk.utils.GeneratorUtils;
import com.wwqk.utils.ImageUtils;
import com.wwqk.utils.StringUtils;
import com.wwqk.utils.TranslateUtils;

public class AdminController extends Controller {
	
	public void index(){
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			redirect("/home");
		}else{
			redirect("/login");
		}
	}

	public void listLeague(){
		render("admin/leagueList.jsp");
	}
	
	public void leagueData(){
		
		Map<Object, Object> map = LeagueService.leagueData(this);
		renderJson(map);
	}
	
	public void editLeague(){
		String id = getPara("id");
		if(id!=null){
			League league = League.dao.findById(id);
			setAttr("league", league);
		}
		
		render("admin/leagueForm.jsp");
	}
	
	public void deleteLeague(){
		renderJson(1);
	}
	
	
	public void listTeam(){
		render("admin/teamList.jsp");
	}
	
	public void teamData(){
		Map<Object, Object> map = TeamService.teamData(this);
		renderJson(map);
	}
	
	public void editTeam(){
		String id = getPara("id");
		if(id!=null){
			Team team = Team.dao.findById(id);
			setAttr("team", team);
		}
		
		render("admin/teamForm.jsp");
	}
	
	public void saveTeam(){
		List<UploadFile> files = new ArrayList<UploadFile>();
		try {
			files = getFiles();
        }catch (Exception e){
            e.printStackTrace();
        }
		
		String id = getPara("id");
		if(id == null){
			return;
		}
		
		String img_small_local = "";
		String img_big_local = "";
		for(UploadFile file : files){
			if("file_small".equals(file.getParameterName())){
				img_small_local = ImageUtils.getInstance().saveFiles(file, "venues", "300x225", id, false);
			}else{
				img_big_local = ImageUtils.getInstance().saveFiles(file, "venues", "600x450", id, false);
			}
		}
		
		Team team = Team.dao.findById(id);
		
		String name = getPara("name");
		String setup_time = getPara("setup_time");
		String venue_name = getPara("venue_name");
		FlagMask.setModelFlag(team, "venue_name", venue_name, FlagMask.TEAM_VENUE_NAME_MASK);
		String team_url = getPara("team_url");
		if(StringUtils.isNotBlank(img_small_local)){
			team.set("venue_small_img_local", img_small_local);
		}
		FlagMask.setModelFlag(team, "venue_small_img_local", img_small_local, FlagMask.TEAM_VENUE_IMG_MASK);
		if(StringUtils.isNotBlank(img_big_local)){
			team.set("venue_img_local", img_big_local);
		}
		FlagMask.setModelFlag(team, "venue_img_local", img_big_local, FlagMask.TEAM_VENUE_IMG_BIG_MASK);
		String venue_address = getPara("venue_address");
		FlagMask.setModelFlag(team, "venue_address", venue_address, FlagMask.TEAM_VENUE_CITY_MASK);
		
		
		team.set("name", name);
		team.set("setup_time", setup_time);
		team.set("venue_name", venue_name);
		team.set("venue_address", venue_address);
		team.set("team_url", team_url);
		team.set("offical_site", getPara("offical_site"));
		String cloth = getPara("cloth");
		if(StringUtils.isNotBlank(cloth) && !cloth.contains("assets")){
			cloth = "assets/image/soccer/teams/cloth/"+cloth;
		}
		team.set("cloth", cloth);
		
		team.update();
		
		renderJson(1);
	}
	
	public void deleteTeam(){
		renderJson(1);
	}
	
	public void listPlayer(){
		render("admin/playerList.jsp");
	}
	
	public void playerData(){
		Map<Object, Object> map = PlayerService.playerData(this);
		renderJson(map);
	}
	
	public void editPlayer(){
		String id = getPara("id");
		if(id!=null){
			Player player = Player.dao.findById(id);
			setAttr("player", player);
		}
		
		render("admin/playerForm.jsp");
	}
	
	public void deletePlayer(){
		renderJson(1);
	}
	
	public void savePlayer(){
		PlayerService.updatePlayer(this);
		render("admin/playerList.jsp");
	}
	
	
	public void listSay(){
		render("admin/sayList.jsp");
	}
	
	public void sayData(){
		Map<Object, Object> map = SayService.sayData(this);
		renderJson(map);
	}
	
	public void editSay(){
		String id = getPara("id");
		if(id!=null){
			Say say = Say.dao.findById(id);
			setAttr("say", say);
		}
		//全部player
		List<Player> lstPlayer = Player.dao.find("select p.*, t.name team_name from player p, team t where p.team_id = t.id ");
		setAttr("lstPlayer", lstPlayer);
		render("admin/sayForm.jsp");
	}
	
	public void saveSay(){
		SayService.saveSay(this);
		Map<String, String> result = new HashMap<String, String>();
		result.put("success", "1");
		renderJson(result);
	}
	
	public void deleteSay(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			Db.update("delete from say where id in (" + ids +")");
			Db.update("delete from fun where source_id in (" + ids +")");
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
	public void listFun(){
		render("admin/funList.jsp");
	}
	
	public void funData(){
		Map<Object, Object> map = FunService.funData(this);
		renderJson(map);
	}
	
	public void editFun(){
		String id = getPara("id");
		if(id!=null){
			Fun fun = Fun.dao.findById(id);
			if(fun.get("player_id")!=null){
				Article article = Article.dao.findFirst("select * from article where fun_id = ? ", id);
				fun.set("content", article.get("content"));
			}
			setAttr("fun", fun);
		}
		//全部player
		List<Player> lstPlayer = Player.dao.find("select p.*, t.name team_name from player p, team t where p.team_id = t.id ");
		setAttr("lstPlayer", lstPlayer);
		render("admin/funForm.jsp");
	}
	
	public void saveFun(){
		FunService.saveFun(this);
		renderJson(1);
	}
	
	public void deleteFun(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from fun "+whereSql);
			String articleWhereSql = " where fun_id in (" + ids +")";
			Db.update("delete from article "+articleWhereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
	
	
	public void listShooter163(){
		render("admin/shooter163List.jsp");
	}
	
	public void shooter163Data(){
		Map<Object, Object> map = Shooter163Service.shooter163Data(this);
		renderJson(map);
	}
	
	public void editShooter163(){
		String id = getPara("id");
		if(id!=null){
			LeagueShooter163 shooter163 = LeagueShooter163.dao.findById(id);
			setAttr("shooter163", shooter163);
		}
		List<Player> lstPlayer = Player.dao.find("select p.*, t.name team_name from player p, team t where p.team_id = t.id ");
		setAttr("lstPlayer", lstPlayer);
		
		render("admin/shooter163Form.jsp");
	}
	
	public void saveShooter163(){
		Shooter163Service.saveShooter163(this);
		renderJson(1);
	}
	
	public void deleteShooter163(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from league_shooter_163 "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
	public void listAssists163(){
		render("admin/assists163List.jsp");
	}
	
	public void assists163Data(){
		Map<Object, Object> map = Assists163Service.assists163Data(this);
		renderJson(map);
	}
	
	public void editAssists163(){
		String id = getPara("id");
		if(id!=null){
			LeagueAssists163 assists163 = LeagueAssists163.dao.findById(id);
			setAttr("assists163", assists163);
		}
		List<Player> lstPlayer = Player.dao.find("select p.*, t.name team_name from player p, team t where p.team_id = t.id ");
		setAttr("lstPlayer", lstPlayer);
		
		render("admin/assists163Form.jsp");
	}
	
	public void saveAssists163(){
		Assists163Service.saveAssists163(this);
		renderJson(1);
	}
	
	public void deleteAssists163(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from league_assists_163 "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
	public void copyShooter(){
		Map<String, String> idENNameMap = new HashMap<String, String>();
		List<Team> lstTeams = Team.dao.find("select id,name_en from team");
		for(Team team : lstTeams){
			idENNameMap.put(team.getStr("id"), team.getStr("name_en"));
		}
		List<League> lstLeagues = League.dao.find("select * from league ");
		for(League league:lstLeagues){
			List<LeagueShooter163> lstShooter163 = LeagueShooter163.dao.find("select * from league_shooter_163 where league_id = ? and player_id is not null order by goal_count desc, penalty_count asc limit 0, ? ", league.get("id"), CommonConstants.DEFAULT_RANK_SIZE);
			List<LeagueShooter> lstShooter = new ArrayList<LeagueShooter>();
			if(lstShooter163.size()>0){
				for(LeagueShooter163 shooter163:lstShooter163){
					LeagueShooter shooter = new LeagueShooter();
					shooter.set("player_id", shooter163.get("player_id"));
					Player player = Player.dao.findById(shooter163.getStr("player_id"));
					shooter.set("player_img", player.get("img_small_local"));
					shooter.set("player_name", player.get("name"));
					shooter.set("player_name_en", player.get("en_url"));
					shooter.set("rank", shooter163.get("rank"));
					shooter.set("team_id", shooter163.get("team_id"));
					shooter.set("team_name", shooter163.get("team_name"));
					shooter.set("team_name_en", idENNameMap.get(shooter163.getStr("team_id")));
					shooter.set("goal_count", shooter163.get("goal_count"));
					shooter.set("penalty_count", shooter163.get("penalty_count"));
					shooter.set("league_id", shooter163.get("league_id"));
					shooter.set("update_time", new Date());
					
					lstShooter.add(shooter);
				}
				updateShooter(league.getStr("id"), lstShooter);
			}
		}
		
		ShooterAssister163.calcGoalAssistsNumber();
		
		renderJson(1);
	}
	
	public void copyAssists(){
		Map<String, String> idENNameMap = new HashMap<String, String>();
		List<Team> lstTeams = Team.dao.find("select id,name_en from team");
		for(Team team : lstTeams){
			idENNameMap.put(team.getStr("id"), team.getStr("name_en"));
		}
		List<League> lstLeagues = League.dao.find("select * from league ");
		for(League league:lstLeagues){
			List<LeagueAssists163> lstAssists163 = LeagueAssists163.dao.find("select * from league_assists_163 where league_id = ? and player_id is not null order by rank asc limit 0, ? ", league.get("id"), CommonConstants.DEFAULT_RANK_SIZE);
			List<LeagueAssists> lstAssists = new ArrayList<LeagueAssists>();
			if(lstAssists163.size()>0){
				for(LeagueAssists163 assists163:lstAssists163){
					LeagueAssists assists = new LeagueAssists();
					assists.set("player_id", assists163.get("player_id"));
					Player player = Player.dao.findById(assists.getStr("player_id"));
					assists.set("player_img", player.get("img_small_local"));
					assists.set("player_name", assists163.get("player_name"));
					assists.set("player_name_en", player.get("en_url"));
					assists.set("rank", assists163.get("rank"));
					assists.set("team_id", assists163.get("team_id"));
					assists.set("team_name", assists163.get("team_name"));
					assists.set("team_name_en", idENNameMap.get(assists163.getStr("team_id")));
					assists.set("assists_count", assists163.get("assists_count"));
					assists.set("league_id", assists163.get("league_id"));
					assists.set("update_time", new Date());
					lstAssists.add(assists);
				}
				updateAssists(league.getStr("id"), lstAssists);
			}
		}
		ShooterAssister163.calcGoalAssistsNumber();
		
		renderJson(1);
	}
	
	@Before(Tx.class)
	private void updateShooter(String leagueId, List<LeagueShooter> lstShooter){
		Db.update("delete from league_shooter where league_id = ? ", leagueId);
		Db.batchSave(lstShooter, lstShooter.size());
	}
	
	@Before(Tx.class)
	private void updateAssists(String leagueId, List<LeagueAssists> lstAssists){
		Db.update("delete from league_assists where league_id = ? ", leagueId);
		Db.batchSave(lstAssists, lstAssists.size());
	}
	
	public void listMatchHistory(){
		render("admin/matchHistoryList.jsp");
	}
	
	public void matchHistoryData(){
		
		Map<Object, Object> map = MatchHistoryService.matchHistoryData(this);
		renderJson(map);
	}
	
	public void editMatchHistory(){
		String id = getPara("id");
		if(id!=null){
			LeagueMatchHistory history = LeagueMatchHistory.dao.findById(id);
			setAttr("history", history);
			
			List<MatchLive> lstMatchLive = MatchLive.dao.find("select * from match_live where match_key = ? ", id);
			setAttr("lstMatchLive", lstMatchLive);
		}
		
		render("admin/matchHistoryForm.jsp");
	}
	
	public void saveMatchLive() throws UnsupportedEncodingException{
		String id = getPara("id");
		String liveName = null;
		if(StringUtils.isNotBlank(getPara("live_name"))){
			liveName = new String(getPara("live_name").getBytes("ISO-8859-1"),"UTF-8");
		}
		if(StringUtils.isNotBlank(id)){
			MatchLive matchLive = MatchLive.dao.findById(id);
			if(matchLive!=null){
				matchLive.set("live_name", java.net.URLDecoder.decode(liveName , "UTF-8"));
				matchLive.set("live_url", getPara("live_url"));
				matchLive.update();
			}
		}else{
			LeagueMatchHistory history = LeagueMatchHistory.dao.findById(getPara("matchKey"));
			MatchLive matchLive = new MatchLive();
			matchLive.set("match_key", getPara("matchKey"));
			matchLive.set("live_name", java.net.URLDecoder.decode(liveName , "UTF-8"));
			matchLive.set("live_url", getPara("live_url"));
			matchLive.set("league_id", history.get("league_id"));
			matchLive.set("home_team_id", history.get("home_team_id"));
			matchLive.set("home_team_name", history.get("home_team_name"));
			matchLive.set("away_team_id", history.get("away_team_id"));
			matchLive.set("away_team_name", history.get("away_team_name"));
			matchLive.set("match_date", history.get("match_date"));
			matchLive.save();
		}
		renderJson(1);
	}
	
	public void deleteMatchLive(){
		String id = getPara("id");
		if(StringUtils.isNotBlank(id)){
			MatchLive.dao.deleteById(id);
		}
		renderJson(1);
	}
	
	public void allPlayerData(){
		Map<Object, Object> map = PlayerService.allPlayerData();
		renderJson(map);
	}
	
	// 比赛列表
	public void listMatch(){
		render("admin/matchList.jsp");
	}
	
	public void matchData(){
		Map<Object, Object> map = MatchService.matchData(this);
		renderJson(map);
	}
	
	public void editMatch(){
		String id = getPara("id");
		if(id!=null){
			LeagueMatchHistory matchHistory = LeagueMatchHistory.dao.findById(id);
			matchHistory.set("match_date", DateTimeUtils.formatDateTime(matchHistory.getTimestamp("match_date")));
			setAttr("match", matchHistory);
		}
		
		render("admin/matchForm.jsp");
	}
	
	public void deleteMatch(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from league_match_history "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
	public void saveMatch(){
		MatchService.updateMatch(this);
		render("admin/matchList.jsp");
	}
	
	
	// 全部直播比赛列表
	public void listAllMatch(){
		render("admin/allMatchList.jsp");
	}
	
	public void allMatchData(){
		Map<Object, Object> map = AllMatchService.matchData(this);
		renderJson(map);
	}
	
	public void editAllMatch(){
		String id = getPara("id");
		if(id!=null){
			AllLiveMatch match = AllLiveMatch.dao.findById(id);
			match.set("match_datetime", DateTimeUtils.formatDateTime(match.getTimestamp("match_datetime")));
			setAttr("match", match);
		}
		
		render("admin/allMatchForm.jsp");
	}
	
	public void deleteAllMatch(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from all_live_match "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
	public void saveAllMatch(){
		AllMatchService.updateMatch(this);
		render("admin/allMatchList.jsp");
	}
	
	public void listTipsMatch(){
		render("admin/tipsMatchList.jsp");
	}
	
	public void tipsMatchData(){
		Map<Object, Object> map = TipsMatchService.tipsMatchData(this);
		renderJson(map);
	}
	
	public void editTipsMatch(){
		String id = getPara("id");
		if(id!=null){
			TipsMatch tipsMatch = TipsMatch.dao.findById(id);
			tipsMatch.set("match_time", DateTimeUtils.formatDateTime(tipsMatch.getTimestamp("match_time")));
			setAttr("tipsMatch", tipsMatch);
		}
		Date nowDate = DateTimeUtils.addHours(new Date(), -20);
		List<AllLiveMatch> lstAllLiveMatch = AllLiveMatch.dao.find("select id,league_name,match_datetime,home_team_name,away_team_name from all_live_match where match_datetime > ?", nowDate);
		for(AllLiveMatch liveMatch:lstAllLiveMatch){
			liveMatch.set("match_datetime", DateTimeUtils.formatDateTime(liveMatch.getTimestamp("match_datetime")));
		}
		setAttr("lstLiveMatch", lstAllLiveMatch);
		render("admin/tipsMatchForm.jsp");
	}
	
	public void saveTipsMatch(){
		TipsMatchService.saveTipsMatch(this);
		renderJson(1);
	}
	
	public void deleteTipsMatch(){
		renderJson(1);
	}
	
	public void listVideos(){
		render("admin/videosList.jsp");
	}
	
	public void videosData(){
		Map<Object, Object> map = VideosService.videosData(this);
		renderJson(map);
	}
	
	public void editVideos(){
		String id = getPara("id");
		if(id!=null){
			Videos videos = Videos.dao.findById(id);
			setAttr("videos", videos);
		}
		render("admin/videosForm.jsp");
	}
	
	public void saveVideos(){
		VideosService.saveVideos(this);
		renderJson(1);
	}
	
	public void deleteVideos(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from videos "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
	
	//日常管理：手动更新比赛信息； 手动更新一只球队球员情况
	public void dailyManage(){
		//获取比赛联赛信息
		List<LeagueMatch> lstMatch = LeagueMatch.dao.find(
				"select m.home_team_id, m.away_team_id, m.home_team_name, m.away_team_name, l.name league_name from league_match m, league l where m.league_id = l.id");
		setAttr("lstMatch", lstMatch);
		render("admin/dailyManage.jsp");
	}
	
	public void handUpdateMatches(){
		CommonUtils.initNameIdMap();
		MatchSina.archiveMatch(CommonUtils.nameIdMap, CommonUtils.nameENNameMap);
		renderJson(1);
	}
	
	public void syncShooterAssister(){
		ShooterAssister163.syncAll();
		renderJson(1);
	}
	
	//更新球队成员
	public void updateTeamPlayer(){
		String teamId = getPara("teamId");
		TeamPlayers.syncTeamPlayers(teamId);
		renderJson(1);
	}
	
	//更新联赛球员
	public void updateLeaguePlayer(){
		String leagueId = getPara("leagueId");
		List<Team> lstTeam = Team.dao.find("select id from team where league_id = ?", leagueId);
		for(Team team : lstTeam){
			TeamPlayers.syncTeamPlayers(team.getStr("id"));
		}
		renderJson(1);
	}
	
	//更新网站地图
	public void updateSiteMap(){
		GeneratorUtils.generateSitemap();
		renderJson(1);
	}
	
	//更新直播源
	public void updateLives(){
		LiveZuqiula.getLiveSource();
		//Live24zbw.getLiveSource();
		Live5chajian.getLiveSource();
		renderJson(1);
	}
	
	//分析进几年来联赛情况
	public void analyzeAll(){
		AnalyzeZgzcw.getInstance().getLeagueOdds();
		renderJson(1);
	}
	
	//更新相同赔率
	public void updateSameOdds(){
		OddsUtils.initHistoryOdds();
		renderJson(1);
	}
	
	//更新球队排名
	public void updateTeamPosition(){
		TeamPosition.getPosition();
		renderJson(1);
	}
	
	//更新今年比赛odds_matches主客队id
	public void updateOddsMatches(){
		AnalyzeZgzcw.getInstance().setHomeAwayId();
		renderJson(1);
	}
	
	//分析比赛历史数据
	public void generateMatchStatic(){
		String result = AnalyzeZgzcw.getInstance().analyzeWinOrLose(this);
		Map<String, String> map = new HashMap<String, String>();
		map.put("data", result);
		renderJson(map);
	}
	
	//更新球员转会
	public void updatePlayerTransfer(){
		String playerId = getPara("playerId");
		Player player = Player.dao.findById(playerId);
		if(player!=null){
			HttpClient httpClient = new DefaultHttpClient();
			PlayerInfoPlugin.updateTransfer(httpClient, player);
			httpClient.getConnectionManager().shutdown();
		}
		renderJson(1);
	}
	
	//更新情报
	public void updateMatchNews(){
		News7M.crawl();
		TranslateUtils.translate();
		renderJson(1);
	}
}
