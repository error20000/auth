package com.testAuto.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;

/**
 * com.tools.auto 自动生成  Menu
 * @author liujian
 *
 */
@Table("s_menu")
public class Menu {
	
	//field
	private String updateTime;
	private String createTime;
	private int status;
	private String path;
	private String marks;
	private String name;
	private String parentId;
	private String appId;
	@PrimaryKey
	private String pid;
	
	//get set
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
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
		json.put("updateTime", updateTime);
		json.put("createTime", createTime);
		json.put("status", status);
		json.put("path", path);
		json.put("marks", marks);
		json.put("name", name);
		json.put("parentId", parentId);
		json.put("appId", appId);
		json.put("pid", pid);
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
			updateTime = json.getString("updateTime");
			createTime = json.getString("createTime");
			status = json.getInteger("status");
			path = json.getString("path");
			marks = json.getString("marks");
			name = json.getString("name");
			parentId = json.getString("parentId");
			appId = json.getString("appId");
			pid = json.getString("pid");
		}
	}

	
}
