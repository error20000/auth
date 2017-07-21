package com.auth.entity;

import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;

@Table("s_group_menu_btn")
public class AuthGroupMenuBtn {
	
	private String pid;
	private String groupId;
	private String appId;
	private String menuId;
	private String btnId;
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getBtnId() {
		return btnId;
	}
	public void setBtnId(String btnId) {
		this.btnId = btnId;
	}
	
	
	
	public String serialize() {
		JSONObject json = new JSONObject();
		json.put("pid", pid);
		json.put("groupId", groupId);
		json.put("appId", appId);
		json.put("menuId", menuId);
		json.put("btnId", btnId);
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			pid = json.getString("pid");
			groupId = json.getString("groupId");
			appId = json.getString("appId");
			menuId = json.getString("menuId");
			btnId = json.getString("btnId");
		}
	}

	
}
