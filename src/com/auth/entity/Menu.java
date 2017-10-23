package com.auth.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;
import com.tools.annotation.Excel;

/**
 * com.tools.auto 自动生成  Menu
 * @author liujian
 *
 */
@Table("s_menu")
public class Menu {
	
	//field
	@PrimaryKey
	@Excel(name="主键", sort=0 )
	private String pid;
	@Excel(name="appId", sort=1 )
	private String appId;
	@Excel(name="父级ID", sort=2 )
	private String parentId;
	@Excel(name="名称", sort=3 )
	private String name;
	@Excel(name="标识符", sort=4 )
	private String marks;
	@Excel(name="路径", sort=5 )
	private String path;
	@Excel(name="状态： 0 -- 禁用， 1--启用", sort=6, value="0" )
	private int status;
	@Excel(name="创建时间", sort=7 )
	private String createTime;
	@Excel(name="修改时间", sort=8 )
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
		json.put("parentId", parentId);
		json.put("name", name);
		json.put("marks", marks);
		json.put("path", path);
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
			parentId = json.getString("parentId");
			name = json.getString("name");
			marks = json.getString("marks");
			path = json.getString("path");
			status = json.getIntValue("status");
			createTime = json.getString("createTime");
			updateTime = json.getString("updateTime");
		}
	}

	
}
