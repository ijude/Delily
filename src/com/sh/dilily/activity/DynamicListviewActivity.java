package com.sh.dilily.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AbsListView;

import com.sh.dilily.R;

public class DynamicListviewActivity extends DelilyActivity implements AbsListView.OnScrollListener {

	protected ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected void initDynamicListView() {
		listview.addFooterView(View.inflate(this, R.layout.listitem_loading, null));
		listview.setOnScrollListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}
	
}
