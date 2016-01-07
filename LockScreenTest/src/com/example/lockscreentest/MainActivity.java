package com.example.lockscreentest;

import com.example.lockscreentest.wangsz.ViewPagerWelcomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class MainActivity extends Activity {
//	private Receiver r;
	private ImageButton setting, xueba, reviseMode, search, game, yangsheng;
	private Spinner menu;
	private ArrayAdapter adapter;
	private boolean isSpinnerFirst = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		

		setting =(ImageButton) findViewById(R.id.but_lock);
		xueba = (ImageButton) findViewById(R.id.but_xueba);
		reviseMode = (ImageButton) findViewById(R.id.but_review);
		search = (ImageButton) findViewById(R.id.but_search);
		game = (ImageButton) findViewById(R.id.but_game);
		yangsheng = (ImageButton) findViewById(R.id.but_yangsheng);
		menu = (Spinner) findViewById(R.id.bt1);
		adapter = ArrayAdapter.createFromResource(this, R.array.menu_item, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		menu.setAdapter(adapter);
		menu.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent = null;
				if(isSpinnerFirst){
					System.out.println("not working right now");
					arg1.setVisibility(View.INVISIBLE);
				}
				else{
					System.out.println("\nmenu clicked once" + "    " + arg2);
				int tmp = arg2 + 1;
				switch (tmp){
					
				case 1:
					intent = new Intent(MainActivity.this, ReviseMode.class);
					break;
				case 2:
					intent = new Intent(MainActivity.this, ReviseMode.class);
					break;
				case 3:
					intent = new Intent(MainActivity.this, ReviseMode.class);
					break;
				case 4:
					intent = new Intent(MainActivity.this, ReviseMode.class);
					break;
				case 5:
					intent = new Intent(MainActivity.this, ReviseMode.class);
					break;
				case 6:
					intent = new Intent(MainActivity.this, ReviseMode.class);
					default:
						intent = new Intent(MainActivity.this, ReviseMode.class);
						break;
				}
				}
				isSpinnerFirst = false;
				
				
				if(intent != null)
				startActivity(intent);
					
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

			
			
		});
		
		
		yangsheng.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, Yangsheng.class);
				startActivity(intent);
			}
			
		});
		
		
		game.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, ViewPagerWelcomeActivity.class);
				startActivity(intent);
			}
			
		});

		
		search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, Search.class);
				startActivity(intent);
			}
			
		});
		
		setting.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this , LockSetting.class);
				startActivity(intent);
			}
		});
		
		xueba.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, XuebaMode.class);
				startActivity(intent);
			}
			
		});
		
		reviseMode.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, ReviseMode.class);
				startActivity(intent);
			}
			
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.e("MainActivity", "-----ONDESTROY");
//		unregisterReceiver(r);
		super.onDestroy();
	}
	
	

}
