package com.auth.entity;

import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.PrimaryKey;
import com.tools.jdbc.Table;

@Table("s_user")
public class User {
	
	@PrimaryKey
	private String pid;
	private String username;
	private String password;
	private String thridId;
	private String nick;
	private int status;
	private String info;
	private String createTime;
	private String updateTime;
	private int admin;
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getThridId() {
		return thridId;
	}
	public void setThridId(String thridId) {
		this.thridId = thridId;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
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
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
	
	public String serialize() {
		JSONObject json = new JSONObject();
		json.put("pid", pid);
		json.put("username", username);
		json.put("password", password);
		json.put("thridId", thridId);
		json.put("nick", nick);
		json.put("status", status);
		json.put("info", info);
		json.put("createTime", createTime);
		json.put("updateTime", updateTime);
		json.put("admin", admin);
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			pid = json.getString("pid");
			username = json.getString("username");
			password = json.getString("password");
			thridId = json.getString("thridId");
			nick = json.getString("nick");
			status = json.getInteger("status");
			info = json.getString("info");
			createTime = json.getString("createTime");
			updateTime = json.getString("updateTime");
			admin = json.getInteger("admin");
		}
	}

	
}
