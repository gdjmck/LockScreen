package com.example.lockscreentest;


import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lockscreentest.Service.LocalService;

/**
 * @author Chan Kong
 * 
 * 
 */
public class LockSetting extends Activity implements OnClickListener {
	private Button addT, minusT, modeView, modeQ, moreSet, q_cure, q_medi, q_allkind;
	private TextView t_num;
	private Switch on_off;
	private Spinner menu;
	private ArrayAdapter adapter;
	private boolean state = Global.isLock_on_off();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock);
		addT = (Button) findViewById(R.id.add_timu);
		minusT = (Button) findViewById(R.id.minus_timu);
		t_num = (TextView) findViewById(R.id.timu_num);
		t_num.setText(String.valueOf(Global.getScreen_count()));
		modeView = (Button) findViewById(R.id.mode_view);
		modeQ = (Button) findViewById(R.id.mode_question);
		moreSet = (Button) findViewById(R.id.moresetting);
		on_off = (Switch) findViewById(R.id.use_or_not);
		q_cure = (Button) findViewById(R.id.chinese_cure);
		q_medi = (Button) findViewById(R.id.chinese_medi);
		q_allkind = (Button) findViewById(R.id.allkind);
		on_off.setChecked(state);
		
		q_cure.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.setQuestion_type(0);
			}
			
		});
		q_medi.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.setQuestion_type(1);
			}
			
		});
		q_allkind.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.setQuestion_type(2);
			}
			
		});

		addT.setOnClickListener(this);
		minusT.setOnClickListener(this);
		modeView.setOnClickListener(this);
		modeQ.setOnClickListener(this);
		moreSet.setOnClickListener(this);
		menu = (Spinner) findViewById(R.id.bt1);
		adapter = ArrayAdapter.createFromResource(this, R.array.menu_item,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		menu.setAdapter(adapter);

		on_off.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				state = isChecked;
			}

		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e("LockSetting", "---onPause");
		Global.setLock_on_off(state);
		if (state) {
			KeyguardManager km = (KeyguardManager) LockSetting.this
					.getSystemService(Context.KEYGUARD_SERVICE);
			KeyguardLock kl = km.newKeyguardLock("");
			kl.disableKeyguard();
			Intent intent = new Intent(LockSetting.this, LocalService.class);
			startService(intent);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(addT)) {

			int count = Integer.valueOf((String) t_num.getText());
			if (count == 8) {
				Toast.makeText(LockSetting.this, "8道题目已经足够了",
						Toast.LENGTH_SHORT).show();
				return;
			}
			count++;
			// 存到全局变量当中
			Global.setScreen_count(count);
			String value = String.valueOf(count);
			t_num.setText(value);
		}
		if (v.equals(minusT)) {
			int count = Integer.valueOf((String) t_num.getText());
			if (count == 1) {
				Toast.makeText(LockSetting.this, "至少看1题吧", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			count--;
			Global.setScreen_count(count);
			String value = String.valueOf(count);
			t_num.setText(value);
		}
		if (v.equals(modeView)) {
			Global.setLockscreen_type(Global.MODE_VIEW);
			Toast.makeText(LockSetting.this, "现在是浏览模式", Toast.LENGTH_SHORT)
					.show();
		}
		if (v.equals(modeQ)) {
			Global.setLockscreen_type(Global.MODE_Q);
			Toast.makeText(LockSetting.this, "现在是答题模式", Toast.LENGTH_SHORT)
					.show();
		}
		if (v.equals(moreSet)) {
			Intent intent = new Intent(LockSetting.this, MoreSetting.class);
			startActivity(intent);
		}
	}

}
