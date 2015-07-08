/**  
 * @Title: TravelRecordActivity.java
 * @Package com.superdata.soho.activity
 * @Description: TODO(用一句话描述该文件做什么)
 * @author A18ccms A18ccms_gmail_com  
 * @date 2014年6月12日 上午10:55:17
 * @version V1.0  
 */
package com.superdata.soho.activity.workattendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.TravelBean;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.NetCheckUtil;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;
import com.superdata.soho.view.XListView;
import com.superdata.soho.view.XListView.IXListViewListener;

/**
 * @ClassName: TravelRecordActivity
 * @Description: 出差记录列表
 * @author Administrator
 * @date 2014年6月12日 上午10:55:17
 * 
 */
public class TravelRecordActivity extends BaseActivity implements
		OnClickListener, IXListViewListener {
	XListView listView;// 显示数据listview
	List<TravelBean> travelList;
	TravelBean travelBean;
	Button leave_bt_back,leave_bt_pc_records;
	SDProgressDialog sdDialog;
	Intent intent;
	private int pageIndex = 1;
	private int pageRecord = 10;
	private int pageCount, page;
	private Message msg;
	String id;
	MyAdapter adapter;
	TextView mytitlec;

	/*
	 * (非 Javadoc) <p>Title: onCreate</p> <p>Description: 进入页面调用此方法加载页面</p>
	 * 
	 * @param savedInstanceState
	 * 
	 * @see com.superdata.soho.common.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leave_record_activity);
		mytitlec=(TextView) findViewById(R.id.mytitlec);
		mytitlec.setText("出差记录");
		init();

	}

	public void init() {
		sdDialog = new SDProgressDialog(TravelRecordActivity.this);
		sdDialog.show();
		intent = new Intent();
		leave_bt_back = (Button) findViewById(R.id.leave_bt_back);
		leave_bt_pc_records = (Button) findViewById(R.id.leave_bt_pc_records);
		leave_bt_back.setOnClickListener(this);
		leave_bt_pc_records.setOnClickListener(this);
		listView = (XListView) findViewById(R.id.leave_record_listview);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		listView.setOnItemClickListener(itemClickListener);

		travelBean = new TravelBean();
		travelList = new ArrayList<TravelBean>();

		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(TravelRecordActivity.this);
		id = mapconfig.get(InterfaceConfig.ID);

		LoadDataThread loadDataThread = new LoadDataThread();
		loadDataThread.start();

	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

		}

	};

	class LoadDataThread extends Thread {

		/*
		 * (非 Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			if (!NetCheckUtil.isNetworkAvailable(TravelRecordActivity.this)) {
				Toast.makeText(TravelRecordActivity.this, "网络有点不给力,请稍后重试!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			String url = InterfaceConfig.TRAVEL_LIST_URL;
			SDHttpClient sdClient = new SDHttpClient();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("page", String.valueOf(pageIndex)));
			params.add(new BasicNameValuePair("rows", String
					.valueOf(pageRecord)));
			try {
				String json = sdClient.post_session(url, params);
				msg = new Message();
				msg.what = 3;
				msg.obj = json;
				handler.sendMessage(msg);

			} catch (Exception e) {
				e.printStackTrace();
				msg = new Message();
				msg.what = 500;
				handler.sendMessage(msg);
			}
		}
	}

	private class LoadMoreDataTask implements Runnable {
		@Override
		public void run() {
			try {
				if (!NetCheckUtil.isNetworkAvailable(TravelRecordActivity.this)) {
					Toast.makeText(TravelRecordActivity.this, "网络有点不给力,请稍后重试!",
							Toast.LENGTH_SHORT).show();
					return;
				}
				String url = InterfaceConfig.TRAVEL_LIST_URL;
				SDHttpClient sdClient = new SDHttpClient();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// params.add(new BasicNameValuePair("empId", id));
				params.add(new BasicNameValuePair("page", String
						.valueOf(pageIndex)));
				params.add(new BasicNameValuePair("rows", String
						.valueOf(pageRecord)));
				String json = sdClient.post_session(url, params);
				Log.d("json++", json);

				msg = new Message();
				msg.what = 1;
				msg.obj = json;
				handler.sendMessage(msg);

			} catch (Exception e) {
				e.printStackTrace();
				msg = new Message();
				msg.what = 500;
				handler.sendMessage(msg);
			}
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				updateUI(msg.obj.toString());
				break;
			case 2:

				break;
			case 3:
				updateUI3(msg.obj.toString());
				break;
			case 500:
				Toast.makeText(TravelRecordActivity.this, "网络异常", 1).show();
				break;
			}
			if (sdDialog.isShow()) {
				sdDialog.cancel();
			}
		}
	};

	public void updateUI3(String json) {
		if (travelList.size() > 0) {
			travelList.clear();
		}
		JSONObject jRoot;
		try {
			jRoot = new JSONObject(json);
			JSONArray jData = jRoot.getJSONArray("rows");
			int jsonpage = jRoot.getInt("total");
			page = jsonpage % pageRecord;
			if (page >= 1) {
				pageCount = jsonpage / pageRecord + 1;
			} else {
				pageCount = jsonpage / pageRecord;
			}

			for (int i = 0; i < jData.length(); i++) {
				JSONObject j = (JSONObject) jData.opt(i);
				travelBean = new TravelBean();
				travelBean.setUserName(j.getString("empName"));
				travelBean.setRecordApproval(j.getString("status"));
				travelBean.setEndTime(j.getString("endDate").trim().substring(0,j.getString("endDate").trim().length()-2));
				if (j.getString("remark").equals("null")) {
					travelBean.setRecordReason("暂无");
				} else {
					travelBean.setRecordReason(j.getString("remark"));
				}
				travelBean.setStartTime(j.getString("startDate").trim().substring(0,j.getString("startDate").trim().length()-2));
				travelBean.setTotalTime(j.getInt("length"));
				travelBean.setTypeName(j.getString("typeName"));
				travelBean.setCode(j.getString("code"));
				travelBean.setId(j.getString("id"));
				travelBean.setRoute(j.getString("route"));
				travelList.add(travelBean);
			}

			adapter = new MyAdapter(TravelRecordActivity.this, travelList);
			listView.setAdapter(adapter);// 给listview添加适配器
			adapter.notifyDataSetChanged();// 数据变化刷新
			sdDialog.cancel();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void updateUI(String json) {
		JSONObject jRoot;
		try {
			jRoot = new JSONObject(json);
			JSONArray jData = jRoot.getJSONArray("rows");
			for (int i = 0; i < jData.length(); i++) {
				JSONObject j = (JSONObject) jData.opt(i);
				travelBean = new TravelBean();
				travelBean.setUserName(j.getString("empName"));
				travelBean.setRecordApproval(j.getString("status"));
				travelBean.setEndTime(j.getString("endDate"));
				if (j.getString("remark").equals("null")) {
					travelBean.setRecordReason("暂无");
				} else {
					travelBean.setRecordReason(j.getString("remark"));
				}
				travelBean.setStartTime(j.getString("startDate"));
				travelBean.setTotalTime(j.getInt("length"));
				travelBean.setTypeName(j.getString("typeName"));
				travelBean.setCode(j.getString("code"));
				travelBean.setId(j.getString("id"));
				travelBean.setRoute(j.getString("route"));
				travelList.add(travelBean);
			}

			adapter = new MyAdapter(TravelRecordActivity.this, travelList);
			listView.setAdapter(adapter);// 给listview添加适配器
			adapter.notifyDataSetChanged();// 数据变化刷新
			sdDialog.cancel();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onLoad();
	}

	class MyAdapter extends BaseAdapter {

		List<TravelBean> travelList = new ArrayList<TravelBean>();
		private Context context;
		LayoutInflater inflater = null;

		public MyAdapter(Context context, List<TravelBean> travelList) {
			this.context = context;
			this.inflater = LayoutInflater.from(context);
			this.travelList = travelList;
		}

		/*
		 * (非 Javadoc) <p>Title: getCount</p> <p>Description: </p>
		 * 
		 * @return
		 * 
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			return travelList.size();
		}

		/*
		 * (非 Javadoc) <p>Title: getItem</p> <p>Description: </p>
		 * 
		 * @param position
		 * 
		 * @return
		 * 
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			return travelList.get(position);
		}

		/*
		 * (非 Javadoc) <p>Title: getItemId</p> <p>Description: </p>
		 * 
		 * @param position
		 * 
		 * @return
		 * 
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}

		/*
		 * (非 Javadoc) <p>Title: getView</p> <p>Description: </p>
		 * 
		 * @param position
		 * 
		 * @param convertView
		 * 
		 * @param parent
		 * 
		 * @return
		 * 
		 * @see android.widget.Adapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(TravelRecordActivity.this)
						.inflate(R.layout.leave_record_list_item, null);
				holder.leave_user_name = (TextView) convertView
						.findViewById(R.id.leave_user_name);
				holder.leave_record_start_time = (TextView) convertView
						.findViewById(R.id.leave_record_start_time);
				holder.leave_record_end_time = (TextView) convertView
						.findViewById(R.id.leave_record_end_time);
				holder.leave_record_type = (TextView) convertView
						.findViewById(R.id.leave_record_type);
				holder.leave_record_total_time = (TextView) convertView
						.findViewById(R.id.leave_record_total_time);
				holder.leave_record_approval = (TextView) convertView
						.findViewById(R.id.leave_record_approval);
				holder.leave_record_reason = (TextView) convertView
						.findViewById(R.id.leave_record_reason);
				holder.leave_record_modify_btn = (Button) convertView
						.findViewById(R.id.leave_record_modify_btn);
				holder.leave_record_delete_btn = (Button) convertView
						.findViewById(R.id.leave_record_delete_btn);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.leave_user_name.setText(travelList.get(position)
					.getUserName());
			
			
			
			String startStr = travelList.get(position).getStartTime();
			String[] ymd = startStr.substring(0, startStr.indexOf(" "))
					.split("-");
			String[] hm = startStr.substring(startStr.indexOf(" ")).trim()
					.split(":");
			int y = Integer.parseInt(ymd[0]);
			int m = Integer.parseInt(ymd[1]);
			int d = Integer.parseInt(ymd[2]);
			int h = Integer.parseInt(hm[0]);
			int mi = Integer.parseInt(hm[1]);
			
			String endStr = travelList.get(position).getEndTime();
			String[] ymdend = endStr.substring(0, endStr.indexOf(" "))
					.split("-");
			String[] hmend = endStr.substring(endStr.indexOf(" ")).trim()
					.split(":");
			int ye = Integer.parseInt(ymdend[0]);
			int me = Integer.parseInt(ymdend[1]);
			int de = Integer.parseInt(ymdend[2]);
			int he = Integer.parseInt(hmend[0]);
			int mie = Integer.parseInt(hmend[1]);
			holder.leave_record_start_time.setText(String.format("%d-%d-%d %02d:%02d",y ,m, d,h,mi));
			
			holder.leave_record_end_time.setText(String.format("%d-%d-%d %02d:%02d",ye,me,de,he,mie));
			
			
			holder.leave_record_type.setText(travelList.get(position)
					.getTypeName());
			holder.leave_record_total_time.setText( travelList.get(position).getTotalTime() + "小时");
			if (travelList.get(position).getRecordApproval().toString()
					.equals("0")) {
				holder.leave_record_approval.setText("未审核");
			} else {
				holder.leave_record_approval.setText("已审核");
			}
			holder.leave_record_reason.setText(travelList.get(position)
					.getRecordReason());
			holder.leave_record_modify_btn
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View view) {

							intent.setClass(TravelRecordActivity.this,
									AddTravelActivity.class);
							intent.putExtra("update", travelList.get(position));
							startActivity(intent);
							finish();
						}
					});
			holder.leave_record_delete_btn
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							
							showDeleteDialog(travelList.get(position).getId());
						}
					});

			return convertView;
		}

	}

	class ViewHolder {
		public TextView leave_user_name, leave_record_start_time,
				leave_record_end_time, leave_record_type,
				leave_record_total_time, leave_record_approval,
				leave_record_reason;
//		leave_record_reason, pay_leave;
		Button leave_record_modify_btn, leave_record_delete_btn;
	}

	/**
	 * 显示确认删除
	 * @Title showUpdateDialog
	 * @param id 方法注释
	 */
		public void showDeleteDialog(final String id) {
			AlertDialog.Builder builder = new Builder(this);    //实例化对话框
			//builder.setIcon(getResources().getDrawable(R.drawable.notification));   //添加图标
			builder.setTitle("确认删除!");   //添加标题
			//builder.setMessage(info.getDescription());   //添加内容
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					sdDialog.show();
					deleteThread deleteThread = new deleteThread(id);
					new Thread(deleteThread).start();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
				}
			});
			builder.create().show();   //显示对话框
		}
		
	private class deleteThread implements Runnable {

		String sid;
		public deleteThread(String id){
			sid=id;
		}
		/*
		 * (非 Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if (!NetCheckUtil.isNetworkAvailable(TravelRecordActivity.this)) {
				Toast.makeText(TravelRecordActivity.this, "网络有点不给力,请稍后重试!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			String url = InterfaceConfig.TRAVEL_DELETE_URL;
			SDHttpClient sdClient = new SDHttpClient();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// params.add(new BasicNameValuePair("empId", id));
			params.add(new BasicNameValuePair("ids", sid));
			
			try {
				String json = sdClient.post_session(url, params);
				Log.v("json---", json);
				
				JSONObject jRoot=new JSONObject(json);
				String ResultCode=jRoot.getString("resultCode");
				if(ResultCode.equals("200")){
					pageIndex = 1;
					LoadDataThread task = new LoadDataThread();
					new Thread(task).start();
				}else{
					msg.what = 500;
					handler.sendMessage(msg);
				}
				
				/*if(json.equals("true")){
					msg = new Message();
					msg.what = 2;
					msg.obj = json;
					handler.sendMessage(msg);
					pageIndex = 1;
					LoadDataThread task = new LoadDataThread();
					new Thread(task).start();
				}*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * (非 Javadoc) <p>Title: onClick</p> <p>Description: </p>
	 * 
	 * @param v
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.leave_bt_back:
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
			break;
		case R.id.leave_bt_pc_records:
		intent = new Intent();
		//Boolean flag=CheckServiceUtil.isServiceRun(getApplication());
		intent.setClass(TravelRecordActivity.this, AddTravelActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		break;
		default:
			break;
		}
	}
/**
 * 记录加载信息
 * @Title onLoad 方法注释
 */
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}

	/*
	 * (非 Javadoc)
	 * 上拉刷新
	 * @see com.superdata.soho.view.XListView.IXListViewListener#onRefresh()
	 */
	@Override
	public void onRefresh() {
		sdDialog.show();
		pageIndex = 1;
		LoadDataThread task = new LoadDataThread();
		new Thread(task).start();
		onLoad();

	}

	/*
	 * (非 Javadoc)
	 * 下拉加载
	 * @see com.superdata.soho.view.XListView.IXListViewListener#onLoadMore()
	 */
	@Override
	public void onLoadMore() {
		pageIndex = pageIndex + 1;
		if (pageIndex - 1 >= pageCount) {
			Toast.makeText(TravelRecordActivity.this, "已经是最后一页!",
					Toast.LENGTH_SHORT).show();
			onLoad();
		} else {

			LoadMoreDataTask task = new LoadMoreDataTask();
			new Thread(task).start();
		}

	}
}
