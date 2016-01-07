package com.example.lockscreentest.wangsz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.example.lockscreentest.R;
import com.example.lockscreentest.wangsz.data.Const;
import com.example.lockscreentest.wangsz.util.Util;

public class MyPicsView extends Activity implements OnClickListener {
	ImageSwitcher imageSwitcher; // æ¾¹ç??ï¿½ï¿½ImageSwitcher??µç????????ï¿½ï¿½ï¿½å?§ï¿½ï¿½ï¿½ï¿½å?§ã??ï¿½ï¿½???ï¿½ï¿½
	Gallery gallery; // æ¾¹ç??ï¿½ï¿½Gallery??µç????????ï¿½ï¿½ï¿½å?§ï¿½ï¿½ï¿½ï¿½ï¿½?????§å?¨å??ï¿?
	int imagePosition; // ï¿½ï¿½ï¿½ç????¿ï¿½??§ï¿½ï¿½ï¿½ï¿½æ??ï¿½ï¿½æ¶?ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½ã?¤ï¿½ï¿½å?°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å?§ã??
	// æ¾¹ç??ï¿½ï¿½ï¿½ï¿½??§ï¿½ï¿½ï¿½ï¿½æ??ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½
	private String[] images;

	// ??©ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿?
	private ImageButton backIBtn;

	// æ·?ï¿½ç??ï¿½ï¿½ï¿½å?§ï¿½ï¿?
	private ImageButton saveIBtn;

