package blockService;





import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



/** 
 * @author Chan Kong
 * 
 * 
 */
public class BlockUtils {

	
	/**
	 * 服务是否在运行
	 * 
	 * @param instance
	 * @param serviceClass
	 */
	public static boolean isBlockServiceRunning( Activity instance, Class<?> serviceClass ){
		
		return isMyServiceRunning(instance, serviceClass);
	}
	
	private static boolean isMyServiceRunning(Activity instance, Class<?> serviceClass) {
		 ActivityManager manager = (ActivityManager) instance.getSystemService(Context.ACTIVITY_SERVICE);
		 for(RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
			 if (serviceClass.getName().equals(service.service.getClassName())) {
		            return true;
		        }
		 }
		 
		return false;
		
	}
	
	
	/**
	 * 存储block列表
	 * 
	 * @param context
	 * @param list
	 * @return
	 */
	public static void save( Context context, ArrayList<String> list ){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);  
		 SharedPreferences.Editor editor= sharedPreferences.edit();  
		 
		 Set<String> set = new HashSet<String>(list);
		 
		 editor.putStringSet("applist", set);
		 
		 editor.commit();
	}
	
	
	/**
	 * 获取block列表
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getBlockList( Context context ){
		ArrayList<String> list = new ArrayList<String>();
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context); 
		Set<String> set = sharedPreferences.getStringSet("applist", null);
		if(set!=null){
			list =  new ArrayList<String>(set);
		}
		
		return list;
	}
	
	
	
	
}
