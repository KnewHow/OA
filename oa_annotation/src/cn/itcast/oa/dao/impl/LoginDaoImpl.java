package cn.itcast.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.oa.dao.LoginDao;
import cn.itcast.oa.dao.base.impl.BaseDaoImpl;
import cn.itcast.oa.domain.User;

@Repository("loginDao")
public class LoginDaoImpl extends BaseDaoImpl<User> implements LoginDao<User>{

	public User getUserByUsernameAndPassword(String username, String password) {
		Object [] params = new Object []{username,password};
		List<User> userList = this.hibernateTemplate.find("from User u where u.username=? and u.password=?",params);
		if(userList.size()==0){
			return null;
		}else{
			return userList.get(0);
		}
	}

}
