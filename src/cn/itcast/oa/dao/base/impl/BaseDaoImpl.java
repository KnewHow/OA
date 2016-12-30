package cn.itcast.oa.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.annotation.Resource;


import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.itcast.oa.dao.base.BaseDao;

//子类里面的t是给父类里面t赋值，子类里面的t是实参，父类里面的t是形参
public class BaseDaoImpl<T> implements BaseDao<T> {

	@Resource(name="hibernateTemplate")
	public HibernateTemplate hibernateTemplate;
	private Class classt;//泛型的Class
	/*
	 * 在父类中，要执行一段代码，执行的时间是在子类创建对象的时候，那么有两种解决方案
	 * 1、使用static代码块
	 * 2、利用父类的构造函数
	 * 
	 * 分析：如果需要使用到this，那么就要使用父类的构造函数，如果不需要使用daothis，可以使用static代码块
	 * 因为下面需要使用this，获取ParameterizedType，所以使用父类的构造函数
	 * 如何获取泛型里面的class
	 */
	public BaseDaoImpl(){
		//ParameterizedType,就是泛型
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获取到泛型的Class
		this.classt = (Class) type.getActualTypeArguments()[0];
	}
	public Collection<T> getAllEntries() {
		return this.getHibernateTemplate().find("from "+this.classt.getName());
	}

	public T getEntryById(Serializable id) {
		// TODO Auto-generated method stub
		return (T) this.hibernateTemplate.get(this.classt, id);
		
	}

	public void saveEntry(T t) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.save(t);
	}

	public void updateEntry(T t) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.update(t);
	}

	public void deleteEntryById(Serializable id) {
		T t = this.getEntryById(id);
		this.getHibernateTemplate().delete(t);
		
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
	
	

}
