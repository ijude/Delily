package com.sh.dilily.activity.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sh.dilily.R;
import com.sh.dilily.data.AttributeItem;
import com.sh.dilily.data.PersonAttributes;
import com.sh.dilily.data.Student;
import com.sh.dilyly.adapter.list.StudentInfoAdapter;
import com.sh.dilyly.util.Utils;


public class StudentInfoFragment extends PersonInfoFragment {
	
	private StudentInfoAdapter adapter;
	private Student student;

	@Override
	public View onCreateView(LayoutInflater inflater) {
		View view = View.inflate(getContext(), R.layout.teacher_info, null);
		setTitle(view, null, null, null);
		initListView(view);
		return view;
	}
	
	private Student getStudent() {
		if (this.student != null)
			return this.student;
		Student t = new Student();
		t.id = 1;
		t.name = "Bruce";
		t.gender = "男";
		t.phone = "13899999999";
		t.region = "上海";
		t.address = "南京东路100号";
		t.age = 10;
		t.level = "中级";
		this.student = t;
		return t;
	}
	
	public void initListView(View view) {
		ListView lv = null;
		if (view == null)
			lv = (ListView) findViewById(R.id.teacher_info_list);
		else
			lv = (ListView) view.findViewById(R.id.teacher_info_list);
		adapter = new StudentInfoAdapter(getContext(), getStudent(), StudentInfoAdapter.VIEWPORT_STUDENT);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}
	
	protected void setField(CharSequence title, String value) {
//		Utils.toast(getContext(), title + "=" + value);
		boolean changed = false;
		if (PersonAttributes.KEY_NAME.equals(title)) {
			if (!Utils.isEmpty(value) && !Utils.equals(student.name, value)) {
				student.name = value;
				changed = true;
			}
		} else if (PersonAttributes.KEY_GENDER.equals(title)) {
			if (!Utils.equals(student.gender, value)) {
				student.gender = value;
				changed = true;
			}
		} else if (PersonAttributes.KEY_AGE.equals(title)) {
			int age = Utils.toInteger(value);
			if (age > 0 && age < 20 && student.age != age) {
				student.age = age;
				changed = true;
			}
		} else if (PersonAttributes.KEY_REGION.equals(title)) {
			if (!Utils.equals(student.region, value)) {
				student.region = value;
				changed = true;
			}
		} else if (PersonAttributes.KEY_DESC.equals(title)) {
			if (!Utils.equals(student.desc, value)) {
				student.desc = value;
				changed = true;
			}
		}
		if (changed) {
			ListView list = (ListView) findViewById(R.id.teacher_info_list);
			StudentInfoAdapter adapter = (StudentInfoAdapter)list.getAdapter();
			adapter.setData(student);
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
			editText(PersonAttributes.KEY_NAME, student.name, true);
		} else if (PersonAttributes.KEY_GENDER.equals(attr)) {
			editSelect(PersonAttributes.KEY_GENDER, student.gender, new String[]{"男", "女"});
		} else if (PersonAttributes.KEY_AGE.equals(attr)) {
			editNumber(PersonAttributes.KEY_AGE, student.age);
		} else if (PersonAttributes.KEY_ADDRESS.equals(attr)) {
			editTextarea(PersonAttributes.KEY_ADDRESS, student.address);
		} else if (PersonAttributes.KEY_REGION.equals(attr)) {
			String[] areas = getResources().getStringArray(R.array.shanghai_regions);
			editSelect(PersonAttributes.KEY_REGION, student.region, areas);
		} else if (PersonAttributes.KEY_LEVEL.equals(attr)) {
			editTextarea(PersonAttributes.KEY_LEVEL, student.desc);
		} else if (PersonAttributes.KEY_DESC.equals(attr)) {
			editTextarea(PersonAttributes.KEY_DESC, student.desc);
		}
	}
	
}
