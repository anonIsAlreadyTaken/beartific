package org.wuxb.beartific.batisUtils.SQL.simplePOJOSQL;

import java.util.Map;

import org.dom4j.Element;

public interface SimplePOJOSQLCreater{



	/**
	 *
	 * @function 创建基础公用SQL语句
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 */
	public void createBaseColumnSQL(Element rootElement,String tableName,

			Map<String, Map<String, String>> collectionOfTableInfo);
	/**
	 * @serialNo 3.执行序列3.2
	 * @function 创建简单分页SQL语句
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 * @param cls 类对象
	 */
	public void createSimpleSelectByPageSQL(Element rootElement,String tableName,Class<?> cls,

			String RESULTMAPPER_ID_NAME,Map<String, Map<String, String>> collectionOfSqlInfo,String identification);;



	/**
	 *
	 * @function 全列表查询
	 * @author 吴相博
	 * @time 2016-2-22 下午3:51:12
	 * @param rootElement 根元素
	 * @param tableName 表名
	 */
			public void createSimpleSelectList(Element rootElement,String tableName,Class<?> cls,

					String RESULTMAPPER_ID_NAME,Map<String, Map<String, String>> collectionOfSqlInfo,String identification);;


	/**
	 *
	 * @function 查询列表数据条数
	 * @author 吴相博
	 * @time 2016-2-22 下午3:51:12
	 * @param rootElement 根元素
	 * @param tableName 表名
	 */
	public void createSelectCountSQL(Element rootElement,String tableName,Map<String, Map<String, String>> collectionOfSqlInfo);
	/**
	 *
	 * @function 创建查找对象SQL
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 * @param cls 类对象
	 * @param identification 该类对象的标识属性
	 */
	public void createSimpleSelectByIdSQL(Element rootElement,String tableName,Class<?> cls,String identification,

			String RESULTMAPPER_ID_NAME,String idType,Map<String, Map<String, String>> collectionOfSqlInfo,

			Map<String, String> collectionOfJDBCAndJAVATypeConvert);;
	/**
	 *
	 * @function 创建删除SQL
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 * @param identification 该类对象的标识属性
	 */
	public void createSimpleDeleteByIdSQL(Element rootElement,String tableName,String identification,String idType,

			Map<String, Map<String, String>> collectionOfSqlInfo,Map<String, String> collectionOfJDBCAndJAVATypeConvert);
	/**
	 *
	 * @function 创建增加对象SQL
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 * @param cls 类对象
	 * @param identification 该类对象的标识属性
	 */
	public void createSimpleInsertSQL(Element rootElement,String tableName,Class<?> cls,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, Map<String, String>> collectionOfSqlInfo);
	/**
	 *
	 * @function 创建更新对象SQL
	 * @author 吴相博
	 * @time 2016-2-21 下午6:48:36
	 * @param rootElement 根元素
	 * @param tableName 表名
	 * @param cls 类对象
	 * @param identification 该类对象的标识属性
	 */
	public void createSimpleUpdateSelectiveSQL(Element rootElement,String tableName,Class<?> cls,String identification,String idType,

			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);

}
