package cn.itcast.oa.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.dao.KynamicDao;
import cn.itcast.oa.domain.Kynamic;
import cn.itcast.oa.domain.Version;
import cn.itcast.oa.service.KynamicService;

@Service("kynamicService")
public class KynamicServiceImpl implements KynamicService{
	
	@Resource(name="kynamicDao")
	private KynamicDao kynamicDao;

	public Collection<Kynamic> getAllKynamics() {
		// TODO Auto-generated method stub
		return this.kynamicDao.getAllEntries() ;
	}

	@Transactional(readOnly=false)
	public void saveKynamic(Kynamic kynamic) {
		// TODO Auto-generated method stub
		this.kynamicDao.saveEntry(kynamic);
	}

	public Boolean isKynamicExistByName(String name,Long pid) {
		Kynamic kynamic = this.kynamicDao.getKynamicByName(name,pid);
		if(kynamic==null){//不存在
			return false;
		}else{//存在
			return true;
		}
	}
	
	@Transactional(readOnly=false)
	public void deleteKynamicById(Long kid) {
		// TODO Auto-generated method stub
		this.kynamicDao.deleteEntryById(kid);
	}

	public Collection<Kynamic> getSiblingNodes(Long kid) {
		// TODO Auto-generated method stub
		return this.kynamicDao.getSiblingNodes(kid);
	}

	public Kynamic getParentNode(Long kid) {
		// TODO Auto-generated method stub
		return this.kynamicDao.getParentNode(kid);
	}

	public Kynamic getKynamicById(Long id) {
		// TODO Auto-generated method stub
		return (Kynamic) this.kynamicDao.getEntryById(id);
	}

	@Transactional(readOnly=false)
	public void updateKynamic(Kynamic kynamic) {
		// TODO Auto-generated method stub
		this.kynamicDao.updateEntry(kynamic);
	}

	public Collection<Version> getVersionsByKid(Long kid) {
		// TODO Auto-generated method stub
		return this.kynamicDao.getVersionsByKid(kid);
	}

}
