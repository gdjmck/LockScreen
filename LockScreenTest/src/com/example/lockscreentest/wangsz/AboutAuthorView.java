package com.example.lockscreentest.wangsz;

import com.example.lockscreentest.R;
import com.example.lockscreentest.wangsz.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * ��???��������
 * @author wangsz
 *
 */
public class AboutAuthorView extends Activity {
	
	//??�����������?
	private ImageButton backIBtn;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.about_author_view);
		
		backIBtn = (ImageButton) findViewById(R.id.about_back);
		backIBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Util.StartActivity(AboutAuthorView.this, MainActivity.class);
			}
			
		});
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
