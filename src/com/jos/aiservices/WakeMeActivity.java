package com.jos.aiservices;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jos.aiservices.services.WakeUpService;

public class WakeMeActivity extends Activity {

  private static final String TAG = "WakeMeActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // setting up the service
    AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
    PendingIntent pi = PendingIntent.getService(this, 0, new Intent(this, WakeUpService.class),
        PendingIntent.FLAG_UPDATE_CURRENT);
    am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_HALF_HOUR,
        AlarmManager.INTERVAL_HALF_HOUR, pi);

    Log.d(TAG, "WakeMeActivity created");

  }

  //Broadcast Receiver class
  public static class MyBroadcastMessageReceiver extends BroadcastReceiver {

    private static final int MY_BC_M_RECEIVER = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
      // Create a notification to awake this activity?
      Log.d(TAG, "onReceive at MyBroadcastMessageReceiver <----");

      String ns = Context.NOTIFICATION_SERVICE;
      NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(ns);

      mNotificationManager.notify(MY_BC_M_RECEIVER, createNotification(context));
    }

    private Notification createNotification(Context context) {
      Log.d(TAG, "Create Notification in Broadcast receiver Called");
      int icon = android.R.drawable.btn_default;
      CharSequence tickerText = "Interval notification!";
      long when = System.currentTimeMillis();

      Notification notification = new Notification(icon, tickerText, when);
      notification.flags = Notification.FLAG_AUTO_CANCEL;

      CharSequence contentTitle = "My Broadcast notification";
      CharSequence contentText = "Hello BROADCAST ALARM!";
      Intent notificationIntent = new Intent(context, WakeMeActivity.class);//TODO do I know this class in AI? NOPE -- problem!
      PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

      notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

      return notification;
    }

  }
}


