package org.wuxb.beartific.batisUtils.IOFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class InterfaceCreateUtil extends InterfaceCreateClass{

	private String srcBasePath;
	private Class<?> targetClass;
	@Override
	public void InterfaceAutoCreate() {
		
		
		Map<String, String> pathMap = super.getCollectionOfAbsolutePath();
		Map<String, Map<String, String>> methodPrototype = super.getCollectionOfSqlInfo();
		String DAOPath = pathMap.get(super.getBaseDaoKey());
		String packageLocation = "package "+srcBasePath+".DAO;";
		String importList = "import java.util.List;";
		String importMap = "import java.util.Map;";
		String interfaceName = targetClass.getSimpleName()+"Mapper";
		String classDeclared = "public interface "+interfaceName+"{";
		String end = "}";
		String importEntity = "import "+targetClass.getName()+";";
		BufferedWriter interfaceWriter = null;//����д�ķ�װ�ַ���
		try {
			
			
			
			interfaceWriter = new BufferedWriter(new FileWriter(DAOPath+interfaceName+".java")); //������װ��
			
			
			interfaceWriter.write(packageLocation);
			interfaceWriter.newLine();
			interfaceWriter.write(importList);
			interfaceWriter.newLine();
			interfaceWriter.write(importMap);
			interfaceWriter.newLine();
			interfaceWriter.newLine();
			interfaceWriter.write(importEntity);
			interfaceWriter.newLine();
			interfaceWriter.write(classDeclared);
			
			Set<String> keySet = methodPrototype.keySet();
			StringBuffer methodBody = new StringBuffer();
			for (String string : keySet) {
				interfaceWriter.newLine();
				interfaceWriter.newLine();
				methodBody.delete(0, methodBody.length());
				Map<String, String> methodFactor = methodPrototype.get(string);
				String argsFactor = "("+methodFactor.get("args")+")";
				String returnFactor = methodFactor.get("return");
				methodBody.append("public ").append(returnFactor)
				.append(" "+string).append(argsFactor).append(";");
				
				/*if(targetClass.getSimpleName().equals("Edu")){
					System.out.println(methodBody);
					
				}*/
				
				
				interfaceWriter.write(methodBody.toString());
				interfaceWriter.newLine();
			}
			
			interfaceWriter.write(end);
			interfaceWriter.flush();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			
			if(interfaceWriter!=null){
				
				try {
					interfaceWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		
	}

	public InterfaceCreateUtil() {
		super();
	}

	public InterfaceCreateUtil(Map<String, String> collectionOfAbsolutePath,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			String baseDaoKey, String srcBasePath, Class<?> targetClass) {
		super(collectionOfAbsolutePath, collectionOfSqlInfo, baseDaoKey);
		this.srcBasePath = srcBasePath;
		this.targetClass = targetClass;
	}

	public String getSrcBasePath() {
		return srcBasePath;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	

	
	

	

	
}
