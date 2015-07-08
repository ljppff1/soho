package com.superdata.soho.common;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * 帮助类
 * @author 谭树林 
 *
 */
public class Utils {
	/**
	 * 字符串 -> Map
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param obj
	 * @return
	 */
	public Map<String, String> string2Map(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, new TypeToken<Map<String, String>>() {
		}.getType());
	}

	/**
	 * 字符串 -> List
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param obj
	 * @return
	 */
	public static List<Map<String, String>> string2Liststr(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString,
				new TypeToken<List<Map<String, String>>>() {
				}.getType());
	}

	public static List<Map<String, Object>> string2ListObject(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString,
				new TypeToken<List<Map<String, Object>>>() {
				}.getType());
	}
	public static List<String> string2List(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString,
				new TypeToken<List<String>>() {
		}.getType());
	}

	public static List<List<Map<String, String>>> string2Lists(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString,
				new TypeToken<List<List<Map<String, String>>>>() {
				}.getType());
	}

	public static List<Map<String, List<Map<String, String>>>> string2MapList(
			String jsonString) {

		Gson gson = new Gson();
		return gson.fromJson(jsonString,
				new TypeToken<List<Map<String, List<Map<String, String>>>>>() {
				}.getType());
	}

	/**
	 * 判断是否有网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (connectivity == null) {   
            return false;   
        } else {   
            NetworkInfo[] info = connectivity.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    }
 

	/**
	 * 获取当前网络连接的类型信息
	 * 
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}
	
	public static  int getMetricsWidth(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	
	public static int sp2px(Activity activity, float spValue) {
        float fontScale = activity.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Activity activity, float pxValue) {
        float fontScale = activity.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dip2px(Activity activity, int dipValue) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Activity activity, float pxValue) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
