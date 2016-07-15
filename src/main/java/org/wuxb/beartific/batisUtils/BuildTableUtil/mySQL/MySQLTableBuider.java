package org.wuxb.beartific.batisUtils.BuildTableUtil.mySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public interface MySQLTableBuider {
	
	void initBuider();
	
	public void itIsSimpleTableBuildingTime(Map<String, Map<String, String>> collectionOfTableInfo,String identification);
	
	public void itIsSimpleTableBuildingTime(Map<String, Map<String, String>> collectionOfTableInfo,String Many_POJO_identification,String One_POJO_identification,String Many_POJO_tableName,String One_POJO_tableName);
	
	public void itIsSimpleTableBuildingTime(Map<String, Map<String, String>> collectionOfTableInfo,String identification2,String tableName2,String identification1,String tableName1,String MappingTableName);
		

	public Connection getConnection();
	
	public void free(ResultSet rs,Statement st,Connection conn);
}
