package com.sh.dilily.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.sh.dilily.R;
import com.sh.dilily.data.Teacher;
import com.sh.dilyly.adapter.list.TeacherInfoAdaper;

/**
 * 老师资料界面, 学生看到的
 * */
public class TeacherInfoActivity extends DililyActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_info);
		setTitle("老师信息", getString(R.string.back), null);
		ListView lv = (ListView)findViewById(R.id.teacher_info_list);
		int teacherId = getIntent().getIntExtra("teacherId", 0);
		TeacherInfoAdaper adapter = new TeacherInfoAdaper(getBaseContext(), getTeacher(teacherId));
		lv.setAdapter(adapter);
	}
	
	protected Teacher getTeacher(int teacherId) {
		Teacher t = new Teacher();
		t.id = 1;
		t.name = "Lily";
		t.gender = "女";
		t.major = "钢琴";
		t.mode = "学生上门";
		t.phone = "138888888";
		t.price = 300;
		t.rate = 4;
		t.region = "黄浦区、杨浦区";
		return t;
	}
	
}
