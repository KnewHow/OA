package cn.itcast.oa.dao;

import java.util.Collection;

import cn.itcast.oa.dao.base.BaseDao;
import cn.itcast.oa.domain.Kynamic;
import cn.itcast.oa.domain.Version;

public interface KynamicDao<T> extends BaseDao<T>{

	public Kynamic getKynamicByName(String name,Long pid);
	
	public Collection<Kynamic> getSiblingNodes(Long kid);
	
	public Kynamic getParentNode(Long kid);
	
	public Collection<Version> getVersionsByKid(Long kid);
	
	
}
