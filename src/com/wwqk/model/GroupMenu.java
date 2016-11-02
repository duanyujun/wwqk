package com.wwqk.model;

import java.io.Serializable;
import java.util.List;

public class GroupMenu implements Serializable{

	private static final long serialVersionUID = 1L;

	private String groupName;
	
	private List<Permissions> lstMenu;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Permissions> getLstMenu() {
		return lstMenu;
	}

	public void setLstMenu(List<Permissions> lstMenu) {
		this.lstMenu = lstMenu;
	}
	
}
