package cn.itcast.oa.dao;

import cn.itcast.oa.dao.base.BaseDao;
import cn.itcast.oa.domain.User;

public interface UserDao<T> extends BaseDao<T> {

	public User getUserByName(String username);
}
