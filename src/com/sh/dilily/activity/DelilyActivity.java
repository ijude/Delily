package com.sh.dilily.activity;

import com.sh.dilily.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 标准的嘀哩哩Activity
 * */
public class DelilyActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
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
	
	protected void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	protected View createView(int res) {
		return getLayoutInflater().inflate(res, null);
	}
	
	protected SpannableString getString(int strRes, int imgRes) {
		SpannableString spanString = new SpannableString(' ' + getResources().getString(strRes));
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
}
