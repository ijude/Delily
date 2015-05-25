package com.sh.dilily.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TabWidget;

public class QQTabWidget extends TabWidget {
	private static final int SPLIT_WIDTH   = 27;
	private Paint mPaint = new Paint();
//	private Bitmap mBg = BitmapFactory.decodeResource(getResources(), R.drawable.bottom_bar_background_noskin);
//	private Bitmap mDivider = BitmapFactory.decodeResource(getResources(), R.drawable.tab_divider);
	private RectF mDestRect = new RectF();
	
	private float mPosX = 0;
	private float mPosY = 0;
	private onTabWidgetTouchMoveListener mMoveListener;
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
//		QLog.d("MainActivity", QLog.CLR, "QQTabWidget onInterceptTouchEvent. action="+ev.getAction()
//    			+",x="+ev.getX()+",y="+ev.getY());
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
		{
			mNotified = false;
			mPosX = ev.getX();
			mPosY = ev.getY();
			break;
		}
		case MotionEvent.ACTION_MOVE:
		{
			float posX = ev.getX();
			float posY = ev.getY();
			if(posY < mPosY && mPosY-posY>MOVE_MINI_LENGHT 
					&& (mPosY-posY)>(Math.abs(mPosX-posX)))
			{
				if(mMoveListener!=null && !mNotified)
				{
					mNotified = true;
					mMoveListener.onTabWidgetMove();
				}
			}
			break;
		}
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
		{
			break;
		}

		default:
			break;
		}
		
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		QLog.d("MainActivity", QLog.CLR, "QQTabWidget onTouchEvent. action="+event.getAction()
//    			+",x="+event.getX()+",y="+event.getY());
		return super.onTouchEvent(event);
	}
	
	public void setTabWidgetMoveListener(onTabWidgetTouchMoveListener listener)
	{
		mMoveListener = listener;
	}
	
	public static interface onTabWidgetTouchMoveListener
	{
		public void onTabWidgetMove();
	}
	
//	@Override
//	public void onDraw(Canvas canvas) {
//	
//		mDestRect.left = 0;
//		mDestRect.top = 0;
//		mDestRect.right = getWidth();
//		mDestRect.bottom = getHeight();
//		
//		canvas.drawBitmap(mBg, null, mDestRect, mPaint);
//		for(int i = 1; i < getTabCount(); i++) {
//			int xPos = i * getWidth() / getTabCount() - SPLIT_WIDTH / 2;
//			mDestRect.left = xPos;
//			mDestRect.right = xPos + SPLIT_WIDTH;
//			canvas.drawBitmap(mDivider, null, mDestRect, mPaint);
//		}
//		
//		
//		super.onDraw(canvas);
//	}
}