package com.auth.entity;

//import
import com.tools.jdbc.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;
import com.tools.annotation.Excel;

/**
 * com.tools.auto 自动生成  GroupMenuBtn
 * @author liujian
 *
 */
@Table("s_group_menu_btn")
public class GroupMenuBtn {
	
	//field
	@PrimaryKey
	@Excel(name="主键", sort=0 )
	private String pid;
	@Excel(name="分组", sort=1 )
	private String groupId;
	@Excel(name="应用", sort=2 )
	private String appId;
	@Excel(name="菜单", sort=3 )
	private String menuId;
	@Excel(name="菜单按钮", sort=4 )
	private String btnId;
	
	//get set
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getBtnId() {
		return btnId;
	}
	public void setBtnId(String btnId) {
		this.btnId = btnId;
	}
	
	public String serialize() {
		JSONObject json = new JSONObject();
		//serialize
		json.put("pid", pid);
		json.put("groupId", groupId);
		json.put("appId", appId);
		json.put("menuId", menuId);
		json.put("btnId", btnId);
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
			pid = json.getString("pid");
			groupId = json.getString("groupId");
			appId = json.getString("appId");
			menuId = json.getString("menuId");
			btnId = json.getString("btnId");
		}
	}

	
}
