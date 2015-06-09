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
import com.sh.dilily.data.AttributeItem;
import com.sh.dilily.data.PersonAttributes;
import com.sh.dilily.data.Student;

/**
 * 学生个人信息
 * */
public class StudentInfoAdapter extends BaseAdapter {
	
	public static final int VIEWPORT_TEACHER = 1;
	public static final int VIEWPORT_STUDENT = 2;
	private Context context;
	private Student student;
	private int viewport;
	private boolean showBanner = true;
	private CharSequence buttonText;
	private View.OnClickListener buttonListener;
	private ArrayList<AttributeItem> items;

	/**
	 * @param viewport 视角, 老师看到的和学生看到的显示不同的内容
	 * */
	public StudentInfoAdapter(Context context, Student student, int viewport) {
		this.context = context;
		this.student = student;
		this.viewport = viewport;
		initItems(student, viewport);
	}

	public void setData(Student student) {
		this.student = student;
		initItems(student, viewport);
		notifyDataSetChanged();
	}
	
	public void showButton(CharSequence text, View.OnClickListener listener) {
		this.buttonText = text;
		this.buttonListener = listener;
	}
	
	private void initItems(Student student, int viewport) {
		this.items = new ArrayList<AttributeItem>();
		if (viewport == VIEWPORT_STUDENT) {
			addItem(PersonAttributes.KEY_NAME, student.name, true);
			addItem(PersonAttributes.KEY_GENDER, student.gender, true);
			addItem(PersonAttributes.KEY_AGE, String.valueOf(student.age), true);
			addDivider();
			addItem(PersonAttributes.KEY_PHONE, student.phone, true);
			addItem(PersonAttributes.KEY_REGION, student.region, true);
			addItem(PersonAttributes.KEY_ADDRESS, student.address, true);
			addDivider();
			addItem(PersonAttributes.KEY_LEVEL, "", true);
			if (student.level != null && student.level.length() > 0) {
				addItem(student.level, "");
			}

			addDivider();
			addItem(PersonAttributes.KEY_DESC, "", true);
			if (student.desc != null && student.desc.length() > 0) {
				addItem(student.desc, "");
			}
		} else {
			addItem(PersonAttributes.KEY_NAME, student.name);
			addItem(PersonAttributes.KEY_GENDER, student.gender);
			addItem(PersonAttributes.KEY_AGE, String.valueOf(student.age));
			addDivider();
			addItem(PersonAttributes.KEY_PHONE, student.phone);
			addItem(PersonAttributes.KEY_REGION, student.region);
			
			if (student.level != null && student.level.length() > 0) {
				addDivider();
				addItem(PersonAttributes.KEY_LEVEL, "");
				addItem(student.level, "");
			}
			if (student.desc != null && student.desc.length() > 0) {
				addDivider();
				addItem(PersonAttributes.KEY_DESC, "");
				addItem(student.desc, "");
			}
		}
	}
	
	private void addItem(CharSequence label, CharSequence value) {
		addItem(label, value, false);
	}
	private void addItem(CharSequence label, CharSequence value, boolean editable) {
		items.add(new AttributeItem(label, value, editable));
	}
	
	private void addDivider() {
		items.add(new AttributeItem());
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
	public AttributeItem getItem(int position) {
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
			nameView.setText(student.name);
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
		AttributeItem item = getItem(position);
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
	
}
