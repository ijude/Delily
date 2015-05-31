package com.sh.dilily;

public abstract class Dilily {

	public static final String SERVER = "http://dilili.sinaapp.com";
	public static final String SALT = "dilili@2015";
	
//	public static final String KEY = "wvPYIR2AjdVD+cQ1Bui+nw==";
//	public static final String PLATFORM = "android";
//	public static final String CHANNEL = "";
	
	//以下为数据库配置表的预定义key值
	public static final String KEY_SERIALID = "serialId";		//服务端的授权码, 即一个时间戳20150531.....
	public static final String KEY_TOKEN = "userEncode";		//登录后返回的授权码
	public static final String KEY_USERID = "userId";			//登录后返回的当前用户ID
	public static final String KEY_USERNAME = "userName";		//当前用户名
	public static final String KEY_USERTYPE = "userType";		//用户类型
}
