package com.superdata.soho.activity.workattendance;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfigeration;
import com.baidu.mapapi.map.MyLocationConfigeration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.superdata.soho.R;

/**
 * 演示poi搜索功能
 */
public class AddPositionActivity extends FragmentActivity implements
		OnGetPoiSearchResultListener, OnGetSuggestionResultListener,
		OnGetGeoCoderResultListener, OnClickListener {

	private PoiSearch mPoiSearch = null;
	private SuggestionSearch mSuggestionSearch = null;
	private BaiduMap mBaiduMap = null;
	private int load_Index = 0;

	LocationMode mCurrentMode;
	LocationClient mLocClient;
	Double Latitude, Longitude;
	boolean isFirstLoc = true;// 是否首次定位
	public MyLocationListenner myListener = new MyLocationListenner();
	GeoCoder mSearchCenter = null; // 搜索模块，也可去掉地图模块独立使用
	String curCity = "长沙市";// 默认长沙
	ImageButton queryBtn, delBtn;
	View plant;
	EditText queryEdit;
	InfoWindow infoWindow;
	PopupWindow mPopupWindow;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_position_activity);
		// 初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
		mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
				.findFragmentById(R.id.map))).getBaiduMap();

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
		mSearchCenter.setOnGetGeoCodeResultListener(this);

		queryBtn = (ImageButton) findViewById(R.id.queryBtn);
		delBtn = (ImageButton) findViewById(R.id.delBtn);
		queryEdit = (EditText) findViewById(R.id.queryEdit);
		plant = findViewById(R.id.plant);
		queryBtn.setOnClickListener(this);
		delBtn.setOnClickListener(this);
		queryEdit.addTextChangedListener(watcher);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.queryBtn:
			searchButtonProcess();
			break;
		case R.id.delBtn:
			delBtn.setVisibility(View.GONE);
			queryEdit.setText(null);
			plant.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#
	 * onGetGeoCodeResult(com.baidu.mapapi.search.geocode.GeoCodeResult)
	 */
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		// TODO Auto-generated method stub

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
			Toast.makeText(AddPositionActivity.this, "抱歉，未能找到结果",
					Toast.LENGTH_LONG).show();
		}
		if (null != mBaiduMap) {
			mBaiduMap.clear();
		}
		curCity = result.getAddressDetail().city;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mLocClient.unRegisterLocationListener(myListener);
		mPoiSearch.destroy();
		mSuggestionSearch.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * 影响搜索按钮点击事件
	 * 
	 * @param v
	 */
	public void searchButtonProcess() {
		mPoiSearch.searchInCity((new PoiCitySearchOption()).city(curCity)
				.keyword(queryEdit.getText().toString()).pageNum(load_Index));
	}

	/**
	 * 下一组数据
	 * 
	 * @Title goToNextPage
	 * @param v
	 *            方法注释
	 */
	// public void goToNextPage(View v) {
	// load_Index++;
	// searchButtonProcess(null);
	// }

	@Override
	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(AddPositionActivity.this, strInfo, Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(AddPositionActivity.this, "抱歉，未找到结果",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(AddPositionActivity.this,
					result + ": " + "eeeeeeeee", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {

	}

	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
//			super.onPoiClick(index);
			final PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// Toast.makeText(
			// AddPositionActivity.this,
			// poi.location.longitude + ": " + poi.location.latitude
			// + " city:" + poi.city + " name:" + poi.name
			// + " addr:" + poi.address, Toast.LENGTH_LONG).show();
			
			Point p = mBaiduMap.getProjection().toScreenLocation(
					new LatLng(poi.location.latitude, poi.location.longitude));
			p.y -= 47;
			OnInfoWindowClickListener listener = null;
			listener = new OnInfoWindowClickListener() {
				@Override
				public void onInfoWindowClick() {
					mBaiduMap.hideInfoWindow();
					Intent data = new Intent();
					data.putExtra("text1","1");
					data.putExtra("lat", poi.location.latitude);
					data.putExtra("lng", poi.location.longitude);
					data.putExtra("addr", poi.address);
					setResult(2, data);
					finish();
				}
			};
			LatLng latLng  = mBaiduMap.getProjection().fromScreenLocation(p);
			View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_position_item_activity, null);
			TextView name = (TextView) view.findViewById(R.id.itemName);
			TextView addr = (TextView) view.findViewById(R.id.itemAddr);
			name.setText(poi.name);
			addr.setText(poi.address);
			view.setBackgroundResource(R.drawable.popup);
			infoWindow = new InfoWindow(view,latLng, listener);
			mBaiduMap.showInfoWindow(infoWindow);
			return false;
		}
	}
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			Latitude = location.getLatitude();
			Longitude = location.getLongitude();
			if(null != locData){
				mBaiduMap.setMyLocationData(locData);
			}
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

	/** 文本域改变事件处理 **/
	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			String content = s.toString().trim();
			if (content.length() > 0) {
				plant.setVisibility(View.VISIBLE);
				delBtn.setVisibility(View.VISIBLE);
			} else {
				plant.setVisibility(View.GONE);
				delBtn.setVisibility(View.GONE);
			}
		}
	};
	/**
	 * 返回键 控制
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.putExtra("text1", "-1");
			intent.putExtra("text2", " ");
			setResult(2, intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
