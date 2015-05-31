package com.sh.dilyly.adapter.list;

import java.util.List;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.dilily.R;
import com.sh.dilily.data.Message;

public class MessageListAdapter extends BaseAdapter {
	
	private List<Message> messages;
	
	public MessageListAdapter(List<Message> messages) {
		this.messages = messages;
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
		notifyDataSetChanged();
	}
	
	public void appendMessages(List<Message> messages) {
		this.messages.addAll(messages);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return messages == null ? 0 : messages.size();
	}

	@Override
	public Object getItem(int position) {
		return position < 0 || messages == null || position >= messages.size() ? null : messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		Message msg = (Message)getItem(position);
		return msg == null ? 0 : msg.id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message msg = (Message)getItem(position);
		if (msg == null)
			return null;
		if (convertView == null)
			convertView = View.inflate(parent.getContext(), R.layout.message_listitem, null);
		TextView timeView = (TextView)((ViewGroup)convertView).findViewById(R.id.message_time);
		ImageView unreadView = (ImageView)((ViewGroup)convertView).findViewById(R.id.message_unread_mark);
		TextView msgView = (TextView)((ViewGroup)convertView).findViewById(R.id.message_body);
		timeView.setText(msg.time);
		unreadView.setVisibility(msg.unread ? View.VISIBLE : View.GONE);
		
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(msg.fromUser + " " + msg.msg);
		ForegroundColorSpan span = new ForegroundColorSpan(0xCC0000CC);
		builder.setSpan(span, 0, msg.fromUser.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		msgView.setText(builder);
		return convertView;
	}

}
