package cn.itcast.oa.service;

import java.io.Serializable;
import java.util.Collection;

import cn.itcast.oa.domain.User;

public interface UserService {

	public Collection<User> getAllUsers();
	public void saveUser(User user);
	public void updateUser(User user);
	public void deleteUserById(Serializable id);
	public User getUserById(Serializable id);
	public User getUserByName(String username);

}
