package cn.itcast.oa.struts.action;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Post;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.service.PostService;
import cn.itcast.oa.service.UserService;
import cn.itcast.oa.struts.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="postService")
	private PostService postService;
	
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	private Long did;
	
	private Long[] pids;
	
	private String message;

	public String getAllUsers(){
		Collection<User> users = this.userService.getAllUsers();
		ActionContext.getContext().getValueStack().push(users);
		return listAction;
	}
	
	public String addUI(){
		Collection<Post> posts = this.postService.getAllPosts();
		Collection<Department> departments = this.departmentService.getAllDepartments();
		ActionContext.getContext().put("posts", posts);
		ActionContext.getContext().put("departments", departments);
		return addUI;
	}
	
	public String add(){
		Department department = this.departmentService.getDepartmentById(this.did);
		Set<Post> posts = this.postService.getPostsByIds(this.pids);
		User user =  new User();
		BeanUtils.copyProperties(this.getModel(), user);
		user.setDepartment(department);
		user.setPosts(posts);
		this.userService.saveUser(user);
		return actionToAction;
	}
	
	/**
	 * 使用ajax异步校验用户名
	 * @return
	 * @throws Exception 
	 */
	public String ajaxCheckUser() throws Exception{
		int a=1/0;
		User user = this.userService.getUserByName(this.getModel().getUsername());
		//String message = null;
		if(user==null){
			this.message="该用户名可以使用";
		}else{
			this.message="该用户名已经存在";
		}
		//ActionContext.getContext().getValueStack().push(message);
		return SUCCESS;
	}
	
	
	public String getMessage() {
		return message;
	}
	public Long getDid() {
		return did;
	}

	public void setDid(Long did) {
		this.did = did;
	}

	public Long[] getPids() {
		return pids;
	}

	public void setPids(Long[] pids) {
		this.pids = pids;
	}
	
	
	
}
