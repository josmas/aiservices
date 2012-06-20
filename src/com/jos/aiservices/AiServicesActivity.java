package com.jos.aiservices;

import com.jos.aiservices.services.ReadFromWebService;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * This is the main activity and it does nothing other than showing a message
 * It is also a shared preferences listener and provides a menu button to
 * access the preferences activity in which the Interval for the ReadFromWebService
 * can be set.
 * @author jos
 *
 */
public class AiServicesActivity extends Activity implements OnSharedPreferenceChangeListener {

	private static final String TAG = "AiServicesActivity";
	private SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d(TAG, "AiServicesActivity created");
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this); 
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemPrefs:
			startActivity(new Intent(this, PrefsActivity.class));
			break;
		}
		return true;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		long interval = new Long( prefs.getString("webinterval", "0") ).longValue();
        Log.d(TAG, "Boot up Receiver was called: onReceive : setting an alarm every " + interval + " minutes (pending intent)");
        
        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getService(this, 0, new Intent(this, ReadFromWebService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        //TODO If inexact repeating is called with a non-defined alarm interval then it's like calling setRepeating <-- convert intervals into alarms
        if (interval == 0l)
        	am.cancel(pi);
        else
        	am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, interval, pi);
		
	}
}
