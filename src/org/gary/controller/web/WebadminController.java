package org.gary.controller.web;

import java.util.HashMap;
import java.util.Map;

import org.gary.interceptor.UserInterceptor;
import org.gary.model.User;
import org.gary.utils.MD5;

import com.google.gson.Gson;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;



/**
 * 后台管理
 * 
 */
@ControllerBind(controllerKey = "/webadmin")
public class WebadminController extends WebBaseController {
	
	@Before(UserInterceptor.class)
	public void index() {
		User user=this.getSessionAttr("user");
		if(user==null){
			this.render("/admin/login.html");
		}else{
			this.render("/admin/main.html");
		}
	}
	public void loginDialog(){
		this.render("/admin/login.html");
	}
	public void login(){
		String username=this.getPara("username");
		String pwd=this.getPara("pwd");
		String code=this.getPara("code");
		String check= this.getSessionAttr("check");
		Map<String,Object> json=new HashMap<String,Object>();
		Gson gson=new Gson();
		if(username==null||"".equals(username.trim())||pwd==null||"".equals(pwd)||code==null||"".equals(code)){
			json.put("stat",200);
			json.put("msg", "信息填写不全！");
		}else
		if(check==null||"".equals(check)){
			json.put("stat",200);
			json.put("msg", "验证码超时");
		}else if(!check.equals(code)){
			json.put("stat",200);
			json.put("msg", "验证码错误");
		}else{
			pwd=MD5.getMD5ofStr(pwd);
			User user= User.dao.findFirst("select * from user where login_name=? and password=?", new Object[]{username,pwd});
			if(user!=null){
				this.setSessionAttr("user", user);
				json.put("stat",100);
			}else{
				json.put("stat",200);
				json.put("msg", "用户名或密码错误");
			}
		}
		renderText(gson.toJson(json));
	}
	public void logout(){
		this.removeSessionAttr("user");
		this.redirect(this.getroot()+"/webadmin");
	}
}
