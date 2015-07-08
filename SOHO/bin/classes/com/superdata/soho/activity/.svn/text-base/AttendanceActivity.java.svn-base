package com.superdata.soho.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.activity.workattendance.AddTravelActivity;
import com.superdata.soho.activity.workattendance.AskForLeaveActivity;
import com.superdata.soho.activity.workattendance.OverTimeActivity;
import com.superdata.soho.activity.workattendance.PunchCardActivity;
import com.superdata.soho.activity.workattendance.RealTimeCheckActivity;
import com.superdata.soho.activity.workattendance.ResignRunchCardActivity;
import com.superdata.soho.activity.workattendance.StaffTrajectoryActivity;
import com.superdata.soho.activity.workattendance.TakePhotoMainActivity;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;

/**
 * 
 * @ClassName: AttendanceActivity
 * @Description: 考勤管理
 * @author 刘定富
 * @date 2014年6月10日 上午9:34:17
 * 
 */
public class AttendanceActivity extends BaseActivity {
	private GridView gridView;// 显示菜单gridview
	List<HashMap<String, Object>> gridViewList = null;// 菜单list
	Message msg;
	String userId,userName,depName,positionName;
	int ps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		gridView = (GridView) findViewById(R.id.home_gridView);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));// 取消点击效果
		init();// 初始化数据
		gridView.setAdapter(new MyAdapter());// 添加数据适配器
		gridView.setOnItemClickListener(new OnItemClickListener() {// 添加gridView监听事件

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				ps = position;

				Intent intent = new Intent();
				if(position !=0){	
					
				if (null == companyIdJson) {
					Toast.makeText(AttendanceActivity.this, "网络有点不给力,请稍后重试!",
							Toast.LENGTH_SHORT).show();
					return;
				}
				}

				switch (position) {
				case 0:// 打卡
					intent.setClass(getApplicationContext(),
							PunchCardActivity.class);// 跳到打卡页面
					startActivity(intent);// 跳转
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					break;
				case 1:// 请假
					new Thread(numRun).start();
					break;
				case 2:// 加班
					new Thread(numRun).start();
					break;
				case 3:// 出差
					new Thread(numRun).start();
					break;
				case 4:// 员工轨迹
					intent.setClass(getApplicationContext(),
							StaffTrajectoryActivity.class);// 跳到员工轨迹页面
					startActivity(intent);// 跳转
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					break;
				case 5:// 实时查岗
					intent.setClass(getApplicationContext(),
							RealTimeCheckActivity.class);// 跳到实时查岗页面
					startActivity(intent);// 跳转
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					break;
				case 6:
					intent.setClass(getApplicationContext(),
							TakePhotoMainActivity.class);// 跳到拍照上传页面
					intent.putExtra("userName", userName);
					intent.putExtra("depName", depName);
					intent.putExtra("positionName", positionName);
					startActivity(intent);// 跳转
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					break;
				case 7:// 位置补录
					intent.setClass(getApplicationContext(),
							ResignRunchCardActivity.class);// 跳到位置补录页面
					startActivity(intent);// 跳转
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					break;
				default:
					intent.setClass(getApplicationContext(),
							PunchCardActivity.class);// 默认打卡页面
					startActivity(intent);// 跳转
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					break;
				}
			}
		});

	}

	
	Runnable numRun = new Runnable() {
		@Override
		public void run() {
			String url = InterfaceConfig.ASKFORLEAVE_QUERYNUM_URL;
			SDHttpClient sdClient = new SDHttpClient();
			try {
				String numStr = sdClient.post_session(url,
						new ArrayList<NameValuePair>());
				if (numStr.contains("\"")) {
					numStr = numStr.substring(1, numStr.lastIndexOf("\""));
				}
				msg = new Message();
				msg.what = 1;
				msg.obj = numStr;
				handler.sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	Handler handler = new Handler(){@Override
	public void handleMessage(Message msg) {
		Intent intent =null;
		switch (msg.what) {
		case 1:
			intent = new Intent();
			switch (ps) {
			case 1://请假
				intent.setClass(getApplicationContext(),
						AskForLeaveActivity.class);// 跳到请假页面
				
				break;
			case 2://加班
				intent.setClass(getApplicationContext(),
						OverTimeActivity.class);// 跳到加班页面
				break;
			case 3://出差
				intent.setClass(getApplicationContext(),
						AddTravelActivity.class);// 跳到出差页面
				break;
			default:
				break;
			}
			
			intent.putExtra("num", (String)msg.obj);
			startActivity(intent);// 跳转
			overridePendingTransition(R.anim.push_left_in,
					R.anim.push_left_out);
			break;
		default:
			break;
		}
	};};
	
	public void getJsonList(String json) {
		try {
			JSONArray jsonArray = new JSONArray(json);
			int len = jsonArray.length();
			for (int i = 0; i < len; i++) {
				JSONObject jRoot = (JSONObject) jsonArray.get(i);
					if (Integer.parseInt(userId) == Integer.parseInt(userId)) {
						 depName = jRoot.getString("deptName");
						 positionName = jRoot.getString("positionName");
					}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: init
	 * @Description: TODO(数据初始化)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void init() {
		gridViewList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", "打卡管理");
		map.put("image", R.drawable.punchcard_icon);
		gridViewList.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "请假管理");
		map.put("image", R.drawable.askleave_icon);
		gridViewList.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "加班管理");
		map.put("image", R.drawable.workovertime_icon);
		gridViewList.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "出差管理");
		map.put("image", R.drawable.travel_icon);
		gridViewList.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "员工轨迹");
		map.put("image", R.drawable.starfftrajectory_icon);
		gridViewList.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "实时查岗");
		map.put("image", R.drawable.chechtime_icon);
		gridViewList.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "拍照上传");
		map.put("image", R.drawable.takephoto_icon);
		gridViewList.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "位置补录");
		map.put("image", R.drawable.postionrepair_icon);
		gridViewList.add(map);

		mapconfig = SharedPreferencesConfig.config(AttendanceActivity.this);
		loadDateThread loadDateThread = new loadDateThread();
		new Thread(loadDateThread).start();
	}
 
	/**
	 * 加载所有员工信息
	 * 
	 * @ClassName loadDateThread
	 * @author Administrator
	 * @date 2014年8月27日 下午1:45:56
	 * 
	 */
	List<NameValuePair> params;
	Map<String, String> mapconfig;

	class loadDateThread implements Runnable {

		/*
		 * (非 Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			String url = InterfaceConfig.SEARCH_COMPANY;
			SDHttpClient sdClient = new SDHttpClient();
			params = new ArrayList<NameValuePair>();
			String companyId = mapconfig.get(InterfaceConfig.COMPANYID);
			userId = mapconfig.get(InterfaceConfig.ID);
			userName = mapconfig.get(InterfaceConfig.LOGIN_USER_NAME);
			params.add(new BasicNameValuePair("companyID", companyId));
			try {
				companyIdJson = sdClient.post_session(url, params);
				getJsonList(companyIdJson);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * @ClassName: MyAdapter
	 * @Description: TODO(自定义适配器用来自定义加载gridview数据)
	 * @author 刘定富
	 * @date 2014年6月10日 上午9:23:51
	 */
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return gridViewList.size();// 数据大小
		}

		@Override
		public Object getItem(int position) {
			return gridViewList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {// 返回自定义view
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.home_gridview_item, null);// 加载自定义布局
				holder.textView = (TextView) convertView
						.findViewById(R.id.home_gitem_textView);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.home_gitem_img);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textView.setText(gridViewList.get(position).get("title")
					.toString());
			holder.imageView.setImageResource((Integer) (gridViewList
					.get(position).get("image")));
			return convertView;
		}

	}

	/**
	 * 
	 * @ClassName: ViewHolder
	 * @Description: TODO(用来装组件类)
	 * @author 刘定富
	 * @date 2014年6月10日 上午9:26:26
	 * 
	 */
	class ViewHolder {
		TextView textView;// 标题
		ImageView imageView;// 图片
	}
}
