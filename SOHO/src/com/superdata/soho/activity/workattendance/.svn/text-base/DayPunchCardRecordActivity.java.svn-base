package com.superdata.soho.activity.workattendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.PunchCardBean;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.NetCheckUtil;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;
import com.superdata.soho.view.XListView;
import com.superdata.soho.view.XListView.IXListViewListener;

import android.os.Handler;
import android.util.Log;
/**
 * 
 * @ClassName: DayPunchCardRecordActivity
 * @Description: 打卡记录类
 * @author 刘定富
 * @date 2014年6月10日 上午9:36:01
 * 
 */
public class DayPunchCardRecordActivity extends Activity implements
		IXListViewListener {
	XListView listView;// 显示数据listview
	List<HashMap<String, Object>> listArray;// 装数据的list集合
	List<PunchCardBean> listPunchCard;
	private int pageIndex = 1;
	private int pageRecord = 10;
	private int pageCount,page;
	private Message msg;
	SDProgressDialog sdDialog;
	String userName;
	MyAdapter adapter;
	String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_punch_card_records);
		
		init();

	}
	
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			
		}
		
	};
	/**
	 * 
	 * @Title init 
	 */
	public void init() {
		
		listView = (XListView) findViewById(R.id.dayRecordList);// 初始化listview
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		listView.setOnItemClickListener(itemClickListener);
		adapter = new MyAdapter();
		
		sdDialog = new SDProgressDialog(DayPunchCardRecordActivity.this);
		sdDialog.show();
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(DayPunchCardRecordActivity.this);
		userName = mapconfig.get(InterfaceConfig.LAST_USER_NAME);
		id = mapconfig.get(InterfaceConfig.ID);
		listPunchCard = new ArrayList<PunchCardBean>();

		LoadDataThread loadDataThread = new LoadDataThread();
		loadDataThread.start();

	}
