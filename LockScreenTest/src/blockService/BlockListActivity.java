package blockService;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import com.example.lockscreentest.R;

/**
 * @author Chan Kong
 * 
 * 
 */
public class BlockListActivity extends ListActivity {
	private AppListAdapter mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applist);

		List<PackageInfo> appList = getPackageManager().getInstalledPackages(0);
		List<PackageInfo> installedList = new ArrayList<PackageInfo>();

		for (PackageInfo packageInfo : appList) {

			if (!isSystemPackage(packageInfo)
					&& !getApplicationInfo().packageName
							.equals(packageInfo.packageName)) {
				installedList.add(packageInfo);
			}
		}

		mAdapter = new AppListAdapter(this, installedList,
				BlockUtils.getBlockList(getApplicationContext()));
		setListAdapter(mAdapter);
	}

	private boolean isSystemPackage(PackageInfo packageInfo) {
		return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
				: false;
	}

	//ºóÍË¼ü¿ªÆôSERVICE
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
			
		}
		return super.onKeyDown(keyCode, event);
	}

}
