package com.wwqk.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.Roles;
import com.wwqk.model.Users;
import com.wwqk.model.UsersRoles;
import com.wwqk.utils.StringUtils;

public class UserController extends Controller {

	public void index(){
		render("user/userList.jsp");
	}
	
	public void list(){
		String sumSql = "select count(*) from users";
		String sql = "select * from users";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search =search.replaceAll("'", "").trim();
			whereSql = " where username like '%"+search+"%'"+" OR name like '%"+search+"%'"+" OR mobile_no like '%"+search+"%'"+" OR qq like '%"+search+"%'"+" OR email like '%"+search+"%'";
		}
		
		int sortColumn = getParaToInt("order[0][column]");
		String sortType = getPara("order[0][dir]");
		switch (sortColumn) {
		case 1:
			orderSql = " order by username "+sortType;
			break;
		case 2:
			orderSql = " order by name "+sortType;
			break;
		case 3:
			orderSql = " order by mobile_no "+sortType;
			break;
		case 4:
			orderSql = " order by qq "+sortType;
			break;
		case 5:
			orderSql = " order by email "+sortType;
			break;
		case 6:
			orderSql = " order by ustatus "+sortType;
			break;
		default:
			break;
		}
		
		int start = getParaToInt("start");
		int length = getParaToInt("length");
		if(length!=-1){
			limitSql = " limit "+start+","+length;
		}
		Long recordsTotal = Db.queryLong(sumSql+whereSql);
		List<Users> lstUsers = new ArrayList<Users>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstUsers = Users.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstUsers.size()];
			for(int i=0; i<lstUsers.size(); i++){
				Object[] obj = new Object[7];
				Users users = lstUsers.get(i);
				obj[0] = users.get("id");
				obj[1] = users.get("username");
				obj[2] = users.get("name");
				obj[3] = users.get("mobile_no");
				obj[4] = users.get("qq");
				obj[5] = users.get("email");
				if(users.getInt("ustatus")==0){
					obj[6] = "未激活";
				}else if(users.getInt("ustatus")==1){
					obj[6] = "已激活";
				}else if(users.getInt("ustatus")==2){
					obj[6] = "已注销";
				}
					
				data[i] = obj;
			}
		}
		if(data==null){
			data = new Object[0];
		}
		
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("draw", getParaToInt("draw"));
		map.put("recordsTotal", recordsTotal);
		map.put("recordsFiltered", recordsTotal);
		map.put("data", data);
		
		renderJson(map);
	}
	
	public void submit(){
		Users user = getModel(Users.class, "user");
		if(user.get("id")==null){
			user.save();
		}else{
			user.update();
		}
		redirect("/user");
	}
	
	public void edit(){
		String id = getPara("id");
		if(id!=null){
			Users user = Users.dao.findById(id);
			setAttr("user", user);
			//查询角色
			List<Roles> roleList = Roles.dao.find("SELECT r.* FROM roles r LEFT JOIN `user_roles` ur on ur.role_id=r.id where ur.user_id=?", user.get("id").toString());
			if(roleList.size()>0){
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<roleList.size(); i++){
					sb.append(roleList.get(i).getInt("id")).append(",");
				}
				if(sb.length()>0){
					sb.deleteCharAt(sb.length()-1);
				}
				setAttr("existRoleIds", sb.toString());
			}
		}
		List<Roles> lstRoles = Roles.dao.find("select * from roles");
		setAttr("lstRoles", lstRoles);
		render("user/userForm.jsp");
	}
	
	@Before(Tx.class)
	public void save() throws UnsupportedEncodingException{
		String username = StringUtils.decode(getPara("username"));
		String password = getPara("password");
		String name = StringUtils.decode(getPara("name"));
		String mobile_no = getPara("mobile_no");
		String qq = getPara("qq");
		String email = getPara("email");
		String remark = StringUtils.decode(getPara("remark"));
		int ustatus = getParaToInt("ustatus");
		Users users = new Users();
		users.set("username", username);
		users.set("name",name);
		users.set("mobile_no",mobile_no);
		users.set("qq",qq);
		users.set("email",email);
		users.set("remark",remark);
		users.set("ustatus", ustatus);
		String roleIds = getPara("roleIds");
		
		if(StringUtils.isNotBlank(getPara("id"))){
			Users userDB = Users.dao.findById(getPara("id"));
			//如果传过来的密码和数据库中的密码不一致，则进行md5
			if(!userDB.getStr("password").equals(password)){
				password = StringUtils.md5(password);
			}else{
				//如果传过来的密码和数据库中的md5一致，说明用户并没有修改，不需要再次md5
				//do nothing
			}
			users.set("password", password);
			users.set("id", getPara("id"));
			users.set("password", userDB.getStr("password"));
			users.update();
		}else{
			//如果新建用户，md5
			users.set("password", StringUtils.md5(password));
			users.save();
		}
		
		if(StringUtils.isNotBlank(roleIds)){
			List<UsersRoles> lstUsersRoles = new ArrayList<UsersRoles>();
			String[] roleArray = roleIds.split(",");
			for(int i=0; i<roleArray.length; i++){
				UsersRoles usersRoles = new UsersRoles();
				usersRoles.set("user_id", users.get("id"));
				usersRoles.set("role_id", roleArray[i]);
				lstUsersRoles.add(usersRoles);
			}
			if(lstUsersRoles.size()!=0){
				Db.update("delete from user_roles where user_id = ?", users.getStr("id"));
				Db.batchSave(lstUsersRoles, lstUsersRoles.size());
			}
		}
		
		renderJson(1);
	}
	
	public void del(){
		String ids = getPara("ids");
		if(StringUtils.isNotBlank(ids)){
			String whereSql = " where id in (" + ids +")";
			Db.update("delete from users "+whereSql);
			renderJson(1);
		}else{
			renderJson(0);
		}
	}
}
