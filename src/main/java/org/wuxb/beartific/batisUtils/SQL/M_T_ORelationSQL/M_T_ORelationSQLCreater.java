package org.wuxb.beartific.batisUtils.SQL.M_T_ORelationSQL;

import java.util.Map;

import org.dom4j.Element;

public interface M_T_ORelationSQLCreater {
	
	
	/**
	 * -----------------------------------ONE TO MANY----------------------------------------------------------
	 */

	/**
	 *
	 * @function 一对多列表查询
	 * @author 吴相博
	 * @time 2016-2-23 上午9:36:47
	 */
	public void createOneToManySelectListSQL(Element rootElement,Class<?> One_POJO,String One_POJO_tableName,String Many_POJO_tableName,
			String One_POJOIdentification,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Class<?> Many_POJO,
			String idType,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert
			);

	/**
	 *
	 * @function 一对多根据ID查询
	 * @author 吴相博
	 * @time 2016-2-23 上午9:48:21
	 */
	public void createOneToManySelectByIdSQL(Element rootElement,Class<?> One_POJO,String One_POJO_tableName,String Many_POJO_tableName,
			String One_POJOIdentification,
			String RESULTMAPPER_ID_NAME,
			String idType,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);


	/**
	 *
	 * @function 一对多删除
	 * @author 吴相博
	 * @time 2016-2-24 下午6:42:21
	 */
	public void createOneToManyDeleteSQL(Element rootElement,String One_POJO_tableName,String One_POJOIdentification,
			String Many_POJO_tableName,
			String idType,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert,
			Map<String, Map<String, String>> collectionOfSqlInfo);;

	/**
	 *
	 * @function 一对多关联修改
	 * @author 吴相博
	 * @time 2016-2-24 下午8:05:32
	 */
	public void createOneToManyUpdateWithRelationSQL(Element rootElement,Class<?> One_POJO,String Many_POJO_tableName,String One_POJOIdentification,
			String One_POJO_tableName,String Many_POJOIdentification,String O_T_M_Field,
			String idType,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo);;
	/**
	 * -----------------------------------MANY TO ONE-----------------------------------------------
	 */

	/**
	 *
	 * @function 多对一修改Sql
	 * @author 吴相博
	 * @time 2016-2-22 下午8:06:04
	 */
	public void createManyToOneUpdateSQL(Element rootElement,Class<?> Many_POJO,String Many_POJO_tableName,
			String One_POJO_tableName,String M_T_O_Field,String One_POJOIdentification,String Many_POJOIdentification,
			String idType,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);
	/**
	 *
	 * @function 创建多对一根据ID查询
	 * @author 吴相博
	 * @time 2016-2-22 下午7:22:42
	 */
	public void createManyToOneSelectById(Element rootElement,Class<?> Many_POJO,String Many_POJO_tableName,String Many_POJOIdentification,
			String idType,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);
	/**
	 *
	 * @function 创建多对一添加
	 * @author 吴相博
	 * @time 2016-2-22 下午7:22:27
	 */
	public void createManyToOneInsertSQL(Element rootElement,Class<?> Many_POJO,
			String Many_POJO_tableName,String One_POJO_tableName,String M_T_O_Field,
			String One_POJOIdentification,
			String idType,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);
	/**
	 *
	 * @function 多对一基础SQL
	 * @author 吴相博
	 * @time 2016-2-22 下午6:06:11
	 */
	public void manyToOneBaseColumnSQL(Element rootElement,String Many_POJO_tableName,String One_POJOIdentification,
			String idType,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);;
	/**
	 *
	 * @function 多对一分页SQL
	 * @author 吴相博
	 * @time 2016-2-22 下午6:10:14
	 */
	public void manyToOneSelectByPageSQL(Element rootElement,Class<?> Many_POJO,String Many_POJO_tableName,
			String One_POJO_tableName,String One_POJOIdentification,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			String Many_POJOIdentification);;

	

}
