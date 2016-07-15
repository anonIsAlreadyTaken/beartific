package org.wuxb.beartific.batisUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public class PasteFactory {
	
	/**
	 * ȡ���·��ʱ��Key
	 */
	private final String BASEPATH = "basePathLocation";
	private final String DAOPATH = "DAOPathLocation";
	private final String MAPPERPATH = "MapperPathLocation";
	
	/**
	 * ��Ŀ¼ eg:org.wuxb
	 */
	private String basePath;
	/**
	 * ������String
	 */
	private final String DAOPACKAGENAME = "DAO\\";
	private final String MAPPERPACKAGENAME = "Mapper\\";
	/**
	 * id���Ե�Java����
	 */
	private String idType;
	/**
	 * DAO,Mapper,�ͻ��ľ��·��
	 */
	private Map<String, String> collectionOfAbsolutePath = new HashMap<String, String>();
	/**
	 * JAVA���JDBC������Ͷ�Ӧ��Ϣ����
	 */
	private Map<String, String> collectionOfJDBCAndJAVATypeConvert = new HashMap<String, String>();
	/**
	 * ����Ϣ������K:����,V:�ֶ���Ϣ
	 */
	private Map<String, Map<String, String>> collectionOfTableInfo = new HashMap<String, Map<String, String>>();
	/**
	 * sql�����Ϣ���� K:sql���ID,V:��������ֵ
	 */
	private Map<String, Map<String, String>> collectionOfSqlInfo = new HashMap<String, Map<String, String>>();
	/**
	 * ��׼MapperResult��ID
	 */
	private String RESULTMAPPER_ID_NAME = "BaseResultMap";
	
	
	public static void main(String[] args) throws IOException {
	
		
		
		//PasteFactory pf = new PasteFactory();
		//pf.createMyBatisMany_TO_OneXML(Emp.class, "id", "t_emp", Edu.class, "eid", "t_edu");
		//pf.createMyBatisPOJOXML(POJOXmlTest.class, "pid", "M_T");
		//pf.createMyBatisMany_To_ManyXML(Role.class, "rid", "ssh_role", User.class, "sid", "ssh_user", "ssh_user_role");
		//pf.createMyBatisMany_To_ManyXML(User.class, "sid", "ssh_user", Role.class, "rid", "ssh_role", "ssh_user_role");
	}
	
	
	
	/**
	 * @serialNo 1.ִ������1
	 * @function ����JAVA��JDBC����ת������
	 * @author ���಩
	 * @time 2016-2-21 ����6:02:33
	 */
	private void buildJDBCAndJavaTypeConvert(){
		
		collectionOfJDBCAndJAVATypeConvert.put("java.lang.Integer", "INTEGER");
		collectionOfJDBCAndJAVATypeConvert.put("java.lang.String", "VARCHAR");
		collectionOfJDBCAndJAVATypeConvert.put("java.lang.Double", "DOUBLE");
		collectionOfJDBCAndJAVATypeConvert.put("java.lang.Float", "FLOAT");
		collectionOfJDBCAndJAVATypeConvert.put("java.util.Date", "DATE");
		collectionOfJDBCAndJAVATypeConvert.put("java.sql.Date", "DATE");
		collectionOfJDBCAndJAVATypeConvert.put("double", "DOUBLE");
		collectionOfJDBCAndJAVATypeConvert.put("int", "INTEGER");
		
		
	}
	
	/**
	 * @serialNo 2.ִ������2
	 * @function ����DAO���Mapper��
	 * @author ���಩
	 * @time 2016-2-18 ����9:38:12
	 */
	private Map<String, String> makeDAOAndMapperPackage(Class<?> c) throws IOException{
		
		//��ð���
		String packageName = c.getPackage().getName();
		int lastIndexOfSpace = packageName.lastIndexOf(" ");
		//�����õİ���
		String forUserPackageName = packageName.substring(lastIndexOfSpace+1);
		//�����õİ���
		int lastIndexOfCut = packageName.lastIndexOf('.');
		basePath = packageName.substring(lastIndexOfSpace+1,lastIndexOfCut);
		//�õ������������·��
		String entityLocation = "src/"+forUserPackageName.replace('.','/')+"/"+c.getSimpleName()+".java";
		//�õ��������ľ��·��
		File file = new File(entityLocation);
		String absoluteEntityLocation = file.getAbsolutePath();
		//������·����ƴ��DAO����Mapper���ַ
		int indexOf = absoluteEntityLocation.lastIndexOf('\\');
		String middleElement = absoluteEntityLocation.substring(0, indexOf);
		String basePath = middleElement.substring(0, middleElement.lastIndexOf('\\'))+"\\";
		String DaoPathWay =  basePath+DAOPACKAGENAME;
		File file3 = new File(DaoPathWay);
		file3.mkdir();
		String mapperPathWay = basePath+MAPPERPACKAGENAME;
		File file4 = new File(mapperPathWay);
		file4.mkdir();
		
	
	
	collectionOfAbsolutePath.put(BASEPATH, basePath);
	collectionOfAbsolutePath.put(DAOPATH, DaoPathWay);
	collectionOfAbsolutePath.put(MAPPERPATH, mapperPathWay);
	
	
	
	return collectionOfAbsolutePath;
		
	}
	
	/**
	 * 
	 * @function �������1,2
	 * @author ���಩
	 * @time 2016-2-21 ����7:12:59
	 * @param cls �����
	 */
	private void firstStepOfXmlCreate(Class<?> cls){
		
		 buildJDBCAndJavaTypeConvert();
		 try {
			makeDAOAndMapperPackage(cls);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * @serialNo 3.ִ������3
	 * @function �����򵥵�����ɾ�Ĳ�XML
	 * @author ���಩
	 * @time 2016-2-21 ����6:04:34
	 * @param cls ������������
	 * @param identification ����ı�ʶ����
	 * @param tableName �����Ӧ�ı���
	 */
	public void createMyBatisPOJOXML(Class<?> cls,String identification,String tableName){
		
		 firstStepOfXmlCreate(cls);
		 
		 Document rootDocument = DocumentHelper.createDocument();
	     rootDocument.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
	     //�����
	     Element rootElement = rootDocument.addElement("mapper");
	     String mapperDAOPathWay = basePath+".DAO."+cls.getSimpleName()+"Mapper";
	     cls.getPackage();
	     rootElement.addAttribute("namespace", mapperDAOPathWay);
	     //�ڸ��мӵ�һ���ӽڵ�
	     Element elementResultMap = rootElement.addElement("resultMap");
	     elementResultMap.addAttribute("id", RESULTMAPPER_ID_NAME);
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
	     
	     collectionOfTableInfo.put(tableName, tableInfo);
	     
	     /*
	      * 
	      * <sql/>�ڵ� ����SQL
	      * 
	      * */
	     createBaseColumnSQL(rootElement,tableName);
	     /*
	      * <select id='selectByPageNo'/>�ڵ� ��ҳ�б�
	      */
	     createSimpleSelectByPageSQL(rootElement,tableName,cls);
	     
	     
	     /*
	      * <select id="getCount" resultType="int" >
		  *  SELECT COUNT(*) FROM t_person
		  *  </select>
	      */
	     createSelectCountSQL(rootElement,tableName);
	     
	     /*
	      * <select id='selectByPrimaryKey'/>�ڵ� ��ݱ�ʶ���Բ��Ҷ���
	      */
	     createSimpleSelectByIdSQL(rootElement,tableName,cls,identification);
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
	     createSimpleDeleteByIdSQL(rootElement,tableName,identification);
	     /*
	      * <insert id='insertSelective'/>�ڵ� ���Ӷ���
	      */
	     createSimpleInsertSQL(rootElement, tableName, cls);
	    
	     /*
	      * <update id='updateByPrimaryKeySelective'/>�ڵ� �޸Ķ���(���ɸ���)
	      */
	     createSimpleUpdateSelectiveSQL(rootElement, tableName, cls, identification);
	     
	    
	     
	     //дxml
	   
	     createXMLByRootDocument(rootDocument,cls);
		
	    
	}

	/**
	 * 
	 * @function ���һ�����ļ����
	 * @author ���಩
	 * @time 2016-2-21 ����8:20:52
	 */
	public void createMyBatisMany_TO_OneXML(Class<?> Many_POJO,String Many_POJOIdentification,String Many_POJO_tableName,Class<?> One_POJO,String One_POJOIdentification,String One_POJO_tableName){
		
		/*
		 * ---------------------------------MANY_POJO------------------------------------------
		 */
		
		firstStepOfXmlCreate(Many_POJO);
		
		String M_T_O_Field = "";
		 
		 Document rootDocument = DocumentHelper.createDocument();
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
		
	    
	     manyToOneBaseColumnSQL(rootElement,Many_POJO_tableName,One_POJOIdentification);
	     
	     /*
	      * ���������ҳ
	      */
	     manyToOneSelectByPageSQL(rootElement,Many_POJO,Many_POJO_tableName,
	 			One_POJO_tableName,One_POJOIdentification);
	     
	     /*
	      * �б��������
	      */
	     createSelectCountSQL(rootElement,Many_POJO_tableName);
	     
	     /*
	      * ���һ����
	      */
	     createManyToOneInsertSQL(rootElement, Many_POJO, Many_POJO_tableName
	    		 , One_POJO_tableName, M_T_O_Field, One_POJOIdentification);
	    
	     /*
	      * ���һ���ID��ѯ
	      */
	     createManyToOneSelectById(rootElement, Many_POJO, Many_POJO_tableName, Many_POJOIdentification);
	     
	     /*
	      * ���һɾ�� (��ͨɾ��)
	      */
	     createSimpleDeleteByIdSQL(rootElement, Many_POJO_tableName, Many_POJOIdentification);
	     
	     /*
	      * ���һ�޸�
	      */
	     createManyToOneUpdateSQL(rootElement, Many_POJO, Many_POJO_tableName, One_POJO_tableName, 
	    		 M_T_O_Field, One_POJOIdentification, Many_POJOIdentification);
	     /*
	      * ����XML
	      */
	     createXMLByRootDocument(rootDocument,Many_POJO);
	     
	     /*
	      * ---------------------------------ONE_POJO--------------------------------------------------
	      */
	     
	     
	     String O_T_M_Field = "";
		 
		 rootDocument = DocumentHelper.createDocument();
	     rootDocument.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
	     //�����
	     rootElement = rootDocument.addElement("mapper");
	     mapperDAOPathWay = basePath+".DAO."+One_POJO.getSimpleName()+"Mapper";
	     Many_POJO.getPackage();
	     rootElement.addAttribute("namespace", mapperDAOPathWay);
	     //�ڸ��мӵ�һ���ӽڵ�
	     elementResultMap = rootElement.addElement("resultMap");
	     RESULTMAPPER_ID_NAME = One_POJO.getSimpleName()+RESULTMAPPER_ID_NAME;
	     elementResultMap.addAttribute("id", RESULTMAPPER_ID_NAME);
	     elementResultMap.addAttribute("type", One_POJO.getName());
	     ElementClassID = elementResultMap.addElement("id");
	     declaredFields = One_POJO.getDeclaredFields();
	     
	     //tableInfo.put("tableName", tableName);
	     /*
	      * ����ResultMap,��װ��collectionOfTableInfo�б���Ϣ
	      */
	     for (Field Onefield : declaredFields) {
	    	 
	    	 /**
	    	  * ----------------------2.22------------------------------
	    	  */
	    	 /*
	    	  * ��֤�Ƿ���Collection
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
	    		 tableInfoMany.put(Onefield.getName(), JDBCTypeOfResult);
	    	 }
	    	 
			
		 }
	     
	     /*
	      * ����һ�Զ��б��ѯ(��෽����)
	      */
	     createOneToManySelectListSQL(rootElement, One_POJO, One_POJO_tableName, Many_POJO_tableName, One_POJOIdentification);
	     /*
	      * ����һ�Զ���ID��ѯ
	      */
	     createOneToManySelectByIdSQL(rootElement, One_POJO, One_POJO_tableName, Many_POJO_tableName, One_POJOIdentification);
	     
	     /*
	      * ����һ�Զ�һ������(��)
	      */
	     createSimpleInsertSQL(rootElement, One_POJO_tableName, One_POJO);
	     
	     /*
	      * ����һ�Զ�һ���޸�(��)
	      */
	     createSimpleUpdateSelectiveSQL(rootElement, One_POJO_tableName, One_POJO, One_POJOIdentification);
	     
	     /*
	      * ����һ�Զ�һ��ɾ��(�����Ķ෽���������)
	      */
	     createOneToManyDeleteSQL(rootElement, One_POJO_tableName, One_POJOIdentification, Many_POJO_tableName);
	     
	     /*
	      * ����һ�Զ�����޸�
	      */
	     createOneToManyWithRelationSQL(rootElement, One_POJO, Many_POJO_tableName, One_POJOIdentification, One_POJO_tableName, Many_POJOIdentification, O_T_M_Field);
	     
	     /*
	      * ����XML
	      */
	     createXMLByRootDocument(rootDocument,One_POJO);
		
	};
	
	/**
	 * 
	 * @function ��Զ������ļ����
	 * @author ���಩
	 * @time 2016-2-25 ����10:54:58
	 */
	public void createMyBatisMany_To_ManyXML(Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,String MappingTableName){
		
		
		firstStepOfXmlCreate(ManyPOJO1);
		
		 String M_T_M_Field_1 = "";
		 RESULTMAPPER_ID_NAME = "BaseResultMap";
		 Document rootDocument = DocumentHelper.createDocument();
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
		
		/*
		 * ��Զ��ҳ�б�
		 */
	     createManyToManySelectListByPageSQL(rootElement, ManyPOJO1, identification1, tableName1, ManyPOJO2, identification2, tableName2, MappingTableName);
	     
	     /*
	      * �б�����
	      */
	     createSelectCountSQL(rootElement, tableName1);
	     
		/*
		 * ��Զ����,(�޼���)
		 */
	     createManyToManyInsertWithRelationSQL(rootElement, ManyPOJO1, identification1, tableName1, ManyPOJO2, identification2, tableName2, MappingTableName, M_T_M_Field_1);
	     
	     /*
	      * ��Զ�ɾ��(ͬʱɾ���м�����)
	      */
	     createManyToManyDeleteWithRelationSQL(rootElement, identification1, tableName1, MappingTableName);
	     
	    /*
	     * ��Զ���id��ѯ����
	     */
	     createManyToManySelectByPrimaryKeySQL(rootElement, ManyPOJO1, tableName1, identification1);
	    
	     /*
	      * ��Զ��޸�(�޸��м�����)
	      */
	     createManyToManyUpdateByPrimaryKeySQL(rootElement, ManyPOJO1, identification1, identification2, tableName1, MappingTableName, M_T_M_Field_1);
	      
	     
	     
	     
	     /*
	      * ����XML
	      */
	     createXMLByRootDocument(rootDocument,ManyPOJO1);
	     
	     
	     
	     
	    
	    
	}
	/**
	 * -----------------------------MANY TO MANY---------------------------------------------------
	 */
	
	/**
	 * 
	 * @function ��Զ��ҳ�б�
	 * @author ���಩
	 * @time 2016-2-27 ����9:46:58
	 */
	private void createManyToManySelectListByPageSQL(Element rootElement,Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,String MappingTableName){
		
		
		   /*
	      * ��Զ��ҳ�б�
	      */
	     Element selectM_T_MListByPageEle = rootElement.addElement("select");
	     String selectM_T_MListByPageSql = "selectM_T_MListByPage";
	     selectM_T_MListByPageEle.addAttribute("id", selectM_T_MListByPageSql);
	     selectM_T_MListByPageEle.addAttribute("resultMap", RESULTMAPPER_ID_NAME);
	     HashMap<String, String> selectM_T_MListByPageInfo = new HashMap<String, String>();
	     selectM_T_MListByPageInfo.put("args", "Map<String,Object> map");
	     selectM_T_MListByPageInfo.put("return", "List<"+ManyPOJO1.getSimpleName()+">");
	     
	     collectionOfSqlInfo.put("selectO_T_MList", selectM_T_MListByPageInfo);
	     
	     /*
	      * ԭ��SQL
	      */
	     StringBuffer sql_selectM_T_MListByPageBuffer = new StringBuffer();
	     sql_selectM_T_MListByPageBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+tableName1)
	     .append("\n\tLIMIT")
	     .append(" #{pageNo},#{pageSize}\n\t");
	     selectM_T_MListByPageEle.addText(sql_selectM_T_MListByPageBuffer.toString());
		
	     
	     
	     
	  /*   <collection property="userRole" ofType="org.wuxb.testMTMEntity.Role" select="selectRoleBySid" column="sid">
	      	<id property="rid" column="rid" javaType="java.lang.Integer" jdbcType="INTEGER"/>
	      	<result column="roleName" property="roleName" javaType="java.lang.String" jdbcType="VARCHAR"/>
	    	</collection>
	  		</resultMap>
	  		<select id="selectM_T_MListByPage" resultMap="UserBaseResultMap">
				SELECT * FROM ssh_user LIMIT #{pageNo},#{pageSize}
			</select>
			<select id="selectRoleBySid" parameterType="int" resultType="org.wuxb.testMTMEntity.Role">
			SELECT * FROM ssh_role,ssh_user_role WHERE ssh_role.rid=ssh_user_role.rid AND sid = #{sid}
			</select>*/
	     
	    
	     
	     Element selectM_T_MComponentEle = rootElement.addElement("select");
	     //String selectM_T_MComponentSql = "selectM_T_MListByPage";
	     selectM_T_MComponentEle.addAttribute("id", "select"+ManyPOJO2.getSimpleName()+"By"+identification1);
	     selectM_T_MComponentEle.addAttribute("parameterType", idType);
	     selectM_T_MComponentEle.addAttribute("resultType", ManyPOJO2.getName());
	     
	     /*
	      * ��ҳ�б�Ƕ��SQL
	      *
	      * <select id="selectRoleBySid" parameterType="int" resultType="org.wuxb.testMTMEntity.Role">
			SELECT * FROM ssh_role,ssh_user_role WHERE ssh_role.rid=ssh_user_role.rid AND sid = #{sid}
			</select>
	      * 
	      */
	     StringBuffer sql_selectM_T_MComponentBuffer = new StringBuffer();
	     sql_selectM_T_MComponentBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+tableName2+",")
	     .append(MappingTableName)
	     .append("\n\tWHERE ").append(tableName2+"."+identification2)
	     .append(" = ").append(MappingTableName+"."+identification2)
	     .append("\n\tAND").append(" "+identification1+" = ")
	     .append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}")
	     .append("\n\t");
	     selectM_T_MComponentEle.addText(sql_selectM_T_MComponentBuffer.toString());
		
	     
	     
	    
		
		
		
	};
	
	private void createManyToManyInsertWithRelationSQL(Element rootElement,Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,
			
			String MappingTableName,String M_T_M_Field_1){
		
		Element insertM_T_MSelectiveEle = rootElement.addElement("insert");
	     String insertM_T_MSelectiveSql = "insertM_T_MSelective";
	     insertM_T_MSelectiveEle.addAttribute("id", insertM_T_MSelectiveSql);
	     insertM_T_MSelectiveEle.addAttribute("parameterType",ManyPOJO1.getName() );
	     HashMap<String, String> insertM_T_MSelectiveSqlInfo = new HashMap<String, String>();
	     insertM_T_MSelectiveSqlInfo.put("args", ManyPOJO1.getName().substring(ManyPOJO1.getName().lastIndexOf(".")+1)+" "+ManyPOJO1.getName().substring(ManyPOJO1.getName().lastIndexOf(".")+1).toLowerCase());
	     insertM_T_MSelectiveSqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put(insertM_T_MSelectiveSql, insertM_T_MSelectiveSqlInfo);
	     
	     StringBuffer sql_insertM_T_MSelectiveBuffer = new StringBuffer();
	     sql_insertM_T_MSelectiveBuffer.append("\n\tINSERT ")
	     .append("INTO").append(" "+tableName1);
	     
	     insertM_T_MSelectiveEle.addText(sql_insertM_T_MSelectiveBuffer.toString());
	     Element trimEleOfinsertSelective = insertM_T_MSelectiveEle.addElement("trim");
	     trimEleOfinsertSelective.addAttribute("prefix", "(");
	     trimEleOfinsertSelective.addAttribute("suffix", ")");
	     trimEleOfinsertSelective.addAttribute("suffixOverrides", ",");
	     
	     Map<String, String> tableColumn = collectionOfTableInfo.get(tableName1);
	     Set<String> columnNames = tableColumn.keySet();
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertSelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+",\n\t");
	    	 
	     }
	     
	     
	     Element trimEleOfinsertSelective_values = insertM_T_MSelectiveEle.addElement("trim");
	     trimEleOfinsertSelective_values.addAttribute("prefix", "values (");
	     trimEleOfinsertSelective_values.addAttribute("suffix", ");");
	     trimEleOfinsertSelective_values.addAttribute("suffixOverrides", ",");
	     
	     
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertSelective_values.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+"#{"+columnName+",jdbcType="+tableColumn.get(columnName)+"},"+"\n\t");
	     }
	     
	     
	     
	     
	     /*
	      *
	      * <insert id="insertMulti">
			INSERT INTO ssh_user(username) VALUES('ȫ��');
			INSERT INTO ssh_user_role (sid,rid) VALUES(LAST_INSERT_ID(),1);
			INSERT INTO ssh_user_role (sid,rid) VALUES(LAST_INSERT_ID(),2);
			INSERT INTO ssh_user_role (sid,rid) VALUES(LAST_INSERT_ID(),3);
			INSERT INTO ssh_user_role (sid,rid) VALUES(LAST_INSERT_ID(),4)
			</insert>	
	      * 
	      * 
	      * <if test="eduEmp != null">
			UPDATE t_emp SET eid = #{eid,jdbcType=INTEGER}  WHERE id IN
		      <foreach collection="eduEmp" item="obj" open="(" close=");" separator=",">
			#{obj.id}
			</foreach>
		    </if>
	      * 
	      * 
	      */
	     Element ifEleComponent = insertM_T_MSelectiveEle.addElement("if");
	     ifEleComponent.addAttribute("test", M_T_M_Field_1+" != null");
	     Element foreachComponent = ifEleComponent.addElement("foreach");
	     foreachComponent.addAttribute("collection", M_T_M_Field_1);
	     foreachComponent.addAttribute("item", "obj");
	     foreachComponent.addAttribute("separator", ";");
	     foreachComponent.addAttribute("close", " ");
	     
	     StringBuffer sql_foreachComponentBuffer = new StringBuffer();
	     sql_foreachComponentBuffer.append("\n\tINSERT INTO "+MappingTableName)
	     .append(" ("+identification1+","+identification2+") VALUES ")
	     .append("(LAST_INSERT_ID(),#{obj."+identification2+"})\n\t");
	     foreachComponent.addText(sql_foreachComponentBuffer.toString());
		
		
	};
	
	private void createManyToManyDeleteWithRelationSQL(Element rootElement,String identification1,String tableName1,String MappingTableName){

		
		
		
		 Element deleteWithRelationShipByPrimaryKeyEle = rootElement.addElement("delete");
	     String deleteWithRelationShipByPrimaryKeySql = "deleteWithRelationShipByPrimaryKey";
	     deleteWithRelationShipByPrimaryKeyEle.addAttribute("id", deleteWithRelationShipByPrimaryKeySql);
	     deleteWithRelationShipByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> deleteWithRelationShipByPrimaryKeySqlInfo = new HashMap<String, String>();
	     deleteWithRelationShipByPrimaryKeySqlInfo.put("args",idType.substring(idType.lastIndexOf(".")+1)+" "+identification1);
	     deleteWithRelationShipByPrimaryKeySqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("deleteWithRelationShipByPrimaryKey", deleteWithRelationShipByPrimaryKeySqlInfo);
	     
	     
	     
	     /*
	      * 1.ɾ����������
	      */
	     StringBuffer sql_deleteM_T_MRemoveRelationKeyBuffer = new StringBuffer();
	     sql_deleteM_T_MRemoveRelationKeyBuffer
	     .append("\n\tDELETE FROM ").append(MappingTableName)
	     .append(" WHERE ").append(identification1)
	     .append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     
	     
	     
	     
	     //1
	     deleteWithRelationShipByPrimaryKeyEle.addText(sql_deleteM_T_MRemoveRelationKeyBuffer.toString());
	     
	     
	     StringBuffer sql_deleteM_T_MByPrimaryKeyBuffer = new StringBuffer();
	     sql_deleteM_T_MByPrimaryKeyBuffer
	     .append("\n\tDELETE FROM ").append(tableName1)
	     .append(" WHERE ").append(identification1)
	     .append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}\n\t");
	     
	     
	     
	     
	     deleteWithRelationShipByPrimaryKeyEle.addText(sql_deleteM_T_MByPrimaryKeyBuffer.toString());
	     
	    
	     
		
		
	};
	
	private void createManyToManySelectByPrimaryKeySQL(Element rootElement,Class<?> ManyPOJO1,String tableName1,String identification1){
		
		 Element selectByPrimaryKeyEle = rootElement.addElement("select");
	     String selectByPrimaryKeySql = "selectByPrimaryKey";
	     selectByPrimaryKeyEle.addAttribute("id", selectByPrimaryKeySql);
	     selectByPrimaryKeyEle.addAttribute("resultMap", RESULTMAPPER_ID_NAME);
	     selectByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> selectByPrimaryKeySqlInfo = new HashMap<String, String>();
	     selectByPrimaryKeySqlInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+identification1);
	     selectByPrimaryKeySqlInfo.put("return", ManyPOJO1.getSimpleName());
	     
	     collectionOfSqlInfo.put("selectByPrimaryKey", selectByPrimaryKeySqlInfo);
	     
	     StringBuffer sql_selectByPrimaryKeyBuffer = new StringBuffer();
	     sql_selectByPrimaryKeyBuffer.append("\n\tSELECT *")
	     .append(" \n\tFROM "+tableName1).append(" WHERE ")
	     .append(identification1).append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}\n\t");
	     
	    
	     selectByPrimaryKeyEle.addText(sql_selectByPrimaryKeyBuffer.toString());
	     
		
	}
	
	
	private void createManyToManyUpdateByPrimaryKeySQL(Element rootElement,Class<?> ManyPOJO1,String identification1,String identification2,String tableName1,
			String MappingTableName,String M_T_M_Field_1){
		
		
		Element updateWithRelationShipByPrimaryKeyEle = rootElement.addElement("update");
	     String updateWithRelationShipByPrimaryKeySql = "updateWithRelationShipByPrimaryKey";
	     updateWithRelationShipByPrimaryKeyEle.addAttribute("id", updateWithRelationShipByPrimaryKeySql);
	     updateWithRelationShipByPrimaryKeyEle.addAttribute("parameterType",ManyPOJO1.getName() );
	     HashMap<String, String> updateWithRelationShipByPrimaryKeySqlInfo = new HashMap<String, String>();
	     updateWithRelationShipByPrimaryKeySqlInfo.put("args", ManyPOJO1.getName().substring(ManyPOJO1.getName().lastIndexOf(".")+1)+" "+ManyPOJO1.getName().substring(ManyPOJO1.getName().lastIndexOf(".")+1).toLowerCase());
	     updateWithRelationShipByPrimaryKeySqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("updateWithRelationShipByPrimaryKey", updateWithRelationShipByPrimaryKeySqlInfo);
	     
	     
	     StringBuffer sql_updateWithRelationShipByPrimaryKeyBuffer = new StringBuffer();
	     sql_updateWithRelationShipByPrimaryKeyBuffer.append("\n\tUPDATE ")
	     .append(" "+tableName1);
	     
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateWithRelationShipByPrimaryKeyBuffer.toString());
	     Element setEleOfupdateByPrimaryKeySelective = updateWithRelationShipByPrimaryKeyEle.addElement("set");
	     
	     Map<String, String> tableColumn = collectionOfTableInfo.get(tableName1);
	     Set<String> columnNames = tableColumn.keySet();
	    
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = setEleOfupdateByPrimaryKeySelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+" = #{"+columnName+",jdbcType="+tableColumn.get(columnName)+"},"+"\n\t");
	    	 
	     }
	     
	     sql_updateWithRelationShipByPrimaryKeyBuffer = new StringBuffer();
	     sql_updateWithRelationShipByPrimaryKeyBuffer
	     .append("\n\t\tWHERE ")
	     .append(identification1).append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     
	    
	     
	     /*
	      * ---------------------------------------
	      */
	    
	     //3
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateWithRelationShipByPrimaryKeyBuffer.toString());
	     
	    
	     
	     
	     
	     /*
	      * 1.ɾ���ϵ
	      */
	     StringBuffer sql_updateM_T_MEmptyRelationKeyBuffer = new StringBuffer();
	     sql_updateM_T_MEmptyRelationKeyBuffer
	     .append("\n\tDELETE FROM ").append(MappingTableName)
	     .append(" WHERE ").append(identification1)
	     .append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     
	     //1
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateM_T_MEmptyRelationKeyBuffer.toString());
	     
	     
	     /*
	      * ������еĹ�ϵ
	      */
	     Element isListNullEle = updateWithRelationShipByPrimaryKeyEle.addElement("if");
	     
	     StringBuffer sql_updateM_T_MRelationKeyBuffer = new StringBuffer();
	     sql_updateM_T_MRelationKeyBuffer
	     .append("\n\tINSERT INTO ").append(MappingTableName)
	     .append(" ("+identification1+","+identification2+")VALUES ")
	     .append("(#{"+identification1+"},#{obj."+identification2+"})\n\t");
	     
	     
	    
	     isListNullEle.addAttribute("test", M_T_M_Field_1+" != null");
	     Element foreachEle = isListNullEle.addElement("foreach");
	     foreachEle.addAttribute("collection", M_T_M_Field_1);
	     foreachEle.addAttribute("item", "obj");
	     foreachEle.addAttribute("close", " ");
	     foreachEle.addAttribute("separator", ";");
	     foreachEle.addText(sql_updateM_T_MRelationKeyBuffer.toString());
	   
	     
	     
	    
		
		
	};
	/**
	 * -----------------------------------ONE TO MANY----------------------------------------------------------
	 */
	
	/**
	 * 
	 * @function һ�Զ��б��ѯ
	 * @author ���಩
	 * @time 2016-2-23 ����9:36:47
	 */
	private void createOneToManySelectListSQL(Element rootElement,Class<?> One_POJO,String One_POJO_tableName,String Many_POJO_tableName,
			String One_POJOIdentification){
		
		
		 Element selectO_T_MListEle = rootElement.addElement("select");
	     String selectO_T_MListSql = "selectO_T_MList";
	     selectO_T_MListEle.addAttribute("id", selectO_T_MListSql);
	     selectO_T_MListEle.addAttribute("resultMap", RESULTMAPPER_ID_NAME);
	     HashMap<String, String> selectO_T_MListInfo = new HashMap<String, String>();
	     selectO_T_MListInfo.put("args", "");
	     selectO_T_MListInfo.put("return", "List<"+One_POJO.getSimpleName()+">");
	     
	     collectionOfSqlInfo.put("selectO_T_MList", selectO_T_MListInfo);
	     
	     StringBuffer sql_selectO_T_MListBuffer = new StringBuffer();
	     sql_selectO_T_MListBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+One_POJO_tableName+","+Many_POJO_tableName)
	     .append("\n\tWHERE "+One_POJO_tableName+"."+One_POJOIdentification)
	     .append(" = ").append(Many_POJO_tableName+"."+One_POJOIdentification+"\n\t");
	     
	     selectO_T_MListEle.addText(sql_selectO_T_MListBuffer.toString());
		
	}
	
	/**
	 * 
	 * @function һ�Զ���ID��ѯ
	 * @author ���಩
	 * @time 2016-2-23 ����9:48:21
	 */
	private void createOneToManySelectByIdSQL(Element rootElement,Class<?> One_POJO,String One_POJO_tableName,String Many_POJO_tableName,
			String One_POJOIdentification){
		
		 Element selectO_T_MByIdEle = rootElement.addElement("select");
	     String selectO_T_MByIdSql = "selectO_T_MById";
	     selectO_T_MByIdEle.addAttribute("id", selectO_T_MByIdSql);
	     selectO_T_MByIdEle.addAttribute("resultMap", RESULTMAPPER_ID_NAME);
	     HashMap<String, String> selectO_T_MByIdInfo = new HashMap<String, String>();
	     selectO_T_MByIdInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+One_POJOIdentification);
	     selectO_T_MByIdInfo.put("return", One_POJO.getSimpleName());
	     
	     collectionOfSqlInfo.put("selectO_T_MById", selectO_T_MByIdInfo);
	     
	     StringBuffer sql_selectO_T_MByIdBuffer = new StringBuffer();
	     sql_selectO_T_MByIdBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+One_POJO_tableName+","+Many_POJO_tableName)
	     .append("\n\tWHERE "+One_POJO_tableName+"."+One_POJOIdentification)
	     .append(" = ").append(Many_POJO_tableName+"."+One_POJOIdentification+"\n\t")
	     .append("AND ").append(One_POJO_tableName+"."+One_POJOIdentification)
	     .append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}")
	     .append("\n\t");
	     
	     
	     selectO_T_MByIdEle.addText(sql_selectO_T_MByIdBuffer.toString());
	     
		
	}
	
	
	/**
	 * 
	 * @function һ�Զ�ɾ��
	 * @author ���಩
	 * @time 2016-2-24 ����6:42:21
	 */
	private void createOneToManyDeleteSQL(Element rootElement,String One_POJO_tableName,String One_POJOIdentification,
			String Many_POJO_tableName){
		
		
		Element deleteO_T_MByPrimaryKeyEle = rootElement.addElement("delete");
	     
	    
	    
	     
	     /*
	      * ɾ��һ���������
	      */
	     String deleteO_T_MByPrimaryKeySql = "deleteO_T_MByPrimaryKey";
	     deleteO_T_MByPrimaryKeyEle.addAttribute("id", deleteO_T_MByPrimaryKeySql);
	     deleteO_T_MByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> deleteO_T_MByPrimaryKeySqlInfo = new HashMap<String, String>();
	     deleteO_T_MByPrimaryKeySqlInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+One_POJOIdentification);
	     deleteO_T_MByPrimaryKeySqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("deleteO_T_MByPrimaryKey", deleteO_T_MByPrimaryKeySqlInfo);
	     
	     StringBuffer sql_deleteO_T_MByPrimaryKeyBuffer = new StringBuffer();
	     sql_deleteO_T_MByPrimaryKeyBuffer
	     .append("\n\tDELETE")
	     .append(" FROM "+One_POJO_tableName).append("\n\tWHERE ")
	     .append(One_POJOIdentification).append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}\n\t");
	     
	     /*
	      * �޸Ķ෽�������
	      */
	     StringBuffer sql_updateO_T_MRelationKeyBuffer = new StringBuffer();
	     sql_updateO_T_MRelationKeyBuffer
	     .append("\n\tUPDATE ").append(Many_POJO_tableName)
	     .append(" SET ").append(One_POJOIdentification)
	     .append(" = NULL WHERE ").append(One_POJOIdentification)
	     .append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     deleteO_T_MByPrimaryKeyEle.addText(sql_updateO_T_MRelationKeyBuffer.toString());
	     deleteO_T_MByPrimaryKeyEle.addText(sql_deleteO_T_MByPrimaryKeyBuffer.toString());
		
	};
	
	/**
	 * 
	 * @function һ�Զ�����޸�
	 * @author ���಩
	 * @time 2016-2-24 ����8:05:32
	 */
	private void createOneToManyWithRelationSQL(Element rootElement,Class<?> One_POJO,String Many_POJO_tableName,String One_POJOIdentification,
			String One_POJO_tableName,String Many_POJOIdentification,String O_T_M_Field){
		
		 Element updateWithRelationShipByPrimaryKeyEle = rootElement.addElement("update");
	     String updateWithRelationShipByPrimaryKeySql = "updateWithRelationShipByPrimaryKey";
	     updateWithRelationShipByPrimaryKeyEle.addAttribute("id", updateWithRelationShipByPrimaryKeySql);
	     updateWithRelationShipByPrimaryKeyEle.addAttribute("parameterType",One_POJO.getName() );
	     HashMap<String, String> updateWithRelationShipByPrimaryKeySqlInfo = new HashMap<String, String>();
	     updateWithRelationShipByPrimaryKeySqlInfo.put("args", One_POJO.getName().substring(One_POJO.getName().lastIndexOf(".")+1)+" "+One_POJO.getName().substring(One_POJO.getName().lastIndexOf(".")+1).toLowerCase());
	     updateWithRelationShipByPrimaryKeySqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("updateWithRelationShipByPrimaryKey", updateWithRelationShipByPrimaryKeySqlInfo);
	     
	     
	     
	     /*
	      * 1.�޸Ķ෽�������ΪNULL
	      */
	     StringBuffer sql_updateO_T_MEmptyRelationKeyBuffer = new StringBuffer();
	     sql_updateO_T_MEmptyRelationKeyBuffer
	     .append("\n\tUPDATE ").append(Many_POJO_tableName)
	     .append(" SET ").append(One_POJOIdentification)
	     .append(" = NULL WHERE ").append(One_POJOIdentification)
	     .append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     
	     
	     
	     
	     //1
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateO_T_MEmptyRelationKeyBuffer.toString());
	     
	     
	     /*
	      * ������еĹ�ϵ
	      */
	     Element isListNullEle = updateWithRelationShipByPrimaryKeyEle.addElement("if");
	     
	     StringBuffer sql_updateO_T_MRelationKeyBuffer = new StringBuffer();
	     sql_updateO_T_MRelationKeyBuffer
	     .append("\n\tUPDATE ").append(Many_POJO_tableName)
	     .append(" SET ").append(One_POJOIdentification)
	     .append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"} ")
	     .append(" WHERE ").append(Many_POJOIdentification).append(" IN");
	     
	     isListNullEle.addText(sql_updateO_T_MRelationKeyBuffer.toString());
	    
	     
	    
	     isListNullEle.addAttribute("test", O_T_M_Field+" != null");
	     Element foreachEle = isListNullEle.addElement("foreach");
	     foreachEle.addAttribute("collection", O_T_M_Field);
	     foreachEle.addAttribute("item", "obj");
	     foreachEle.addAttribute("open", "(");
	     foreachEle.addAttribute("close", ");");
	     foreachEle.addAttribute("separator", ",");
	     foreachEle.addText("\n\t#{obj."+Many_POJOIdentification+"}\n\t");
	   
	     
	     
	     
	     
	     
	     StringBuffer sql_updateWithRelationShipByPrimaryKeyBuffer = new StringBuffer();
	     sql_updateWithRelationShipByPrimaryKeyBuffer.append("\n\tUPDATE ")
	     .append(" "+One_POJO_tableName);
	     
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateWithRelationShipByPrimaryKeyBuffer.toString());
	     Element setEleOfupdateByPrimaryKeySelective = updateWithRelationShipByPrimaryKeyEle.addElement("set");
	     
	     Map<String, String> tableColumn = collectionOfTableInfo.get(One_POJO_tableName);
	     Set<String> columnNames = tableColumn.keySet();
	    
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = setEleOfupdateByPrimaryKeySelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+" = #{"+columnName+",jdbcType="+tableColumn.get(columnName)+"},"+"\n\t");
	    	 
	     }
	     
	     sql_updateWithRelationShipByPrimaryKeyBuffer = new StringBuffer();
	     sql_updateWithRelationShipByPrimaryKeyBuffer
	     .append("\n\t\tWHERE ")
	     .append(One_POJOIdentification).append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}");
	     
	     
	    
	     
	     /*
	      * ---------------------------------------
	      */
	    
	     //3
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateWithRelationShipByPrimaryKeyBuffer.toString());
	     
	     
		
	};
	/**
	 * -----------------------------------MANY TO ONE-----------------------------------------------
	 */
	
	/**
	 * 
	 * @function ���һ�޸�Sql
	 * @author ���಩
	 * @time 2016-2-22 ����8:06:04
	 */
	private void createManyToOneUpdateSQL(Element rootElement,Class<?> Many_POJO,String Many_POJO_tableName,
			String One_POJO_tableName,String M_T_O_Field,String One_POJOIdentification,String Many_POJOIdentification){
		
		
		Element updateM_T_OByPrimaryKeySelectiveEle = rootElement.addElement("update");
	     String updateM_T_OByPrimaryKeySelectiveSql = "updateM_T_OByPrimaryKeySelective";
	     updateM_T_OByPrimaryKeySelectiveEle.addAttribute("id", updateM_T_OByPrimaryKeySelectiveSql);
	     updateM_T_OByPrimaryKeySelectiveEle.addAttribute("parameterType",Many_POJO.getName() );
	     HashMap<String, String> updateM_T_OByPrimaryKeySelectiveSqlInfo = new HashMap<String, String>();
	     updateM_T_OByPrimaryKeySelectiveSqlInfo.put("args", Many_POJO.getName().substring(Many_POJO.getName().lastIndexOf(".")+1)+" "+Many_POJO.getName().substring(Many_POJO.getName().lastIndexOf(".")+1).toLowerCase());
	     updateM_T_OByPrimaryKeySelectiveSqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("updateM_T_OByPrimaryKeySelective", updateM_T_OByPrimaryKeySelectiveSqlInfo);
	     
	     StringBuffer sql_updateM_T_OByPrimaryKeySelectiveBuffer = new StringBuffer();
	     sql_updateM_T_OByPrimaryKeySelectiveBuffer.append("\n\tUPDATE ")
	     .append(" "+Many_POJO_tableName);
	     
	     
	     updateM_T_OByPrimaryKeySelectiveEle.addText(sql_updateM_T_OByPrimaryKeySelectiveBuffer.toString());
	     Element setEleOfupdateM_T_OByPrimaryKeySelective = updateM_T_OByPrimaryKeySelectiveEle.addElement("set");
	     
	     Map<String, String> tableColumn_MANY = collectionOfTableInfo.get(Many_POJO_tableName);
	     Map<String, String> tableColumn_ONE = collectionOfTableInfo.get(One_POJO_tableName);
	     Set<String> columnNames = tableColumn_MANY.keySet();
	    
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = setEleOfupdateM_T_OByPrimaryKeySelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+" = #{"+columnName+",jdbcType="+tableColumn_MANY.get(columnName)+"},"+"\n\t");
	    	 
	     }
	     
	     Element Relation_M_T_O_ColumnEle_CLASS = setEleOfupdateM_T_OByPrimaryKeySelective.addElement("if");
	     Relation_M_T_O_ColumnEle_CLASS.addAttribute("test", M_T_O_Field+" != null");
	     
	     Element Relation_M_T_O_ColumnEle_Field = Relation_M_T_O_ColumnEle_CLASS.addElement("if");
	     Relation_M_T_O_ColumnEle_Field.addAttribute("test", M_T_O_Field+"."+One_POJOIdentification+" != null");
	     Relation_M_T_O_ColumnEle_Field.addText("\n\t\t"+One_POJOIdentification+" = #{"+M_T_O_Field+"."+One_POJOIdentification+",jdbcType="+tableColumn_ONE.get(One_POJOIdentification)+"},"+"\n\t");
   	 
	     
	     
	     sql_updateM_T_OByPrimaryKeySelectiveBuffer = new StringBuffer();
	     sql_updateM_T_OByPrimaryKeySelectiveBuffer
	     .append("\n\t\tWHERE ")
	     .append(Many_POJOIdentification).append(" = ").append("#{"+Many_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}");
	     
	     updateM_T_OByPrimaryKeySelectiveEle.addText(sql_updateM_T_OByPrimaryKeySelectiveBuffer.toString());
	     
		
	}
	/**
	 * 
	 * @function �������һ���ID��ѯ
	 * @author ���಩
	 * @time 2016-2-22 ����7:22:42
	 */
	private void createManyToOneSelectById(Element rootElement,Class<?> Many_POJO,String Many_POJO_tableName,String Many_POJOIdentification){
		
		 Element selectM_T_OByPrimaryKeyEle = rootElement.addElement("select");
	     String selectM_T_OByPrimaryKeySql = "selectM_T_OByPrimaryKey";
	     selectM_T_OByPrimaryKeyEle.addAttribute("id", selectM_T_OByPrimaryKeySql);
	     selectM_T_OByPrimaryKeyEle.addAttribute("resultMap", RESULTMAPPER_ID_NAME);
	     selectM_T_OByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> selectM_T_OByPrimaryKeySqlInfo = new HashMap<String, String>();
	     selectM_T_OByPrimaryKeySqlInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+Many_POJOIdentification);
	     selectM_T_OByPrimaryKeySqlInfo.put("return", Many_POJO.getSimpleName());
	     
	     collectionOfSqlInfo.put("selectM_T_OByPrimaryKey", selectM_T_OByPrimaryKeySqlInfo);
	     
	     StringBuffer sql_selectM_T_OByPrimaryKeyBuffer = new StringBuffer();
	     sql_selectM_T_OByPrimaryKeyBuffer.append("\n\tSELECT ");
	     
	     selectM_T_OByPrimaryKeyEle.addText(sql_selectM_T_OByPrimaryKeyBuffer.toString());
	     Element includeEleOfselectM_T_OByPrimaryKey = selectM_T_OByPrimaryKeyEle.addElement("include");
	     includeEleOfselectM_T_OByPrimaryKey.addAttribute("refid", "Base_Column_List");
	     sql_selectM_T_OByPrimaryKeyBuffer = new StringBuffer();
	     sql_selectM_T_OByPrimaryKeyBuffer
	     .append(" \n\tFROM "+Many_POJO_tableName).append(" WHERE ")
	     .append(Many_POJOIdentification).append(" = ").append("#{"+Many_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}");
	     
	     
	    
	     selectM_T_OByPrimaryKeyEle.addText(sql_selectM_T_OByPrimaryKeyBuffer.toString());
		
	}
	/**
	 * 
	 * @function �������һ���
	 * @author ���಩
	 * @time 2016-2-22 ����7:22:27
	 */
	private void createManyToOneInsertSQL(Element rootElement,Class<?> Many_POJO,
			String Many_POJO_tableName,String One_POJO_tableName,String M_T_O_Field,
			String One_POJOIdentification){
		
		 Element insertM_T_OSelectiveEle = rootElement.addElement("insert");
	     String insertM_T_OSelectiveSql = "insertM_T_OSelective";
	     insertM_T_OSelectiveEle.addAttribute("id", insertM_T_OSelectiveSql);
	     insertM_T_OSelectiveEle.addAttribute("parameterType",Many_POJO.getName() );
	     HashMap<String, String> insertM_T_OSelectiveSqlInfo = new HashMap<String, String>();
	     insertM_T_OSelectiveSqlInfo.put("args", Many_POJO.getName().substring(Many_POJO.getName().lastIndexOf(".")+1)+" "+Many_POJO.getName().substring(Many_POJO.getName().lastIndexOf(".")+1).toLowerCase());
	     insertM_T_OSelectiveSqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("insertM_T_OSelective", insertM_T_OSelectiveSqlInfo);
	     
	     StringBuffer sql_insertM_T_OSelectiveBuffer = new StringBuffer();
	     sql_insertM_T_OSelectiveBuffer.append("\n\tINSERT ")
	     .append("INTO").append(" "+Many_POJO_tableName);
	     
	     insertM_T_OSelectiveEle.addText(sql_insertM_T_OSelectiveBuffer.toString());
	     Element trimEleOfinsertM_T_OSelective = insertM_T_OSelectiveEle.addElement("trim");
	     trimEleOfinsertM_T_OSelective.addAttribute("prefix", "(");
	     trimEleOfinsertM_T_OSelective.addAttribute("suffix", ")");
	     trimEleOfinsertM_T_OSelective.addAttribute("suffixOverrides", ",");
	     
	     Map<String, String> tableColumn_MANY = collectionOfTableInfo.get(Many_POJO_tableName);
	     Map<String, String> tableColumn_ONE = collectionOfTableInfo.get(One_POJO_tableName);
	     Set<String> columnNames = tableColumn_MANY.keySet();
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertM_T_OSelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+",\n\t");
	    	 
	     }
	     
	     Element relation_Column_MTO_Class = trimEleOfinsertM_T_OSelective.addElement("if");
	     relation_Column_MTO_Class.addAttribute("test", M_T_O_Field+" != null");
	     
	     
	     
	     Element relation_Column_MTO_Field = relation_Column_MTO_Class.addElement("if");
	     relation_Column_MTO_Field.addAttribute("test", M_T_O_Field+"."+One_POJOIdentification+" != null");
	     relation_Column_MTO_Field.addText("\n\t\t"+One_POJOIdentification+",\n\t");
	     
	     Element trimEleOfinsertM_T_OSelective_values = insertM_T_OSelectiveEle.addElement("trim");
	     trimEleOfinsertM_T_OSelective_values.addAttribute("prefix", "values (");
	     trimEleOfinsertM_T_OSelective_values.addAttribute("suffix", ")");
	     trimEleOfinsertM_T_OSelective_values.addAttribute("suffixOverrides", ",");
	     
	     
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertM_T_OSelective_values.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+"#{"+columnName+",jdbcType="+tableColumn_MANY.get(columnName)+"},"+"\n\t");
	     }
	     
	     
	     Element values_Column_MTO_CLASS = trimEleOfinsertM_T_OSelective_values.addElement("if");
	     values_Column_MTO_CLASS.addAttribute("test", M_T_O_Field+" != null");
	     
	     
	     
	     Element values_Column_MTO_FIELD = values_Column_MTO_CLASS.addElement("if");
	     values_Column_MTO_FIELD.addAttribute("test", M_T_O_Field+"."+One_POJOIdentification+" != null");
	     values_Column_MTO_FIELD.addText("\n\t\t"+"#{"+M_T_O_Field+"."+One_POJOIdentification+",jdbcType="+tableColumn_ONE.get(One_POJOIdentification)+"},"+"\n\t");
	     
	     
		
	}
	/**
	 * 
	 * @function ���һ��SQL
	 * @author ���಩
	 * @time 2016-2-22 ����6:06:11
	 */
	private void manyToOneBaseColumnSQL(Element rootElement,String Many_POJO_tableName,String One_POJOIdentification){
		
		 Element sql_BaseColumn_List = rootElement.addElement("sql");
	     sql_BaseColumn_List.addAttribute("id", "Base_Column_List");
	     StringBuffer ListTextBuffer = new StringBuffer();
	     
	     Set<String> ColumnName = collectionOfTableInfo.get(Many_POJO_tableName).keySet();
	     for (String column : ColumnName) {
	    	 
	    	 if(!column.equals("tableName")){
	    		 
	    		 ListTextBuffer.append(",").append(column);
	    		 
	    	 }
			
		}
	     ListTextBuffer.append(",").append(One_POJOIdentification);
	    
	     
	     String listText = ListTextBuffer.substring(1);
	     sql_BaseColumn_List.addText(listText);
		
	};
	/**
	 * 
	 * @function ���һ��ҳSQL
	 * @author ���಩
	 * @time 2016-2-22 ����6:10:14
	 */
	private void manyToOneSelectByPageSQL(Element rootElement,Class<?> Many_POJO,String Many_POJO_tableName,
			String One_POJO_tableName,String One_POJOIdentification){
		
		 Element selectM_T_OByPageNoEle = rootElement.addElement("select");
	     String selectM_T_OByPageNoSql = "selectM_T_OByPageNo";
	     selectM_T_OByPageNoEle.addAttribute("id", selectM_T_OByPageNoSql);
	     selectM_T_OByPageNoEle.addAttribute("resultMap", RESULTMAPPER_ID_NAME);
	     selectM_T_OByPageNoEle.addAttribute("parameterType", "java.util.Map");
	     HashMap<String, String> selectM_T_OByPageNoInfo = new HashMap<String, String>();
	     selectM_T_OByPageNoInfo.put("args", "Map<String, Object> map");
	     selectM_T_OByPageNoInfo.put("return", "List<"+Many_POJO.getSimpleName()+">");
	     
	     collectionOfSqlInfo.put("selectM_T_OByPageNo", selectM_T_OByPageNoInfo);
	     
	     StringBuffer sql_selectM_T_OByPageNoBuffer = new StringBuffer();
	     sql_selectM_T_OByPageNoBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+Many_POJO_tableName+","+One_POJO_tableName)
	     .append("\n\tWHERE "+Many_POJO_tableName+"."+One_POJOIdentification)
	     .append(" = ").append(One_POJO_tableName+"."+One_POJOIdentification)
	     .append("\n\tLIMIT")
	     .append(" #{pageNo},#{pageSize}\n\t");
	     selectM_T_OByPageNoEle.addText(sql_selectM_T_OByPageNoBuffer.toString());
		
	};
	
	/**
	 * -------------------------------SIMPLE POJO-------------------------------------------------
	 */
	
	
	/**
	 * @serialNo 3.ִ������3.1
	 * @function ��������SQL���
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 */
	private void createBaseColumnSQL(Element rootElement,String tableName){

		
		
		 Element sql_BaseColumn_List = rootElement.addElement("sql");
	     sql_BaseColumn_List.addAttribute("id", "Base_Column_List");
	     StringBuffer ListTextBuffer = new StringBuffer();
	     
	     Set<String> ColumnName = collectionOfTableInfo.get(tableName).keySet();
	     for (String column : ColumnName) {
	    	 
	    	 if(!column.equals("tableName")){
	    		 
	    		 ListTextBuffer.append(",").append(column);
	    		 
	    	 }
			
		}
	     String listText = ListTextBuffer.substring(1);
	     sql_BaseColumn_List.addText(listText);
		
	}
	/**
	 * @serialNo 3.ִ������3.2
	 * @function �����򵥷�ҳSQL���
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param cls �����
	 */
	private void createSimpleSelectByPageSQL(Element rootElement,String tableName,Class<?> cls){
		
		 Element selectByPageNoEle = rootElement.addElement("select");
	     String selectByPageSql = "selectByPageNo";
	     selectByPageNoEle.addAttribute("id", selectByPageSql);
	     selectByPageNoEle.addAttribute("resultMap", RESULTMAPPER_ID_NAME);
	     selectByPageNoEle.addAttribute("parameterType", "java.util.Map");
	     HashMap<String, String> selectSqlInfo = new HashMap<String, String>();
	     selectSqlInfo.put("args", "Map<String, Object> map");
	     selectSqlInfo.put("return", "List<"+cls.getSimpleName()+">");
	     
	     collectionOfSqlInfo.put("selectByPageNo", selectSqlInfo);
	     
	     StringBuffer sql_SelectByPageBuffer = new StringBuffer();
	     sql_SelectByPageBuffer.append("\n\tSELECT ");
	     
	     selectByPageNoEle.addText(sql_SelectByPageBuffer.toString());
	     Element includeEle = selectByPageNoEle.addElement("include");
	     includeEle.addAttribute("refid", "Base_Column_List");
	     sql_SelectByPageBuffer = new StringBuffer();
	     sql_SelectByPageBuffer
	     .append("\n\tFROM "+tableName).append(" LIMIT")
	     .append(" #{pageNo},#{pageSize}");
	     selectByPageNoEle.addText(sql_SelectByPageBuffer.toString());
		
	};
	/**
	 * @serialNo 3.ִ������3.3
	 * @function ��ѯ�б��������
	 * @author ���಩
	 * @time 2016-2-22 ����3:51:12
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 */
	private void createSelectCountSQL(Element rootElement,String tableName){
		
		
		Element selectCountEle = rootElement.addElement("select");
	     String selectCountSql = "selectCount";
	     selectCountEle.addAttribute("id", selectCountSql);
	     selectCountEle.addAttribute("resultType", "java.lang.Integer");
	     HashMap<String, String> selectCountInfo = new HashMap<String, String>();
	     selectCountInfo.put("args", "");
	     selectCountInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("selectCount", selectCountInfo);
	     
	     StringBuffer sql_selectCountBuffer = new StringBuffer();
	     sql_selectCountBuffer.append("\n\tSELECT COUNT(*) ")
	     .append("FROM "+tableName+"\n\t");
	     selectCountEle.addText(sql_selectCountBuffer.toString());
		
	}
	/**
	 * @serialNo 3.ִ������3.4
	 * @function �������Ҷ���SQL
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param cls �����
	 * @param identification �������ı�ʶ����
	 */
	private void createSimpleSelectByIdSQL(Element rootElement,String tableName,Class<?> cls,String identification){

		
		
		 Element selectByPrimaryKeyEle = rootElement.addElement("select");
	     String selectByPrimaryKeySql = "selectByPrimaryKey";
	     selectByPrimaryKeyEle.addAttribute("id", selectByPrimaryKeySql);
	     selectByPrimaryKeyEle.addAttribute("resultMap", RESULTMAPPER_ID_NAME);
	     selectByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> selectByPrimaryKeySqlInfo = new HashMap<String, String>();
	     selectByPrimaryKeySqlInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+identification);
	     selectByPrimaryKeySqlInfo.put("return", cls.getSimpleName());
	     
	     collectionOfSqlInfo.put("selectByPrimaryKey", selectByPrimaryKeySqlInfo);
	     
	     StringBuffer sql_selectByPrimaryKeyBuffer = new StringBuffer();
	     sql_selectByPrimaryKeyBuffer.append("\n\tSELECT ");
	     
	     selectByPrimaryKeyEle.addText(sql_selectByPrimaryKeyBuffer.toString());
	     Element includeEleOfselectByPrimaryKey = selectByPrimaryKeyEle.addElement("include");
	     includeEleOfselectByPrimaryKey.addAttribute("refid", "Base_Column_List");
	     sql_selectByPrimaryKeyBuffer = new StringBuffer();
	     sql_selectByPrimaryKeyBuffer
	     .append(" \n\tFROM "+tableName).append(" WHERE ")
	     .append(identification).append(" = ").append("#{"+identification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}");
	     
	     
	    
	     selectByPrimaryKeyEle.addText(sql_selectByPrimaryKeyBuffer.toString());
		
	};
	/**
	 * @serialNo 3.ִ������3.5
	 * @function ����ɾ��SQL
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param identification �������ı�ʶ����
	 */
	private void createSimpleDeleteByIdSQL(Element rootElement,String tableName,String identification){
		
		 Element deleteByPrimaryKeyEle = rootElement.addElement("delete");
	     String deleteByPrimaryKeySql = "deleteByPrimaryKey";
	     deleteByPrimaryKeyEle.addAttribute("id", deleteByPrimaryKeySql);
	     deleteByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> deleteByPrimaryKeySqlInfo = new HashMap<String, String>();
	     deleteByPrimaryKeySqlInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+identification);
	     deleteByPrimaryKeySqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("deleteByPrimaryKey", deleteByPrimaryKeySqlInfo);
	     
	     StringBuffer sql_deleteByPrimaryKeyBuffer = new StringBuffer();
	     sql_deleteByPrimaryKeyBuffer
	     .append("\n\tDELETE")
	     .append(" FROM "+tableName).append("\n\tWHERE ")
	     .append(identification).append(" = ").append("#{"+identification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}\n\t");
	     
	     
	    
	     deleteByPrimaryKeyEle.addText(sql_deleteByPrimaryKeyBuffer.toString());
		
	}
	/**
	 * @serialNo 3.ִ������3.6
	 * @function �������Ӷ���SQL
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param cls �����
	 * @param identification �������ı�ʶ����
	 */
	private void createSimpleInsertSQL(Element rootElement,String tableName,Class<?> cls){
		
		 Element insertSelectiveEle = rootElement.addElement("insert");
	     String insertSelectiveSql = "insertSelective";
	     insertSelectiveEle.addAttribute("id", insertSelectiveSql);
	     insertSelectiveEle.addAttribute("parameterType",cls.getName() );
	     HashMap<String, String> insertSelectiveSqlInfo = new HashMap<String, String>();
	     insertSelectiveSqlInfo.put("args", cls.getName().substring(cls.getName().lastIndexOf(".")+1)+" "+cls.getName().substring(cls.getName().lastIndexOf(".")+1).toLowerCase());
	     insertSelectiveSqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("insertSelective", insertSelectiveSqlInfo);
	     
	     StringBuffer sql_insertSelectiveBuffer = new StringBuffer();
	     sql_insertSelectiveBuffer.append("\n\tINSERT ")
	     .append("INTO").append(" "+tableName);
	     
	     insertSelectiveEle.addText(sql_insertSelectiveBuffer.toString());
	     Element trimEleOfinsertSelective = insertSelectiveEle.addElement("trim");
	     trimEleOfinsertSelective.addAttribute("prefix", "(");
	     trimEleOfinsertSelective.addAttribute("suffix", ")");
	     trimEleOfinsertSelective.addAttribute("suffixOverrides", ",");
	     
	     Map<String, String> tableColumn = collectionOfTableInfo.get(tableName);
	     Set<String> columnNames = tableColumn.keySet();
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertSelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+",\n\t");
	    	 
	     }
	     
	     
	     Element trimEleOfinsertSelective_values = insertSelectiveEle.addElement("trim");
	     trimEleOfinsertSelective_values.addAttribute("prefix", "values (");
	     trimEleOfinsertSelective_values.addAttribute("suffix", ")");
	     trimEleOfinsertSelective_values.addAttribute("suffixOverrides", ",");
	     
	     
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertSelective_values.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+"#{"+columnName+",jdbcType="+tableColumn.get(columnName)+"},"+"\n\t");
	     }
	     
		
	}
	/**
	 * @serialNo 3.ִ������3.7
	 * @function �������¶���SQL
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param cls �����
	 * @param identification �������ı�ʶ����
	 */
	private void createSimpleUpdateSelectiveSQL(Element rootElement,String tableName,Class<?> cls,String identification){
		
		 Element updateByPrimaryKeySelectiveEle = rootElement.addElement("update");
	     String updateByPrimaryKeySelectiveSql = "updateByPrimaryKeySelective";
	     updateByPrimaryKeySelectiveEle.addAttribute("id", updateByPrimaryKeySelectiveSql);
	     updateByPrimaryKeySelectiveEle.addAttribute("parameterType",cls.getName() );
	     HashMap<String, String> updateByPrimaryKeySelectiveSqlInfo = new HashMap<String, String>();
	     updateByPrimaryKeySelectiveSqlInfo.put("args", cls.getName().substring(cls.getName().lastIndexOf(".")+1)+" "+cls.getName().substring(cls.getName().lastIndexOf(".")+1).toLowerCase());
	     updateByPrimaryKeySelectiveSqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("updateByPrimaryKeySelective", updateByPrimaryKeySelectiveSqlInfo);
	     
	     StringBuffer sql_updateByPrimaryKeySelectiveBuffer = new StringBuffer();
	     sql_updateByPrimaryKeySelectiveBuffer.append("\n\tUPDATE ")
	     .append(" "+tableName);
	     
	     updateByPrimaryKeySelectiveEle.addText(sql_updateByPrimaryKeySelectiveBuffer.toString());
	     Element setEleOfupdateByPrimaryKeySelective = updateByPrimaryKeySelectiveEle.addElement("set");
	     
	     Map<String, String> tableColumn = collectionOfTableInfo.get(tableName);
	     Set<String> columnNames = tableColumn.keySet();
	    
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = setEleOfupdateByPrimaryKeySelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+" = #{"+columnName+",jdbcType="+tableColumn.get(columnName)+"},"+"\n\t");
	    	 
	     }
	     
	     sql_updateByPrimaryKeySelectiveBuffer = new StringBuffer();
	     sql_updateByPrimaryKeySelectiveBuffer
	     .append("\n\t\tWHERE ")
	     .append(identification).append(" = ").append("#{"+identification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}");
	     
	     updateByPrimaryKeySelectiveEle.addText(sql_updateByPrimaryKeySelectiveBuffer.toString());
	     
		
	};
	/**
	 * -----------------------------------------------------------------------------------
	 * -----------------------------------------------------------------------------------
	 */
	/**
	 * @serialNo 4.ִ������4
	 * @function ����XML
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootDocument ���ĵ�
	 * @param cls �����
	 */
	private void createXMLByRootDocument(Document rootDocument,Class<?> cls){
		
		Writer writer = null;
	     String pathWay = "src/"+basePath.replace('.','/')+"/"+MAPPERPACKAGENAME+cls.getSimpleName()+"Mapper.xml";
	     
		try {
			writer = new FileWriter(pathWay);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	   
	     //��ʽ��xml
	     OutputFormat format = new OutputFormat();
	     format.setNewlines(true);
	     format.setIndentSize(2);
	     format.setTrimText(false);
	     format.setPadText(true);
	     format.setEncoding("UTF-8");

	     XMLWriter xml = new XMLWriter(writer,format);
	     try {
	    	
			xml.write(rootDocument);
		} catch (IOException e) {
		
			e.printStackTrace();
			
		}finally{
			
			
			if(writer!=null){
				
				try {
					writer.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
			 if(xml!=null){
		    	 
		    	 try {
		 			xml.close();
		 		} catch (IOException e) {
		 			e.printStackTrace();
		 		}
		     }
			
		}
		
		
		
	}
}
