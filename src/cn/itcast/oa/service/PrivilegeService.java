package cn.itcast.oa.service;

import java.util.Collection;
import java.util.Set;

import cn.itcast.oa.domain.Menuitem;

public interface PrivilegeService {

	public Collection<Menuitem> getAllPrivileges();
	
	public Set<Menuitem> getPrivilegesByIds(Long[] ids);
	
	public Collection<Menuitem> getPrivilegesByUid(Long uid);
}
