package cn.itcast.oa.dao;

import java.util.Collection;
import java.util.Set;

import cn.itcast.oa.dao.base.BaseDao;
import cn.itcast.oa.domain.Menuitem;

public interface PrivilegeDao<T> extends BaseDao<T> {

	public Set<Menuitem> getPrivilegesByIds(Long[] ids);
	
	public Collection<Menuitem> getPrivilegesByUid(Long uid);
}
