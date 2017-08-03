package com.testAuto.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;

/**
 * com.tools.auto 自动生成  GroupFunction
 * @author liujian
 *
 */
@Table("s_group_function")
public class GroupFunction {
	
	//field
	private String funId;
	private String appId;
	private String groupId;
	@PrimaryKey
	private String pid;
	
	//get set
	public String getFunId() {
		return funId;
	}
	public void setFunId(String funId) {
		this.funId = funId;
	}
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
		json.put("funId", funId);
		json.put("appId", appId);
		json.put("groupId", groupId);
		json.put("pid", pid);
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
			funId = json.getString("funId");
			appId = json.getString("appId");
			groupId = json.getString("groupId");
			pid = json.getString("pid");
		}
	}

	
}
