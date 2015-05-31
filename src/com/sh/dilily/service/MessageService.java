package com.sh.dilily.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 消息服务，自动到服务器获取新消息，仅在消息界面和聊天界面
 * */
public class MessageService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
