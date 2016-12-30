package cn.itcast.oa.dao.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.oa.dao.MenuitemDao;
import cn.itcast.oa.dao.base.impl.BaseDaoImpl;
import cn.itcast.oa.domain.Menuitem;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.utils.OAUtils;

@Repository("menuitemDao")
public class MenuitemDaoImpl extends BaseDaoImpl<Menuitem> implements MenuitemDao<Menuitem>{

	@Override
	public Collection<Menuitem> getAllEntries() {
		//使用迫切外连接,提高sql的效率
		List<Menuitem> list =this.hibernateTemplate.find("from Menuitem");
		return list;
	}

	public Collection<Menuitem> getMenuitemsByPid(Long pid) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Menuitem m left join fetch m.users u where pid=?",pid);
	}
	
	public Collection<Menuitem> getMenuitemsByUser(){
		User user = OAUtils.getUserFromSession();
		if(user.getUsername().equals("admin")){
			return this.getAllEntries();
		}else{
			List<Menuitem> list =  this.hibernateTemplate.find("from Menuitem m inner join fetch m.users u where u.uid=?",user.getUid());
			return new HashSet<Menuitem>(list);
		}
	}

}
