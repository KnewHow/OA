package cn.itcast.oa.struts.action.base;

import java.lang.reflect.ParameterizedType;
import java.sql.Driver;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 使用泛型，让baseAction实现ModelDriven，在这里面对页面数据进行封装
 * @author Administrator
 *
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	/**
	 *经常使用的返回类型
	 */
	private T t;//目标类,需要返回的对象
	private Class classt;//目标类的class
	public BaseAction(){
		//parameterizedType 就是泛型
		//这里的this指的是子类的this，通过子类，获取父类的泛型
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获取泛型的class
		this.classt = (Class) parameterizedType.getActualTypeArguments()[0];
		System.out.println(classt);
		this.fun1();
		//使用class创建目标类
		try {
			
			this.t = (T) this.classt.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static final String LISTACTION="listAction";
	public static final String ADDUI="addUI";
	public static final String UPDATEUI="updateUI";
	public static final String ACTIONTOACTION="actionToAction";
	
	public String listAction=LISTACTION;
	public String addUI=ADDUI;
	public String updateUI=UPDATEUI;
	public String actionToAction=ACTIONTOACTION;
	public T getModel() {
		// TODO Auto-generated method stub
		return this.t;
	}
	
	public void fun1(){
		System.out.println("父类的fun1");
	}
}
