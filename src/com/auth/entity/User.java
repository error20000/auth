package com.auth.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;
import com.tools.annotation.Excel;

/**
 * com.tools.auto 自动生成  User
 * @author liujian
 *
 */
@Table("s_user")
public class User {
	
	//field
	@PrimaryKey
	@Excel(name="主键", sort=0 )
	private String pid;
	@Excel(name="登录名", sort=1 )
	private String username;
	@Excel(name="密码 （md5）", sort=2 )
	private String password;
	@Excel(name="第三方ID", sort=3 )
	private String thridId;
	@Excel(name="昵称", sort=4 )
	private String nick;
	@Excel(name="状态： 0 -- 禁用， 1--启用", sort=5, value="0" )
	private int status;
	@Excel(name="备注", sort=6 )
	private String info;
	@Excel(name="创建时间", sort=7 )
	private String createTime;
	@Excel(name="修改时间", sort=8 )
	private String updateTime;
	@Excel(name="管理员 0 -- 普通，1 --超级，2--其他，......", sort=9, value="0" )
	private int admin;
	
	//get set
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getThridId() {
		return thridId;
	}
	public void setThridId(String thridId) {
		this.thridId = thridId;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
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
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
	public String serialize() {
		JSONObject json = new JSONObject();
		//serialize
		json.put("pid", pid);
		json.put("username", username);
		json.put("password", password);
		json.put("thridId", thridId);
		json.put("nick", nick);
		json.put("status", status);
		json.put("info", info);
		json.put("createTime", createTime);
		json.put("updateTime", updateTime);
		json.put("admin", admin);
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
			pid = json.getString("pid");
			username = json.getString("username");
			password = json.getString("password");
			thridId = json.getString("thridId");
			nick = json.getString("nick");
			status = json.getIntValue("status");
			info = json.getString("info");
			createTime = json.getString("createTime");
			updateTime = json.getString("updateTime");
			admin = json.getIntValue("admin");
		}
	}

	
}
