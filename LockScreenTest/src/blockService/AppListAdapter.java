package blockService;



import java.util.ArrayList;
import java.util.List;




import com.example.lockscreentest.R;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/** 
 * @author Chan Kong
 * 
 * 
 */
public class AppListAdapter extends BaseAdapter {
	
	private Activity mInstance;
	private List<PackageInfo> mInstalledList = null;
	private ArrayList<String> mCheckedList = null;
	
	public AppListAdapter( Activity instance, List<PackageInfo> installedList, ArrayList<String> checkedList ){
		
		mInstance = instance;
		mInstalledList = installedList;
		mCheckedList = checkedList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mInstalledList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mInstalledList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
ViewHolder holder = null;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(mInstance).inflate(R.layout.listview_item, null);
			
			holder = new ViewHolder(); 
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
			holder.textView = (TextView) convertView.findViewById(R.id.app_name);
			holder.imageView = (ImageView) convertView.findViewById(R.id.app_logo);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		final PackageInfo packageInfo = mInstalledList.get(position);
		
		holder.textView.setText( packageInfo.applicationInfo.loadLabel(mInstance.getPackageManager()).toString() );
		holder.imageView.setImageResource(packageInfo.applicationInfo.icon);
		Drawable drawable = packageInfo.applicationInfo.loadIcon(mInstance.getPackageManager());
		holder.imageView.setBackgroundDrawable(drawable);
		
		if (contains(mCheckedList, packageInfo)) {
			holder.checkBox.setChecked(true);
		} else {
			holder.checkBox.setChecked(false);
		}
		
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (contains(mCheckedList, packageInfo)) {
					remove(mCheckedList, packageInfo);
				} else {
					mCheckedList.add(packageInfo.packageName);
				}
				
				BlockUtils.save(mInstance, mCheckedList);
				
				notifyDataSetChanged();
			}
		});
		
		return convertView;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean contains(ArrayList<String> list, PackageInfo item){
		if(list==null || item==null){
			return false;
		}
		
		for(int i=0; i<list.size(); i++){
			
			if(list.get(i).equals(item.packageName)){
				return true;
			}
		}
		
		return false;
	}
	
	
	private void remove(ArrayList<String> list, PackageInfo item){
		if(list==null || item==null){
			return;
		}
		
		for(int i=0; i<list.size(); i++){
			
			if(list.get(i).equals(item.packageName)){
				list.remove(i);
				break;
			}
		}
	}
	
	class ViewHolder {
		public CheckBox checkBox;
		public TextView textView;
		public ImageView imageView;
	}

}
