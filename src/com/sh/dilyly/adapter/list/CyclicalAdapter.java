package com.sh.dilyly.adapter.list;

import android.widget.BaseAdapter;

public abstract class CyclicalAdapter extends BaseAdapter {
	
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	public abstract int getActualCount();

}
