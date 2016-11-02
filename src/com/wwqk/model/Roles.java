package com.wwqk.model;

import java.util.Collection;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 角色
 * 
 * @author java
 * 
 */
public class Roles extends Model<Roles> {
	private static final long serialVersionUID = 2920278340134539131L;

	public static Roles dao = new Roles();
	
	public Map<String, Object> getAttrs(){
	    return super.getAttrs();
	}

	/**
	 * 权限
	 * 
	 * @return
	 */
	public Collection<String> getPermissionNameList() {
		int roleId = this.getInt("id");
		return Db.query("SELECT p.name FROM permissions p LEFT JOIN `roles_permissions` rp on p.id=rp.permission_id where rp.role_id=?", roleId);
	}

}
