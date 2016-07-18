package org.wuxb.beartific.batisUtils.creater;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQL.simplePOJOSQL.SimplePOJOSQLCreater;
import org.wuxb.beartific.batisUtils.SQL.simplePOJOSQL.impl.SimplePOJOSQLCreaterImpl;
import org.wuxb.beartific.batisUtils.SQLFactor.Factor;
import org.wuxb.beartific.batisUtils.initStep.CreateStepinitialClass;
import org.wuxb.beartific.batisUtils.resultMapMaker.SimplePOJOResultMapMaker;
import org.wuxb.beartific.batisUtils.xmlWriteUtils.XMLWriteUtils;

public class SimplePOJOXMLCreater {
	
	
	
	
	public String createMyBatisPOJOXML(Class<?> cls,String identification,String tableName,
			String idType,
			String DAOPATH, 
			String basePath,
			String BASEPATH, 
			String MAPPERPATH,
			String DAOPACKAGENAME,
			String MAPPERPACKAGENAME,
			String RESULTMAPPER_ID_NAME,
			Map<String, String> collectionOfAbsolutePath,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		/**
		 * 创建包,获取绝对路径
		 */
		basePath = CreateStepinitialClass.firstStepOfXmlCreate(cls, collectionOfJDBCAndJAVATypeConvert, basePath, 
				 DAOPACKAGENAME, MAPPERPACKAGENAME, collectionOfAbsolutePath, BASEPATH, 
				 DAOPATH, MAPPERPATH);
		 //System.out.println(basePath);
		/**
		 * 生成根XML和resultMap
		 */
		 
		 Document rootDocument = DocumentHelper.createDocument();
	     Factor factor = SimplePOJOResultMapMaker.MakeSimpleResultMap(rootDocument, cls, idType, DAOPATH, basePath, 
	    		 BASEPATH, tableName, MAPPERPATH, identification, DAOPACKAGENAME, MAPPERPACKAGENAME, RESULTMAPPER_ID_NAME, 
	    		 collectionOfAbsolutePath, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);

		/**
		 * 生成SQL语句
		 */
	     writeSimpleXMLSQL(cls, factor.getIdType(), tableName, factor.getRootElement(), identification, RESULTMAPPER_ID_NAME, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
	     //System.out.println(basePath);
	     //дxml
	   
	     XMLWriteUtils.createXMLByRootDocument(rootDocument, cls, basePath, MAPPERPACKAGENAME);
		
	     return basePath;
	    
	}
	
	
	private void writeSimpleXMLSQL(Class<?> cls,
									String idType,
								    String tableName,
								    Element rootElement,
								    String identification,
									String RESULTMAPPER_ID_NAME,
									Map<String, Map<String, String>> collectionOfTableInfo,
									Map<String, Map<String, String>> collectionOfSqlInfo,
									Map<String, String> collectionOfJDBCAndJAVATypeConvert
								   
			
			){
		
		SimplePOJOSQLCreater SQLCreater = new SimplePOJOSQLCreaterImpl();
	     /*
	      * 
	      * <sql/>节点 公用SQL
	      * 
	      * */
	     SQLCreater.createBaseColumnSQL(rootElement, tableName, collectionOfTableInfo);
	     
	     /*
	      * <select id='selectByPageNo'/>节点 分页列表
	      */
	     SQLCreater.createSimpleSelectByPageSQL(rootElement, tableName, cls, RESULTMAPPER_ID_NAME, collectionOfSqlInfo,identification);
	     
	     
	     /*
	      * <select id="getCount" resultType="int" >
		  *  SELECT COUNT(*) FROM t_person
		  *  </select>
	      */
	     SQLCreater.createSelectCountSQL(rootElement, tableName, collectionOfSqlInfo);
	     
	     
	     
	     SQLCreater.createSimpleSelectList(rootElement, tableName, cls, RESULTMAPPER_ID_NAME, collectionOfSqlInfo, identification);
	     

	     /*
	      * <select id='selectByPrimaryKey'/>节点 根据标识属性查找对象
	      */
	     SQLCreater.createSimpleSelectByIdSQL(rootElement, tableName, cls, identification, RESULTMAPPER_ID_NAME, idType, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
	     /*
	      * 2/20:第一条分页查询SQL语句生成成功
	      * TIP:寻找DOM4J生成XML换行方法(已解决:自定义OutPutFormat,避免转义)
	      *
	      * ===================================================================================
	      *
	      *
	      * */
	     /*
	      * <delete id='deleteByPrimaryKey'/>节点 删除对象
	      */
	     SQLCreater.createSimpleDeleteByIdSQL(rootElement, tableName, identification, idType, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
	     
	      /*
	      * <insert id='insertSelective'/>节点 增加对象
	      */
	     SQLCreater.createSimpleInsertSQL(rootElement, tableName, cls, collectionOfTableInfo, collectionOfSqlInfo);
	    
	    /*
	      * <update id='updateByPrimaryKeySelective'/>节点 修改对象(不可赋空)
	      */
	     SQLCreater.createSimpleUpdateSelectiveSQL(rootElement, tableName, cls, identification, idType, collectionOfSqlInfo, collectionOfTableInfo, collectionOfJDBCAndJAVATypeConvert);
	     
	    
		
	}

}
