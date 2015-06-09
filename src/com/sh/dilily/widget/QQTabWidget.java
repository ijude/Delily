package com.sh.dilily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TabWidget;

public class QQTabWidget extends TabWidget {
	
	private float mPosX = 0;
	private float mPosY = 0;
	private OnTabWidgetTouchMoveListener mMoveListener;
	private boolean mNotified = false;

	private static final int MOVE_MINI_LENGHT = 50;

	public QQTabWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QQTabWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			mNotified = false;
			mPosX = ev.getX();
			mPosY = ev.getY();
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			float posX = ev.getX();
			float posY = ev.getY();
			if (posY < mPosY && mPosY - posY > MOVE_MINI_LENGHT
					&& (mPosY - posY) > (Math.abs(mPosX - posX))) {
				if (mMoveListener != null && !mNotified) {
					mNotified = true;
					mMoveListener.onTabWidgetMove();
				}
			}
			break;
		}
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL: {
			break;
		}

		default:
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	public void setTabWidgetMoveListener(OnTabWidgetTouchMoveListener listener) {
		mMoveListener = listener;
	}

	public static interface OnTabWidgetTouchMoveListener {
		public void onTabWidgetMove();
	}

}