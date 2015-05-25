package com.sh.dilily.activity.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.provider.Telephony.Mms.Rate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sh.dilily.R;
import com.sh.dilily.data.STeacherData;

public class SListAdaper extends BaseAdapter {

	public Context mContext;
	public ArrayList<STeacherData> mData = new ArrayList<STeacherData>();
	
	public SListAdaper(Context context){
		mContext = context;
	}
	public void setData(ArrayList<STeacherData> data){
		mData.clear();
		mData.addAll(data);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public STeacherData getItem(int position) {
		if (position >= 0 && position < mData.size()) {
			return mData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View root = null;
		if(convertView == null){
			root = View.inflate(mContext, R.layout.list_item_teacher_info, null);
		}else{
			root = convertView;
		}
		
		STeacherData data = getItem(position);
		TextView name = (TextView)root.findViewById(R.id.title);
		name.setText(data.name);
		
		TextView type = (TextView)root.findViewById(R.id.major);
		type.setText(data.type);
		
		RatingBar rb = (RatingBar)root.findViewById(R.id.ratingBar);
		rb.setNumStars(5);
		rb.setRating(5);
		
		TextView desc = (TextView)root.findViewById(R.id.desc);
		desc.setText(data.desc);
		
		TextView cost = (TextView)root.findViewById(R.id.fee);
		cost.setText("￥"+data.price+"起");
		
		TextView region = (TextView)root.findViewById(R.id.area);
		region.setText(data.region);
		
//		TextView distance = (TextView)root.findViewById(R.id.title);
//		name.setText(data.distance+"m");
		return root;
	}

}
