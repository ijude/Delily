package com.sh.dilily.net;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sh.dilily.service.NetworkService;

public abstract class NetworkReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		int request = intent.getIntExtra(NetworkService.KEY_REQUEST, 0);
		String extra = intent.getStringExtra(NetworkService.KEY_EXTRA);
		String result = intent.getStringExtra(NetworkService.KEY_RESULT);
		if (onResult(result, request, extra)) {
			return;
		}
		Map<String, Object> o = DililyNetworkHelper.parseResult(result);
		if (DililyNetworkHelper.isSuccess(o)) {
			onSuccess(o, request, extra);
		} else {
			String error = null;
			if (o != null) {
				error = (String)o.get("message");
			}
			onFailure(error, request, extra);
		}
	}
	
	public boolean onResult(String result, int request, String extra) {
		return false;
	}

	public abstract void onSuccess(Map<String, Object> result, int request, String extra);
	
	public abstract void onFailure(String msg, int request, String extra);
	
	public <T> T getValue(Map<String, Object> result, String... keys) {
		for (int i=0; i<keys.length; i++) {
			Object o = result.get(keys[i]);
			if (!(o instanceof Map))
				return (T)o;
		}
		return (T)result;
	}
	
}
