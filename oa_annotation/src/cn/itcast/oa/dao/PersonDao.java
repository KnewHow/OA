package cn.itcast.oa.dao;

import java.io.Serializable;

import cn.itcast.oa.domain.Person;

public interface PersonDao {

	public void savePerson(Person person);
	public Person getPersonById(Serializable id);

}
