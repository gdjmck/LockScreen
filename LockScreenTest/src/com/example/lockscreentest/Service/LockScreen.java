package com.example.lockscreentest.Service;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lockscreentest.R;
import com.example.lockscreentest.Global;

/**
 * @author Chan Kong
 * 
 * 
 */

public class LockScreen extends Activity implements OnTouchListener,
		OnGestureListener {
	private int mode;// 0�����ģʽ��1�Ǵ���ģʽ
	private int q_type;//0:��ҽ 1:��ҩ 2:��ҽҩ
	private GestureDetector gesture = new GestureDetector(this);
	private LinearLayout layout;
	private ImageView image;
	private Button answer1, answer2;
	private TextView text;
	private int count = 0;
	private com.example.lockscreentest.Database.DBmani mani;
	private static int verticalMinDistance = 40;
	private static int horizonalMinDistance = 20;
	private String[] answer;
	Map<String, Object> map;
	List<Map<String, Object>> list, pics;
	
	//ÿ��һ����ߴ�һ��ͼ�����1
	private void addview(){
		Global.setViewNum(1 + Global.getViewNum());
	}
	
	private void addUnlockCount()
	{
		Global.setUnlockNum(Global.getUnlockNum() + 1);
	}
	
	private void addAnswerNum(){
		Global.setAnswerNum(Global.getAnswerNum() + 1);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mode = Global.getLockscreen_type();
		q_type = Global.getQuestion_type();
		mani = new com.example.lockscreentest.Database.DBmani(LockScreen.this);
		Log.e("LockScreen", "now im in and mode= " + mode);
		setContentView(mode==0?R.layout.view:R.layout.view1);
		// ���ģʽ
		if (mode == 0) {
//			setContentView(R.layout.view);
			image = (ImageView) findViewById(R.id.image);
			text = (TextView) findViewById(R.id.text);
			switch (q_type){
			case 0:
				list = mani
				.list("select sub_info from subject where state!='get' and med_type = 'zhongyi' and sub_type = 0",
						null);
				break;
			case 1:
				list = mani
				.list("select sub_info from subject where state!='get' and med_type = 'zhongyao' and sub_type = 0",
						null);
				break;
			case 2:
				list = mani
					.list("select sub_info from subject where state!='get' and sub_type = 0",
							null);
				break;
				default :
					list = mani
					.list("select sub_info from subject where state!='get' and sub_type = 0",
							null);
				break;
			}
			
			text.setText((String) list.get(0).get("sub_info"));
		}
		// ����ģʽ
		if (mode == 1) {
//			setContentView(R.layout.view1);
			text = (TextView) findViewById(R.id.q_text);
			image = (ImageView) findViewById(R.id.q_image);
			answer1 = (Button) findViewById(R.id.q_answer1);
			answer2 = (Button) findViewById(R.id.q_answer2);
			switch (q_type){
			case 0:
				list = mani
				.list("select sub_info,choice,answer from subject where sub_type = 1 and med_type = 'zhongyi' and state != 'get'",
						null);
				break;
				
			case 1:
				list = mani
				.list("select sub_info,choice,answer from subject where sub_type = 1 and med_type = 'zhongyao' and state != 'get'",
						null);
				break;
			case 3:
				
			list = mani
					.list("select sub_info,choice,answer from subject where sub_type = 1 and state != 'get'",
							null);
			break;
			default:
				list = mani
				.list("select sub_info,choice,answer from subject where sub_type = 1 and state != 'get'",
						null);
		break;}
			String[] answers = ((String) list.get(0).get("choice")).split(" ");
			answer = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				answer[i] = (String) list.get(i).get("answer");
			}
			answer1.setText(answers[0]);
			answer2.setText(answers[1]);
			text.setText((String)list.get(0).get("sub_info"));
			answer1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//ÿ��һ�������1
					addAnswerNum();
					if (answer1.getText().toString().split("\\.")[0].equals(answer[count])) {
						//��Լ�����1
						int rightNum = Global.getRight_answerNum() + 1;
						Global.setRight_answerNum(rightNum);
						count++;
						if (count > Global.getScreen_count() - 1 || count == list.size()) {
							Finish();
							return;
						}
						text.setText((String) list.get(count).get("sub_info"));
						String[] answers = ((String) list.get(count).get(
								"choice")).split(" ");
						answer1.setText(answers[0]);
						answer2.setText(answers[1]);
					} else {
						Toast.makeText(LockScreen.this, "�𰸴��ˣ�������ѡ��",
								Toast.LENGTH_SHORT).show();
					}
				}
			});

			answer2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//ÿ��һ�������1
					addAnswerNum();
					System.out.println("answer:" + answer[count]);
					System.out.println("  title:" +  answer2.getText().toString().split("."));
					if (answer2.getText().toString().split("\\.")[0].equals(answer[count])) {
						//��Լ�����1
						int rightNum = Global.getRight_answerNum() + 1;
						Global.setRight_answerNum(rightNum);
						count++;
						if (count > Global.getScreen_count() - 1 || count == list.size()) {
							Finish();
							return;
						}
						text.setText((String) list.get(count).get("sub_info"));
						String[] answers = ((String) list.get(count).get(
								"choice")).split(" ");
						answer1.setText(answers[0]);
						answer2.setText(answers[1]);
					} else {
						Toast.makeText(LockScreen.this, "�𰸴��ˣ�������ѡ��",
								Toast.LENGTH_SHORT).show();
					}
				}
			});
		}

		layout = (LinearLayout) findViewById(mode==0?R.id.layout:R.id.layout1);
		Bitmap wallpaper = Global.getsWallpaper();
		if(wallpaper!=null){
			layout.setBackground(new BitmapDrawable(wallpaper));
		}
		layout.setOnTouchListener(this);
		layout.setLongClickable(true);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		if (mode == 0) {
			if (arg0.getX() - arg1.getX() > horizonalMinDistance
					&& Math.abs(arg2) > 0 && Math.abs(arg3) < 10
					&& arg0.getY() - arg1.getY() < verticalMinDistance) {
				
				//���������1
				addview();
				
				Log.v("����", "��");
				Toast.makeText(LockScreen.this, "��һ��",
						Toast.LENGTH_SHORT).show();
				count++;

				if (count > com.example.lockscreentest.Global.getScreen_count() - 1 || count == list.size()) {

					list = mani.seeAllData();
					for (int i = 0; i < list.size(); i++) {

						System.out.println("type : "
								+ list.get(i).get("sub_type") + "----state : "
								+ list.get(i).get("state") + "-----"
								+ list.get(i).get("sub_info"));
					}
					Finish();
					return true;
				}
				// ��û��ָ����Ŀ
				else {
					text.setText((String) list.get(count).get("sub_info"));
				}
			} else if (arg1.getX() - arg0.getX() > horizonalMinDistance
					&& Math.abs(arg2) > 0
					&& arg0.getY() - arg1.getY() < verticalMinDistance) {
				count++;
				
				//���������1
				addview();
				
				Log.v("����", "�һ�");
				Toast.makeText(LockScreen.this, "��һ��",
						Toast.LENGTH_SHORT).show();
				if (count > com.example.lockscreentest.Global.getScreen_count() - 1 || count == list.size()) {
					list = mani.seeAllData();
					for (int i = 0; i < list.size(); i++) {

						System.out.println("type : "
								+ list.get(i).get("sub_type") + "----state : "
								+ list.get(i).get("state") + "-----"
								+ list.get(i).get("sub_info"));
					}
					Finish();
					return true;

				}
				// ��û��ָ����Ŀ
				else {
					text.setText((String) list.get(count).get("sub_info"));
				}
			} else if (arg0.getY() - arg1.getY() > verticalMinDistance
					&& Math.abs(arg3) > 10) {
				
				//���������1
				addview();
				
				Log.v("����", "�ϻ�");
				Toast.makeText(LockScreen.this, "��ס",
						Toast.LENGTH_SHORT).show();
				count++;
				// �޸����ݿ��ж�Ӧ��¼��״̬
				String curText = (String) text.getText();
				String[] params = { "sub_info", "'" + curText + "'", "get" };
				mani.update(params);
				Log.d("Lockscreen", curText + "�Ѿ���ס");
				if (count > com.example.lockscreentest.Global.getScreen_count() - 1 || count == list.size()) {
					list = mani.seeAllData();
					for (int i = 0; i < list.size(); i++) {

						System.out.println("type : "
								+ list.get(i).get("sub_type") + "----state : "
								+ list.get(i).get("state") + "-----"
								+ list.get(i).get("sub_info"));
					}
					Finish();
					return true;
				}

			} else if (arg1.getY() - arg0.getY() > verticalMinDistance
					&& Math.abs(arg3) > 10) {
				Log.v("����", "�»�");
				
				//���������1
				addview();
				
				Toast.makeText(LockScreen.this, "�������ʱ�", Toast.LENGTH_SHORT)
						.show();
				count++;
				// �޸����ݿ��ж�Ӧ��¼��״̬
				String curText = (String) text.getText();
				String[] params = { "sub_info", "'" + curText + "'", "unget" };
				mani.update(params);
				Log.d("Lockscreen", curText + "��������");
				if (count > com.example.lockscreentest.Global.getScreen_count() - 1 || count == list.size()) {
					list = mani.seeAllData();
					for (int i = 0; i < list.size(); i++) {

						System.out.println("type : "
								+ list.get(i).get("sub_type") + "----state : "
								+ list.get(i).get("state") + "-----"
								+ list.get(i).get("sub_info"));
					}
					Finish();
					return true;
				}

			} else {
				return false;
			}
			// }

			return false;
		}

		// ����ģʽ
		else if (mode == 1) {
			if (arg0.getY() - arg1.getY() > verticalMinDistance
					&& Math.abs(arg3) > 10) {
				Log.v("����", "�ϻ�");
				
				//���������1
				addview();
				
				Toast.makeText(LockScreen.this, "�ϻ���ס", Toast.LENGTH_SHORT)
						.show();
				count++;
				// �޸����ݿ��ж�Ӧ��¼��״̬
				String curText = (String) text.getText();
				String[] params = { "sub_info", "'" + curText + "'", "get" };
				mani.update(params);
				Log.d("Lockscreen", curText + "�Ѿ���ס");
				if (count > com.example.lockscreentest.Global.getScreen_count() - 1 || count == list.size()) {
					list = mani.seeAllData();
					for (int i = 0; i < list.size(); i++) {

						System.out.println("type : "
								+ list.get(i).get("sub_type") + "----state : "
								+ list.get(i).get("state") + "-----"
								+ list.get(i).get("sub_info"));
					}
					Finish();
					return true;
				}

			} else if (arg1.getY() - arg0.getY() > verticalMinDistance
					&& Math.abs(arg3) > 10) {
				Log.v("����", "�»�");
				
				//���������1
				addview();
				
				Toast.makeText(LockScreen.this, "�»�", Toast.LENGTH_SHORT)
						.show();
				count++;
				// �޸����ݿ��ж�Ӧ��¼��״̬
				String curText = (String) text.getText();
				String[] params = { "sub_info", "'" + curText + "'", "unget" };
				mani.update(params);
				Log.d("Lockscreen", curText + "��������");
				if (count > com.example.lockscreentest.Global.getScreen_count() - 1 || count == list.size()) {
					list = mani.seeAllData();
					for (int i = 0; i < list.size(); i++) {

						System.out.println("type : "
								+ list.get(i).get("sub_type") + "----state : "
								+ list.get(i).get("state") + "-----"
								+ list.get(i).get("sub_info"));
					}
					Finish();
					return true;
				}

			}
		}

		return false;

	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return gesture.onTouchEvent(arg1);
	}

	private void Finish() {
		addUnlockCount();
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addCategory(Intent.CATEGORY_HOME);
		startActivity(i);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		count = 0;
	}

	class learnGestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			if (arg0.getX() - arg1.getX() > horizonalMinDistance
					&& Math.abs(arg2) > 0 && Math.abs(arg3) < 10
					&& arg0.getY() - arg1.getY() < verticalMinDistance) {
				Log.v("����", "��");
				Toast.makeText(LockScreen.this, "��" + String.valueOf(count),
						Toast.LENGTH_SHORT).show();
				count++;

				if (count > com.example.lockscreentest.Global.getScreen_count() - 1 || count == list.size()) {

					list = mani.seeAllData();
					for (int i = 0; i < list.size(); i++) {

						System.out.println("type : "
								+ list.get(i).get("sub_type") + "----state : "
								+ list.get(i).get("state") + "-----"
								+ list.get(i).get("sub_info"));
					}
					Finish();
					return true;
				}
				// ��û��ָ����Ŀ
				else {
					text.setText((String) list.get(count).get("sub_info"));
				}
			} else if (arg1.getX() - arg0.getX() > horizonalMinDistance
					&& Math.abs(arg2) > 0
					&& arg0.getY() - arg1.getY() < verticalMinDistance) {
				count++;
				Log.v("����", "�һ�");
				Toast.makeText(LockScreen.this, "�һ�" + String.valueOf(count),
						Toast.LENGTH_SHORT).show();
				if (count > com.example.lockscreentest.Global.getScreen_count() - 1 || count == list.size()) {
					list = mani.seeAllData();
					for (int i = 0; i < list.size(); i++) {

						System.out.println("type : "
								+ list.get(i).get("sub_type") + "----state : "
								+ list.get(i).get("state") + "-----"
								+ list.get(i).get("sub_info"));
					}
					Finish();
					return true;

				}
				// ��û��ָ����Ŀ
				else {
					text.setText((String) list.get(count).get("sub_info"));
				}
			} else if (arg0.getY() - arg1.getY() > verticalMinDistance
					&& Math.abs(arg3) > 10) {
				Log.v("����", "�ϻ�");
				Toast.makeText(LockScreen.this, "�ϻ���ס" + String.valueOf(count),
						Toast.LENGTH_SHORT).show();
				count++;
				// �޸����ݿ��ж�Ӧ��¼��״̬
				String curText = (String) text.getText();
				String[] params = { "sub_info", "'" + curText + "'", "get" };
				mani.update(params);
				Log.d("Lockscreen", curText + "�Ѿ���ס");
				if (count > com.example.lockscreentest.Global.getScreen_count() - 1 || count == list.size()) {
					list = mani.seeAllData();
					for (int i = 0; i < list.size(); i++) {

						System.out.println("type : "
								+ list.get(i).get("sub_type") + "----state : "
								+ list.get(i).get("state") + "-----"
								+ list.get(i).get("sub_info"));
					}
					Finish();
					return true;
				}

			} else if (arg1.getY() - arg0.getY() > verticalMinDistance
					&& Math.abs(arg3) > 10) {
				Log.v("����", "�»�");
				Toast.makeText(LockScreen.this, "�»�", Toast.LENGTH_SHORT)
						.show();
				count++;
				// �޸����ݿ��ж�Ӧ��¼��״̬
				String curText = (String) text.getText();
				String[] params = { "sub_info", "'" + curText + "'", "unget" };
				mani.update(params);
				Log.d("Lockscreen", curText + "��������");
				if (count > com.example.lockscreentest.Global.getScreen_count() - 1 || count == list.size()) {
					list = mani.seeAllData();
					for (int i = 0; i < list.size(); i++) {

						System.out.println("type : "
								+ list.get(i).get("sub_type") + "----state : "
								+ list.get(i).get("state") + "-----"
								+ list.get(i).get("sub_info"));
					}
					Finish();
					return true;
				}

			} else {
				return false;
			}
			// }

			return false;
		}

	}

}
