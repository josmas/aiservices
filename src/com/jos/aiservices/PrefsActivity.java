package com.jos.aiservices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.jos.aiservices.services.ReadFromWebService;

public class PrefsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

  private static final String TAG = "PrefsActivity";
  private SharedPreferences prefs;
  SharedPreferences.Editor editor;
  private boolean enabled;
  private long interval;

  @Override
  protected void onCreate(Bundle state) {
    super.onCreate(state);

    ListPreference listP = new ListPreference(this);
    listP.setTitle("Set the Interval");
    
    listP.setEntries(new String [] {"Cancel Readings", "Every 10 seconds", "Every 30 seconds", "Every 5 minutes"}); // set displayed text
    listP.setEntryValues(new String [] {"0", "10000", "30000", "300000"}); // set associated values
    listP.setKey("interval");

    PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
    root.addPreference(listP);
    setPreferenceScreen(root);

    prefs = PreferenceManager.getDefaultSharedPreferences(this); 
    editor = prefs.edit();
    prefs.registerOnSharedPreferenceChangeListener(this);
    enabled = prefs.getBoolean("enabled", false);
    setEnabled(enabled);
    interval = new Long(prefs.getString("interval", "0")).longValue();
    setInterval(interval);
    
  }

  private void setInterval(long interval) {
    this.interval = interval;
  }

  private void setEnabled(boolean enabled) {
    this.enabled = enabled;

  }

  @Override
  protected void onStop() {
    super.onStop();
    //This is only a demo on how to store content. Might come from the Form when I move this to AI.
    editor.putBoolean("enabled", enabled);
    editor.commit();  
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    
    if (key.equals("interval")){
      interval = new Long(prefs.getString("interval", "0")).longValue();
      Log.d(TAG, " --- onSharedPreferenceChanged was called: setting an alarm every " + (interval / 1000)
          + " seconds (pending intent) : changed key is: " + key);

      AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
      PendingIntent pi = PendingIntent.getService(this, 0,
          new Intent(this, ReadFromWebService.class), PendingIntent.FLAG_UPDATE_CURRENT);

      if (interval == 0l)
        am.cancel(pi);
      else
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval,
            interval, pi);      
    }
    //else ignore all other keys
  }
}
