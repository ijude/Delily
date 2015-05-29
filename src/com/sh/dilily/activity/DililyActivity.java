package com.sh.dilily.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sh.dilily.R;

/**
 * 标准的嘀哩哩Activity
 * */
public class DililyActivity extends BaseActivity implements View.OnClickListener {
/*	
	public static int SCREEN_WIDTH = -1;
	public static int SCREEN_HEIGHT;
*/
	public DililyActivity() {
/*
		if (SCREEN_WIDTH == -1) {
			WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
			SCREEN_WIDTH = wm.getDefaultDisplay().getWidth();
			SCREEN_HEIGHT = wm.getDefaultDisplay().getHeight();
		}
*/
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		View titleBar = findViewById(R.id.title_bar);
		if (titleBar != null) {
			int[] ids = {R.id.title_left, R.id.title_right};
			setClickListener(ids, this);
		}
	}
	
	protected void startActivity(Class<? extends Activity> clz) {
		startActivity(new Intent(getBaseContext(), clz));
	}
	
	protected void setClickListener(int[] ids, View.OnClickListener listener) {
		if (ids == null || ids.length == 0 || listener == null)
			return;
		for (int id : ids) {
			View v = findViewById(id);
			if (v != null)
				v.setOnClickListener(listener);
		}
	}
	
	protected void toast(long num) {
		toast(String.valueOf(num));
	}
	protected void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	protected View createView(int res) {
		return getLayoutInflater().inflate(res, null);
	}
	
	protected SpannableString getString(int strRes, int imgRes) {
		SpannableString spanString = new SpannableString(strRes > 0 ? ' ' + getString(strRes) : " ");
		Bitmap b = BitmapFactory.decodeResource(getResources(), imgRes);
		ImageSpan imgSpan = new ImageSpan(this, b);
		spanString.setSpan(imgSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spanString;
	}
	
	protected View getIcon(int strRes, int imgRes) {
		LinearLayout l = (LinearLayout)createView(R.layout.icon);
		ImageView v = (ImageView)l.getChildAt(0);
		v.setImageDrawable(getResources().getDrawable(imgRes));
		TextView t = (TextView)l.getChildAt(1);
		t.setText(getResources().getString(strRes));
		return l;
	}
	
	@SuppressWarnings("deprecation")
	public void setTitle(View parent, CharSequence title, CharSequence leftButtonText, CharSequence rightButtonText) {
		ViewGroup titleBar = (ViewGroup)(parent == null ? findViewById(R.id.title_bar) : parent.findViewById(R.id.title_bar));
		if (titleBar == null) {
			return;
		}
		titleBar.setVisibility(View.VISIBLE);
		TextView center = (TextView)titleBar.getChildAt(1);
		center.setBackgroundDrawable(null);
		if (title != null) {
			center.setText(title);
		} else {
			center.setText(getString(0, R.drawable.logo_white));
		}
		if (leftButtonText != null) {
			TextView left = (TextView)titleBar.getChildAt(0);
			left.setText(leftButtonText);
			left.setVisibility(View.VISIBLE);
		}
		if (rightButtonText != null) {
			TextView right = (TextView)titleBar.getChildAt(2);
			right.setText(rightButtonText);
			right.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.title_left:
			finish();
			break;
		case R.id.title_right:
			break;
		}
	}
}
