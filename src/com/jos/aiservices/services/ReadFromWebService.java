package com.jos.aiservices.services;

import com.jos.aiservices.AiServicesActivity;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * This service is a Mock of reading from a Web Service.
 * It does nothing from now, other than sending a notification to the user
 * signaling that something happened and letting them open the activity from
 * that notification
 * @author jos
 *
 */
public class ReadFromWebService extends IntentService {
	
	private static final String TAG = "ReadFromWebService";
	private static final int READ_FROM_WEB_ID = 1;

	public ReadFromWebService() {
		super("ReadFromWebService");
		Log.d(TAG, "ReadFromWebService Created");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "(Mocked)Reading from the WEB on an specified interval");
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		
		mNotificationManager.notify(READ_FROM_WEB_ID, createNotification());
	}

	private Notification createNotification() {
		Log.d(TAG, "Create Notification Called");
		int icon = android.R.drawable.btn_default;
		CharSequence tickerText = "Interval notification!";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		
		Context context = getApplicationContext();
		CharSequence contentTitle = "My Interval notification";
		CharSequence contentText = "Hello ALARM!";
		Intent notificationIntent = new Intent(this, AiServicesActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		
		return notification;
		
	}

}
