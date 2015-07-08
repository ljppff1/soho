/**
 * @Title TakePhotoMainActivity.java
 * @Package com.superdata.soho.activity
 * @author Administrator
 * @date 2014年7月7日 上午10:50:19
 * @version V1.0
 */
package com.superdata.soho.activity.workattendance;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfigeration;
import com.baidu.mapapi.map.MyLocationConfigeration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.superdata.soho.R;
import com.superdata.soho.activity.LocalPostionActivity;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.PicInfo;

/**
 * 拍照上传
 * 
 * @ClassName TakePhotoMainActivity
 * @author Administrator
 * @date 2014年7月7日 上午10:50:19
 * 
 */
public class TakePhotoMainActivity extends BaseActivity implements
		OnClickListener,OnGetGeoCoderResultListener {

	Button takephoto_bt_back, takephoto_bt_upload_position,
			takephoto_bt_online;
	private int requestCode = 123;
	Intent intent;
	EditText people_takephoto_id, department_takephoto_id,
			position_takephoto_id,  client_name,
			takephoto_description;
	View myView;
	PicInfo picInfo;
	
	Button start_time_takephoto_id,takephoto_bt_choise;

	
	
	
	MapView mMapView;
	BaiduMap mBaiduMap;
	LocationMode mCurrentMode;
	LocationClient mLocClient;
	MyLocationListenner myListener = new MyLocationListenner();
	Double Latitude, Longitude;
	boolean isFirstLoc = true;// 是否首次定位
	GeoCoder mSearch = null;
	String longitude;
	String latitude;
	
	/*
	 * (非 Javadoc)
	 * 
	 * @see com.superdata.soho.common.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.takephoto_main_activity);
		init();
	}

	public void init() {
		people_takephoto_id = (EditText) findViewById(R.id.people_takephoto_id);
		department_takephoto_id = (EditText) findViewById(R.id.department_takephoto_id);
		position_takephoto_id = (EditText) findViewById(R.id.position_takephoto_id);
		start_time_takephoto_id = (Button) findViewById(R.id.start_time_takephoto_id);
		client_name = (EditText) findViewById(R.id.client_name);
		takephoto_description = (EditText) findViewById(R.id.takephoto_description);
		picInfo=new PicInfo();
		start_time_takephoto_id.setOnClickListener(this);
		takephoto_bt_back = (Button) findViewById(R.id.takephoto_bt_back);
		takephoto_bt_back.setOnClickListener(this);
		takephoto_bt_upload_position = (Button) findViewById(R.id.takephoto_bt_upload_position);
		takephoto_bt_upload_position.setOnClickListener(this);
		takephoto_bt_online = (Button) findViewById(R.id.takephoto_bt_online);
		takephoto_bt_online.setOnClickListener(this);
		takephoto_bt_choise = (Button) findViewById(R.id.takephoto_bt_choise);
		takephoto_bt_choise.setOnClickListener(this);
		
		Intent intent = getIntent();
		//设置默认值
		people_takephoto_id.setText(intent.getStringExtra("userName"));
		department_takephoto_id.setText(intent.getStringExtra("depName"));
		position_takephoto_id.setText(intent.getStringExtra("positionName"));
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DAY_OF_MONTH);

		start_time_takephoto_id.setText(String.format("%d-%d-%d %02d:%02d",y ,m, d,InterfaceConfig.STARTHOUR , InterfaceConfig.STARTMIN));
		 
		
		
		mMapView = (MapView) findViewById(R.id.getmapView);
		mBaiduMap = mMapView.getMap();
		mCurrentMode = LocationMode.NORMAL;
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfigeration(
				mCurrentMode, true, null));
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);// 定位时间间隔
		mLocClient.setLocOption(option);
		mLocClient.start();
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
	}

	 
	/*
	 * (非 Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (321 == resultCode) {
			takephoto_bt_upload_position.setText(data.getExtras().getString("sddr"));
			longitude = data.getExtras().getString("longitude");
			latitude = data.getExtras().getString("latitude");
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.takephoto_bt_back:
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
			break;
		case R.id.takephoto_bt_upload_position:
			intent = new Intent(TakePhotoMainActivity.this,
					LocalPostionActivity.class);
			startActivityForResult(intent, requestCode);
			break;
		case R.id.takephoto_bt_online:
			setDataToBean();
			intent = new Intent();
			intent.setClass(TakePhotoMainActivity.this, XcqzTakeImage.class);
			intent.putExtra("picInfo", picInfo);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.start_time_takephoto_id:
			setTimeText(R.id.start_time_takephoto_id);
			break;
		case R.id.takephoto_bt_choise:
			Intent showPicIntent = new Intent(TakePhotoMainActivity.this,XcqzLocalImage.class);
			setDataToBean();
			showPicIntent.putExtra("picInfo", picInfo);
			startActivity(showPicIntent);
			break;
		default:
			break;
		}

	}

	public void setDataToBean(){
		picInfo.setUserName(people_takephoto_id.getText().toString().trim());
		picInfo.setTime(start_time_takephoto_id.getText().toString().trim());
		picInfo.setCustomer(client_name.getText().toString().trim());
		picInfo.setInfo(takephoto_description.getText().toString().trim());
		picInfo.setPostion(takephoto_bt_upload_position.getText().toString().trim());
		picInfo.setLatitude(latitude);
		picInfo.setLongitude(longitude);
		
	}
	public void setTimeText(final int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		myView = View.inflate(this, R.layout.date_time_dialog, null);
		final DatePicker datePicker = (DatePicker) myView
				.findViewById(R.id.date_picker);
		final TimePicker timePicker = (android.widget.TimePicker) myView
				.findViewById(R.id.time_picker);
		builder.setView(myView);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), null);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(Calendar.MINUTE);
		builder.setTitle("选取起始时间");

		builder.setPositiveButton("确  定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						StringBuffer sb = new StringBuffer();

						sb.append(String.format("%d-%02d-%02d",

						datePicker.getYear(),

						datePicker.getMonth() + 1,

						datePicker.getDayOfMonth()));

						sb.append("  ");

						sb.append(timePicker.getCurrentHour()).append(":")
								.append(timePicker.getCurrentMinute());

						start_time_takephoto_id.setText(sb);

						dialog.cancel();

					}

				});

		Dialog dialog = builder.create();
		InputMethodManager imm = (InputMethodManager) getApplicationContext()
				.getSystemService(
						Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		dialog.show();
	}

	/* (非 Javadoc)
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#onGetGeoCodeResult(com.baidu.mapapi.search.geocode.GeoCodeResult)
	 */
	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (非 Javadoc)
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#onGetReverseGeoCodeResult(com.baidu.mapapi.search.geocode.ReverseGeoCodeResult)
	 */
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(TakePhotoMainActivity.this, "抱歉，未能找到结果",
					Toast.LENGTH_LONG).show();
		}

		if(null != mBaiduMap){
			mBaiduMap.clear();
		}
		longitude = String.valueOf(result.getLocation().longitude);
		latitude  = String.valueOf(result.getLocation().latitude);
		takephoto_bt_upload_position.setText(result.getAddress());
	}
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			Latitude = location.getLatitude();
			Longitude = location.getLongitude();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
				LatLng ptCenter = new LatLng(Latitude, Longitude);
				// 反Geo搜索
				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
						.location(ptCenter));
			}
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {

		}
	}
	/* (非 Javadoc)
	 * @see com.superdata.soho.common.BaseActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mLocClient.unRegisterLocationListener(myListener);
		mMapView.onDestroy();
	}
}
