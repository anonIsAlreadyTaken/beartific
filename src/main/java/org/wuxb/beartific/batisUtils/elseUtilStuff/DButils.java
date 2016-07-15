package org.wuxb.beartific.batisUtils.elseUtilStuff;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;




/**
 * 
 *��ݿ����ӹ����� 
 * @author Anon
 **/
public class DButils {

	private static String Driver;
	private static String url;
	private static String userName;
	private static String password;

	/**
	 * 
	 *��̬�����DB.properties�ļ� 
	 * 
	 **/
	static{


		InputStream resource = DButils.class.getResourceAsStream("DB.properties");

		Properties p = new Properties();
		try {
			p.load(resource);
			Enumeration<Object> keys = p.keys();
			while(keys.hasMoreElements()){

				String key =(String)keys.nextElement();
				if(key.equals("Driver")){
					Driver = p.getProperty(key);
				}else if(key.equals("url")){
					url = p.getProperty(key);
				}else if(key.equals("userName")){
					userName = p.getProperty(key);
				}else{
					password = p.getProperty(key);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	/**
	 *
	 *@author Anon
	 * 
	 *@return ����Connectionʵ��,�����Զ�ȡDB.properties�ļ�
	 *
	 **/
	public static Connection getConnection(){
		Connection conn = null;
		
			try {
				Class.forName(Driver);
				conn = DriverManager.getConnection(url,userName,password);
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException:���properties�е�Driver");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("SQLException:���properties�ļ��е�url,userName,password�����ò���");
				e.printStackTrace();
			}
		
		return conn;

	}
	/**
	 * 
	 * @author Anon
	 * 
	 *@function ����
	 *
	 * 
	 **/
	public static void free(ResultSet rs,Statement st,Connection conn){

		if(rs!=null){
			try {
				rs.close();
			} catch (Exception e) {
				System.out.println("������ʧ��");
				e.printStackTrace();
			}
		}
		if(st!=null){
			try {
				st.close();
			} catch (Exception e) {
				System.out.println("��������ʧ��");
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println("���ӹ���ʧ��");
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 *<p>���Listչʾ�б� </p>
	 * 
	 * @author Anon
	 * 
	 * @function ��ý���List<?>������ʽ
	 * 
	 * @param c javaBean��Classʵ��
	 * 
	 * @param sql:��Ҫִ�е�SQL��䡣
	 * 
	 * @return ��ѯ���
	 * 
	 * ʹ�ø÷������뱣֤��ݿ����ֶ�����javaBean���������Ӧ��һ��,����javaBean��Ӧ���ڿղι���
	 **/
	@SuppressWarnings("unchecked")
	public static List<?> getList(Class<?> c,String sql){

		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList objList = new ArrayList();

		
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				Object obj = null;
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();

				while(rs.next()){
					obj = c.newInstance();
					for (int i = 1; i <= columnCount; i++) {
						BeanUtils.setProperty(obj, metaData.getColumnName(i), rs.getObject(i));
					}
					objList.add(obj);
				}
			} catch (SQLException e) {
				System.out.println("SQLException:�����ݿ���������javaBean���������Ƿ���ͬ");
				e.printStackTrace();
			} catch (InstantiationException e) {
				System.out.println("InstantiationException:���javaBean���Ƿ�����޲ι���");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("IllegalAccessException:����Ƿ����set,get�������Ƿ���ڿղι���");
				e.printStackTrace();
			}finally{
				
				free(rs, ps, conn);
			}
		
		return objList;

	}

	/**
	 *
	 *@author Anon
	 *
	 *@function ִ������ɾ���ĵ�SQL��� 
	 *
	 *@param Ҫִ�е�SQL���
	 *
	 *@return ����ִ���Ƿ�ɹ�
	 * 
	 **/
	public static boolean executeSQL(String sql){

		Connection conn = getConnection();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sql);
			int flag = ps.executeUpdate();
			if(flag>0){
				return true;
			}

		} catch (SQLException e) {
			System.out.println("SQLException:���SQL���");
			e.printStackTrace();
		}finally{

			free(null, ps, conn);

		}

		return false;
	}
	/**
	 * 
	 *@author Anon
	 * 
	 *@function ͨ��id�õ�ʵ����� 
	 *
	 *@param c javaBean��Classʵ��
	 *
	 *@param sql ��Ҫִ�е�SQL��䡣
	 *
	 *@return ���ز��ҵ��Ķ���
	 *
	 *ʹ�ø÷������뱣֤��ݿ����ֶ�����javaBean���������Ӧ��һ��,����javaBean��Ӧ���ڿղι���
	 **/
	public static Object getObjById(Class<?> c,String sql){

		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object obj = null;

			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();

				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();

				if(rs.next()){
					obj = c.newInstance();
					for (int i = 1; i <= columnCount; i++) {
						BeanUtils.setProperty(obj, metaData.getColumnName(i), rs.getObject(i));
					}
				
}
			} catch (SQLException e) {
				System.out.println("SQLException:���SQL���");
				e.printStackTrace();
			} catch (InstantiationException e) {
				System.out.println("InstantiationException:���javaBean���Ƿ�����޲ι���");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("IllegalAccessException:����Ƿ����set,get�������Ƿ���ڿղι���");
				e.printStackTrace();
			} finally{
				
				free(rs, ps, conn);
			}


		return obj;


	}
	
	/**
	 * 
	 *@author Anon
	 * 
	 *@function ��ñ���������� 
	 *
	 *@param sql ��ѯ����ʱ�õ���SQL���
	 *
	 *@return �����ܹ����������
	 *
	 **/
	public static int getCountOfTable(String sql){
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				
				result = rs.getInt(1);
				
			}
		} catch (SQLException e) {
			System.out.println("SQLException:���SQL���");
			e.printStackTrace();
		}finally{
			
			free(rs, ps, conn);
		}
		return result;
	}
	
	/**
	 * 
	 * @author Anon
	 * 
	 * @function ִ������
	 * 
	 * @param sqlArray ��Ҫִ�е������е����
	 * 
	 * 
	 * 
	 **/
	public static boolean executeTransaction(String...sqlArray){
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		
		try {
			conn.setAutoCommit(false);
			
			for (String sql : sqlArray) {
				
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				
			}

			conn.commit();
			conn.setAutoCommit(true);
			return true;
			
		} catch (SQLException e) {
			
			
			try {
				System.out.println("����ִ�г��ִ��󣬼��SQL���");
				conn.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}finally{
			
			free(null, ps, conn);
			
		}
		
		return false;
		
		
	}

}
