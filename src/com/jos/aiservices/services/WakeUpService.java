package com.jos.aiservices.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class WakeUpService extends IntentService {
  
  public static final String CUSTOM_INTENT = "com.jos.aiservices.android.intent.action.BroadcastReceiver";

  public WakeUpService() {
    super("WakeUpService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    //Here I do not know the name of the Activity that is going to receive this broadcast.
    Log.d("WakeUpService", "Abour to create the intent");
    Intent anotherIntent = new Intent();
    anotherIntent.setAction(CUSTOM_INTENT);
    //Grab info from intent and pass it around in the new one (if needed?)
    anotherIntent.putExtras(intent.getExtras());
    Log.d("WakeUpService", "Broadcasting the WakeUpService intent");
    this.sendBroadcast(anotherIntent);
  }

}
