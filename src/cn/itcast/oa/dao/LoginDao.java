package cn.itcast.oa.dao;

import cn.itcast.oa.dao.base.BaseDao;
import cn.itcast.oa.domain.User;

public interface LoginDao<T> extends BaseDao<T> {

	public User getUserByUsernameAndPassword(String username,String password);
}
