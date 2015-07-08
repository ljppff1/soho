/**
 * @Title CheckServiceUtil.java
 * @Package com.superdata.soho.util
 * @author Administrator
 * @date 2014年7月24日 下午1:59:32
 * @version V1.0
 */
package com.superdata.soho.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * @ClassName CheckServiceUtil
 * @author Administrator
 * @date 2014年7月24日 下午1:59:32
 *
 */
public class CheckServiceUtil {

	public static boolean isServiceRun(Context context){
	     ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	     List<RunningServiceInfo> list = am.getRunningServices(30);
	     for(RunningServiceInfo info : list){
	         if(info.service.getClassName().trim().equals("com.superdata.soho.service.UploadPostionService")){
	                  return true;
	         }
	    }
	    return false;
	}
}
