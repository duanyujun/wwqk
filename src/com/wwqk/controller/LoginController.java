package com.wwqk.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;
import com.wwqk.utils.StringUtils;

public class LoginController extends Controller {
	
	public void index(){
		render("login.jsp");
	}

	public void enter() throws InterruptedException {
		if(!login(getPara("username"), getPara("password1"))){
			redirect("/login");
			return;
		}
		Thread.sleep(1000);
		redirect("/home");
	}
	
	public void iosLogin(){
		Map<String, String> result = new HashMap<String, String>();
		if(!login(getPara("username"), getPara("password"))){
			result.put("success", "0");
		}else{
			result.put("success", "1");
		}
		renderJson(result);
	}
	
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			currentUser.logout();
		}
		redirect("/home");
	}
	
	private boolean login(String username, String password){
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			return false;
		}
		boolean isLogin = true;
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password.toLowerCase());
		try {
			currentUser.login(token);
			getSession().setAttribute("username", username);
			if(getSession().getAttribute("loginError")!=null){
				getSession().removeAttribute("loginError");
			}
		} catch (Exception e) {
			// 登录失败
			isLogin = false;
			getSession().setAttribute("loginError", "loginError");
		}
		
		return isLogin;
	}
}
