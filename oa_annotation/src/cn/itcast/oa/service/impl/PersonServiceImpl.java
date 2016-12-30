package cn.itcast.oa.service.impl;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.dao.PersonDao;
import cn.itcast.oa.domain.Person;
import cn.itcast.oa.service.PersonService;

public class PersonServiceImpl implements PersonService {

	private PersonDao personDao;
	@Transactional(readOnly=false)
	public void savePerson(Person person) {
		// TODO Auto-generated method stub
		this.personDao.savePerson(person);
	}
	public PersonDao getPersonDao() {
		return personDao;
	}
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
	public Person getPersonById(Serializable id) {
		Person person = this.personDao.getPersonById(id);
		return person;
	}
	
}
