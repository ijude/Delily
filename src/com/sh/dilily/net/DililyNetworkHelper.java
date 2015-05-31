package com.sh.dilily.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.res.Resources;

import com.sh.dilily.Dilily;
import com.sh.dilily.R;
import com.sh.dilily.data.Post;
import com.sh.dilily.db.DililyDatabaseHelper;
import com.sh.dilyly.util.NetUtils;
import com.sh.dilyly.util.Utils;

public final class DililyNetworkHelper {
	private static final String ENCODING = "UTF-8";
	private static final String FORMAT = "json";
	
	private Context context;
	
	private static String appKey;
	private static String appVersion;
	private static String channel;
	
	private static DililyNetworkHelper helper;
	
	public DililyNetworkHelper(Context context) {
		this.context = context;
	}
	
	public static synchronized DililyNetworkHelper get(Context context) {
		if (helper == null && context != null)
			helper = new DililyNetworkHelper(context);
		return helper;
	}
	
	/**
	 * 参数转为map，自动增加"系统参数"
	 * */
	private Map<String, String> buildParams(String... params) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("platform", "android");
		map.put("format", FORMAT);
		map.put("t", String.valueOf(new Date().getTime()));
		if (appKey == null) {
			Resources r = context.getResources();
			appKey = r.getString(R.string.app_key);			//Dilily.KEY
			appVersion = r.getString(R.string.app_version);
			channel = r.getString(R.string.channel);
		}
		map.put("key", appKey);
		map.put("version", appVersion);
		map.put("channel", channel);
		String serialId = DililyDatabaseHelper.get(context).getConfiguration("serialId");
		map.put("serialId", serialId == null ? "" : serialId);
		int len = params.length;
		for (int i=0; i<len; i+=2) {
			map.put(params[i], params[i+1]);
		}
		String encryptCode = calcEncryptCode(map);
		map.put("encryptCode", encryptCode);
		return map;
	}
	
	/**
	 * 计算 校验码
	 * */
	private String calcEncryptCode(Map<String, String> params) {
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(params.keySet());
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (String key : keys) {
			sb.append(key).append("=").append(params.get(key)).append("&");
		}
		String query = sb.substring(0, sb.length()-1);
		return Utils.md5(query, Dilily.SALT);
	}
	
	/**
	 * Get请求
	 * 参数转为a=1&b=2格式
	 * */
	private String toString(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		try {
			for (Map.Entry<String, String> e : params.entrySet()) {
				String value = URLEncoder.encode(e.getValue(), ENCODING);
				sb.append(e.getKey()).append("=").append(value).append("&");
			}
		} catch(UnsupportedEncodingException e) {
		}
		return sb.substring(0, sb.length()-1);
	}
	
	private String toUrl(String uri, String... params) {
		Map<String, String> map = buildParams(params);
		String query = toString(map);
		return Dilily.SERVER + uri + "?" + query;
	}
	
	/**
	 * Post请求
	 * 参数转存在List列表
	 * */
	private List<NameValuePair> toPairs(Map<String, String> params) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> e : params.entrySet()) {
			pairs.add(new BasicNameValuePair(e.getKey(), e.getValue()));
		}
		return pairs;
	}
	
	private Post toPost(String uri, String... params) {
		Map<String, String> map = buildParams(params);
		List<NameValuePair> pairs = toPairs(map);
		return new Post(Dilily.SERVER + uri, pairs);
	}
	
	/**
	 * 获取服务端序列号
	 * */
	public String getSerialIdUrl() {
		String uri = "/api/serial/assign.xhtml";
		String deviceId = Utils.getDeviceId(context);
//		String deviceToken = "";		//iOS推送token ?
		return toUrl(uri, "deviceId", deviceId);
	}
	
	/**
	 * 获取服务端系统时间
	 * */
	public String getTimestampUrl() {
		String uri = "/api/sys/timestamp.xhtml";
		return toUrl(uri);
	}
	
	/**
	 * 获取版本升级信息
	 * */
	public String getUpgradeUrl() {
		String uri = "/api/sys/upgrade.xhtml";
		return toUrl(uri);
	}
	
	/**
	 * 上传推送token(iOS?)
	 * */
	public Post getUploadTokenPost(String token) {
		String uri = "/api/serial/uploadDeviceToken.xhtml";
		String deviceId = Utils.getDeviceId(context);
		return toPost(uri, "deviceId", deviceId, "deviceToken", token);
	}
	
	/**
	 * 上传头像
	 * TODO 待确认
	 * */
	public Post getUploadImagePost() {
		String uri = "/api/sys/upload.xhtml";
		return toPost(uri);
	}
	
	/**
	 * 注册
	 * @param username 用户名
	 * @param password 密码
	 * @param teacherOrStudent true:老师、false:学生 
	 * */
	public String getRegisterUrl(String username, String password, boolean teacherOrStudent) {
		String uri = "/api/user/register.xhtml";
		password = Utils.md5(password, Dilily.SALT);
		return toUrl(uri, "username", username, "password", password, "type",teacherOrStudent ? "2" : "1");
	}
	
	/**
	 * 登录
	 * */
	public String getLoginUrl(String username, String password) {
		String uri = "/api/user/login.xhtml";
		password = Utils.md5(password, Dilily.SALT);
		return toUrl(uri, "username", username, "password", password);
	}
	
	/**
	 * 上传当前位置信息
	 * */
	public Post getUploadLocationPost(String userEncode, double longitude, double latitude) {
		String uri = "/api/auth/user/uploadLocation.xhtml";
		return toPost(uri, "userEncode", userEncode, "longitude", String.valueOf(longitude), "latitude", String.valueOf(latitude));
	}
	
	/**
	 * 获取当前用户信息
	 * */
	public String getUserUrl(String userEncode) {
		String uri = "/api/auth/user/detail.xhtml";
		return toUrl(uri, "userEncode", userEncode);
	}
	
	/**
	 * 修改用户信息
	 * TODO 未确认
	 * */
	public Post getEditUserPost(String userEncode) {
		String uri = "/api/auth/teacher/edit.xhtml";
		String[] params = {"userEncode", userEncode};
		return toPost(uri, params);
	}
	
	/**
	 * 获取/过滤老师列表
	 * */
	public Post getFilterTeachersPost(String userEncode, String districtCode, int gender, int sortType, String keyword, int page, int pageSize) {
		String uri = "/api/auth/teacher/list.xhtml";
		String[] params = {"userEncode", userEncode, "districtCode", "0", "gender", String.valueOf(gender), "sortType", "0", "keyword", keyword, "page", String.valueOf(page), "pagesize", String.valueOf(pageSize)};
		return toPost(uri, params);
	}
	
	/** 发表评论 */
	public Post getCommentPost(String userEncode, String teacherId, int grade, String content) {
		String uri = "/api/auth/comment/add.xhtml";
		String[] params = {"userEncode", userEncode, "teacherId", teacherId, "grade", String.valueOf(grade), "content", content};
		return toPost(uri, params);
	}
	
	/**
	 * 评论列表
	 * */
	public String getListCommentsUrl(String userEncode, String teacherId, int page, int pagesize) {
		String uri = "/api/auth/comment/list.xhtml";
		String[] params = {"userEncode", userEncode, "teacherId", teacherId, "page", String.valueOf(page), "pagesize", String.valueOf(pagesize)};
		return toUrl(uri, params);
	}
	
	/**
	 * 发送留言/消息
	 * TODO 接口有错
	 * */
	public Post getSendMessagePost(String userEncode, String teacherId, String message) {
		String uri = "/api/auth/note/add.xhtml";
		String[] params = {"userEncode", userEncode, "teacherId", teacherId, "message", message};
		return toPost(uri, params);
	}
	
	/**
	 * 获取留言列表
	 * */
	public String getListMessagesUrl(String userEncode, int page, int pagesize) {
		String uri = "/api/auth/note/list.xhtml";
		String[] params = {"userEncode", userEncode, "page", String.valueOf(page), "pagesize", String.valueOf(pagesize)};
		return toUrl(uri, params);
	}
	
	/**
	 * 获取留言信息
	 * */
	public String getMessageDetailUrl(String userEncode, String messageId) {
		String uri = "/api/auth/note/detail.xhtml";
		String[] params = {"userEncode", userEncode, "noteId", messageId};
		return toUrl(uri, params);
	}
	
	/**
	 * 获取当前支持的区域
	 * */
	public String getFetchRegionsUrl(String userEncode) {
		String uri = "/api/auth/district/list.xhtml";
		return toUrl(uri, "userEncode", userEncode);
	}
	
	public String doGet(String url) {
		return NetUtils.get(url);
	}
	
	public String doPost(Post post) {
		return NetUtils.post(post.getUrl(), post.getPairs());
	}
	
	/**
	 * 解析 data={}
	 * */
	public static Map<String, Object> parseResult(String result) {
		if (result == null || result.length() == 0)
			return null;
		result = result.trim();
		if (result.charAt(0) != '{') {
			int p = result.indexOf('=');
			result = result.substring(p + 1);
		}
		try {
			JSONTokener parser = new JSONTokener(result.trim());
/*
			String key = (String)parser.nextValue();
			System.out.println(key);
			Object o = parser.nextValue();
*/
			JSONObject object = (JSONObject) parser.nextValue();
			return toMap(object);
		} catch (JSONException e) {
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isSuccess(Map<String, Object> object) {
		if (object == null)
			return false;
		try {
			String code = (String)object.get("code");
			return code != null && "0000".equals(code.trim());
		} catch (ClassCastException e) {
		} catch (Exception e) {
		}
		return false;
	}
	
	public static Map<String, Object> toMap(JSONObject jo) {
		if (jo == null)
			return null;
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			Iterator<String> it = jo.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object o = jo.get(key);
				if (o == null)
					continue;
				if (o instanceof JSONObject) {
					map.put(key, toMap((JSONObject)o));
				} else {
					map.put(key, o);
				}
			}
			return map;
		} catch(JSONException e) {
		}
		return null;
	}
	
}
