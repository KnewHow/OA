package cn.itcast.oa.struts.action;


import com.opensymphony.xwork2.ActionSupport;

public class ForwardAction extends ActionSupport {

	public String top(){
		return "top";
	}
	
	public String left(){
		return "left";
	}
	
	public String right(){
		return "right";
	}
	
	public String bottom(){
		return "bottom";
	}
	
	public String index(){
		return "index";
	}
	
	public String kynamic(){
		return "kynamic";
	}
	
	
}
