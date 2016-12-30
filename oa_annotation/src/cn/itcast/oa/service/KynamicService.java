package cn.itcast.oa.service;

import java.util.Collection;

import cn.itcast.oa.domain.Kynamic;
import cn.itcast.oa.domain.Version;

public interface KynamicService {
	public Collection<Kynamic> getAllKynamics();
	
	public void saveKynamic(Kynamic kynamic);
	
	public Boolean isKynamicExistByName(String name,Long pid);
	
	public void deleteKynamicById(Long kid);
	
	public Collection<Kynamic> getSiblingNodes(Long kid);
	
	public Kynamic getParentNode(Long kid);
	
	public Kynamic getKynamicById(Long id);
	
	public void updateKynamic(Kynamic kynamic);
	
	public Collection<Version> getVersionsByKid(Long kid);
}
