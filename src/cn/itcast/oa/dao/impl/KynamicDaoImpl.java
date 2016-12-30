package cn.itcast.oa.dao.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.oa.dao.KynamicDao;
import cn.itcast.oa.dao.base.impl.BaseDaoImpl;
import cn.itcast.oa.domain.Kynamic;
import cn.itcast.oa.domain.Version;

@Repository("kynamicDao")
public class KynamicDaoImpl extends BaseDaoImpl<Kynamic> implements KynamicDao<Kynamic>{

	public Kynamic getKynamicByName(String name,Long pid) {
		// TODO Auto-generated method stub
		Object [] params = new Object []{name};
		List<Kynamic> kynamicList = this.hibernateTemplate.find("from Kynamic where name=?",params);
		if(kynamicList.size()==0){
			return null;
		}else{
			return kynamicList.get(0);
		}
		
	}

	public Collection<Kynamic> getSiblingNodes(Long kid) {
		// TODO Auto-generated method stub
		/*
		 SELECT *FROM kynamic
		WHERE pid=(SELECT pid FROM kynamic WHERE kid=5);
		 */
		StringBuilder sb = new StringBuilder("from Kynamic");
		sb.append(" where pid=(");
		sb.append("select pid from Kynamic where kid=?)");
		return this.hibernateTemplate.find(sb.toString(),kid);
	}

	public Kynamic getParentNode(Long kid) {
		// TODO Auto-generated method stub
		/*
		 * SELECT *FROM kynamic
		WHERE kid=(SELECT pid FROM kynamic WHERE kid=5);
		 */
		StringBuilder sb = new StringBuilder("from Kynamic");
		sb.append(" where kid=(");
		sb.append("select pid from Kynamic where kid=?)");
		List<Kynamic> kynamicList = this.hibernateTemplate.find(sb.toString(),kid);
		return kynamicList.get(0);
	}

	public Collection<Version> getVersionsByKid(Long kid) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Version v where v.kynamic.kid=?",kid);
	}

}
