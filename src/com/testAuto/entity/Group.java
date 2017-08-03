package com.testAuto.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;

/**
 * com.tools.auto 自动生成  Group
 * @author liujian
 *
 */
@Table("s_group")
public class Group {
	
	//field
	private String updateTime;
	private String createTime;
	private String info;
	private int status;
	private String marks;
	private String name;
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
		json.put("info", info);
		json.put("status", status);
		json.put("marks", marks);
		json.put("name", name);
		json.put("pid", pid);
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
			updateTime = json.getString("updateTime");
			createTime = json.getString("createTime");
			info = json.getString("info");
			status = json.getInteger("status");
			marks = json.getString("marks");
			name = json.getString("name");
			pid = json.getString("pid");
		}
	}

	
}
