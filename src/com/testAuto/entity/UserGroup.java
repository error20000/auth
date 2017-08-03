package com.testAuto.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;

/**
 * com.tools.auto 自动生成  UserGroup
 * @author liujian
 *
 */
@Table("s_user_group")
public class UserGroup {
	
	//field
	private String groupId;
	private String userId;
	@PrimaryKey
	private String pid;
	
	//get set
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
		json.put("groupId", groupId);
		json.put("userId", userId);
		json.put("pid", pid);
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
			groupId = json.getString("groupId");
			userId = json.getString("userId");
			pid = json.getString("pid");
		}
	}

	
}
