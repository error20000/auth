package com.auth.entity;

import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.PrimaryKey;
import com.tools.jdbc.Table;

@Table("s_app")
public class App {
	
	@PrimaryKey
	private String pid;
	private String name;
	private String marks;
	private String secretKey;
	private int status;
	private String info;
	private String createTime;
	private String updateTime;
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
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
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
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
	
	public String serialize() {
		JSONObject json = new JSONObject();
		json.put("pid", pid);
		json.put("name", name);
		json.put("marks", marks);
		json.put("secretKey", secretKey);
		json.put("status", status);
		json.put("info", info);
		json.put("createTime", createTime);
		json.put("updateTime", updateTime);
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			pid = json.getString("pid");
			name = json.getString("name");
			marks = json.getString("marks");
			secretKey = json.getString("secretKey");
			status = json.getInteger("status");
			info = json.getString("info");
			createTime = json.getString("createTime");
			updateTime = json.getString("updateTime");
		}
	}

	
}
