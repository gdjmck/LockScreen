package com.example.lockscreentest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.example.lockscreentest.Database.DBmani;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class Search extends Activity {
	private Context context;
	private String searchInfo;
	private DBmani mani;
	private AutoCompleteTextView autoText;
	public static ArrayList<HashMap<String, String>> all_list;
	public static ArrayList<HashMap<String, String>> freq_list;
	
	private String searchTip;
	private List<Map<String, Object>> medList;
	
	private SharedPreferences sharePreferences;
	private ArrayAdapter<String> adapter;
	private Button ok;
	private TextView resultView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		context = getApplicationContext();
		
		mani = new DBmani(context, 1);
		
		
//		sharePreferences = getSharedPreferences("search_history", Activity.MODE_PRIVATE);
		
		ok = (Button) findViewById(R.id.search_click);
		resultView = (TextView) findViewById(R.id.search_result);
		resultView.setMovementMethod(ScrollingMovementMethod.getInstance());
		autoText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
		medList = mani.list("select * from db_medicine", null);
		initAutoComplete("search_history", autoText);
		
//		ActionBar actionBar = getActionBar();
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM);
//		setTitle(" ");
		
		ok.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchInfo = autoText.getText().toString();
				saveHistory("search_history",autoText);
				if (!searchInfo.equals("")) {
					String sql = "select name,introduction from db_medicine where name like '%"
							+ searchInfo + "%'";
					medList = mani.list(sql, null);
					if (medList != null) {
						String info = "";

						for (int i = 0; i < medList.size(); i++) {
							info += "\n\n\n" + (String)medList.get(i).get("name") +"\n" + (String)medList.get(i).get("introduction");
						}
						resultView.setText(info);
					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								Search.this);
						builder.setTitle("没有查询结果");
						builder.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								});
						builder.create().show();
					}
				}
				else{
					Toast.makeText(Search.this, "请在搜索框输入搜索内容", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	
	private void saveHistory(String field, AutoCompleteTextView view){
		String text = view.getText().toString();
		if(sharePreferences == null)
			sharePreferences = getSharedPreferences("search_history", 0);
		
		String longHistory = sharePreferences.getString(field, "");
		if(!longHistory.contains(text + ",")){
			StringBuilder builder = new StringBuilder(longHistory);
			builder.insert(0, (text + ","));
			sharePreferences.edit().putString("search_history", builder.toString()).commit();
		}
	}
	
	
	private void initAutoComplete(String field, AutoCompleteTextView view){
		sharePreferences = getSharedPreferences("search_history", 0);
		String longHistory = sharePreferences.getString("search_history", "");
		String[] histories = longHistory.split(",");
		
		//只保留最近50条
		if(histories.length > 50){
			String[] newHistories = new String[50];
			System.arraycopy(histories, 0, newHistories, 0, 50);
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, newHistories);
		}
		else
			adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, histories);
		view.setAdapter(adapter);
		
		view.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				AutoCompleteTextView view = (AutoCompleteTextView) v;
				if(hasFocus)
					view.showDropDown();
			}
		});
		
	}
	
}





