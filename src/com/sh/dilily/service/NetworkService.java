package com.sh.dilily.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.sh.dilily.data.Post;

public class NetworkService extends IntentService {
	private static final String NS = "NetworkService";
	
	public static final String KEY_URL = "url";
	public static final String KEY_POST = "post";

	public NetworkService() {
		super(NS);
	}
	
	public NetworkService(String name) {
		super(name);
		Log.i(NS, "NEW");
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(NS, "create");
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.i(NS, "start");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(NS, "handlerIntent");
		String url = intent.getStringExtra(KEY_URL);
		String result = null;
		if (url != null) {
			
		} else {
			Post post = intent.getParcelableExtra(KEY_POST);
			if (post == null)
				return;
			
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(NS, "destroy");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(NS, "bind");
		return super.onBind(intent);
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(NS, "unbind");
		return super.onUnbind(intent);
	}
}
