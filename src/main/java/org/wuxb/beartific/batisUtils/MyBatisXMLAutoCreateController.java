package org.wuxb.beartific.batisUtils;

import java.util.HashMap;
import java.util.Map;

import org.wuxb.beartific.batisUtils.BuildTableUtil.mySQL.MySQLTableBuider;
import org.wuxb.beartific.batisUtils.BuildTableUtil.mySQL.impl.MySQLTableBuiderImpl;
import org.wuxb.beartific.batisUtils.IOFactory.InterfaceCreateUtil;
import org.wuxb.beartific.batisUtils.creater.RelationM_T_MXMLCreater;
import org.wuxb.beartific.batisUtils.creater.RelationM_T_OXMLCreater;
import org.wuxb.beartific.batisUtils.creater.SimplePOJOXMLCreater;

public class MyBatisXMLAutoCreateController {

	/**
	 * 取绝对路径时的Key
	 */
	private final String BASEPATH = "basePathLocation";
	private final String DAOPATH = "DAOPathLocation";
	private final String MAPPERPATH = "MapperPathLocation";

	/**
	 * 跟目录 eg:org.wuxb
	 */
	private String basePath;
	/**
	 * 工具型String
	 */
	private final String DAOPACKAGENAME = "DAO\\";
	private final String MAPPERPACKAGENAME = "Mapper\\";
	/**
	 * id属性的Java类型
	 */
	private static String idType;
	/**
	 * DAO,Mapper,和基本包的绝对路径
	 */
	private Map<String, String> collectionOfAbsolutePath = new HashMap<String, String>();
	/**
	 * JAVA类和JDBC类的类型对应信息容器
	 */
	private Map<String, String> collectionOfJDBCAndJAVATypeConvert = new HashMap<String, String>();
	/**
	 * 表信息容器，K:表名,V:字段信息
	 */
	private Map<String, Map<String, String>> collectionOfTableInfo = new HashMap<String, Map<String, String>>();
	/**
	 * sql语句信息容器 K:sql语句ID,V:需求参数及返回值
	 */
	private Map<String, Map<String, String>> collectionOfSqlInfo = new HashMap<String, Map<String, String>>();
	/**
	 * 基准MapperResult的ID
	 */
	private String RESULTMAPPER_ID_NAME = "BaseResultMap";
	
	
	
	
	public void createMyBatisSimplePOJOXML(boolean needTable,Class<?> cls, String identification, String tableName){
		
		basePath = new SimplePOJOXMLCreater().createMyBatisPOJOXML(cls, identification, tableName, idType, DAOPATH, basePath, BASEPATH, MAPPERPATH, DAOPACKAGENAME, MAPPERPACKAGENAME, RESULTMAPPER_ID_NAME, collectionOfAbsolutePath, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
		
		
		
		InterfaceCreateUtil createUtil = new InterfaceCreateUtil(collectionOfAbsolutePath, collectionOfSqlInfo,DAOPATH,basePath,cls);
		createUtil.InterfaceAutoCreate();
		
		if(needTable){
			/**
			 * 创建表
			 */
			MySQLTableBuider builder = new MySQLTableBuiderImpl();
			builder.itIsSimpleTableBuildingTime(collectionOfTableInfo,identification);
			
		}
		
	}
	
	
	public void createMyBatisMappingManyToOnePOJOXML(boolean needTable,Class<?> Many_POJO,String Many_POJOIdentification,String Many_POJO_tableName,Class<?> One_POJO,String One_POJOIdentification,String One_POJO_tableName){
		
		basePath = new RelationM_T_OXMLCreater().createMyBatisRelationManyPOJOXML(Many_POJO,One_POJO,idType,DAOPATH, basePath,BASEPATH, MAPPERPATH,DAOPACKAGENAME,MAPPERPACKAGENAME,One_POJO_tableName,RESULTMAPPER_ID_NAME,Many_POJO_tableName,Many_POJOIdentification,One_POJOIdentification,collectionOfAbsolutePath,collectionOfTableInfo,collectionOfSqlInfo,collectionOfJDBCAndJAVATypeConvert);
		
		InterfaceCreateUtil createUtil = new InterfaceCreateUtil(collectionOfAbsolutePath, collectionOfSqlInfo,DAOPATH,basePath,Many_POJO);
		createUtil.InterfaceAutoCreate();
		
		collectionOfSqlInfo = new HashMap<String, Map<String, String>>();
		 
		basePath = new RelationM_T_OXMLCreater().createMyBatisRelationOnePOJOXML(Many_POJO,One_POJO,idType,DAOPATH, basePath,BASEPATH, MAPPERPATH,DAOPACKAGENAME,MAPPERPACKAGENAME,One_POJO_tableName,RESULTMAPPER_ID_NAME,Many_POJO_tableName,Many_POJOIdentification,One_POJOIdentification,collectionOfAbsolutePath,collectionOfTableInfo,collectionOfSqlInfo,collectionOfJDBCAndJAVATypeConvert);
			
		createUtil = new InterfaceCreateUtil(collectionOfAbsolutePath, collectionOfSqlInfo,DAOPATH,basePath,One_POJO);
		createUtil.InterfaceAutoCreate();
		
		if(needTable){
			/**
			 * 创建表
			 */
			MySQLTableBuider builder = new MySQLTableBuiderImpl();
			builder.itIsSimpleTableBuildingTime(collectionOfTableInfo, Many_POJOIdentification, One_POJOIdentification,Many_POJO_tableName,One_POJO_tableName);
		}
		
	};
	
	public void createMyBatisManyToManyPOJOXML(boolean needTable,Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,String MappingTableName){
		
		RelationM_T_MXMLCreater M_T_MXMLCreater = new RelationM_T_MXMLCreater();
		
		basePath = M_T_MXMLCreater.createMyBatisRelationM_T_MPOJOXML(ManyPOJO1,identification1,tableName1,ManyPOJO2,identification2,tableName2,MappingTableName,idType,DAOPATH, basePath,BASEPATH, MAPPERPATH,DAOPACKAGENAME,MAPPERPACKAGENAME,RESULTMAPPER_ID_NAME,collectionOfAbsolutePath,collectionOfTableInfo,collectionOfSqlInfo,collectionOfJDBCAndJAVATypeConvert);
		InterfaceCreateUtil createUtil = new InterfaceCreateUtil(collectionOfAbsolutePath, collectionOfSqlInfo, DAOPATH,basePath,ManyPOJO1);
		createUtil.InterfaceAutoCreate();
		
		
		M_T_MXMLCreater.createMyBatisRelationM_T_MPOJOXML(ManyPOJO2,identification2,tableName2,ManyPOJO1,identification1,tableName1,MappingTableName,idType,DAOPATH, basePath,BASEPATH, MAPPERPATH,DAOPACKAGENAME,MAPPERPACKAGENAME,RESULTMAPPER_ID_NAME,collectionOfAbsolutePath,collectionOfTableInfo,collectionOfSqlInfo,collectionOfJDBCAndJAVATypeConvert);
		createUtil = new InterfaceCreateUtil(collectionOfAbsolutePath, collectionOfSqlInfo, DAOPATH,basePath,ManyPOJO2);
		createUtil.InterfaceAutoCreate();
		
		
		if(needTable){
			/**
			 * 创建表
			 */
			MySQLTableBuider builder = new MySQLTableBuiderImpl();
			builder.itIsSimpleTableBuildingTime(collectionOfTableInfo, identification2, tableName2, identification1, tableName1, MappingTableName);
			
		}
		
	}
		

	
	
	
	
}
