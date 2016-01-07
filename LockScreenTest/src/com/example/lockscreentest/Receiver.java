package com.example.lockscreentest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.lockscreentest.Service.LockScreen;

/** 
 * @author Chan Kong
 * 
 * 
 */
public class Receiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.e("Receiver", "receive a broadcast");
		if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
			Log.e("Receiver", "it is a screnn off signal");
			intent.setClass(context, LockScreen.class);
			context.startActivity(intent);
		}
		if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
			Log.e("Receiver", "it is a screnn on signal");
//			intent.setClass(context, LockScreen.class);
//			context.startActivity(intent);
		}
	}

}
