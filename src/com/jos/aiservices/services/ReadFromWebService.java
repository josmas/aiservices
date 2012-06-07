package com.jos.aiservices.services;

import com.jos.aiservices.AiServicesActivity;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReadFromWebService extends IntentService {
	
	private static final String TAG = "ReadFromWebService";
	private static final int READ_FROM_WEB_ID = 1;

	public ReadFromWebService() {
		super("ReadFromWebService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "(Mocked)Reading from the WEB on a 15 minutes interval");
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		
		mNotificationManager.notify(READ_FROM_WEB_ID, createNotification());
	}

	private Notification createNotification() {
		
		int icon = android.R.drawable.btn_default;
		CharSequence tickerText = "15 minute interval notification!";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		
		Context context = getApplicationContext();
		CharSequence contentTitle = "My 15 minute notification";
		CharSequence contentText = "Hello 15!";
		Intent notificationIntent = new Intent(this, AiServicesActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		
		return notification;
		
	}

}
