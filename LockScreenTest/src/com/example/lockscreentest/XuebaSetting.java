package com.example.lockscreentest;

import blockService.BlockListActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class XuebaSetting extends Activity{
	private Button list;
	private RadioGroup soundGroup, superviseGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xuebaset);
		
		list = (Button) findViewById(R.id.blockList);
		soundGroup = (RadioGroup) findViewById(R.id.soundGroup);
		superviseGroup = (RadioGroup) findViewById(R.id.superviseMdoe);
		
		list.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(XuebaSetting.this, BlockListActivity.class);
				startActivity(intent);
			}
			
		});
		
		soundGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.shengyin){
					Global.setNotimode(0);
					System.out.println("现在是提示音模式");
				}
				else if(checkedId == R.id.zhendong){
					Global.setNotimode(1);
					System.out.println("现在是震动模式");
				}
			}
		});
		
		superviseGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.qianghan)
					Global.setSuperviseMode(0);
				else if(checkedId == R.id.wenrou)
					Global.setSuperviseMode(1);
			}
		});
	}
	
}
