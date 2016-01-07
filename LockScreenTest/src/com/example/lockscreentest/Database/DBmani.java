package com.example.lockscreentest.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.lockscreentest.R;

import com.example.lockscreentest.Global;

/**
 * @author Chan Kong
 * 
 * 
 */
public class DBmani implements DbOps {
	public static void main(String[] args) {
		// DBmani mani = new DBmani();
		File file = new File("lockscreen.db");
		File data = new File("yangsheng.db");
		if(!file.exists()){
			System.out.println("�ļ�������");
		}
	}

	public static final int CHANGE_TO_REMEMBER = 3;

	private static final String DATABASE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/data";
	private static final String DATABASE_NAME = "lockscreen.db";
	private static final String DATABASE_NAME1 = "yangsheng.db";
	private static final String DATABASE_FILE_NAME = DATABASE_PATH + "/"
			+ DATABASE_NAME;
	private static final String DATABASE_FILE_NAME1 = DATABASE_PATH + "/"
			+ DATABASE_NAME1;
	private SQLiteDatabase db = null;
	
	public DBmani(Context context, int flag){
		File dir1 = new File(DATABASE_PATH);
		if(!dir1.exists()){
			boolean flag1 = dir1.mkdir();
		}
		if (!new File(DATABASE_FILE_NAME1).exists()) {
			InputStream input = context.getResources().openRawResource(
					R.raw.yangsheng);
			try {
				FileOutputStream fos = new FileOutputStream(DATABASE_FILE_NAME1);
				byte[] tmp = new byte[100];

				int count = 0;
				while ((count = input.read(tmp)) > 0) {
					fos.write(tmp, 0, count);
				}
				fos.flush();
				if (fos != null) {
					fos.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("д��sd��ʱ����");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		db = SQLiteDatabase.openOrCreateDatabase(DATABASE_FILE_NAME1, null);
	}

	public DBmani(Context context) {
		// TODO Auto-generated constructor stub
		File dir = new File(DATABASE_PATH);
		
		Log.d("database", "׼����ȡ���ݿ��ļ�");
		if (!dir.exists()) {
			boolean flag = dir.mkdir();
			Log.d("database", "���ݿ�Ŀ¼������");
		}
		if (!new File(DATABASE_FILE_NAME).exists()) {
			Log.d("database", "���ݿ��ļ�������");
			InputStream input = context.getResources().openRawResource(
					R.raw.lockscreen);
			try {
				FileOutputStream fos = new FileOutputStream(DATABASE_FILE_NAME);
				byte[] tmp = new byte[100];

				int count = 0;
				while ((count = input.read(tmp)) > 0) {
					fos.write(tmp, 0, count);
				}
				fos.flush();
				if (fos != null) {
					fos.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("д��sd��ʱ����");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out
				.println("raw�е����ݿ��ļ�·��Ϊ��"
						+ context.getResources().getResourceEntryName(
								R.raw.lockscreen));
		
		
		
		
//		db = SQLiteDatabase.openOrCreateDatabase(context.getResources()
//				.getResourceEntryName(R.raw.lockscreen), null);
		// db.execSQL("select sub_info from subject where id = 1");
//		Cursor c = db.query("subject", new String[] { "sub_info" }, "id=1",
//				null, null, null, null);
//		System.out.println("��һ�δ����ݿ�"
//				+ c.getString(c.getColumnIndex("sub_info")));
		 db = SQLiteDatabase.openOrCreateDatabase(DATABASE_FILE_NAME, null);
	}

	@Override
	public boolean add(Object[] params) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean delete(Object[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Object[] params) {
		// TODO Auto-generated method stub
		ContentValues value = new ContentValues();
		value.put("state", (String) params[2]);
		String where = (String) params[0] + "=" + (String) params[1];
		db.update("subject", value, where, null);
		return false;
	}

	@Override
	// ������ѯ
	public Map<String, Object> view(String[] selectionArgs, int code) {
		// TODO Auto-generated method stub
		String query = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if (code == 1) {
			query = "select sub_info from subject where id=?";
		}

		else if (code == 2) {
			query = "select background from subject where id =?";
		} else if (code == 3) {
			query = "update subject set state = 'get' where sub_info = '?'";
		}
		Cursor cursor = db.rawQuery(query, selectionArgs);
		int col = 0;
		col = cursor.getColumnCount();
		if (col == 1 && code == 3) {
			return null;
		}
		while (cursor.moveToNext()) {

			for (int i = 0; i < col; i++) {
				String col_name = cursor.getColumnName(i);

				if (code == 1) {
					String col_value = cursor.getString(cursor
							.getColumnIndex(col_name));
					map.put(col_name, col_value);
				} else if (code == 2) {
					int col_value = cursor.getInt(cursor
							.getColumnIndex(col_name));
					Log.d("�ֶ���", col_name);
					if (col_value != 0x7f020012)
						Log.d("pic_id", "ͼƬIDΪ:" + String.valueOf(col_value)
								+ "ԭ��ͼƬ��ԴidΪ:" + String.valueOf(0x7f020012));
					map.put(col_name, col_value);
				}
			}
		}
		return map;
	}

	// @Override
	// // ������ѯ
	// public List<Map<String, Object>> listMaps(String[] selectionArgs, int
	// code) {
	// // TODO Auto-generated method stub
	// List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	// String query = "";
	// query = "select sub_info from subject where state!='get'";
	// Cursor cursor = db.rawQuery(query, selectionArgs);
	// int columns = cursor.getColumnCount();
	// int count = 0;
	// while(cursor.moveToNext()&&count<3){
	// Map<String,Object> map = new HashMap<String,Object>();
	// for(int i=0;i<columns;i++){
	// String col_name = cursor.getColumnName(i);
	// String col_value = cursor.getString(cursor.getColumnIndex(col_name));
	// map.put(col_name, col_value);
	// }
	// list.add(map);
	// count++;
	// }
	// return list;
	// }

	@Override
	public Map<String, Object> view(String query, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//�������еļ�¼
	public List<Map<String, Object>> listAll(String query, String[] selectionArgs) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Cursor cursor = db.rawQuery(query, selectionArgs);
		int columns = cursor.getColumnCount();
		while (cursor.moveToNext()) {//
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columns; i++) {
				String col_name = cursor.getColumnName(i);
				String col_value = cursor.getString(cursor
						.getColumnIndex(col_name));
				map.put(col_name, col_value);
			}
			list.add(map);
		}
		return list;
	}

	@Override
	//�����̶�������
	public List<Map<String, Object>> list(String query, String[] selectionArgs) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Cursor cursor = db.rawQuery(query, selectionArgs);
		int columns = cursor.getColumnCount();
		int count = 0;
		Log.d("DBmani", "Ҫ��ش���Ŀ��Ϊ:" + Global.getScreen_count());//
		while (cursor.moveToNext() && count < Global.getScreen_count()) {//
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columns; i++) {
				String col_name = cursor.getColumnName(i);
				String col_value = cursor.getString(cursor
						.getColumnIndex(col_name));
				map.put(col_name, col_value);
			}
			list.add(map);
			count++;
		}
		return list;
	}
	
	//�����е���Ϣ
	public List<Map<String,Object>> seeAllData(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String select = "select sub_type ,sub_info,state from subject";
		Cursor cursor = db.rawQuery(select, null);
		int columns = cursor.getColumnCount();
		while (cursor.moveToNext() ) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columns; i++) {
				String col_name = cursor.getColumnName(i);
				String col_value = cursor.getString(cursor
						.getColumnIndex(col_name));
				map.put(col_name, col_value);
			}
			list.add(map);
		}
		return list;
		
	}

	
}
