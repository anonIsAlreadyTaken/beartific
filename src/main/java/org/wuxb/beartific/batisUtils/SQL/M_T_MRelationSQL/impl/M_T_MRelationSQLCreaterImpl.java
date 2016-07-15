package org.wuxb.beartific.batisUtils.SQL.M_T_MRelationSQL.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQL.M_T_MRelationSQL.M_T_MRelationSQLCreater;

public class M_T_MRelationSQLCreaterImpl implements M_T_MRelationSQLCreater{
	
	
	/**
	 * 
	 * @function ��Զ��ҳ�б�
	 * @author ���಩
	 * @time 2016-2-27 ����9:46:58
	 */
	public void createManyToManySelectListByPageSQL(Element rootElement,Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,String MappingTableName,
			String idType,
			String RESULTMAPPER_ID_NAME,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		
		   /*
	      * ��Զ��ҳ�б�
	      */
		
		 rootElement.addComment("Many_To_Many:Get list by page");
	     Element selectM_T_MListByPageEle = rootElement.addElement("select");
	     String selectM_T_MListByPageSql = "selectM_T_MListByPage";
	     selectM_T_MListByPageEle.addAttribute("id", selectM_T_MListByPageSql);
	     selectM_T_MListByPageEle.addAttribute("resultMap",ManyPOJO1.getSimpleName()+RESULTMAPPER_ID_NAME);
	     HashMap<String, String> selectM_T_MListByPageInfo = new HashMap<String, String>();
	     selectM_T_MListByPageInfo.put("args", "Map<String,Object> map");
	     selectM_T_MListByPageInfo.put("return", "List<"+ManyPOJO1.getSimpleName()+">");
	     
	     collectionOfSqlInfo.put("selectM_T_MListByPage", selectM_T_MListByPageInfo);
	     
	     /*
	      * ԭ��SQL
	      */
	     StringBuffer sql_selectM_T_MListByPageBuffer = new StringBuffer();
	     sql_selectM_T_MListByPageBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+tableName1)
	     .append(" ORDER BY "+identification1+" ASC")
	     .append("\n\tLIMIT")
	     .append(" #{pageNo},#{pageSize}\n\t");
	     selectM_T_MListByPageEle.addText(sql_selectM_T_MListByPageBuffer.toString());
		
	     
	     
	     
	  /*   <collection property="userRole" ofType="org.wuxb.testMTMEntity.Role" select="selectRoleBySid" column="sid">
	      	<id property="rid" column="rid" javaType="java.lang.Integer" jdbcType="INTEGER"/>
	      	<result column="roleName" property="roleName" javaType="java.lang.String" jdbcType="VARCHAR"/>
	    	</collection>
	  		</resultMap>
	  		<select id="selectM_T_MListByPage" resultMap="UserBaseResultMap">
				SELECT * FROM ssh_user LIMIT #{pageNo},#{pageSize}
			</select>
			<select id="selectRoleBySid" parameterType="int" resultType="org.wuxb.testMTMEntity.Role">
			SELECT * FROM ssh_role,ssh_user_role WHERE ssh_role.rid=ssh_user_role.rid AND sid = #{sid}
			</select>*/
	     
	    
	     rootElement.addComment("Many_To_Many:Component method");
	     
	     Element selectM_T_MComponentEle = rootElement.addElement("select");
	     //String selectM_T_MComponentSql = "selectM_T_MListByPage";
	     selectM_T_MComponentEle.addAttribute("id", "select"+ManyPOJO2.getSimpleName()+"By"+identification1);
	     selectM_T_MComponentEle.addAttribute("parameterType", idType);
	     selectM_T_MComponentEle.addAttribute("resultType", ManyPOJO2.getName());
	     
