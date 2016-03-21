package com.wwqk.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.wwqk.model.User;

public class UserController extends Controller {

	public void index(){
		List<User> users = User.dao.find("select * from user");
		setAttr("users", users);
		System.err.println(users.size());
		render("list.jsp");
	}
	
	public void form(){
		render("form.jsp");
	}
	
	public void submit(){
		User user = getModel(User.class, "user");
		if(user.get("id")==null){
			user.save();
		}else{
			user.update();
		}
		redirect("/user");
	}
	
	public void edit(){
		String id = getPara(0);
		if(id!=null){
			User user = User.dao.findById(id);
			setAttr("user", user);
		}
		
		form();
	}
	
	public void del(){
		//index();
		String id = getPara(0);
		User.dao.deleteById(id);
		redirect("/user");
	}
	
}
