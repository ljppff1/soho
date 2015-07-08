/**  
 * @Title: ResignRunchCardActivity.java
 * @Package com.superdata.soho.activity
 * @Description: TODO(用一句话描述该文件做什么)
 * @author A18ccms A18ccms_gmail_com  
 * @date 2014年6月13日 下午5:15:15
 * @version V1.0  
 */
package com.superdata.soho.activity.workattendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.google.gson.Gson;
import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.common.GridRadio;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.User;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
import android.app.AlertDialog;
import android.os.Handler;
import android.util.Log;
import android.view.View.OnClickListener;
/**
 * @ClassName: ResignRunchCardActivity
 * @Description: 位置补录
 * @author Administrator
 * @date 2014年6月13日 下午5:15:15
 * 
 */
public class ResignRunchCardActivity extends BaseActivity implements
		OnClickListener,OnGetGeoCoderResultListener {

	Button resign_bt_back, resign_bt_pc_records, choosenpostion_regist,
			attendance_time;

	TextView people_resign_text, people_resign_code, retroactivepeople;
	RelativeLayout people_resign_id;
	List<Map<String, String>> listMapPeople = new ArrayList<Map<String, String>>();

	User user;
	List<User> userList;
	String[] listStr;
	View myView;
	String userId, username;
	String lat, lng;
	List<NameValuePair> params;

	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resign_runchcard_activity);
		init();
	}

	/* (非 Javadoc)
	 * @see com.superdata.soho.common.BaseActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		mLocClient.unRegisterLocationListener(myListener);
		mMapView.onDestroy();
		super.onDestroy();
	}
	public void init() {
		resign_bt_back = (Button) findViewById(R.id.resign_bt_back);
		resign_bt_pc_records = (Button) findViewById(R.id.resign_bt_pc_records);
		choosenpostion_regist = (Button) findViewById(R.id.choosenpostion_regist);
		attendance_time = (Button) findViewById(R.id.attendance_time);
		resign_bt_back.setOnClickListener(this);
		attendance_time.setOnClickListener(this);
		resign_bt_pc_records.setOnClickListener(this);
		choosenpostion_regist.setOnClickListener(this);

		people_resign_id = (RelativeLayout) findViewById(R.id.people_resign_id);
		people_resign_id.setOnTouchListener(touch);
		people_resign_text = (TextView) findViewById(R.id.people_resign_text);
		people_resign_code = (TextView) findViewById(R.id.people_resign_code);
		retroactivepeople = (TextView) findViewById(R.id.retroactivepeople);
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(ResignRunchCardActivity.this);
		userId = mapconfig.get(InterfaceConfig.ID);
		username = mapconfig.get(InterfaceConfig.LOGIN_USER_NAME);
		retroactivepeople.setText(username);
		getJsonList(companyIdJson);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DAY_OF_MONTH);
		int h = cal.get(Calendar.HOUR_OF_DAY);
		int mi = cal.get(Calendar.MINUTE);
		attendance_time.setText(String.format("%d-%d-%d %02d:%02d", y, m, d, h,
				mi));
		
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

	Message msg;
	Runnable commitRun = new Runnable() {

		@Override
		public void run() {
			String url = InterfaceConfig.ASKFORLEAVE_ADD_URL;
			SDHttpClient sdClient = new SDHttpClient();
			getParamList();
			msg = new Message();
			try {

				String json = sdClient.post_session(url, params);
				Log.d("json++", json);

				JSONObject jRoot = new JSONObject(json);
				String ResultCode = jRoot.getString("resultCode");
				if (ResultCode.equals("200")) {
					msg.what = 1;
					msg.obj = json;
					handler.sendMessage(msg);
				} else {
					msg.what = 4;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				msg.what = 500;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	};

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Intent intent ;
			switch (msg.what) {
			case 1:
				intent = new Intent();
				intent.setClass(ResignRunchCardActivity.this,
						DayPunchCardRecordActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				break;
			case 4:
				Toast.makeText(ResignRunchCardActivity.this, "查询失败！", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		};
	};

	public List<NameValuePair> getParamList() {
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", people_resign_code.getText()
				.toString()));
		params.add(new BasicNameValuePair("trackDate", attendance_time
				.getText().toString()));
		params.add(new BasicNameValuePair("latitude", lat));
		params.add(new BasicNameValuePair("longitude", lng));
		params.add(new BasicNameValuePair("address", choosenpostion_regist
				.getText().toString()));
		params.add(new BasicNameValuePair("regId", userId));// 当前登录ID
		return params;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.resign_bt_back:
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
			break;
		case R.id.resign_bt_pc_records:// 提交
			if (chechIsNull(choosenpostion_regist)) {
				Toast.makeText(getApplicationContext(), "选择地址...",
						Toast.LENGTH_LONG).show();
				return;
			}
			
			Intent intent = new Intent();
			intent.setClass(ResignRunchCardActivity.this,
					DayPunchCardRecordActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.push_left_in,
					R.anim.push_left_out);
//			new Thread(commitRun).start();
			break;
		case R.id.choosenpostion_regist:// 地址
			Intent intentposition = new Intent(ResignRunchCardActivity.this,
					AddPositionActivity.class);
			startActivityForResult(intentposition, 1);
			break;
		case R.id.attendance_time:// 选择结束时间
			setTimeText(attendance_time);
			break;
		default:
			break;
		}
	}

	public boolean chechIsNull(Button btnText) {
		boolean flag = false;
		String str = btnText.getText().toString().trim();
		if (str == null || str.equals("")) {
			flag = true;
		}
		return flag;
	}

	public void setTimeText(final TextView t1) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		myView = View.inflate(this, R.layout.date_time_dialog, null);
		final DatePicker datePicker = (DatePicker) myView
				.findViewById(R.id.date_picker);
		final TimePicker timePicker = (android.widget.TimePicker) myView
				.findViewById(R.id.time_picker);
		builder.setView(myView);
		String str1 = "";
		str1 = t1.getText().toString();
		String[] ymd1 = str1.substring(0, str1.indexOf(" ")).split("-");
		String[] hm1 = str1.substring(str1.indexOf(" ")).trim().split(":");
		int y1 = Integer.parseInt(ymd1[0]);
		int m1 = Integer.parseInt(ymd1[1]);
		int d1 = Integer.parseInt(ymd1[2]);
		int h1 = Integer.parseInt(hm1[0]);
		int mi1 = Integer.parseInt(hm1[1]);

		datePicker.init(y1, (m1 - 1), d1, null);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(h1);
		timePicker.setCurrentMinute(mi1);
		builder.setTitle("选取起始时间");

		builder.setPositiveButton("确  定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						StringBuffer sb = new StringBuffer();

						sb.append(String.format("%d-%d-%d %02d:%02d",

						datePicker.getYear(),

						datePicker.getMonth() + 1,

						datePicker.getDayOfMonth(),
								timePicker.getCurrentHour(),
								timePicker.getCurrentMinute()));
						t1.setText(sb.toString());
						dialog.cancel();

					}

				});

		Dialog dialog = builder.create();
		InputMethodManager imm = (InputMethodManager) getApplicationContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		dialog.show();
	}

	public void getJsonList(String json) {
		userList = new ArrayList<User>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			int len = jsonArray.length();
			listStr = new String[len];
			for (int i = 0; i < len; i++) {
				JSONObject jRoot = (JSONObject) jsonArray.get(i);
				user = new User();
				user.setName(jRoot.getString("name"));
				user.setId(jRoot.getInt("id"));
				user.setPositionID(jRoot.getInt("positionId"));
				user.setPositionName(jRoot.getString("positionName"));
				user.setDepartmentID(jRoot.getInt("deptId"));
				user.setDepartmentName(jRoot.getString("deptName"));
				userList.add(user);
				if (user.getId() == Integer.parseInt(userId)) {
					people_resign_text.setText(username);//员工
					people_resign_code.setText(userId);
				}
				listStr[i] = jRoot.getString("name");
				Map<String, String> m = new HashMap<String, String>();
				m.put("text1", user.getId() + "");
				m.put("text2", jRoot.getString("name"));
				listMapPeople.add(m);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private OnTouchListener touch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			switch (v.getId()) {
			case R.id.people_resign_id:
				switch (action) {
				case MotionEvent.ACTION_UP:
					people_resign_id
							.setBackgroundResource(android.R.drawable.editbox_background);
					Gson gson = new Gson();
					Intent intent = new Intent(ResignRunchCardActivity.this,
							GridRadio.class);
					intent.putExtra("data", gson.toJson(listMapPeople));
					intent.putExtra("resultCode", 1);
					String checkCode = people_resign_code.getText().toString()
							.trim().equals("") ? null : people_resign_code
							.getText().toString();
					intent.putExtra("checkCode", checkCode);
					intent.putExtra("titleBar", 1);
					startActivityForResult(intent, 1);
					break;
				case MotionEvent.ACTION_DOWN:
					people_resign_id.setBackgroundColor(Color.YELLOW);
					break;
				}
				break;
			}
			return true;
		}
	};

	/*
	 * (非 Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		String code = data.getStringExtra("text1");
		String name = data.getStringExtra("text2");
		switch (resultCode) {
		case 1:// 员工
			int c = Integer.parseInt(code);
			if (c != -1) {
				people_resign_code.setText(code);
				people_resign_text.setText(name);
			}
			break;

		case 2:// 地图
			int cc = Integer.parseInt(code);
			if (cc != -1) {
				lat = data.getStringExtra("lat");
				lng = data.getStringExtra("lng");
				choosenpostion_regist.setText(data.getStringExtra("addr"));
			}
			break;
		default:
			break;
		}
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
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(ResignRunchCardActivity.this, "抱歉，未能找到结果",
					Toast.LENGTH_LONG).show();
		}

		if(null != mBaiduMap){
			mBaiduMap.clear();
		}
		longitude = String.valueOf(result.getLocation().longitude);
		latitude  = String.valueOf(result.getLocation().latitude);
		choosenpostion_regist.setText(result.getAddress());
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
}
