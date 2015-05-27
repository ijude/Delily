package com.sh.dilily.activity.fragment;

import com.sh.dilily.R;

import android.view.LayoutInflater;
import android.view.View;

public class TeacherMessageFragment extends Frame {

	@Override
	public void fillData() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater) {
		View view = View.inflate(getContext(), R.layout.teacher_message, null);
		setTitle(view, "返回", null, null);
		return view;
	}
}
