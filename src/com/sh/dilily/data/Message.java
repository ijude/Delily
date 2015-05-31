package com.sh.dilily.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {

	public int id;
	public String time;
	public String fromUser;
	public String fromUserId;
	public String toUser;
	public String toUserId;
	public CharSequence msg;
	public boolean unread;
	
	public Message() {
	}
	
	public Message(Parcel source) {
		this.id = source.readInt();
		this.time = source.readString();
		this.fromUser = source.readString();
		this.fromUserId = source.readString();
		this.toUser = source.readString();
		this.toUserId = source.readString();
		this.msg = source.readString();
		this.unread = source.readByte() > 0;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(id);
		parcel.writeString(time);
		parcel.writeString(fromUser);
		parcel.writeString(fromUserId);
		parcel.writeString(toUser);
		parcel.writeString(toUserId);
		parcel.writeString(msg.toString());
		parcel.writeByte(unread ? (byte)1 : (byte)0);
	}
	
	public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {

		@Override
		public Message createFromParcel(Parcel source) {
			return new Message(source);
		}

		@Override
		public Message[] newArray(int size) {
			return new Message[size];
		}
		
	};
}
