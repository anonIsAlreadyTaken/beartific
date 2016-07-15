package org.wuxb.beartific.batisUtils.BuildTableUtil.mySQL.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.wuxb.beartific.batisUtils.BuildTableUtil.mySQL.MySQLTableBuider;

public class MySQLTableBuiderImpl implements MySQLTableBuider{

	private static String Driver;
	private static String url;
	private static String userName;
	private static String password;
	
	
	/**
	 * ���JDBC������������
	 */
	static {
		
		InputStream resource = MySQLTableBuiderImpl.class.getResourceAsStream("/DB.properties");

		//System.out.println(resource);
		Properties p = new Properties();
		try {
			p.load(resource);
			Enumeration<Object> keys = p.keys();
			while(keys.hasMoreElements()){
				String key =(String)keys.nextElement();
				
				
				if(key.equals("driver")){
					Driver = p.getProperty(key);
				}else if(key.equals("url")){
					url = p.getProperty(key);
				}else if(key.equals("userName")){
					userName = p.getProperty(key);
				}else{
					password = p.getProperty(key);
				}

			}
		} catch (IOException e) {
			
			boolean flag = e.getClass().getSimpleName().equals("NullPointerException");
			if(flag){
				
				System.out.println("ע��DB.properties�е��������:driver,url,userName,password");
				
			}
			
			
		}finally{
			
			if(resource!=null){
				
				try {
					resource.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
		
	}


	public Connection getConnection(){
		Connection conn = null;
		
			try {
				
				Class.forName(Driver);
				conn = DriverManager.getConnection(url,userName,password);
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException:���properties�е�Driver");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("SQLException:���properties�ļ��е�url,userName,password�����ò���");
				e.printStackTrace();
			}catch(NullPointerException e){
				System.out.println("ע��DB.properties�е��������:driver,url,userName,password");
				e.printStackTrace();
			}
		
		return conn;

	}


	
	public void free(ResultSet rs,Statement st,Connection conn){

		if(rs!=null){
			try {
				rs.close();
			} catch (Exception e) {
				System.out.println("������ʧ��");
				e.printStackTrace();
			}
		}
		if(st!=null){
			try {
				st.close();
			} catch (Exception e) {
				System.out.println("��������ʧ��");
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println("���ӹ���ʧ��");
				e.printStackTrace();
			}
		}

	}

	
	public void executeSQL(String sql){

		Connection conn = getConnection();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			

		} catch (SQLException e) {
			String message = e.getMessage();
			if(!message.startsWith("Unknown table")){
				e.printStackTrace();
			}
		}finally{

			free(null, ps, conn);

		}

	}
	/**
	 * 
	 * @function �򵥽ṹ�����
	 * @author ���಩
	 * @time 2016-2-28 ����7:43:05
	 */
	public void itIsSimpleTableBuildingTime(
			Map<String, Map<String, String>> collectionOfTableInfo,String identification) {
		
		/*
		 *  CREATE TABLE `NewTable` (
			`id`  int NULL AUTO_INCREMENT ,
			`name`  varchar(255) NULL ,
			`birthday`  date NULL ,
			`price`  double NULL ,
			`degree`  varchar(255) NULL ,
			PRIMARY KEY (`id`)
			)
			;
		 */
		
		Set<String> set = collectionOfTableInfo.keySet();
		
		for (String string : set) {
			
			Map<String, String> tableMap = collectionOfTableInfo.get(string);
			StringBuffer TableSQL = new StringBuffer();
			String removeTable = "DROP TABLE `"+string+"`";
			executeSQL(removeTable);
			TableSQL.append("CREATE TABLE `").append(string).append("` (");
			
			Set<String> columns = tableMap.keySet();
			for (String column : columns) {
				
				String type = tableMap.get(column).equals("VARCHAR")?tableMap.get(column)+"(255)":tableMap.get(column);
				TableSQL.append("`"+column+"` ").append(type).append(" NULL "+(column.equals(identification)?"AUTO_INCREMENT":""))
				.append(",");
				
			}
			TableSQL.append("PRIMARY KEY (`").append(identification).append("`))");
			
			//System.out.println(TableSQL.toString());
			
			executeSQL(TableSQL.toString());
			
		}
		
		
	}

	/**
	 * 
	 * @function ���һ�ṹ�����
	 * @author ���಩
	 * @time 2016-2-28 ����7:43:26
	 */
	public void itIsSimpleTableBuildingTime(
			Map<String, Map<String, String>> collectionOfTableInfo,String Many_POJO_identification,String One_POJO_identification,String Many_POJO_tableName,String One_POJO_tableName) {
		
		
		
		/*
		 *  CREATE TABLE `NewTable` (
			`id`  int NULL AUTO_INCREMENT ,
			`name`  varchar(255) NULL ,
			`birthday`  date NULL ,
			`price`  double NULL ,
			`degree`  varchar(255) NULL ,
			PRIMARY KEY (`id`)
			)
			;
		 */
		
		Set<String> set = collectionOfTableInfo.keySet();
		
		for (String string : set) {
			
			Map<String, String> tableMap = collectionOfTableInfo.get(string);
			StringBuffer TableSQL = new StringBuffer();
			if(string.equals(Many_POJO_tableName)){
				
			}
			//ɾ���
			String removeTable = "DROP TABLE `"+string+"`";
			//System.out.println(removeTable);
			executeSQL(removeTable);
			
		
			
			TableSQL.append("CREATE TABLE `").append(string).append("` (");
			
			Set<String> columns = tableMap.keySet();
			for (String column : columns) {
				
				String type = tableMap.get(column).equals("VARCHAR")?tableMap.get(column)+"(255)":tableMap.get(column);
				TableSQL.append("`"+column+"` ").append(type).append(" NULL "+(column.equals(Many_POJO_identification)||column.equals(One_POJO_identification)?"AUTO_INCREMENT":""))
				.append(",");
				
			}
			//��������
			if(string.equals(Many_POJO_tableName)){
				
				TableSQL.append("`"+One_POJO_identification+"`");
				Map<String, String> oneTableMap = collectionOfTableInfo.get(One_POJO_tableName);
				String one_id_type = oneTableMap.get(One_POJO_identification);
				TableSQL.append(" ").append(one_id_type).append(" NULL,");
			}
			//ȷ������
			if(string.equals(Many_POJO_tableName)){
				
				TableSQL.append("PRIMARY KEY (`").append(Many_POJO_identification).append("`))");
			}else{
				
				TableSQL.append("PRIMARY KEY (`").append(One_POJO_identification).append("`))");
				
			}
			//System.out.println(TableSQL);
			
			
			executeSQL(TableSQL.toString());
			
		}
		
		/*
		 * 
		 * ALTER TABLE `many_emp` 
		 * ADD FOREIGN KEY (`eid`) 
		 * REFERENCES `one_edu` (`eid`) 
		 * ON DELETE RESTRICT ON UPDATE RESTRICT;
		 * 
		 * 
		 * */
		/*
		 * ���Լ��?
		 * 
		 */
		StringBuffer referencesKey = new StringBuffer();
		referencesKey.append("ALTER TABLE `").append(Many_POJO_tableName).append("`")
		.append(" ADD FOREIGN KEY (`").append(One_POJO_identification).append("`)")
		.append(" REFERENCES `").append(One_POJO_tableName).append("` (`")
		.append(One_POJO_identification).append("`)");
		
		//System.out.println(referencesKey);
		executeSQL(referencesKey.toString());
		
	}



	/**
	 * 
	 * @function ��Զ�����
	 * @author ���಩
	 * @time 2016-2-28 ����7:54:31
	 */
	public void itIsSimpleTableBuildingTime(Map<String, Map<String, String>> collectionOfTableInfo,String identification2,String tableName2,String identification1,String tableName1,String MappingTableName){
		
		
		String removeTable = "DROP TABLE `"+MappingTableName+"`";
		
		executeSQL(removeTable);
		
		
		Map<String, String> map_1 = collectionOfTableInfo.get(tableName1);
		Map<String, String> map_2 = collectionOfTableInfo.get(tableName2);
		
		collectionOfTableInfo = new HashMap<String, Map<String,String>>();
		
		collectionOfTableInfo.put(tableName1, map_1);
		
		itIsSimpleTableBuildingTime(collectionOfTableInfo, identification1);
		
		collectionOfTableInfo = new HashMap<String, Map<String,String>>();
		
		collectionOfTableInfo.put(tableName2, map_2);
		
		itIsSimpleTableBuildingTime(collectionOfTableInfo, identification2);
		
		

		/*
		 * CREATE TABLE `NewTable` (
			`sid`  int NULL ,
			`rid`  int NULL ,
			PRIMARY KEY (`sid`, `rid`),
			FOREIGN KEY (`sid`) REFERENCES `ssh_user` (`sid`),
			FOREIGN KEY (`rid`) REFERENCES `ssh_role` (`rid`)
			)
			;

		 */
		String id_1_type = map_1.get(identification1);
		String id_2_type = map_2.get(identification2);
		
		StringBuffer relationShip = new StringBuffer();
		
		relationShip.append("CREATE TABLE `").append(MappingTableName).append("` (")
		.append("`"+identification1+"` ").append(id_1_type).append(" NULL ,")
		.append("`"+identification2+"` ").append(id_2_type).append(" NULL ,")
		.append("PRIMARY KEY (`").append(identification1).append("`, `")
		.append(identification2).append("`),")
		.append("FOREIGN KEY (`"+identification1+"`)").append(" REFERENCES ")
		.append("`"+tableName1+"`").append(" (`"+identification1+"`),")
		.append("FOREIGN KEY (`"+identification2+"`)").append(" REFERENCES ")
		.append("`"+tableName2+"`").append(" (`"+identification2+"`))");
		
		//System.out.println(relationShip);
		executeSQL(relationShip.toString());
		
	}
	

	public void initBuider() {
		// TODO Auto-generated method stub
		
	}



}
