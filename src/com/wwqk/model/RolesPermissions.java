package com.wwqk.model;

import java.util.Collection;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 角色——权限
 * 
 * @author java
 * 
 */
public class RolesPermissions extends Model<RolesPermissions> {
	private static final long serialVersionUID = 2920278340134539131L;

	public static RolesPermissions dao = new RolesPermissions();

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
