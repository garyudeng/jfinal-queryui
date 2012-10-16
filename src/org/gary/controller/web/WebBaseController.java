package org.gary.controller.web;

import com.jfinal.core.Controller;

/**
 * 基础Controller
 * 重写了render 直接转向到/WEB-INF/pages目录下的文件
 */
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
