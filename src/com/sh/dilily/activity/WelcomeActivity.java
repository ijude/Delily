package com.sh.dilily.activity;

import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sh.dilily.Dilily;
import com.sh.dilily.R;
import com.sh.dilily.data.People;
import com.sh.dilily.handler.DililyHandler;
import com.sh.dilily.net.NetworkReceiver;
import com.sh.dilyly.util.Utils;

/**
 * 欢迎/加载
 * 判断是否登录, 打开不同的界面
 * */
public class WelcomeActivity extends DililyActivity {
	
	private static final int MSG_DISPATCH = 1;
	private static final int MSG_CHECK_DATA = 2;
	private static final int MSG_RETRY = 3;
	
	private static final int WELCOME = 500;		//显示欢迎时间
	
	private static final int REQUEST_CODE_SERIALID = 1;
	
	private WelcomeHandler handler;
	
	private boolean ready = false;				//是否准备好了,即获取服务端序列号
	private boolean welcomed = false;			//是否已经显示欢迎界面指定时间
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		initContentView();
		initHandler();
		initReceiver();
	}
	
	private void initContentView() {
		ImageView splash = new ImageView(this);
		splash.setImageResource(R.drawable.welcome_bg);
		splash.setScaleType(ImageView.ScaleType.FIT_XY);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		splash.setLayoutParams(lp);
		setContentView(splash);
	}
	
	private void initHandler() {
		if (handler == null) {
			handler = new WelcomeHandler(this);
			handler.sendEmptyMessage(MSG_CHECK_DATA);
			handler.sendMessageDelayed(Message.obtain(handler, MSG_DISPATCH), WELCOME);
		}
	}
	
	private void initReceiver() {
		registerNetworkReceiver(new NetworkReceiver() {
			@Override
			public void onSuccess(Map<String, Object> result, int request, String extra) {
				switch(request) {
				case REQUEST_CODE_SERIALID:
					String serialId = getValue(result, "serial", "serialId");
					setConfiguration(Dilily.KEY_SERIALID, serialId);
					ready = true;
					if (welcomed) {
						dispatch();
					} else {
						//wait
					}
					break;
				}
			}
			
			@Override
			public void onFailure(String msg, int request, String extra) {
				toast("request failuer");
				switch(request) {
				case REQUEST_CODE_SERIALID:
					handler.sendEmptyMessageDelayed(MSG_RETRY, 3000);
					break;
				}
			}
		});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
//		this.finish();
	}
	
	private void dispatch() {
		if (isLogined()) {
			//loading
			if (isTeacher()) {
				startActivity(TeacherActivity.class);
				finish();
			} else if (isStudent()) {
				startActivity(StudentActivity.class);
				finish();
			} else {
				//无效数据
				toast("无效数据");
			}
		} else {
			startActivity(LoginActivity.class);
			finish();
		}
	}
	
	/**
	 * 是否已经登录
	 * */
	private boolean isLogined() {
		String token = getConfiguration(Dilily.KEY_TOKEN);
		return !Utils.isEmpty(token);
	}
	
	/**
	 * 已登录用户，是否老师？
	 * */
	private boolean isTeacher() {
		return getConfigurationInt(Dilily.KEY_USERTYPE, 0) == People.TYPE_TEACHER;
	}
	
	/**
	 * 已登录用户，是否学生？
	 * */
	private boolean isStudent() {
		return getConfigurationInt(Dilily.KEY_USERTYPE, 0) == People.TYPE_STUDENT;
	}
	
	/** 检测是否有serialId, 没有则向服务器端获取 */
	private void checkData() {
		String serialId = getConfiguration(Dilily.KEY_SERIALID);
		if (Utils.isEmpty(serialId)) {
			request(getNetwork().getSerialIdUrl(), REQUEST_CODE_SERIALID);
		} else {
			ready = true;
		}
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
				activity.welcomed = true;
				if (activity.ready) {
					activity.dispatch();
				}
				break;
			case MSG_CHECK_DATA:
				activity.checkData();
//				activity.request(getNetwork().getUpgradeUrl(), 0);
				break;
			case MSG_RETRY:
				activity.toast("获取授权码失败，几秒后重试，请检查网络。");
				activity.request(getNetwork().getSerialIdUrl(), REQUEST_CODE_SERIALID);
				break;
			default:
				return false;
			}
			return true;
		}
		
	}
}
