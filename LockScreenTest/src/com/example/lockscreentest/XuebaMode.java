package com.example.lockscreentest;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import blockService.BlockListActivity;
import blockService.BlockService;

public class XuebaMode extends Activity{
	private Button startButton;
	private Button setList;
	private TextView minuteText;
	private TextView secondText;
	private ImageButton xuebaSetting, xuebaList;
	private Spinner menu;
	private ArrayAdapter adapter;
	int minute;
	int second;
	
	private void ring(Context context) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		MediaPlayer mPlayer = new MediaPlayer();
		mPlayer.reset();
		mPlayer.setDataSource(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		mPlayer.prepare();
		mPlayer.start();
	}
	
	
	Timer timer;
	TimerTask timerTask;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("handle!");
			if (minute == 0) {
				minuteText.setText("0");
				if (second == 0) {
					//notify a notification when finish
					NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
					Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
					Notification n = new Notification();
					n.tickerText = "时间到";
					n.icon = R.drawable.ic_launcher;
					n.when = System.currentTimeMillis();
					n.flags = Notification.FLAG_AUTO_CANCEL;
					n.sound = ringUri;
					n.setLatestEventInfo(XuebaMode.this, "时间到", "学习结束", null);
					
					nm.notify(1, n);
					startButton.setClickable(true);
					
//					timeView.setText("Time out !");
					if (timer != null) {
						timer.cancel();
						timer = null;
					}
					if (timerTask != null) {
						timerTask = null;
					}
					 //发送自定义广播，通知停止屏蔽APP
    				Intent intent = new Intent();
    				intent.setAction("broadcast_timeup");
    				sendBroadcast(intent);
    				System.out.println("------已经发送广播-------");
				}//end of second = 0
				else {
					second--;
//					if (second >= 10) {
						secondText.setText(String.valueOf(second));
//					} else {
//						timeView.setText("0" + minute + ":0" + second);
//					}
				}
			}//end of min = 0
			else {
				if (second == 0) {
					second = 59;
					minute--;
//					if (minute >= 10) {
						minuteText.setText(String.valueOf(minute));
						secondText.setText(String.valueOf(second));
//					} else {
//						timeView.setText("0" + minute + ":" + second);
//					}
				} else {
					second--;
					secondText.setText(String.valueOf(second));
//					if (second >= 10) {
//						if (minute >= 10) {
//							timeView.setText(minute + ":" + second);
//						} else {
//							timeView.setText("0" + minute + ":" + second);
//						}
//					} else {
//						if (minute >= 10) {
//							timeView.setText(minute + ":0" + second);
//						} else {
//							timeView.setText("0" + minute + ":0" + second);
//						}
//					}
				}
			}
		};
	};
	
	private void chooseTime(Context context){
		TimePickerDialog timePicker = new TimePickerDialog(context, otsListener, 0, 0, true);
		timePicker.show();
	}
	
	OnTimeSetListener otsListener = new OnTimeSetListener(){

		@Override
		public void onTimeSet(TimePicker view, int minutes, int seconds) {
			// TODO Auto-generated method stub
			minute = minutes;
			second = seconds;
			minuteText.setText(String.valueOf(minutes));
			secondText.setText(String.valueOf(seconds));
			System.out.println("时间是" + minute + "分" + second + "秒");
		}
		
	};

//	private void showMinDialog(Context context){
//		LayoutInflater inflater = LayoutInflater.from(this);
//		final View entryView = inflater.inflate(R.layout.dialoglayout, null);
//		final EditText editInput = (EditText) entryView.findViewById(R.id.edtInput);
//		chooseTime(context);
		
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setIcon(null);
//		builder.setTitle("请输入时间");
//		builder.setCancelable(false);
//		builder.setView(entryView);
//		
//		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				String text = editInput.getText().toString();
//				if(text.equals("") || !text.matches("\\d+"))
//					secondText.setText("0");
//				minuteText.setText(text);
//			}
//		});
//		builder.show();
//	}
	
