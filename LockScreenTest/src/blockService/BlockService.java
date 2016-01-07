package blockService;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;

/**
 * @author Chan Kong
 * 
 * 
 */
public class BlockService extends Service {

	private static final int delayMillis = 1000;

	private ActivityManager mActivityManager;

	private Handler mHandler;

	private ArrayList<String> mBlockList = null;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		//启动BroadcastReceiver
		MyBroadcastReceiver receiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("broadcast_timeup");
		registerReceiver(receiver , filter);
		
		
		mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		mHandler = new Handler();
		mHandler.postDelayed(mRunnable, delayMillis);
	}

	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mBlockList = BlockUtils.getBlockList(getApplicationContext());

			ComponentName topActivity = mActivityManager.getRunningTasks(1)
					.get(0).topActivity;

			if (mBlockList != null
					&& mBlockList.contains(topActivity.getPackageName())) {

				// Logger.getLogger().i( "block packageName：" +
				// topActivity.getPackageName() );
				// Logger.getLogger().i( "block className：" +
				// topActivity.getClassName() );

				Intent tancIntent = new Intent(getApplicationContext(),
						Lock.class);
				tancIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(tancIntent);
			}
			
			mHandler.postDelayed(mRunnable, delayMillis);
		}

	};
	
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mHandler.removeCallbacks(mRunnable);
		super.onDestroy();
	}

	//收到广播停止屏蔽APP
	public class MyBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			System.out.println("------广播收到-------");
			onDestroy();
		}
		
	}
	
	
	
}
