package com.superdata.soho.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.superdata.soho.R;
import com.superdata.soho.common.SDAppManager;

/**
 * 自定义TabHost实现类
 * 
 * @author Administrator
 * 
 */
public abstract class TabHostActivity extends TabActivity {

	private TabHost mTabHost;
	private TabWidget mTabWidget;// 底部菜单
	private LayoutInflater mLayoutflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set theme because we do not want the shadow
		setTheme(R.style.Theme_Tabhost);// 设置样式
		// 设置全屏模式

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

		WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// 去除标题栏

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.api_tab_host);

		mLayoutflater = getLayoutInflater();

		mTabHost = getTabHost();// 获取系统tabhost
		mTabWidget = getTabWidget();// 获取系统tabwidget
		// mTabWidget.setStripEnabled(false); // need android2.2
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {// tab页切换监听
			@Override
			public void onTabChanged(String tabId) {
				TextView title_Name = (TextView) findViewById(R.id.example_center);// 设置标题
				title_Name.setText(tabId);
			}
		});
		prepare();// 调用初始化方法

		initTop();
		initTabSpec();// 初始化tabitem
		SDAppManager.getAppManager().addActivity(this); // 添加Activity到堆栈
	}

	/**
	 * 初始化标题栏
	 */
	private void initTop() {

		View child = getTop();
		LinearLayout layout = (LinearLayout) findViewById(R.id.tab_top);
		layout.addView(child);
	}

	/**
	 * 
	 * 初始化tab
	 */
	private void initTabSpec() {

		int count = getTabItemCount();

		for (int i = 0; i < count; i++) {
			// set text view
			View tabItem = mLayoutflater.inflate(R.layout.api_tab_item, null);

			TextView tvTabItem = (TextView) tabItem
					.findViewById(R.id.tab_item_tv);
			setTabItemTextView(tvTabItem, i);
			// set id
			String tabItemId = getTabItemId(i);
			// set tab spec
			TabSpec tabSpec = mTabHost.newTabSpec(tabItemId);
			tabSpec.setIndicator(tabItem);
			tabSpec.setContent(getTabItemIntent(i));

			mTabHost.addTab(tabSpec);
		}

	}

	/** 在初始化界面之前调用 */
	protected void prepare() {
		// do nothing or you override it
	}

	/** 自定义头部布局 */
	protected View getTop() {
		// do nothing or you override it
		return null;
	}

	protected int getTabCount() {
		return mTabHost.getTabWidget().getTabCount();
	}

	/** 设置TabItem的图标和标题等 */
	abstract protected void setTabItemTextView(TextView textView, int position);

	abstract protected String getTabItemId(int position);

	abstract protected Intent getTabItemIntent(int position);

	abstract protected int getTabItemCount();

	protected void setCurrentTab(int index) {
		mTabHost.setCurrentTab(index);
	}

	protected void focusCurrentTab(int index) {
		mTabWidget.focusCurrentTab(index);
	}

}
