package cn.itcast.oa.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.oa.dao.MenuitemDao;
import cn.itcast.oa.domain.Menuitem;
import cn.itcast.oa.service.MenuitemService;

@Service("menuitemService")
public class MenuitemServiceImpl implements MenuitemService {

	@Resource(name="menuitemDao")
	private MenuitemDao menuitemDao;
	public  Collection<Menuitem> getAllMenuitems() {
		return this.menuitemDao.getAllEntries();
		// TODO Auto-generated method stub
		
	}
	public Collection<Menuitem> getMenuitemsByPid(Long pid) {
		// TODO Auto-generated method stub
		return this.menuitemDao.getMenuitemsByPid(pid);
	}
	public Collection<Menuitem> getMenuitemsByUsers() {
		// TODO Auto-generated method stub
		return this.menuitemDao.getMenuitemsByUser();
	}

	
}
