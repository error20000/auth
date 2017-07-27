package com.auth.entity;

import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.PrimaryKey;
import com.tools.jdbc.Table;

@Table("s_menu_btn")
public class MenuBtn {

	@PrimaryKey
	private String pid;
	private String appId;
	private String menuId;
	private String name;
	private String marks;
	private String path;
	private int status;
	private String createTime;
	private String updateTime;
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String serialize() {
		JSONObject json = new JSONObject();
		json.put("pid", pid);
		json.put("appId", appId);
		json.put("menuId", menuId);
		json.put("name", name);
		json.put("marks", marks);
		json.put("marks", marks);
		json.put("path", path);
		json.put("createTime", createTime);
		json.put("updateTime", updateTime);
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			pid = json.getString("pid");
			appId = json.getString("appId");
			menuId = json.getString("menuId");
			name = json.getString("name");
			marks = json.getString("marks");
			path = json.getString("path");
			status = json.getInteger("status");
			createTime = json.getString("createTime");
			updateTime = json.getString("updateTime");
		}
	}
	
}
