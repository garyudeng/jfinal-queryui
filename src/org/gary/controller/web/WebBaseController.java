package org.gary.controller.web;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;

/**
 * 基础Controller
 * 重写了render 直接转向到/WEB-INF/pages目录下的文件
 */
@ControllerBind(controllerKey="",ignore=true)
public class WebBaseController extends Controller {
	protected static String root;
	protected int pageSize=20;
	public String getroot(){
		return root;
	}
	@Override
	public void render(String view) {
		if(root==null)
			root=this.getRequest().getContextPath();
		this.setAttr("root",root);
		super.render("/WEB-INF/views/"+view);
	}
}
