package cn.itcast.oa.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import cn.itcast.oa.domain.Post;

public interface PostService {

	public Collection<Post> getAllPosts();
	public void savePost(Post post);
	public void updatePost(Post post);
	public void deletePostById(Serializable id);
	public Post getPostById(Serializable id);
	public Set<Post> getPostsByIds(Long[] pids);
}
