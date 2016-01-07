package com.example.lockscreentest.wangsz.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * ???述�?????�?SharePreferences???工�?�类
 * 
 * @author wangsz
 * 
 */
public class SPHelper {
	
	/**
	 * �????boolean??��??
	 * 
	 * @param context
	 * @param spName
	 * @param key ??��??�?
	 * @param value ???
	 */
	public static void saveBoolean(Context context, String spName, String key, boolean value) {
		Editor editor = context.getSharedPreferences(spName, 0).edit();
		editor.putBoolean(key, value);
		editor.commit();// ???交修???
	}

	/**
	 * ??��??sp�????boolean??��??
	 * @param context
	 * @param spName 
	 * @param key ??��??�?
	 * @return
	 */
	public static boolean getBoolean(Context context, String spName, String key) {
		SharedPreferences sp = context.getSharedPreferences(spName, 0);
		if (sp != null && key != null) {
			return sp.getBoolean(key, true);
		}
		return true;
	}
}