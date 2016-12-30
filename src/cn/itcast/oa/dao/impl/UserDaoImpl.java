package cn.itcast.oa.dao.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.oa.dao.UserDao;
import cn.itcast.oa.dao.base.impl.BaseDaoImpl;
import cn.itcast.oa.domain.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao<User> {

	@Override
	public Collection<User> getAllEntries() {
		// TODO Auto-generated method stub
		List<User> userList =  this.getHibernateTemplate().find("from User u left join fetch u.department d left join fetch u.posts p");
		return new HashSet<User>(userList);
	}

	public User getUserByName(String username) {
		List<User> userList = this.hibernateTemplate.find("from User where username=?",username);
		if(userList.size()==0){
			return null;
		}else{
			return userList.get(0);
		}
	}

}
