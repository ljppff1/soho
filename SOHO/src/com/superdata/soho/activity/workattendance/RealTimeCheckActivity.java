/**
 * @Title StaffTrajectoryActivity.java
 * @Package com.superdata.soho.activity
 * @author Administrator
 * @date 2014年6月30日 下午2:38:36
 * @version V1.0
 */
package com.superdata.soho.activity.workattendance;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfigeration;
import com.baidu.mapapi.map.MyLocationConfigeration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOvelray;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.superdata.soho.R;
import com.superdata.soho.R.color;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.AddressInfo;
import com.superdata.soho.entity.User;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.NetCheckUtil;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;
import android.app.AlertDialog;
import android.os.Handler;
import android.util.Log;
import android.view.View.OnClickListener;
/**
 * 员工轨迹
 * @ClassName StaffTrajectoryActivity
 * @author luolei
 * @date 2014年6月30日 下午2:38:36
 * 
 */
public class RealTimeCheckActivity extends BaseActivity implements
		OnClickListener,OnMapClickListener,OnGetRoutePlanResultListener,OnGetGeoCoderResultListener {

	private BaiduMap mBaiduMap;
	View myView;
	MapView mMapView;
	Button bt_back, bt_search_staff, bt_takeup_pic_staff, bt_staff_search,
			bt_staff_cancel,bt_upload_postion,pick_up_endTime,pick_up_startTime;
	private InfoWindow mInfoWindow;
	private Marker mMarkerA;
	BitmapDescriptor bdA;
	public static PendingIntent pendingIntent;
	TextView title_staff;
	private String path = "SDApp";
	List<NameValuePair> params;
	Map<String, String> mapconfig;
	Message msg;
	String[] listStr;
	User user;
	SDProgressDialog sdDialog;
	List<User> userList;
	EditText search_staff_starttime,search_staff_endtime;
	int pos;
	List<AddressInfo> addressList;
	List<PlanNode> nodesList;
	RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	int nodeIndex = -2;// 节点索引,供浏览节点时使用
	OverlayManager routeOverlay = null;
	List<Marker> mMarkerList;
	private LocationMode mCurrentMode;
	LocationClient mLocClient;
	Double Latitude, Longitude;
	boolean isFirstLoc = true;// 是否首次定位
	public MyLocationListenner myListener = new MyLocationListenner();
	GeoCoder mSearchCenter = null; // 搜索模块，也可去掉地图模块独立使用
	String sddr = "读取中···";// 地址
	LatLng llA;
	
	LinearLayout searchLayout;
	String jsonPeople = null ;
	/*
	 * (非 Javadoc)
	 * 
	 * @see com.superdata.soho.common.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staff_traje_activity);
		init();
		
	}

	/**
	 * 开始加载
	 * @Title init 方法注释
	 */
	public void init() {
		searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
		sdDialog = new SDProgressDialog(RealTimeCheckActivity.this);
		searchLayout.setOnClickListener(this);
//		sdDialog.show();
		bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
		userList=new ArrayList<User>();
		addressList=new ArrayList<AddressInfo>();
		mapconfig = SharedPreferencesConfig.config(RealTimeCheckActivity.this);
		mCurrentMode = LocationMode.NORMAL;
		mMapView = (MapView) findViewById(R.id.staffmapView);
		mBaiduMap = mMapView.getMap();
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
				mSearchCenter = GeoCoder.newInstance();
//				mSearchCenter.setOnGetGeoCodeResultListener(this);
		mSearch = RoutePlanSearch.newInstance();
//		mSearch.setOnGetRoutePlanResultListener(this);
		
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
				mSearchCenter.reverseGeoCode(new ReverseGeoCodeOption()
						.location(ptCenter));
			}
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {

		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 switch (resultCode) {
		case 1:
			minCheckTime = data.getStringExtra("startTime");
			maxCheckTime = data.getStringExtra("endTime");
			pos = Integer.parseInt(data.getStringExtra("usernamecode").trim());
			loadThread loadThread=new loadThread();
			new Thread(loadThread).start();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();
		bdA.recycle();
	}

	
	/*
	 * (非 Javadoc)
	 * 得到地名
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#
	 * onGetReverseGeoCodeResult
	 * (com.baidu.mapapi.search.geocode.ReverseGeoCodeResult)
	 */
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(RealTimeCheckActivity.this, "抱歉，未能找到结果",
					Toast.LENGTH_LONG).show();
		}

		mBaiduMap.clear();
		mMarkerA = (Marker) mBaiduMap.addOverlay(new MarkerOptions().position(
				result.getLocation()).icon(
				BitmapDescriptorFactory.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		sddr = result.getAddress();

		if (sdDialog.isShow()) {
			sdDialog.cancel();
		}
		llA = new LatLng(Latitude, Longitude);
		OverlayOptions ooA = new MarkerOptions()
				.position(llA)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)).zIndex(9);
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				Button button = new Button(getApplicationContext());
				button.setTextColor(color.back);
				button.setBackgroundResource(R.drawable.popup);
				Point p = mBaiduMap.getProjection().toScreenLocation(llA);
				p.y -= 47;
				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
				OnInfoWindowClickListener listener = null;
				listener = new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick() {
						mBaiduMap.hideInfoWindow();
					}
				};
				button.setText("当前位置："+sddr);
				mInfoWindow = new InfoWindow(button, llInfo, listener);
				mBaiduMap.showInfoWindow(mInfoWindow);
				return true;
			}

		});

	}
	
	/*
	 * (非 Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_back:
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
			break;
		case R.id.searchLayout:
			getJsonList(companyIdJson);
			Intent intent = new Intent(RealTimeCheckActivity.this,StaffTrajectorySearchActivity.class);
			intent.putExtra("data", companyIdJson);
			startActivityForResult(intent, 1);
			break;
		}

	}

	/**
	 * 加载公司员工
	 * @ClassName loadDateThread
	 * @author Administrator
	 * @date 2014年8月19日 下午2:14:57
	 *
	 */
	class loadDateThread implements Runnable{

		/* (非 Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if (!NetCheckUtil
					.isNetworkAvailable(RealTimeCheckActivity.this)) {
				Toast.makeText(RealTimeCheckActivity.this,
						"网络有点不给力,请稍后重试!", Toast.LENGTH_SHORT).show();
				return;
			}
			
			String url = InterfaceConfig.SEARCH_COMPANY;
			SDHttpClient sdClient = new SDHttpClient();
			params = new ArrayList<NameValuePair>();
			String companyId=mapconfig.get(InterfaceConfig.COMPANYID);
			params.add(new BasicNameValuePair("companyID", companyId));
			try {
				if(jsonPeople == null){
					jsonPeople=sdClient.post_session(url, params);
				}
				
				Log.v("loadDateThread", jsonPeople);
				msg = new Message();
				msg.what = 5;
				msg.obj=jsonPeople;
				handler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}}
	/**
	 * 请求轨迹信息
	 * @ClassName loadThread
	 * @author Administrator
	 * @date 2014年8月19日 下午2:15:13
	 *
	 */
	String minCheckTime;
	String maxCheckTime;
	class loadThread implements Runnable{
		@Override
		public void run() {
		 
			String url = InterfaceConfig.LOAD_POSTION_URL;
			SDHttpClient sdClient = new SDHttpClient();
			msg = new Message();
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("minCheckTime", minCheckTime));
			params.add(new BasicNameValuePair("maxCheckTime", maxCheckTime));
			if(userList.size()!=0){
				params.add(new BasicNameValuePair("empId", userList.get(pos).getId().toString()));
			}
			try {
				String json=sdClient.post_session(url, params);
				Log.i("tanshulin",json);
				Log.i("tanshulin",json);
				Log.i("tanshulin",json);
				Log.i("tanshulin",json);
				Log.i("tanshulin",json);
				Log.i("tanshulin",json);
				Log.i("tanshulin",json);
				msg.what = 1;
				msg.obj=json;
				handler.sendMessage(msg);
			} catch (Exception e) {
				msg.what = 6;
				handler.sendMessage(msg);
				e.printStackTrace();
			} 
			
		}
		
	}
	
	private Handler handler = new Handler() {
		 
		@Override
		public void handleMessage(Message msg) {
			if(sdDialog.isShow()){
				sdDialog.cancel();;
			}
			switch (msg.what) {
			case 1:
				String mjson=(String) msg.obj;
				parseJsonList(mjson);
				showPostionInMap(addressList);
				
				break;
			case 5:
				String json=(String) msg.obj;
				getJsonList(json);
				Intent intent = new Intent(RealTimeCheckActivity.this,StaffTrajectorySearchActivity.class);
				intent.putExtra("data", json);
				intent.putExtra("title", "实时查岗");
				startActivityForResult(intent, 1);
				break;
			case 6:
				Toast.makeText(RealTimeCheckActivity.this, "网络异常", 1).show();
				break;
			case 7:
				Toast.makeText(RealTimeCheckActivity.this, "请选择起始时间!", 1).show();
				break;
			}
		}
	};
	
	OnMarkerClickListener onMarkerClickListener=new OnMarkerClickListener() {
		@Override
		public boolean onMarkerClick(final Marker marker) {
			Button button = new Button(getApplicationContext());
			button.setBackgroundResource(R.drawable.popup);
			final LatLng ll = marker.getPosition();
			Point p = mBaiduMap.getProjection().toScreenLocation(ll);
			p.y -= 47;
			LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
			OnInfoWindowClickListener listener = null;

			button.setText("当前位置："+marker.getTitle()+"，点击查看图片");
			
			listener = new OnInfoWindowClickListener() {
				@Override
				public void onInfoWindowClick() {
					try {
						File f = new File(Environment.getExternalStorageDirectory(), path);
						Log.d("dd",
								Environment.getExternalStorageDirectory()
										+ "/mm.jpg");
						if (!f.exists()) {
							f.mkdirs();
						}

						File file = new File(f.getAbsolutePath()
								+ "/mm.jpg");
						if (!file.exists()) {
							file.createNewFile();
						}
						Intent intent = new Intent();
						intent.putExtra("path", file.getAbsolutePath());
						intent.setClass(RealTimeCheckActivity.this,
								ReadImgActivity.class);
						startActivityForResult(intent, 1);
					} catch (Exception e) {
						Log.d("dd", "异常");
						e.printStackTrace();
					}
				}
				
				
				
			};

			mInfoWindow = new InfoWindow(button, llInfo, listener);
			mBaiduMap.showInfoWindow(mInfoWindow);
			return true;
		}
	};
	/**
	 * 在地图上显示轨迹列表s
	 * @Title showPostionInMap
	 * @param addList 方法注释
	 */
	public void showPostionInMap(List<AddressInfo> addList){
		if(addList.size()==0){
			Toast.makeText(RealTimeCheckActivity.this, "记录为空!", Toast.LENGTH_SHORT)
			.show();
			return;
		}
		LatLng startLat = new LatLng(addList.get(0).getLatitude(), addList.get(0).getLongitude());//开始节点
		PlanNode stNode = PlanNode.withLocation(startLat);
		int size=addList.size()-1;
		LatLng endLat = new LatLng(addList.get(size).getLatitude(), addList.get(size).getLongitude());
		PlanNode enNode = PlanNode.withLocation(endLat);
		LatLng middleLatLng;
		PlanNode middleNode;
		nodesList = new ArrayList<PlanNode>();// 初始化途径节点集合
		for (int i = 0; i < addList.size(); i++) {
			middleLatLng=new LatLng(addList.get(i).getLatitude(), addList.get(i).getLongitude());
			if(i!=0&&i!=size){
				middleNode=PlanNode.withLocation(middleLatLng);
				nodesList.add(middleNode);
			}

			OverlayOptions ooA = new MarkerOptions().position(middleLatLng).icon(bdA)
					.zIndex(9);
			mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
			mMarkerA.setTitle(addList.get(i).getAddress());
			
		}
		Log.v("nodesList", nodesList.size()+"");
		mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode)
				.passBy(nodesList).to(enNode));
		if(sdDialog.isShow()){
			sdDialog.cancel();;
		}
	}
	/**
	 * 解析轨迹列表json
	 * @Title parseJsonList
	 * @param json 方法注释
	 */
	public void parseJsonList(String json){
		JSONArray myArray;
		try {
			myArray = new JSONArray(json);
			int len=myArray.length();
			for (int i = 0; i < len; i++) {
				JSONObject jRoot=(JSONObject) myArray.get(i);
				AddressInfo address=new AddressInfo();
				address.setAddress(jRoot.getString("address"));
				address.setLatitude(jRoot.getDouble("latitude"));
				address.setLongitude(jRoot.getDouble("longitude"));
				address.setTrackDate(jRoot.getString("trackDate"));
				addressList.add(address);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getJsonList(String json){
		try {
			//JSONObject jRoot = new JSONObject(json);
			JSONArray jsonArray=new JSONArray(json);
			int len=jsonArray.length();
			listStr=new String[len];
			for (int i = 0; i < len; i++) {
				JSONObject jRoot=(JSONObject) jsonArray.get(i);
				user=new User();
				user.setName(jRoot.getString("name"));
				user.setId(jRoot.getInt("id"));
				user.setPositionID(jRoot.getInt("positionId"));
				user.setPositionName(jRoot.getString("positionName"));
				user.setDepartmentID(jRoot.getInt("deptId"));
				user.setDepartmentName(jRoot.getString("deptName"));
				userList.add(user);
				//listPeopleLeave.add(jRoot.getString("name"));
				listStr[i]=jRoot.getString("name");
			}
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		if(sdDialog.isShow()){
			sdDialog.cancel();
		}
		
	}
	
	/* (非 Javadoc)
	 * 点击地图触发事件
	 * @see com.baidu.mapapi.map.BaiduMap.OnMapClickListener#onMapClick(com.baidu.mapapi.model.LatLng)
	 */
	@Override
	public void onMapClick(LatLng arg0) {
		mBaiduMap.hideInfoWindow();
		
	}

	/* (非 Javadoc)
	 * @see com.baidu.mapapi.map.BaiduMap.OnMapClickListener#onMapPoiClick(com.baidu.mapapi.map.MapPoi)
	 */
	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		return false;
	}
	/**
	 * 时间选择
	 * @Title setTimeText
	 * @param id 方法注释
	 */
	public void setTimeText(final int id){
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

						if(id==R.id.pick_up_startTime){
							
							search_staff_starttime.setText(sb);
						}else {
							search_staff_endtime.setText(sb);
						}

						dialog.cancel();

					}

				});

		Dialog dialog = builder.create();
		InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		dialog.show();
	}

	// 定制RouteOverly
		private class MyDrivingRouteOverlay extends DrivingRouteOvelray {

			public MyDrivingRouteOverlay(BaiduMap baiduMap) {
				super(baiduMap);
			}

			@Override
			public BitmapDescriptor getStartMarker() {
					return BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
			}

			@Override
			public BitmapDescriptor getTerminalMarker() {
					return BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
			}
			
		}
		
	/* (非 Javadoc)
	 * 行车路线
	 * @see com.baidu.mapapi.search.route.OnGetRoutePlanResultListener#onGetDrivingRouteResult(com.baidu.mapapi.search.route.DrivingRouteResult)
	 */
	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(RealTimeCheckActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			DrivingRouteOvelray overlay = new MyDrivingRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMapClickListener(this);
			mBaiduMap.setOnMarkerClickListener(onMarkerClickListener);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
		
	}

	/* (非 Javadoc)
	 * @see com.baidu.mapapi.search.route.OnGetRoutePlanResultListener#onGetTransitRouteResult(com.baidu.mapapi.search.route.TransitRouteResult)
	 */
	@Override
	public void onGetTransitRouteResult(TransitRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (非 Javadoc)
	 * @see com.baidu.mapapi.search.route.OnGetRoutePlanResultListener#onGetWalkingRouteResult(com.baidu.mapapi.search.route.WalkingRouteResult)
	 */
	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (非 Javadoc)
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#onGetGeoCodeResult(com.baidu.mapapi.search.geocode.GeoCodeResult)
	 */
	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
