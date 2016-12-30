package cn.itcast.oa.domain;

import java.io.Serializable;
import java.util.Set;

public class User implements Serializable{

	private Long uid;//用户id
	private String username;//用户名称
	private String password;
	private String email;//邮件
	private String phone;//手机号码
	private String sex;//性别
	private Department department;//所属部门
	private Set<Post> posts;//所属职位
	private Set<Menuitem> menuitems;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Set<Post> getPosts() {
		return posts;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	public Set<Menuitem> getMenuitems() {
		return menuitems;
	}
	public void setMenuitems(Set<Menuitem> menuitems) {
		this.menuitems = menuitems;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
