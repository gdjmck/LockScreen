package com.example.lockscreentest.wangsz;

import com.example.lockscreentest.R;
import com.example.lockscreentest.wangsz.data.Const;
import com.example.lockscreentest.wangsz.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * ����?��������
 * 
 * @author wangsz
 *
 */
public class AllPassView extends Activity implements OnClickListener{
	
	// 寰��???����??�?View
	private ImageButton mViewSharedWeixin;
	
	// ���绯�?���?�?View
	private ImageButton mViewMailToUs;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.all_pass_view);
		
		//��������???��???����??��
		LinearLayout view = (LinearLayout) findViewById(R.id.title_bar_coins);
		view.setVisibility(View.INVISIBLE);
		
		Util.saveData(this, Const.BEGIN_STAGE, -1);
		
		//��??�?��??��讳���?????��??��
		mViewSharedWeixin = (ImageButton) findViewById(R.id.weixin_pass_share);
		mViewSharedWeixin.setOnClickListener(this);
		
		mViewMailToUs = (ImageButton) findViewById(R.id.btn_mail);
		mViewMailToUs.setOnClickListener(this);
		
		Util.saveData(this, Const.BEGIN_STAGE, -1);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch (viewId) {
		case R.id.back:
			Util.StartActivity(this, MainActivity.class);
			break;
		//寰��???����??�?
		case R.id.weixin_pass_share:
			Toast.makeText(getApplicationContext(), "�?�������?��???��?���???����??����?��??������?����??�������?~",
					Toast.LENGTH_SHORT).show();
			break;
		//���绯�?���?�?	
		case R.id.btn_mail:
			Toast.makeText(getApplicationContext(), 
					"�?�������?��???��????������?����??�������?~濡�����?��??????��???��?????���?�?���?�?wangshouzhao0000@163.com",
					Toast.LENGTH_LONG).show();
			break;
		}
	}
	
	/**
	 * ??����������轰���??�?
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Util.StartActivity(this, MainActivity.class);
			return false;
		}
		return false;
	}

}
