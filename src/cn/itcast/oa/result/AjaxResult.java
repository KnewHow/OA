package cn.itcast.oa.result;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class AjaxResult implements Result {

	public void execute(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		String message = ActionContext.getContext().getValueStack().peek().toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		System.out.println(message);
		response.getWriter().print(message);
	}

}
