package com.auth.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;
import com.tools.annotation.Excel;

/**
 * com.tools.auto 自动生成  Group
 * @author liujian
 *
 */
@Table("s_group")
public class Group {
	
	//field
	@PrimaryKey
	@Excel(name="主键", sort=0 )
	private String pid;
	@Excel(name="名称", sort=1 )
	private String name;
	@Excel(name="标识符", sort=2 )
	private String marks;
	@Excel(name="状态： 0 -- 禁用， 1--启用", sort=3, value="0" )
	private int status;
	@Excel(name="备注", sort=4 )
	private String info;
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
		//serialize
		json.put("pid", pid);
		json.put("name", name);
		json.put("marks", marks);
		json.put("status", status);
		json.put("info", info);
		json.put("createTime", createTime);
		json.put("updateTime", updateTime);
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
			pid = json.getString("pid");
			name = json.getString("name");
			marks = json.getString("marks");
			status = json.getIntValue("status");
			info = json.getString("info");
			createTime = json.getString("createTime");
			updateTime = json.getString("updateTime");
		}
	}

	
}
