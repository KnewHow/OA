package cn.itcast.oa.service.impl;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.oa.dao.PrivilegeDao;
import cn.itcast.oa.dao.UserDao;
import cn.itcast.oa.domain.Menuitem;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.PrivilegeService;

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

	@Resource(name="privilegeDao")
	private PrivilegeDao privilegeDao;
	@Resource(name="userDao")
	private UserDao userDao;
	
	
	public Collection<Menuitem> getAllPrivileges() {
		// TODO Auto-generated method stub
		return this.privilegeDao.getAllEntries();
	}
	public Set<Menuitem> getPrivilegesByIds(Long[] ids) {
		// TODO Auto-generated method stub
		return this.privilegeDao.getPrivilegesByIds(ids);
	}
	public Collection<Menuitem> getPrivilegesByUid(Long uid) {
		User user = (User) this.userDao.getEntryById(uid);
		Collection<Menuitem> menuitems = this.privilegeDao.getAllEntries();
		Collection<Menuitem> menuitems2 = this.privilegeDao.getPrivilegesByUid(uid);
		if(user.getUsername().equals("admin")){
			for(Menuitem menuitem:menuitems){
				menuitem.setChecked(true);
			}
		}else{
			for(Menuitem menuitem1:menuitems){
				for(Menuitem menuitem2:menuitems2){
					if(menuitem1.getMid().longValue()==menuitem2.getMid().longValue()){
						menuitem1.setChecked(true);
					}
				}
			}
		}
		return menuitems;
	}

}
