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
	 * 取绝对路径时的Key
	 */
	private final String BASEPATH = "basePathLocation";
	private final String DAOPATH = "DAOPathLocation";
	private final String MAPPERPATH = "MapperPathLocation";

	/**
	 * 跟目录 eg:org.wuxb
	 */
	private String basePath;
	/**
	 * 工具型String
	 */
	private final String DAOPACKAGENAME = "DAO\\";
	private final String MAPPERPACKAGENAME = "Mapper\\";
	/**
	 * id属性的Java类型
	 */
	private String idType;
	/**
	 * DAO,Mapper,和基本包的绝对路径
	 */
	private Map<String, String> collectionOfAbsolutePath = new HashMap<String, String>();
	/**
	 * JAVA类和JDBC类的类型对应信息容器
	 */
	private Map<String, String> collectionOfJDBCAndJAVATypeConvert = new HashMap<String, String>();
	/**
	 * 表信息容器，K:表名,V:字段信息
	 */
	private Map<String, Map<String, String>> collectionOfTableInfo = new HashMap<String, Map<String, String>>();
	/**
	 * sql语句信息容器 K:sql语句ID,V:需求参数及返回值
	 */
	private Map<String, Map<String, String>> collectionOfSqlInfo = new HashMap<String, Map<String, String>>();
	/**
	 * 基准MapperResult的ID
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
	 * @serialNo 1.执行序列1
	 * @function 创建JAVA和JDBC类型转换容器
	 * @author 吴相博
	 * @time 2016-2-21 下午6:02:33
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
	 * @serialNo 2.执行序列2
	 * @function 创建DAO包和Mapper包
	 * @author 吴相博
	 * @time 2016-2-18 上午9:38:12
	 */
	private Map<String, String> makeDAOAndMapperPackage(Class<?> c) throws IOException{
		
		//获得包名
		String packageName = c.getPackage().getName();
		int lastIndexOfSpace = packageName.lastIndexOf(" ");
		//处理获得的包名
		String forUserPackageName = packageName.substring(lastIndexOfSpace+1);
		//处理获得的包名
		int lastIndexOfCut = packageName.lastIndexOf('.');
		basePath = packageName.substring(lastIndexOfSpace+1,lastIndexOfCut);
		//获得文件相对路径
		String entityLocation = "src/"+forUserPackageName.replace('.','/')+"/"+c.getSimpleName()+".java";
		//获得文件绝对路径
		File file = new File(entityLocation);
		String absoluteEntityLocation = file.getAbsolutePath();
		//处理绝对路径，拼接DAO层与Mapper层地址
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
	 * @function 整合序列1,2
	 * @author 吴相博
	 * @time 2016-2-21 下午7:12:59
	 * @param cls 类对象
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
	 * @serialNo 3.执行序列3
	 * @function 创建简单单表增删改查XML
	 * @author 吴相博
	 * @time 2016-2-21 下午6:04:34
	 * @param cls 操作类的类对象
	 * @param identification 该类的标识属性
	 * @param tableName 该类对应的表名
	 */
	public void createMyBatisPOJOXML(Class<?> cls,String identification,String tableName){
		
		 firstStepOfXmlCreate(cls);
		 
		 Document rootDocument = DocumentHelper.createDocument();
	     rootDocument.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
	     //加入根
	     Element rootElement = rootDocument.addElement("mapper");
	     String mapperDAOPathWay = basePath+".DAO."+cls.getSimpleName()+"Mapper";
	     cls.getPackage();
	     rootElement.addAttribute("namespace", mapperDAOPathWay);
	     //在根中加入第一个子节点
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
	      * <sql/>节点 公用SQL
	      *
	      * */
		createBaseColumnSQL(rootElement,tableName);
	     /*
	      * <select id='selectByPageNo'/>节点 分页列表
	      */
	     createSimpleSelectByPageSQL(rootElement,tableName,cls);
	     
	     
	     /*
	      * <select id="getCount" resultType="int" >
		  *  SELECT COUNT(*) FROM t_person
		  *  </select>
	      */
	     createSelectCountSQL(rootElement,tableName);
	     

	     /*
	      * <select id='selectByPrimaryKey'/>节点 根据标识属性查找对象
	      */
		createSimpleSelectByIdSQL(rootElement,tableName,cls,identification);
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
	     createSimpleDeleteByIdSQL(rootElement,tableName,identification);
	     /*
	      * <insert id='insertSelective'/>节点 增加对象
	      */
		createSimpleInsertSQL(rootElement, tableName, cls);

	     /*
	      * <update id='updateByPrimaryKeySelective'/>节点 修改对象(不可赋空)
	      */
		createSimpleUpdateSelectiveSQL(rootElement, tableName, cls, identification);



		//写xml

		createXMLByRootDocument(rootDocument,cls);
		
	    
	}

	/**
	 *
	 * @function 多对一配置文件生成
	 * @author 吴相博
	 * @time 2016-2-21 下午8:20:52
	 */
	public void createMyBatisMany_TO_OneXML(Class<?> Many_POJO,String Many_POJOIdentification,String Many_POJO_tableName,Class<?> One_POJO,String One_POJOIdentification,String One_POJO_tableName){
		
		/*
		 * ---------------------------------MANY_POJO------------------------------------------
		 */
		
		firstStepOfXmlCreate(Many_POJO);
		
		String M_T_O_Field = "";
		 
		 Document rootDocument = DocumentHelper.createDocument();
	     rootDocument.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
	     //加入根
	     Element rootElement = rootDocument.addElement("mapper");
	     String mapperDAOPathWay = basePath+".DAO."+Many_POJO.getSimpleName()+"Mapper";
	     Many_POJO.getPackage();
	     rootElement.addAttribute("namespace", mapperDAOPathWay);
	     //在根中加入第一个子节点
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
	      * 创建ResultMap,并装入collectionOfTableInfo中表信息
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
	      * 可以通过调用One方主键属性时使用tType.tid的方式来避免在Many类中多添加tid字段
	      *
	      * <sql id=base_Column_List>
	      */


		collectionOfTableInfo.put(One_POJO_tableName, tableInfoOne);
	     collectionOfTableInfo.put(Many_POJO_tableName, tableInfoMany);
		
	    
	     manyToOneBaseColumnSQL(rootElement,Many_POJO_tableName,One_POJOIdentification);
	     /*
	      * 两表联查分页
	      */
	     manyToOneSelectByPageSQL(rootElement,Many_POJO,Many_POJO_tableName,
	 			One_POJO_tableName,One_POJOIdentification);
	     
	     /*
	      * 列表数据条数
	      */
	     createSelectCountSQL(rootElement,Many_POJO_tableName);
	     
	     /*
	      * 多对一增加
	      */
	     createManyToOneInsertSQL(rootElement, Many_POJO, Many_POJO_tableName
	    		 , One_POJO_tableName, M_T_O_Field, One_POJOIdentification);
	    
	     /*
	      * 多对一根据id查询
	      */
	     createManyToOneSelectById(rootElement, Many_POJO, Many_POJO_tableName, Many_POJOIdentification);
	     
	     /*
	      * 简单的根据id删除
	      */
	     createSimpleDeleteByIdSQL(rootElement, Many_POJO_tableName, Many_POJOIdentification);
	     
	     /*
	      * 多对一修改
	      */
	     createManyToOneUpdateSQL(rootElement, Many_POJO, Many_POJO_tableName, One_POJO_tableName, 
	    		 M_T_O_Field, One_POJOIdentification, Many_POJOIdentification);
	     /*
	      * 创建该XML
	      */
	     createXMLByRootDocument(rootDocument,Many_POJO);
	     
	     /*
	      * ---------------------------------ONE_POJO--------------------------------------------------
	      */
	     
	     
	     String O_T_M_Field = "";
		 
		 rootDocument = DocumentHelper.createDocument();
	     rootDocument.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
	     //创建根
	     rootElement = rootDocument.addElement("mapper");
	     mapperDAOPathWay = basePath+".DAO."+One_POJO.getSimpleName()+"Mapper";
	     Many_POJO.getPackage();
	     rootElement.addAttribute("namespace", mapperDAOPathWay);
	     //根中加入第一个子节点
	     elementResultMap = rootElement.addElement("resultMap");
	     RESULTMAPPER_ID_NAME = One_POJO.getSimpleName()+RESULTMAPPER_ID_NAME;
	     elementResultMap.addAttribute("id", RESULTMAPPER_ID_NAME);
	     elementResultMap.addAttribute("type", One_POJO.getName());
	     ElementClassID = elementResultMap.addElement("id");
	     declaredFields = One_POJO.getDeclaredFields();
	     
	     //tableInfo.put("tableName", tableName);
	     /*
	      * 创建ResultMap,并装入collectionOfTableInfo中表信息
	      */
	     for (Field Onefield : declaredFields) {
	    	 
	    	 /**
	    	  * ----------------------2.22------------------------------
	    	  */
	    	 /*
	    	  *  验证是否是Collection
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
	      * 创建一对多列表查询(包含多方个体)
	      */
	     createOneToManySelectListSQL(rootElement, One_POJO, One_POJO_tableName, Many_POJO_tableName, One_POJOIdentification);
	     /*
	      * 创建一对多根据id查询
	      */
	     createOneToManySelectByIdSQL(rootElement, One_POJO, One_POJO_tableName, Many_POJO_tableName, One_POJOIdentification);
	     
	    /*
	      * 创建一对多一方增加(简单)
	      */
		createSimpleInsertSQL(rootElement, One_POJO_tableName, One_POJO);

	     /*
	      * 创建一对多一方修改(简单)
	      */
		createSimpleUpdateSelectiveSQL(rootElement, One_POJO_tableName, One_POJO, One_POJOIdentification);

	     /*
	      * 创建一对多一方删除(关联的多方关联键将清空)
	      */
		createOneToManyDeleteSQL(rootElement, One_POJO_tableName, One_POJOIdentification, Many_POJO_tableName);

	     /*
	      * 创建一对多关联修改
	      */
		createOneToManyWithRelationSQL(rootElement, One_POJO, Many_POJO_tableName, One_POJOIdentification, One_POJO_tableName, Many_POJOIdentification, O_T_M_Field);

	     /*
	      * 创建XML
	      */
	     createXMLByRootDocument(rootDocument,One_POJO);
		
	};

	/**
	 *
	 * @function 多对多配置文件生成
	 * @author 吴相博
	 * @time 2016-2-25 上午10:54:58
	 */
	public void createMyBatisMany_To_ManyXML(Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,String MappingTableName){
		
		
		firstStepOfXmlCreate(ManyPOJO1);
		
		 String M_T_M_Field_1 = "";
		 RESULTMAPPER_ID_NAME = "BaseResultMap";
		 Document rootDocument = DocumentHelper.createDocument();
	     rootDocument.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
	     //创建根
	     Element rootElement = rootDocument.addElement("mapper");
	     String mapperDAOPathWay = basePath+".DAO."+ManyPOJO1.getSimpleName()+"Mapper";
	     ManyPOJO1.getPackage();
	     rootElement.addAttribute("namespace", mapperDAOPathWay);
	   //添加第一个元素
	     Element elementResultMap = rootElement.addElement("resultMap");
	     RESULTMAPPER_ID_NAME = ManyPOJO1.getSimpleName()+RESULTMAPPER_ID_NAME;
	     elementResultMap.addAttribute("id", RESULTMAPPER_ID_NAME);
	     elementResultMap.addAttribute("type", ManyPOJO1.getName());
	     Element ElementClassID = elementResultMap.addElement("id");
	     Field[]declaredFields = ManyPOJO1.getDeclaredFields();
	     HashMap<String, String> tableInfoMany1 = new HashMap<String, String>();
	     //tableInfo.put("tableName", tableName);
	     /*
	      * 创建ResultMap,并装入collectionOfTableInfo中表信息
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
		 * 多对多分页列表
		 */
		createManyToManySelectListByPageSQL(rootElement, ManyPOJO1, identification1, tableName1, ManyPOJO2, identification2, tableName2, MappingTableName);

	     /*
	      * 列表条数
	      */
		createSelectCountSQL(rootElement, tableName1);

		/*
		 * 多对多添加,(无极联)
		 */
		createManyToManyInsertWithRelationSQL(rootElement, ManyPOJO1, identification1, tableName1, ManyPOJO2, identification2, tableName2, MappingTableName, M_T_M_Field_1);

	     /*
	      * 多对多删除(同时删除中间表数据)
	      */
		createManyToManyDeleteWithRelationSQL(rootElement, identification1, tableName1, MappingTableName);

	    /*
	     * 多对多根据id查询对象
	     */
		createManyToManySelectByPrimaryKeySQL(rootElement, ManyPOJO1, tableName1, identification1);

	     /*
	      * 多对多修改(修改中间表数据)
	      */
		createManyToManyUpdateByPrimaryKeySQL(rootElement, ManyPOJO1, identification1, identification2, tableName1, MappingTableName, M_T_M_Field_1);




	     /*
	      * 创建XML
	      */
	     createXMLByRootDocument(rootDocument,ManyPOJO1);
	     
	     
	     
	     
	    
	    
	}
	/**
	 * -----------------------------MANY TO MANY---------------------------------------------------
	 */

	/**
	 *
	 * @function 多对多分页列表
	 * @author 吴相博
	 * @time 2016-2-27 上午9:46:58
	 */
	private void createManyToManySelectListByPageSQL(Element rootElement,Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,String MappingTableName){
		
		
		   /*
	      * 多对多分页列表
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
	      * 原型SQL
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
	      * 分页列表嵌套SQL
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
			INSERT INTO ssh_user(username) VALUES('全能');
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
	      * 1.删除关联表数据
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
	      * 1.删除关系
	      */
	     StringBuffer sql_updateM_T_MEmptyRelationKeyBuffer = new StringBuffer();
	     sql_updateM_T_MEmptyRelationKeyBuffer
	     .append("\n\tDELETE FROM ").append(MappingTableName)
	     .append(" WHERE ").append(identification1)
	     .append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     
	     //1
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateM_T_MEmptyRelationKeyBuffer.toString());
	     
	     
	      /*
	      * 添加现有的关系
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
	 * @function 一对多列表查询
	 * @author 吴相博
	 * @time 2016-2-23 上午9:36:47
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
	 * @function 一对多根据ID查询
	 * @author 吴相博
	 * @time 2016-2-23 上午9:48:21
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
	 * @function 一对多删除
	 * @author 吴相博
	 * @time 2016-2-24 下午6:42:21
	 */
	private void createOneToManyDeleteSQL(Element rootElement,String One_POJO_tableName,String One_POJOIdentification,
			String Many_POJO_tableName){
		
		
		Element deleteO_T_MByPrimaryKeyEle = rootElement.addElement("delete");
	     
	    
	    
	     
	     /*
	      * 删除一方表的内容
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
	      * 修改多方表的内容
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
	 * @function 一对多关联修改
	 * @author 吴相博
	 * @time 2016-2-24 下午8:05:32
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
	      * 1.修改多方表的内容为NULL
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
	      * 添加现有的关系
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
	 * @function 多对一修改Sql
	 * @author 吴相博
	 * @time 2016-2-22 下午8:06:04
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
	 * @function 创建多对一根据ID查询
	 * @author 吴相博
	 * @time 2016-2-22 下午7:22:42
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
	 * @function 创建多对一添加
	 * @author 吴相博
	 * @time 2016-2-22 下午7:22:27
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
	 * @function 多对一基础SQL
	 * @author 吴相博
	 * @time 2016-2-22 下午6:06:11
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
	 * @function 多对一分页SQL
	 * @author 吴相博
	 * @time 2016-2-22 下午6:10:14
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
	 * @serialNo 3.执行序列3.1
	 * @function 创建基础公用SQL语句
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
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
	 * @serialNo 3.执行序列3.2
	 * @function 创建简单分页SQL语句
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 * @param cls 类对象
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
	 * @serialNo 3.执行序列3.3
	 * @function 查询列表数据条数
	 * @author 吴相博
	 * @time 2016-2-22 下午3:51:12
	 * @param rootElement 根元素
	 * @param tableName 表名
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
	 * @serialNo 3.执行序列3.4
	 * @function 创建查找对象SQL
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 * @param cls 类对象
	 * @param identification 该类对象的标识属性
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
	 * @serialNo 3.执行序列3.5
	 * @function 创建删除SQL
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 * @param identification 该类对象的标识属性
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
	 * @serialNo 3.执行序列3.6
	 * @function 创建增加对象SQL
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 * @param cls 类对象
	 * @param identification 该类对象的标识属性
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
	 * @serialNo 3.执行序列3.7
	 * @function 创建更新对象SQL
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 * @param cls 类对象
	 * @param identification 该类对象的标识属性
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
	 * @serialNo 4.执行序列4
	 * @function 创建XML
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootDocument 根文档
	 * @param cls 类对象
	 */
	private void createXMLByRootDocument(Document rootDocument,Class<?> cls){
		
		Writer writer = null;
	     String pathWay = "src/"+basePath.replace('.','/')+"/"+MAPPERPACKAGENAME+cls.getSimpleName()+"Mapper.xml";
	     
		try {
			writer = new FileWriter(pathWay);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	   
	     //格式化xml
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
