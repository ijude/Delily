package com.sh.dilily.activity;

import com.sh.dilily.R;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

public class TeacherActivity extends FrameActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ImageView splash = new ImageView(this);
		splash.setImageResource(R.drawable.qrcode2);
		splash.setScaleType(ImageView.ScaleType.FIT_CENTER);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		splash.setLayoutParams(lp);
		setContentView(splash);
	}
}
