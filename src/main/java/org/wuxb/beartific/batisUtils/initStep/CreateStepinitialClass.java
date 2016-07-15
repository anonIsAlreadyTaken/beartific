package org.wuxb.beartific.batisUtils.initStep;

import java.io.IOException;
import java.util.Map;

import org.wuxb.beartific.batisUtils.buidPath.BuildPackagePathClass;
import org.wuxb.beartific.batisUtils.converter.JDBCAndJavaTypeConverter;

public class CreateStepinitialClass {
	
	/**
	 * 
	 * @function ��ʼ������
	 * @author ���಩
	 * @time 2016-2-27 ����4:31:10
	 */
	public static String firstStepOfXmlCreate(Class<?> cls,Map<String, String> collectionOfJDBCAndJAVATypeConvert,
											   String basePath,
											   String DAOPACKAGENAME,
											   String MAPPERPACKAGENAME,
											   Map<String, String> collectionOfAbsolutePath,
											   String BASEPATH,
											   String DAOPATH,
											   String MAPPERPATH){
		
		 JDBCAndJavaTypeConverter.buildJDBCAndJavaTypeConvert(collectionOfJDBCAndJAVATypeConvert);
		 try {
			basePath = BuildPackagePathClass.makeDAOAndMapperPackage(cls, basePath, DAOPACKAGENAME, MAPPERPACKAGENAME, collectionOfAbsolutePath, BASEPATH, DAOPATH, MAPPERPATH);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 
		 return basePath;
	}

}
