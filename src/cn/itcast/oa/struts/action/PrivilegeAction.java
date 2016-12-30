package cn.itcast.oa.struts.action;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.domain.Menuitem;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.service.UserService;
import cn.itcast.oa.struts.action.base.BaseAction;
import cn.itcast.oa.utils.OAUtils;

@Controller("privilegeAction")
@Scope("prototype")
public class PrivilegeAction extends BaseAction<Menuitem>{
	@Resource(name="privilegeService")
	private PrivilegeService privilegeService;
	
	@Resource(name="userService")
	private UserService userService;
	
	private Collection<Menuitem> privilegeList;
	
	private Long uid;
	
	private String mids;
	
	private Long [] aa;

	public String showAllPrivileges(){
		this.privilegeList = this.privilegeService.getAllPrivileges();
		return SUCCESS;
	}
	
	public String showPrivilegesByUid(){
		this.privilegeList = this.privilegeService.getPrivilegesByUid(this.uid);
		return SUCCESS;
	}
	
	public Collection<Menuitem> getPrivilegeList() {
		return privilegeList;
	}
	
	public String savePrivilege(){
		User user = this.userService.getUserById(this.uid);
		System.out.println(this.aa);
		Set<Menuitem> menuitemList = this.privilegeService.getPrivilegesByIds(OAUtils.stringToLongArray(this.mids));
		user.setMenuitems(menuitemList);
		this.userService.updateUser(user);
		return SUCCESS;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getMids() {
		return mids;
	}

	public void setMids(String mids) {
		this.mids = mids;
	}

	public Long[] getAa() {
		return aa;
	}

	public void setAa(Long[] aa) {
		this.aa = aa;
	}
	
	
	
	
	
	
	
	
}
