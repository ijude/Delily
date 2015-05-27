package com.sh.dilyly.adapter.list;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sh.dilily.R;
import com.sh.dilily.data.Teacher;

/**
 * 老师个人信息
 * */
public class TeacherInfoAdapter extends BaseAdapter {
	
	public static final int VIEWPORT_TEACHER = 1;
	public static final int VIEWPORT_STUDENT = 2;
	private Context context;
	private Teacher teacher;
	private int viewport;
	private View[] views;

	/**
	 * @param viewport 视角, 老师看到的和学生看到的显示不同的内容
	 * */
	public TeacherInfoAdapter(Context context, Teacher teacher, int viewport) {
		this.context = context;
		this.teacher = teacher;
		this.viewport = viewport;
	}

	public void setData(Teacher teacher) {
		this.teacher = teacher;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return viewport == VIEWPORT_TEACHER ? 13 : 7;
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
		ViewGroup view = (ViewGroup)View.inflate(context, R.layout.teacher_info_banner, null);
		TextView nameView = (TextView)view.findViewById(R.id.teacher_name);
		if (nameView != null) {
			nameView.setText(teacher.name);
			if (viewport == VIEWPORT_STUDENT)
				nameView.setVisibility(View.VISIBLE);
		}
		//TODO 更新头像
/*
		RatingBar ratingBar = (RatingBar)view.findViewById(R.id.teacher_rating);
		ratingBar.setRating(teacher.rate);
*/
		return view;
	}
	
	private View getDivider() {
		return View.inflate(context, R.layout.divider, null);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (views == null) {
			views = new View[getCount()];
		} else if (position < views.length && views[position] != null) {
			return views[position];
		}
		View v = null;
		if (position == 0) {
			v = getBanner(parent, convertView);
			views[position] = v;
			return v;
		}
		if (VIEWPORT_TEACHER == viewport)
			v = getViewForTeacher(teacher, position, convertView);
		else
			v = getViewForStudent(teacher, position, convertView);
		views[position] = v;
		return v;
	}
	
	private View getViewForTeacher(Teacher teacher, int position, View convertView) {
		if (position == 4 || position == 8 || position == 10)
			return getDivider();
		ViewGroup view = null;
		if (position == 12) {
			view = (ViewGroup)View.inflate(context, R.layout.teacher_info_button, null);
			Button button = (Button)view.getChildAt(0);
			button.setText("退出登录");
			return view;
		}
		if (convertView == null || !(convertView instanceof RelativeLayout)) {
			view = (ViewGroup)View.inflate(context, R.layout.teacher_info_listitem, null);
		} else {
			view = (ViewGroup)convertView;
		}
		TextView t1 = (TextView)view.getChildAt(0);
		TextView t2 = (TextView)view.getChildAt(1);
		switch(position) {
		case 1:
			t1.setText("姓名");
			t2.setText(String.valueOf(teacher.name));
			break;
		case 2:
			t1.setText("性别");
			if (teacher.gender != null)
				t2.setText(teacher.gender);
			break;
		case 3:
			t1.setText("专业");
			if (teacher.major != null)
				t2.setText(teacher.major);
			break;
		case 5:
			t1.setText("授权费用");
			if (teacher.price > 0)
				t2.setText(teacher.price + "元/小时");
			break;
		case 6:
			t1.setText("授课方式");
			if (teacher.mode != null)
				t2.setText(teacher.mode);
			break;
		case 7:
			t1.setText("授课区域");
			if (teacher.region != null)
				t2.setText(teacher.region);
			break;
		case 9:
			t1.setText("个人经历");
			break;
		case 11:
			t1.setText("个人描述");
			break;
		}
		return view;
	}

	private View getViewForStudent(Teacher teacher, int position, View convertView) {
		ViewGroup view = null;
		if (position == 6) {
			view = (ViewGroup)View.inflate(context, R.layout.teacher_info_button, null);
			Button button = (Button)view.getChildAt(0);
			button.setText("预约课程");
			return view;
		}
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
