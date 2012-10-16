package org.gary.model;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.tablebind.TableBind;

@TableBind(tableName="user")
@SuppressWarnings("serial")
public class User extends Model<User>{
	public static final User dao=new User();
}
