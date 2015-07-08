/**
 * @Title UploadPostionService.java
 * @Package com.superdata.soho.service
 * @author Administrator
 * @date 2014年7月22日 上午11:19:14
 * @version V1.0
 */
package com.superdata.soho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.superdata.soho.activity.workattendance.StaffTrajectoryActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.util.SharedPreferencesConfig;

/**
 * 定时上传服务
 * 
 * @ClassName UploadPostionService
 * @author Administrator
 * @date 2014年7月22日 上午11:19:14
 * 
 */
public class UploadPostionService extends Service implements
		OnGetGeoCoderResultListener, BDLocationListener {

	String TAG = "UploadPostionService--";
	String sddr;
	AlarmManager am;
	Intent myintent;
	//PendingIntent pendingIntent;
	private Handler mHandler;
	Map<String, String> mapconfig;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(UploadPostionService.this, "上传地址!" + sddr, 1)
						.show();
				stopSelf();
				break;
			case 404:
				Toast.makeText(UploadPostionService.this, "服务器异常", 1).show();
				stopSelf();
				break;
			default:
				break;
			}
		};
	};
	GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	Double Latitude, Longitude;
	boolean isFirstLoc = true;// 是否首次定位
	LocationClient mLocClient;

	/*
	 * (非 Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		Log.v(TAG, "onBind");
		return null;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		InterfaceConfig.ALARMMANAGE_WORK_COUNT++;
		if(InterfaceConfig.ALARMMANAGE_WORK_COUNT>=3){
			Toast.makeText(UploadPostionService.this, "停止上传位置！", 1).show();
			AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
			alarm.cancel(StaffTrajectoryActivity.pendingIntent);  //停止闹钟
			InterfaceConfig.IS_ALARMMANAGE_WORK=false;
			stopSelf();
		}
		Log.v(TAG, "onCreate");

	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
		mLocClient.stop();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see android.app.Service#onStart(android.content.Intent, int)
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.v(TAG, "onStart");
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll"); // 设置坐标类型为bd09ll
		option.disableCache(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先
//		option.setScanSpan(60*1000);// 定位时间间隔
		mLocClient.setLocOption(option);
		mLocClient.start();
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
	}
	
	/*
	 * (非 Javadoc)
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "onStartCommand");

		return super.onStartCommand(intent, flags, startId);
	}

	Runnable mRunnableThread = new Runnable() {

		/*
		 * (非 Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			mapconfig = SharedPreferencesConfig.config(UploadPostionService.this);
			String companyId=mapconfig.get(InterfaceConfig.COMPANYID);
			String ID=mapconfig.get(InterfaceConfig.ID);
			String URL=InterfaceConfig.AUTO_UPLOAD_POSTION_URL;
			SDHttpClient sdClient = new SDHttpClient();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("companyId", companyId));
			params.add(new BasicNameValuePair("trackType", "5"));//打卡方式
			params.add(new BasicNameValuePair("latitude", Latitude+""));
			params.add(new BasicNameValuePair("longitude", Longitude+""));
			params.add(new BasicNameValuePair("address", sddr));
			params.add(new BasicNameValuePair("isReg", "0"));//是否补登 
			params.add(new BasicNameValuePair("empId", ID));
			try {
				String json = sdClient.post_session(URL, params);
				Log.v("---json---", json);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Message message;
			Log.v("mRunnableThread", "mRunnableThread");
			message = new Message();
			message.what = 0;
			message.obj = sddr;
			handler.sendMessage(message);
		}

	};

	/*
	 * (非 Javadoc)
	 * 
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#
	 * onGetGeoCodeResult(com.baidu.mapapi.search.geocode.GeoCodeResult)
	 */
	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {

	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#
	 * onGetReverseGeoCodeResult
	 * (com.baidu.mapapi.search.geocode.ReverseGeoCodeResult)
	 */
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(UploadPostionService.this, "抱歉，未能找到结果",
					Toast.LENGTH_LONG).show();
		}
		Log.v(TAG, "onGetReverseGeoCodeResult");
		sddr = result.getAddress();
		HandlerThread thread = new HandlerThread("MyHandlerThread");
		thread.start();
		mHandler = new Handler(thread.getLooper());
		mHandler.post(mRunnableThread);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * com.baidu.location.BDLocationListener#onReceiveLocation(com.baidu.location
	 * .BDLocation)
	 */
	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null)
			return;
		MyLocationData locData = new MyLocationData.Builder()
				.accuracy(location.getRadius())
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();
		Latitude = location.getLatitude();
		Longitude = location.getLongitude();
		/*if (isFirstLoc) {
			isFirstLoc = false;*/
			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			LatLng ptCenter = new LatLng(Latitude, Longitude);
			// 反Geo搜索
			mSearch.reverseGeoCode(new ReverseGeoCodeOption()
					.location(ptCenter));
//		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * com.baidu.location.BDLocationListener#onReceivePoi(com.baidu.location
	 * .BDLocation)
	 */
	@Override
	public void onReceivePoi(BDLocation arg0) {

	}

}
