package com.example.lockscreentest;

import android.app.Application;
import android.graphics.Bitmap;

/** 
 * @author Chan Kong
 * 
 * 
 */
public class Global extends Application{

	

	public final static int MODE_VIEW = 0;
	public final static int MODE_Q = 1;
	
	//0������ʾ����1������
	public static int notiMode = 0;
	

	//0����ǿ����1��������
	public static int superviseMode = 1;
	//0������ҽ��1������ҩ,2������ҽҩ
	public static int question_type = 2;
	
	private static int screen_count = 3;
	//0������ģʽ,1�������ģʽ
	private static int lockscreen_type = 0;
	private static int right_answerNum = 0;
	private static int unlockNum = 0;
	private static int viewNum = 0;
	private static int answerNum = 0;
	private static String date = "";
	
	//
	
	private static Bitmap sWallpaper = null;
	
	public static int getQuestion_type() {
		return question_type;
	}

	public static void setQuestion_type(int question_type) {
		Global.question_type = question_type;
	}
	
	public static int getSuperviseMode() {
		return superviseMode;
	}

	public static void setSuperviseMode(int superviseMode) {
		Global.superviseMode = superviseMode;
	}
	
	public static int getNotimode() {
		return notiMode;
	}
	
	public static void setNotimode(int mode){
		Global.notiMode = mode;
	}

	public static void reset()
	{
		setRight_answerNum(0);
		setUnlockNum(0);
		setViewNum(0);
		setAnswerNum(0);
	}

	public static int getRight_answerNum() {
		return right_answerNum;
	}

	public static void setRight_answerNum(int right_answerNum) {
		Global.right_answerNum = right_answerNum;
	}

	public static int getUnlockNum() {
		return unlockNum;
	}

	public static void setUnlockNum(int unlockNum) {
		Global.unlockNum = unlockNum;
	}

	public static int getViewNum() {
		return viewNum;
	}

	public static void setViewNum(int reviewNum) {
		Global.viewNum = reviewNum;
	}

	public static int getAnswerNum() {
		return answerNum;
	}

	public static void setAnswerNum(int answerNum) {
		Global.answerNum = answerNum;
	}

	
	public static String getDate() {
		return date;
	}

	public static void setDate(String date) {
		Global.date = date;
	}

	public static Bitmap getsWallpaper() {
		return sWallpaper;
	}

	public static void setsWallpaper(Bitmap sWallpaper) {
		Global.sWallpaper = sWallpaper;
	}

	//true����������,false����ر�����
	private static boolean lock_on_off = true;

	public static int getScreen_count() {
		return screen_count;
	}

	public static void setScreen_count(int screen_count) {
		Global.screen_count = screen_count;
	}

	public static int getLockscreen_type() {
		return lockscreen_type;
	}

	public static void setLockscreen_type(int lockscreen_type) {
		Global.lockscreen_type = lockscreen_type;
	}

	public static boolean isLock_on_off() {
		return lock_on_off;
	}

	public static void setLock_on_off(boolean lock_on_off) {
		Global.lock_on_off = lock_on_off;
	}
	

	
}
