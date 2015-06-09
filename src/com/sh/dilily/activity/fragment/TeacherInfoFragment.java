package com.sh.dilily.activity.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sh.dilily.R;
import com.sh.dilily.data.AttributeItem;
import com.sh.dilily.data.PersonAttributes;
import com.sh.dilily.data.Teacher;
import com.sh.dilyly.adapter.list.TeacherInfoAdapter;
import com.sh.dilyly.util.Utils;


public class TeacherInfoFragment extends PersonInfoFragment {
	private Teacher teacher;
	private TeacherInfoAdapter adapter;

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
		adapter = new TeacherInfoAdapter(getContext(), getTeacher(), TeacherInfoAdapter.VIEWPORT_TEACHER);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}
	
	protected void setField(CharSequence title, String value) {
//		Utils.toast(getContext(), title + "=" + value);
		boolean changed = false;
		if (PersonAttributes.KEY_NAME.equals(title)) {
			if (!Utils.equals(teacher.name, value)) {
				teacher.name = value;
				changed = true;
			}
		} else if (PersonAttributes.KEY_GENDER.equals(title)) {
			if (!Utils.equals(teacher.gender, value)) {
				teacher.gender = value;
				changed = true;
			}
		} else if (PersonAttributes.KEY_MAJOR.equals(title)) {
			if (!Utils.equals(teacher.major, value)) {
				teacher.major = value;
				changed = true;
			}
		} else if (PersonAttributes.KEY_PRICE.equals(title)) {
			int price = Utils.toInteger(value);
			if (teacher.price != price) {
				teacher.price = price;
				changed = true;
			}
		} else if (PersonAttributes.KEY_MODE.equals(title)) {
			if (!Utils.equals(teacher.mode, value)) {
				teacher.mode = value;
				changed = true;
			}
		} else if (PersonAttributes.KEY_REGION.equals(title)) {
			if (!Utils.equals(teacher.region, value)) {
				teacher.region = value;
				changed = true;
			}
		} else if (PersonAttributes.KEY_EXPERIENCE.equals(title)) {
			if (!Utils.equals(teacher.experience, value)) {
				teacher.experience = value;
				changed = true;
			}
		} else if (PersonAttributes.KEY_DESC.equals(title)) {
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
		AttributeItem item = adapter.getItem(position);
		if (item == null)
			return;
		String attr = item.label.toString();
		if (PersonAttributes.KEY_NAME.equals(attr)) {
			editText(PersonAttributes.KEY_NAME, teacher.name, true);
		} else if (PersonAttributes.KEY_GENDER.equals(attr)) {
			editSelect(PersonAttributes.KEY_GENDER, teacher.gender, new String[]{"男", "女"});
		} else if (PersonAttributes.KEY_MAJOR.equals(attr)) {
			editSelect(PersonAttributes.KEY_MAJOR, teacher.major, new String[]{"钢琴"});
		} else if (PersonAttributes.KEY_PRICE.equals(attr)) {
			editNumber(PersonAttributes.KEY_PRICE, teacher.price);
		} else if (PersonAttributes.KEY_MODE.equals(attr)) {
			editSelect(PersonAttributes.KEY_MODE, teacher.mode, new String[]{"学生上门", "老师上门", "两都皆可"});
		} else if (PersonAttributes.KEY_REGION.equals(attr)) {
			String[] areas = getResources().getStringArray(R.array.shanghai_regions);
			editSelect(PersonAttributes.KEY_REGION, teacher.region, areas);
		} else if (PersonAttributes.KEY_EXPERIENCE.equals(attr)) {
			editTextarea(PersonAttributes.KEY_EXPERIENCE, teacher.experience);
		} else if (PersonAttributes.KEY_DESC.equals(attr)) {
			editTextarea(PersonAttributes.KEY_DESC, teacher.desc);
		}
	}
	
}
