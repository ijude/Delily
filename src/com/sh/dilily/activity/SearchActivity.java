package com.sh.dilily.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.sh.dilily.R;
import com.sh.dilyly.adapter.list.TeacherListAdaper;

public class SearchActivity extends DelilyActivity {
	
	private ListView listview;
	private TeacherListAdaper adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initContentView();
	}
	
	private void initContentView() {
		setContentView(R.layout.activity_search);
		listview = (ListView)findViewById(R.id.listview);
		adapter = new TeacherListAdaper(this);
		listview.setAdapter(adapter);
		listview.addFooterView(View.inflate(this, R.layout.listitem_loading, null));
	}
	
}
