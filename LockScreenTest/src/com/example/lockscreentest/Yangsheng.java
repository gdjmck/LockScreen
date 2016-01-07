package com.example.lockscreentest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.lockscreentest.Database.DBmani;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Yangsheng extends Activity{
	
	private DBmani mani;
	private ListView listView;
	private ArrayList<Map<String,Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yangsheng);
		
		listView = (ListView) findViewById(R.id.yangsheng_list);
		mani = new DBmani(Yangsheng.this, 1);
		
		String sql = "select * from YangSheng";
		List<Map<String, Object>> tmpList =  mani.list(sql, null);
		for(Map<String, Object> obj : tmpList){
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("title", obj.get("title"));
			list.add(item);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.listitem, new String[]{"title"}, new int[]{R.id.list_item});
		listView.setAdapter(adapter);
	}

}
