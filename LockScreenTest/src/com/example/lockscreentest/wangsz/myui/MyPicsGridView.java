package com.example.lockscreentest.wangsz.myui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;



import com.example.lockscreentest.R;
import com.example.lockscreentest.wangsz.model.IWordButtonClickListener;
import com.example.lockscreentest.wangsz.util.Util;

public class MyPicsGridView extends GridView {
	
	private ArrayList<Bitmap> mArrayList = new ArrayList<Bitmap>();

	private MyGridAdapter mAdapter;
	
	private Context mContext;
	
	public MyPicsGridView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		
		mContext = context;
		
		mAdapter = new MyGridAdapter();
		this.setAdapter(mAdapter);
	}
	
	public void updateData(ArrayList<Bitmap> list) {
		mArrayList = list;
		
		// ï¿½ï¿½ï¿½ï¿½ï¿½æ??ï¿½å?§ç??ï¿½ï¿½???ï¿½ï¿½???ï¿??		
		setAdapter(mAdapter);
	}

	class MyGridAdapter extends BaseAdapter {
		public int getCount() {
			return mArrayList.size();
		}

		public Object getItem(int pos) {
			return mArrayList.get(pos);
		}

		public long getItemId(int pos) {
			return pos;
		}

		public View getView(int pos, View v, ViewGroup p) {
			final Bitmap holder;
			
			if (v == null) {
				v = Util.getView(mContext, R.layout.pic_ui_gridview_item);
				
				holder = mArrayList.get(pos);
		
				v.setTag(holder);
			} else {
				holder = (Bitmap)v.getTag();
			}
			
			return v;
		}
	}
	
}
