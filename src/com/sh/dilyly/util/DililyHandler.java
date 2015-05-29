package com.sh.dilyly.util;

import java.lang.ref.WeakReference;

import com.sh.dilily.activity.DililyActivity;

import android.os.Handler;

public class DililyHandler extends Handler {
	
	private WeakReference<DililyActivity> activity;
	public DililyHandler(DililyActivity activity) {
		this.activity = new WeakReference<DililyActivity>(activity);
	}
	
	/**
	 * may null
	 * */
	public DililyActivity getActivity() {
		return activity.get();
	}
}
