package cn.itcast.oa.dao.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import cn.itcast.oa.dao.PrivilegeDao;
import cn.itcast.oa.dao.base.impl.BaseDaoImpl;
import cn.itcast.oa.domain.Menuitem;

@Repository("privilegeDao")
public class PrivilegeDaoImpl extends BaseDaoImpl<Menuitem> implements PrivilegeDao<Menuitem>{

	@Override
	public Collection<Menuitem> getAllEntries() {
		//使用迫切外连接,提高sql的效率
		List<Menuitem> list =this.hibernateTemplate.find("from Menuitem");
		return list;
	}
	
	public Set<Menuitem> getPrivilegesByIds(Long[] ids){
		StringBuilder sb = new StringBuilder("from Menuitem where mid in(");
		int length = ids.length;
		for(int i=0;i<length;i++){
			if(i<length-1){
				sb.append(ids[i]+",");
			}else{
				sb.append(ids[i]);
			}
		}
		sb.append(")");
		List<Menuitem> menuitemList =  this.hibernateTemplate.find(sb.toString());
		return new HashSet<Menuitem>(menuitemList);
	}

	public Collection<Menuitem> getPrivilegesByUid(Long uid) {
		List<Menuitem> list= this.hibernateTemplate.find("from Menuitem m inner join fetch m.users u where u.uid=?",uid);
		return new HashSet<Menuitem>(list);
	}
}
