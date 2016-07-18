package org.wuxb.beartific.batisUtils.SQL.M_T_MRelationSQL;

import java.util.Map;

import org.dom4j.Element;

public interface M_T_MRelationSQLCreater {


	/**
	 *
	 * @function 多对多分页列表
	 * @author 吴相博
	 * @time 2016-2-27 上午9:46:58
	 */
	public void createManyToManySelectListByPageSQL(Element rootElement,Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,String MappingTableName,
			String idType,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);;

	/**
	 *
	 * @function 多对多关联增加
	 * @author 吴相博
	 * @time 2016-2-27 下午4:24:17
	 */
	public void createManyToManyInsertWithRelationSQL(Element rootElement,Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,
			
			String MappingTableName,String M_T_M_Field_1,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo);;

	/**
	 *
	 * @function 多对多关联删除
	 * @author 吴相博
	 * @time 2016-2-27 下午4:24:32
	 */
	public void createManyToManyDeleteWithRelationSQL(Element rootElement,String identification1,String tableName1,String MappingTableName,
			String idType,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);;

	/**
	 *
	 * @function
	 * @author 吴相博多对多根据ID查询
	 * @time 2016-2-27 下午4:24:47
	 */
	public void createManyToManySelectByPrimaryKeySQL(Element rootElement,Class<?> ManyPOJO1,String tableName1,String identification1,
						String idType,
						String RESULTMAPPER_ID_NAME,
						Map<String, Map<String, String>> collectionOfSqlInfo,
						Map<String, Map<String, String>> collectionOfTableInfo,
						Map<String, String> collectionOfJDBCAndJAVATypeConvert);
	/**
	 *
	 * @function 多对多关联修改
	 * @author 吴相博
	 * @time 2016-2-27 下午4:25:08
	 */
	public void createManyToManyUpdateByPrimaryKeySQL(Element rootElement,Class<?> ManyPOJO1,String identification1,String identification2,String tableName1,
			String MappingTableName,String M_T_M_Field_1,
			String idType,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);;
	
	

}
