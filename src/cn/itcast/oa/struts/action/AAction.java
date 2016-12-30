package cn.itcast.oa.struts.action;

import java.sql.Driver;

import cn.itcast.oa.domain.Person;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AAction extends ActionSupport implements ModelDriven<Person> {
	Person model = new Person();

	public String aa(){
		return "aa";
	}
	
	public String bb(){
		return "bb";
	}
	public Person getModel() {
		// TODO Auto-generated method stub
		return this.model;
	}
}
