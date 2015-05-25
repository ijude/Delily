package com.sh.dilily.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.sh.dilily.R;
import com.sh.dilily.activity.fragment.SMainFragment;
import com.sh.dilily.activity.fragment.SMessageFragment;

public class MainActivity extends FrameActivity implements View.OnClickListener {

	public static final int STEP_USERID_VERIFY = 0;
	public static final int STEP_USERID_SHAIXUAN = STEP_USERID_VERIFY + 1;
	public static final int MSG_LOAD_MAIN_UI = STEP_USERID_SHAIXUAN + 1;

	private static final String TAB_1_TAG = "tab_1";
	private static final String TAB_2_TAG = "tab_2";

	private TabHost mTabHost;

	private int mStep = 0;

	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case STEP_USERID_VERIFY:
				setContentView(R.layout.role_select);
				break;
			case STEP_USERID_SHAIXUAN:
				/** 学生首次则 进入筛选界面 */
				initStudentShaixuan();
				/** 老师则进入编辑个人资料的界面 */
				initTeacherEditProfile();
				break;
			case MSG_LOAD_MAIN_UI:
				/** 完成之后进入主界面 */
				// setContentView(R.layout.activity_main);
				/** 学生首次则 进入筛选界面 */
				initStudentMain();
				/** 老师则进入编辑个人资料的界面 */
				// initTeacherMain();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ImageView splash = new ImageView(this);
		splash.setImageResource(R.drawable.welcome_bg);
		splash.setScaleType(ImageView.ScaleType.FIT_XY);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		splash.setLayoutParams(lp);
		setContentView(splash);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mStep = MSG_LOAD_MAIN_UI;
		mHandler.sendMessageDelayed(Message.obtain(mHandler, mStep), 500);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.f_userid_techer:
			break;

		case R.id.f_userid_student:
			mStep++;
			mHandler.sendMessageDelayed(Message.obtain(mHandler, mStep), 0);
			break;
		default:
			break;
		}

	};

	public void initStudentMain() {
		setContentView(R.layout.student_activity);
		TextView t1 = new TextView(this);
		t1.setText("main");
		addFrame(SMainFragment.class, t1);

		TextView t2 = new TextView(this);
		t2.setText("message");
		addFrame(SMessageFragment.class, t2);

	}

	private void initStudentShaixuan() {
		// TODO Auto-generated method stub

	}

	private void initTeacherEditProfile() {
		// TODO Auto-generated method stub

	}

	private void initTeacherMain() {

	}

}
