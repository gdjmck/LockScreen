package com.example.lockscreentest.wangsz.data;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Const {
	//题目下标
	public static final int QUESTION_INDEX = 0;
	//答案下标
	public static final int ANSWER_INDEX = 1; 
	//混淆答案选项
	public static final int WRONG_INDEX = 2;
	//起始关卡
	public static final int BEGIN_STAGE = -1; 
	//起始金币
	public static final int TOTAL_COINT = 10; 
	//
	public static final String FILE_NAME_SAVE_DATA = "data.dat";
	//保存的关卡下标
	public static final int INDEX_LOAD_DATA_STAGE = 0;
	//保存的金币下标
	public static final int INDEX_LOAD_DATA_COINS = 1;
	//
	public static final String IS_FIRST_TIME_GAME = "is_first_time_game";
	//问题+答案
	public static final String[][] QUESTION_ANSWER = {
		{"bds.png","为人","作用"},
		{"cmqz.png","陡壁",""},
		{"bls.png","吃饭","洗澡"},
		{"pkq.png","游泳",""},
		{"ssbls.png","睡觉","喝茶"},
		{"xd.png","流行","绿茶"},
		{"yb.png","面包","小麦"},
		{"lcf.png","贩卖","精神"},
		{"xch.png","美国","山西"},
		{"sjs.png","枪手","合法"}	  
	};
	
	//提示
	public static final String[] ANSWER_TYPE = {
		"药名",
		"药名",
		"药名",
		"药名",
		"药名",
		"药名",
		"药名",
		"药名",
		"药名",
		"药名",
	};
	
}
