package com.sh.dilily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabHost;

public class QQTabHost extends TabHost {
	
	private OnTabSelectionListener mOnTabSelectionListener;

	public QQTabHost(Context context) {
		super(context);
	}

	public QQTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public static interface OnTabSelectionListener {
		public void onTabSelected(int pre, int cur, QQTabHost from);
	}

	public void setOnTabSelectionListener(OnTabSelectionListener l) {
		mOnTabSelectionListener = l;
	}
	
	public void setCurrentTab(int index) {
		int count = getTabWidget().getTabCount();
		if(index < 0 || index >= count) {
			return;
		}
		
		int pre = getCurrentTab();
		super.setCurrentTab(index);
		if(null != mOnTabSelectionListener) {
			mOnTabSelectionListener.onTabSelected(pre, index, this);
		}
	}
}
