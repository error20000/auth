package com.auth.entity;

import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;

@Table("s_user_group")
public class AuthUserGroup {
	
	private String pid;
	private String userId;
	private String groupId;
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	
	public String serialize() {
		JSONObject json = new JSONObject();
		json.put("pid", pid);
		json.put("userId", userId);
		json.put("groupId", groupId);
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			pid = json.getString("pid");
			userId = json.getString("userId");
			groupId = json.getString("groupId");
		}
	}

	
}
