package com.jos.aiservices.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.jos.aiservices.services.ReadFromWebService;

public class BootUpReceiver extends BroadcastReceiver {
	
	private static final String TAG = "BootUpReceiver";
	private SharedPreferences prefs;

	@Override
	public void onReceive(Context context, Intent intent) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        long interval = new Long( prefs.getString("webinterval", "0") ).longValue();
        
        if (interval != 0l){
        	Log.d(TAG, "Boot up Receiver was called: onReceive : setting an alarm every " + interval + " minutes (pending intent)");
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pi = PendingIntent.getService(context, 0, new Intent(context, ReadFromWebService.class), PendingIntent.FLAG_UPDATE_CURRENT);
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, interval, pi);        	
        }
	}

}
