package org.wuxb.beartific.batisUtils.elseUtilStuff;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@SuppressWarnings("deprecation")
public class HibernateXMLAutoCreateUtilsVersion2 {
	
	/**
	 * 
	 * @function ���POJO����ɼ򵥵���Ӧ*.hbm.xml�����ļ������ӹ������ڴ˻����ֶ����á�<br/>(Tip:�������POJO��ʹ��junit����main����Ԥ��ִ�д˷�������������ļ�)
	 * @author anon
	 * @time 2016-1-13 ����03:48:20
	 * @param cls POJO�������
	 * @param identification ��ݿ��������Ӧ����Ӧ��POJO������
	 * @param tableName ��ݿ��е���Ӧ����
	 */
	public static void createHibernatePOJOXML(Class<?> cls,String identification,String tableName){
		
		 
		 Document rootDocument = DocumentHelper.createDocument();
	     rootDocument.addDocType("hibernate-mapping", "-//Hibernate/Hibernate Mapping DTD 3.0//EN", "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
	     //�����
	     Element rootElement = rootDocument.addElement("hibernate-mapping");
	     //�ڸ��мӵ�һ���ӽڵ�
	     Element elementClass = rootElement.addElement("class");
	     elementClass.addAttribute("name", cls.getName());
	     elementClass.addAttribute("table", tableName);
	     Element ElementClassID = elementClass.addElement("id");
	     Field[] declaredFields = cls.getDeclaredFields();
	     for (Field field : declaredFields) {
	    	 
	    	 if(field.getName().equals(identification)){
	    		 
	    		 ElementClassID.addAttribute("name", identification);
	    		 ElementClassID.addAttribute("type", field.getType().getName());
	    		 Element ElementClassIDColumn = ElementClassID.addElement("column");
	    		 ElementClassIDColumn.addAttribute("name", identification);
	    		 Element ElementClassIDGenerator = ElementClassID.addElement("generator");
	    	     ElementClassIDGenerator.addAttribute("class", "native");
	    	 }else{
	    		 
	    		 Element ElementProperty = elementClass.addElement("property");
	    		 ElementProperty.addAttribute("name", field.getName());
	    		 ElementProperty.addAttribute("type", field.getType().getName());
	    		 Element ElementColumn = ElementProperty.addElement("column");
	    		 ElementColumn.addAttribute("name", field.getName());
	    	 }
	    	 
			
		}
	     
	     //дxml
	   
	     Writer writer = null;
	     String pathWay = "src/"+cls.getName().replace('.','/')+".hbm.xml";
		try {
			writer = new FileWriter(pathWay);
			
		} catch (IOException e) {
			System.out.println("HibernateXMLAutoCreateUtils:XML��ɴ���,��������������Ϊ���ó�");
			e.printStackTrace();
		}
	     //System.out.println(cls.getName().replace('.','/'));
	     //��ʽ��xml
	     OutputFormat format = OutputFormat.createPrettyPrint();
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
	
	/**
	 * 
	 * @function ʹ�ô˷��������ļ��е��ַ������GBK,�������ֽ������⡣<br/>�˷�����������������ļ���ͬʱ�����������ļ�ӳ��<br/>���POJO����ɼ򵥵���Ӧ*.hbm.xml�����ļ������ӹ������ڴ˻����ֶ����á�<br/>(Tip:�������POJO��ʹ��junit����main����Ԥ��ִ�д˷�������������ļ�)
	 * @author anon
	 * @time 2016-1-13 ����03:48:20
	 * @param cls POJO�������
	 * @param identification ��ݿ��������Ӧ����Ӧ��POJO������
	 * @param tableName ��ݿ��е���Ӧ����
	 */
	public static void createHibernatePOJOMapping(Class<?> cls,String identification,String tableName){
		
		 
		 Document rootDocument = DocumentHelper.createDocument();
	     rootDocument.addDocType("hibernate-mapping", "-//Hibernate/Hibernate Mapping DTD 3.0//EN", "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
	     //�����
	     Element rootElement = rootDocument.addElement("hibernate-mapping");
	     //�ڸ��мӵ�һ���ӽڵ�
	     Element elementClass = rootElement.addElement("class");
	     elementClass.addAttribute("name", cls.getName());
	     elementClass.addAttribute("table", tableName);
	     Element ElementClassID = elementClass.addElement("id");
	     Field[] declaredFields = cls.getDeclaredFields();
	     for (Field field : declaredFields) {
	    	 
	    	 if(field.getName().equals(identification)){
	    		 
	    		 ElementClassID.addAttribute("name", identification);
	    		 ElementClassID.addAttribute("type", field.getType().getName());
	    		 Element ElementClassIDColumn = ElementClassID.addElement("column");
	    		 ElementClassIDColumn.addAttribute("name", identification);
	    		 Element ElementClassIDGenerator = ElementClassID.addElement("generator");
	    	     ElementClassIDGenerator.addAttribute("class", "native");
	    	 }else{
	    		 
	    		 Element ElementProperty = elementClass.addElement("property");
	    		 ElementProperty.addAttribute("name", field.getName());
	    		 ElementProperty.addAttribute("type", field.getType().getName());
	    		 Element ElementColumn = ElementProperty.addElement("column");
	    		 ElementColumn.addAttribute("name", field.getName());
	    	 }
	    	 
			
		}
	     
	     //дxml
	   
	     Writer writer = null;
	     String pathWay = "src/"+cls.getName().replace('.','/')+".hbm.xml";
		try {
			writer = new FileWriter(pathWay);
		} catch (IOException e) {
			System.out.println("HibernateXMLAutoCreateUtils:XML��ɴ���,��������������Ϊ���ó�");
			e.printStackTrace();
		}
	     //System.out.println(cls.getName().replace('.','/'));
	     //��ʽ��xml
	     OutputFormat format = OutputFormat.createPrettyPrint();
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
		
		SAXReader reader = new SAXReader();
		/*
		 *��EntityResolver ���һ��new EntityResolver()�Ļ��Ϳ��Խ�������DTDԼ���ļ���XML
		 */
		reader.setEntityResolver(new EntityResolver() {
			
			
			
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				return new InputSource(new StringBufferInputStream(""));
			}
		});
		 Document doc = null;
		try {
			doc = reader.read(new File("src/hibernate.cfg.xml"));
		} catch (DocumentException e2) {
			System.out.println("[createHibernatePOJOMapping]˵���������ļ�Ҫ��GBK�����ʽ�����Ӿ��ǲ���");
			System.out.println("[createHibernatePOJOMapping]ɾ���������ļ�Ȼ������Ӻ��޸������ļ����뼯ΪGBK");
			e2.printStackTrace();
		}
		
		 List<?> selectNodes = doc.selectNodes("/hibernate-configuration/session-factory/mapping/@resource");
		 boolean flag = false;
		 for (Iterator<?> i = selectNodes.iterator(); i.hasNext();) {
			Attribute mapping = (Attribute) i.next();
			//System.out.println(mapping.getText());
			
			if(mapping.getText().equals(pathWay.substring(4))){
				
				flag = true;
			}
			
		}
		 
		 if(!flag){
			 List<?> sessionFactoryNodes = doc.selectNodes("/hibernate-configuration/session-factory");
			 Element sessionFactoryNode = (Element) sessionFactoryNodes.get(0);
			 Element mappingElementForAdd = sessionFactoryNode.addElement("mapping");
			 mappingElementForAdd.addAttribute("resource",pathWay.substring(4));
		 }
		 
		 OutputFormat cpp = OutputFormat.createPrettyPrint();
	     cpp.setEncoding(doc.getXMLEncoding());
		 
		 XMLWriter writerConfig = null;
		try {
			writerConfig = new XMLWriter(new FileWriter(new File(
						"src/hibernate.cfg.xml")),cpp);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 	try {
				writerConfig.write(doc);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(writerConfig!=null){
				
				try {
					writerConfig.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		
		
		
	    
	}
	
	
	
	
	/**
	 * 
	 * @function ��ɶ��һ��ϵ��ӳ���ļ�<br/>
	 * ʹ�ô˷��������ļ��е��ַ������GBK,�������ֽ������⡣<br/>
	 * �˷�����������������ļ���ͬʱ�����������ļ�ӳ��<br/>
	 * (Tip:�������POJO��ʹ��junit����main����Ԥ��ִ�д˷�������������ļ�)
	 * @author anon
	 * @time 2016-1-13 ����03:48:20
	 * @param Many_POJO N-1��ϵN�˵������
	 * @param Many_POJOIdentification N-1��ϵN�˶����Ӧ�����������Ӧ��������
	 * @param Many_POJO_tableName N-1��ϵN�˶����Ӧ�ı���
	 * @param One_POJO N-1��ϵ1�˵������
	 * @param One_POJOIdentification N-1��ϵ1�˶����Ӧ�����������Ӧ��������
	 * @param One_POJO_tableName N-1��ϵ1�˶����Ӧ�ı���
	 */
	public static void createHibernatePOJOMappingManyToOne(Class<?> Many_POJO,String Many_POJOIdentification,String Many_POJO_tableName,Class<?> One_POJO,String One_POJOIdentification,String One_POJO_tableName){
		
		 
		 Document rootDocumentPOJO1 = DocumentHelper.createDocument();
		 rootDocumentPOJO1.addDocType("hibernate-mapping", "-//Hibernate/Hibernate Mapping DTD 3.0//EN", "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
	     //�����
	     Element rootElement1 = rootDocumentPOJO1.addElement("hibernate-mapping");
	     //�ڸ��мӵ�һ���ӽڵ�
	     Element elementClass1 = rootElement1.addElement("class");
	     elementClass1.addAttribute("name", Many_POJO.getName());
	     elementClass1.addAttribute("table", Many_POJO_tableName);
	     Element ElementClassID1 = elementClass1.addElement("id");
	     Field[] declaredFields1 = Many_POJO.getDeclaredFields();
	     //System.out.println(One_POJO.getSimpleName());
	     for (Field field : declaredFields1) {
	    	 //System.out.println(field.getName());
	    	 //System.out.println(field.getType().getSimpleName());
	    	 if(field.getName().equals(Many_POJOIdentification)){
	    		 
	    		 ElementClassID1.addAttribute("name", Many_POJOIdentification);
	    		 ElementClassID1.addAttribute("type", field.getType().getName());
	    		 Element ElementClassIDColumn = ElementClassID1.addElement("column");
	    		 ElementClassIDColumn.addAttribute("name", Many_POJOIdentification);
	    		 Element ElementClassIDGenerator = ElementClassID1.addElement("generator");
	    	     ElementClassIDGenerator.addAttribute("class", "native");
	    	 }else if(field.getType().getSimpleName().equals(One_POJO.getSimpleName())){
	    		 
	    		 Element MTOElement = elementClass1.addElement("many-to-one");
	    		 MTOElement.addAttribute("name", field.getName());
	    		 
	    		 MTOElement.addAttribute("lazy", "false");
	    		
	    		 MTOElement.addAttribute("column", One_POJOIdentification);
	    		
	    		 MTOElement.addAttribute("class", One_POJO.getName());
	    		 
	    		 
	    	 }else{
	    		 
	    		 Element ElementProperty = elementClass1.addElement("property");
	    		 ElementProperty.addAttribute("name", field.getName());
	    		 ElementProperty.addAttribute("type", field.getType().getName());
	    		 Element ElementColumn = ElementProperty.addElement("column");
	    		 ElementColumn.addAttribute("name", field.getName());
	    	 }
	    	 
			
		}
	     
	     
	     
	     Document rootDocumentPOJO2 = DocumentHelper.createDocument();
		 rootDocumentPOJO2.addDocType("hibernate-mapping", "-//Hibernate/Hibernate Mapping DTD 3.0//EN", "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
	     //�����
	     Element rootElement2 = rootDocumentPOJO2.addElement("hibernate-mapping");
	     //�ڸ��мӵ�һ���ӽڵ�
	     Element elementClass2 = rootElement2.addElement("class");
	     elementClass2.addAttribute("name", One_POJO.getName());
	     elementClass2.addAttribute("table", One_POJO_tableName);
	     Element ElementClassID2 = elementClass2.addElement("id");
	     Field[] declaredFields2 = One_POJO.getDeclaredFields();
	     for (Field field : declaredFields2) {
	    	 //System.out.println(field.getName());
	    	 if(field.getName().equals(One_POJOIdentification)){
	    		 
	    		 ElementClassID2.addAttribute("name", One_POJOIdentification);
	    		 ElementClassID2.addAttribute("type", field.getType().getName());
	    		 Element ElementClassIDColumn = ElementClassID2.addElement("column");
	    		 ElementClassIDColumn.addAttribute("name", One_POJOIdentification);
	    		 Element ElementClassIDGenerator = ElementClassID2.addElement("generator");
	    	     ElementClassIDGenerator.addAttribute("class", "native");
	    	 }else if(field.getType().getSimpleName().equals("Set")){
	    		 
	    		 Element SetElement = elementClass2.addElement("set");
	    		 SetElement.addAttribute("name", field.getName());
	    		 
	    		 SetElement.addAttribute("lazy", "false");
	    		 SetElement.addAttribute("order-by", One_POJOIdentification);
	    		 Element keyElement = SetElement.addElement("key");
	    		 keyElement.addAttribute("column", One_POJOIdentification);
	    		 Element MTOLable = SetElement.addElement("one-to-many");
	    		 MTOLable.addAttribute("class", Many_POJO.getName());
	    		 
	    		 
	    	 }else{
	    		 
	    		 Element ElementProperty = elementClass2.addElement("property");
	    		 ElementProperty.addAttribute("name", field.getName());
	    		 ElementProperty.addAttribute("type", field.getType().getName());
	    		 Element ElementColumn = ElementProperty.addElement("column");
	    		 ElementColumn.addAttribute("name", field.getName());
	    	 }
	    	 
			
		}
	     
	    
			
		
	     
	     //дxml
	   
	     Writer writer1 = null;
	     Writer writer2 = null;
	     String pathWay1 = "src/"+Many_POJO.getName().replace('.','/')+".hbm.xml";
	     String pathWay2 = "src/"+One_POJO.getName().replace('.','/')+".hbm.xml";
		try {
			writer1 = new FileWriter(pathWay1);
			writer2 = new FileWriter(pathWay2);
		} catch (IOException e) {
			System.out.println("HibernateXMLAutoCreateUtils:XML��ɴ���,��������������Ϊ���ó�");
			e.printStackTrace();
		}
	     //System.out.println(cls.getName().replace('.','/'));
	     //��ʽ��xml
	     OutputFormat format = OutputFormat.createPrettyPrint();
	     format.setEncoding("UTF-8");

	     XMLWriter xml1 = new XMLWriter(writer1,format);
	     XMLWriter xml2 = new XMLWriter(writer2,format);
	     
	     
	     
	     try {
			xml1.write(rootDocumentPOJO1);
			System.out.println(xml1);
			xml2.write(rootDocumentPOJO2);
			System.out.println(xml2);
		} catch (IOException e) {
		
			e.printStackTrace();
			
		}finally{
			
			
			
			if(writer1!=null){
				
				try {
					writer1.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
			if(writer2!=null){
				
				try {
					writer2.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
			
			 if(xml1!=null){
		    	 
		    	 try {
		 			xml1.close();
		 		} catch (IOException e) {
		 			e.printStackTrace();
		 		}
		     }
			
			 if(xml2!=null){
				 
				 try {
					 xml2.close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
			 
		}
		
		SAXReader reader = new SAXReader();
		/*
		 *��EntityResolver ���һ��new EntityResolver()�Ļ��Ϳ��Խ�������DTDԼ���ļ���XML
		 */
		reader.setEntityResolver(new EntityResolver() {
			
			
			
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				return new InputSource(new StringBufferInputStream(""));
			}
		});
		 Document doc = null;
		try {
			doc = reader.read(new File("src/hibernate.cfg.xml"));
		} catch (DocumentException e2) {
			System.out.println("[createHibernatePOJOMapping]˵���������ļ�Ҫ��GBK�����ʽ�����Ӿ��ǲ���");
			System.out.println("[createHibernatePOJOMapping]ɾ���������ļ�Ȼ������Ӻ��޸������ļ����뼯ΪGBK");
			e2.printStackTrace();
		}
		
		 List<?> selectNodes = doc.selectNodes("/hibernate-configuration/session-factory/mapping/@resource");
		 boolean flagPOJO1 = false;
		 boolean flagPOJO2 = false;
		 for (Iterator<?> i = selectNodes.iterator(); i.hasNext();) {
			Attribute mapping = (Attribute) i.next();
			//System.out.println(mapping.getText());
			
			if(mapping.getText().equals(pathWay1.substring(4))){
				
				flagPOJO1 = true;
			}
			if(mapping.getText().equals(pathWay2.substring(4))){
				
				flagPOJO2 = true;
			}
			
		}
		 
		 if(!flagPOJO1){
			 List<?> sessionFactoryNodes = doc.selectNodes("/hibernate-configuration/session-factory");
			 Element sessionFactoryNode = (Element) sessionFactoryNodes.get(0);
			 Element mappingElementForAdd = sessionFactoryNode.addElement("mapping");
			 mappingElementForAdd.addAttribute("resource",pathWay1.substring(4));
			
		 }
		 if(!flagPOJO2){
			 List<?> sessionFactoryNodes = doc.selectNodes("/hibernate-configuration/session-factory");
			 Element sessionFactoryNode = (Element) sessionFactoryNodes.get(0);
			 Element mappingElementForAdd = sessionFactoryNode.addElement("mapping");
			 mappingElementForAdd.addAttribute("resource",pathWay2.substring(4));
			 
		 }
		 
		 OutputFormat cpp = OutputFormat.createPrettyPrint();
	     cpp.setEncoding(doc.getXMLEncoding());
		 
		 XMLWriter writerConfig = null;
		try {
			writerConfig = new XMLWriter(new FileWriter(new File(
						"src/hibernate.cfg.xml")),cpp);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 	try {
				writerConfig.write(doc);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(writerConfig!=null){
				
				try {
					writerConfig.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		
	    
	}
	
	/**
	 * 
	 * @function ��ɶ�Զ��ϵ��ӳ���ļ����������<br/>
	 * @author anon
	 * @time 2016-1-18 ����01:34:43
	 * @param ManyPOJO1 N-N��ϵ����һ�˵������
	 * @param identification1 N-N��ϵ����һ�˶�Ӧ�����������Ӧ�����Ե�������
	 * @param tableName1 N-N��ϵ��һ�˶�Ӧ��ı���
	 * @param ManyPOJO2 N-N��ϵ����һ�˵������
	 * @param identification2 N-N��ϵ����һ�˶�Ӧ�����������Ӧ�����Ե�������
	 * @param tableName2 N-N��ϵ��һ�˶�Ӧ��ı���
	 * @param MappingTableName ��ϵ��ı���
	 */
	private static Document createHibernatePOJOMappingManyToManyComponent(Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,String MappingTableName){
		
		 Document rootDocumentPOJO = DocumentHelper.createDocument();
		 rootDocumentPOJO.addDocType("hibernate-mapping", "-//Hibernate/Hibernate Mapping DTD 3.0//EN", "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
	     //�����
	     Element rootElement = rootDocumentPOJO.addElement("hibernate-mapping");
	     //�ڸ��мӵ�һ���ӽڵ�
	     Element elementClass = rootElement.addElement("class");
	     elementClass.addAttribute("name", ManyPOJO1.getName());
	     elementClass.addAttribute("table", tableName1);
	     Element ElementClassID = elementClass.addElement("id");
	     Field[] declaredFields = ManyPOJO1.getDeclaredFields();
	     for (Field field : declaredFields) {
	    	 
	    	 if(field.getName().equals(identification1)){
	    		 
	    		 ElementClassID.addAttribute("name", identification1);
	    		 ElementClassID.addAttribute("type", field.getType().getName());
	    		 Element ElementClassIDColumn = ElementClassID.addElement("column");
	    		 ElementClassIDColumn.addAttribute("name", identification1);
	    		 Element ElementClassIDGenerator = ElementClassID.addElement("generator");
	    	     ElementClassIDGenerator.addAttribute("class", "native");
	    	 }else if(field.getType().getSimpleName().equals("Set")){
	    		 
	    		 Element SetElement = elementClass.addElement("set");
	    		 SetElement.addAttribute("name", field.getName());
	    		 SetElement.addAttribute("table", MappingTableName);
	    		 SetElement.addAttribute("lazy", "false");
	    		 SetElement.addAttribute("order-by", identification1);
	    		 Element keyElement = SetElement.addElement("key");
	    		 keyElement.addAttribute("column", identification1);
	    		 Element MTMLable = SetElement.addElement("many-to-many");
	    		 MTMLable.addAttribute("class", ManyPOJO2.getName());
	    		 MTMLable.addAttribute("column", identification2);
	    		 
	    	 }else{
	    		 
	    		 Element ElementProperty = elementClass.addElement("property");
	    		 ElementProperty.addAttribute("name", field.getName());
	    		 ElementProperty.addAttribute("type", field.getType().getName());
	    		 Element ElementColumn = ElementProperty.addElement("column");
	    		 ElementColumn.addAttribute("name", field.getName());
	    	 }
	    	 
			
		}
	     
	     return rootDocumentPOJO;
	}
	
	/**
	 * 
	 * @function ʹ�ô˷��������ļ��е��ַ������GBK,�������ֽ������⡣<br/>�˷�����������������ļ���ͬʱ�����������ļ�ӳ��<br/>���POJO����ɼ򵥵���Ӧ*.hbm.xml�����ļ������ӹ������ڴ˻����ֶ����á�<br/>(Tip:�������POJO��ʹ��junit����main����Ԥ��ִ�д˷�������������ļ�)
	 * <br/>ʹ�ô˷������Ը��ָ����POJO�ͱ�����������Զ��ϵӳ���ļ���<br/>TIP��ʹ�ô˷�����ɵ�ӳ���ļ�Ĭ��lazy='false'
	 * <br/>�����Ҫ�ظ���ɵĻ�,��ɾ���Լ��ֶ���ӵ��������ͺ������
	 * @author anon
	 * @time 2016-1-13 ����03:48:20
	 * @param ManyPOJO1 ��Զ�����ĵ�һ����
	 * @param identification1 ��һ����ı�ʶ�ж�Ӧ��������
	 * @param tableName1 ��һ�����Ӧ����ݿ�����
	 * @param ManyPOJO2 ��Զ�����ĵڶ�����
	 * @param identification2 �ڶ�����ı�ʶ�ж�Ӧ��������
	 * @param tableName2 �ڶ������Ӧ����ݿ�����
	 * @param MappingTableName ������ı���
	 * 
	 * 
	 * 
	 */
	public static void createHibernatePOJOMappingManyToMany(Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,String MappingTableName){
		
		 
		Document rootDocumentPOJO1 = createHibernatePOJOMappingManyToManyComponent(ManyPOJO1, identification1, tableName1, ManyPOJO2, identification2, tableName2, MappingTableName);
		Document rootDocumentPOJO2 = createHibernatePOJOMappingManyToManyComponent(ManyPOJO2, identification2, tableName2, ManyPOJO1, identification1, tableName1, MappingTableName);
		
	     
	     //дxml
	   
	     Writer writer1 = null;
	     Writer writer2 = null;
	     String pathWay1 = "src/"+ManyPOJO1.getName().replace('.','/')+".hbm.xml";
	     String pathWay2 = "src/"+ManyPOJO2.getName().replace('.','/')+".hbm.xml";
		try {
			writer1 = new FileWriter(pathWay1);
			writer2 = new FileWriter(pathWay2);
			
		} catch (IOException e) {
			System.out.println("HibernateXMLAutoCreateUtils:XML��ɴ���,��������������Ϊ���ó�");
			e.printStackTrace();
		}
	     //System.out.println(cls.getName().replace('.','/'));
	     //��ʽ��xml
	     OutputFormat format = OutputFormat.createPrettyPrint();
	     format.setEncoding("UTF-8");

	     XMLWriter xml1 = new XMLWriter(writer1,format);
	     XMLWriter xml2 = new XMLWriter(writer2,format);
	     
	     
	     
	     try {
			xml1.write(rootDocumentPOJO1);
			xml2.write(rootDocumentPOJO2);
		} catch (IOException e) {
		
			e.printStackTrace();
			
		}finally{
			
			if(writer1!=null){
				
				try {
					writer1.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
			if(writer2!=null){
				
				try {
					writer2.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
			
			 if(xml1!=null){
		    	 
		    	 try {
		 			xml1.close();
		 		} catch (IOException e) {
		 			e.printStackTrace();
		 		}
		     }
			
			 if(xml2!=null){
				 
				 try {
					 xml2.close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
			 
		}
		
		SAXReader reader = new SAXReader();
		/*
		 *��EntityResolver ���һ��new EntityResolver()�Ļ��Ϳ��Խ�������DTDԼ���ļ���XML
		 */
		reader.setEntityResolver(new EntityResolver() {
			
			
			
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				return new InputSource(new StringBufferInputStream(""));
			}
		});
		 Document doc = null;
		try {
			doc = reader.read(new File("src/hibernate.cfg.xml"));
		} catch (DocumentException e2) {
			System.out.println("[createHibernatePOJOMapping]˵���������ļ�Ҫ��GBK�����ʽ�����Ӿ��ǲ���");
			System.out.println("[createHibernatePOJOMapping]ɾ���������ļ�Ȼ������Ӻ��޸������ļ����뼯ΪGBK");
			e2.printStackTrace();
		}
		
		 List<?> selectNodes = doc.selectNodes("/hibernate-configuration/session-factory/mapping/@resource");
		 boolean flagPOJO1 = false;
		 boolean flagPOJO2 = false;
		 for (Iterator<?> i = selectNodes.iterator(); i.hasNext();) {
			Attribute mapping = (Attribute) i.next();
			//System.out.println(mapping.getText());
			
			if(mapping.getText().equals(pathWay1.substring(4))){
				
				flagPOJO1 = true;
			}
			if(mapping.getText().equals(pathWay2.substring(4))){
				
				flagPOJO2 = true;
			}
			
		}
		 
		 if(!flagPOJO1){
			 List<?> sessionFactoryNodes = doc.selectNodes("/hibernate-configuration/session-factory");
			 Element sessionFactoryNode = (Element) sessionFactoryNodes.get(0);
			 Element mappingElementForAdd = sessionFactoryNode.addElement("mapping");
			 mappingElementForAdd.addAttribute("resource",pathWay1.substring(4));
			
		 }
		 if(!flagPOJO2){
			 List<?> sessionFactoryNodes = doc.selectNodes("/hibernate-configuration/session-factory");
			 Element sessionFactoryNode = (Element) sessionFactoryNodes.get(0);
			 Element mappingElementForAdd = sessionFactoryNode.addElement("mapping");
			 mappingElementForAdd.addAttribute("resource",pathWay2.substring(4));
			 
		 }
		 
		 OutputFormat cpp = OutputFormat.createPrettyPrint();
	     cpp.setEncoding(doc.getXMLEncoding());
		 
		 XMLWriter writerConfig = null;
		try {
			writerConfig = new XMLWriter(new FileWriter(new File(
						"src/hibernate.cfg.xml")),cpp);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		 
		 	try {
				writerConfig.write(doc);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(writerConfig!=null){
				
				try {
					writerConfig.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		
		
		
	    
	}

}
