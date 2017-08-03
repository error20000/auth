package com.testAuto.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;

/**
 * com.tools.auto 自动生成  User
 * @author liujian
 *
 */
@Table("s_user")
public class User {
	
	//field
	private int admin;
	private String updateTime;
	private String createTime;
	private String info;
	private int status;
	private String nick;
	private String thridId;
	private String password;
	private String username;
	@PrimaryKey
	private String pid;
	
	//get set
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
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
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getThridId() {
		return thridId;
	}
	public void setThridId(String thridId) {
		this.thridId = thridId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
		json.put("admin", admin);
		json.put("updateTime", updateTime);
		json.put("createTime", createTime);
		json.put("info", info);
		json.put("status", status);
		json.put("nick", nick);
		json.put("thridId", thridId);
		json.put("password", password);
		json.put("username", username);
		json.put("pid", pid);
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
			admin = json.getInteger("admin");
			updateTime = json.getString("updateTime");
			createTime = json.getString("createTime");
			info = json.getString("info");
			status = json.getInteger("status");
			nick = json.getString("nick");
			thridId = json.getString("thridId");
			password = json.getString("password");
			username = json.getString("username");
			pid = json.getString("pid");
		}
	}

	
}
