package com.sh.dilyly.adapter.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sh.dilily.R;
import com.sh.dilily.data.Teacher;

/**
 * 老师个人信息
 * */
public class TeacherInfoAdaper extends BaseAdapter {

	public Context context;
	public Teacher teacher;

	public TeacherInfoAdaper(Context context, Teacher teacher) {
		this.context = context;
		this.teacher = teacher;
	}

	public void setData(Teacher teacher) {
		this.teacher = teacher;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public String[] getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	private View getBanner(ViewGroup parent, View convertView) {
		ViewGroup view = null;
		if (convertView == null || !(convertView instanceof LinearLayout)) {
			view = (ViewGroup)View.inflate(context, R.layout.teacher_info_banner, null);
		} else {
			view = (ViewGroup)convertView;
		}
		TextView nameView = (TextView)view.findViewById(R.id.teacher_name);
		nameView.setText(teacher.name);
/*
		RatingBar ratingBar = (RatingBar)view.findViewById(R.id.teacher_rating);
		ratingBar.setRating(teacher.rate);
*/
		return view;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position == 0)
			return getBanner(parent, convertView);
		
		ViewGroup view = null;
		if (convertView == null || !(convertView instanceof RelativeLayout)) {
			view = (ViewGroup)View.inflate(context, R.layout.teacher_info_listitem, null);
		} else {
			view = (ViewGroup)convertView;
		}
		TextView t1 = (TextView)view.getChildAt(0);
		TextView t2 = (TextView)view.getChildAt(1);
		switch(position) {
		case 1:
			t1.setText("课时费");
			if (teacher.price > 0)
				t2.setText(String.valueOf(teacher.price));
			break;
		case 2:
			t1.setText("专业");
			if (teacher.major != null)
				t2.setText(teacher.major);
			break;
		case 3:
			t1.setText("授课区域");
			if (teacher.region != null)
				t2.setText(teacher.region);
			break;
		case 4:
			t1.setText("授课方式");
			if (teacher.mode != null)
				t2.setText(teacher.mode);
			break;
		case 5:
			t1.setText("课时设置");
			t2.setText(">");
			break;
		}
		return view;
	}

}
