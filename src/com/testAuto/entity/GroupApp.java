package com.testAuto.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;

/**
 * com.tools.auto 自动生成  GroupApp
 * @author liujian
 *
 */
@Table("s_group_app")
public class GroupApp {
	
	//field
	private String appId;
	private String groupId;
	@PrimaryKey
	private String pid;
	
	//get set
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String serialize() {
		JSONObject json = new JSONObject();
		//serialize
		json.put("appId", appId);
		json.put("groupId", groupId);
		json.put("pid", pid);
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
			appId = json.getString("appId");
			groupId = json.getString("groupId");
			pid = json.getString("pid");
		}
	}

	
}
