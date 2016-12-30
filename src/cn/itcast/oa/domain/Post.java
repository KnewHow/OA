package cn.itcast.oa.domain;

import java.io.Serializable;
import java.util.Set;

public class Post implements Serializable{

	private Long pid;//值为的id
	private String pname;//职位名称
	private String description;//职位描述
	private Set<User> users;//职位里面的所有用户
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
}
