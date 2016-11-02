package com.wwqk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;
import com.wwqk.constants.RolesConstants;
import com.wwqk.model.GroupMenu;
import com.wwqk.model.Permissions;
import com.wwqk.utils.StringUtils;

public class HomeController extends Controller {

	//@RequiresRoles(value = { RolesConstants.ROLE_USER})
	public void index() {
		
		Subject subject = SecurityUtils.getSubject();
		String username = (String)subject.getPrincipal(); 
		if(StringUtils.isNotBlank(username)){
			String sql = "SELECT * FROM permissions WHERE id IN (SELECT rp.permission_id FROM roles_permissions rp WHERE rp.role_id IN (SELECT ur.role_id FROM user_roles ur, users u  WHERE ur.user_id = u.id AND u.username = '"+username+"')) ORDER BY id ASC";
			List<Permissions> lstPermissions = Permissions.dao.find(sql);
			Map<String, List<Permissions>> menuMap = new HashMap<String, List<Permissions>>();
			Map<String, String> idNameMap = new HashMap<String, String>();
			Map<String, String> nameIdMap = new HashMap<String, String>();
			List<Integer> lstId = new ArrayList<Integer>();
			for(Permissions permissions : lstPermissions){
				if(permissions.getInt("pid")==0){
					if(idNameMap.get(permissions.getInt("id"))==null){
						lstId.add(permissions.getInt("id"));
						idNameMap.put(permissions.getInt("id")+"", permissions.getStr("name"));
						nameIdMap.put(permissions.getStr("name"), permissions.getInt("id")+"");
						List<Permissions> lstMenu = new ArrayList<Permissions>();
						menuMap.put(permissions.getStr("name"), lstMenu);
					}
				}else{
					String name = idNameMap.get(permissions.getInt("pid")+"");
					menuMap.get(name).add(permissions);
				}
			}
			
			List<GroupMenu> groupMenuList = new ArrayList<GroupMenu>();
			//排序
			for(Integer id : lstId){
				String groupName = idNameMap.get(id.toString());
				GroupMenu groupMenu = new GroupMenu();
				groupMenu.setGroupName(groupName);
				groupMenu.setLstMenu(menuMap.get(groupName));
				groupMenuList.add(groupMenu);
			}
			
			//setAttr("groupMenuList", groupMenuList);
			getSession().setAttribute("groupMenuList", groupMenuList);
		}
		
		render("home.jsp");
	}
	
}
