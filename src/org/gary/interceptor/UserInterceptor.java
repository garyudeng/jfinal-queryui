package org.gary.interceptor;


import org.gary.model.User;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
/**
 * 管理用户后台登录状态及权限验证拦截器
 * @author gary
 */
public class UserInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Controller ctrl=ai.getController();
		User user= (User)ctrl.getSessionAttr("user");
		if(user==null){
			ctrl.redirect(ctrl.getRequest().getContextPath()+"/index");
		}else
		ai.invoke();//注意 一定要执行此方法
	}
}