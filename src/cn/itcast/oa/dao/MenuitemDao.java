package cn.itcast.oa.dao;

import java.util.Collection;

import cn.itcast.oa.dao.base.BaseDao;
import cn.itcast.oa.domain.Menuitem;

public interface MenuitemDao<T> extends BaseDao<T> {
	//根据pid获取子节点
	public Collection<Menuitem> getMenuitemsByPid(Long pid);
	public Collection<Menuitem> getMenuitemsByUser();
}
