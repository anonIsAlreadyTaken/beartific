package org.wuxb.beartific.batisUtils.xmlWriteUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XMLWriteUtils {

	/**
	 *
	 * @function 完成XML生成
	 * @author 吴相博
	 * @time 2016-2-27 下午6:27:37
	 */
public static void createXMLByRootDocument(Document rootDocument,Class<?> cls,String basePath,String MAPPERPACKAGENAME){
		
		Writer writer = null;
	     String pathWay = "src/main/java/"+basePath.replace('.','/')+"/"+MAPPERPACKAGENAME+cls.getSimpleName()+"Mapper.xml";
	     
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
