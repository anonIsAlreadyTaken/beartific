package org.wuxb.beartific.batisUtils.resultMapMaker;

import java.lang.reflect.Field;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQLFactor.Factor;
import org.wuxb.beartific.batisUtils.SQLFactor.impl.WithRelationFieldFactor;

public class RelationO_T_MResultMapMaker {
	
	public static Factor MakeO_T_MRelationResultMap(Document rootDocument,
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
		
		
	     
	     String O_T_M_Field = "";
		 
		 
	     rootDocument.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
	     //创建根
	     Element rootElement = rootDocument.addElement("mapper");
	     String mapperDAOPathWay = basePath+".DAO."+One_POJO.getSimpleName()+"Mapper";
	     Many_POJO.getPackage();
	     rootElement.addAttribute("namespace", mapperDAOPathWay);
	     //添加首个元素
	     Element elementResultMap = rootElement.addElement("resultMap");
	     RESULTMAPPER_ID_NAME = One_POJO.getSimpleName()+RESULTMAPPER_ID_NAME;
	     elementResultMap.addAttribute("id", RESULTMAPPER_ID_NAME);
	     elementResultMap.addAttribute("type", One_POJO.getName());
	     Element ElementClassID = elementResultMap.addElement("id");
	     Field[]declaredFields = One_POJO.getDeclaredFields();

		//tableInfo.put("tableName", tableName);
	     /*
	      * 创建ResultMap,并装入collectionOfTableInfo中表信息
	      */
	     for (Field Onefield : declaredFields) {
	    	 
	    	 /**
	    	  * ----------------------2.22------------------------------
	    	  */
	    	/*
	    	  * 验证是否是Collection
	    	  */
	    	 boolean isCollectionValue = Onefield.getGenericType().toString().contains("<"+Many_POJO.getName()+">");
	    	
	    	 
	    	
	    	 
	    	 if(Onefield.getName().equals(One_POJOIdentification)){
	    		 
	    		 ElementClassID.addAttribute("column", One_POJOIdentification);
	    		 ElementClassID.addAttribute("property", One_POJOIdentification);
	    		 idType = Onefield.getType().getName();
	    		 ElementClassID.addAttribute("javaType", idType);
	    		 String JDBCTypeOfId = collectionOfJDBCAndJAVATypeConvert.get(idType);
	    		 ElementClassID.addAttribute("jdbcType", JDBCTypeOfId);
	    		 
	    		 
	    	 }else if(isCollectionValue){
	    	 
	    		 O_T_M_Field = Onefield.getName();
	    		 
	    		 Element ElementCollection = elementResultMap.addElement("collection");
	    		 ElementCollection.addAttribute("property", O_T_M_Field);
	    		 ElementCollection.addAttribute("ofType", Many_POJO.getName());
	    		 ElementCollection.addAttribute("select", "select"+Many_POJO.getSimpleName()+"By"+One_POJOIdentification);
	    		 ElementCollection.addAttribute("column", One_POJOIdentification);
	    		 Element ElementCollectionManyPOJOId = ElementCollection.addElement("id");
	    		 ElementCollectionManyPOJOId.addAttribute("property", Many_POJOIdentification);
	    		 ElementCollectionManyPOJOId.addAttribute("column", Many_POJOIdentification);
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 for (Field ManyPOJOfield : Many_POJO.getDeclaredFields()) {
	    			 
	    			 if(ManyPOJOfield.getName().equals(Many_POJOIdentification)){
	    				 
	    				 String JDBCTypeOfOnePOJOId = collectionOfJDBCAndJAVATypeConvert.get(ManyPOJOfield.getType().getName());
	    	    		 ElementCollectionManyPOJOId.addAttribute("javaType", ManyPOJOfield.getType().getName());
	    	    		 ElementCollectionManyPOJOId.addAttribute("jdbcType", JDBCTypeOfOnePOJOId);
	    			 }
	    			 else{
	    				 
	    				 String JDBCTypeOfResult = collectionOfJDBCAndJAVATypeConvert.get(ManyPOJOfield.getType().getName());
	    				 if(JDBCTypeOfResult!=null){
	    					 
	    					 Element Many_ResultEle = ElementCollection.addElement("result");
	    					 Many_ResultEle.addAttribute("column", ManyPOJOfield.getName());
	    					 Many_ResultEle.addAttribute("property", ManyPOJOfield.getName());
	    					 Many_ResultEle.addAttribute("javaType", ManyPOJOfield.getType().getName());
	    					 Many_ResultEle.addAttribute("jdbcType", JDBCTypeOfResult);
	    				 }
	    			 }
					
				}
	    		 
	    	 
	    	 }else{
	    		 
	    		 Element ElementProperty = elementResultMap.addElement("result");
	    		 ElementProperty.addAttribute("column", Onefield.getName());
	    		 ElementProperty.addAttribute("property", Onefield.getName());
	    		 ElementProperty.addAttribute("javaType", Onefield.getType().getName());
	    		 String JDBCTypeOfResult = collectionOfJDBCAndJAVATypeConvert.get(Onefield.getType().getName());
	    		 ElementProperty.addAttribute("jdbcType", JDBCTypeOfResult);
	    	 }
	    	 
			
		 }
	     
		return new WithRelationFieldFactor(idType, rootElement,O_T_M_Field);
	};
	

}
