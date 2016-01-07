package com.example.lockscreentest.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.example.lockscreentest.Receiver;

/** 
 * @author Chan Kong
 * 
 * 
 */
public class LocalService extends Service{
	Receiver r ;
	Intent startIntent = null;
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.e("LocalService", "---onCreate");
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_DATE_CHANGED);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		 r = new Receiver();
		this.registerReceiver(r,filter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		startIntent = intent;
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("LocalService", "---onDestroy");
		unregisterReceiver(r);
		if(startIntent!=null){
			startService(startIntent);
		}
	}
	
	

}
