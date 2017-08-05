package com.tools.auto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.tools.auto.db.Structure;
import com.tools.auto.db.Table;
import com.tools.auto.db.TableManager;
import com.tools.utils.Tools;

/**
 * 自动生成管理器。需要配置的参数：
 * <p>{@code packge} 包路径
 * <p>{@code dbPath} 数据库配置文件（必填）
 * <p>{@code prefix} 表前缀
 * <p>{@code separator} 表分隔符
 * <p>{@code chartset} 生成文件字符集，默认“utf-8”
 * @author liujian
 *
 * @see com.tools.auto.Config
 * @see com.tools.auto.db.TableManager
 */
public class AutoCreateManager {
	
	private Config config =  new Config();
	private boolean overWrite = true;
	private TableManager manager = null;
	private String dbPath = ""; //数据库配置
	private String prefix = ""; //数据表前缀
	private String separator = ""; //数据表分隔符
	private String chartset = "utf-8";//文件字符集
	
	public AutoCreateManager(){
		
	}
	
	public AutoCreateManager(String packge, String dbPath){
		if(!Tools.isNullOrEmpty(packge)){
			config = new Config(packge);
		}
		if(!Tools.isNullOrEmpty(dbPath)){
			this.dbPath = dbPath;
			manager = new TableManager(dbPath);
		}
	}
	
	public AutoCreateManager(String packge, String dbPath, String prefix, String separator){
		if(!Tools.isNullOrEmpty(packge)){
			config = new Config(packge);
		}
		if(!Tools.isNullOrEmpty(dbPath)){
			this.dbPath = dbPath;
			manager = new TableManager(dbPath);
		}
		this.prefix = prefix;
		this.separator = separator;
	}
	
	public void setPackge(String packge){
		if(!Tools.isNullOrEmpty(packge)){
			config = new Config(packge);
		}
	} 
	
	public void setDbPath(String dbPath){
		if(!Tools.isNullOrEmpty(dbPath)){
			this.dbPath = dbPath;
			manager = new TableManager(dbPath);
		}
	}
	
	public void setPrefix(String prefix){
		this.prefix = prefix;
	} 
	
	public void setSeparator(String separator){
		this.separator = separator;
	} 
	
	public void setChartset(String chartset){
		this.chartset = chartset;
	} 
	
	public void start(){
		if(manager == null){
			return;
		}
		createBase();
		createEntity();
	} 

	public void createEntity(){
		if(manager == null){
			return;
		}
		//解析数据库
		List<Table> list = manager.getDbInfo();
		for (Table table : list) {
			String ename = table.getTableName().replace(prefix, "");
			ename = ename.substring(0, 1).toUpperCase() + ename.substring(1);
			if(!Tools.isNullOrEmpty(separator)){
				int index = 0;
				while ((index = ename.indexOf(separator)) != -1) {
					ename = ename.substring(0, index)+ename.substring(index+1, index+2).toUpperCase()+ename.substring(index+2);
				}
			}
			table.setEntityName(ename);
			createEntity(table);
		}
	}
	
	/**
	 * 
	 * @param table 表信息
	 */
	public void createEntity(Table table){
		String packName = config.getEntityPath(); //包路径
		String eName = table.getEntityName();
		//创建entity
		createEntityFile(packName, table, chartset);
		
		//创建dao
		createDao("TempDao", eName + "Dao");
		createDaoImpl("TempDaoImpl", eName + "DaoImpl");
		createDB(eName);
		//创建service
		createService("TempService", eName + "Service");
		createServiceImpl("TempServiceImpl", eName + "ServiceImpl");
		//创建controller
		createController("TempController", eName + "Controller");
	}
	
	/**
	 * 创建dao层接口
	 * @param tempName 模版名
	 * @param fileName 生成文件名
	 */
	public void createDao(String tempName, String fileName){
		String packName = config.getDaoPath(); //包路径
		createDaoFile(packName, tempName, fileName, chartset);
	}
	
	/**
	 * 创建dao层实现
	 * @param tempName 模版名
	 * @param fileName 生成文件名
	 */
	public void createDaoImpl(String tempName, String fileName){
		String packName = config.getDaoImplPath(); //包路径
		createDaoFile(packName, tempName, fileName, chartset);
	}

	/**
	 * 创建dao层工具
	 * @param tempName 模版名
	 * @param fileName 生成文件名
	 */
	public void createDaoUtil(String tempName, String fileName){
		String packName = config.getDaoUtilPath(); //包路径
		createDaoFile(packName, tempName, fileName, chartset);
	}
	
