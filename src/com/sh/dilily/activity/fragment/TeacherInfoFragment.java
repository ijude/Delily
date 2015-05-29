package com.sh.dilily.activity.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sh.dilily.R;
import com.sh.dilily.activity.EditActivity;
import com.sh.dilily.data.Teacher;
import com.sh.dilyly.adapter.list.TeacherInfoAdapter;
import com.sh.dilyly.util.Utils;


public class TeacherInfoFragment extends Frame implements AdapterView.OnItemClickListener {
	private static final int EDIT_REQUEST_CODE = 1;
	private Teacher teacher;

	@Override
	public void fillData() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater) {
		View view = View.inflate(getContext(), R.layout.teacher_info, null);
		setTitle(view, null, null, null);
		initListView(view);
		return view;
	}
	
	private Teacher getTeacher() {
		if (this.teacher != null)
			return this.teacher;
		//TODO 获取当前老师资料
		Teacher t = new Teacher();
		t.id = 1;
		t.name = "Bruce";
		t.gender = "男";
		t.major = "唱歌、跳舞";
		t.mode = "学生上门";
		t.phone = "13899999999";
		t.price = 250;
		t.rate = 5;
		t.region = "上海";
		this.teacher = t;
		return t;
	}
	
	public void initListView(View view) {
		ListView lv = null;
		if (view == null)
			lv = (ListView) findViewById(R.id.teacher_info_list);
		else
			lv = (ListView) view.findViewById(R.id.teacher_info_list);
		TeacherInfoAdapter adapter = new TeacherInfoAdapter(getContext(), getTeacher(), TeacherInfoAdapter.VIEWPORT_TEACHER);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == EDIT_REQUEST_CODE && resultCode == EditActivity.SUCCESS_CODE) {
			String result = data.getStringExtra(EditActivity.KEY_RESULT);
			String title = data.getCharSequenceExtra(EditActivity.KEY_TITLE).toString();
			setField(title, result);
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void setField(String title, String value) {
//		Utils.toast(getContext(), title + "=" + value);
		boolean changed = false;
		if ("名字".equals(title)) {
			if (!Utils.equals(teacher.name, value)) {
				teacher.name = value;
				changed = true;
			}
		} else if ("性别".equals(title)) {
			if (!Utils.equals(teacher.gender, value)) {
				teacher.gender = value;
				changed = true;
			}
		} else if ("专业".equals(title)) {
			if (!Utils.equals(teacher.major, value)) {
				teacher.major = value;
				changed = true;
			}
		} else if ("授课费用".equals(title)) {
			int price = Utils.toInteger(value);
			if (teacher.price != price) {
				teacher.price = price;
				changed = true;
			}
		} else if ("授课方式".equals(title)) {
			if (!Utils.equals(teacher.mode, value)) {
				teacher.mode = value;
				changed = true;
			}
		} else if ("授课区域".equals(title)) {
			if (!Utils.equals(teacher.region, value)) {
				teacher.region = value;
				changed = true;
			}
		} else if ("个人经历".equals(title)) {
			if (!Utils.equals(teacher.experience, value)) {
				teacher.experience = value;
				changed = true;
			}
		} else if ("个人描述".equals(title)) {
			if (!Utils.equals(teacher.desc, value)) {
				teacher.desc = value;
				changed = true;
			}
		}
		if (changed) {
//			Utils.toast(getContext(), "changed");
			ListView list = (ListView) findViewById(R.id.teacher_info_list);
			TeacherInfoAdapter adapter = (TeacherInfoAdapter)list.getAdapter();
			adapter.setData(teacher);
			//TODO 更新服务器端
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		Utils.toast(getContext(), String.valueOf(position));
		switch(position) {
		case 0:		//头像
			break;
		case 1:		//名字
			editText("名字", teacher.name, true);
			break;
		case 2:
			editSelect("性别", teacher.gender, new String[]{"男", "女"});
			break;
		case 3:
			editSelect("专业", teacher.major, new String[]{"钢琴", "小提琴", "歌唱", "跳舞"});
			break;
		case 5:
			editNumber("授课费用", teacher.price);
			break;
		case 6:
			editSelect("授课方式 ", teacher.mode, new String[]{"学生上门", "老师上门"});
			break;
		case 7:
			String[] areas = getResources().getStringArray(R.array.shanghai_regions);
			editSelect("授课区域", teacher.region, areas);
			break;
		case 9:
			editTextarea("个人经历", teacher.experience);
			break;
		case 11:
			editTextarea("个人描述", teacher.desc);
			break;
		}
	}
	
	private void editText(String label, String current) {
		editText(label, current, false);
	}
	
	private void editText(String label, String current, boolean not_empty) {
		Intent intent = new Intent(getContext(), EditActivity.class);
		intent.putExtra(EditActivity.KEY_TYPE, not_empty ? EditActivity.EDIT_TYPE_TEXT_NON_EMPTY : EditActivity.EDIT_TYPE_TEXT);
		intent.putExtra(EditActivity.KEY_TITLE, (CharSequence)label);
		intent.putExtra(EditActivity.KEY_DEFAULT, (CharSequence)current);
		startActivityForResult(intent, EDIT_REQUEST_CODE);
	}
	
	private void editTextarea(String label, String current) {
		Intent intent = new Intent(getContext(), EditActivity.class);
		intent.putExtra(EditActivity.KEY_TYPE, EditActivity.EDIT_TYPE_TEXTAREA);
		intent.putExtra(EditActivity.KEY_TITLE, (CharSequence)label);
		intent.putExtra(EditActivity.KEY_DEFAULT, (CharSequence)current);
		startActivityForResult(intent, EDIT_REQUEST_CODE);
	}
	
	private void editNumber(String label, int num) {
		Intent intent = new Intent(getContext(), EditActivity.class);
		intent.putExtra(EditActivity.KEY_TYPE, EditActivity.EDIT_TYPE_INT);
		intent.putExtra(EditActivity.KEY_TITLE, (CharSequence)label);
		intent.putExtra(EditActivity.KEY_DEFAULT, (CharSequence)String.valueOf(num));
		startActivityForResult(intent, EDIT_REQUEST_CODE);
	}
	
	private void editSelect(String label, String value, String[] options) {
		Intent intent = new Intent(getContext(), EditActivity.class);
		intent.putExtra(EditActivity.KEY_TYPE, EditActivity.EDIT_TYPE_SELECT);
		intent.putExtra(EditActivity.KEY_TITLE, (CharSequence)label);
		intent.putExtra(EditActivity.KEY_DEFAULT, (CharSequence)value);
		intent.putExtra(EditActivity.KEY_OPTIONS, options);
		startActivityForResult(intent, EDIT_REQUEST_CODE);
	}
}
