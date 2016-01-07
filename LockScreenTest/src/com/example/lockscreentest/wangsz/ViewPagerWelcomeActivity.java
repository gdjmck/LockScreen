package com.example.lockscreentest.wangsz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lockscreentest.R;
import com.example.lockscreentest.wangsz.adapter.ViewPagerAdapter;
import com.example.lockscreentest.wangsz.data.Const;
import com.example.lockscreentest.wangsz.util.SPHelper;
import com.example.lockscreentest.wangsz.util.Util;

/**
 * 引导页面
 * @author Chankong
 *
 */
public class ViewPagerWelcomeActivity extends Activity implements
		OnClickListener, OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;



	// 记录当前选中位置
	private int currentIndex;

	// 开始游戏
	private ImageButton mBeginGame;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_view_page);

		
			Util.StartActivity(ViewPagerWelcomeActivity.this,
					MainActivity.class);
		
		
		mBeginGame = (ImageButton) findViewById(R.id.begin_game);
		mBeginGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Util.StartActivity(ViewPagerWelcomeActivity.this,
						MainActivity.class);
			}
		});

		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		
		vp = (ViewPager) findViewById(R.id.viewpager);
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);


	}

	

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}



	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}