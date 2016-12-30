package cn.itcast.oa.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import cn.itcast.oa.dao.PostDao;
import cn.itcast.oa.dao.base.impl.BaseDaoImpl;
import cn.itcast.oa.domain.Post;

@Repository("postDao")
public class PostDaoImpl extends BaseDaoImpl<Post> implements PostDao<Post> {

	public Set<Post> getPostsByIds(Long[] pids) {
		StringBuilder sb = new StringBuilder("from Post where pid in(");
		for(int i=0;i<pids.length;i++){
			if(i<pids.length-1){
				sb.append(pids[i]+",");
			}else{
				sb.append(pids[i]);
			}
		}
		sb.append(")");
		List<Post> posts = this.getHibernateTemplate().find(sb.toString());
		
		return new HashSet<Post>(posts);
	}

}
