package com.auth.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;
import com.tools.annotation.Excel;

/**
 * com.tools.auto 自动生成  Language
 * @author liujian
 *
 */
@Table("s_language")
public class Language {
	
	//field
	@PrimaryKey
	@Excel(name="主键", sort=0 )
	private String pid;
	@Excel(name="appId", sort=1 )
	private String appId;
	@Excel(name="名称", sort=2 )
	private String name;
	@Excel(name="标识符", sort=3 )
	private String marks;
	@Excel(name="状态： 0 -- 禁用， 1--启用", sort=4, value="0" )
	private int status;
	@Excel(name="创建时间", sort=5 )
	private String createTime;
	@Excel(name="修改时间", sort=6 )
	private String updateTime;
	
	//get set
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
		//serialize
		json.put("pid", pid);
		json.put("appId", appId);
		json.put("name", name);
		json.put("marks", marks);
		json.put("status", status);
		json.put("createTime", createTime);
		json.put("updateTime", updateTime);
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
			pid = json.getString("pid");
			appId = json.getString("appId");
			name = json.getString("name");
			marks = json.getString("marks");
			status = json.getIntValue("status");
			createTime = json.getString("createTime");
			updateTime = json.getString("updateTime");
		}
	}

	
}
