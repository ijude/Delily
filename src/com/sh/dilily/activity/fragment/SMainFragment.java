package com.sh.dilily.activity.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;

import com.sh.dilily.R;
import com.sh.dilily.data.STeacherData;

public class SMainFragment extends Frame {
	
	public View onCreateView(LayoutInflater inflater) {
		View view = View.inflate(mActivity,R.layout.layout_s_main, null);
		ListView lv = (ListView)view.findViewById(R.id.s_main_listview);
		SListAdaper sla = new SListAdaper(getActivity());
		ArrayList<STeacherData> data = new ArrayList<STeacherData>();
		for(int i=0;i<20;i++){
			STeacherData item = new STeacherData();
			item.name = "lily";
			item.type="piano"; 
			item.desc="认真负责，完成每个孩子的梦想";
			item.price=300;
			item.region="黄浦区";
			item.distance=800;
			data.add(item);	
		}
		
		lv.setAdapter(sla);
		sla.setData(data);
		return view;
    }
	
//	public void onCreate(Bundle savedInstanceState) {
//	}
	
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View v = inflater.inflate(R.layout.layout_s_main, null);
//		
//		
//		return v;
//	}
//	
//	public void onViewCreated(View view, Bundle savedInstanceState) {
//		ListView lv = (ListView)view.findViewById(R.id.s_main_listview);
//		SListAdaper sla = new SListAdaper(getActivity());
//		ArrayList<STeacherData> data = new ArrayList<STeacherData>();
//		for(int i=0;i<20;i++){
//			STeacherData item = new STeacherData();
//			item.name = "lily";
//			item.type="piano";
//			item.desc="认真负责，完成每个孩子的梦想";
//			item.price=300;
//			item.region="黄浦区";
//			item.distance=800;
//				
//		}
//		
//		lv.setAdapter(sla);
//		sla.setData(data);
////		super.onViewCreated(view, savedInstanceState);
//	}

//	public View createTabContent(String tag) {
//		View view = View.inflate(mActivity,R.layout.layout_s_main, null);
//		ListView lv = (ListView)view.findViewById(R.id.s_main_listview);
//		SListAdaper sla = new SListAdaper(getActivity());
//		ArrayList<STeacherData> data = new ArrayList<STeacherData>();
//		for(int i=0;i<20;i++){
//			STeacherData item = new STeacherData();
//			item.name = "lily";
//			item.type="piano"; 
//			item.desc="认真负责，完成每个孩子的梦想";
//			item.price=300;
//			item.region="黄浦区";
//			item.distance=800;
//				
//		}
//		
//		lv.setAdapter(sla);
//		sla.setData(data);
//		return null;
//	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub
		
	}

}
