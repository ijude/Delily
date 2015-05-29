package com.sh.dilyly.adapter.list;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
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
	private boolean showBanner = true;
	private CharSequence buttonText;
	private View.OnClickListener buttonListener;
	private ArrayList<Item> items;

	/**
	 * @param viewport 视角, 老师看到的和学生看到的显示不同的内容
	 * */
	public TeacherInfoAdapter(Context context, Teacher teacher, int viewport) {
		this.context = context;
		this.teacher = teacher;
		this.viewport = viewport;
		initItems(teacher, viewport);
	}

	public void setData(Teacher teacher) {
		this.teacher = teacher;
		initItems(teacher, viewport);
		notifyDataSetChanged();
	}
	
	public void showButton(CharSequence text, View.OnClickListener listener) {
		this.buttonText = text;
		this.buttonListener = listener;
	}
	
	private void initItems(Teacher teacher, int viewport) {
		this.items = new ArrayList<Item>();
		if (viewport == VIEWPORT_TEACHER) {
			addItem("姓名", teacher.name, true);
			addItem("性别", teacher.gender, true);
			addItem("专业", teacher.major, true);
			addDivider();
			addItem("授课费用", teacher.price + "元/小时", true);
			addItem("授课方式", teacher.mode + "", true);
			addItem("授课区域", teacher.region, true);
			addDivider();
			addItem("个人经历", "", true);
			addDivider();
			addItem("个人描述", "", true);
		} else {
			addItem("他的动态", "", true);
			addItem("课时费", teacher.price + "元/小时");
			addItem("专业", teacher.major);
			addItem("授课区域", teacher.region);
			addItem("授课方式", teacher.mode);
			addDivider();
			//TODO 经历
			addItem("2005年-2012年", "上海音乐学院");
			addItem("2012年-2014年", "美国茱丽亚音乐学院");
			if (teacher.desc != null && teacher.desc.length() > 0) {
				addDivider();
				addItem("个人描述", "");
				addItem(teacher.desc, "");
			}
		}
	}
	
	private void addItem(CharSequence label, CharSequence value) {
		addItem(label, value, false);
	}
	private void addItem(CharSequence label, CharSequence value, boolean editable) {
		items.add(new Item(label, value, editable));
	}
	
	private void addDivider() {
		items.add(new Item());
	}

	@Override
	public int getCount() {
		int count = items.size();
		if (showBanner)
			count ++;
		if (buttonText != null)
			count ++;
		return count;
	}

	@Override
	public Item getItem(int position) {
		if (position <= 0 && showBanner)
			return null;
		if (position <= items.size())
			return items.get(position-1);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position + 1;
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
		Item item = getItem(position);
		if (item == null) {
			if (position == 0 && showBanner) {
				return getBanner(parent, convertView);
			}
			if (buttonText != null && position == getCount() - 1) {
				ViewGroup view = (ViewGroup)View.inflate(context, R.layout.teacher_info_button, null);
				Button button = (Button)view.getChildAt(0);
				button.setText(buttonText);
				button.setOnClickListener(this.buttonListener);
				return view;
			}
			return getDivider();
		}
		if (item.divider)
			return getDivider();
		ViewGroup view = null;
		if (convertView == null || !(convertView instanceof RelativeLayout)) {
			view = (ViewGroup)View.inflate(context, R.layout.teacher_info_listitem, null);
		} else {
			view = (ViewGroup)convertView;
		}
		TextView t1 = (TextView)view.getChildAt(0);
		t1.setText(item.label);
		TextView t2 = (TextView)view.getChildAt(1);
		if (item.editable) {
			SpannableStringBuilder builder = new SpannableStringBuilder();
			if (item.value != null)
				builder.append(item.value);
			builder.append(" ");
			Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.right_angle_bracket);
			ImageSpan imgSpan = new ImageSpan(context, b);
			builder.setSpan(imgSpan, builder.length()-1, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			t2.setText(builder);
		} else {
			t2.setText(item.value);
		}
		return view;
	}
/*
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
*/
	private static class Item {
		public CharSequence label;
		public CharSequence value;
		public boolean editable;
		public boolean divider;
		public Item() {
			divider = true;
		}
		public Item(CharSequence label, CharSequence value, boolean editable) {
			this.label = label;
			this.value = value;
			this.editable = editable;
		}
	}
}
