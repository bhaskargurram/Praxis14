package com.vesit.utils;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.pushbots.push.Pushbots;

import com.vesit.praxis14.NavigationMain;

public class NotificationHandler extends BroadcastReceiver {


	private static final String TAG = "customPushReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Log.d(TAG, "action=" + action);
		// Handle Push Message when opened
		if (action.equals(Pushbots.MSG_OPENED)) {
			HashMap<?, ?> PushdataOpen = (HashMap<?, ?>) intent.getExtras()
					.get(Pushbots.MSG_OPEN);
			Log.w(TAG, "User clicked notification with Message: "
					+ PushdataOpen.get("message"));
			// Start activity if not active
			// set the value of local variable "active" in onStart()/onStop() in
			// MainActivity
			// to check for MainActivity status

			if (!NavigationMain.isActive()) {
				Intent launch = new Intent(Intent.ACTION_MAIN);
				launch.setClass(Pushbots.getInstance().appContext,
						NavigationMain.class);
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(context);

				preferences.edit().putInt("POSITION", 0).commit();
				launch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Pushbots.getInstance().appContext.startActivity(launch);
			}
			// Handle Push Message when received
		} else if (action.equals(Pushbots.MSG_RECEIVE)) {
			HashMap<?, ?> Pushdata = (HashMap<?, ?>) intent.getExtras().get(
					Pushbots.MSG_RECEIVE);
			Log.w(TAG,
					"User Received notification with Message: "
							+ Pushdata.get("message"));
		}
	}

}