	/**
	 * 注册dao层到DB。 例如：public static final UserDao USER_DAO = new UserDaoImpl();
	 * @param fileName 生成文件名
	 */
	public void createDB(String fileName){
		String packName = config.getDaoUtilPath(); //包路径
		createDBFile(packName, fileName, chartset);
	}

	/**
	 * 创建service层接口
	 * @param tempName 模版名
	 * @param fileName 生成文件名
	 */
	public void createService(String tempName, String fileName){
		String packName = config.getServicePath(); //包路径
		createServiceFile(packName, tempName, fileName, chartset);
	}
	
	/**
	 * 创建service层实现
	 * @param tempName 模版名
	 * @param fileName 生成文件名
	 */
	public void createServiceImpl(String tempName, String fileName){
		String packName = config.getServiceImplPath(); //包路径
		createServiceFile(packName, tempName, fileName, chartset);
	}

	/**
	 * 创建控制层，以Servlet模式
	 * @param tempName 模版名
	 * @param fileName 生成文件名
	 */
	public void createServlet(String tempName, String fileName){
		String packName = config.getServletPath(); //包路径
		createServletFile(packName, tempName, fileName, chartset);
	}

	/**
	 * 创建控制层，以Controller模式
	 * @param tempName 模版名
	 * @param fileName 生成文件名
	 */
	public void createController(String tempName, String fileName){
		String packName = config.getControllerPath(); //包路径
		createControllerFile(packName, tempName, fileName, chartset);
	}
	
	/**
	 * 创建基本信息。包括：BaseDao、BaseDaoImpl、JdbcOperateManager、BaseService、BaseServiceImpl
	 * @param tempName 模版名
	 * @param fileName 生成文件名
	 */
	public void createBase(){
		createDao("BaseDao", "BaseDao");
		createDaoImpl("BaseDaoImpl", "BaseDaoImpl");
		createDaoUtil("JdbcOperateManager", "JdbcOperateManager");
		createService("BaseService", "BaseService");
		createServiceImpl("BaseServiceImpl", "BaseServiceImpl");
	}
	
