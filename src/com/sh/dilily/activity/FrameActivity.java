package com.sh.dilily.activity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

import com.sh.dilily.activity.fragment.Frame;
import com.sh.dilily.widget.QQTabHost;
import com.sh.dilily.widget.QQTabHost.OnTabSelectionListener;

public class FrameActivity extends DililyActivity implements TabContentFactory,
		OnTabChangeListener {
	protected TabHost mTabHost;
	private final Map<String, Frame> mFrames = new HashMap<String, Frame>(2);
	private Frame preFrame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (getCurrentFrame() != null) {
			getCurrentFrame().onResume();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (getCurrentFrame() != null) {
			getCurrentFrame().onPause();
		}
	}

	public void addFrame(Class<? extends Frame> clz, View tab) {
		if (mTabHost == null) {
			mTabHost = (TabHost) findViewById(android.R.id.tabhost);
			mTabHost.setup();
			mTabHost.setOnTabChangedListener(this);
			if (mTabHost instanceof QQTabHost) {
				QQTabHost tabHost = (QQTabHost) mTabHost;
				tabHost.setOnTabSelectionListener(new OnTabSelectionListener() {

					@Override
					public void onTabSelected(int pre, int cur, QQTabHost from) {
						if (pre == cur) {
							// 只放开需求的部分
							Frame f = getCurrentFrame();
							if (null != f) {
								f.onFrameTabClick();
							}
						}
					}
				});
			}
		}
		TabSpec spec = mTabHost.newTabSpec(clz.getName()).setIndicator(tab);
		spec.setContent(this);
		mTabHost.addTab(mTabHost.newTabSpec(clz.getName()).setIndicator(tab).setContent(this));
	}

	/**
	 * 返回titlebar的高度 in pixels
	 */
	public int getTitleBarHeight() {
		return 0;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getCurrentFrame().onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Collection<Frame> all = mFrames.values();
		for (Frame frame : all) {
			frame.onConfigurationChanged(newConfig);
		}
	}

	@Override
	public View createTabContent(String tag) {
		final String className = tag;
		View content = null;
		Frame frame = null;
		try {
			frame = (Frame) Class.forName(className).newInstance();
		} catch (Exception e) {
			return null;
		}
		frame.setActivity(this);
		content = frame.onCreateView(getLayoutInflater());
		frame.setContentView(content);
		frame.onCreate();
		mFrames.put(className, frame);
		return content;
	}

	@Override
	public void onTabChanged(String tabId) {
		if (preFrame != null) {
			preFrame.onPause();
		}
		preFrame = getCurrentFrame();
		preFrame.onResume();
	}

	protected Frame getCurrentFrame() {
		if (mTabHost == null) {
			return null;
		}
		return mFrames.get(mTabHost.getCurrentTabTag());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Collection<Frame> all = mFrames.values();
		for (Frame frame : all) {
			frame.onDestroy();
		}
	}

	public int getCurrentTab() {
		return mTabHost.getCurrentTab();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		String currentTabTag = mTabHost.getCurrentTabTag();
		if (currentTabTag != null) {
			outState.putString("currentTab", currentTabTag);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		String cur = savedInstanceState.getString("currentTab");
		if (cur != null) {
			mTabHost.setCurrentTabByTag(cur);
		}
	}
}
