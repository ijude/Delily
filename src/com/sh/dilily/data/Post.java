package com.sh.dilily.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POST请求数据
 * */
public class Post implements Parcelable {

	private String url;
	private List<NameValuePair> pairs;
	
	public Post(Parcel source) {
		url = source.readString();
		int count = source.readInt();
		pairs = new ArrayList<NameValuePair>(count);
		for (int i=0; i<count; i++) {
			String name = source.readString();
			String value = source.readString();
			pairs.add(new BasicNameValuePair(name, value));
		}
	}
	
	public Post(String url, List<NameValuePair> pairs) {
		this.setUrl(url);
		this.setPairs(pairs);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<NameValuePair> getPairs() {
		return pairs;
	}

	public void setPairs(List<NameValuePair> pairs) {
		this.pairs = pairs;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(url);
		if (pairs == null || pairs.size() == 0) {
			dest.writeInt(0);
		} else {
			int count = pairs.size();
			dest.writeInt(count);
			for (NameValuePair nv : pairs) {
				dest.writeString(nv.getName());
				dest.writeString(nv.getValue());
			}
		}
	}

	public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {

		@Override
		public Post createFromParcel(Parcel source) {
			return new Post(source);
		}

		@Override
		public Post[] newArray(int size) {
			return new Post[size];
		}
		
	};
}
