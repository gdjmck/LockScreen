package com.example.lockscreentest;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ReviseMode extends Activity {
	private List<Map<String, Object>> reviseList;
	private com.example.lockscreentest.Database.DBmani mani;
	private Button startRevise, get, unget;
	private TextView title, answer, unlockNum, rightNum, viewNum, answerNum;
	private int location;
	private Spinner menu;
	private ArrayAdapter adapter;
	
	private void notice()
	{
		new AlertDialog.Builder(ReviseMode.this).setTitle("复习列表为空")
		.setMessage("请继续使用屏幕解锁学习")
		.setPositiveButton("返回", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				onBackPressed();
			}

			
		}).create()
		.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mani = new com.example.lockscreentest.Database.DBmani(ReviseMode.this);
		reviseList = mani
				.listAll("select sub_info,state,sub_type from subject where state=='unget' and sub_type = 0",
						null);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);
		
		//设置时间，若日期过期则更新
		Calendar c = Calendar.getInstance();
		if(!Global.getDate().equals(String.valueOf(c.get(Calendar.YEAR)) + "-" + String.valueOf((1+c.get(Calendar.MONTH))) + "-" + String.valueOf(c.get(Calendar.DAY_OF_MONTH)))){
			Global.reset();
			Global.setDate(String.valueOf(c.get(Calendar.YEAR)) + "-" + String.valueOf((1+c.get(Calendar.MONTH))) + "-" + String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
		}
		System.out.println("日期是" + c.get(Calendar.YEAR) + "年, " + (1+c.get(Calendar.MONTH)) + "月," + c.get(Calendar.DAY_OF_MONTH) + "日");
		
		
		
		final Random rand = new Random();
		startRevise = (Button) findViewById(R.id.startreview);
		unlockNum = (TextView) findViewById(R.id.unlock_num);
		rightNum = (TextView) findViewById(R.id.right_answerNum);
		viewNum = (TextView) findViewById(R.id.viewNum);
		answerNum = (TextView) findViewById(R.id.answerModeNum);
		menu = (Spinner) findViewById(R.id.bt1);
		
		unlockNum.setText(String.valueOf(Global.getUnlockNum()));
		rightNum.setText(String.valueOf(Global.getRight_answerNum()));
		viewNum.setText(String.valueOf(Global.getViewNum()));
		answerNum.setText(String.valueOf(Global.getAnswerNum()));
		
		adapter = ArrayAdapter.createFromResource(this, R.array.menu_item,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		menu.setAdapter(adapter);
		
		startRevise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContentView(R.layout.review_choice);
				//如果复习列表为空
				if(reviseList.isEmpty()){
					notice();
					return;
				}
				
				//打印unget列表
				for (int i = 0; i < reviseList.size(); i++) {

					System.out.println("type : "
							+ reviseList.get(i).get("sub_type") + "----state : "
							+ reviseList.get(i).get("state") + "-----"
							+ reviseList.get(i).get("sub_info"));
				}
				
				title = (TextView) findViewById(R.id.title);
				answer = (TextView) findViewById(R.id.answer);
				location = rand.nextInt(reviseList.size());
				title.setText((String) reviseList.get(location).get("sub_info"));
				if (reviseList.get(location).get("answer") != null)
					answer.setText((String) reviseList.get(location).get(
							"answer"));
				else
					answer.setText("");
				get = (Button) findViewById(R.id.get);
				unget = (Button) findViewById(R.id.unget);

				get.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// 修改数据库该记录的状态
						String curText = (String) title.getText();
						String[] params = { "sub_info", "'" + curText + "'",
								"get" };
						mani.update(params);

						// 从reviseList中删掉这条信息
						reviseList.remove(location);
						//判断复习列表此时是否为空
						if(reviseList.isEmpty())
						{
							notice();
							return;
						}
						location = rand.nextInt(reviseList.size());
						title.setText((String) reviseList.get(location).get(
								"sub_info"));
						if (reviseList.get(location).get("answer") != null)
							answer.setText((String) reviseList.get(location)
									.get("answer"));
						else
							answer.setText("");
					}

				});

				
				
				//有除数为0的错误
				unget.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// 从reviseList中删掉这条信息
						reviseList.remove(location);
						//判断复习列表此时是否为空
						if(reviseList.isEmpty())
						{
							System.out.println("已经完了");
							notice();
							return;
						}
						location = rand.nextInt(reviseList.size());
						System.out.println("size = " + reviseList.size() + " , location = " + location);
						title.setText((String) reviseList.get(location).get(
								"sub_info"));
						if (reviseList.get(location).get("answer") != null)
							answer.setText((String) reviseList.get(location)
									.get("answer"));
						else
							answer.setText("");
					}

				});
			}

		});

		for (int i = 0; i < reviseList.size(); i++) {
			System.out.print(reviseList.get(i).get("sub_info"));
		}
	}

}
