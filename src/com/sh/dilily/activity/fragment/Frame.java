/**
 * 
 */
package com.sh.dilily.activity.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;

import com.sh.dilily.activity.DelilyActivity;

public abstract class Frame {
	protected DelilyActivity mActivity;
	private View contentView;
	protected boolean isResume;

	public void onFrameTabClick() {
	}

	public void post(Runnable r) {
		if (null != contentView) {
			contentView.post(r);
		}
	}

	public void setActivity(DelilyActivity act) {
		this.mActivity = act;
	}

	public void setContentView(View content) {
		this.contentView = content;
	}

	public View onCreateView(LayoutInflater inflater) {
		return null;
	}

	/**
	 * Return the Activity this frame is currently associated with.
	 */
	public final DelilyActivity getActivity() {
		return mActivity;
	}
	
	public final Context getContext() {
		return mActivity.getBaseContext();
	}

	protected String setLastActivityName() {
		return null;
	}

	// 系统方法
	public void onCreate() {
	}

	public void onResume() {
		isResume = true;
	}

	public void onPause() {
		isResume = false;
	}

	public void onDestroy() {

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	public void onConfigurationChanged(Configuration newConfig) {

	}

	public Resources getResources() {
		return mActivity.getResources();
	}

	public View findViewById(int id) {
		return contentView.findViewById(id);
	}

	public void startActivity(Intent intent) {
		mActivity.startActivity(intent);
	}

	public void startActivityForResult(Intent intent, int requestCode) {
		mActivity.startActivityForResult(intent, requestCode);
	}

	public String getString(int resId) {
		return mActivity.getString(resId);
	}

	public void runOnUiThread(Runnable action) {
		mActivity.runOnUiThread(action);
	}

	public Object getSystemService(String name) {
		return mActivity.getSystemService(name);
	}

	public ContentResolver getContentResolver() {
		return mActivity.getContentResolver();
	}

	public void finish() {
		mActivity.finish();
	}

	public boolean onBackPressed() {
		return false;
	}

	protected void setTitle(View parent, CharSequence title, CharSequence leftButtonText, CharSequence rightButtonText) {
		if (mActivity instanceof DelilyActivity)
			((DelilyActivity)mActivity).setTitle(parent, title, leftButtonText, rightButtonText);
	}
}
