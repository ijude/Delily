package com.sh.dilily.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import com.sh.dilily.R;

/**
 * 欢迎/加载
 * 判断是否登录, 打开不同的界面
 * */
public class WelcomeActivity extends DililyActivity {
	
	private static final int MSG_ID = 1;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case MSG_ID:
				dispatch();
				return;
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ImageView splash = new ImageView(this);
		splash.setImageResource(R.drawable.welcome_bg);
		splash.setScaleType(ImageView.ScaleType.FIT_XY);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		splash.setLayoutParams(lp);
		setContentView(splash);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		handler.sendMessageDelayed(Message.obtain(handler, MSG_ID), 500);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		this.finish();
	}
	
	private void dispatch() {
		if (isLogined()) {
			//loading
			if (isTeacher()) {
				startActivity(TeacherActivity.class);
			} else if (isStudent()) {
				startActivity(StudentActivity.class);
			}
		} else {
			startActivity(LoginActivity.class);
		}
	}
	
	/**
	 * 是否已经登录
	 * */
	private boolean isLogined() {
		//TODO
		return false;
	}
	
	/**
	 * 已登录用户，是否老师？
	 * */
	private boolean isTeacher() {
		//TODO
		return true;
	}
	
	/**
	 * 已登录用户，是否学生？
	 * */
	private boolean isStudent() {
		return !isTeacher();
	}
	
}
