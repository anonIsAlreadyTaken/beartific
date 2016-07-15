package org.wuxb.beartific.batisUtils.buidPath;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class BuildPackagePathClass {
	

	/**
	 * 
	 * @function 创建DAO包和Mapper包
	 * @author 吴相博
	 * @time 2016-2-18 上午9:38:12
	 */
	public static String makeDAOAndMapperPackage(Class<?> c,
													   String basePath,
													   String DAOPACKAGENAME,
													   String MAPPERPACKAGENAME,
													   Map<String, String> collectionOfAbsolutePath,
													   String BASEPATH,
													   String DAOPATH,
													   String MAPPERPATH
													    ) throws IOException{
		
		//获得包名
		String packageName = c.getPackage().getName();
		int lastIndexOfSpace = packageName.lastIndexOf(" ");
		//处理获得的包名
		String forUserPackageName = packageName.substring(lastIndexOfSpace+1);
		//处理获得的包名
		int lastIndexOfCut = packageName.lastIndexOf('.');
		basePath = packageName.substring(lastIndexOfSpace+1,lastIndexOfCut);
		//System.out.println(basePath);
		//得到传入对象的相对路径
		String entityLocation = "src/main/java/"+forUserPackageName.replace('.','/')+"/"+c.getSimpleName()+".java";
		//得到传入对象的绝对路径
		File file = new File(entityLocation);
		String absoluteEntityLocation = file.getAbsolutePath();
		System.out.println(absoluteEntityLocation);
		//处理绝对路径，拼接DAO层与Mapper层地址
		int indexOf = absoluteEntityLocation.lastIndexOf('\\');
		String middleElement = absoluteEntityLocation.substring(0, indexOf);
		String basePath_C = middleElement.substring(0, middleElement.lastIndexOf('\\'))+"\\";
		String DaoPathWay =  basePath_C+DAOPACKAGENAME;
		File file3 = new File(DaoPathWay);
		boolean mkdir2 = file3.mkdir();
		String mapperPathWay = basePath_C+MAPPERPACKAGENAME;
		File file4 = new File(mapperPathWay);
		boolean mkdir = file4.mkdir();
		
	System.out.println("DAO:"+mkdir2+";Mapper:"+mkdir);
	
	collectionOfAbsolutePath.put(BASEPATH, basePath_C);
	collectionOfAbsolutePath.put(DAOPATH, DaoPathWay);
	collectionOfAbsolutePath.put(MAPPERPATH, mapperPathWay);
	
	
	
	return basePath;
		
	}
	
}
