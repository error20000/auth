package com.auth.entity;

import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;

@Table("s_group_language")
public class AuthGroupLanguage {
	
	private String pid;
	private String groupId;
	private String appId;
	private String langId;
	
	
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
	public String getLangId() {
		return langId;
	}
	public void setLangId(String langId) {
		this.langId = langId;
	}
	
	
	
	public String serialize() {
		JSONObject json = new JSONObject();
		json.put("pid", pid);
		json.put("groupId", groupId);
		json.put("appId", appId);
		json.put("langId", langId);
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			pid = json.getString("pid");
			groupId = json.getString("groupId");
			appId = json.getString("appId");
			langId = json.getString("langId");
		}
	}

	
}
