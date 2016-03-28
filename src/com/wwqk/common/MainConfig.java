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
import com.wwqk.model.User;
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
		arp.addMapping("user", User.class);
		me.add(c3p0Plugin);
		me.add(arp);
		
//		ConfigPlugin configPlugin = new ConfigPlugin();
//		configPlugin.addResource("job.properties");
//		me.add(new QuartzPlugin());
		
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



