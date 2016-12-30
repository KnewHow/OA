package cn.itcast.oa.struts.action;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.domain.Kynamic;
import cn.itcast.oa.domain.Version;
import cn.itcast.oa.service.KynamicService;
import cn.itcast.oa.struts.action.base.BaseAction;

@Controller("kynamicAction")
@Scope("prototype")
public class KynamicAction extends BaseAction<Kynamic>{

	@Resource(name="kynamicService")
	private KynamicService kynamicService;
	
	private Collection<Kynamic> kynamicList;
	
	private Long kid;
	
	private String message;
	
	private Kynamic kynamic;
	
	private Collection<Version> versionList;
	
	public String showAllKynamics(){
		this.kynamicList = this.kynamicService.getAllKynamics();
		return SUCCESS;
	}
	
	public String saveKynamic(){
		Kynamic kynamic = new Kynamic();
		BeanUtils.copyProperties(this.getModel(), kynamic);
		this.kynamicService.saveKynamic(kynamic);
		this.kid = kynamic.getKid();//返回当前的节点ID
		return SUCCESS;
	}
	public Collection<Kynamic> getKynamicList() {
		return kynamicList;
	}
	
	public String isExsitByName(){
		if(!this.kynamicService.isKynamicExistByName(this.getModel().getName(),this.getModel().getPid())){
			this.message="1";//不存在
		}else{
			this.message="0";//存在
		}
		return SUCCESS;
	}
	
	public String deleteNodeById(){
		this.kynamicService.deleteKynamicById(this.getModel().getKid());
		this.message="删除成功";
		return SUCCESS;
	}
	
	public String showSiblingNodes(){
		this.kynamicList = this.kynamicService.getSiblingNodes(this.getModel().getKid());
		return SUCCESS;
	}
	
	public String showParentNode(){
		this.kynamic = this.kynamicService.getParentNode(this.getModel().getKid());
		System.out.println(getModel().getKid());
		System.out.println(this);
		return SUCCESS;
	}
	
	public String updateKynamic(){
		Kynamic kynamic = this.kynamicService.getKynamicById(this.getModel().getKid());
		kynamic.setName(this.getModel().getName());
		this.kynamicService.updateKynamic(kynamic);
		return SUCCESS;
	}
	
	public String showVersionsByKid(){
		this.versionList = this.kynamicService.getVersionsByKid(this.getModel().getKid());
		System.out.println(versionList.isEmpty());
		if(versionList.isEmpty()){//没有版本
			this.message="0";
		}else{
			this.message="1";
		}
		return SUCCESS;
	}

	public Long getKid() {
		return kid;
	}

	public String getMessage() {
		return message;
	}

	public Kynamic getKynamic() {
		return kynamic;
	}

	public Collection<Version> getVersionList() {
		return versionList;
	}
	
	
	
	
	
	
	
	
}
