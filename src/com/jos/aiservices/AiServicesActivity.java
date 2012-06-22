package com.jos.aiservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
public class AiServicesActivity extends Activity {

	private static final String TAG = "AiServicesActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d(TAG, "AiServicesActivity created");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    addPrefsButton(menu);
    addExitButtonToMenu(menu);
    
    return true;
  }
  
  public void addPrefsButton(Menu menu) {
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

}