/**
 * 加载分页数据
 * @ClassName LoadDataThread
 * @author Administrator
 * @date 2014年8月27日 下午3:12:34
 *
 */
	class LoadDataThread extends Thread {

		/*
		 * (非 Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			if (!NetCheckUtil
					.isNetworkAvailable(DayPunchCardRecordActivity.this)) {
				Toast.makeText(DayPunchCardRecordActivity.this,
						"网络有点不给力,请稍后重试!", Toast.LENGTH_SHORT).show();
				return;
			}
			String url = InterfaceConfig.PUNCH_CARD;
			SDHttpClient sdClient = new SDHttpClient();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("empId", id));
			params.add(new BasicNameValuePair("page", String.valueOf(pageIndex)));
			params.add(new BasicNameValuePair("rows", String
					.valueOf(pageRecord)));
			try {
				String json = sdClient.post_session(url, params);
				Log.d("json++", json);

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

	/**
	 * 
	 * @Title: pcOnClick
	 * @Description: TODO(按钮的点击事件)
	 * @param @param view 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void pcOnClick(View view) {
		switch (view.getId()) {
		case R.id.bt_back:// 返回
			// onBackPressed();
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @ClassName: MyAdapter
	 * @Description: 自定义适配器类继承baseadapter
	 * @author 刘定富
	 * @date 2014年6月10日 上午9:37:27
	 * 
	 */
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listPunchCard.size();// 数据大小
		}

		@Override
		public Object getItem(int position) {
			return listPunchCard.get(position);
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
						.inflate(R.layout.day_punch_card_list_item, null);// 加载自定义view
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.dayPunch_name);
				holder.tv_date = (TextView) convertView
						.findViewById(R.id.dayPunch_date);
				holder.tv_address = (TextView) convertView
						.findViewById(R.id.dayPunch_address);
				holder.tv_type = (TextView) convertView
						.findViewById(R.id.dayPunch_type);
				holder.tv_status = (TextView) convertView
						.findViewById(R.id.dayPunch_status);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_name.setText(listPunchCard.get(position).getUserName());
			
			String endStr =listPunchCard.get(position).getCheckTime();
			String[] ymdend = endStr.substring(0, endStr.indexOf(" "))
					.split("-");
			String[] hmend = endStr.substring(endStr.indexOf(" ")).trim()
					.split(":");
			int ye = Integer.parseInt(ymdend[0]);
			int me = Integer.parseInt(ymdend[1]);
			int de = Integer.parseInt(ymdend[2]);
			int he = Integer.parseInt(hmend[0]);
			int mie = Integer.parseInt(hmend[1]);
			
			holder.tv_date.setText(String.format("%d-%d-%d %02d:%02d",ye,me,de,he,mie));
			holder.tv_address.setText(listPunchCard.get(position).getAddress());
			if (listPunchCard.get(position).getTypeId().equals("1")) {
				holder.tv_status.setText("补登");
			} else {
				holder.tv_status.setText("正常");
			}
			holder.tv_type.setText(listPunchCard.get(position).getTypeName());
			return convertView;
		}

	}

	/**
	 * 
	 * @ClassName: ViewHolder
	 * @Description: 控件封装类
	 * @author 刘定富
	 * @date 2014年6月10日 上午9:40:41
	 * 
	 */
	class ViewHolder {
		TextView tv_name, tv_date, tv_address, tv_type, tv_status;// 名字，日期，时间，地址，类型，状态
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	
	/**
	 * 
	 * @ClassName LoadMoreDataTask
	 * @author Administrator
	 * @date 2014年7月30日 上午9:22:12
	 *
	 */
	private class LoadMoreDataTask implements Runnable {
		@Override
		public void run() {
			try {
				if (!NetCheckUtil
						.isNetworkAvailable(DayPunchCardRecordActivity.this)) {
					Toast.makeText(DayPunchCardRecordActivity.this,
							"网络有点不给力,请稍后重试!", Toast.LENGTH_SHORT).show();
					return;
				}
				String url = InterfaceConfig.PUNCH_CARD;
				SDHttpClient sdClient = new SDHttpClient();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("empId", id));
				params.add(new BasicNameValuePair("page", String.valueOf(pageIndex)));
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
				Toast.makeText(DayPunchCardRecordActivity.this, "网络异常", 1).show();
				break;
			case 4:
				Toast.makeText(DayPunchCardRecordActivity.this, "---", 1).show();
				break;
			}
			if (sdDialog.isShow()) {
				sdDialog.cancel();
			}
		}
	};

	public void updateUI3(String json) {
		if(listPunchCard.size()>0){
			listPunchCard.clear();
		}
		JSONObject jRoot;
		try {
			jRoot = new JSONObject(json);
			
			JSONArray jData = jRoot.getJSONArray("rows");
			int jsonpage=jRoot.getInt("total");
			page=jsonpage%pageRecord;
			if(page>=1){
				pageCount=jsonpage/pageRecord+1;
			}else{
				pageCount=jsonpage/pageRecord;
			}
			PunchCardBean punchCardBean;
			for (int i = 0; i < jData.length(); i++) {
				JSONObject j = (JSONObject) jData.opt(i);
				punchCardBean = new PunchCardBean();
				
				if(j.getString("address").equals("null")){
					punchCardBean.setAddress("地址为空");
				}else {
					punchCardBean.setAddress(j.getString("address"));
				}
				
				punchCardBean.setCheckTime(j.getString("trackDate"));
				punchCardBean.setUserName(userName);
				punchCardBean.setTypeId(j.getString("isReg"));
//				punchCardBean.setCheckTypeId(j.getString("trackType"));
				punchCardBean.setTypeName(j.getString("typeName"));

				listPunchCard.add(punchCardBean);
			}

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
			PunchCardBean punchCardBean;
			for (int i = 0; i < jData.length(); i++) {
				JSONObject j = (JSONObject) jData.opt(i);
				punchCardBean = new PunchCardBean();
				if(j.getString("address").equals("null")){
					punchCardBean.setAddress("地址为空");
				}else {
					punchCardBean.setAddress(j.getString("address"));
				}
				
				punchCardBean.setCheckTime(j.getString("trackDate"));
				punchCardBean.setUserName(userName);
				punchCardBean.setTypeId(j.getString("isReg"));
				punchCardBean.setTypeName(j.getString("typeName"));
				
				listPunchCard.add(punchCardBean);
			}

			if (jData.length() == 0) {
				Toast.makeText(DayPunchCardRecordActivity.this, "数据加载完成", 1).show();
			}
			adapter.notifyDataSetChanged();
			sdDialog.cancel();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		onLoad();
	}

	/* (非 Javadoc)
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
	
	/* (非 Javadoc)
	 * @see com.superdata.soho.view.XListView.IXListViewListener#onLoadMore()
	 */
	@Override
	public void onLoadMore() {
		pageIndex = pageIndex + 1;
		if(pageIndex-1>=pageCount){
			Toast.makeText(DayPunchCardRecordActivity.this,"已经是最后一页!", Toast.LENGTH_SHORT).show();
			onLoad();
		}else{
			
			LoadMoreDataTask task = new LoadMoreDataTask();
			new Thread(task).start();
		}
		
	}

}
