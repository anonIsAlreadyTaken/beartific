package org.wuxb.beartific.batisUtils.elseUtilStuff;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/***
 * 
 * @author Anon
 * @function ����JavaBean�����ԺͶ������
 * 
 */
public class BeanUtils {


	/***
	 * 
	 * @author Anon
	 * 
	 * @function ��object model ���������Ը��Ƹ�object copy ����Ҫ����ͬһ�����ʵ�� 
	 * 
	 * @param model �Ӹö�����ȡ������
	 * 
	 * @param copy ���Ƹ�ö���
	 */
	public static void copyProperties(Object model,Object copy){

		//���ģ�Ͷ������ʵ��
		Class<? extends Object> modelClass = model.getClass();
		Class<? extends Object> copyClass = copy.getClass();
		//�������ģ�Ͷ������������ķ���
		Method[] modelMethods = modelClass.getDeclaredMethods();
		Method[] copyMethods = copyClass.getDeclaredMethods();

		//����
		for (Method method : modelMethods) {
			//�ж�ȡ��get����
			if(method.getName().startsWith("get")){
				//�ٴα���
				for (Method methodSet : copyMethods) {
					//�жϻ�����е�set����
					if(methodSet.getName().startsWith("set")){
						//�ж�setXXX��getXXX��XXX�Ƿ�һ��
						if(methodSet.getName().substring(1).equals(method.getName().substring(1))){
							Object value = null;
							try {
								value = method.invoke(model);
								//���Ѹ��ƶ����set����������ģ�Ͷ����get����
								methodSet.invoke(copy, value);
							} catch (Exception e) {
								System.out.println("beanutils:����������"+methodSet.getName().substring(3)+"���Ե��������Ͳ�ͬ,�����Ը�ֵʧ��");
								System.out.println("beanutils:��������ж�Ӧ������Ϊ"+value.getClass().getName()+"����ʹ˫���������Ͷ�Ӧ");
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
	 * @function ������obj��������Ϊname�����Ե�ֵ����Ϊvalue
	 * 
	 * @param obj Ҫ������ԵĶ���
	 * 
	 * @param name ��Ҫ��ĵ�������
	 * 
	 * @param value ��Ҫ���õ�����ֵ
	 * 
	 **/

	public static void setProperty(Object obj,String name,Object value){

		//���ģ�Ͷ������ʵ��
		Class<? extends Object> objClass = obj.getClass();
		//�������ģ�Ͷ������������ķ���
		Method[] methods = objClass.getDeclaredMethods();
		
		for (Method method : methods) {
			//���Ҫset���Ե�set����
			if(method.getName().equalsIgnoreCase("set"+name)){
				try {
					//���Ѳ�����ֵ
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
	 * @function ��obj�е������Լ�ֵ�Եķ�ʽ����map,��mapToObject��Ӧʹ��
	 * 
	 * @param o ��Ҫת��ΪHashMap�Ķ���
	 * 
	 * @throws InvocationTargetException 
	 * 
	 * @throws IllegalAccessException 
	 * 
	 * @throws IllegalArgumentException 
	 * 
	 * @return ת���ɹ���Map<?,?>ʵ��
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
	 * @function ��¡��һ���¶��󣬸ö�����Ա����������ͬ
	 * 
	 * @param o ��Ϊģ��Ķ���
	 * 
	 * @return ��¡���Ķ���
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
	 * @function ��mapת��Ϊ��Ӧ���󣬸�map�ļ�ֵ�Ա�����ö�������ж������������ֵ����һһ��Ӧ
	 * 
	 * @param m ������ݵ�map
	 * 
	 * @param o Ҫ�������ԵĶ���
	 * 
	 * @return ��������������ԵĶ���
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
