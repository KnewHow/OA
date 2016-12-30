package cn.itcast.oa.service;

import cn.itcast.oa.domain.User;

public interface LoginService {

	public User login(String username,String password);
}
