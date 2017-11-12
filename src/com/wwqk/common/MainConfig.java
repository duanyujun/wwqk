package com.wwqk.common;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.JspRender;
import com.jfinal.render.ViewType;
import com.wwqk.controller.AdminController;
import com.wwqk.controller.BifenController;
import com.wwqk.controller.DataController;
import com.wwqk.controller.FunController;
import com.wwqk.controller.HomeController;
import com.wwqk.controller.IndexController;
import com.wwqk.controller.LiveController;
import com.wwqk.controller.LoginController;
import com.wwqk.controller.ManageController;
import com.wwqk.controller.MatchController;
import com.wwqk.controller.MatchHistoryController;
import com.wwqk.controller.PermissionController;
import com.wwqk.controller.RoleController;
import com.wwqk.controller.SayController;
import com.wwqk.controller.TeamController;
import com.wwqk.controller.UploadController;
import com.wwqk.controller.UserController;
import com.wwqk.controller.VideosController;
import com.wwqk.model.AllLiveMatch;
import com.wwqk.model.Article;
import com.wwqk.model.Career;
import com.wwqk.model.Coach;
import com.wwqk.model.CoachCareer;
import com.wwqk.model.CoachTrophy;
import com.wwqk.model.Fun;
import com.wwqk.model.Injury;
import com.wwqk.model.League;
import com.wwqk.model.LeagueAssists;
import com.wwqk.model.LeagueAssists163;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.LeagueShooter;
import com.wwqk.model.LeagueShooter163;
import com.wwqk.model.MatchLive;
import com.wwqk.model.MatchSource;
import com.wwqk.model.MatchSourceSina;
import com.wwqk.model.OddsAH;
import com.wwqk.model.OddsBet365;
import com.wwqk.model.OddsBwin;
import com.wwqk.model.OddsEuro;
import com.wwqk.model.OddsLB;
import com.wwqk.model.OddsML;
import com.wwqk.model.OddsMatches;
import com.wwqk.model.OddsSource;
import com.wwqk.model.OddsWH;
import com.wwqk.model.Permissions;
import com.wwqk.model.Player;
import com.wwqk.model.Roles;
import com.wwqk.model.RolesPermissions;
import com.wwqk.model.Say;
import com.wwqk.model.ShooterAssistsSource;
import com.wwqk.model.Team;
import com.wwqk.model.TipsAll;
import com.wwqk.model.TipsMatch;
import com.wwqk.model.Transfer;
import com.wwqk.model.Trophy;
import com.wwqk.model.Users;
import com.wwqk.model.UsersRoles;
import com.wwqk.model.Videos;
import com.wwqk.model.VideosRealLinks;
import com.wwqk.plugin.QuartzPlugin;

public class MainConfig extends JFinalConfig {
	
	private Routes routes;

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		me.setViewType(ViewType.JSP);
		PropKit.use("config.properties");
		JspRender.setSupportActiveRecord(true);
		me.setErrorView(401, "/web/error.jsp");
		me.setErrorView(403, "/web/error.jsp");
		me.setError404View("/web/error.jsp");
		me.setError500View("/web/error.jsp");
	}

	@Override
	public void configRoute(Routes me) {
		this.routes = me;
		
		me.add("/", IndexController.class, "web");
		me.add("/match", MatchController.class, "web");
		me.add("/data", DataController.class, "web");
		me.add("/say", SayController.class, "web");
		me.add("/team", TeamController.class, "web");
		me.add("/fun", FunController.class, "web");
		me.add("/login", LoginController.class);
		me.add("/user", UserController.class, "web");
		me.add("/role", RoleController.class, "web");
		me.add("/permission", PermissionController.class, "web");
		me.add("/home", HomeController.class, "web");
		me.add("/admin",AdminController.class, "web");
		me.add("/history",MatchHistoryController.class, "web");
		me.add("/manage",ManageController.class, "web");
		me.add("/upload", UploadController.class, "web");
		me.add("/live", LiveController.class, "web");
		me.add("/bifen",BifenController.class, "web");
		me.add("/videos",VideosController.class, "web");
		
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.setShowSql(false);
		arp.addMapping("league", League.class);
		arp.addMapping("team", Team.class);
		arp.addMapping("player", Player.class);
		arp.addMapping("career", Career.class);
		arp.addMapping("injury", Injury.class);
		arp.addMapping("league_assists", LeagueAssists.class);
		arp.addMapping("league_position", LeaguePosition.class);
		arp.addMapping("league_shooter", LeagueShooter.class);
		arp.addMapping("league_match", LeagueMatch.class);
		arp.addMapping("league_match_history", LeagueMatchHistory.class);
		arp.addMapping("transfer", Transfer.class);
		arp.addMapping("trophy", Trophy.class);
		arp.addMapping("coach", Coach.class);
		arp.addMapping("coach_trophy", CoachTrophy.class);
		arp.addMapping("coach_career", CoachCareer.class);
		arp.addMapping("fun", Fun.class);
		arp.addMapping("say", Say.class);
		arp.addMapping("users", Users.class);
		arp.addMapping("roles", Roles.class);
		arp.addMapping("permissions", Permissions.class);
		arp.addMapping("user_roles", UsersRoles.class);
		arp.addMapping("roles_permissions", RolesPermissions.class);
		arp.addMapping("league_shooter_163", LeagueShooter163.class);
		arp.addMapping("league_assists_163", LeagueAssists163.class);
		arp.addMapping("shooter_assists_source", ShooterAssistsSource.class);
		arp.addMapping("league_match_history", LeagueMatchHistory.class);
		arp.addMapping("match_source", MatchSource.class);
		arp.addMapping("match_live", MatchLive.class);
		arp.addMapping("match_source_sina", MatchSourceSina.class);
		
		arp.addMapping("odds_wh", OddsWH.class);
		arp.addMapping("odds_bet365", OddsBet365.class);
		arp.addMapping("odds_lb", OddsLB.class);
		arp.addMapping("odds_ml", OddsML.class);
		arp.addMapping("odds_bwin", OddsBwin.class);
		
		arp.addMapping("odds_source", OddsSource.class);
		arp.addMapping("odds_matches", OddsMatches.class);
		arp.addMapping("odds_euro", OddsEuro.class);
		arp.addMapping("odds_ah", OddsAH.class);
		arp.addMapping("all_live_match", AllLiveMatch.class);
		arp.addMapping("article", Article.class);
		arp.addMapping("tips_match", TipsMatch.class);
		arp.addMapping("tips_all", TipsAll.class);

		arp.addMapping("videos", Videos.class);
		arp.addMapping("videos_real_links", VideosRealLinks.class);
		
		me.add(c3p0Plugin);
		me.add(arp);
		
		QuartzPlugin quartzPlugin =  new QuartzPlugin("job.properties");
	    me.add(quartzPlugin);
	    
	    ShiroPlugin shiroPlugin = new ShiroPlugin(routes);
		shiroPlugin.setLoginUrl("/web/login.jsp");
		shiroPlugin.setUnauthorizedUrl("/web/login.jsp");
		shiroPlugin.setSuccessUrl("/home");
		me.add(shiroPlugin);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new ShiroInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		

	}
	
	public static void main(String[] args) {
		JFinal.start("WebRoot", 9999, "/", 5);
	}

}



