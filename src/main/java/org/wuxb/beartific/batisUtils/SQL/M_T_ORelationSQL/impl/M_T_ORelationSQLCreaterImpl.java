package org.wuxb.beartific.batisUtils.SQL.M_T_ORelationSQL.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQL.M_T_ORelationSQL.M_T_ORelationSQLCreater;

public class M_T_ORelationSQLCreaterImpl implements M_T_ORelationSQLCreater{
	
	
	/**
	 * -----------------------------------ONE TO MANY----------------------------------------------------------
	 */
	
	/**
	 * 
	 * @function һ�Զ��б��ѯ
	 * @author ���಩
	 * @time 2016-2-23 ����9:36:47
	 */
	public void createOneToManySelectListSQL(Element rootElement,Class<?> One_POJO,String One_POJO_tableName,String Many_POJO_tableName,
			String One_POJOIdentification,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Class<?> Many_POJO,
			String idType,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		 rootElement.addComment("One_To_Many:Get list for Show");
		 Element selectO_T_MListEle = rootElement.addElement("select");
	     String selectO_T_MListSql = "selectO_T_MList";
	     selectO_T_MListEle.addAttribute("id", selectO_T_MListSql);
	     selectO_T_MListEle.addAttribute("resultMap", One_POJO.getSimpleName()+RESULTMAPPER_ID_NAME);
	     HashMap<String, String> selectO_T_MListInfo = new HashMap<String, String>();
	     selectO_T_MListInfo.put("args", "");
	     selectO_T_MListInfo.put("return", "List<"+One_POJO.getSimpleName()+">");
	     
	     collectionOfSqlInfo.put("selectO_T_MList", selectO_T_MListInfo);
	     
	     StringBuffer sql_selectO_T_MListBuffer = new StringBuffer();
	     sql_selectO_T_MListBuffer.append("\n\tSELECT *")
	     .append(" FROM "+One_POJO_tableName);
	     
	     selectO_T_MListEle.addText(sql_selectO_T_MListBuffer.toString());
	     
	     rootElement.addComment("One_To_Many:Component Method of get show list");
	     Element selectO_T_MComponentEle = rootElement.addElement("select");
	     //String selectM_T_MComponentSql = "selectM_T_MListByPage";
	     selectO_T_MComponentEle.addAttribute("id", "select"+Many_POJO.getSimpleName()+"By"+One_POJOIdentification);
	     selectO_T_MComponentEle.addAttribute("parameterType", idType);
	     selectO_T_MComponentEle.addAttribute("resultType", Many_POJO.getName());
	     
	     /*
	      * ��ҳ�б�Ƕ��SQL
	      *
	      * <select id="selectRoleBySid" parameterType="int" resultType="org.wuxb.testMTMEntity.Role">
			SELECT * FROM ssh_role WHERE sid = #{sid}
			</select>
	      * 
	      */
	     
	     StringBuffer sql_selectO_T_MComponentBuffer = new StringBuffer();
	     sql_selectO_T_MComponentBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+Many_POJO_tableName)
	     .append("\n\tWHERE ").append(Many_POJO_tableName+"."+One_POJOIdentification)
	     
