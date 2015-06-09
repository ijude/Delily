package com.sh.dilily.activity;

import android.os.Bundle;

import com.sh.dilily.R;
import com.sh.dilily.activity.fragment.TeacherInfoFragment;
import com.sh.dilily.activity.fragment.TeacherMessageFragment;

/**
 * 老师登录后看到的页面
 * */
public class TeacherActivity extends FrameActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher);
		
		addFrame(TeacherInfoFragment.class, R.string.home, R.drawable.tabicon_user);
		addFrame(TeacherMessageFragment.class, R.string.message, R.drawable.tabicon_message);
/*
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ImageView splash = new ImageView(this);
		splash.setImageResource(R.drawable.qrcode2);
		splash.setScaleType(ImageView.ScaleType.FIT_CENTER);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		splash.setLayoutParams(lp);
		setContentView(splash);
*/
	}
}
