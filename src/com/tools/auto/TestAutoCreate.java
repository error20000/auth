package com.tools.auto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;

public class TestAutoCreate {
	
	private Config config = new Config();
	
	public TestAutoCreate(){
		
	}

	public TestAutoCreate(Config config){
		this.config = config;
	}
	
	public TestAutoCreate(String baseBackge){
		config = new Config(baseBackge);
	}

	public void createEntity(){
		
	}
	
	public void createDao(){
		
	}
	
	public void createDaoImpl(){
		
	}

	public void createDaoUtil(){
		String srcPath = System.getProperty("user.dir") + File.separator + "src";
		String outPath = srcPath + File.separator + config.getDaoUtilPath().replace(".", File.separator);
		String chartset = "utf-8";
		String path = getClass().getResource("").getPath().replace("build/classes", "src").replace("WEB-INF/classes", "src")+"JdbcOperateManager.java";
		System.out.println(path);
		BufferedWriter bw = null;
		BufferedReader br = null;
		File outFile = new File(outPath + File.separator + "JdbcOperateManager.java");
		File pfile = outFile.getParentFile();
		if(!pfile.exists()){
			pfile.mkdirs();
		}
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),chartset)); 
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), chartset)); 
			String line; 
			while((line = br.readLine()) != null){ 
				if(line.indexOf("package ") != -1){
					line = "package " + config.getDaoUtilPath() + ";";
				}
				bw.write(line); 
				bw.newLine(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try { 
				if(br != null){
					br.close(); 
				}
				if(bw != null){
					bw.flush();
					bw.close(); 
				}
			}catch (Exception e) { 
				e.printStackTrace(); 
			}
		}
	}

	public void createService(){
		
	}

	public void createServiceImpl(){
		
	}

	public void createServlet(){
		
	}

	public void createController(){
		
	}
	
	public static void main(String[] args) {
		
//		System.out.println(System.getProperty("user.dir"));
		
		TestAutoCreate test =  new TestAutoCreate("com.testAuto");
		test.createDaoUtil();
	}
	
}
