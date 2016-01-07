package com.example.lockscreentest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

/** 
 * @author Chan Kong
 * 
 * 
 */
public class MoreSetting extends Activity{
	private Button gallery , revice , defaultwp;
	private Spinner spinner;
	private Spinner menu;
	private ArrayAdapter adapter;
	private LinearLayout layout;
	private Notification mNotify;
	 private NotificationManager mNotificationManager;  
	 private Context context = MoreSetting.this;
	 private final static int NOTIFICATION_ID = 0x0001;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lockset);
		gallery = (Button) findViewById(R.id.localgallery);
		defaultwp = (Button) findViewById(R.id.defaultwp);
		revice = (Button) findViewById(R.id.revice);
		layout = (LinearLayout) findViewById(R.id.backgroundtest);
		spinner = (Spinner) findViewById(R.id.notifytime);
		menu = (Spinner) findViewById(R.id.bt1);
		adapter = ArrayAdapter.createFromResource(this, R.array.menu_item,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		menu.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		defaultwp.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.setsWallpaper(null);
			}
			
		});
		
		revice.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mNotify = new Notification(R.drawable.ic_launcher,"This is a notification.",System.currentTimeMillis());  
				mNotify.defaults = Notification.DEFAULT_SOUND;  
				 mNotificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
				 Intent mIntent = new Intent(context,MoreSetting.class);  
		            //这里需要设置Intent.FLAG_ACTIVITY_NEW_TASK属性  
		            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);      
		            PendingIntent mContentIntent =PendingIntent.getActivity(context,0, mIntent, 0);  
				 mNotify.setLatestEventInfo(context, "10086", "您的当前话费不足,请充值.哈哈~", mContentIntent);  
				 mNotificationManager.notify(NOTIFICATION_ID, mNotify);    
			}
			
		});
		
		gallery.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
			    intent.addCategory(Intent.CATEGORY_OPENABLE);
			    intent.setType("image/*");
			    intent.putExtra("return-data", true);

			    startActivityForResult(intent, 0);
			}
			
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.e("MoreSetting", "in here");
		if(data.getExtras().get("data")!=null){
			Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
//		 this.getWindow().getDecorView().setBackgroundDrawable(new BitmapDrawable(cameraBitmap));
//		layout.setBackground(new BitmapDrawable(cameraBitmap));
		Global.setsWallpaper(cameraBitmap);
		}
		 
		super.onActivityResult(requestCode, resultCode, data);
	}
	

}