	     .append(" = ")
	     .append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}")
	     .append("\n\t");
	     selectO_T_MComponentEle.addText(sql_selectO_T_MComponentBuffer.toString());
		
	     
		
	}
	
	/**
	 * 
	 * @function һ�Զ���ID��ѯ
	 * @author ���಩
	 * @time 2016-2-23 ����9:48:21
	 */
	public void createOneToManySelectByIdSQL(Element rootElement,Class<?> One_POJO,String One_POJO_tableName,String Many_POJO_tableName,
			String One_POJOIdentification,
			String RESULTMAPPER_ID_NAME,
			String idType,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		 rootElement.addComment("One_To_Many:Get Object By ID");
		 Element selectO_T_MByIdEle = rootElement.addElement("select");
	     String selectO_T_MByIdSql = "selectO_T_MById";
	     selectO_T_MByIdEle.addAttribute("id", selectO_T_MByIdSql);
	     selectO_T_MByIdEle.addAttribute("resultMap", One_POJO.getSimpleName()+RESULTMAPPER_ID_NAME);
	     HashMap<String, String> selectO_T_MByIdInfo = new HashMap<String, String>();
	     selectO_T_MByIdInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+One_POJOIdentification);
	     selectO_T_MByIdInfo.put("return", One_POJO.getSimpleName());
	     
	     collectionOfSqlInfo.put("selectO_T_MById", selectO_T_MByIdInfo);
	     
	     StringBuffer sql_selectO_T_MByIdBuffer = new StringBuffer();
	     sql_selectO_T_MByIdBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+One_POJO_tableName)
	     .append("\n\tWHERE ").append(One_POJO_tableName+"."+One_POJOIdentification)
	     .append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}")
	     .append("\n\t");
	     
	     
	     selectO_T_MByIdEle.addText(sql_selectO_T_MByIdBuffer.toString());
	     
		
	}
	
	
	/**
	 * 
	 * @function һ�Զ�ɾ��
	 * @author ���಩
	 * @time 2016-2-24 ����6:42:21
	 */
	public void createOneToManyDeleteSQL(Element rootElement,String One_POJO_tableName,String One_POJOIdentification,
			String Many_POJO_tableName,
			String idType,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert,
			Map<String, Map<String, String>> collectionOfSqlInfo){
		
		rootElement.addComment("One_To_Many:Delete Object By ID");
		Element deleteO_T_MByPrimaryKeyEle = rootElement.addElement("delete");
	     
	    
	    
	     
	     /*
	      * ɾ��һ���������
	      */
		 
		
	     String deleteO_T_MByPrimaryKeySql = "deleteO_T_MByPrimaryKey";
	     deleteO_T_MByPrimaryKeyEle.addAttribute("id", deleteO_T_MByPrimaryKeySql);
	     deleteO_T_MByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> deleteO_T_MByPrimaryKeySqlInfo = new HashMap<String, String>();
	     deleteO_T_MByPrimaryKeySqlInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+One_POJOIdentification);
	     deleteO_T_MByPrimaryKeySqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("deleteO_T_MByPrimaryKey", deleteO_T_MByPrimaryKeySqlInfo);
	     
	     StringBuffer sql_deleteO_T_MByPrimaryKeyBuffer = new StringBuffer();
	     sql_deleteO_T_MByPrimaryKeyBuffer
	     .append("\n\tDELETE")
	     .append(" FROM "+One_POJO_tableName).append("\n\tWHERE ")
	     .append(One_POJOIdentification).append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}\n\t");
	     
	     /*
	      * �޸Ķ෽�������
	      */
	     StringBuffer sql_updateO_T_MRelationKeyBuffer = new StringBuffer();
	     sql_updateO_T_MRelationKeyBuffer
	     .append("\n\tUPDATE ").append(Many_POJO_tableName)
	     .append(" SET ").append(One_POJOIdentification)
	     .append(" = NULL WHERE ").append(One_POJOIdentification)
	     .append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     deleteO_T_MByPrimaryKeyEle.addText(sql_updateO_T_MRelationKeyBuffer.toString());
	     deleteO_T_MByPrimaryKeyEle.addText(sql_deleteO_T_MByPrimaryKeyBuffer.toString());
		
	};
	
	/**
	 * 
	 * @function һ�Զ�����޸�
	 * @author ���಩
	 * @time 2016-2-24 ����8:05:32
	 */
	public void createOneToManyUpdateWithRelationSQL(Element rootElement,Class<?> One_POJO,String Many_POJO_tableName,String One_POJOIdentification,
			String One_POJO_tableName,String Many_POJOIdentification,String O_T_M_Field,
			String idType,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo){
		
		 rootElement.addComment("One_To_Many:Update Object");
		
		 Element updateWithRelationShipByPrimaryKeyEle = rootElement.addElement("update");
	     String updateWithRelationShipByPrimaryKeySql = "updateWithRelationShipByPrimaryKey";
	     updateWithRelationShipByPrimaryKeyEle.addAttribute("id", updateWithRelationShipByPrimaryKeySql);
	     updateWithRelationShipByPrimaryKeyEle.addAttribute("parameterType",One_POJO.getName() );
	     HashMap<String, String> updateWithRelationShipByPrimaryKeySqlInfo = new HashMap<String, String>();
	     updateWithRelationShipByPrimaryKeySqlInfo.put("args", One_POJO.getName().substring(One_POJO.getName().lastIndexOf(".")+1)+" "+One_POJO.getName().substring(One_POJO.getName().lastIndexOf(".")+1).toLowerCase());
	     updateWithRelationShipByPrimaryKeySqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("updateWithRelationShipByPrimaryKey", updateWithRelationShipByPrimaryKeySqlInfo);
	     
	     
	     
	     /*
	      * 1.�޸Ķ෽�������ΪNULL
	      */
	     StringBuffer sql_updateO_T_MEmptyRelationKeyBuffer = new StringBuffer();
	     sql_updateO_T_MEmptyRelationKeyBuffer
	     .append("\n\tUPDATE ").append(Many_POJO_tableName)
	     .append(" SET ").append(One_POJOIdentification)
	     .append(" = NULL WHERE ").append(One_POJOIdentification)
	     .append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     
	     
	     
	     
	     //1
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateO_T_MEmptyRelationKeyBuffer.toString());
	     
	     
	     /*
	      * ������еĹ�ϵ
	      */
	     Element isListNullEle = updateWithRelationShipByPrimaryKeyEle.addElement("if");
	     
	     StringBuffer sql_updateO_T_MRelationKeyBuffer = new StringBuffer();
	     sql_updateO_T_MRelationKeyBuffer
	     .append("\n\tUPDATE ").append(Many_POJO_tableName)
	     .append(" SET ").append(One_POJOIdentification)
	     .append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"} ")
	     .append(" WHERE ").append(Many_POJOIdentification).append(" IN");
	     
	     isListNullEle.addText(sql_updateO_T_MRelationKeyBuffer.toString());
	    
	     
	    
	     isListNullEle.addAttribute("test", O_T_M_Field+" != null");
	     Element foreachEle = isListNullEle.addElement("foreach");
	     foreachEle.addAttribute("collection", O_T_M_Field);
	     foreachEle.addAttribute("item", "obj");
	     foreachEle.addAttribute("open", "(");
	     foreachEle.addAttribute("close", ");");
	     foreachEle.addAttribute("separator", ",");
	     foreachEle.addText("\n\t#{obj."+Many_POJOIdentification+"}\n\t");
	   
	     
	     
	     
	     
	     
	     StringBuffer sql_updateWithRelationShipByPrimaryKeyBuffer = new StringBuffer();
	     sql_updateWithRelationShipByPrimaryKeyBuffer.append("\n\tUPDATE ")
	     .append(" "+One_POJO_tableName);
	     
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateWithRelationShipByPrimaryKeyBuffer.toString());
	     Element setEleOfupdateByPrimaryKeySelective = updateWithRelationShipByPrimaryKeyEle.addElement("set");
	     
	     Map<String, String> tableColumn = collectionOfTableInfo.get(One_POJO_tableName);
	     Set<String> columnNames = tableColumn.keySet();
	    
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = setEleOfupdateByPrimaryKeySelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+" = #{"+columnName+",jdbcType="+tableColumn.get(columnName)+"},"+"\n\t");
	    	 
	     }
	     
	     sql_updateWithRelationShipByPrimaryKeyBuffer = new StringBuffer();
	     sql_updateWithRelationShipByPrimaryKeyBuffer
	     .append("\n\t\tWHERE ")
	     .append(One_POJOIdentification).append(" = ").append("#{"+One_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}");
	     
	     
	    
	     
	     /*
	      * ---------------------------------------
	      */
	    
	     //3
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateWithRelationShipByPrimaryKeyBuffer.toString());
	     
	     
		
	};
	/**
	 * -----------------------------------MANY TO ONE-----------------------------------------------
	 */
	
	/**
	 * 
	 * @function ���һ�޸�Sql
	 * @author ���಩
	 * @time 2016-2-22 ����8:06:04
	 */
	public void createManyToOneUpdateSQL(Element rootElement,Class<?> Many_POJO,String Many_POJO_tableName,
			String One_POJO_tableName,String M_T_O_Field,String One_POJOIdentification,String Many_POJOIdentification,
			String idType,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		 rootElement.addComment("Many_To_One:Update Object");
		
		 Element updateM_T_OByPrimaryKeySelectiveEle = rootElement.addElement("update");
	     String updateM_T_OByPrimaryKeySelectiveSql = "updateM_T_OByPrimaryKeySelective";
	     updateM_T_OByPrimaryKeySelectiveEle.addAttribute("id", updateM_T_OByPrimaryKeySelectiveSql);
	     updateM_T_OByPrimaryKeySelectiveEle.addAttribute("parameterType",Many_POJO.getName() );
	     HashMap<String, String> updateM_T_OByPrimaryKeySelectiveSqlInfo = new HashMap<String, String>();
	     updateM_T_OByPrimaryKeySelectiveSqlInfo.put("args", Many_POJO.getName().substring(Many_POJO.getName().lastIndexOf(".")+1)+" "+Many_POJO.getName().substring(Many_POJO.getName().lastIndexOf(".")+1).toLowerCase());
	     updateM_T_OByPrimaryKeySelectiveSqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("updateM_T_OByPrimaryKeySelective", updateM_T_OByPrimaryKeySelectiveSqlInfo);
	     
	     StringBuffer sql_updateM_T_OByPrimaryKeySelectiveBuffer = new StringBuffer();
	     sql_updateM_T_OByPrimaryKeySelectiveBuffer.append("\n\tUPDATE ")
	     .append(" "+Many_POJO_tableName);
	     
	     
	     updateM_T_OByPrimaryKeySelectiveEle.addText(sql_updateM_T_OByPrimaryKeySelectiveBuffer.toString());
	     Element setEleOfupdateM_T_OByPrimaryKeySelective = updateM_T_OByPrimaryKeySelectiveEle.addElement("set");
	     
	     Map<String, String> tableColumn_MANY = collectionOfTableInfo.get(Many_POJO_tableName);
	     Map<String, String> tableColumn_ONE = collectionOfTableInfo.get(One_POJO_tableName);
	     Set<String> columnNames = tableColumn_MANY.keySet();
	    
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = setEleOfupdateM_T_OByPrimaryKeySelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+" = #{"+columnName+",jdbcType="+tableColumn_MANY.get(columnName)+"},"+"\n\t");
	    	 
	     }
	     
	     Element Relation_M_T_O_ColumnEle_CLASS = setEleOfupdateM_T_OByPrimaryKeySelective.addElement("if");
	     Relation_M_T_O_ColumnEle_CLASS.addAttribute("test", M_T_O_Field+" != null");
	     
	     Element Relation_M_T_O_ColumnEle_Field = Relation_M_T_O_ColumnEle_CLASS.addElement("if");
	     Relation_M_T_O_ColumnEle_Field.addAttribute("test", M_T_O_Field+"."+One_POJOIdentification+" != null");
	     Relation_M_T_O_ColumnEle_Field.addText("\n\t\t"+One_POJOIdentification+" = #{"+M_T_O_Field+"."+One_POJOIdentification+",jdbcType="+tableColumn_ONE.get(One_POJOIdentification)+"},"+"\n\t");
   	 
	     
	     
	     sql_updateM_T_OByPrimaryKeySelectiveBuffer = new StringBuffer();
	     sql_updateM_T_OByPrimaryKeySelectiveBuffer
	     .append("\n\t\tWHERE ")
	     .append(Many_POJOIdentification).append(" = ").append("#{"+Many_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}");
	     
	     updateM_T_OByPrimaryKeySelectiveEle.addText(sql_updateM_T_OByPrimaryKeySelectiveBuffer.toString());
	     
		
	}
	/**
	 * 
	 * @function �������һ���ID��ѯ
	 * @author ���಩
	 * @time 2016-2-22 ����7:22:42
	 */
	public void createManyToOneSelectById(Element rootElement,Class<?> Many_POJO,String Many_POJO_tableName,String Many_POJOIdentification,
			String idType,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		 rootElement.addComment("Many_To_One:Select Object by ID");
		 
		 Element selectM_T_OByPrimaryKeyEle = rootElement.addElement("select");
	     String selectM_T_OByPrimaryKeySql = "selectM_T_OByPrimaryKey";
	     selectM_T_OByPrimaryKeyEle.addAttribute("id", selectM_T_OByPrimaryKeySql);
	     selectM_T_OByPrimaryKeyEle.addAttribute("resultMap", Many_POJO.getSimpleName()+RESULTMAPPER_ID_NAME);
	     selectM_T_OByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> selectM_T_OByPrimaryKeySqlInfo = new HashMap<String, String>();
	     selectM_T_OByPrimaryKeySqlInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+Many_POJOIdentification);
	     selectM_T_OByPrimaryKeySqlInfo.put("return", Many_POJO.getSimpleName());
	     
	     collectionOfSqlInfo.put("selectM_T_OByPrimaryKey", selectM_T_OByPrimaryKeySqlInfo);
	     
	     StringBuffer sql_selectM_T_OByPrimaryKeyBuffer = new StringBuffer();
	     sql_selectM_T_OByPrimaryKeyBuffer.append("\n\tSELECT ");
	     
	     selectM_T_OByPrimaryKeyEle.addText(sql_selectM_T_OByPrimaryKeyBuffer.toString());
	     Element includeEleOfselectM_T_OByPrimaryKey = selectM_T_OByPrimaryKeyEle.addElement("include");
	     includeEleOfselectM_T_OByPrimaryKey.addAttribute("refid", "Base_Column_List");
	     sql_selectM_T_OByPrimaryKeyBuffer = new StringBuffer();
	     sql_selectM_T_OByPrimaryKeyBuffer
	     .append(" \n\tFROM "+Many_POJO_tableName).append(" WHERE ")
	     .append(Many_POJOIdentification).append(" = ").append("#{"+Many_POJOIdentification+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}");
	     
	     
	    
	     selectM_T_OByPrimaryKeyEle.addText(sql_selectM_T_OByPrimaryKeyBuffer.toString());
		
	}
	/**
	 * 
	 * @function �������һ���
	 * @author ���಩
	 * @time 2016-2-22 ����7:22:27
	 */
	public void createManyToOneInsertSQL(Element rootElement,Class<?> Many_POJO,
			String Many_POJO_tableName,String One_POJO_tableName,String M_T_O_Field,
			String One_POJOIdentification,
			String idType,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		rootElement.addComment("Many_To_One:Insert Object");
		
		 Element insertM_T_OSelectiveEle = rootElement.addElement("insert");
	     String insertM_T_OSelectiveSql = "insertM_T_OSelective";
	     insertM_T_OSelectiveEle.addAttribute("id", insertM_T_OSelectiveSql);
	     insertM_T_OSelectiveEle.addAttribute("parameterType",Many_POJO.getName() );
	     HashMap<String, String> insertM_T_OSelectiveSqlInfo = new HashMap<String, String>();
	     insertM_T_OSelectiveSqlInfo.put("args", Many_POJO.getName().substring(Many_POJO.getName().lastIndexOf(".")+1)+" "+Many_POJO.getName().substring(Many_POJO.getName().lastIndexOf(".")+1).toLowerCase());
	     insertM_T_OSelectiveSqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("insertM_T_OSelective", insertM_T_OSelectiveSqlInfo);
	     
	     StringBuffer sql_insertM_T_OSelectiveBuffer = new StringBuffer();
	     sql_insertM_T_OSelectiveBuffer.append("\n\tINSERT ")
	     .append("INTO").append(" "+Many_POJO_tableName);
	     
	     insertM_T_OSelectiveEle.addText(sql_insertM_T_OSelectiveBuffer.toString());
	     Element trimEleOfinsertM_T_OSelective = insertM_T_OSelectiveEle.addElement("trim");
	     trimEleOfinsertM_T_OSelective.addAttribute("prefix", "(");
	     trimEleOfinsertM_T_OSelective.addAttribute("suffix", ")");
	     trimEleOfinsertM_T_OSelective.addAttribute("suffixOverrides", ",");
	     
	     Map<String, String> tableColumn_MANY = collectionOfTableInfo.get(Many_POJO_tableName);
	     Map<String, String> tableColumn_ONE = collectionOfTableInfo.get(One_POJO_tableName);
	     Set<String> columnNames = tableColumn_MANY.keySet();
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertM_T_OSelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+",\n\t");
	    	 
	     }
	     
	     Element relation_Column_MTO_Class = trimEleOfinsertM_T_OSelective.addElement("if");
	     relation_Column_MTO_Class.addAttribute("test", M_T_O_Field+" != null");
	     
	     
	     
	     Element relation_Column_MTO_Field = relation_Column_MTO_Class.addElement("if");
	     relation_Column_MTO_Field.addAttribute("test", M_T_O_Field+"."+One_POJOIdentification+" != null");
	     relation_Column_MTO_Field.addText("\n\t\t"+One_POJOIdentification+",\n\t");
	     
	     Element trimEleOfinsertM_T_OSelective_values = insertM_T_OSelectiveEle.addElement("trim");
	     trimEleOfinsertM_T_OSelective_values.addAttribute("prefix", "values (");
	     trimEleOfinsertM_T_OSelective_values.addAttribute("suffix", ")");
	     trimEleOfinsertM_T_OSelective_values.addAttribute("suffixOverrides", ",");
	     
	     
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertM_T_OSelective_values.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+"#{"+columnName+",jdbcType="+tableColumn_MANY.get(columnName)+"},"+"\n\t");
	     }
	     
	     
	     Element values_Column_MTO_CLASS = trimEleOfinsertM_T_OSelective_values.addElement("if");
	     values_Column_MTO_CLASS.addAttribute("test", M_T_O_Field+" != null");
	     
	     
	     
	     Element values_Column_MTO_FIELD = values_Column_MTO_CLASS.addElement("if");
	     values_Column_MTO_FIELD.addAttribute("test", M_T_O_Field+"."+One_POJOIdentification+" != null");
	     values_Column_MTO_FIELD.addText("\n\t\t"+"#{"+M_T_O_Field+"."+One_POJOIdentification+",jdbcType="+tableColumn_ONE.get(One_POJOIdentification)+"},"+"\n\t");
	     
	     
		
	}
	/**
	 * 
	 * @function ���һ��SQL
	 * @author ���಩
	 * @time 2016-2-22 ����6:06:11
	 */
	public void manyToOneBaseColumnSQL(Element rootElement,String Many_POJO_tableName,String One_POJOIdentification,
			String idType,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		
		rootElement.addComment("Many_To_One:Base SQL");
		
		 Element sql_BaseColumn_List = rootElement.addElement("sql");
	     sql_BaseColumn_List.addAttribute("id", "Base_Column_List");
	     StringBuffer ListTextBuffer = new StringBuffer();
	     
	     Set<String> ColumnName = collectionOfTableInfo.get(Many_POJO_tableName).keySet();
	     for (String column : ColumnName) {
	    	 
	    	 if(!column.equals("tableName")){
	    		 
	    		 ListTextBuffer.append(",").append(column);
	    		 
	    	 }
			
		}
	     ListTextBuffer.append(",").append(One_POJOIdentification);
	    
	     
	     String listText = ListTextBuffer.substring(1);
	     sql_BaseColumn_List.addText(listText);
		
	};
	/**
	 * 
	 * @function ���һ��ҳSQL
	 * @author ���಩
	 * @time 2016-2-22 ����6:10:14
	 */
	public void manyToOneSelectByPageSQL(Element rootElement,Class<?> Many_POJO,String Many_POJO_tableName,
			String One_POJO_tableName,String One_POJOIdentification,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			String Many_POJOIdentification){
		
		
		rootElement.addComment("Many_To_One:Select by Page List");
		
		 Element selectM_T_OByPageNoEle = rootElement.addElement("select");
	     String selectM_T_OByPageNoSql = "selectM_T_OByPageNo";
	     selectM_T_OByPageNoEle.addAttribute("id", selectM_T_OByPageNoSql);
	     selectM_T_OByPageNoEle.addAttribute("resultMap", Many_POJO.getSimpleName()+RESULTMAPPER_ID_NAME);
	     selectM_T_OByPageNoEle.addAttribute("parameterType", "java.util.Map");
	     HashMap<String, String> selectM_T_OByPageNoInfo = new HashMap<String, String>();
	     selectM_T_OByPageNoInfo.put("args", "Map<String, Object> map");
	     selectM_T_OByPageNoInfo.put("return", "List<"+Many_POJO.getSimpleName()+">");
	     
	     collectionOfSqlInfo.put("selectM_T_OByPageNo", selectM_T_OByPageNoInfo);
	     
	     StringBuffer sql_selectM_T_OByPageNoBuffer = new StringBuffer();
	     sql_selectM_T_OByPageNoBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+Many_POJO_tableName+","+One_POJO_tableName)
	     .append("\n\tWHERE "+Many_POJO_tableName+"."+One_POJOIdentification)
	     .append(" = ").append(One_POJO_tableName+"."+One_POJOIdentification)
	     .append(" ORDER BY "+Many_POJO_tableName+"."+Many_POJOIdentification+" ASC")
	     .append("\n\tLIMIT")
	     .append(" #{pageNo},#{pageSize}\n\t");
	     selectM_T_OByPageNoEle.addText(sql_selectM_T_OByPageNoBuffer.toString());
		
	};

	

}
