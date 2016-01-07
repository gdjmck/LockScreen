package com.example.lockscreentest.wangsz.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lockscreentest.R;
import com.example.lockscreentest.wangsz.data.Const;
import com.example.lockscreentest.wangsz.model.IAlertDialogButtonListener;

public class Util {

	private static AlertDialog mAlertDialog;

	public static View getView(Context context, int layoutId) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View layout = inflater.inflate(layoutId, null);

		return layout;
	}

	/**
	 * ï¿½ï¿½ï¿½ï¿½ï¿½ã?£ç?????ï¿?
	 * 
	 * @param content
	 *            è¤°ï¿½ï¿½ï¿½ï¿½æ¤¤??¸ï¿½ï¿?
	 * @param desti
	 *            ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½æ¤¤ç?¸ï¿½ï¿?Class
	 */
	public static void StartActivity(Context context, Class desti) {
		Intent intent = new Intent();
		intent.setClass(context, desti);
		context.startActivity(intent);

		// ï¿½ï¿½??½ï¿½ï¿½è¤°ï¿½ï¿½ï¿½ï¿½Activity
		((Activity) context).finish();
	}

	/**
	 * ï¿½ï¿½??§ã??ï¿½ï¿½ï¿½ç?????ï¿½ç??ï¿½ï¿½å¦?ï¿?
	 * 
	 * @param context
	 *            ï¿½ï¿½ï¿½æ£°ï¿?
	 * @param message
	 *            ï¿½ï¿½ï¿½ç?????ï¿½ï¿½??¹ï¿½
	 * @param listener
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void showDialog(Context context, String message,
			final IAlertDialogButtonListener listener) {
		View dialogView = null;

		AlertDialog.Builder builder = new AlertDialog.Builder(context,
				R.style.Theme_Transparent);
		dialogView = getView(context, R.layout.dialog_view);

		ImageButton btnOkView = (ImageButton) dialogView
				.findViewById(R.id.btn_ok);
		ImageButton btnCancelView = (ImageButton) dialogView
				.findViewById(R.id.btn_cancel);
		TextView textMessageView = (TextView) dialogView
				.findViewById(R.id.tv_dialog_message);

		textMessageView.setText(message);

		btnOkView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// ï¿½ï¿½??½ï¿½ï¿½ç?µç??ï¿½ï¿½å¦?ï¿?
				if (mAlertDialog != null) {
					mAlertDialog.cancel();
				}

				// æµ?ï¿½æ??è·ºï¿½ï¿½ç??ï¿?
				if (listener != null) {
					listener.onClick();
				}
			}
		});

		btnCancelView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// ï¿½ï¿½??½ï¿½ï¿½ç?µç??ï¿½ï¿½å¦?ï¿?
				if (mAlertDialog != null) {
					mAlertDialog.cancel();
				}
			}
		});

		// æ¶?ï¿?dialog?????§ç??View
		builder.setView(dialogView);
		mAlertDialog = builder.create();

		// ï¿½ï¿½??§ã????µç??ï¿½ï¿½å¦?ï¿?
		mAlertDialog.show();
	}

	/**
	 * ï¿½ï¿½???ï¿½ï¿½æ·?ï¿½ç??ï¿?
	 * 
	 * @param context
	 * @param stageIndex
	 *            ï¿½ï¿½???ï¿½ï¿½
	 * @param coins
	 *            ï¿½ï¿½ï¿½ç??ï¿½ï¿½ï¿½ï¿½
	 */
	public static void saveData(Context context, int stageIndex, int coins) {
		FileOutputStream fos = null;

		try {
			fos = context.openFileOutput(Const.FILE_NAME_SAVE_DATA,
					Context.MODE_PRIVATE);
			DataOutputStream dos = new DataOutputStream(fos);

			dos.writeInt(stageIndex);
			if (coins != -1) {
				dos.writeInt(coins);
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

	/**
	 * ???è¯²ï¿½ï¿½ï¿½ï¿½ç??ï¿½ï¿½
	 * 
	 * @param context
	 * @return
	 */
	public static int[] loadData(Context context) {
		FileInputStream fis = null;
		int[] datas = { Const.BEGIN_STAGE, Const.TOTAL_COINT };

		try {
			fis = context.openFileInput(Const.FILE_NAME_SAVE_DATA);
			DataInputStream dis = new DataInputStream(fis);

			datas[Const.INDEX_LOAD_DATA_STAGE] = dis.readInt();
			datas[Const.INDEX_LOAD_DATA_COINS] = dis.readInt();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return datas;
	}
}
