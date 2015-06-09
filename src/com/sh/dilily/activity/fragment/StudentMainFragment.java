package com.sh.dilily.activity.fragment;

import java.util.ArrayList;

import org.taptwo.android.widget.AutoViewFlow;
import org.taptwo.android.widget.CircleFlowIndicator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.sh.dilily.R;
import com.sh.dilily.activity.TeacherInfoActivity;
import com.sh.dilily.data.Teacher;
import com.sh.dilyly.adapter.list.BannerAdapter;
import com.sh.dilyly.adapter.list.TeacherListAdaper;

public class StudentMainFragment extends Frame implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
	
	private AutoViewFlow viewFlow;
	
	private int regionSelection = -1;
	private int genderSelection = -1;
	private int sortSelection = -1;

	public View onCreateView(LayoutInflater inflater) {
		View view = View.inflate(getContext(), R.layout.student_main, null);
		initBanners(view);
//		initSpinners(view);
		initListView(view);
		return view;
	}
	
	private void initBanners(View view) {
		viewFlow = (AutoViewFlow) view.findViewById(R.id.viewflow);
		BannerAdapter adapter = new BannerAdapter(getActivity());
		viewFlow.setAdapter(adapter);
//		viewFlow.setOnItemClickListener(adapter);
//		viewFlow.setSelection(adapter.getActualCount() * 100 - 1);
		CircleFlowIndicator indic = (CircleFlowIndicator) view.findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);
		viewFlow.startPlay();
	}
	
	private void initSpinners(View view) {
		Spinner regionSpinner = (Spinner)view.findViewById(R.id.regions);
		ArrayAdapter<CharSequence> regionAdapter = ArrayAdapter.createFromResource(getContext(), R.array.shanghai_regions, android.R.layout.simple_spinner_item);
		regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		regionSpinner.setAdapter(regionAdapter);
		regionSpinner.setOnItemSelectedListener(this);
		
		Spinner genderSpinner = (Spinner)view.findViewById(R.id.teacher_genders);
		ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.teacher_genders, android.R.layout.simple_spinner_item);
		genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		genderSpinner.setAdapter(genderAdapter);
		genderSpinner.setOnItemSelectedListener(this);
		
		Spinner sortSpinner = (Spinner)view.findViewById(R.id.sort_by);
		ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sort_by, android.R.layout.simple_spinner_item);
		sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sortSpinner.setAdapter(sortAdapter);
		sortSpinner.setOnItemSelectedListener(this);
//		sortSpinner.setSelection(position);
	}

	private void initListView(View view) {
		ListView lv = null;
		if (view == null)
			lv = (ListView) findViewById(R.id.student_teachers_list);
		else
			lv = (ListView) view.findViewById(R.id.student_teachers_list);
		TeacherListAdaper sla = new TeacherListAdaper(getActivity());
		ArrayList<Teacher> data = new ArrayList<Teacher>();
		for (int i = 0; i < 8; i++) {
			Teacher item = new Teacher();
			item.name = "Lily" + (i + 1);
			item.major = "piano";
			item.desc = "认真负责，完成每个孩子的梦想";
			item.price = 300;
			item.region = "黄浦区";
			item.distance = 800;
			data.add(item);
		}
		lv.setAdapter(sla);
		sla.setData(data);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Context context = getContext();
		Intent intent = new Intent(context, TeacherInfoActivity.class);
		intent.putExtra("teacherId", id);
		intent.putExtra("position", position);
		getActivity().startActivity(intent);
//		Toast.makeText(getContext(), position + " / " + id, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		boolean changed = false;
		int vid = parent.getId();
		if (vid == R.id.regions) {
			changed = regionSelection != position;
		} else if (vid == R.id.teacher_genders) {
			changed = genderSelection != position;
		} else if (vid == R.id.sort_by) {
			changed = sortSelection != position;
		}
		if (changed) {
			initListView(null);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (viewFlow != null)
			viewFlow.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (viewFlow != null)
			viewFlow.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (viewFlow != null)
			viewFlow.onDestroy();
	}
}
