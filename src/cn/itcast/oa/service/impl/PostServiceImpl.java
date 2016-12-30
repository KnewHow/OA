package cn.itcast.oa.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.dao.PostDao;
import cn.itcast.oa.domain.Post;
import cn.itcast.oa.service.PostService;

@Service("postService")
public class PostServiceImpl implements PostService {

	@Resource(name="postDao")
	private PostDao postDao;
	
	public Collection<Post> getAllPosts() {
		return this.postDao.getAllEntries();
		
	}
	
	@Transactional(readOnly=false)
	public void savePost(Post post) {
		// TODO Auto-generated method stub
		this.postDao.saveEntry(post);
	}

	@Transactional(readOnly=false)
	public void updatePost(Post post) {
		// TODO Auto-generated method stub
		this.postDao.updateEntry(post);
	}

	@Transactional(readOnly=false)
	public void deletePostById(Serializable id) {
		// TODO Auto-generated method stub
		this.postDao.deleteEntryById(id);
	}

	public Post getPostById(Serializable id) {
		// TODO Auto-generated method stub
		return (Post) this.postDao.getEntryById(id);
	}

	public Set<Post> getPostsByIds(Long[] pids) {
		return this.postDao.getPostsByIds(pids);
	}

}
