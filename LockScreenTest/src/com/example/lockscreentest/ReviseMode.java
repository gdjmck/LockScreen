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
		new AlertDialog.Builder(ReviseMode.this).setTitle("��ϰ�б�Ϊ��")
		.setMessage("�����ʹ����Ļ����ѧϰ")
		.setPositiveButton("����", new DialogInterface.OnClickListener(){

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
		
		//����ʱ�䣬�����ڹ��������
		Calendar c = Calendar.getInstance();
		if(!Global.getDate().equals(String.valueOf(c.get(Calendar.YEAR)) + "-" + String.valueOf((1+c.get(Calendar.MONTH))) + "-" + String.valueOf(c.get(Calendar.DAY_OF_MONTH)))){
			Global.reset();
			Global.setDate(String.valueOf(c.get(Calendar.YEAR)) + "-" + String.valueOf((1+c.get(Calendar.MONTH))) + "-" + String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
		}
		System.out.println("������" + c.get(Calendar.YEAR) + "��, " + (1+c.get(Calendar.MONTH)) + "��," + c.get(Calendar.DAY_OF_MONTH) + "��");
		
		
		
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
				//�����ϰ�б�Ϊ��
				if(reviseList.isEmpty()){
					notice();
					return;
				}
				
				//��ӡunget�б�
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
						// �޸����ݿ�ü�¼��״̬
						String curText = (String) title.getText();
						String[] params = { "sub_info", "'" + curText + "'",
								"get" };
						mani.update(params);

						// ��reviseList��ɾ��������Ϣ
						reviseList.remove(location);
						//�жϸ�ϰ�б��ʱ�Ƿ�Ϊ��
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

				
				
				//�г���Ϊ0�Ĵ���
				unget.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// ��reviseList��ɾ��������Ϣ
						reviseList.remove(location);
						//�жϸ�ϰ�б��ʱ�Ƿ�Ϊ��
						if(reviseList.isEmpty())
						{
							System.out.println("�Ѿ�����");
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
