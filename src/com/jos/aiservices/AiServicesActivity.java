package com.jos.aiservices;

import com.jos.aiservices.services.ReadFromWebService;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

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
    addPrefsButtonToMenu(menu);
    addExitButtonToMenu(menu);
    return true;
  }

  public void addPrefsButtonToMenu(Menu menu) {
    MenuItem stopApplicationItem = menu.add(Menu.NONE, Menu.NONE, Menu.FIRST,
    "Prefs")
    .setOnMenuItemClickListener(new OnMenuItemClickListener() {
      public boolean onMenuItemClick(MenuItem item) {
        startPrefsActivity();
        return true;
      }
    });
    stopApplicationItem.setIcon(android.R.drawable.ic_menu_preferences);
  }
  
  private void startPrefsActivity() {
    startActivity(new Intent(this, PrefsActivity.class));
  }
  
  public void addExitButtonToMenu(Menu menu) {
    MenuItem stopApplicationItem = menu.add(Menu.NONE, Menu.NONE, Menu.FIRST,
    "Stop this application")
    .setOnMenuItemClickListener(new OnMenuItemClickListener() {
      public boolean onMenuItemClick(MenuItem item) {
        showExitApplicationNotification();
        return true;
      }
    });
    stopApplicationItem.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
  }

  private void showExitApplicationNotification() {
    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    alertDialog.setTitle("Stop application?");
    // prevents the user from escaping the dialog by hitting the Back button
    alertDialog.setCancelable(false);
    alertDialog.setMessage("Stop this application and exit? You'll need to relaunch " +
    "the application to use it again.");
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Stop and exit",
        new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        // I know that this is frowned upon in Android circles but I really think that it's
        // confusing to users if the exit button doesn't really stop everything, including other
        // forms in the app (when we support them), non-UI threads, etc.  We might need to be
        // careful about this is we ever support services that start up on boot (since it might
        // mean that the only way to restart that service) is to reboot but that's a long way off.
        System.exit(0); //TODO Copied this from a different method in the Form class
      }});
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Don't stop",
        new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        // nothing to do here
      }
    });
    alertDialog.show();
  }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		long interval = new Long( prefs.getString("webinterval", "0") ).longValue();
        Log.d(TAG, "onSharedPreferenceChanged was called: setting an alarm every " + (interval / 1000) + " seconds (pending intent)");
        
        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getService(this, 0, new Intent(this, ReadFromWebService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        //TODO If inexact repeating is called with a non-defined alarm interval then it's like calling setRepeating <-- convert intervals into alarms
        if (interval == 0l)
        	am.cancel(pi);
        else
        	am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, interval, pi);
		
	}
}
