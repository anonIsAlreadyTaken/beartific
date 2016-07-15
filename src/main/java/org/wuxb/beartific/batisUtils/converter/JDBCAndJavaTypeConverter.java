package org.wuxb.beartific.batisUtils.converter;

import java.util.Map;

public class JDBCAndJavaTypeConverter {

	
	
	/**
	 * @serialNo 1.ִ������1
	 * @function ����JAVA��JDBC����ת������
	 * @author ���಩
	 * @time 2016-2-21 ����6:02:33
	 */
	public static void buildJDBCAndJavaTypeConvert(Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		collectionOfJDBCAndJAVATypeConvert.put("java.lang.Integer", "INTEGER");
		collectionOfJDBCAndJAVATypeConvert.put("java.lang.String", "VARCHAR");
		collectionOfJDBCAndJAVATypeConvert.put("java.lang.Double", "DOUBLE");
		collectionOfJDBCAndJAVATypeConvert.put("java.lang.Float", "FLOAT");
		collectionOfJDBCAndJAVATypeConvert.put("java.util.Date", "DATE");
		collectionOfJDBCAndJAVATypeConvert.put("java.sql.Date", "DATE");
		collectionOfJDBCAndJAVATypeConvert.put("double", "DOUBLE");
		collectionOfJDBCAndJAVATypeConvert.put("int", "INTEGER");
		
		
	}
	
	
}
