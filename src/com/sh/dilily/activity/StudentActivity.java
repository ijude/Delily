package com.sh.dilily.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sh.dilily.R;
import com.sh.dilily.activity.fragment.SMainFragment;
import com.sh.dilily.activity.fragment.SMessageFragment;

public class StudentActivity extends FrameActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_activity);
//*
		addFrame(SMainFragment.class, getIcon(R.string.home, R.drawable.icon_home));

		addFrame(SMessageFragment.class, getIcon(R.string.message, R.drawable.icon_message));

/*/		
		TextView t1 = new TextView(this);
		t1.setText(getString(R.string.home, R.drawable.icon_home));
//		t1.setBackground(getResources().getDrawable(R.drawable.icon_home));
		addFrame(SMainFragment.class, t1);
		
		TextView t2 = new TextView(this);
		t2.setText(getString(R.string.message, R.drawable.icon_message));
		addFrame(SMessageFragment.class, t2);
//*/
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
}
