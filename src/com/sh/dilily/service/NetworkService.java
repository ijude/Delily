package com.sh.dilily.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.sh.dilily.data.Post;
import com.sh.dilily.net.DililyNetworkHelper;

public class NetworkService extends IntentService {
	private static final String NS = "NetworkService";
	
	public static final String KEY_URL = "url";
	public static final String KEY_POST = "post";
	public static final String KEY_REQUEST = "request";
	public static final String KEY_EXTRA = "extra";
	public static final String KEY_RESULT = "result";
	
	public static final String ACTION_NETWORK_RESULT = "com.sh.dilily.network.RESULT";

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
		String result = null;
		DililyNetworkHelper net = DililyNetworkHelper.get(getBaseContext());
		String url = intent.getStringExtra(KEY_URL);
		if (url != null) {
			result = net.doGet(url);
		} else {
			Post post = intent.getParcelableExtra(KEY_POST);
			if (post == null)
				return;
			result = net.doPost(post);
		}
		sendResult(intent, result);
	}
	
	private void sendResult(Intent intent, String result) {
		int request = intent.getIntExtra(KEY_REQUEST, 0);
		String extra = intent.getStringExtra(KEY_EXTRA);
		Intent r = new Intent(ACTION_NETWORK_RESULT);
		r.putExtra(KEY_REQUEST, request);
		r.putExtra(KEY_EXTRA, extra);
		r.putExtra(KEY_RESULT, result);
		sendBroadcast(r);
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