//	private void showSecDialog(Context context){
//		LayoutInflater inflater = LayoutInflater.from(this);
//		final View entryView = inflater.inflate(R.layout.dialoglayout, null);
//		final EditText editInput = (EditText) entryView.findViewById(R.id.edtInput);
//		
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setIcon(null);
//		builder.setTitle("请输入时间");
//		builder.setCancelable(false);
//		builder.setView(entryView);
//		
//		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				String text = editInput.getText().toString();
//				if(text.equals("") || !text.matches("\\d+"))
//					secondText.setText("0");
//				secondText.setText(text);
//			}
//		});
		
		
//		builder.show();
//	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xueba);
		
		//开启SERVICE
				Intent intent = new Intent(XuebaMode.this , BlockService.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startService(intent);
				

				startButton = (Button) findViewById(R.id.start);
//				setList = (Button) findViewById(R.id.setList);
				minuteText = (TextView) findViewById(R.id.minute);
				secondText = (TextView) findViewById(R.id.second);
				xuebaList = (ImageButton) findViewById(R.id.ib_menu_main);
				xuebaSetting = (ImageButton) findViewById(R.id.ib_menu_setting);
				menu = (Spinner) findViewById(R.id.bt1);
				adapter = ArrayAdapter.createFromResource(this, R.array.menu_item, android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				menu.setAdapter(adapter);
				
				xuebaList.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(XuebaMode.this, BlockListActivity.class);
						startActivity(intent);
					}
					
				});
				
				minuteText.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						showMinDialog(XuebaMode.this);
						chooseTime(XuebaMode.this);
					}
				});
				
				secondText.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						showSecDialog(XuebaMode.this);
						chooseTime(XuebaMode.this);
					}
					
				});
				
				startButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						if (!minuteText.getText().toString().equals("")) {
//							minute = Integer.parseInt(minuteText.getText().toString());
//						}
//						if (!secondText.getText().toString().equals("")) {
//							second = Integer.parseInt(secondText.getText().toString());
//						}
						
						 if (minute != 0 || second != 0) {  
			                    System.out.println(minute+":"+second);  
			                      
//			                    ArrayList<Integer> list = new ArrayList<Integer>();  
//			                    list.add(minute);  
//			                    list.add(second);  
//			                      
//			                    Intent intent = new Intent(XuebaMode.this,StartActivity.class);  
//			                    intent.setAction(".StartActivity");  
			                      
//			                    intent.putIntegerArrayListExtra("times", list);  
//			                    startActivity(intent);  
			                    
			                 
			                    
			                    timerTask = new TimerTask() {  
			      	              
			        	            @Override  
			        	            public void run() {  
			        	                Message msg = new Message();  
			        	                msg.what = 0;  
			        	                handler.sendMessage(msg);  
			        	            }  
			        	        };  
			        	          
			        	        timer = new Timer();  
			        	        timer.schedule(timerTask,0,1000);
			        	        startButton.setClickable(false);
			                }  
					}

				});
				
				xuebaSetting.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(XuebaMode.this, XuebaSetting.class);
						startActivity(intent);
					}
					
				});
				
				
//				setList.setOnClickListener(new OnClickListener(){
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						//尝试封锁APP
//						Intent intent = new Intent(XuebaMode.this, BlockListActivity.class);
//						startActivity(intent);
//						
//						
//					}
//					
//				});
				
	}
	
//	void startActivitySafely(Intent intent) {  
//	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//	    try {  
//	        startActivity(intent);  
//	    } catch (ActivityNotFoundException e) {  
//	        Toast.makeText(this, "unabletoopensoftware",  
//	                Toast.LENGTH_SHORT).show();  
//	    } catch (SecurityException e) {  
//	        Toast.makeText(this, "unabletoopensoftware",  
//	                Toast.LENGTH_SHORT).show();  
//	        Log.e(  
//	                        "tag",  
//	                        "Launcher does not have the permission to launch "  
//	                                + intent  
//	                                + ". Make sure to create a MAIN intent-filter for the corresponding activity "  
//	                                + "or use the exported attribute for this activity.",  
//	                        e);  
//	    }  
//	}  

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
//		PackageManager pm = getPackageManager();
//		ResolveInfo homeInfo = pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0);
//		
//		ActivityInfo ai = homeInfo.activityInfo;  
//		Intent startIntent = new Intent(Intent.ACTION_MAIN);  
//		startIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
//		startIntent.setComponent(new ComponentName(ai.packageName,  
//		                    ai.name));  
//		startActivitySafely(startIntent); 
		//按下后退时清理掉计时
		if (timerTask != null) {
			timerTask = null;
		}
	}

}
