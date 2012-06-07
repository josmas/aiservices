package com.jos.aiservices.bootup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jos.aiservices.AiServicesActivity;
import com.jos.aiservices.services.ReadFromWebService;

public class BootUpReceiver extends BroadcastReceiver {
	
	private static final String TAG = "BootUpReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {	
        Log.d(TAG, "Boot up Receiver was called: onReceive : setting an alarm every 15 minutes (pending intent)");
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getService(context, 0, new Intent(context, ReadFromWebService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi);
        
	}

}
