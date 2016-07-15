package org.wuxb.beartific.batisUtils.resultMapMaker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQLFactor.Factor;
import org.wuxb.beartific.batisUtils.SQLFactor.impl.WithRelationFieldFactor;

public class RelationM_T_MResultMapMaker {

	public static Factor MakeM_T_MResultMap(Document rootDocument,
			Class<?> ManyPOJO1, String identification1, String tableName1,
			Class<?> ManyPOJO2, String identification2, String tableName2,
			String MappingTableName, String idType, String DAOPATH,
			String basePath, String BASEPATH, String MAPPERPATH,
			String DAOPACKAGENAME, String MAPPERPACKAGENAME,
			String RESULTMAPPER_ID_NAME,
			Map<String, String> collectionOfAbsolutePath,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert) {

		
		
		 String M_T_M_Field_1 = "";
		 RESULTMAPPER_ID_NAME = "BaseResultMap";
		 
	     rootDocument.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
	     //�����
	     Element rootElement = rootDocument.addElement("mapper");
	     String mapperDAOPathWay = basePath+".DAO."+ManyPOJO1.getSimpleName()+"Mapper";
	     ManyPOJO1.getPackage();
	     rootElement.addAttribute("namespace", mapperDAOPathWay);
	   //�ڸ��мӵ�һ���ӽڵ�
	     Element elementResultMap = rootElement.addElement("resultMap");
	     RESULTMAPPER_ID_NAME = ManyPOJO1.getSimpleName()+RESULTMAPPER_ID_NAME;
	     elementResultMap.addAttribute("id", RESULTMAPPER_ID_NAME);
	     elementResultMap.addAttribute("type", ManyPOJO1.getName());
	     Element ElementClassID = elementResultMap.addElement("id");
	     Field[]declaredFields = ManyPOJO1.getDeclaredFields();
	     HashMap<String, String> tableInfoMany1 = new HashMap<String, String>();
	     //tableInfo.put("tableName", tableName);
	     /*
	      * ����ResultMap,��װ��collectionOfTableInfo�б���Ϣ
	      */
	     for (Field Manyfield1 : declaredFields) {
	    	 
	    	 
	    	 boolean isCollectionValue = Manyfield1.getGenericType().toString().contains("<"+ManyPOJO2.getName()+">");
		    
	    	// System.out.println(Manyfield1.getGenericType().toString());
	    	 
	    	 
	    	 
	    	 if(Manyfield1.getName().equals(identification1)){
	    		 
	    		 ElementClassID.addAttribute("column", identification1);
	    		 ElementClassID.addAttribute("property", identification1);
	    		 idType = Manyfield1.getType().getName();
	    		 ElementClassID.addAttribute("javaType", idType);
	    		 String JDBCTypeOfId = collectionOfJDBCAndJAVATypeConvert.get(idType);
	    		 ElementClassID.addAttribute("jdbcType", JDBCTypeOfId);
	    		 tableInfoMany1.put(identification1, JDBCTypeOfId);
	    		 
	    	 }else if(isCollectionValue){
	    	 
	    		 M_T_M_Field_1 = Manyfield1.getName();
	    		 
	    		 
	    		 /*
	    		  * 
	    		  * <collection property="userRole" ofType="org.wuxb.testMTMEntity.Role" select="selectRoleBySid" column="sid">
	      			<id property="rid" column="rid" javaType="java.lang.Integer" jdbcType="INTEGER"/>
	      			<result column="roleName" property="roleName" javaType="java.lang.String" jdbcType="VARCHAR"/>
	    			</collection>
	    		  * 
	    		  */
	    		 
	    		 
	    		 
	    		 Element ElementCollection = elementResultMap.addElement("collection");
	    		 ElementCollection.addAttribute("property", M_T_M_Field_1);
	    		 ElementCollection.addAttribute("ofType", ManyPOJO2.getName());
	    		 ElementCollection.addAttribute("select", "select"+ManyPOJO2.getSimpleName()+"By"+identification1);
	    		 ElementCollection.addAttribute("column", identification1);
	    		 
	    		 Element ElementCollectionManyPOJOId = ElementCollection.addElement("id");
	    		 ElementCollectionManyPOJOId.addAttribute("property", identification2);
	    		 ElementCollectionManyPOJOId.addAttribute("column", identification2);
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 for (Field ManyPOJOfield2 : ManyPOJO2.getDeclaredFields()) {
	    			 
	    			 if(ManyPOJOfield2.getName().equals(identification2)){
	    				 
	    				 String JDBCTypeOfOnePOJOId = collectionOfJDBCAndJAVATypeConvert.get(ManyPOJOfield2.getType().getName());
	    	    		 ElementCollectionManyPOJOId.addAttribute("javaType", ManyPOJOfield2.getType().getName());
	    	    		 ElementCollectionManyPOJOId.addAttribute("jdbcType", JDBCTypeOfOnePOJOId);
	    			 }
	    			 else{
	    				 
	    				 String JDBCTypeOfResult = collectionOfJDBCAndJAVATypeConvert.get(ManyPOJOfield2.getType().getName());
	    				 if(JDBCTypeOfResult!=null){
	    					 
	    					 Element Many_ResultEle = ElementCollection.addElement("result");
	    					 Many_ResultEle.addAttribute("column", ManyPOJOfield2.getName());
	    					 Many_ResultEle.addAttribute("property", ManyPOJOfield2.getName());
	    					 Many_ResultEle.addAttribute("javaType", ManyPOJOfield2.getType().getName());
	    					 Many_ResultEle.addAttribute("jdbcType", JDBCTypeOfResult);
	    				 }
	    			 }
					
				}
	    		 
	    		 
	    	 
	    	 }else{
	    		 
	    		 Element ElementProperty = elementResultMap.addElement("result");
	    		 ElementProperty.addAttribute("column", Manyfield1.getName());
	    		 ElementProperty.addAttribute("property", Manyfield1.getName());
	    		 ElementProperty.addAttribute("javaType", Manyfield1.getType().getName());
	    		 String JDBCTypeOfResult = collectionOfJDBCAndJAVATypeConvert.get(Manyfield1.getType().getName());
	    		 ElementProperty.addAttribute("jdbcType", JDBCTypeOfResult);
	    		 tableInfoMany1.put(Manyfield1.getName(), JDBCTypeOfResult);
	    	 }
	    	 
			
		 }
	     
	     collectionOfTableInfo.put(tableName1, tableInfoMany1);
		
		
		

		return new WithRelationFieldFactor(idType, rootElement, M_T_M_Field_1);
	}

}
