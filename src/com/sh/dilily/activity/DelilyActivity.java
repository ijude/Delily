package com.sh.dilily.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.sh.dilily.R;
import com.sh.dilily.data.Post;
import com.sh.dilily.db.DililyDatabaseHelper;
import com.sh.dilily.net.DililyNetworkHelper;
import com.sh.dilily.net.NetworkReceiver;
import com.sh.dilily.service.NetworkService;

/**
 * 标准的嘀哩哩Activity
 * */
public class DelilyActivity extends BaseActivity implements View.OnClickListener {
	
	private NetworkReceiver networkReceiver;
	
	public DelilyActivity() {
	}

	@Override
	protected void onResume() {
		super.onResume();
		View titleBar = findViewById(R.id.title_bar);
		if (titleBar != null) {
			int[] ids = {R.id.title_left, R.id.title_right};
			setClickListener(ids, this);
		}
	}
	
	protected DililyNetworkHelper getNetwork() {
		return DililyNetworkHelper.get(getApplicationContext());
	}
	
	protected DililyDatabaseHelper getDatabase() {
		return DililyDatabaseHelper.get(getApplicationContext());
	}
	
	protected int getConfigurationInt(String key, int def) {
		return getDatabase().getConfigurationInt(key, def);
	}
	protected String getConfiguration(String key) {
		return getDatabase().getConfiguration(key);
	}
	
	protected void setConfiguration(String key, String value) {
		getDatabase().setConfiguration(key, value);
	}
	
	protected void startActivity(Class<? extends Activity> clz) {
		startActivity(new Intent(getBaseContext(), clz));
	}
	
	protected void request(String url, int requestCode) {
		request(url, requestCode, null);
	}
	protected void request(String url, int requestCode, String extra) {
		if (networkReceiver == null)
			error("需要先注册接收服务");
		Intent intent = new Intent(getBaseContext(), NetworkService.class);
		intent.putExtra(NetworkService.KEY_URL, url);
		intent.putExtra(NetworkService.KEY_REQUEST, requestCode);
		if (extra != null) {
			intent.putExtra(NetworkService.KEY_EXTRA, extra);
		}
		startService(intent);
	}
	
	protected void request(Post post, int requestCode) {
		request(post, requestCode, null);
	}
	protected void request(Post post, int requestCode, String extra) {
		if (networkReceiver == null)
			error("需要先注册接收服务");
		Intent intent = new Intent(getBaseContext(), NetworkService.class);
		intent.putExtra(NetworkService.KEY_POST, post);
		intent.putExtra(NetworkService.KEY_REQUEST, requestCode);
		if (extra != null) {
			intent.putExtra(NetworkService.KEY_EXTRA, extra);
		}
		startService(intent);
	}
	
	protected void startNetworkService() {
		Intent intent = new Intent(getBaseContext(), NetworkService.class);
		startService(intent);
	}
	
	protected void registerNetworkReceiver(NetworkReceiver receiver) {
		if (networkReceiver == null) {
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction(NetworkService.ACTION_NETWORK_RESULT);  
	        registerReceiver(receiver, intentFilter);
	        networkReceiver = receiver;
		} else {
			error("已注册");
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (networkReceiver != null) {
			unregisterReceiver(networkReceiver);
			networkReceiver = null;
		}
	}
	
	
	protected void error() {
		throw new RuntimeException();
	}
	protected void error(String msg) {
		throw new RuntimeException(msg);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.title_left:
			finish();
			break;
		case R.id.title_right:
			break;
		}
	}
}
