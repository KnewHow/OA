package cn.itcast.oa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.oa.dao.LoginDao;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.LoginService;
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Resource(name="loginDao")
	private LoginDao loginDao;

	public User login(String username, String password) {
		// TODO Auto-generated method stub
		return this.loginDao.getUserByUsernameAndPassword(username, password);
	}
}
