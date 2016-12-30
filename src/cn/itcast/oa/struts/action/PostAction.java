package cn.itcast.oa.struts.action;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.regexp.internal.recompile;

import cn.itcast.oa.domain.Post;
import cn.itcast.oa.service.PostService;
import cn.itcast.oa.struts.action.base.BaseAction;

@Controller("postAction")
@Scope("prototype")
public class PostAction extends BaseAction<Post>{

	@Resource(name="postService")
	private PostService postService;
	
	public String getAllPosts(){
		Collection<Post> posts = this.postService.getAllPosts();
		ActionContext.getContext().getValueStack().push(posts);
		return listAction;
	}
	
	public String addUI(){
		return ADDUI;
	}
	
	public String add(){
		Post post = new Post();
		BeanUtils.copyProperties(this.getModel(), post);
		this.postService.savePost(post);
		return actionToAction;
	}
	
	public String updateUI(){
		Post post = this.postService.getPostById(this.getModel().getPid());
		ActionContext.getContext().getValueStack().push(post);
		return updateUI;
	}
	
	public String update(){
		Post post = this.postService.getPostById(this.getModel().getPid());
		BeanUtils.copyProperties(this.getModel(), post);
		this.postService.updatePost(post);
		return actionToAction;
	}
	
	public String delete(){
		this.postService.deletePostById(this.getModel().getPid());
		return actionToAction;
	}
	
}
