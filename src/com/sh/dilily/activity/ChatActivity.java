package com.sh.dilily.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sh.dilily.R;
import com.sh.dilily.data.Message;
import com.sh.dilily.handler.DililyHandler;
import com.sh.dilyly.util.Utils;

public class ChatActivity extends DelilyActivity implements
		View.OnClickListener, View.OnKeyListener {
	private static final int MESSAGE_SCROLL_BOTTOM = 1;
	
	private ChatHandler handler;
	private MessageReceiver receiver;
	
	private List<Message> messages;
	private ScrollView scrollView;
	private EditText editText;
	private String me = "me";
//	private String you = "you";
	private int textMaxWidth = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity);
		
		initInterfaces();
		initService();
		registerReceiver();
		initHandler();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (receiver != null)
			unregisterReceiver(receiver);
		if (handler != null) {
			handler.release();
			handler = null;
		}
//		stopService(new Intent(this, NetworkService.class));
	}
	
	private void initInterfaces() {
		setTitle(null, "某某人", "返回", null);
		scrollView = (ScrollView)findViewById(R.id.scrollview);
		editText = (EditText)findViewById(R.id.edittext);
		loadMessages();
		initScrollView();
		setClickListener(new int[]{R.id.send_message}, this);
		editText.setOnKeyListener(this);
	}
	
	private void initService() {
//		startService(new Intent(this, NetworkService.class));
	}
	
	private void initHandler() {
		handler = new ChatHandler(this) {
			@Override
			public boolean handleMessage(ChatActivity activity, int what) {
				switch (what) {
				case MESSAGE_SCROLL_BOTTOM:
					activity.scrollBottom();
					break;
				default:
					return false;
				}
				return true;
			}
		};
		handler.sendEmptyMessageDelayed(MESSAGE_SCROLL_BOTTOM, 50);
	}
	
	private void scrollBottom() {
		if (scrollView != null)
			scrollView.fullScroll(ScrollView.FOCUS_DOWN);
	}
	
	private void registerReceiver() {
		MessageReceiver receiver = new MessageReceiver();  
        IntentFilter intentFilter = new IntentFilter();  
        intentFilter.addAction("com.sh.dilily.message.RECEIVER");  
        registerReceiver(receiver, intentFilter);
//		unregisterReceiver(receiver);
	}
	
	private List<Message> loadMessages() {
		if (this.messages != null)
			return this.messages;
		List<Message> messages = new ArrayList<Message>();
		String[] msgs = {
				"你好", 
				"以前没想过在百度贴吧上提问的，现在这里是个学习的好地方", "很高兴认识你，谢谢你的帮助。", 
				"Jude",
				"小黄鱼-左.png小黄鱼-左.png小黄鱼-左.png小黄鱼-左.png小黄鱼-左.png小黄鱼-左.png小黄鱼-左.png小黄鱼-左.png",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"有网友表示对于很多工程中的MATCH_PARENT出现在layout中感到不明白，过去只有FILL_PARENT和WRAP_CONTENT那么match_parent到底是什么类型呢? 其实从Android 2.2开始FILL_PARENT改名为MATCH_PARENT ，从API Level为8开始我们可以直接用MATCH_PARENT来代替FILL_PARENT，最后Android123提醒大家，他们的定义本质是一样均为-1，只是换了个别名，可能为了更准确些，比如最终在SDK中的定义为"
		};
		int count = (int)Math.floor(Math.random() * 10) + 5;
		for (int i=0; i<count; i++) {
			Message msg = new Message();
			msg.id = i + 254;
			msg.time = "2015-05-" + (i+1);
			msg.fromUser = Math.random() * 10 > 5 ? "me" : "you";
			msg.msg = Utils.random(msgs);
			messages.add(msg);
		}
		this.messages = messages;
		return messages;
	}
	
	@SuppressWarnings("deprecation")
	private void initScrollView() {
		ViewGroup container = (ViewGroup)scrollView.getChildAt(0);
		WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
		int screenWidth = wm.getDefaultDisplay().getWidth();
		textMaxWidth = screenWidth - 190;
		for (Message msg : messages) {
			appendMessage(container, msg, false);
		}
	}
	
	private void appendMessage(ViewGroup container, Message msg, boolean scroll) {
		if (container == null)
			container = (ViewGroup)scrollView.getChildAt(0);
		//TODO show time
		int layout = this.me.equals(msg.fromUser) ? R.layout.message_content_left : R.layout.message_content_right;
		ViewGroup view = (ViewGroup)View.inflate(getBaseContext(), layout, null);
		TextView tv = (TextView)view.findViewById(R.id.message_content);
		if (textMaxWidth > 0)
			tv.setMaxWidth(textMaxWidth);
		tv.setText(msg.msg);	//Utils.toDBC(msg.msg.toString())
		container.addView(view);
		if (scroll) {
			handler.sendEmptyMessage(MESSAGE_SCROLL_BOTTOM);
		}
	}
	
	private void uploadMessage(Message msg) {
		//TODO
	}
	
	private void doSend() {
		CharSequence msg = editText.getText();
		if (msg == null || msg.length() == 0)
			return;
		Message message = new Message();
		message.fromUser = me;
		message.time = new Date().toString();
		message.msg = msg;
		appendMessage(null, message, true);
		uploadMessage(message);
		editText.setText("");
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.send_message:
			doSend();
			break;
		default:
			super.onClick(v);
			break;
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			doSend();
			return true;
		}
		return false;
	}
	
	private class MessageReceiver extends BroadcastReceiver {  
        @Override  
        public void onReceive(Context context, Intent intent) {  
//        	int progress = intent.getIntExtra("progress", 0);  
//			mProgressBar.setProgress(progress);  
        }  
          
    }
	
	private static class ChatHandler extends DililyHandler<ChatActivity> {

		public ChatHandler(ChatActivity activity) {
			super(activity);
		}
		@Override
		public boolean handleMessage(ChatActivity activity, int what) {
			switch (what) {
			case MESSAGE_SCROLL_BOTTOM:
				activity.scrollBottom();
				break;
			default:
				break;
			}
			return false;
		}
		
	}
}