	private void createEntityFile(String packName, Table table, String chartset){
		String srcPath = System.getProperty("user.dir") + File.separator + "src"; //src 路径
		String path = getClass().getResource("").getPath().replace("build/classes", "src").replace("WEB-INF/classes", "src") + "Temp.txt";
		String outPath = srcPath + File.separator + packName.replace(".", File.separator) + File.separator + table.getEntityName() + ".java"; //输出路径
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
			List<String> content = new ArrayList<String>();
			String line; 
			while((line = br.readLine()) != null){ 
				//packge
				if(line.indexOf("package PK;") != -1){
					line = "package " + packName + ";";
				}
				//table
				if(line.indexOf("{Table_Name}") != -1){
					line = line.replace("{Table_Name}", table.getTableName());
				}
				//Temp
				if(line.indexOf("Temp") != -1){
					line = line.replace("Temp", table.getEntityName());
				}
				content.add(line);
			}
			//遍历字段
			List<Structure> sList = table.getTableInfo();
			for (int m = sList.size() - 1; m >= 0; m--) {
				Structure structure = sList.get(m);
				//@PrimaryKey
				if("PRI".equals(structure.getKey())){
					if("auto_increment".equals(structure.getExtra())){
						for (int i = 0; i < content.size(); i++) {
							if("//import".equals(content.get(i).trim())){
								content.add(i+1, "import com.tools.jdbc.PrimaryKey;");
								content.add(i+2, "import com.tools.jdbc.PrimaryKeyType;");
							}else if("//field".equals(content.get(i).trim())){
								content.add(i+1, "	@PrimaryKey(type=PrimaryKeyType.AUTO_INCREMENT)");
								content.add(i+2, "	@Excel(name=\""+structure.getComment().replace("\"", "")+"\", sort="+m+")");
								content.add(i+3, "	private "+structure.getType()+" "+structure.getField()+";");
							}
						}
					}else{
						for (int i = 0; i < content.size(); i++) {
							if("//import".equals(content.get(i).trim())){
								content.add(i+1, "import com.tools.jdbc.PrimaryKey;");
							}else if("//field".equals(content.get(i).trim())){
								content.add(i+1, "	@PrimaryKey");
								content.add(i+2, "	@Excel(name=\""+structure.getComment().replace("\"", "")+"\", sort="+m+")");
								content.add(i+3, "	private "+structure.getType()+" "+structure.getField()+";");
							}
						}
					}
				}else{
					for (int i = 0; i < content.size(); i++) {
						if("//field".equals(content.get(i).trim())){
							content.add(i+1, "	@Excel(name=\""+structure.getComment().replace("\"", "")+"\", sort="+m+")");
							content.add(i+2, "	private "+structure.getType()+" "+structure.getField()+";");
						}
					}
				}
				//public
				for (int i = 0; i < content.size(); i++) {
					if("//get set".equals(content.get(i).trim())){
						content.add(i+1, "	public "+structure.getType()+" get"+(structure.getField().substring(0,1).toUpperCase() + structure.getField().substring(1))+"() {");
						content.add(i+2, "		return "+structure.getField()+";");
						content.add(i+3, "	}");
						
						content.add(i+4, "	public void set"+(structure.getField().substring(0,1).toUpperCase() + structure.getField().substring(1))+"("+structure.getType()+" "+structure.getField()+") {");
						content.add(i+5, "		this."+structure.getField()+" = "+structure.getField()+";");
						content.add(i+6, "	}");
					}else if("//serialize".equals(content.get(i).trim())){
						content.add(i+1, "		json.put(\""+structure.getField()+"\", "+structure.getField()+");");
					}else if("//unserialize".equals(content.get(i).trim())){
						content.add(i+1, "			"+structure.getField()+" = json.get"+("String".equals(structure.getType()) ? "String" : structure.getType().substring(0,1).toUpperCase() + structure.getType().substring(1)+"Value" )+"(\""+structure.getField()+"\");");
					}
				}
			}
			
			for (String str : content) {
				bw.write(str);
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
					line = "import " + packName.replace(".dao", "").replace(".impl", "")+".entity."+en + ";"; //import xxxx.entity.xxx
				}else{
					if(isBean){
						line = line.replace("<T>", "<"+en+">");
						line = line.replace("Temp", en);
					}
				}
				//db file
				if(line.indexOf("{dbPath}") != -1){
					line = line.replace("{dbPath}", dbPath);
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
					line = "import " + packName.replace(".service", "").replace(".impl", "")+".entity."+en + ";";  //import xxxx.entity.xxx
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
		String path = getClass().getResource("").getPath().replace("build/classes", "src").replace("WEB-INF/classes", "src") + "DB.txt";
		String outPath = srcPath + File.separator + packName.replace(".", File.separator) + File.separator + "DB.java"; //输出路径
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
			//判断dao层是否已注册
			boolean flag = true;
			for (String str : content) {
				if(str.indexOf(fileName.toUpperCase()+"_DAO") != -1){
					flag = false;
				}
			}
			if(flag){
				for (int i = 0; i < content.size(); i++) {
					if("//import".equals(content.get(i).trim())){
						content.add(i+1, "import "+ packName.replace("util", "") + fileName+"Dao;");
						content.add(i+1, "import "+ packName.replace("util", "impl.") + fileName+"DaoImpl;");
					}else if("//dao".equals(content.get(i).trim())){
						content.add(i+1, "	public static final "+fileName+"Dao "+fileName.toUpperCase()+"_DAO = new "+fileName+"DaoImpl();");
					}
				}
				for (String str : content) {
					rafWrite.write(str.getBytes());
					rafWrite.write("\n".getBytes());
				}
			}
			rafWrite.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void createServletFile(String packName, String tempName, String fileName, String chartset){
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
	
	private void createControllerFile(String packName, String tempName, String fileName, String chartset){
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
			while((line = br.readLine()) != null){ 
				//packge
				if(line.indexOf("package PK;") != -1){
					line = "package " + packName + ";";
				}
				//import
				String en = fileName.replace("Controller", ""); //entity name
				if(line.indexOf("import T;") != -1){
					line = "import " + packName.replace("controller", "entity."+en) + ";"; //import xxxx.entity.xxx
				}else if(line.indexOf("import Service;") != -1){
					line = "import " + packName.replace("controller", "service."+en+"Service") + ";"; //import xxxx.service.xxxService
				}else if(line.indexOf("import ServiceImpl;") != -1){
					line = "import " + packName.replace("controller", "service.impl."+en+"ServiceImpl") + ";"; //import xxxx.service.impl.xxxServiceImpl
				}else{
					line = line.replace("Temp", en);
				}
				//mapping
				if(line.indexOf("{path}") != -1){
					line = line.replace("{path}", en.toLowerCase());
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

		
		AutoCreateManager test =  new AutoCreateManager("com.testAuto", "db.properties", "s_", "_");
		test.start();
	}
	
}
