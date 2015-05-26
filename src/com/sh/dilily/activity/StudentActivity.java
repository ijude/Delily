package com.sh.dilily.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sh.dilily.R;
import com.sh.dilily.activity.fragment.SMainFragment;
import com.sh.dilily.activity.fragment.SMessageFragment;

/**
 * 学生登录后看到的页面
 * */
public class StudentActivity extends FrameActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_activity);
		
		addFrame(SMainFragment.class, getIcon(R.string.home, R.drawable.icon_home));
		addFrame(SMessageFragment.class, getIcon(R.string.message, R.drawable.icon_message));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
}
