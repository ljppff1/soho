package com.superdata.soho.activity.approvalcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.view.SDProgressDialog;

/**
 * 费用支出Items详情
 * @author lj
 *
 * 2014年8月14日
 */
public class CostReportlookdetailActivity extends BaseActivity  {
    private List<HashMap<Integer, String>> list =new ArrayList<HashMap<Integer,String>>();
	private SDProgressDialog sdDialog;
	private ListView listview;
	private MyAdapter adapter;
	private TextView cost_report_lookdetail_bt_back;
	private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_report_lookdetail_activity);
	    id =	getIntent().getExtras().getString("ID");
		initView();
		initdata();
	}
	
	private void initView() {
		cost_report_lookdetail_bt_back =(TextView)this.findViewById(R.id.cost_report_lookdetail_bt_back);
		cost_report_lookdetail_bt_back.setOnClickListener(listener);
		listview =(ListView)this.findViewById(R.id.cost_report_lookdetail_app_listview);
	}

    OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cost_report_lookdetail_bt_back:
				onBackPressed();
				break;
			default:
				break;
			}
		}
	};

	public void initdata(){
		initdata(id);	
	}
	
	private void initdata(String id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id",id));
		new MyTask().execute(params);
	}

	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =com.superdata.soho.config.InterfaceConfig.COSTREPORTAPPROVALDETAIL;
		private HashMap<Integer, String> map;
		private Message  msg ;
		@Override
        protected void onPreExecute() {
        	sdDialog = new SDProgressDialog(CostReportlookdetailActivity.this);
    	 	sdDialog.show();
        	super.onPreExecute();
        }
		@Override
		protected String doInBackground(List<NameValuePair>... params) {
		 String result = null;
		try {
			result = new SDHttpClient().post_session(url, params[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			 if(result!=null){
					try {
					   JSONObject jRoot = new JSONObject(result);
					   JSONArray jaresultList = jRoot.getJSONArray("saleplandtail");
						for(int i=0;i<jaresultList.length();i++){
							map =new HashMap<Integer,String>();
				            String itemname =((jaresultList.getJSONObject(i).get("itemname")).equals(null))?"没有":(String) jaresultList.getJSONObject(i).get("itemname");
				            String amount = ((String.valueOf((jaresultList.getJSONObject(i).get("amount")))).equals(null))?"": String.valueOf((jaresultList.getJSONObject(i).get("amount")));
				            String remark =((jaresultList.getJSONObject(i).get("remark")).equals(null))?"负责人没有":(String) jaresultList.getJSONObject(i).get("remark");
				            map.put(0, itemname);
				            map.put(1, String.valueOf(amount));
				            map.put(2, remark);
				            list.add(map);
						}
						
						msg =new Message();
					      adapter =new MyAdapter();
					    msg.what =1;
					    if(map.size()==0){
					    	msg.what=2;
					    }
					} catch (Exception e) {
						e.printStackTrace();
						msg =new Message();
						msg.what=2;
					}
				}

			     if(sdDialog.isShow())
				sdDialog.cancel();
			     handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	
	private Handler handler =new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				listview.setAdapter(adapter);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "暂无数据", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(CostReportlookdetailActivity.this).inflate(R.layout.item_cost_report_lookdetail, null);
			    holder.name=(TextView) convertView.findViewById(R.id.tv_cb_item_cost_report_lookdetail_name);
                holder.money=(TextView)convertView.findViewById(R.id.tv_cb_item_cost_report_lookdetail_money);
                holder.remark=(TextView)convertView.findViewById(R.id.tv_cb_item_cost_report_lookdetail_remark);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
		    holder.name.setText(list.get(position).get(0));
            holder.money.setText(list.get(position).get(1));
            holder.remark.setText(list.get(position).get(2));
            return convertView;
		}
	}

	class ViewHolder {
		TextView name,money,remark;
	}

	
	

	
	
}
