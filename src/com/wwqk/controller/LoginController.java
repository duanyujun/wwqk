package com.wwqk.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;

public class LoginController extends Controller {
	
	public void index(){
		render("login.jsp");
	}

	public void enter() {
		String username = getPara("username");
		String password = getPara("password");
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			currentUser.login(token);
			getSession().setAttribute("username", username);
			redirect("/home");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			// 登录失败
			forwardAction("/login");
		}
	}
	
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			currentUser.logout();
		}
		redirect("/login");
	}
	
}
