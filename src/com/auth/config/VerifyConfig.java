package com.auth.config;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tools.jdbc.PrimaryKeyCondition;
import com.tools.utils.AccessTokenTools;
import com.tools.utils.ResultKey;
import com.tools.utils.Tips;
import com.tools.utils.Tools;

/**
 * com.tools.auto 自动生成  VerifyConfig
 * @author liujian
 *
 */
public class VerifyConfig {
	
	private static String uidName = CtrlConfig.uidName; //登录验证的uid名称
	private static String tokenName = CtrlConfig.tokenName; //登录验证的token名称
	
	//登录验证（公有）
	private static Map<String, Object> verifyLogin(HttpServletRequest req, Class<?> clss){
		//TODO 根据需要自己修改验证
		String uid = Tools.isNullOrEmpty(req.getHeader(uidName)) ? Tools.getReqParamSafe(req, uidName) : req.getHeader(uidName); 
		String token = Tools.isNullOrEmpty(req.getHeader(tokenName)) ? Tools.getReqParamSafe(req, tokenName) : req.getHeader(tokenName); 
		Map<String, Object> vMap = Tools.verifyParam(uidName, uid, 0, 0);
		if(vMap != null){
			return vMap;
		}
		vMap = Tools.verifyParam(tokenName, token, 0, 0);
		if(vMap != null){
			return vMap;
		}
		if(!AccessTokenTools.checkToken(uid, token)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(ResultKey.CODE, Tips.ERROR111.getCode());
			map.put(ResultKey.MSG, Tips.ERROR111.getDesc());
			return map;
		}
//		user = (User) AccessTokenTools.getToken(uid).getValue(); //登录用户
		
		return null;
	}
	
	//sign验证（公有）
	private static Map<String, Object> verifySign(HttpServletRequest req, Class<?> clss){
		//TODO 根据需要自己修改验证
		
		return null;
	}
	
	//保存验证
	public static Map<String, Object> verifySave(HttpServletRequest req, Class<?> clss){
		//TODO 根据需要自己修改验证
		Map<String, Object> vMap = null;
		//sign
		if(CtrlConfig.saveSign){
			vMap = verifySign(req, clss);
			if(vMap != null){
				return vMap;
			}
		}
		
		return CtrlConfig.saveLogin ? verifyLogin(req, clss) : null;
	}
	
	//修改验证
	public static Map<String, Object> verifyModify(HttpServletRequest req, Class<?> clss){
		//TODO 根据需要自己修改验证
		//获取主键
		List<PrimaryKeyCondition> pkeys = Tools.getPrimaryKeys(clss);//获取主键
		Map<String, Object> vMap = null;
		for (PrimaryKeyCondition pkc : pkeys) {
			String pkName = pkc.getField();
			String pkValue = Tools.getReqParamSafe(req, pkName);
			vMap = Tools.verifyParam(pkName, pkValue, 0, 0);
			if(vMap != null){
				return vMap;
			}
		}
		//sign
		if(CtrlConfig.modifySign){
			vMap = verifySign(req, clss);
			if(vMap != null){
				return vMap;
			}
		}
		
		return CtrlConfig.modifyLogin ? verifyLogin(req, clss) : null;
	}
	
	//删除验证
	public static Map<String, Object> verifyDelete(HttpServletRequest req, Class<?> clss){
		//TODO 根据需要自己修改验证
		//获取主键
		List<PrimaryKeyCondition> pkeys = Tools.getPrimaryKeys(clss);//获取主键
		Map<String, Object> vMap = null;
		for (PrimaryKeyCondition pkc : pkeys) {
			String pkName = pkc.getField();
			String pkValue = Tools.getReqParamSafe(req, pkName);
			vMap = Tools.verifyParam(pkName, pkValue, 0, 0);
			if(vMap != null){
				return vMap;
			}
		}
		//sign
		if(CtrlConfig.deleteSign){
			vMap = verifySign(req, clss);
			if(vMap != null){
				return vMap;
			}
		}
		
		return CtrlConfig.deleteLogin ? verifyLogin(req, clss) : null;
	}

	
	//查询验证（query、list）
	public static Map<String, Object> verifyQuery(HttpServletRequest req, Class<?> clss){
		//TODO 根据需要自己修改验证
		Map<String, Object> vMap = null;
		//sign
		if(CtrlConfig.querySign){
			vMap = verifySign(req, clss);
			if(vMap != null){
				return vMap;
			}
		}
		
		return CtrlConfig.queryLogin ? verifyLogin(req, clss) : null;
	}
	
	//查询验证（find、findOne）
	public static Map<String, Object> verifyFind(HttpServletRequest req, Class<?> clss){
		//TODO 根据需要自己修改验证
		Map<String, Object> vMap = null;
		//sign
		if(CtrlConfig.findSign){
			vMap = verifySign(req, clss);
			if(vMap != null){
				return vMap;
			}
		}
		
		return CtrlConfig.findLogin ? verifyLogin(req, clss) : null;
	}
	
	
	//excel验证（excel、excelHeader、excelFree）
	public static Map<String, Object> verifyExcel(HttpServletRequest req, Class<?> clss){
		//TODO 根据需要自己修改验证
		Map<String, Object> vMap = null;
		//sign
		if(CtrlConfig.exceSign){
			vMap = verifySign(req, clss);
			if(vMap != null){
				return vMap;
			}
		}
		
		return CtrlConfig.excelLogin ? verifyLogin(req, clss) : null;
	}
	
}
