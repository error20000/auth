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
	private boolean overWrite = true;
	
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
		String packName = config.getDaoPath(); //包路径
		String fileName = "BaseDao"; //dao层接口
		String chartset = "utf-8"; //字符集
		createDaoFile(packName, fileName, chartset, false);
	}
	
	public void createDaoImpl(){
		String packName = config.getDaoImplPath(); //包路径
		String fileName = "BaseDaoImpl"; //dao层实现
		String chartset = "utf-8"; //字符集
		createDaoFile(packName, fileName, chartset, true);
	}

	public void createDaoUtil(){
		String packName = config.getDaoUtilPath(); //包路径
		String fileName = "JdbcOperateManager"; //dao层工具
		String chartset = "utf-8"; //字符集
		createDaoFile(packName, fileName, chartset, false);
	}

	public void createService(){
		String packName = config.getServicePath(); //包路径
		String fileName = "BaseService"; //service层接口
		String chartset = "utf-8"; //字符集
		createFile(packName, fileName, chartset);
	}
	
	public void createServiceImpl(){
		String packName = config.getServiceImplPath(); //包路径
		String fileName = "BaseServiceImpl"; //service层实现
		String chartset = "utf-8"; //字符集
		createFile(packName, fileName, chartset);
	}

	public void createServlet(){
		
	}

	public void createController(){
		
	}
	
	private void createFile(String packName, String fileName, String chartset){
		String srcPath = System.getProperty("user.dir") + File.separator + "src"; //src 路径
		String path = getClass().getResource("").getPath().replace("build/classes", "src").replace("WEB-INF/classes", "src") + fileName + ".java";
		String outPath = srcPath + File.separator + packName.replace(".", File.separator); //输出路径
		System.out.println("in path: "+path);
		System.out.println("out path: "+outPath);
		BufferedWriter bw = null;
		BufferedReader br = null;
		File outFile = new File(outPath + File.separator + fileName + ".java");
		//如果文件已存在，并且不开启重写。结束创建。
		if(outFile.exists() && !overWrite){
			return;
		}
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
					line = "package " + packName + ";";
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
	
	private void createDaoFile(String packName, String fileName, String chartset, boolean isImpl){
		String srcPath = System.getProperty("user.dir") + File.separator + "src"; //src 路径
		String path = getClass().getResource("").getPath().replace("build/classes", "src").replace("WEB-INF/classes", "src") + fileName + ".java";
		String outPath = srcPath + File.separator + packName.replace(".", File.separator); //输出路径
		System.out.println("in path: "+path);
		System.out.println("out path: "+outPath);
		BufferedWriter bw = null;
		BufferedReader br = null;
		File outFile = new File(outPath + File.separator + fileName + ".java");
		//如果文件已存在，并且不开启重写。结束创建。
		if(outFile.exists() && !overWrite){
			return;
		}
		File pfile = outFile.getParentFile();
		if(!pfile.exists()){
			pfile.mkdirs();
		}
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), chartset)); 
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), chartset)); 
			String line; 
			boolean isBean = false;
			while((line = br.readLine()) != null){ 
				//packge
				if(line.indexOf("package PK;") != -1){
					line = "package " + packName + ";";
				}
				//import
				if(isImpl){
					String dn = fileName.replace("Impl", ""); //dao name
					String en = fileName.replace("DaoImpl", ""); //entity name
					if(line.indexOf("import BaseDao;") != -1){
						line = "import " + packName.replace("impl", dn) + ";";
					}else if(line.indexOf("import JdbcOperateManager;") != -1){
						line = "import " + packName.replace("impl", "util.JdbcOperateManager") + ";";
					}else if(line.indexOf("import Dao;") != -1){
						line = "import " + packName.replace("impl", dn) + ";";
					}else if(line.indexOf("import T;") != -1){
						isBean = true;
						line = "import " + packName.replace("dao.impl", "entity."+en) + ";";
					}else{
						if(isBean){
							line = line.replace("<T>", "<"+en+">");
							line = line.replace("TempDao", en);
						}
					}
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
	
	private void createFile(String packName, String fileName, String chartset){
		String srcPath = System.getProperty("user.dir") + File.separator + "src"; //src 路径
		String path = getClass().getResource("").getPath().replace("build/classes", "src").replace("WEB-INF/classes", "src") + fileName + ".java";
		String outPath = srcPath + File.separator + packName.replace(".", File.separator); //输出路径
		System.out.println("in path: "+path);
		System.out.println("out path: "+outPath);
		BufferedWriter bw = null;
		BufferedReader br = null;
		File outFile = new File(outPath + File.separator + fileName + ".java");
		//如果文件已存在，并且不开启重写。结束创建。
		if(outFile.exists() && !overWrite){
			return;
		}
		File pfile = outFile.getParentFile();
		if(!pfile.exists()){
			pfile.mkdirs();
		}
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), chartset)); 
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), chartset)); 
			String line; 
			while((line = br.readLine()) != null){ 
				if(line.indexOf("package ") != -1){
					line = "package " + packName + ";";
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
	
	
	private void readDaoFile(String packName, String fileName, String chartset){
		String srcPath = System.getProperty("user.dir") + File.separator + "src"; //src 路径
		String path = getClass().getResource("").getPath().replace("build/classes", "src").replace("WEB-INF/classes", "src") + fileName + ".java";//输入路径
		String outPath = srcPath + File.separator + packName.replace(".", File.separator) + File.separator + fileName + ".java"; //输出路径
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), chartset)); 
			writeFile(br, outPath, chartset);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try { 
				if(br != null){
					br.close(); 
				}
			}catch (Exception e) { 
				e.printStackTrace(); 
			}
		}
	}
	
	private void writeFile(BufferedReader br, String outPath, String chartset){
		BufferedWriter bw = null;
		File outFile = new File(outPath);
		//如果文件已存在，并且不开启重写。结束创建。
		if(outFile.exists() && !overWrite){
			return;
		}
		File pfile = outFile.getParentFile();
		if(!pfile.exists()){
			pfile.mkdirs();
		}
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), chartset)); 
			String line; 
			while((line = br.readLine()) != null){ 
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
	
	public static void main(String[] args) {
		
//		System.out.println(System.getProperty("user.dir"));
//        File directory = new File("");//设定为当前文件夹  
//        try{  
//           System.out.println(directory.getCanonicalPath());//获取标准的路径  
//           System.out.println(directory.getAbsolutePath());//获取绝对路径  
//       }catch(Exception e)  
//        {  
//            e.printStackTrace();  
//        }

		
		TestAutoCreate test =  new TestAutoCreate("com.testAuto");
		test.createDao();
		test.createDaoImpl();
		test.createDaoUtil();
//		test.createService();
//		test.createServiceImpl();
	}
	
}
