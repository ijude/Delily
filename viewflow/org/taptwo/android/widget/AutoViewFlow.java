package org.taptwo.android.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AutoViewFlow extends ViewFlow implements Playable {
	
	private Player player;

	public AutoViewFlow(Context context) {
		super(context);
	}

	public AutoViewFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoViewFlow(Context context, int sideBuffer) {
		super(context, sideBuffer);
	}
	
	public void onPause() {
		if (player != null)
			player.pause();
	}
	
	public void onResume() {
		if (player != null)
			player.resume();
	}
	
	public void onDestroy() {
		if (player != null) {
			player.destroy();
			player = null;
		}
	}
	
	public void startPlay() {
		if (player == null)
			player = new Player(this, 5000);
		player.start();
	}
	
	public void stopPlay() {
		if (player != null) {
			player.destroy();
			player = null;
		}
	}
	
	@Override
	public void onTick() {
		this.snapToNext();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (player != null)
				player.pause();
			break;
		case MotionEvent.ACTION_UP:
			if (player != null)
				player.resume();
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	private static class Player extends Handler {
		private static final int MESSAGE = 1;
		private Playable callback;
		private int interval;
		
		public Player(Playable callback, int interval) {
			this.callback = callback;
			this.interval = interval;
		}
		
		@Override
		public void handleMessage(Message msg) {
			if (callback != null) {
				callback.onTick();
				resume();
			}
		}
		
		public void start() {
			removeMessages(MESSAGE);
			sendEmptyMessageDelayed(MESSAGE, interval + 1000);
		}
		
		public void resume() {
			removeMessages(MESSAGE);
			sendEmptyMessageDelayed(MESSAGE, interval);
		}
		
		public void pause() {
			removeMessages(MESSAGE);
		}
		
		public void destroy() {
			removeMessages(MESSAGE);
			this.callback = null;
		}

	}

}

