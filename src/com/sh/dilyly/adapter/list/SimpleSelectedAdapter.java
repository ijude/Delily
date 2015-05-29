package com.sh.dilyly.adapter.list;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.dilily.R;

public class SimpleSelectedAdapter extends BaseAdapter {
//	private Context context;
	private String[] items;				//所有可选项
	private boolean multiple;			//是否多选
	private List<String> selected;		//
	
	public SimpleSelectedAdapter(String[] items) {
		this(items, (String)null);
	}
	
	public SimpleSelectedAdapter(String[] items, String selected) {
		this.items = items;
		this.selected = new ArrayList<String>();
		this.multiple = false;
		if (selected != null)
			this.selected.add(selected);
	}
	
	public SimpleSelectedAdapter(String[] items, String[] selected) {
		this.items = items;
		this.selected = new ArrayList<String>();
		for (int i = 0; i < selected.length; i++) {
			this.selected.add(selected[i]);
		}
		this.multiple = true;
	}
	
	public <T> T getSelected() {
		if (!multiple) {
			if (selected.size() > 0)
				return (T)selected.get(0);
			return null;
		}
		String[] s = new String[selected.size()];
		return (T)selected.toArray(s);
	}
	
	public <T> void setSelected(T item) {
		this.selected.clear();
		if (item != null) {
			if (multiple) {
				String[] ss = (String[])item;
				for (int i = 0; i < ss.length; i++) {
					this.selected.add(ss[i]);
				}
			} else {
				this.selected.add((String)item);
			}
		}
		notifyDataSetChanged();
	}
	
	public void toggleSelection(int position) {
		String item = (String)getItem(position);
		if (multiple) {
			if (this.selected.contains(item)) {
				selected.remove(item);
			} else {
				selected.add(item);
			}
		} else {
			this.selected.clear();
			this.selected.add(item);
		}
//		Utils.toast(context, item);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		return position + 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Context context = parent.getContext();
		if (convertView == null)
			convertView = View.inflate(context, R.layout.simple_selectable_listitem, null);	//android.R.layout.simple_selectable_list_item
		TextView textView = (TextView)((ViewGroup)convertView).getChildAt(0);
		ImageView imageView = (ImageView)((ViewGroup)convertView).getChildAt(1);
		String item = (String)getItem(position);
		textView.setText(item);
		if (item != null && selected.contains(item)) {
			textView.setTextColor(0xcc6666ff);
//			Drawable drawable = context.getResources().getDrawable(R.drawable.checked);
//			textView.setCompoundDrawables(drawable, drawable, drawable, drawable);
//			view.setSelected(true);
			imageView.setVisibility(View.VISIBLE);
		} else {
			textView.setTextColor(0xcc333333);
//			textView.setCompoundDrawables(null, null, null, null);
//			view.setSelected(false);
			imageView.setVisibility(View.GONE);
		}
		return convertView;
	}

}
