package org.wuxb.beartific.batisUtils.elseUtilStuff;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/***
 *
 * @author Anon
 * @function 进行JavaBean的属性和对象操作
 *
 */
public class BeanUtils {


	/***
	 *
	 * @author Anon
	 *
	 * @function 将object model 的所有属性复制给object copy 二者要属于同一个类的实例 
	 *
	 * @param model 从该对象中取出属性
	 *
	 * @param copy 复制给该对象
	 */
	public static void copyProperties(Object model,Object copy){

		//获得模型对象的类实例
		Class<? extends Object> modelClass = model.getClass();
		Class<? extends Object> copyClass = copy.getClass();
		//获得所有模型对象类中声明的方法
		Method[] modelMethods = modelClass.getDeclaredMethods();
		Method[] copyMethods = copyClass.getDeclaredMethods();

		//遍历方法
		for (Method method : modelMethods) {
			//判断取得get方法
			if(method.getName().startsWith("get")){
				//再次遍历
				for (Method methodSet : copyMethods) {
					//判断获得所有的set方法
					if(methodSet.getName().startsWith("set")){
						//判断setXXX和getXXX的XXX是否一致
						if(methodSet.getName().substring(1).equals(method.getName().substring(1))){
							Object value = null;
							try {
								value = method.invoke(model);
								//唤醒复制对象的set方法，传入模型对象的get方法
								methodSet.invoke(copy, value);
							} catch (Exception e) {
								System.out.println("beanutils:两个对象中"+methodSet.getName().substring(3)+"属性的属性类型不同,该属性赋值失败");
								System.out.println("beanutils:传入数据中对应列类型为"+value.getClass().getName()+"请更改使双方属性类型对应");
							}

						}

					}

				}
			}
		}


	}
	/**
	 * @author Anon
	 *
	 * @function 将对象obj中属性名为name的属性的值设置为value
	 *
	 * @param obj 要更改属性的对象
	 *
	 * @param name 需要更改的属性名
	 *
	 * @param value 需要设置的属性值
	 *
	 **/

	public static void setProperty(Object obj,String name,Object value){

		//获得模型对象的类实例
		Class<? extends Object> objClass = obj.getClass();
		//获得所有模型对象类中声明的方法
		Method[] methods = objClass.getDeclaredMethods();

		for (Method method : methods) {
			//获得要set属性的set方法
			if(method.getName().equalsIgnoreCase("set"+name)){
				try {
					//唤醒并传入值
					method.invoke(obj, value);
				} catch (Exception e) {
					System.out.println(method.getName());
					e.printStackTrace();
				}

			}

		}

	}
	/**
	 *
	 * @author Anon
	 *
	 * @function 将obj中的属性以键值对的方式传入map,和mapToObject对应使用
	 *
	 * @param o 需要转化为HashMap的对象
	 *
	 * @throws InvocationTargetException
	 *
	 * @throws IllegalAccessException
	 *
	 * @throws IllegalArgumentException
	 *
	 * @return 转化成功的Map<?,?>实例
	 *
	 **/

	public static Map<Object, Object> ObjectToMap(Object o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{

		HashMap<Object, Object> map = new HashMap<Object, Object>();
		Class<? extends Object> clazz = o.getClass();
		Method[] method = clazz.getDeclaredMethods();
		for (Method methodEle : method) {

			String name = methodEle.getName();
			if(name.startsWith("get")){
				String key = name.substring(3).toLowerCase();
				Object value = methodEle.invoke(o);
				map.put(key, value);

			}


		}

		return map;

	}

	/**
	 *
	 * @author Anon
	 *
	 * @function 克隆出一个新对象，该对象与员对象属性相同
	 *
	 * @param o 作为模板的对象
	 *
	 * @return 克隆出的对象
	 *
	 * @throws IllegalAccessException
	 *
	 * @throws InstantiationException
	 *
	 * @throws InvocationTargetException
	 *
	 * @throws IllegalArgumentException
	 **/

	public static Object Clone(Object o) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{



		Class<? extends Object> srcClass = o.getClass();
		Method[] method = srcClass.getDeclaredMethods();
		Object objectTarget = srcClass.newInstance();
		for (Method methodGet : method) {
			if(methodGet.getName().startsWith("get")){
				Object value = methodGet.invoke(o);
				for (Method methodSet : method) {
					if(methodSet.getName().startsWith("set")){

						if(methodGet.getName().substring(3).equals(methodSet.getName().substring(3))){

							methodSet.invoke(objectTarget,value);


						}

					}

				}

			}

		}

		return objectTarget;
	}

	/**
	 *
	 *
	 * @author Anon
	 *
	 * @function 将map转化为相应对象，该map的键值对必须与该对象的类中定义的属性名与值类型一一对应
	 *
	 * @param m 存在数据的map
	 *
	 * @param o 要放入属性的对象
	 *
	 * @return 返回设置完成属性的对象
	 *
	 * @throws InvocationTargetException
	 *
	 * @throws IllegalAccessException
	 *
	 * @throws IllegalArgumentException
	 *
	 **/

	public static Object mapToObject(Map<?, ?> m,Object o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class<? extends Object> ObjectClass = o.getClass();
		Method[] methods = ObjectClass.getDeclaredMethods();

		for (Method method : methods) {

			if(method.getName().startsWith("set")){

				Set<?> keySet = m.keySet();
				for (Object object : keySet) {

					if(method.getName().substring(3).toLowerCase().equals(object.toString())){

						Object value = m.get(object.toString());
						method.invoke(o, value);
					}

				}


			}

		}


		return o;
	}



}
