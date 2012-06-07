package com.jos.aiservices.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class ReadFromWebService extends IntentService {
	
	private static final String TAG = "ReadFromWebService";

	public ReadFromWebService() {
		super("ReadFromWebService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "(Mocked)Reading from the WEB on a 15 minutes interval");
	}

}
