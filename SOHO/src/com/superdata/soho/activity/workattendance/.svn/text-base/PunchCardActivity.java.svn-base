package com.superdata.soho.activity.workattendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

import android.support.v4.app.FragmentActivity;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.superdata.soho.R;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ZoomControls;

/**
 * 
 * @ClassName: PunchCardActivity2
 * @Description: 打卡
 * @author luolei
 * @date 2014年6月10日 上午10:34:45
 * 
 */
public class PunchCardActivity extends FragmentActivity implements
		OnClickListener, OnGetGeoCoderResultListener {
	// Button bt_back, bt_pc_records;
	// bt_qiandao_am, bt_qiantui_am, bt_qiandao_pm,
	// bt_qiantui_pm;
	MapView mMapView;
	BaiduMap mBaiduMap;
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	boolean isFirstLoc = true;// 是否首次定位
	BitmapDescriptor mCurrentMarker;
	Double Latitude, Longitude;
	String sddr = "读取中···";// 地址
	GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	SDProgressDialog sdDialog;
	int checktypeId = 1;
//	private int checktypeId_bt_qiandao_am = 1, checktypeId_bt_qiantui_am = 2,
//			checktypeId_bt_qiandao_pm = 3, checktypeId_bt_qiantui_pm = 4;
	LoadDataThread loadDataThread;
	String id;
	Message msg;
	Intent intent;
	private Marker mMarkerA;
	LatLng llA;
	private InfoWindow mInfoWindow;

	ImageButton checkinbtn, checkoutbtn, trackmapList;
	SupportMapFragment map;
	PopupWindow popupWindow;
	View parentView;
	Button btnYes,btnNo,showResultBtn;
	 Dialog dialog,dialogShowResult ;
	View punchardItemsView,punchardShowResultItemsView;
	int cury,curm,curd,curh,curmi;
	TextView bmap_top,timeTitle,timetv,rTitle , rAddr , rAddrShow , rTime , rTimeShow ;
	
//	ToggleButton toggleButton;
	int putcardtype = 1;
	boolean isPutInOrOut = true;//默认是签到
	boolean twoOrFour = false;//默认4中打卡类型
	/*
	 * (非 Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = LayoutInflater.from(this).inflate(R.layout.punch_card,
				null);
		setContentView(R.layout.punch_card);
		init();
	}

	public void init() {
		sdDialog = new SDProgressDialog(PunchCardActivity.this);
		sdDialog.show();
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(PunchCardActivity.this);
		id = mapconfig.get(InterfaceConfig.ID);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
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
		checkinbtn = (ImageButton) findViewById(R.id.checkinbtn);
		checkoutbtn = (ImageButton) findViewById(R.id.checkoutbtn);
		trackmapList = (ImageButton) findViewById(R.id.trackmapList);
		trackmapList.setOnClickListener(this);
		bmap_top = (TextView) findViewById(R.id.bmap_top);
		checkinbtn.setBackgroundColor(Color.TRANSPARENT);
		checkoutbtn.setBackgroundColor(Color.TRANSPARENT);
		trackmapList.setBackgroundColor(Color.TRANSPARENT);
		checkinbtn.setOnTouchListener(touch);
		checkoutbtn.setOnTouchListener(touch);
		trackmapList.setOnTouchListener(touch);
		// 隐藏缩放控件
		visibilityZoom();

		bmap_top.setText(bmap_top.getText().toString() + " "
				+ mapconfig.get(InterfaceConfig.LOGIN_USER_NAME));
			
		punchardItemsView = LayoutInflater.from(PunchCardActivity.this)
				.inflate(R.layout.punchcard_items, null);
		punchardShowResultItemsView = LayoutInflater.from(PunchCardActivity.this)
				.inflate(R.layout.punchcard_items_show_result, null);
		
		//加载第一个弹出框
		initDialogBefore();
		//加载第二个弹出框
		initDialogAfter();
		 
	}

	/**
	 * @Title initDialogAfter 方法注释
	 */
	private void initDialogAfter() {
		dialogShowResult = getDialog(punchardShowResultItemsView);
		showResultBtn = (Button) punchardShowResultItemsView.findViewById(R.id.showResultBtn);
		showResultBtn.setOnTouchListener(touch);
		rTitle = (TextView) punchardShowResultItemsView.findViewById(R.id.rTitle);
		rAddr = (TextView) punchardShowResultItemsView.findViewById(R.id.rAddr);
		rAddrShow = (TextView) punchardShowResultItemsView.findViewById(R.id.rAddrShow);
		rTime = (TextView) punchardShowResultItemsView.findViewById(R.id.rTime);
		rTimeShow = (TextView) punchardShowResultItemsView.findViewById(R.id.rTimeShow);
	}

	/**
	 * @Title initDialogBefore 方法注释
	 */
	private void initDialogBefore() {
		dialog = getDialog(punchardItemsView);
		btnYes = (Button) punchardItemsView.findViewById(R.id.putInCardYes);
		btnNo = (Button) punchardItemsView.findViewById(R.id.putInCardNo);
		btnYes.setOnTouchListener(touch);
		btnNo.setOnTouchListener(touch);
		timeTitle = (TextView) punchardItemsView.findViewById(R.id.timeTitle);
		timetv = (TextView) punchardItemsView.findViewById(R.id.timetv);
	}

	/**
	 * @Title getDialog 方法注释
	 */
	private Dialog getDialog(View view) {
		Dialog dialog = new Dialog(PunchCardActivity.this,R.style.dialog);
		dialog.setCancelable(false);
		dialog.setContentView(view);
		return dialog;
	}
	/**
	 * 
	 * @Title setTimeCardType
	 * @param flag	默认选择上午还是下午按钮
	 * @param isAmPm 区分签到还是签退
	 */
	Button cardTypeBtn;
	TextView cardTypetv;
	private void setTimeCardType(boolean flag) {
		TextView timeshow = (TextView) punchardItemsView.findViewById(R.id.timeshow);
		cardTypeBtn = (Button) punchardItemsView.findViewById(R.id.cardTypeBtn);
		cardTypetv = (TextView) punchardItemsView.findViewById(R.id.cardTypetv);
		cardTypeBtn.setOnClickListener(this);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cury = cal.get(Calendar.YEAR);
		curm = cal.get(Calendar.MONTH) + 1;
		curd = cal.get(Calendar.DAY_OF_MONTH);
		curh = cal.get(Calendar.HOUR_OF_DAY);
		curmi =cal.get(Calendar.MINUTE);
		timeshow.setText(String.format("%02d-%02d-%02d %02d:%02d",cury ,curm, curd,curh,curmi));
	}

	/**
	 * @Title visibilityZoom 方法注释
	 */
	private void visibilityZoom() {
		// 隐藏缩放控件
		int childCount = mMapView.getChildCount();
		View zoom = null;
		for (int i = 0; i < childCount; i++) {
			View child = mMapView.getChildAt(i);
			if (child instanceof ZoomControls) {
				zoom = child;
				break;

			}
		}
		zoom.setVisibility(View.GONE);
	}

	private OnTouchListener touch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			switch (v.getId()) {
			case R.id.checkinbtn://签到
				switch (action) {
				case MotionEvent.ACTION_UP:
					 setTimeCardType(true);
					 isPutInOrOut = true;
					checkinbtn .setImageResource(R.drawable.mapcheckin_btn_gray_n);
					dialog.show();
					Window  window = dialog.getWindow();
					window.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
					window.setBackgroundDrawableResource(android.R.color.transparent);
					window.setLayout(getMetricsWidth()*9/10,getMetricsHeight()/3);
					timeTitle.setText(R.string.intimetitle);
					timetv.setText(R.string.intimetv);
					btnYes.setText(R.string.putin);
					break;
				case MotionEvent.ACTION_DOWN:
					checkinbtn.setImageResource(R.drawable.mapcheckin_btn_gray_d);
					break;
				}
				break;
			case R.id.checkoutbtn://签退
				switch (action) {
				case MotionEvent.ACTION_UP:
					 setTimeCardType(false);
					 isPutInOrOut=false;
					checkoutbtn.setImageResource(R.drawable.mapcheckout_btn_gray_n);
					dialog.show();
					Window  window = dialog.getWindow();
					window.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
					window.setBackgroundDrawableResource(android.R.color.transparent);
					window.setLayout(getMetricsWidth()*9/10,getMetricsHeight()/3);
					timeTitle.setText(R.string.outtimetitle);
					timetv.setText(R.string.outtimetv);
					btnYes.setText(R.string.putout);
					break;
				case MotionEvent.ACTION_DOWN:
					checkoutbtn
							.setImageResource(R.drawable.mapcheckout_btn_gray_d);
					break;
				}
				break;
			case R.id.trackmapList://当日考勤
				switch (action) {
				case MotionEvent.ACTION_UP:
					trackmapList.setImageResource(R.drawable.trackmap_list);
					break;
				case MotionEvent.ACTION_DOWN:
					trackmapList
							.setImageResource(R.drawable.trackmap_list_click);
					break;
				}
				break;
			case R.id.putInCardYes://签到-->签到
				switch (action) {
				case MotionEvent.ACTION_UP:
					btnYes.setBackgroundResource(R.drawable.button_on);
					sdDialog.show();
					String time = "上午";
					if(isPutInOrOut){//签到
						if(twoOrFour){
							putcardtype = 1;   //上午上班打卡
						}else{
							if(cardTypetv.getText().equals("上午")){
								putcardtype = 1; //上午上班打卡
								time = "上午";
							}else{
								time = "下午";
								putcardtype = 3;//下午上班打卡
							}
						}
					}else{//签退
						if(twoOrFour){
							putcardtype = 2;//下午下班打卡
						}else{
							if(cardTypetv.getText().equals("下午")){
								putcardtype = 4;//下午下班打卡
								time = "下午";
							}else{
								time = "上午";
								putcardtype = 2;//上午下班打卡
							}
						}
					}
//					Toast.makeText(getApplicationContext(),(isPutInOrOut?time+"上班打卡"+putcardtype:time+"下班打卡"+putcardtype), Toast.LENGTH_LONG).show();
					//访问后台
					new LoadDataThread(putcardtype, sddr).start();
					break;
				case MotionEvent.ACTION_DOWN:
					btnYes
					.setBackgroundResource(R.drawable.button_down);
					break;
				}
				break;
			case R.id.putInCardNo://签到-->不签了
				switch (action) {
				case MotionEvent.ACTION_UP:
					btnNo.setBackgroundResource(R.drawable.button_on);
					dialog.dismiss();
					break;
				case MotionEvent.ACTION_DOWN:
					btnNo
					.setBackgroundResource(R.drawable.button_down);
					break;
				}
				break;
			case R.id.showResultBtn://签到后-->确定
				switch (action) {
				case MotionEvent.ACTION_UP:
					showResultBtn.setBackgroundResource(R.drawable.button_on);
					dialogShowResult.dismiss();
					break;
				case MotionEvent.ACTION_DOWN:
					showResultBtn
					.setBackgroundResource(R.drawable.button_down);
					break;
				}
				break;
			}
			return true;
		}
	};

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
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();
	}

	/*
	 * (非 Javadoc) 得到经纬度
	 * 
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#
	 * onGetGeoCodeResult(com.baidu.mapapi.search.geocode.GeoCodeResult)
	 */
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(PunchCardActivity.this, "抱歉，未能找到结果",
					Toast.LENGTH_LONG).show();
		}
		mBaiduMap.clear();
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
		Toast.makeText(PunchCardActivity.this, strInfo, Toast.LENGTH_LONG)
				.show();

		if (sdDialog.isShow()) {
			sdDialog.cancel();
		}
	}

	/*
	 * (非 Javadoc) 得到地名
	 * 
	 * @see com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener#
	 * onGetReverseGeoCodeResult
	 * (com.baidu.mapapi.search.geocode.ReverseGeoCodeResult)
	 */
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(PunchCardActivity.this, "抱歉，未能找到结果",
					Toast.LENGTH_LONG).show();
		}

		mBaiduMap.clear();
		mMarkerA = (Marker) mBaiduMap.addOverlay(new MarkerOptions().position(
				result.getLocation()).icon(
				BitmapDescriptorFactory.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		sddr = result.getAddress();
		/*
		 * Toast.makeText(PunchCardActivity2.this, "当前位置：" +
		 * result.getAddress(), Toast.LENGTH_LONG).show();
		 */

		if (sdDialog.isShow()) {
			sdDialog.cancel();
		}
		llA = new LatLng(Latitude, Longitude);

		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker mark) {

				mBaiduMap.showInfoWindow(mInfoWindow);
				return true;
			}

		});
		Button button = new Button(getApplicationContext());

		button.setBackgroundResource(R.drawable.c_chakan_bg);
		button.setTextColor(Color.WHITE);
		button.setTextSize(13);
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
		button.setText("当前位置：\n " + sddr);
		mInfoWindow = new InfoWindow(button, llInfo, listener);
		mBaiduMap.showInfoWindow(mInfoWindow);

	}

	class LoadDataThread extends Thread {
		int checktypeId;
		String add;

		public LoadDataThread(int i, String address) {
			checktypeId = i;
			add = address;
		}

		/*
		 * (非 Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			String url = InterfaceConfig.PUNCH_CARD_URL;
			SDHttpClient sdClient = new SDHttpClient();
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("empId", id));
			params.add(new BasicNameValuePair("address", add));
			params.add(new BasicNameValuePair("typeId", String
					.valueOf(checktypeId)));
			params.add(new BasicNameValuePair("latitude", Double
					.toString(Latitude)));
			params.add(new BasicNameValuePair("longitude", Double
					.toString(Longitude)));
			try {
				String json = sdClient.post_session(url, params);
				msg = new Message();

				JSONObject jRoot = new JSONObject(json);
				String ResultCode = jRoot.getString("resultCode");
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				Log.i("tanshulin",ResultCode);
				if (ResultCode.equals("200")) {
					msg.what = 1;
					handler.sendMessage(msg);
				} else {
					msg.what = 2;
					handler.sendMessage(msg);
				}

				/*
				 * if (json.equals("true")) { msg.what = 1;
				 * handler.sendMessage(msg); } else { msg.what = 2;
				 * handler.sendMessage(msg); }
				 */
			} catch (Exception e) {
				sdDialog.cancel();
				msg.what = 2;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if(isPutInOrOut){
					rTitle.setText(R.string.inrTitleSuccess);
				}else{
					rTitle.setText(R.string.outrTitleSuccess);
				}
				setPutCardResult();
				break;
			case 2:
				if(isPutInOrOut){
					rTitle.setText(R.string.inrTitleFail);
				}else{
					rTitle.setText(R.string.outrTitleFail);
				}
				setPutCardResult();
				break;
			case 500:
				break;
			}

			if (sdDialog.isShow()) {
				sdDialog.cancel();
			}
		}

		/**
		 * @Title setPutCardResult 方法注释
		 */
		private void setPutCardResult() {
			if(isPutInOrOut){
				rAddr.setText(R.string.inrAddr);
				rTime.setText(R.string.inrTime);
			}else{
				rAddr.setText(R.string.outrAddr);
				rTime.setText(R.string.outrTime);
			}
			rAddrShow.setText(sddr);
			setHMISS(rTimeShow);
			dialog.dismiss();
			dialogShowResult.show();
		}

		/**
		 * @Title setHMISS 方法注释
		 * 设置时 分 秒
		 */
		private void setHMISS(TextView textView) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			int h = cal.get(Calendar.HOUR_OF_DAY);
			int mi =cal.get(Calendar.MINUTE);
			int ss = cal.get(Calendar.SECOND);
			textView.setText(String.format("%02d:%02d:%02d",h,mi,ss));
		};
	};

	public int getMetricsWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public int getMetricsHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/* (非 Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cardTypeBtn:
				if(cardTypetv.getText().toString().equals("上午")){
					cardTypeBtn.setBackgroundResource(R.drawable.pm);
					cardTypetv.setText("下午");
				}else{
					cardTypeBtn.setBackgroundResource(R.drawable.am);
					cardTypetv.setText("上午");
				}
			
			break;

		default:
			break;
		}
	}
}
