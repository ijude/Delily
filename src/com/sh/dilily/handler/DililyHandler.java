package com.sh.dilily.handler;

import java.lang.ref.WeakReference;

import com.sh.dilily.activity.DililyActivity;

import android.os.Handler;
import android.os.Message;

public abstract class DililyHandler<T extends DililyActivity> extends Handler {
	
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
	/*
	public  T getActivity() {
		return activity.get();
	}
	*/
	
	public void release() {
		activity = null;
	}
}
