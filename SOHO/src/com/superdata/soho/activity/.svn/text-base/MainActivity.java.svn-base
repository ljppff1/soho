package com.superdata.soho.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.SDAppManager;
import com.superdata.soho.entity.TabItem;
/**
 * 
* @ClassName: MainActivity
* @Description: TODO(主界面)
* @author 刘定富
* @date 2014年6月10日 上午10:23:07
*
 */
public class MainActivity extends TabHostActivity {
	List<TabItem> mItems;
	private LayoutInflater mLayoutInflater;
	
	/**在初始化TabWidget前调用
	 * 和TabWidget有关的必须在这里初始化*/
	@Override
	protected void prepare() {//底部菜单栏初始化方法
		TabItem home = new TabItem(
				"首页",									//标题
				R.drawable.icon_home,					//图标
				R.drawable.example_tab_item_bg,			// 背景
				new Intent(this, HomeActivity.class));	// 跳转intent
		
		TabItem info = new TabItem(
				"考勤管理",
				R.drawable.icon_kaoqin,
				R.drawable.example_tab_item_bg,
				new Intent(this, AttendanceActivity.class));
		
		TabItem msg = new TabItem(
				"审批中心",
				R.drawable.icon_approval,
				R.drawable.example_tab_item_bg,
				new Intent(this, ApprovalActivity.class));
		
		TabItem square = new TabItem(
				"记录汇总",
				R.drawable.icon_record,
				R.drawable.example_tab_item_bg,
				new Intent(this, RecordActivity.class));
		
		mItems = new ArrayList<TabItem>();
		mItems.add(home);
		mItems.add(info);
		mItems.add(msg);
		mItems.add(square);

		// 设置分割线
		TabWidget tabWidget = getTabWidget();
		tabWidget.setDividerDrawable(R.drawable.tab_divider);
		
		mLayoutInflater = getLayoutInflater();//获取系统渲染类
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCurrentTab(0);//设置默认选中
	}
	
	/**tab的title，icon，边距设定等等*/
	@Override
	protected void setTabItemTextView(TextView textView, int position) {
		textView.setPadding(3, 3, 3, 3);
		textView.setText(mItems.get(position).getTitle());
		textView.setBackgroundResource(mItems.get(position).getBg());
		//textView.setBackgroundResource(R.drawable.ic_launcher);// 
		textView.setCompoundDrawablesWithIntrinsicBounds(0,mItems.get(position).getIcon(), 0, 0);
		
	}
	
	/**tab唯一的id*/
	@Override
	protected String getTabItemId(int position) {
		return mItems.get(position).getTitle();	// 我们使用title来作为id，你也可以自定
	}

	/**点击tab时触发的事件*/
	@Override
	protected Intent getTabItemIntent(int position) {
		return mItems.get(position).getIntent();
	}
 
	@Override
	protected int getTabItemCount() {
		return mItems.size();
	}
	
	/**自定义头部文件*/
	@Override
	protected View getTop() {
		return mLayoutInflater.inflate(R.layout.example_top, null);//加载自定义布局
	}
	
	public boolean isServiceRun(Context context){
	     ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	     List<RunningServiceInfo> list = am.getRunningServices(30);
	     for(RunningServiceInfo info : list){
	         if(info.service.getClassName().trim().equals("com.superdata.soho.service.UploadPostionService")){
	                  return true;
	         }
	    }
	    return false;
	}
	/**
	 * 2次按住返回键退出系统
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		Boolean flag=isServiceRun(MainActivity.this);
		if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				/*if(flag){
					stopService(new Intent("com.superdata.soho.service.UploadPostionService"));	
				}*/
				SDAppManager.getAppManager().AppExit(getApplicationContext());//退出应用程序，执行销毁任务
				android.os.Process.killProcess(android.os.Process.myPid());
			}
			return true;
			}
		return super.dispatchKeyEvent(event);
	}
	
	private long exitTime = 0;
	
}
