package cn.itcast.oa.service;

import java.util.Collection;

import cn.itcast.oa.domain.Menuitem;

public interface MenuitemService {

	public Collection<Menuitem> getAllMenuitems();
	
	public Collection<Menuitem> getMenuitemsByPid(Long pid);
	
	public Collection<Menuitem> getMenuitemsByUsers();
}
