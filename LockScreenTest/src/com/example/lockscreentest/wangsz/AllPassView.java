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
 * ï¿½ï¿½ï¿½æ?©ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
 * 
 * @author wangsz
 *
 */
public class AllPassView extends Activity implements OnClickListener{
	
	// å¯°ï¿½æ·???³ï¿½ï¿½æ??ï¿?View
	private ImageButton mViewSharedWeixin;
	
	// ï¿½ï¿½ï¿½ç»¯ç»?ï¿½ï¿½æµ?ï¿?View
	private ImageButton mViewMailToUs;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.all_pass_view);
		
		//ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½???ï¿½ï¿½ï¿½ç??ï¿½ï¿½
		LinearLayout view = (LinearLayout) findViewById(R.id.title_bar_coins);
		view.setVisibility(View.INVISIBLE);
		
		Util.saveData(this, Const.BEGIN_STAGE, -1);
		
		//ï¿½ï¿½??æ¬?ï¿½ï¿½??°ï¿½è®³ï¿½ï¿½æ?????ï¿½ï¿½??¹ï¿½
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
		//å¯°ï¿½æ·???³ï¿½ï¿½æ??ï¿?
		case R.id.weixin_pass_share:
			Toast.makeText(getApplicationContext(), "æ¶?ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½è¾?ï¿½ï¿½???ï¿½å?°ï¿½æ·???³ï¿½ï¿½æ??ï¿½ï¿½ï¿½ç?¿ï¿½??²ï¿½ï¿½ï¿½ï¿½è?¥ï¿½ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿?~",
					Toast.LENGTH_SHORT).show();
			break;
		//ï¿½ï¿½ï¿½ç»¯ç»?ï¿½ï¿½æµ?ï¿?	
		case R.id.btn_mail:
			Toast.makeText(getApplicationContext(), 
					"æ¶?ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½è¾?ï¿½ï¿½???ï¿½ç????¥ï¿½ï¿½ï¿½ï¿½è?¥ï¿½ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿?~æ¿¡ï¿½ï¿½ï¿½ï¿½ç?¹ï¿½??????ï¿½ï¿½???ï¿½ç?????ï¿½ï¿½ç»?å©?ï¿½ï¿½ç»?ï¿?wangshouzhao0000@163.com",
					Toast.LENGTH_LONG).show();
			break;
		}
	}
	
	/**
	 * ??©ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½è½°ï¿½ï¿½æ??ï¿?
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
