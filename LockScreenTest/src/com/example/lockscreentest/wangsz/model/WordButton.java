package com.example.lockscreentest.wangsz.model;

import android.widget.Button;

/**
 * �?�???�件
 * 
 * @author wangsz
 *
 */
public class WordButton {

	public int mIndex;
	public boolean mIsVisiable;
	public String mWordString;
	
	public Button mViewButton;
	
	public WordButton() {
		mIsVisiable = true;
		mWordString = "";
	}
}
