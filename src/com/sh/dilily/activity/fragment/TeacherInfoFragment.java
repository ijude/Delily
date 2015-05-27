package com.sh.dilily.activity.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.sh.dilily.R;
import com.sh.dilily.data.Teacher;
import com.sh.dilyly.adapter.list.TeacherInfoAdapter;


public class TeacherInfoFragment extends Frame {

	@Override
	public void fillData() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater) {
		View view = View.inflate(getContext(), R.layout.teacher_info, null);
		setTitle(view, null, null, "编辑");
		initListView(view);
		return view;
	}
	
	private Teacher getTeacher() {
		//TODO 获取当前老师资料
		Teacher t = new Teacher();
		t.id = 1;
		t.name = "Alice";
		t.gender = "男";
		t.major = "唱歌、跳舞";
		t.mode = "学生上门";
		t.phone = "13899999999";
		t.price = 250;
		t.rate = 5;
		t.region = "上海";
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
	}
}
