package cn.itcast.oa.service.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.dao.UserDao;
import cn.itcast.oa.dao.impl.UserDaoImpl;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userDao")
	private UserDao userDao;
	public Collection<User> getAllUsers() {
		return this.userDao.getAllEntries();
	}

	@Transactional(readOnly=false)
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		this.userDao.saveEntry(user);
	}
	
	@Transactional(readOnly=false)
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		this.userDao.updateEntry(user);
	}

	@Transactional(readOnly=false)
	public void deleteUserById(Serializable id) {
		// TODO Auto-generated method stub
		this.userDao.deleteEntryById(id);
	}

	public User getUserById(Serializable id) {
		// TODO Auto-generated method stub
		return (User) this.userDao.getEntryById(id);
	}

	public User getUserByName(String username) {
		// TODO Auto-generated method stub
		return this.userDao.getUserByName(username);
	}

}
