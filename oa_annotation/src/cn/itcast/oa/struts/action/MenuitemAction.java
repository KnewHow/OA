package cn.itcast.oa.struts.action;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.domain.Menuitem;
import cn.itcast.oa.service.MenuitemService;
import cn.itcast.oa.struts.action.base.BaseAction;

@Controller("menuitemAction")
@Scope("prototype")
public class MenuitemAction extends BaseAction<Menuitem>{

	@Resource(name="menuitemService")
	private MenuitemService menuitemService;
	
	private Collection<Menuitem> menuitemList;
	
	//使用@JSON(serialize=false)，可以过滤get方法，把json值返回客户端
	@JSON(serialize=false)
	public String getAllMenuitems(){
		this.menuitemList = this.menuitemService.getAllMenuitems();
		return SUCCESS;
	}

	public Collection<Menuitem> getMenuitemList() {
		return menuitemList;
	}
	
	public String showMenuitemsByPid(){
		this.menuitemList = this.menuitemService.getMenuitemsByPid(this.getModel().getPid());
		return SUCCESS;
	}
	
	public String showMenuitemsByUser(){
		this.menuitemList = this.menuitemService.getMenuitemsByUsers();
		return SUCCESS;
	}
	
	
}
