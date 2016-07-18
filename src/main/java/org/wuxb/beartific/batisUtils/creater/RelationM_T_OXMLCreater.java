package org.wuxb.beartific.batisUtils.creater;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQL.M_T_ORelationSQL.M_T_ORelationSQLCreater;
import org.wuxb.beartific.batisUtils.SQL.M_T_ORelationSQL.impl.M_T_ORelationSQLCreaterImpl;
import org.wuxb.beartific.batisUtils.SQL.simplePOJOSQL.SimplePOJOSQLCreater;
import org.wuxb.beartific.batisUtils.SQL.simplePOJOSQL.impl.SimplePOJOSQLCreaterImpl;
import org.wuxb.beartific.batisUtils.SQLFactor.impl.WithRelationFieldFactor;
import org.wuxb.beartific.batisUtils.initStep.CreateStepinitialClass;
import org.wuxb.beartific.batisUtils.resultMapMaker.RelationM_T_OResultMapMaker;
import org.wuxb.beartific.batisUtils.resultMapMaker.RelationO_T_MResultMapMaker;
import org.wuxb.beartific.batisUtils.xmlWriteUtils.XMLWriteUtils;

public class RelationM_T_OXMLCreater {
	
	
	
	
	public String createMyBatisRelationManyPOJOXML(
												Class<?> Many_POJO,
												Class<?> One_POJO,
												String idType,
												String DAOPATH, 
												String basePath,
												String BASEPATH, 
												String MAPPERPATH,
												String DAOPACKAGENAME,
												String MAPPERPACKAGENAME,
												String One_POJO_tableName,
												String RESULTMAPPER_ID_NAME,
												String Many_POJO_tableName,
												String Many_POJOIdentification,
												String One_POJOIdentification,
												Map<String, String> collectionOfAbsolutePath,
												Map<String, Map<String, String>> collectionOfTableInfo,
												Map<String, Map<String, String>> collectionOfSqlInfo,
												Map<String, String> collectionOfJDBCAndJAVATypeConvert
			
			){
		/**
		 * 创建包,获取绝对路径
		 */
		basePath = CreateStepinitialClass.firstStepOfXmlCreate(Many_POJO, collectionOfJDBCAndJAVATypeConvert, basePath, 
				 DAOPACKAGENAME, MAPPERPACKAGENAME, collectionOfAbsolutePath, BASEPATH, 
				 DAOPATH, MAPPERPATH);
		 //System.out.println(basePath);
		/**
		 * 生成根XML和resultMap
		 */


		Document rootDocument = DocumentHelper.createDocument();
	     WithRelationFieldFactor factor = (WithRelationFieldFactor) RelationM_T_OResultMapMaker.MakeM_T_ORelationResultMap(rootDocument, Many_POJO, One_POJO, idType, DAOPATH, basePath, BASEPATH, MAPPERPATH, DAOPACKAGENAME, MAPPERPACKAGENAME, One_POJO_tableName, RESULTMAPPER_ID_NAME, Many_POJO_tableName, Many_POJOIdentification, One_POJOIdentification, collectionOfAbsolutePath, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);

		/**
		 * 生成SQL语句
		 */
	     writeRelationM_T_OXMLSQL(factor.getRelationField(), Many_POJO, One_POJO, factor.getIdType(), One_POJO_tableName, Many_POJO_tableName, factor.getRootElement(), Many_POJOIdentification, One_POJOIdentification, RESULTMAPPER_ID_NAME, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
	     //System.out.println(basePath);
	     //写xml
	     XMLWriteUtils.createXMLByRootDocument(rootDocument, Many_POJO, basePath, MAPPERPACKAGENAME);
	    
	     return basePath;
	}
	
	
	public String createMyBatisRelationOnePOJOXML(
			Class<?> Many_POJO,
			Class<?> One_POJO,
			String idType,
			String DAOPATH, 
			String basePath,
			String BASEPATH, 
			String MAPPERPATH,
			String DAOPACKAGENAME,
			String MAPPERPACKAGENAME,
			String One_POJO_tableName,
			String RESULTMAPPER_ID_NAME,
			String Many_POJO_tableName,
			String Many_POJOIdentification,
			String One_POJOIdentification,
			Map<String, String> collectionOfAbsolutePath,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert
			
			){
		
		
		Document rootDocument = DocumentHelper.createDocument();
		
		WithRelationFieldFactor factor = (WithRelationFieldFactor) RelationO_T_MResultMapMaker.MakeO_T_MRelationResultMap(rootDocument, Many_POJO, One_POJO, idType, DAOPATH, basePath, BASEPATH, MAPPERPATH, DAOPACKAGENAME, MAPPERPACKAGENAME, One_POJO_tableName, RESULTMAPPER_ID_NAME, Many_POJO_tableName, Many_POJOIdentification, One_POJOIdentification, collectionOfAbsolutePath, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
		
		writeRelationO_T_MXMLSQL(factor.getRelationField(), Many_POJO, One_POJO, factor.getIdType(), One_POJO_tableName, Many_POJO_tableName, factor.getRootElement(), Many_POJOIdentification, One_POJOIdentification, RESULTMAPPER_ID_NAME, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
		
		XMLWriteUtils.createXMLByRootDocument(rootDocument, One_POJO, basePath, MAPPERPACKAGENAME);
		
		
		
		return basePath;
	}
	
	
	private void writeRelationM_T_OXMLSQL(
									String M_T_O_Field,
									Class<?> Many_POJO,
									Class<?> One_POJO,
									String idType,
									String One_POJO_tableName,
									String Many_POJO_tableName,
								    Element rootElement,
								    String Many_POJOIdentification,
									String One_POJOIdentification,
									String RESULTMAPPER_ID_NAME,
									Map<String, Map<String, String>> collectionOfTableInfo,
									Map<String, Map<String, String>> collectionOfSqlInfo,
									Map<String, String> collectionOfJDBCAndJAVATypeConvert
								   
			
			){
		
		M_T_ORelationSQLCreater SQLCreater = new M_T_ORelationSQLCreaterImpl();
		
		SimplePOJOSQLCreater SQLCreaterSimple = new SimplePOJOSQLCreaterImpl();
		
		SQLCreater.manyToOneBaseColumnSQL(rootElement, Many_POJO_tableName, One_POJOIdentification, idType, RESULTMAPPER_ID_NAME, collectionOfSqlInfo, collectionOfTableInfo, collectionOfJDBCAndJAVATypeConvert);
		
		SQLCreater.manyToOneSelectByPageSQL(rootElement, Many_POJO, Many_POJO_tableName, One_POJO_tableName, One_POJOIdentification, RESULTMAPPER_ID_NAME, collectionOfSqlInfo,Many_POJOIdentification);
		
		SQLCreaterSimple.createSelectCountSQL(rootElement, Many_POJO_tableName, collectionOfSqlInfo);
		
		SQLCreaterSimple.createSimpleSelectList(rootElement, Many_POJO_tableName, Many_POJO, RESULTMAPPER_ID_NAME, collectionOfSqlInfo, Many_POJOIdentification);
		
	    SQLCreater.createManyToOneInsertSQL(rootElement, Many_POJO, Many_POJO_tableName, One_POJO_tableName, M_T_O_Field, One_POJOIdentification, idType, RESULTMAPPER_ID_NAME, collectionOfSqlInfo, collectionOfTableInfo, collectionOfJDBCAndJAVATypeConvert);
	    
	    SQLCreater.createManyToOneSelectById(rootElement, Many_POJO, Many_POJO_tableName, Many_POJOIdentification, idType, RESULTMAPPER_ID_NAME, collectionOfSqlInfo, collectionOfTableInfo, collectionOfJDBCAndJAVATypeConvert);
	    
	    SQLCreater.createManyToOneUpdateSQL(rootElement, Many_POJO, Many_POJO_tableName, One_POJO_tableName, M_T_O_Field, One_POJOIdentification, Many_POJOIdentification, idType, collectionOfSqlInfo, collectionOfTableInfo, collectionOfJDBCAndJAVATypeConvert);
	    
	    SQLCreaterSimple.createSimpleDeleteByIdSQL(rootElement, Many_POJO_tableName, Many_POJOIdentification, idType, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
	    
		
	}

	private void writeRelationO_T_MXMLSQL(
									String O_T_M_Field,
									Class<?> Many_POJO,
									Class<?> One_POJO,
									String idType,
									String One_POJO_tableName,
									String Many_POJO_tableName,
								    Element rootElement,
								    String Many_POJOIdentification,
									String One_POJOIdentification,
									String RESULTMAPPER_ID_NAME,
									Map<String, Map<String, String>> collectionOfTableInfo,
									Map<String, Map<String, String>> collectionOfSqlInfo,
									Map<String, String> collectionOfJDBCAndJAVATypeConvert
			){
		
		M_T_ORelationSQLCreater SQLCreater = new M_T_ORelationSQLCreaterImpl();
		SimplePOJOSQLCreater SimpleSQLCreater = new SimplePOJOSQLCreaterImpl();
		
		SQLCreater.createOneToManySelectListSQL(rootElement, One_POJO, One_POJO_tableName, Many_POJO_tableName, One_POJOIdentification, RESULTMAPPER_ID_NAME, collectionOfSqlInfo,Many_POJO,idType,collectionOfJDBCAndJAVATypeConvert);
		
		SQLCreater.createOneToManySelectByIdSQL(rootElement, One_POJO, One_POJO_tableName, Many_POJO_tableName, One_POJOIdentification, RESULTMAPPER_ID_NAME, idType, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
		
		SQLCreater.createOneToManyUpdateWithRelationSQL(rootElement, One_POJO, Many_POJO_tableName, One_POJOIdentification, One_POJO_tableName, Many_POJOIdentification, O_T_M_Field, idType, collectionOfJDBCAndJAVATypeConvert, collectionOfSqlInfo, collectionOfTableInfo);
		
		SQLCreater.createOneToManyDeleteSQL(rootElement, One_POJO_tableName, One_POJOIdentification, Many_POJO_tableName, idType, collectionOfJDBCAndJAVATypeConvert, collectionOfSqlInfo);
		
		SimpleSQLCreater.createSimpleInsertSQL(rootElement, One_POJO_tableName, One_POJO, collectionOfTableInfo, collectionOfSqlInfo);
		
		SimpleSQLCreater.createSimpleUpdateSelectiveSQL(rootElement, One_POJO_tableName, One_POJO, One_POJOIdentification, idType, collectionOfSqlInfo, collectionOfTableInfo, collectionOfJDBCAndJAVATypeConvert);
		
	};
}

