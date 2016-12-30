package cn.itcast.oa.utils;

import org.apache.struts2.ServletActionContext;

import cn.itcast.oa.domain.User;

public class OAUtils {

	/**
	 * 把一个string类型的数组转换Long类型数组
	 * @param str
	 * @return
	 */
	public static Long[] stringToLongArray(String str){
		String[] sArr = str.split(",");
		int length = sArr.length;
		Long [] lArr = new Long[length];
		for(int i=0;i<length;i++){
			lArr[i] = Long.parseLong(sArr[i]);
		}
		return lArr;
	}
	
	public static User getUserFromSession(){
		return (User) ServletActionContext.getRequest().getSession().getAttribute("session_user");
	}
	
	public static void putUserToSession(User user){
		ServletActionContext.getRequest().getSession().setAttribute("session_user", user);
	}
}
