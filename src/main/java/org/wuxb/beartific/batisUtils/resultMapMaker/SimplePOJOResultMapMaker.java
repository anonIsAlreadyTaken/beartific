package org.wuxb.beartific.batisUtils.resultMapMaker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQLFactor.Factor;
import org.wuxb.beartific.batisUtils.SQLFactor.impl.IdTypeAndRootEleFactor;

public class SimplePOJOResultMapMaker {
	
	public static Factor MakeSimpleResultMap(Document rootDocument,
												Class<?> cls,
												String idType,
												String DAOPATH, 
												String basePath,
												String BASEPATH, 
												String tableName,
												String MAPPERPATH,
												String identification,
												String DAOPACKAGENAME,
												String MAPPERPACKAGENAME,
												String RESULTMAPPER_ID_NAME,
												Map<String, String> collectionOfAbsolutePath,
												Map<String, Map<String, String>> collectionOfTableInfo,
												Map<String, Map<String, String>> collectionOfSqlInfo,
												Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		
		rootDocument.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
	     //�����
	     Element rootElement = rootDocument.addElement("mapper");
	     String mapperDAOPathWay = basePath+".DAO."+cls.getSimpleName()+"Mapper";
	     cls.getPackage();
	     rootElement.addAttribute("namespace", mapperDAOPathWay);
	     //�ڸ��мӵ�һ���ӽڵ�
	     Element elementResultMap = rootElement.addElement("resultMap");
	     elementResultMap.addAttribute("id", cls.getSimpleName()+RESULTMAPPER_ID_NAME);
	     elementResultMap.addAttribute("type", cls.getName());
	     Element ElementClassID = elementResultMap.addElement("id");
	     Field[] declaredFields = cls.getDeclaredFields();
	     HashMap<String, String> tableInfo = new HashMap<String, String>();
	     //tableInfo.put("tableName", tableName);
	     for (Field field : declaredFields) {
	    	 
	    	 if(field.getName().equals(identification)){
	    		 
	    		 ElementClassID.addAttribute("column", identification);
	    		 ElementClassID.addAttribute("property", identification);
	    		 idType = field.getType().getName();
	    		 ElementClassID.addAttribute("javaType", idType);
	    		 String JDBCTypeOfId = collectionOfJDBCAndJAVATypeConvert.get(idType);
	    		 ElementClassID.addAttribute("jdbcType", JDBCTypeOfId);
	    		 tableInfo.put(identification, JDBCTypeOfId);
	    	 }else{
	    		 
	    		 Element ElementProperty = elementResultMap.addElement("result");
	    		 ElementProperty.addAttribute("column", field.getName());
	    		 ElementProperty.addAttribute("property", field.getName());
	    		 ElementProperty.addAttribute("javaType", field.getType().getName());
	    		 String JDBCTypeOfResult = collectionOfJDBCAndJAVATypeConvert.get(field.getType().getName());
	    		 ElementProperty.addAttribute("jdbcType", JDBCTypeOfResult);
	    		 tableInfo.put(field.getName(), JDBCTypeOfResult);
	    	 }
	    	 
			
		}
	     //System.out.println(idType);
	     
	     collectionOfTableInfo.put(tableName, tableInfo);
		
	     
	     
		return new IdTypeAndRootEleFactor(idType, rootElement);
	};
	

}
