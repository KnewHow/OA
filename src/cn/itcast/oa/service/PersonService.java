package cn.itcast.oa.service;

import java.io.Serializable;

import cn.itcast.oa.domain.Person;

public interface PersonService {

	public void savePerson(Person person);
	public Person getPersonById(Serializable id);
}
