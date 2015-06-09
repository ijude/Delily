package com.sh.dilily.activity;

import android.os.Bundle;

import com.sh.dilily.R;
import com.sh.dilily.activity.fragment.StudentInfoFragment;
import com.sh.dilily.activity.fragment.StudentMainFragment;
import com.sh.dilily.activity.fragment.StudentMessageFragment;

/**
 * 学生登录后看到的页面
 * */
public class StudentActivity extends FrameActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student);
		
		addFrame(StudentMainFragment.class, getIcon(R.string.home, R.drawable.icon_home));
		addFrame(StudentMessageFragment.class, getIcon(R.string.message, R.drawable.icon_message));
		addFrame(StudentInfoFragment.class, getIcon(R.string.me, R.drawable.icon_user));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
}
