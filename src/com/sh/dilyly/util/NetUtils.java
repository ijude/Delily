package com.sh.dilyly.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import com.sh.dilily.handler.ByteArrayResponseHandler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
	public static final String ENCODING_UTF8 = "UTF-8";
	public static final String ENCODING_GBK = "GBK";
	
	/**
	 * 网络是否可用
	 * */
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info != null) {
				return info.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 判断WIFI网络是否可用 
	 * */
	public boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 判断MOBILE网络/移动蜂窝网络是否可用 
	 * */
	public boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 获取网络连接类型
     * @return one of {@link ConnectivityManager#TYPE_MOBILE}, {@link
     * ConnectivityManager#TYPE_WIFI}, {@link ConnectivityManager#TYPE_WIMAX}, {@link
     * ConnectivityManager#TYPE_ETHERNET},  {@link ConnectivityManager#TYPE_BLUETOOTH}, 
     * or other defined, or -1 if not available
     */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	public static String get(String url) {
		return get(url, ENCODING_UTF8);
	}
	
	public static String get(String url, String encoding) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		StringBuffer result = new StringBuffer();
		InputStreamReader reader = null;
		try {
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream in = response.getEntity().getContent();
				reader = new InputStreamReader(in, encoding);
				char[] buffer = new char[2048];
				int n;
				while ((n = reader.read(buffer)) != -1) {
					result.append(buffer, 0, n);
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		} finally {
			Utils.close(reader);
		}
		return result.toString();
	}
	
	public static String post(String url, List<NameValuePair> params) {
		return post(url, params, ENCODING_UTF8, ENCODING_UTF8);
	}
	
	public static String post(String url, List<NameValuePair> params, String paramEncoding, String resultEncoding) {
		StringBuffer result = new StringBuffer();
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		InputStreamReader reader = null;
		try {
			HttpEntity entity = new UrlEncodedFormEntity(params, paramEncoding);
			request.setEntity(entity);
			
			char[] buffer = new char[2048];
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream in = response.getEntity().getContent();
				reader = new InputStreamReader(in);
				int n;
				while ((n = reader.read(buffer)) != -1) {
					result.append(buffer, 0, n);
				}
				in.close();
			} else {
				return null;
			}
		} catch (Throwable e) {
			return null;
		} finally {
			Utils.close(reader);
		}
		return result.toString();
	}
	
	public static byte[] getBinary(String uri) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(uri);
			ResponseHandler<byte[]> handler = new ByteArrayResponseHandler();
			return client.execute(request, handler);
		} catch (Throwable e) {
		}
		return null;
	}
	
	public static byte[] postBinary(String url, List<NameValuePair> params) {
		return postBinary(url, params, ENCODING_UTF8);
	}
	public static byte[] postBinary(String url, List<NameValuePair> params, String paramEncoding) {
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		ByteArrayBuffer result = new ByteArrayBuffer(1000);
		InputStream in = null;
		try {
			HttpEntity entity = new UrlEncodedFormEntity(params, paramEncoding);
			request.setEntity(entity);
			
			byte[] buffer = new byte[1024];
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				in = response.getEntity().getContent();
				int n;
				while ((n = in.read(buffer)) != -1) {
					result.append(buffer, 0, n);
				}
				in.close();
			} else {
				return null;
			}
		} catch (Throwable e) {
			return null;
		} finally {
			Utils.close(in);
		}
		return result.buffer();
	}
	
}
