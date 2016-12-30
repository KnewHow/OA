package cn.itcast.oa.dao.impl;

import java.io.Serializable;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.oa.dao.PersonDao;
import cn.itcast.oa.domain.Person;

public class PersonDaoImpl extends HibernateDaoSupport implements PersonDao{

	public void savePerson(Person person) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(person);
	}

	public Person getPersonById(Serializable id) {
		// TODO Auto-generated method stub
		Person person = (Person) this.getHibernateTemplate().load(Person.class, id);
		return person;
	}

}
