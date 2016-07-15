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
		 * ������,��ȡ���·��
		 */
		basePath = CreateStepinitialClass.firstStepOfXmlCreate(cls, collectionOfJDBCAndJAVATypeConvert, basePath, 
				 DAOPACKAGENAME, MAPPERPACKAGENAME, collectionOfAbsolutePath, BASEPATH, 
				 DAOPATH, MAPPERPATH);
		 //System.out.println(basePath);
		 /**
		  * ��ɸ�XML��resultMap
		  */
		 
		 Document rootDocument = DocumentHelper.createDocument();
	     Factor factor = SimplePOJOResultMapMaker.MakeSimpleResultMap(rootDocument, cls, idType, DAOPATH, basePath, 
	    		 BASEPATH, tableName, MAPPERPATH, identification, DAOPACKAGENAME, MAPPERPACKAGENAME, RESULTMAPPER_ID_NAME, 
	    		 collectionOfAbsolutePath, collectionOfTableInfo, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
	    
	     /**
	      * ���SQL���
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
	      * <sql/>�ڵ� ����SQL
	      * 
	      * */
	     SQLCreater.createBaseColumnSQL(rootElement, tableName, collectionOfTableInfo);
	     
	     /*
	      * <select id='selectByPageNo'/>�ڵ� ��ҳ�б�
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
	      * <select id='selectByPrimaryKey'/>�ڵ� ��ݱ�ʶ���Բ��Ҷ���
	      */
	     SQLCreater.createSimpleSelectByIdSQL(rootElement, tableName, cls, identification, RESULTMAPPER_ID_NAME, idType, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
	     /*
	      * 2/20:��һ����ҳ��ѯSQL�����ɳɹ�
	      * TIP:Ѱ��DOM4J���XML���з���(�ѽ��:�Զ���OutPutFormat,����ת��)
	      * 
	      * ===================================================================================
	      * 
	      * 
	      * */
	     /*
	      * <delete id='deleteByPrimaryKey'/>�ڵ� ɾ�����
	      */
	     SQLCreater.createSimpleDeleteByIdSQL(rootElement, tableName, identification, idType, collectionOfSqlInfo, collectionOfJDBCAndJAVATypeConvert);
	     
	     /*
	      * <insert id='insertSelective'/>�ڵ� ���Ӷ���
	      */
	     SQLCreater.createSimpleInsertSQL(rootElement, tableName, cls, collectionOfTableInfo, collectionOfSqlInfo);
	    
	     /*
	      * <update id='updateByPrimaryKeySelective'/>�ڵ� �޸Ķ���(���ɸ���)
	      */
	     SQLCreater.createSimpleUpdateSelectiveSQL(rootElement, tableName, cls, identification, idType, collectionOfSqlInfo, collectionOfTableInfo, collectionOfJDBCAndJAVATypeConvert);
	     
	    
		
	}

}
