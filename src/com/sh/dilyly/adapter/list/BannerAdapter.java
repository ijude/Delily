package com.sh.dilyly.adapter.list;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.sh.dilily.R;
import com.sh.dilily.activity.WebviewActivity;
import com.sh.dilily.data.Banner;

public class BannerAdapter extends CyclicalAdapter implements OnItemClickListener, View.OnClickListener {
	
	private static final int START_ID = 100000;
	private Context context;
	private List<Banner> banners;
	
	public BannerAdapter(Context context) {
		this.context = context;
		this.banners = new ArrayList<Banner>();
		initBanners();
	}
	
	private void initBanners() {
		banners.add(new Banner(R.drawable.banner1, Banner.TYPE_INTERNAL_URL, "http://www.baidu.com"));
		banners.add(new Banner(R.drawable.banner2, Banner.TYPE_INTERNAL_URL, "http://www.qq.com"));
		banners.add(new Banner(R.drawable.banner3, Banner.TYPE_ASSETS_HTML, "piano.html"));
	}

	@Override
	public int getActualCount() {
		return banners.size();
	}

	@Override
	public Banner getItem(int position) {
		if (banners.size() > 0)
			return banners.get(position % banners.size());
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.banner_item, null);	//context mInflater.inflate(R.layout.image_item, null);
		}
		Banner banner = getItem(position);
		((ImageView) convertView.findViewById(R.id.imageview)).setImageResource(banner.getImageId());
		convertView.setOnClickListener(this);
		convertView.setId(START_ID + position);
		return convertView;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Banner banner = getItem(position);
		if (banner == null)
			return;
		String url = banner.getUrl();
		int type = banner.getType();
		Intent it = null;
		switch (type) {
		case Banner.TYPE_EXTERNAL_URL:
			it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));  
//	        it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
	        context.startActivity(it);
			break;
		case Banner.TYPE_ASSETS_HTML:
			it = new Intent(context, WebviewActivity.class);
			it.putExtra("url", banner.getAssetsUrl());
			break;
		case Banner.TYPE_INTERNAL_URL:
			it = new Intent(context, WebviewActivity.class);
			it.putExtra("url", url);
			break;
		}
		if (it != null) {
			context.startActivity(it);
		}
	}

	@Override
	public void onClick(View v) {
		int position = v.getId() - START_ID;
		if (position < 0)
			return;
		Banner banner = getItem(position);
		if (banner == null)
			return;
		String url = banner.getUrl();
		int type = banner.getType();
		Intent it = null;
		switch (type) {
		case Banner.TYPE_EXTERNAL_URL:
			it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));  
//	        it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
	        context.startActivity(it);
			break;
		case Banner.TYPE_ASSETS_HTML:
			it = new Intent(context, WebviewActivity.class);
			it.putExtra("url", banner.getAssetsUrl());
			break;
		case Banner.TYPE_INTERNAL_URL:
			it = new Intent(context, WebviewActivity.class);
			it.putExtra("url", url);
			break;
		}
		if (it != null) {
			context.startActivity(it);
		}
	}

}
