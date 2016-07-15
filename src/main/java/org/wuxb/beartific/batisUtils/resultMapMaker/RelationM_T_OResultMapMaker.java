package org.wuxb.beartific.batisUtils.resultMapMaker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQLFactor.Factor;
import org.wuxb.beartific.batisUtils.SQLFactor.impl.WithRelationFieldFactor;

public class RelationM_T_OResultMapMaker {
	
	public static Factor MakeM_T_ORelationResultMap(Document rootDocument,
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
												Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		
		String M_T_O_Field = "";
		 
	     rootDocument.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
	     //�����
	     Element rootElement = rootDocument.addElement("mapper");
	     String mapperDAOPathWay = basePath+".DAO."+Many_POJO.getSimpleName()+"Mapper";
	     Many_POJO.getPackage();
	     rootElement.addAttribute("namespace", mapperDAOPathWay);
	     //�ڸ��мӵ�һ���ӽڵ�
	     Element elementResultMap = rootElement.addElement("resultMap");
	     RESULTMAPPER_ID_NAME = Many_POJO.getSimpleName()+RESULTMAPPER_ID_NAME;
	     elementResultMap.addAttribute("id", RESULTMAPPER_ID_NAME);
	     elementResultMap.addAttribute("type", Many_POJO.getName());
	     Element ElementClassID = elementResultMap.addElement("id");
	     Field[] declaredFields = Many_POJO.getDeclaredFields();
	     HashMap<String, String> tableInfoMany = new HashMap<String, String>();
	     HashMap<String, String> tableInfoOne = new HashMap<String, String>();
	     //tableInfo.put("tableName", tableName);
	     /*
	      * ����ResultMap,��װ��collectionOfTableInfo�б���Ϣ
	      */
	     for (Field Manyfield : declaredFields) {
	    	 
	    	 if(Manyfield.getName().equals(Many_POJOIdentification)){
	    		 
	    		 ElementClassID.addAttribute("column", Many_POJOIdentification);
	    		 ElementClassID.addAttribute("property", Many_POJOIdentification);
	    		 idType = Manyfield.getType().getName();
	    		 ElementClassID.addAttribute("javaType", idType);
	    		 String JDBCTypeOfId = collectionOfJDBCAndJAVATypeConvert.get(idType);
	    		 ElementClassID.addAttribute("jdbcType", JDBCTypeOfId);
	    		 tableInfoMany.put(Many_POJOIdentification, JDBCTypeOfId);
	    		 
	    	 }else if(Manyfield.getType().getSimpleName().equals(One_POJO.getSimpleName())){
	    	 
	    		 M_T_O_Field = Manyfield.getName();
	    		 
	    		 Element ElementAssociation = elementResultMap.addElement("association");
	    		 ElementAssociation.addAttribute("property", Manyfield.getName());
	    		 ElementAssociation.addAttribute("javaType", Manyfield.getType().getName());
	    		 Element ElementAssociationManyPOJOId = ElementAssociation.addElement("id");
	    		 ElementAssociationManyPOJOId.addAttribute("property", One_POJOIdentification);
	    		 ElementAssociationManyPOJOId.addAttribute("column", One_POJOIdentification);
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 for (Field OnePOJOfield : One_POJO.getDeclaredFields()) {
	    			 
	    			 if(OnePOJOfield.getName().equals(One_POJOIdentification)){
	    				 
	    				 String JDBCTypeOfOnePOJOId = collectionOfJDBCAndJAVATypeConvert.get(OnePOJOfield.getType().getName());
	    	    		 ElementAssociationManyPOJOId.addAttribute("javaType", OnePOJOfield.getType().getName());
	    	    		 ElementAssociationManyPOJOId.addAttribute("jdbcType", JDBCTypeOfOnePOJOId);
	    	    		 tableInfoOne.put(One_POJOIdentification, JDBCTypeOfOnePOJOId);
	    			 }
	    			 else{
	    				 
	    				 String JDBCTypeOfResult = collectionOfJDBCAndJAVATypeConvert.get(OnePOJOfield.getType().getName());
	    				 if(JDBCTypeOfResult!=null){
	    					 
	    					 Element One_ResultEle = ElementAssociation.addElement("result");
	    					 One_ResultEle.addAttribute("column", OnePOJOfield.getName());
	    					 One_ResultEle.addAttribute("property", OnePOJOfield.getName());
	    					 One_ResultEle.addAttribute("javaType", OnePOJOfield.getType().getName());
	    					 One_ResultEle.addAttribute("jdbcType", JDBCTypeOfResult);
	    					 tableInfoOne.put(OnePOJOfield.getName(), JDBCTypeOfResult);
	    				 }
	    			 }
					
				}
	    		 
	    	 
	    	 }else{
	    		 
	    		 Element ElementProperty = elementResultMap.addElement("result");
	    		 ElementProperty.addAttribute("column", Manyfield.getName());
	    		 ElementProperty.addAttribute("property", Manyfield.getName());
	    		 ElementProperty.addAttribute("javaType", Manyfield.getType().getName());
	    		 String JDBCTypeOfResult = collectionOfJDBCAndJAVATypeConvert.get(Manyfield.getType().getName());
	    		 ElementProperty.addAttribute("jdbcType", JDBCTypeOfResult);
	    		 tableInfoMany.put(Manyfield.getName(), JDBCTypeOfResult);
	    	 }
	    	 
			
		 }
	     
	     /*
	      * Tip:
	      * ����ͨ�����One����������ʱʹ��tType.tid�ķ�ʽ��������Many���ж����tid�ֶ�
	      * 
	      * <sql id=base_Column_List>
	      */
	     
	     collectionOfTableInfo.put(One_POJO_tableName, tableInfoOne);
	     collectionOfTableInfo.put(Many_POJO_tableName, tableInfoMany);
		
	     
	     
		return new WithRelationFieldFactor(idType, rootElement,M_T_O_Field);
	};
	

}
