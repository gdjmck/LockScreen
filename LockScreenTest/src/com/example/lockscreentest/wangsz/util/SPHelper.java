package com.example.lockscreentest.wangsz.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * ???è¿°ï?????ä½?SharePreferences???å·¥å?·ç±»
 * 
 * @author wangsz
 * 
 */
public class SPHelper {
	
	/**
	 * å­????boolean??°æ??
	 * 
	 * @param context
	 * @param spName
	 * @param key ??³é??å­?
	 * @param value ???
	 */
	public static void saveBoolean(Context context, String spName, String key, boolean value) {
		Editor editor = context.getSharedPreferences(spName, 0).edit();
		editor.putBoolean(key, value);
		editor.commit();// ???äº¤ä¿®???
	}

	/**
	 * ??·å??spä¸????boolean??°æ??
	 * @param context
	 * @param spName 
	 * @param key ??³é??å­?
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