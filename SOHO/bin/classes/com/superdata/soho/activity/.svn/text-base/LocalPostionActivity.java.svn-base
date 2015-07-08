/**
 * @Title LocalPostionActivity.java
 * @Package com.superdata.soho.activity
 * @author Administrator
 * @date 2014年7月7日 下午3:11:56
 * @version V1.0
 */
package com.superdata.soho.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
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
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.view.SDProgressDialog;

/**
 * 定位当前位置返回位置信息
 * @ClassName LocalPostionActivity
 * @author Administrator
 * @date 2014年7月7日 下午3:11:56
 *
 */
public class LocalPostionActivity extends BaseActivity implements OnClickListener,OnGetGeoCoderResultListener{
	private BaiduMap mBaiduMap;
	MapView mMapView;
	Button bt_back, localpostion_bt_pc_records;
	private LocationMode mCurrentMode;
	LocationClient mLocClient;
	GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	public MyLocationListenner myListener = new MyLocationListenner();
	Double Latitude, Longitude;
	boolean isFirstLoc = true;// 是否首次定位
	String sddr="读取中···";//地址
	SDProgressDialog SDialog;
	private final int SUCCESSCODE=321;
	String longitude;
	String latitude;
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
	
	/*
	 * (非 Javadoc)
	 * 
	 * @see com.superdata.soho.common.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localpostion_activity);
		init();
	}

	public void init() {
		SDialog=new SDProgressDialog(LocalPostionActivity.this);
		SDialog.show();
		mMapView = (MapView) findViewById(R.id.localpostionmapView);
		mBaiduMap = mMapView.getMap();
		bt_back = (Button) findViewById(R.id.bt_back);
		bt_back.setOnClickListener(this);
		localpostion_bt_pc_records = (Button) findViewById(R.id.localpostion_bt_pc_records);
		localpostion_bt_pc_records.setOnClickListener(this);
		mCurrentMode = LocationMode.NORMAL;
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
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
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
		mLocClient.unRegisterLocationListener(myListener);
		mMapView.onDestroy();
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
			overridePendingTransition(
					R.anim.slide_left_in, R.anim.slide_right_out);
			break;
		case R.id.localpostion_bt_pc_records://提交给定位页面

			Intent intent=new Intent();  
			intent.putExtra("sddr", sddr);
			intent.putExtra("longitude", longitude);
			intent.putExtra("latitude", latitude);
			setResult(SUCCESSCODE, intent);
			finish();
			
			break;
		
		default:
			break;
		}

	}

	/* (非 Javadoc)
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#onGetGeoCodeResult(com.baidu.mapapi.search.geocode.GeoCodeResult)
	 */
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(LocalPostionActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
		}
		mBaiduMap.clear();
		/*mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));*/
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
		Toast.makeText(LocalPostionActivity.this, strInfo, Toast.LENGTH_LONG).show();
		
	}

	/* (非 Javadoc)
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#onGetReverseGeoCodeResult(com.baidu.mapapi.search.geocode.ReverseGeoCodeResult)
	 */
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(LocalPostionActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		sddr=result.getAddress();
		longitude = String.valueOf(result.getLocation().longitude);
		latitude  = String.valueOf(result.getLocation().latitude);
		Toast.makeText(LocalPostionActivity.this, result.getAddress(),
				Toast.LENGTH_LONG).show();
		if (SDialog.isShow()) {
			SDialog.cancel();
		}
	}
}
