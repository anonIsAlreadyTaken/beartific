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
	 * 锟斤拷目录 eg:org.wuxb
	 */
	private String basePath;
	/**
	 * 锟斤拷锟斤拷锟斤拷String
	 */
	private final String DAOPACKAGENAME = "DAO\\";
	private final String MAPPERPACKAGENAME = "Mapper\\";
	/**
	 * id锟斤拷锟皆碉拷Java锟斤拷锟斤拷
	 */
	private static String idType;
	/**
	 * DAO,Mapper,锟酵伙拷锟侥撅拷锟铰凤拷锟�
	 */
	private Map<String, String> collectionOfAbsolutePath = new HashMap<String, String>();
	/**
	 * JAVA锟斤拷锟絁DBC锟斤拷锟斤拷锟斤拷投锟接︼拷锟较拷锟斤拷锟�
	 */
	private Map<String, String> collectionOfJDBCAndJAVATypeConvert = new HashMap<String, String>();
	/**
	 * 锟斤拷锟斤拷息锟斤拷锟斤拷锟斤拷K:锟斤拷锟斤拷,V:锟街讹拷锟斤拷息
	 */
	private Map<String, Map<String, String>> collectionOfTableInfo = new HashMap<String, Map<String, String>>();
	/**
	 * sql锟斤拷锟斤拷锟较拷锟斤拷锟�K:sql锟斤拷锟絀D,V:锟斤拷锟斤拷锟斤拷锟斤拷值
	 */
	private Map<String, Map<String, String>> collectionOfSqlInfo = new HashMap<String, Map<String, String>>();
	/**
	 * 锟斤拷准MapperResult锟斤拷ID
	 */
	private String RESULTMAPPER_ID_NAME = "BaseResultMap";
	
	
	
	
	public void createMyBatisSimplePOJOXML(boolean needTable,Class<?> cls, String identification, String tableName){
		
		basePath = new SimplePOJOXMLCreater().createMyBatisPOJOXML(cls, identification, tableName, idType, DAOPATH, basePath, BASEPATH, MAPPERPATH, DAOPACKAGENAME, MAPPERPACKAGENAME, RESULTMAPPER_ID_NAME, collectionOfAbsolutePath, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
		
		
		
		InterfaceCreateUtil createUtil = new InterfaceCreateUtil(collectionOfAbsolutePath, collectionOfSqlInfo,DAOPATH,basePath,cls);
		createUtil.InterfaceAutoCreate();
		
		if(needTable){
			/**
			 * 锟斤拷锟斤拷锟斤拷
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
			 * 锟斤拷锟斤拷锟斤拷
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
			 * 锟斤拷锟斤拷锟斤拷
			 */
			MySQLTableBuider builder = new MySQLTableBuiderImpl();
			builder.itIsSimpleTableBuildingTime(collectionOfTableInfo, identification2, tableName2, identification1, tableName1, MappingTableName);
			
		}
		
	}
		

	
	
	
	
}
