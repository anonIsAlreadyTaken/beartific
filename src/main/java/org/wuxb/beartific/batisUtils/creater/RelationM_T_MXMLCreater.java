package org.wuxb.beartific.batisUtils.creater;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQL.M_T_MRelationSQL.M_T_MRelationSQLCreater;
import org.wuxb.beartific.batisUtils.SQL.M_T_MRelationSQL.impl.M_T_MRelationSQLCreaterImpl;
import org.wuxb.beartific.batisUtils.SQL.simplePOJOSQL.SimplePOJOSQLCreater;
import org.wuxb.beartific.batisUtils.SQL.simplePOJOSQL.impl.SimplePOJOSQLCreaterImpl;
import org.wuxb.beartific.batisUtils.SQLFactor.impl.WithRelationFieldFactor;
import org.wuxb.beartific.batisUtils.initStep.CreateStepinitialClass;
import org.wuxb.beartific.batisUtils.resultMapMaker.RelationM_T_MResultMapMaker;
import org.wuxb.beartific.batisUtils.xmlWriteUtils.XMLWriteUtils;

public class RelationM_T_MXMLCreater {

	public String createMyBatisRelationM_T_MPOJOXML(Class<?> ManyPOJO1,
			String identification1, String tableName1, Class<?> ManyPOJO2,
			String identification2, String tableName2, String MappingTableName,
			String idType, String DAOPATH, String basePath, String BASEPATH,
			String MAPPERPATH, String DAOPACKAGENAME, String MAPPERPACKAGENAME,
			String RESULTMAPPER_ID_NAME,
			Map<String, String> collectionOfAbsolutePath,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert) {
		
		
		basePath = CreateStepinitialClass.firstStepOfXmlCreate(ManyPOJO1, collectionOfJDBCAndJAVATypeConvert, basePath, 
				 DAOPACKAGENAME, MAPPERPACKAGENAME, collectionOfAbsolutePath, BASEPATH, 
				 DAOPATH, MAPPERPATH);

		/**
		 * 生成根XML和resultMap
		 */
		 
		 Document rootDocument = DocumentHelper.createDocument();
		 WithRelationFieldFactor factor = (WithRelationFieldFactor) RelationM_T_MResultMapMaker.MakeM_T_MResultMap(rootDocument,
	    		 ManyPOJO1,
	 			 identification1, tableName1,
	 			 ManyPOJO2,
	 			 identification2,tableName2,
	 			 MappingTableName,
	    		 idType, DAOPATH, basePath, 
	    		 BASEPATH,  MAPPERPATH,  DAOPACKAGENAME, MAPPERPACKAGENAME, RESULTMAPPER_ID_NAME, 
	    		 collectionOfAbsolutePath, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
	    
	     writeRelationM_T_MXMLSQL(factor.getRootElement(), factor.getRelationField(), ManyPOJO1, identification1, tableName1, ManyPOJO2, identification2, tableName2, MappingTableName, factor.getIdType(), DAOPATH, basePath, BASEPATH, MAPPERPATH, DAOPACKAGENAME, MAPPERPACKAGENAME, RESULTMAPPER_ID_NAME, collectionOfAbsolutePath, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
		
	     XMLWriteUtils.createXMLByRootDocument(rootDocument, ManyPOJO1, basePath, MAPPERPACKAGENAME);
	     
	     return basePath;
	}
	
	
	private void writeRelationM_T_MXMLSQL(Element rootElement,String M_T_M_Field_1,
			Class<?> ManyPOJO1,
			String identification1, String tableName1, 
			Class<?> ManyPOJO2,
			String identification2, String tableName2, 
			String MappingTableName,
			String idType, String DAOPATH, String basePath, String BASEPATH,
			String MAPPERPATH, String DAOPACKAGENAME, String MAPPERPACKAGENAME,
			String RESULTMAPPER_ID_NAME,
			Map<String, String> collectionOfAbsolutePath,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		M_T_MRelationSQLCreater SQLCreater = new M_T_MRelationSQLCreaterImpl();
		SimplePOJOSQLCreater SimpleSQLCreater = new SimplePOJOSQLCreaterImpl();
		
		SQLCreater.createManyToManySelectListByPageSQL(rootElement, ManyPOJO1, identification1, tableName1, ManyPOJO2, identification2, tableName2, MappingTableName, idType, RESULTMAPPER_ID_NAME, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
		
		SimpleSQLCreater.createSelectCountSQL(rootElement, tableName1, collectionOfSqlInfo);
		
		SimpleSQLCreater.createSimpleSelectList(rootElement, tableName1, ManyPOJO1, RESULTMAPPER_ID_NAME, collectionOfSqlInfo, identification1);
		
		SQLCreater.createManyToManyInsertWithRelationSQL(rootElement, ManyPOJO1, identification1, tableName1, ManyPOJO2, identification2, tableName2, MappingTableName, M_T_M_Field_1, collectionOfSqlInfo, collectionOfTableInfo);
		
		SQLCreater.createManyToManyDeleteWithRelationSQL(rootElement, identification1, tableName1, MappingTableName, idType, collectionOfSqlInfo, collectionOfTableInfo, collectionOfJDBCAndJAVATypeConvert);
		
		SQLCreater.createManyToManySelectByPrimaryKeySQL(rootElement, ManyPOJO1, tableName1, identification1, idType, RESULTMAPPER_ID_NAME, collectionOfSqlInfo, collectionOfTableInfo, collectionOfJDBCAndJAVATypeConvert);
		
		SQLCreater.createManyToManyUpdateByPrimaryKeySQL(rootElement, ManyPOJO1, identification1, identification2, tableName1, MappingTableName, M_T_M_Field_1, idType, collectionOfSqlInfo, collectionOfTableInfo, collectionOfJDBCAndJAVATypeConvert);
	}

}
