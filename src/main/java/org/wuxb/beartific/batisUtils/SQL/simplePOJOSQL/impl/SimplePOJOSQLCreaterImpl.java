package org.wuxb.beartific.batisUtils.SQL.simplePOJOSQL.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQL.simplePOJOSQL.SimplePOJOSQLCreater;

public class SimplePOJOSQLCreaterImpl implements SimplePOJOSQLCreater{
	
	

	/**
	 * 
	 * @function ��������SQL���
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 */
	public void createBaseColumnSQL(Element rootElement,String tableName,
			
			Map<String, Map<String, String>> collectionOfTableInfo){

		
		 rootElement.addComment("Simple:Base SQL");
		 Element sql_BaseColumn_List = rootElement.addElement("sql");
	     sql_BaseColumn_List.addAttribute("id", "Base_Column_List");
	     StringBuffer ListTextBuffer = new StringBuffer();
	     
	     Set<String> ColumnName = collectionOfTableInfo.get(tableName).keySet();
	     for (String column : ColumnName) {
	    	 
	    	 if(!column.equals("tableName")){
	    		 
	    		 ListTextBuffer.append(",").append(column);
	    		 
	    	 }
			
		}
	     String listText = ListTextBuffer.substring(1);
	     sql_BaseColumn_List.addText(listText);
		
	}
	
	/**
	 * @serialNo 3.ִ������3.2
	 * @function �������б�SQL���
	 * @author ���಩
	 * @time 2016-2-21 ����6:48:36
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 * @param cls �����
	 */
	public void createSimpleSelectList(Element rootElement,String tableName,Class<?> cls,
			
			String RESULTMAPPER_ID_NAME,Map<String, Map<String, String>> collectionOfSqlInfo,String identification){
		
		 rootElement.addComment("Simple:Get getSimpleList for show");
			
		 Element selectByPageNoEle = rootElement.addElement("select");
	     String selectByPageSql = "getSimpleList";
	     selectByPageNoEle.addAttribute("id", selectByPageSql);
	     selectByPageNoEle.addAttribute("resultMap", cls.getSimpleName()+RESULTMAPPER_ID_NAME);
	     HashMap<String, String> selectSqlInfo = new HashMap<String, String>();
	     selectSqlInfo.put("args", "");
	     selectSqlInfo.put("return", "List<"+cls.getSimpleName()+">");
	     
	     collectionOfSqlInfo.put("getSimpleList", selectSqlInfo);
	     
	     StringBuffer sql_SelectByPageBuffer = new StringBuffer();
	     sql_SelectByPageBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+tableName)
	     .append(" ORDER BY "+tableName+"."+identification+" ASC");
	     
