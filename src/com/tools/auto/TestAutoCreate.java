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
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.auth.dao.UserDao;
import com.auth.dao.impl.UserDaoImpl;
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
	
	public void createDao(String tempName, String fileName){
		String packName = config.getDaoPath(); //包路径
//		String tempName = "BaseDao"; //dao层接口,模版名
//		String fileName = "BaseDao"; //dao层接口
		String chartset = "utf-8"; //字符集
		createDaoFile(packName, tempName, fileName, chartset);
	}
	
	public void createDaoImpl(String tempName, String fileName){
		String packName = config.getDaoImplPath(); //包路径
//		String tempName = "BaseDaoImpl"; //dao层实现,模版名
//		String fileName = "BaseDaoImpl"; //dao层实现
		String chartset = "utf-8"; //字符集
		createDaoFile(packName, tempName, fileName, chartset);
	}

	public void createDaoUtil(String tempName, String fileName){
		String packName = config.getDaoUtilPath(); //包路径
//		String tempName = "JdbcOperateManager"; //dao层工具,模版名
//		String fileName = "JdbcOperateManager"; //dao层工具
		String chartset = "utf-8"; //字符集
		createDaoFile(packName, tempName, fileName, chartset);
	}
	
	public void createDB(String fileName){
		String packName = config.getDaoUtilPath(); //包路径
//		String tempName = "JdbcOperateManager"; //dao层工具,模版名
//		String fileName = "JdbcOperateManager"; //dao层工具
		String chartset = "utf-8"; //字符集
		createDBFile(packName, fileName, chartset);
	}

	public void createService(String tempName, String fileName){
		String packName = config.getServicePath(); //包路径
//		String tempName = "BaseService"; //service层接口,模版名
//		String fileName = "BaseService"; //service层接口
		String chartset = "utf-8"; //字符集
		createServiceFile(packName, tempName, fileName, chartset);
	}
	
	public void createServiceImpl(String tempName, String fileName){
		String packName = config.getServiceImplPath(); //包路径
//		String tempName = "BaseServiceImpl"; //service层实现,模版名
//		String fileName = "BaseServiceImpl"; //service层实现
		String chartset = "utf-8"; //字符集
		createServiceFile(packName, tempName, fileName, chartset);
	}

	public void createServlet(){
		
	}

	public void createController(){
		
	}
	
	public void createBase(){
		createDao("BaseDao", "BaseDao");
		createDaoImpl("BaseDaoImpl", "BaseDaoImpl");
		createDaoUtil("JdbcOperateManager", "JdbcOperateManager");
		createService("BaseService", "BaseService");
		createServiceImpl("BaseServiceImpl", "BaseServiceImpl");
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
	
	private void createDaoFile(String packName, String tempName, String fileName, String chartset){
		String srcPath = System.getProperty("user.dir") + File.separator + "src"; //src 路径
		String path = getClass().getResource("").getPath().replace("build/classes", "src").replace("WEB-INF/classes", "src") + tempName + ".txt";
		String outPath = srcPath + File.separator + packName.replace(".", File.separator) + File.separator + fileName + ".java"; //输出路径
		BufferedWriter bw = null;
		BufferedReader br = null;
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
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), chartset)); 
			String line; 
			boolean isBean = false;
			while((line = br.readLine()) != null){ 
				//packge
				if(line.indexOf("package PK;") != -1){
					line = "package " + packName + ";";
				}
				//import
				String dn = fileName.replace("Impl", ""); //dao name
				String en = fileName.replace("Impl", "").replace("Dao", ""); //entity name
				if(line.indexOf("import JdbcOperateManager;") != -1){
					line = "import " + packName.replace("impl", "util.JdbcOperateManager") + ";"; //import xxxx.dao.util.JdbcOperateManager
				}else if(line.indexOf("import Dao;") != -1){
					line = "import " + packName.replace("impl", dn) + ";"; //import xxxx.dao.xxxDao
				}else if(line.indexOf("import T;") != -1){
					isBean = true;
					line = "import " + packName.replace("dao.impl", "entity."+en) + ";"; //import xxxx.entity.xxx
				}else{
					if(isBean){
						line = line.replace("<T>", "<"+en+">");
						line = line.replace("Temp", en);
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
	
	private void createServiceFile(String packName, String tempName, String fileName, String chartset){
		String srcPath = System.getProperty("user.dir") + File.separator + "src"; //src 路径
		String path = getClass().getResource("").getPath().replace("build/classes", "src").replace("WEB-INF/classes", "src") + tempName + ".txt";
		String outPath = srcPath + File.separator + packName.replace(".", File.separator) + File.separator + fileName + ".java"; //输出路径
		BufferedWriter bw = null;
		BufferedReader br = null;
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
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), chartset)); 
			String line; 
			boolean isBean = false;
			while((line = br.readLine()) != null){ 
				//packge
				if(line.indexOf("package PK;") != -1){
					line = "package " + packName + ";";
				}
				//import
				String sn = fileName.replace("Impl", ""); //service name
				String en = fileName.replace("Impl", "").replace("Service", ""); //entity name
				if(line.indexOf("import DB;") != -1){
					line = "import " + packName.replace("service.impl", "dao.util.DB") + ";"; //import xxxx.dao.util.DB
				}else if(line.indexOf("import Service;") != -1){
					line = "import " + packName.replace("impl", sn) + ";"; //import xxxx.service.xxxService
				}else if(line.indexOf("import T;") != -1){
					isBean = true;
					line = "import " + packName.replace("service.impl", "entity."+en) + ";";  //import xxxx.entity.xxx
				}else if(line.indexOf("DB.TEMP_DAO") != -1){
					line = line.replace("DB.TEMP_DAO", "DB."+en.toUpperCase()+"_DAO");
				}else{
					if(isBean){
						line = line.replace("<T>", "<"+en+">");
						line = line.replace("Temp", en);
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
	
	private void createDBFile(String packName, String fileName, String chartset){
		String srcPath = System.getProperty("user.dir") + File.separator + "src"; //src 路径
		String path = getClass().getResource("").getPath().replace("build/classes", "src").replace("WEB-INF/classes", "src") + "DB.java";
		String outPath = srcPath + File.separator + packName.replace(".", File.separator) + File.separator + "DB.java"; //输出路径
		System.out.println("in path: "+path);
		System.out.println("out path: "+outPath);
		File inFile = new File(path);
		File outFile = new File(outPath);
		try {
			boolean isExist = outFile.exists();
			RandomAccessFile rafWrite = new RandomAccessFile(outFile, "rw"); 
			List<String> content = new ArrayList<String>();
			String line;
			//如果文件不存在，先创建模版。
			if(!isExist){
				//以只读方式打开并读取一行数据
				RandomAccessFile rafRead = new RandomAccessFile(inFile, "r");
				File pfile = outFile.getParentFile();
				if(!pfile.exists()){
					pfile.mkdirs();
				}
				while ((line = rafRead.readLine()) != null) {
					if(line.indexOf("package PK;") != -1){
						line = "package " + packName + ";";
					}
					content.add(new String(line.getBytes("ISO-8859-1"), chartset));
				}
				rafRead.close();
			}else{
				while ((line = rafWrite.readLine()) != null) {
					content.add(new String(line.getBytes("ISO-8859-1"), chartset));
				}
				rafWrite.seek(0);
			}
			
			for (int i = 0; i < content.size(); i++) {
				if("//import".equals(content.get(i).trim())){
					content.add(i+1, "import "+ packName.replace("util", "") + fileName+"Dao;");
					content.add(i+1, "import "+ packName.replace("util", "impl") + fileName+"DaoImpl;");
				}else if("//dao".equals(content.get(i).trim())){
					content.add(i+1, "	public static final "+fileName+"Dao "+fileName.toUpperCase()+"_DAO = new "+fileName+"DaoImpl();");
				}
			}
			for (String str : content) {
				rafWrite.write(str.getBytes());
				rafWrite.write("\n".getBytes());
			}
			rafWrite.close();
		} catch (Exception e) {
			e.printStackTrace();
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
//		test.createBase();
		
//		test.createDao("TempDao", "UserDao");
//		test.createDaoImpl("TempDaoImpl", "UserDaoImpl");
//		test.createService("TempService", "UserService");
//		test.createServiceImpl("TempServiceImpl", "UserServiceImpl");
		test.createDB("User");
//		test.createDB("App");
	}
	
}
