package cn.itcast.oa.struts.action;

import org.apache.struts2.ServletActionContext;

import cn.itcast.oa.domain.Person;
import cn.itcast.oa.service.PersonService;

public class PersonAction {

	private PersonService personService;

	public String savePerson(){
		Person person = new Person();
		person.setPname("曹操");
		person.setPsex("男");
		this.personService.savePerson(person);
		return null;
	}
	
	public String getPerson(){
		Person person = this.personService.getPersonById(1l);
		ServletActionContext.getRequest().setAttribute("person", person);
		return "index";
	}
	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	
	
}