	     selectByPageNoEle.addText(sql_SelectByPageBuffer.toString());
		
	}
	
	
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
			
			String RESULTMAPPER_ID_NAME,Map<String, Map<String, String>> collectionOfSqlInfo,String identification){
		
		 rootElement.addComment("Simple:Select By Page List");
		
		 Element selectByPageNoEle = rootElement.addElement("select");
	     String selectByPageSql = "selectByPageNo";
	     selectByPageNoEle.addAttribute("id", selectByPageSql);
	     selectByPageNoEle.addAttribute("resultMap", cls.getSimpleName()+RESULTMAPPER_ID_NAME);
	     selectByPageNoEle.addAttribute("parameterType", "java.util.Map");
	     HashMap<String, String> selectSqlInfo = new HashMap<String, String>();
	     selectSqlInfo.put("args", "Map<String, Object> map");
	     selectSqlInfo.put("return", "List<"+cls.getSimpleName()+">");
	     
	     collectionOfSqlInfo.put("selectByPageNo", selectSqlInfo);
	     
	     StringBuffer sql_SelectByPageBuffer = new StringBuffer();
	     sql_SelectByPageBuffer.append("\n\tSELECT ");
	     
	     selectByPageNoEle.addText(sql_SelectByPageBuffer.toString());
	     Element includeEle = selectByPageNoEle.addElement("include");
	     includeEle.addAttribute("refid", "Base_Column_List");
	     sql_SelectByPageBuffer = new StringBuffer();
	     sql_SelectByPageBuffer
	     .append("\n\tFROM "+tableName)
	     .append(" ORDER BY "+tableName+"."+identification+" ASC")
	     .append(" LIMIT")
	     .append(" #{pageNo},#{pageSize}");
	     selectByPageNoEle.addText(sql_SelectByPageBuffer.toString());
		
	};
	/**
	 * 
	 * @function ��ѯ�б��������
	 * @author ���಩
	 * @time 2016-2-22 ����3:51:12
	 * @param rootElement ��Ԫ��
	 * @param tableName ����
	 */
	public void createSelectCountSQL(Element rootElement,String tableName,Map<String, Map<String, String>> collectionOfSqlInfo){
		
		
		
		 rootElement.addComment("Simple:Get sum of table rows");
		
		 Element selectCountEle = rootElement.addElement("select");
	     String selectCountSql = "selectCount";
	     selectCountEle.addAttribute("id", selectCountSql);
	     selectCountEle.addAttribute("resultType", "java.lang.Integer");
	     HashMap<String, String> selectCountInfo = new HashMap<String, String>();
	     selectCountInfo.put("args", "");
	     selectCountInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("selectCount", selectCountInfo);
	     
	     StringBuffer sql_selectCountBuffer = new StringBuffer();
	     sql_selectCountBuffer.append("\n\tSELECT COUNT(*) ")
	     .append("FROM "+tableName+"\n\t");
	     selectCountEle.addText(sql_selectCountBuffer.toString());
		
	}
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
			
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){

		
		rootElement.addComment("Simple:Get object by ID");
		
		 Element selectByPrimaryKeyEle = rootElement.addElement("select");
	     String selectByPrimaryKeySql = "selectByPrimaryKey";
	     selectByPrimaryKeyEle.addAttribute("id", selectByPrimaryKeySql);
	     selectByPrimaryKeyEle.addAttribute("resultMap", cls.getSimpleName()+RESULTMAPPER_ID_NAME);
	     selectByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> selectByPrimaryKeySqlInfo = new HashMap<String, String>();
	     selectByPrimaryKeySqlInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+identification);
	     selectByPrimaryKeySqlInfo.put("return", cls.getSimpleName());
	     
	     collectionOfSqlInfo.put("selectByPrimaryKey", selectByPrimaryKeySqlInfo);
	     
	     StringBuffer sql_selectByPrimaryKeyBuffer = new StringBuffer();
	     sql_selectByPrimaryKeyBuffer.append("\n\tSELECT ");
	     
	     selectByPrimaryKeyEle.addText(sql_selectByPrimaryKeyBuffer.toString());
	     Element includeEleOfselectByPrimaryKey = selectByPrimaryKeyEle.addElement("include");
	     includeEleOfselectByPrimaryKey.addAttribute("refid", "Base_Column_List");
	     sql_selectByPrimaryKeyBuffer = new StringBuffer();
	     sql_selectByPrimaryKeyBuffer
	     .append(" \n\tFROM "+tableName).append(" WHERE ")
	     .append(identification).append(" = ").append("#{"+identification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}");
	     
	     
	    
	     selectByPrimaryKeyEle.addText(sql_selectByPrimaryKeyBuffer.toString());
		
	};
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
			
			Map<String, Map<String, String>> collectionOfSqlInfo,Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		rootElement.addComment("Simple:Delete Object by ID");
		
		 Element deleteByPrimaryKeyEle = rootElement.addElement("delete");
	     String deleteByPrimaryKeySql = "deleteByPrimaryKey";
	     deleteByPrimaryKeyEle.addAttribute("id", deleteByPrimaryKeySql);
	     deleteByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> deleteByPrimaryKeySqlInfo = new HashMap<String, String>();
	     deleteByPrimaryKeySqlInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+identification);
	     deleteByPrimaryKeySqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("deleteByPrimaryKey", deleteByPrimaryKeySqlInfo);
	     
	     StringBuffer sql_deleteByPrimaryKeyBuffer = new StringBuffer();
	     sql_deleteByPrimaryKeyBuffer
	     .append("\n\tDELETE")
	     .append(" FROM "+tableName).append("\n\tWHERE ")
	     .append(identification).append(" = ").append("#{"+identification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}\n\t");
	     
	     
	    
	     deleteByPrimaryKeyEle.addText(sql_deleteByPrimaryKeyBuffer.toString());
		
	}
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
			Map<String, Map<String, String>> collectionOfSqlInfo){
		
		
		rootElement.addComment("Simple:Insert Object");
		
		 Element insertSelectiveEle = rootElement.addElement("insert");
	     String insertSelectiveSql = "insertSelective";
	     insertSelectiveEle.addAttribute("id", insertSelectiveSql);
	     insertSelectiveEle.addAttribute("parameterType",cls.getName() );
	     HashMap<String, String> insertSelectiveSqlInfo = new HashMap<String, String>();
	     insertSelectiveSqlInfo.put("args", cls.getName().substring(cls.getName().lastIndexOf(".")+1)+" "+cls.getName().substring(cls.getName().lastIndexOf(".")+1).toLowerCase());
	     insertSelectiveSqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("insertSelective", insertSelectiveSqlInfo);
	     
	     StringBuffer sql_insertSelectiveBuffer = new StringBuffer();
	     sql_insertSelectiveBuffer.append("\n\tINSERT ")
	     .append("INTO").append(" "+tableName);
	     
	     insertSelectiveEle.addText(sql_insertSelectiveBuffer.toString());
	     Element trimEleOfinsertSelective = insertSelectiveEle.addElement("trim");
	     trimEleOfinsertSelective.addAttribute("prefix", "(");
	     trimEleOfinsertSelective.addAttribute("suffix", ")");
	     trimEleOfinsertSelective.addAttribute("suffixOverrides", ",");
	     
	     Map<String, String> tableColumn = collectionOfTableInfo.get(tableName);
	     Set<String> columnNames = tableColumn.keySet();
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertSelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+",\n\t");
	    	 
	     }
	     
	     
	     Element trimEleOfinsertSelective_values = insertSelectiveEle.addElement("trim");
	     trimEleOfinsertSelective_values.addAttribute("prefix", "values (");
	     trimEleOfinsertSelective_values.addAttribute("suffix", ")");
	     trimEleOfinsertSelective_values.addAttribute("suffixOverrides", ",");
	     
	     
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertSelective_values.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+"#{"+columnName+",jdbcType="+tableColumn.get(columnName)+"},"+"\n\t");
	     }
	     
		
	}
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
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
	
		
		 rootElement.addComment("Simple:Update Object by ID");
		
		 Element updateByPrimaryKeySelectiveEle = rootElement.addElement("update");
	     String updateByPrimaryKeySelectiveSql = "updateByPrimaryKeySelective";
	     updateByPrimaryKeySelectiveEle.addAttribute("id", updateByPrimaryKeySelectiveSql);
	     updateByPrimaryKeySelectiveEle.addAttribute("parameterType",cls.getName() );
	     HashMap<String, String> updateByPrimaryKeySelectiveSqlInfo = new HashMap<String, String>();
	     updateByPrimaryKeySelectiveSqlInfo.put("args", cls.getName().substring(cls.getName().lastIndexOf(".")+1)+" "+cls.getName().substring(cls.getName().lastIndexOf(".")+1).toLowerCase());
	     updateByPrimaryKeySelectiveSqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("updateByPrimaryKeySelective", updateByPrimaryKeySelectiveSqlInfo);
	     
	     StringBuffer sql_updateByPrimaryKeySelectiveBuffer = new StringBuffer();
	     sql_updateByPrimaryKeySelectiveBuffer.append("\n\tUPDATE ")
	     .append(" "+tableName);
	     
	     updateByPrimaryKeySelectiveEle.addText(sql_updateByPrimaryKeySelectiveBuffer.toString());
	     Element setEleOfupdateByPrimaryKeySelective = updateByPrimaryKeySelectiveEle.addElement("set");
	     
	     Map<String, String> tableColumn = collectionOfTableInfo.get(tableName);
	     Set<String> columnNames = tableColumn.keySet();
	    
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = setEleOfupdateByPrimaryKeySelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+" = #{"+columnName+",jdbcType="+tableColumn.get(columnName)+"},"+"\n\t");
	    	 
	     }
	     
	     sql_updateByPrimaryKeySelectiveBuffer = new StringBuffer();
	     sql_updateByPrimaryKeySelectiveBuffer
	     .append("\n\t\tWHERE ")
	     .append(identification).append(" = ").append("#{"+identification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}");
	     
	     updateByPrimaryKeySelectiveEle.addText(sql_updateByPrimaryKeySelectiveBuffer.toString());
	     
		
	
	}

}
