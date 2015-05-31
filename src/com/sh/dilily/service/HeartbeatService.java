package com.sh.dilily.service;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class HeartbeatService extends Service implements Runnable {
	private Thread mThread;
	public int count = 0;
	private boolean isTip = true;
	private static String mRestMsg;
	private static String KEY_REST_MSG = "KEY_REST_MSG";

	@Override
	public void run() {
		while (true) {
			try {
				if (count > 1) {
					Log.i("@qi", "offline");
					count = 1;
					if (isTip) {
						ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
						List<RunningTaskInfo> list = am.getRunningTasks(3);
						for (RunningTaskInfo info : list) {
							if (info.topActivity.getPackageName().equals("org.yhn.demo")) {
								// 通知应用，显示提示“连接不到服务器”
								Intent intent = new Intent("org.yhn.demo");
								intent.putExtra("msg", true);
								sendBroadcast(intent);
								break;
							}
						}

						isTip = false;
					}
				}
				if (mRestMsg != "" && mRestMsg != null) {
					// 向服务器发送心跳包
					sendHeartbeatPackage(mRestMsg);
					count += 1;
				}

				Thread.sleep(1000 * 3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendHeartbeatPackage(String msg) {
		HttpGet httpGet = new HttpGet(msg);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// 发送请求
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (httpResponse == null) {
			return;
		}

		// 处理返回结果
		final int responseCode = httpResponse.getStatusLine().getStatusCode();
		if (responseCode == HttpStatus.SC_OK) {
			// 只要服务器有回应就OK
			count = 0;
			isTip = true;
		} else {
			Log.i("@qi", "responseCode " + responseCode);
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void onStart(Intent intent, int startId) {
		Log.i("@qi", "service onStart");
		// 从本地读取服务器的URL，如果没有就用传进来的URL
		mRestMsg = getRestMsg();
		if (mRestMsg == null || mRestMsg == "") {
			mRestMsg = intent.getExtras().getString("url");
		}
		setRestMsg(mRestMsg);

		mThread = new Thread(this);
		mThread.start();
		count = 0;

		super.onStart(intent, startId);
	}

	public String getRestMsg() {
		SharedPreferences prefer = getSharedPreferences("settings.data",
				Context.MODE_PRIVATE);
		Log.i("@qi", "getRestMsg() " + prefer.getString(KEY_REST_MSG, ""));
		return prefer.getString(KEY_REST_MSG, "");
	}

	public void setRestMsg(String restMsg) {
		SharedPreferences prefer = getSharedPreferences("settings.data",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefer.edit();
		editor.putString(KEY_REST_MSG, restMsg);
		editor.commit();
	}

}
