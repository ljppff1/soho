/**
 * @Title SearchPlaceActivity.java
 * @Package com.superdata.soho.activity.workattendance
 * @author Administrator
 * @date 2014年8月14日 下午1:50:59
 * @version V1.0
 */
package com.superdata.soho.activity.workattendance;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import android.view.View.OnClickListener;
/**
 * 查找地理位置
 * @ClassName SearchPlaceActivity
 * @author Administrator
 * @date 2014年8月14日 下午1:50:59
 *
 */
public class SearchPlaceActivity extends BaseActivity implements
	OnGetGeoCoderResultListener,OnClickListener{

	GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	BaiduMap mBaiduMap = null;
	MapView mMapView = null;
	Button postion_bt_back,postion_bt_pc_records,postion_geocode;
	EditText editCity,editGeoCodeKey;
	/* (非 Javadoc)
	 * @see com.superdata.soho.common.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchpostion_activity);
		init();
	}
	
	public void init(){
		postion_bt_back=(Button) findViewById(R.id.postion_bt_back);
		postion_bt_pc_records=(Button) findViewById(R.id.postion_bt_pc_records);
		
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.postion_mapView);
		mBaiduMap = mMapView.getMap();
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		
		editCity = (EditText) findViewById(R.id.city);
		editGeoCodeKey = (EditText) findViewById(R.id.geocodekey);
		postion_geocode=(Button) findViewById(R.id.postion_geocode);
		postion_geocode.setOnClickListener(this);
		
		
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		mSearch.destroy();
		super.onDestroy();
	}
	
	/* (非 Javadoc)
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#onGetGeoCodeResult(com.baidu.mapapi.search.geocode.GeoCodeResult)
	 */
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(SearchPlaceActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
		Toast.makeText(SearchPlaceActivity.this, strInfo, Toast.LENGTH_LONG).show();
	}

	/* (非 Javadoc)
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#onGetReverseGeoCodeResult(com.baidu.mapapi.search.geocode.ReverseGeoCodeResult)
	 */
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(SearchPlaceActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		Toast.makeText(SearchPlaceActivity.this, result.getAddress(),
				Toast.LENGTH_LONG).show();
	}

	/* (非 Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.postion_geocode:
			// Geo搜索
			mSearch.geocode(new GeoCodeOption().city(
					editCity.getText().toString()).address(
					editGeoCodeKey.getText().toString()));
			break;

		default:
			break;
		}
		
	}
}
