package cn.itcast.oa.dao;

import java.util.Set;

import cn.itcast.oa.dao.base.BaseDao;
import cn.itcast.oa.domain.Post;

public interface PostDao<T> extends BaseDao<T> {

	public Set<Post> getPostsByIds(Long[] pids);
}
