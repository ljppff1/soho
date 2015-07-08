/**
 * @Title MyPhoneAppActivity.java
 * @Package com.superdata.soho.activity.entrance
 * @author Administrator
 * @date 2014年7月25日 上午9:26:48
 * @version V1.0
 */
package com.superdata.soho.activity.entrance;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.superdata.soho.R;
import com.superdata.soho.adapter.AppAdapter;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.entity.AppInfo;
import com.superdata.soho.util.QueryAppUtil;
import com.superdata.soho.util.SaveAppUtil;
import com.superdata.soho.view.SDProgressDialog;

/**
 * 我的手机
 * @ClassName MyPhoneAppActivity
 * @author Administrator
 * @date 2014年7月25日 上午9:26:48
 * 
 */
public class MyPhoneAppActivity extends BaseActivity implements OnClickListener{

	public static final int SEARCH_APP = 1;//搜索类型
	GridView gridView;
	Button myphone_bt_back;
	SDProgressDialog dialog;
	SaveAppUtil appSave;
	QueryAppUtil appQuery;
	private List<AppInfo> apps;
	private List<AppInfo> temp;
	AppAdapter adapter;
	private static final String SCHEME = "package";

	/*
	 * (非 Javadoc)
	 * 
	 * @see com.superdata.soho.common.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_app);
		init();
	}

	public void init() {
		gridView = (GridView) findViewById(R.id.gridView);
		dialog = new SDProgressDialog(MyPhoneAppActivity.this);
		dialog.show();
		myphone_bt_back = (Button) findViewById(R.id.myphone_bt_back);
		myphone_bt_back.setOnClickListener(this);
		appSave = new SaveAppUtil(MyPhoneAppActivity.this);
		appQuery = new QueryAppUtil(MyPhoneAppActivity.this);
		new DataTask(0).start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SEARCH_APP:
				adapter = new AppAdapter(MyPhoneAppActivity.this, apps);
				gridView.setAdapter(adapter);
				gridView.setOnItemClickListener(new ItemClickListener());
			default:
				break;
			}
			if (dialog.isShow()) {
				dialog.cancel();
			}
		}

	};

	/**
	 * 点击事件
	 * @ClassName ItemClickListener
	 * @author Administrator
	 * @date 2014年7月26日 上午9:06:15
	 *
	 */
	private class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			AppInfo app = apps.get(position);
			try {
				appSave.saveData(QueryAppUtil.USEDAPP, app.getPkgName() + ";");
			} catch (Exception e) {
				e.printStackTrace();
			}

			Intent intent = app.getIntent();

			if (intent != null) {
				startActivity(intent);
			} else {
				openSystemApp(app);
			}
		}
	}

	/**
	 * 打开系统应用
	 * 
	 * @param app
	 */
	private void openSystemApp(AppInfo app) {
		Intent intent = new Intent();
		int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 9) {
			intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			Uri uri = Uri.fromParts(SCHEME, app.getPkgName(), null);
			intent.setData(uri);
			startActivity(intent);
		}
	}

	/**
	 * 加载数据线程
	 */
	private class DataTask extends Thread {

		private int category;

		public DataTask(int category) {
			this.category = category;
		}

		@Override
		public void run() {
			switch (category) {
			case QueryAppUtil.FILTER_ALL_APP://所有应用程序
				apps = appQuery.queryFilterAppInfo(QueryAppUtil.FILTER_ALL_APP);
				break;
			case QueryAppUtil.FILTER_USED_APP:// 常用工具
				try {
					String[] names = appSave.readApps(QueryAppUtil.USEDAPP);
					temp = appQuery
							.queryFilterAppInfo(QueryAppUtil.FILTER_ALL_APP);
					apps = appQuery.queryAppInfoByPkname(temp, names);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
			handler.sendEmptyMessage(SEARCH_APP);
		}
	}

	/* (非 Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myphone_bt_back:
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
			break;

		default:
			break;
		}
		
	}

}
