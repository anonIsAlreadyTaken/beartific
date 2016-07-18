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
 *数据库连接工具类 
 * @author Anon
 **/
public class DButils {

	private static String Driver;
	private static String url;
	private static String userName;
	private static String password;

	/**
	 *
	 *静态块加载DB.properties文件 
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
	 *@return 返回Connection实例,其属性读取DB.properties文件
	 *
	 **/
	public static Connection getConnection(){
		Connection conn = null;

		try {
			Class.forName(Driver);
			conn = DriverManager.getConnection(url,userName,password);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:检查properties中的Driver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException:检查properties文件中的url,userName,password等配置参数");
			e.printStackTrace();
		}

		return conn;

	}
	/**
	 *
	 * @author Anon
	 *
	 *@function 关流
	 *
	 *
	 **/
	public static void free(ResultSet rs,Statement st,Connection conn){

		if(rs!=null){
			try {
				rs.close();
			} catch (Exception e) {
				System.out.println("结果集关流失败");
				e.printStackTrace();
			}
		}
		if(st!=null){
			try {
				st.close();
			} catch (Exception e) {
				System.out.println("声明关流失败");
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println("连接关流失败");
				e.printStackTrace();
			}
		}

	}

	/**
	 *
	 *<p>获得List展示列表 </p>
	 *
	 * @author Anon
	 *
	 * @function 获得结果集的List<?>表现形式
	 *
	 * @param c javaBean的Class实例。
	 *
	 * @param sql:需要执行的SQL语句。
	 *
	 * @return 查询结果集
	 *
	 * 使用该方法必须保证数据库中字段名与javaBean的属性名对应相一致,并且javaBean中应存在空参构造
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
			System.out.println("SQLException:检查数据库中列名与javaBean中属性名是否相同");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException:检查javaBean中是否存在无参构造");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException:检查是否存在set,get方法，是否存在空参构造");
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
	 *@function 执行增，删，改的SQL语句 
	 *
	 *@param 要执行的SQL语句
	 *
	 *@return 返回执行是否成功
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
			System.out.println("SQLException:检查SQL语句");
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
	 *@function 通过id得到实例对象
	 *
	 *@param c javaBean的Class实例。
	 *
	 *@param sql 需要执行的SQL语句。
	 *
	 *@return 返回查找到的对象
	 *
	 *使用该方法必须保证数据库中字段名与javaBean的属性名对应相一致,并且javaBean中应存在空参构造
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
			System.out.println("SQLException:检查SQL语句");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException:检查javaBean中是否存在无参构造");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException:检查是否存在set,get方法，是否存在空参构造");
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
	 *@function 获得表中数据条数
	 *
	 *@param sql 查询条数时用到的SQL语句
	 *
	 *@return 返回总共的数据条数
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
			System.out.println("SQLException:检查SQL语句");
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
	 * @function 执行事务
	 *
	 * @param sqlArray 需要执行的事务中的语句
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
				System.out.println("事务执行出现错误，检查SQL语句");
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
