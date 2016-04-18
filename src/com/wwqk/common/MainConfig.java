package com.wwqk.common;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.wwqk.controller.IndexController;
import com.wwqk.controller.UserController;
import com.wwqk.model.Career;
import com.wwqk.model.Injury;
import com.wwqk.model.League;
import com.wwqk.model.LeagueAssists;
import com.wwqk.model.LeagueMatch;
import com.wwqk.model.LeaguePosition;
import com.wwqk.model.LeagueShooter;
import com.wwqk.model.Player;
import com.wwqk.model.Team;
import com.wwqk.model.Transfer;
import com.wwqk.model.Trophy;
import com.wwqk.plugin.QuartzPlugin;


public class MainConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		me.setViewType(ViewType.JSP);
		PropKit.use("config.properties");
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add("/", IndexController.class);
		me.add("/user", UserController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.setShowSql(true);
		arp.addMapping("league", League.class);
		arp.addMapping("team", Team.class);
		arp.addMapping("player", Player.class);
		arp.addMapping("career", Career.class);
		arp.addMapping("injury", Injury.class);
		arp.addMapping("league_assists", LeagueAssists.class);
		arp.addMapping("league_position", LeaguePosition.class);
		arp.addMapping("league_shooter", LeagueShooter.class);
		arp.addMapping("league_match", LeagueMatch.class);
		arp.addMapping("transfer", Transfer.class);
		arp.addMapping("trophy", Trophy.class);
		
		me.add(c3p0Plugin);
		me.add(arp);
		
		QuartzPlugin quartzPlugin =  new QuartzPlugin("job.properties");
	    me.add(quartzPlugin);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configHandler(Handlers me) {
		

	}
	
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}

}



