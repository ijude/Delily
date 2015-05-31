package com.sh.dilily.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sh.dilily.R;
import com.sh.dilily.activity.ChatActivity;
import com.sh.dilily.data.Message;
import com.sh.dilyly.adapter.list.MessageListAdapter;

public abstract class MessageFragment extends Frame implements AdapterView.OnItemClickListener {

	protected static final int CHAT_REQUEST_CODE = 1234;
	protected MessageListAdapter adapter;
	protected List<Message> msgs;
	
	@Override
	public void fillData() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater) {
		View view = View.inflate(getContext(), R.layout.teacher_message, null);
		setTitle(view, null, null, null);
		ListView list = (ListView)view.findViewById(R.id.listview);
		adapter = new MessageListAdapter(getMessages());
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		return view;
	}
	
	public List<Message> getMessages() {
		if (msgs != null)
			return msgs;
		//TODO 加载消息
		List<Message> messages = new ArrayList<Message>();
		int count = (int)(Math.random() * 10 + 1);
		for (int i=0; i<count; i++) {
			Message msg = new Message();
			msg.id = 1000 + i;
			msg.time = "2015-05-29";
			msg.unread = Math.random() * 10 > 5;
			msg.fromUser = "某人" + i;
			msg.msg = "想请你上课，请快快联系我吧！谢谢";
			messages.add(msg);
		}
		this.msgs = messages;
		return messages;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHAT_REQUEST_CODE) {
			if (resultCode > 0) {
				//TODO update msg
				this.msgs = null;
				adapter.setMessages(getMessages());
			}
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Message msg = (Message)adapter.getItem(position);
		Intent intent = new Intent(getContext(), ChatActivity.class);
		intent.putExtra("user", msg.fromUser);
		startActivityForResult(intent, CHAT_REQUEST_CODE);
	}
	
}
