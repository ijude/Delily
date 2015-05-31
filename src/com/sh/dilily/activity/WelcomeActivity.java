package com.sh.dilily.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sh.dilily.R;
import com.sh.dilily.handler.DililyHandler;

/**
 * 欢迎/加载
 * 判断是否登录, 打开不同的界面
 * */
public class WelcomeActivity extends DililyActivity {
	
	private static final int MSG_DISPATCH = 1;
	private static final int MSG_CHECK_UPDATE = 2;
	
	private static final int WELCOME = 500;			//显示欢迎时间
	
	private WelcomeHandler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		ImageView splash = new ImageView(this);
		splash.setImageResource(R.drawable.welcome_bg);
		splash.setScaleType(ImageView.ScaleType.FIT_XY);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		splash.setLayoutParams(lp);
		setContentView(splash);
		
		initHandler();
	}
	
	private void initHandler() {
		if (handler == null) {
			handler = new WelcomeHandler(this);
			handler.sendMessageDelayed(Message.obtain(handler, MSG_DISPATCH), WELCOME);
		}
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
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (handler != null) {
			handler.release();
			handler = null;
		}
	}
	
	static class WelcomeHandler extends DililyHandler<WelcomeActivity> {

		public WelcomeHandler(WelcomeActivity activity) {
			super(activity);
		}

		@Override
		public boolean handleMessage(WelcomeActivity activity, int what) {
			switch (what) {
			case MSG_DISPATCH:
				activity.dispatch();
				break;
			case MSG_CHECK_UPDATE:
				
				break;
			default:
				return false;
			}
			return true;
		}
		
	}
}
