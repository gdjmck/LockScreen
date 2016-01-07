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
	ImageSwitcher imageSwitcher; // 澹�??��ImageSwitcher??��????????����?������?��??��???��
	Gallery gallery; // 澹�??��Gallery??��????????����?������?????��?��??�?
	int imagePosition; // ����????��??������??���?����???����?����?��������?��??
	// 澹�??����??������??����???��
	private String[] images;

	// ??�����������?
	private ImageButton backIBtn;

	// �?��??����?���?
	private ImageButton saveIBtn;

	// 褰�������?���?
	private Bitmap mMyPic;
	// 褰�������?������
	private String mMyPicName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_pictures_view);

		images = getPicsNames();

		// ��������???��???����??��
		LinearLayout view = (LinearLayout) findViewById(R.id.title_bar_coins);
		view.setVisibility(View.INVISIBLE);

		saveIBtn = (ImageButton) findViewById(R.id.save_pic);
		saveIBtn.setVisibility(View.VISIBLE);
		saveIBtn.setOnClickListener(this);

		backIBtn = (ImageButton) findViewById(R.id.back);
		backIBtn.setOnClickListener(this);

		// ����?����??�?���?ID��峰��?imageSwitcher����?��?????
		imageSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		// ?????��??����?���?������??������?��??宸�?���绫�?
		imageSwitcher.setFactory(new MyViewFactory(this));
		// ����?����??�?���?ID��峰��?gallery����?��?????
		gallery = (Gallery) findViewById(R.id.gallery);
		// ?????��??����?���?������??������������
		gallery.setAdapter(new ImageAdapter(this));
		// ??����???������??�����?��?????��������
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// ����?��姹�浣����?��寰�������?��??��??���?
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

	// ���濮������??�������?�?
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

	// 寰���??��??���?
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

	// ����?���?����?������������???��????��BaseAdapter
	class ImageAdapter extends BaseAdapter {
		private Context context; // ??���?��??��??����

		// ������??�?�?��??����������������??��
		public ImageAdapter(Context context) {
			this.context = context;
		}

		// 寰���??��??������澶�?��
		@Override
		public int getCount() { // ?????��??�????��??��������澶�?��
			return images.length;
		}

		// 寰���???��??����??������??��?????
		@Override
		public Object getItem(int position) {
			return position;
		}

		// 寰���???��??����??������??��?????���?ID
		@Override
		public long getItemId(int position) {
			return position;
		}

		// ��??��??��???������??�?
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(context); // ����?��ImageView??��?????
			iv.setImageBitmap(getBitMap(images[position % images.length])); // ?????��??寰�������?��??��??���?
			iv.setAdjustViewBounds(true); // ��??��������???�����?��?��??
			// ?????��??��??������??��??���?�?
			iv.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			return iv; // ??�����?ImageView??��?????
		}
	}

	// ����?���?����?������?��??宸�?���绫�?���???��ViewFactory
	class MyViewFactory implements ViewFactory {
		private Context context; // ??���?��??��??����

		// ������??�?�?��??����������������??��
		public MyViewFactory(Context context) {
			this.context = context;
		}

		// ��??��??��???����???��
		@Override
		public View makeView() {
			ImageView iv = new ImageView(context); // ����?��ImageView??��?????
			iv.setScaleType(ImageView.ScaleType.FIT_CENTER); // ��??��������??����??����?��??
			// ?????��??��??������??��??���?�?
			iv.setLayoutParams(new ImageSwitcher.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			return iv; // ??�����?ImageView??��?????
		}
	}

	/**
	 * ??����������轰���??�?
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
	 * �?��??����?������??�����?
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
						"��??����?�蹭��???��?�蹭��???����"+ Environment.getExternalStorageDirectory() + "/wangsz",
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