	// è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å?§ï¿½ï¿?
	private Bitmap mMyPic;
	// è¤°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å?§ï¿½ï¿½ï¿½ï¿½ï¿½
	private String mMyPicName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_pictures_view);

		images = getPicsNames();

		// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½???ï¿½ï¿½ï¿½ç??ï¿½ï¿½
		LinearLayout view = (LinearLayout) findViewById(R.id.title_bar_coins);
		view.setVisibility(View.INVISIBLE);

		saveIBtn = (ImageButton) findViewById(R.id.save_pic);
		saveIBtn.setVisibility(View.VISIBLE);
		saveIBtn.setOnClickListener(this);

		backIBtn = (ImageButton) findViewById(R.id.back);
		backIBtn.setOnClickListener(this);

		// ï¿½ï¿½ï¿½æ?©ï¿½ï¿½ï¿½??æ¬?ï¿½ï¿½ï¿?IDï¿½ï¿½å³°ï¿½ï¿?imageSwitcherï¿½ï¿½ï¿½ç?µç?????
		imageSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		// ?????§ç??ï¿½ï¿½ï¿½ç?¹ï¿½æ¶?ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½??§ï¿½ï¿½ï¿½ï¿½å?§ã??å®¸ã?¥ï¿½ï¿½ç»«ï¿?
		imageSwitcher.setFactory(new MyViewFactory(this));
		// ï¿½ï¿½ï¿½æ?©ï¿½ï¿½ï¿½??æ¬?ï¿½ï¿½ï¿?IDï¿½ï¿½å³°ï¿½ï¿?galleryï¿½ï¿½ï¿½ç?µç?????
		gallery = (Gallery) findViewById(R.id.gallery);
		// ?????§ç??ï¿½ï¿½ï¿½ç?¹ï¿½æ¶?ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½??§ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
		gallery.setAdapter(new ImageAdapter(this));
		// ??¹ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½ï¿½æ??ï¿½ï¿½ï¿½ï¿½æµ?ï¿½æ?????ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// ï¿½ï¿½ï¿½æ?©ï¿½å§¹ï¿½æµ£ï¿½ï¿½ï¿½å¸?ï¿½ï¿½å¯°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å?§ã??ï¿½ï¿½??§ï¿½ï¿?
				Bitmap bitmap = getBitMap(images[position % images.length]);
				mMyPic = bitmap;
				mMyPicName = images[position % images.length];
				BitmapDrawable bd = new BitmapDrawable(bitmap);
				imageSwitcher.setImageDrawable(bd);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}

	// ï¿½ï¿½ï¿½æ¿®ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½??§ï¿½ï¿½ï¿½ï¿½ï¿½ç»?ï¿?
	private String[] getPicsNames() {
		// String[] datas = null;
		int stageIndex = Util.loadData(this)[Const.INDEX_LOAD_DATA_STAGE];
		String[] datas = new String[stageIndex + 2];
		Log.e("1111111", stageIndex + "");
		for (int i = 0; i <= stageIndex + 1; i++) {
			String[] stage = Const.QUESTION_ANSWER[i];
			datas[i] = stage[Const.QUESTION_INDEX];
			Log.e("22222", datas[i]);
		}
		return datas;
	}

	// å¯°ï¿½ï¿½ï¿½??¿ï¿½??§ï¿½ï¿?
	private Bitmap getBitMap(String s) {
		AssetManager assets = getAssets();
		InputStream is = null;
		try {
			is = assets.open(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
		return bitmap;
	}

	// ï¿½ï¿½ï¿½ç?¹ï¿½æ¶?ï¿½ï¿½ï¿½å?§ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ç????ï¿½ï¿½BaseAdapter
	class ImageAdapter extends BaseAdapter {
		private Context context; // ??¹ï¿½æ¶?ï¿½æ??ï¿½æ??ï¿½ï¿½ï¿½ï¿½

		// ï¿½ï¿½ï¿½ï¿½ï¿½é??è´?æ¶?ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½è??ï¿½ï¿½
		public ImageAdapter(Context context) {
			this.context = context;
		}

		// å¯°ï¿½ï¿½ï¿½??¿ï¿½??§ï¿½ï¿½ï¿½ï¿½ï¿½æ¾¶Ñ?ï¿½ï¿½
		@Override
		public int getCount() { // ?????§ç??æ¶????ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½æ¾¶Ñ?ï¿½ï¿½
			return images.length;
		}

		// å¯°ï¿½ï¿½ï¿½???ï¿½ï¿½??¹ï¿½ï¿½ï¿½??§ï¿½ï¿½ï¿½ï¿½ï¿½??µç?????
		@Override
		public Object getItem(int position) {
			return position;
		}

		// å¯°ï¿½ï¿½ï¿½???ï¿½ï¿½??¹ï¿½ï¿½ï¿½??§ï¿½ï¿½ï¿½ï¿½ï¿½??µç?????ï¿½ï¿½ï¿?ID
		@Override
		public long getItemId(int position) {
			return position;
		}

		// ï¿½ï¿½??§ã??ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½ï¿½ç??ï¿?
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(context); // ï¿½ï¿½ï¿½å?¤ï¿½ImageView??µç?????
			iv.setImageBitmap(getBitMap(images[position % images.length])); // ?????§ç??å¯°ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½å?§ã??ï¿½ï¿½??§ï¿½ï¿?
			iv.setAdjustViewBounds(true); // ï¿½ï¿½??§ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½å­?ï¿½å?§ã??
			// ?????§ç??ï¿½ï¿½??§ï¿½ï¿½ï¿½ï¿½ï¿½??¹è??ï¿½ï¿½æ¥?ï¿?
			iv.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			return iv; // ??©ï¿½ï¿½ï¿½ï¿?ImageView??µç?????
		}
	}

	// ï¿½ï¿½ï¿½ç?¹ï¿½æ¶?ï¿½ï¿½ï¿½å?§ï¿½ï¿½ï¿½ï¿½å?§ã??å®¸ã?¥ï¿½ï¿½ç»«ä¼?ï¿½ï¿½ç¼???ï¿½ï¿½ViewFactory
	class MyViewFactory implements ViewFactory {
		private Context context; // ??¹ï¿½æ¶?ï¿½æ??ï¿½æ??ï¿½ï¿½ï¿½ï¿½

		// ï¿½ï¿½ï¿½ï¿½ï¿½é??è´?æ¶?ï¿½æ??ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½è??ï¿½ï¿½
		public MyViewFactory(Context context) {
			this.context = context;
		}

		// ï¿½ï¿½??§ã??ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½???ï¿½ï¿½
		@Override
		public View makeView() {
			ImageView iv = new ImageView(context); // ï¿½ï¿½ï¿½å?¤ï¿½ImageView??µç?????
			iv.setScaleType(ImageView.ScaleType.FIT_CENTER); // ï¿½ï¿½??§ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½??¥ï¿½ï¿½æ??ï¿½ï¿½ï¿½å?§ã??
			// ?????§ç??ï¿½ï¿½??§ï¿½ï¿½ï¿½ï¿½ï¿½??¹è??ï¿½ï¿½æ¥?ï¿?
			iv.setLayoutParams(new ImageSwitcher.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			return iv; // ??©ï¿½ï¿½ï¿½ï¿?ImageView??µç?????
		}
	}

	/**
	 * ??©ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½è½°ï¿½ï¿½æ??ï¿?
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Util.StartActivity(MyPicsView.this, MainActivity.class);
			return false;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			Util.StartActivity(MyPicsView.this, MainActivity.class);
			break;
		case R.id.save_pic:
			saveBitmap(mMyPic);
			break;
		}
	}

	/**
	 * æ·?ï¿½ç??ï¿½ï¿½ï¿½å?§ï¿½ï¿½ï¿½ï¿½è??ï¿½ï¿½ï¿½ï¿½ï¿?
	 * 
	 * @param bitmap
	 */
	private void saveBitmap(Bitmap bitmap) {
		File fileDir = new File(Environment.getExternalStorageDirectory()
				+ "/wangsz");
		FileOutputStream fos = null;
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}

		File file = new File(fileDir, mMyPicName);
		
		if (file.exists()) {
			file.delete();
		}
		
		try {
			fos = new FileOutputStream(file);
			if (bitmap != null) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				Toast toast = Toast.makeText(getApplicationContext(), 
						"ï¿½ï¿½??§ï¿½ï¿½å?¸è¹­ï¿½ï¿½???ï¿½å?¸è¹­ï¿½ï¿½???ï¿½ï¿½ï¿½ï¿½"+ Environment.getExternalStorageDirectory() + "/wangsz",
						Toast.LENGTH_SHORT);
				toast.show();
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}