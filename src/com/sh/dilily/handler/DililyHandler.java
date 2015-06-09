package com.sh.dilily.handler;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.sh.dilily.activity.DelilyActivity;
import com.sh.dilily.net.DililyNetworkHelper;

public abstract class DililyHandler<T extends DelilyActivity> extends Handler {
	
	private WeakReference<T> activity;
	
	public DililyHandler(T activity) {
		this.activity = new WeakReference<T>(activity);
	}
	
	public abstract boolean handleMessage(T activity, int what);
	
	@Override
	public final void handleMessage(Message msg) {
		if (activity != null) {
			T a = activity.get();
			if (a != null && handleMessage(a, msg.what))
				return;
		}
		super.handleMessage(msg);
	}
	
	/**
	 * may null
	 * */
	public  T getActivity() {
		return activity == null ? null : activity.get();
	}
	
	public void release() {
		activity = null;
	}
	
	public DililyNetworkHelper getNetwork() {
		T activity = getActivity();
		Context context = null;
		if (activity != null)
			context = activity.getApplicationContext();
		return DililyNetworkHelper.get(context);
	}
}
