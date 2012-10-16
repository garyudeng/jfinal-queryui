package org.gary.config;


import org.gary.handler.SessionHandler;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.route.AutoControllerRegist;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.plugin.tablebind.TableNameStyle;

public class SystemStart extends JFinalConfig{

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		loadPropertyFile("a_little_config.txt");				// 加载少量必要配置，随后可用getProperty(...)获取值
		me.setDevMode(getPropertyToBoolean("devMode", true));
		//me.setViewType(ViewType.JSP); 							// 设置视图类型为Jsp，否则默认为FreeMarker
		me.setError404View("/WEB-INF/views/404.html");
		me.setError500View("/WEB-INF/views/500.html");
		
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		//MyRoutesUtil.add(me);
		AutoControllerRegist.regist(me);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		DruidPlugin druidPlugin = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password"));
		WallFilter wall = new WallFilter();
		wall.setDbType("mysql");
		druidPlugin.addFilter(wall);
		druidPlugin.addFilter(new StatFilter());
		me.add(druidPlugin);
		
		//添加自动绑定model与表插件
		AutoTableBindPlugin autoTableBindPlugin = new AutoTableBindPlugin(druidPlugin, TableNameStyle.LOWER);
		autoTableBindPlugin.setShowSql(true);
		me.add(autoTableBindPlugin);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		//me.add(new UserInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new SessionHandler());
	}
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