	     /*
	      * ��ҳ�б�Ƕ��SQL
	      *
	      * <select id="selectRoleBySid" parameterType="int" resultType="org.wuxb.testMTMEntity.Role">
			SELECT * FROM ssh_role,ssh_user_role WHERE ssh_role.rid=ssh_user_role.rid AND sid = #{sid}
			</select>
	      * 
	      */
	     StringBuffer sql_selectM_T_MComponentBuffer = new StringBuffer();
	     sql_selectM_T_MComponentBuffer.append("\n\tSELECT *")
	     .append("\n\tFROM "+tableName2+",")
	     .append(MappingTableName)
	     .append("\n\tWHERE ").append(tableName2+"."+identification2)
	     .append(" = ").append(MappingTableName+"."+identification2)
	     .append("\n\tAND").append(" "+identification1+" = ")
	     .append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}")
	     .append("\n\t");
	     selectM_T_MComponentEle.addText(sql_selectM_T_MComponentBuffer.toString());
		
	     
	     
	    
		
		
		
	};

	/**
	 * 
	 * @function ��Զ��������
	 * @author ���಩
	 * @time 2016-2-27 ����4:24:17
	 */
	public void createManyToManyInsertWithRelationSQL(Element rootElement,Class<?> ManyPOJO1,String identification1,String tableName1,Class<?> ManyPOJO2,String identification2,String tableName2,
			
			String MappingTableName,String M_T_M_Field_1,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo){
		
		 rootElement.addComment("Many_To_Many:Insert Object With Relation");
		
		 Element insertM_T_MSelectiveEle = rootElement.addElement("insert");
	     String insertM_T_MSelectiveSql = "insertM_T_MSelective";
	     insertM_T_MSelectiveEle.addAttribute("id", insertM_T_MSelectiveSql);
	     insertM_T_MSelectiveEle.addAttribute("parameterType",ManyPOJO1.getName() );
	     HashMap<String, String> insertM_T_MSelectiveSqlInfo = new HashMap<String, String>();
	     insertM_T_MSelectiveSqlInfo.put("args", ManyPOJO1.getName().substring(ManyPOJO1.getName().lastIndexOf(".")+1)+" "+ManyPOJO1.getName().substring(ManyPOJO1.getName().lastIndexOf(".")+1).toLowerCase());
	     insertM_T_MSelectiveSqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put(insertM_T_MSelectiveSql, insertM_T_MSelectiveSqlInfo);
	     
	     StringBuffer sql_insertM_T_MSelectiveBuffer = new StringBuffer();
	     sql_insertM_T_MSelectiveBuffer.append("\n\tINSERT ")
	     .append("INTO").append(" "+tableName1);
	     
	     insertM_T_MSelectiveEle.addText(sql_insertM_T_MSelectiveBuffer.toString());
	     Element trimEleOfinsertSelective = insertM_T_MSelectiveEle.addElement("trim");
	     trimEleOfinsertSelective.addAttribute("prefix", "(");
	     trimEleOfinsertSelective.addAttribute("suffix", ")");
	     trimEleOfinsertSelective.addAttribute("suffixOverrides", ",");
	     
	     Map<String, String> tableColumn = collectionOfTableInfo.get(tableName1);
	     Set<String> columnNames = tableColumn.keySet();
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertSelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+",\n\t");
	    	 
	     }
	     
	     
	     Element trimEleOfinsertSelective_values = insertM_T_MSelectiveEle.addElement("trim");
	     trimEleOfinsertSelective_values.addAttribute("prefix", "values (");
	     trimEleOfinsertSelective_values.addAttribute("suffix", ");");
	     trimEleOfinsertSelective_values.addAttribute("suffixOverrides", ",");
	     
	     
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = trimEleOfinsertSelective_values.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+"#{"+columnName+",jdbcType="+tableColumn.get(columnName)+"},"+"\n\t");
	     }
	     
	     
	     
	     
	     /*
	      *
	      * <insert id="insertMulti">
			INSERT INTO ssh_user(username) VALUES('ȫ��');
			INSERT INTO ssh_user_role (sid,rid) VALUES(LAST_INSERT_ID(),1);
			INSERT INTO ssh_user_role (sid,rid) VALUES(LAST_INSERT_ID(),2);
			INSERT INTO ssh_user_role (sid,rid) VALUES(LAST_INSERT_ID(),3);
			INSERT INTO ssh_user_role (sid,rid) VALUES(LAST_INSERT_ID(),4)
			</insert>	
	      * 
	      * 
	      * <if test="eduEmp != null">
			UPDATE t_emp SET eid = #{eid,jdbcType=INTEGER}  WHERE id IN
		      <foreach collection="eduEmp" item="obj" open="(" close=");" separator=",">
			#{obj.id}
			</foreach>
		    </if>
	      * 
	      * 
	      */
	     Element ifEleComponent = insertM_T_MSelectiveEle.addElement("if");
	     ifEleComponent.addAttribute("test", M_T_M_Field_1+" != null");
	     Element foreachComponent = ifEleComponent.addElement("foreach");
	     foreachComponent.addAttribute("collection", M_T_M_Field_1);
	     foreachComponent.addAttribute("item", "obj");
	     foreachComponent.addAttribute("separator", ";");
	     foreachComponent.addAttribute("close", " ");
	     
	     StringBuffer sql_foreachComponentBuffer = new StringBuffer();
	     sql_foreachComponentBuffer.append("\n\tINSERT INTO "+MappingTableName)
	     .append(" ("+identification1+","+identification2+") VALUES ")
	     .append("(LAST_INSERT_ID(),#{obj."+identification2+"})\n\t");
	     foreachComponent.addText(sql_foreachComponentBuffer.toString());
		
		
	};
	
	/**
	 * 
	 * @function ��Զ����ɾ��
	 * @author ���಩
	 * @time 2016-2-27 ����4:24:32
	 */
	public void createManyToManyDeleteWithRelationSQL(Element rootElement,String identification1,String tableName1,String MappingTableName,
			String idType,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){

		
		 rootElement.addComment("Many_To_Many:Delete Object With Relation");
		
		 Element deleteWithRelationShipByPrimaryKeyEle = rootElement.addElement("delete");
	     String deleteWithRelationShipByPrimaryKeySql = "deleteWithRelationShipByPrimaryKey";
	     deleteWithRelationShipByPrimaryKeyEle.addAttribute("id", deleteWithRelationShipByPrimaryKeySql);
	     deleteWithRelationShipByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> deleteWithRelationShipByPrimaryKeySqlInfo = new HashMap<String, String>();
	     deleteWithRelationShipByPrimaryKeySqlInfo.put("args",idType.substring(idType.lastIndexOf(".")+1)+" "+identification1);
	     deleteWithRelationShipByPrimaryKeySqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("deleteWithRelationShipByPrimaryKey", deleteWithRelationShipByPrimaryKeySqlInfo);
	     
	     
	     
	     /*
	      * 1.ɾ����������
	      */
	     StringBuffer sql_deleteM_T_MRemoveRelationKeyBuffer = new StringBuffer();
	     sql_deleteM_T_MRemoveRelationKeyBuffer
	     .append("\n\tDELETE FROM ").append(MappingTableName)
	     .append(" WHERE ").append(identification1)
	     .append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     
	     
	     
	     
	     //1
	     deleteWithRelationShipByPrimaryKeyEle.addText(sql_deleteM_T_MRemoveRelationKeyBuffer.toString());
	     
	     
	     StringBuffer sql_deleteM_T_MByPrimaryKeyBuffer = new StringBuffer();
	     sql_deleteM_T_MByPrimaryKeyBuffer
	     .append("\n\tDELETE FROM ").append(tableName1)
	     .append(" WHERE ").append(identification1)
	     .append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}\n\t");
	     
	     
	     
	     
	     deleteWithRelationShipByPrimaryKeyEle.addText(sql_deleteM_T_MByPrimaryKeyBuffer.toString());
	     
	    
	     
		
		
	};
	
	/**
	 * 
	 * @function 
	 * @author ���಩��Զ���ID��ѯ
	 * @time 2016-2-27 ����4:24:47
	 */
	public void createManyToManySelectByPrimaryKeySQL(Element rootElement,Class<?> ManyPOJO1,String tableName1,String identification1,
						String idType,
						String RESULTMAPPER_ID_NAME,
						Map<String, Map<String, String>> collectionOfSqlInfo,
						Map<String, Map<String, String>> collectionOfTableInfo,
						Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		 rootElement.addComment("Many_To_Many:Select Object By ID");
		
		 Element selectByPrimaryKeyEle = rootElement.addElement("select");
	     String selectByPrimaryKeySql = "selectByPrimaryKey";
	     selectByPrimaryKeyEle.addAttribute("id", selectByPrimaryKeySql);
	     selectByPrimaryKeyEle.addAttribute("resultMap", ManyPOJO1.getSimpleName()+RESULTMAPPER_ID_NAME);
	     selectByPrimaryKeyEle.addAttribute("parameterType",idType );
	     HashMap<String, String> selectByPrimaryKeySqlInfo = new HashMap<String, String>();
	     selectByPrimaryKeySqlInfo.put("args", idType.substring(idType.lastIndexOf(".")+1)+" "+identification1);
	     selectByPrimaryKeySqlInfo.put("return", ManyPOJO1.getSimpleName());
	     
	     collectionOfSqlInfo.put("selectByPrimaryKey", selectByPrimaryKeySqlInfo);
	     
	     StringBuffer sql_selectByPrimaryKeyBuffer = new StringBuffer();
	     sql_selectByPrimaryKeyBuffer.append("\n\tSELECT *")
	     .append(" \n\tFROM "+tableName1).append(" WHERE ")
	     .append(identification1).append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"}\n\t");
	     
	    
	     selectByPrimaryKeyEle.addText(sql_selectByPrimaryKeyBuffer.toString());
	     
		
	}
	/**
	 * 
	 * @function ��Զ�����޸�
	 * @author ���಩
	 * @time 2016-2-27 ����4:25:08
	 */
	public void createManyToManyUpdateByPrimaryKeySQL(Element rootElement,Class<?> ManyPOJO1,String identification1,String identification2,String tableName1,
			String MappingTableName,String M_T_M_Field_1,
			String idType,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			Map<String, Map<String, String>> collectionOfTableInfo,
			Map<String, String> collectionOfJDBCAndJAVATypeConvert){
		
		
		 rootElement.addComment("Many_To_Many:Update Object With Relation");
		
		 Element updateWithRelationShipByPrimaryKeyEle = rootElement.addElement("update");
	     String updateWithRelationShipByPrimaryKeySql = "updateWithRelationShipByPrimaryKey";
	     updateWithRelationShipByPrimaryKeyEle.addAttribute("id", updateWithRelationShipByPrimaryKeySql);
	     updateWithRelationShipByPrimaryKeyEle.addAttribute("parameterType",ManyPOJO1.getName() );
	     HashMap<String, String> updateWithRelationShipByPrimaryKeySqlInfo = new HashMap<String, String>();
	     updateWithRelationShipByPrimaryKeySqlInfo.put("args", ManyPOJO1.getName().substring(ManyPOJO1.getName().lastIndexOf(".")+1)+" "+ManyPOJO1.getName().substring(ManyPOJO1.getName().lastIndexOf(".")+1).toLowerCase());
	     updateWithRelationShipByPrimaryKeySqlInfo.put("return", "Integer");
	     
	     collectionOfSqlInfo.put("updateWithRelationShipByPrimaryKey", updateWithRelationShipByPrimaryKeySqlInfo);
	     
	     
	     StringBuffer sql_updateWithRelationShipByPrimaryKeyBuffer = new StringBuffer();
	     sql_updateWithRelationShipByPrimaryKeyBuffer.append("\n\tUPDATE ")
	     .append(" "+tableName1);
	     
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateWithRelationShipByPrimaryKeyBuffer.toString());
	     Element setEleOfupdateByPrimaryKeySelective = updateWithRelationShipByPrimaryKeyEle.addElement("set");
	     
	     Map<String, String> tableColumn = collectionOfTableInfo.get(tableName1);
	     Set<String> columnNames = tableColumn.keySet();
	    
	     for (String columnName : columnNames) {
	    	 
	    	 Element ifEle = setEleOfupdateByPrimaryKeySelective.addElement("if");
	    	 ifEle.addAttribute("test", columnName+" != null");
	    	 ifEle.addText("\n\t\t"+columnName+" = #{"+columnName+",jdbcType="+tableColumn.get(columnName)+"},"+"\n\t");
	    	 
	     }
	     
	     sql_updateWithRelationShipByPrimaryKeyBuffer = new StringBuffer();
	     sql_updateWithRelationShipByPrimaryKeyBuffer
	     .append("\n\t\tWHERE ")
	     .append(identification1).append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     
	    
	     
	     /*
	      * ---------------------------------------
	      */
	    
	     //3
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateWithRelationShipByPrimaryKeyBuffer.toString());
	     
	    
	     
	     
	     
	     /*
	      * 1.ɾ���ϵ
	      */
	     StringBuffer sql_updateM_T_MEmptyRelationKeyBuffer = new StringBuffer();
	     sql_updateM_T_MEmptyRelationKeyBuffer
	     .append("\n\tDELETE FROM ").append(MappingTableName)
	     .append(" WHERE ").append(identification1)
	     .append(" = ").append("#{"+identification1+",jdbcType="+collectionOfJDBCAndJAVATypeConvert.get(idType)+"};");
	     
	     
	     //1
	     updateWithRelationShipByPrimaryKeyEle.addText(sql_updateM_T_MEmptyRelationKeyBuffer.toString());
	     
	     
	     /*
	      * ������еĹ�ϵ
	      */
	     Element isListNullEle = updateWithRelationShipByPrimaryKeyEle.addElement("if");
	     
	     StringBuffer sql_updateM_T_MRelationKeyBuffer = new StringBuffer();
	     sql_updateM_T_MRelationKeyBuffer
	     .append("\n\tINSERT INTO ").append(MappingTableName)
	     .append(" ("+identification1+","+identification2+")VALUES ")
	     .append("(#{"+identification1+"},#{obj."+identification2+"})\n\t");
	     
	     
	    
	     isListNullEle.addAttribute("test", M_T_M_Field_1+" != null");
	     Element foreachEle = isListNullEle.addElement("foreach");
	     foreachEle.addAttribute("collection", M_T_M_Field_1);
	     foreachEle.addAttribute("item", "obj");
	     foreachEle.addAttribute("close", " ");
	     foreachEle.addAttribute("separator", ";");
	     foreachEle.addText(sql_updateM_T_MRelationKeyBuffer.toString());
	   
	     
	     
	    
		
		
	};
	
	

}
