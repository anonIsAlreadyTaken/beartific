package org.wuxb.beartific.batisUtils.SQL.simplePOJOSQL;

import java.util.Map;

import org.dom4j.Element;

public interface SimplePOJOSQLCreater{
	
	

	/**
	 * 
	 * @function ��������SQL���
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 */
	public void createBaseColumnSQL(Element rootElement,String tableName,
			
			Map<String, Map<String, String>> collectionOfTableInfo);
	/**
	 * @serialNo 3.ִ������3.2
	 * @function �����򵥷�ҳSQL���
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param cls �����
	 */
	public void createSimpleSelectByPageSQL(Element rootElement,String tableName,Class<?> cls,
			
			String RESULTMAPPER_ID_NAME,Map<String, Map<String, String>> collectionOfSqlInfo,String identification);;
			
	
			
			/**
			 * @serialNo 3.ִ������3.2
			 * @function �����򵥷�ҳSQL���
			 * @author ���಩
			 * @time 2016-2-21 ����6:48:36
			 * @param rootElement ��Ԫ��
			 * @param tableName ����
			 * @param cls �����
			 */
			public void createSimpleSelectList(Element rootElement,String tableName,Class<?> cls,
					
					String RESULTMAPPER_ID_NAME,Map<String, Map<String, String>> collectionOfSqlInfo,String identification);;
			
			
	/**
	 * 
	 * @function ��ѯ�б��������
	 * @author ���಩
	 * @time 2016-2-22 ����3:51:12
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 */
	public void createSelectCountSQL(Element rootElement,String tableName,Map<String, Map<String, String>> collectionOfSqlInfo);
	/**
	 * 
	 * @function �������Ҷ���SQL
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param cls �����
	 * @param identification �������ı�ʶ����
	 */
	public void createSimpleSelectByIdSQL(Element rootElement,String tableName,Class<?> cls,String identification,
											
			String RESULTMAPPER_ID_NAME,String idType,Map<String, Map<String, String>> collectionOfSqlInfo,
			
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);;
	/**
	 * 
	 * @function ����ɾ��SQL
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param identification �������ı�ʶ����
	 */
	public void createSimpleDeleteByIdSQL(Element rootElement,String tableName,String identification,String idType,
			
			Map<String, Map<String, String>> collectionOfSqlInfo,Map<String, String> collectionOfJDBCAndJAVATypeConvert);
	/**
	 * 
	 * @function �������Ӷ���SQL
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param cls �����
	 * @param identification �������ı�ʶ����
	 */
	public void createSimpleInsertSQL(Element rootElement,String tableName,Class<?> cls,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, Map<String, String>> collectionOfSqlInfo);
	/**
	 * 
	 * @function �������¶���SQL
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param cls �����
	 * @param identification �������ı�ʶ����
	 */
	public void createSimpleUpdateSelectiveSQL(Element rootElement,String tableName,Class<?> cls,String identification,String idType,
			
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert);

}
