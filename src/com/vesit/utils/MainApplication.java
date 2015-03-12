package com.vesit.utils;

import android.app.Application;

import com.pushbots.push.Pushbots;

public class MainApplication extends Application {
	private String PUSHBOTS_APPLICATION_ID = "53ae97d31d0ab1505e8b457a";
	private String SENDER_ID = "441064501930";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Pushbots.init(this, SENDER_ID, PUSHBOTS_APPLICATION_ID);
		Pushbots.getInstance().setMsgReceiver(NotificationHandler.class);

	}

}
