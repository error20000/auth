package com.tools.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

public class LogsTool {

	
	/**
	 * 写日志，保存为文件
	 * @param context
	 * @param path
	 * @param log
	 */
	public static void logSet(HttpServletRequest req, String path, String log) {
		String base = req.getServletContext().getRealPath("/");
		File file = new File(base+File.separator+"logs"+ File.separator+path+File.separator+Tools.formatDate(null, "yyyyMMdd")+".txt");
		logSet(file, log);
	}
	
	/**
	 * 写文件日志
	 * @param path
	 * @param log
	 * @text 
	 * Utils.class.getClassLoader().getResource("/").toString()
	 * windows file:/E:/Workspaces/MyEclipse%20Professional%202014/.metadata/.me_tcat7/webapps/sitesnqx/WEB-INF/classes/
	 * linux file:/home/admin/website-tomcat-7.0.42/webapps/sitesnqx/WEB-INF/classes/
	 * 
	 * Utils.class.getClassLoader().getResource("/").getPath()
	 * windows E:/Workspaces/MyEclipse%20Professional%202014/.metadata/.me_tcat7/webapps/sitesnqx/WEB-INF/classes/
	 * linux /home/admin/website-tomcat-7.0.42/webapps/sitesnqx/WEB-INF/classes/
	 * 
	 * /build/classes  eclipse
	 * /WEB-INF/classes  myeclipse
	 * 
	 */
	public static void logSet(String path, String log) {
		String base = Tools.class.getResource("/").getPath().replace("/build/classes/", "").replace("/WEB-INF/classes/", "");
		//toString()
//		base = base.split(":").length < 2 ? "/"+base : base; //linux
		String rpath = base + File.separator + "logs"+ File.separator + path + File.separator + Tools.formatDate(null, "yyyyMMdd") + ".txt";
		File file = new File(rpath);
		logSet(file, log);
	}
	
	/**
	 * 写文件日志
	 * @param file
	 * @param log
	 */
	public static synchronized void logSet(File file, String log) {
		OutputStream out;
		try {
			//file.getParentFile().mkdirs();
			File pfile = file.getParentFile();
			if(!pfile.exists()){
				pfile.mkdirs();
			}
			log = Tools.formatDate(null, "yyyy-MM-dd hh:mm:ss") + " -- " + log;
			out = new FileOutputStream(file, true);
			byte[] b = new String(log + "\r\n").getBytes("utf-8");
			for (int i = 0; i < b.length; i++) {
				out.write(b[i]);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
