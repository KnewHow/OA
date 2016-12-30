package cn.itcast.oa.struts.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.LoginService;
import cn.itcast.oa.service.UserService;
import cn.itcast.oa.struts.action.base.BaseAction;
import cn.itcast.oa.utils.OAUtils;

@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends BaseAction<User>{

	@Resource(name="loginService")
	private LoginService loginService;
	
	public String login(){
		User user = this.loginService.login(this.getModel().getUsername(), this.getModel().getPassword());
		if(user!=null){
			OAUtils.putUserToSession(user);
			return "index";
		}else{
			return null;
		}
		
	}
}
