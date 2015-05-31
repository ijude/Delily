package com.sh.dilily.service;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 消息服务，自动到服务器获取新消息，仅在消息界面和聊天界面
 * */
public class PushService extends Service implements Runnable {
	private static final int INTERVAL = 1000;
//	private static final String URL = "http://dilili.sinaapp.com/";
	private Thread thread;
	private boolean running;
	private HttpClient client;		//用于取消连接
	
	@Override
	public void onCreate() {
		super.onCreate();
		running = false;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (running && client != null) {
			client.getConnectionManager().shutdown();
			client = null;
		}
		thread = null;
	}

	@Override
	public void run() {
		running = true;
		client = new DefaultHttpClient();
		
		running = false;
	}
}
