package PK;

//import
import com.alibaba.fastjson.JSONObject;
import com.tools.jdbc.Table;

/**
 * com.tools.auto 自动生成  Temp
 * @author liujian
 *
 */
@Table("{Table_Name}")
public class Temp {
	
	//field
	
	//get set
	
	public String serialize() {
		JSONObject json = new JSONObject();
		//serialize
		
		return json.toString();
	}
	
	public void unserialize(String str) {
		JSONObject json = JSONObject.parseObject(str);
		if (json != null) {
			//unserialize
		}
	}

	
}
