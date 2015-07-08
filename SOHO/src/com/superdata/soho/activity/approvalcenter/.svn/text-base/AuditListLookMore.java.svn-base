package com.superdata.soho.activity.approvalcenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.AuditListDetail;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
/**
 * 查看审批列表信息，此为一个公用的界面，，当传过来数据类型id和单据id时，即可！
 * @author lj
 *
 * 2014年9月11日
 */
public class AuditListLookMore extends BaseActivity{
    private ListView listview;
    //单据id
	private String DataId;
	//数据类型id
	private String DataTypeId;
    private List<AuditListDetail> list =new ArrayList<AuditListDetail>();
    private MyAdapter adapter;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.auditlistlookmore);
    	initSharePreferences();
    	DataId=	getIntent().getExtras().getString("DATAID");
    	DataTypeId =getIntent().getExtras().getString("DATATYPEID");
    	
    	initView();
    	initData();
    	
    	
    }
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(AuditListLookMore.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
	}


	    private void initData() {
	    	initdata(DataId,DataTypeId);
		
	}


		
	
		private void initdata(String dataId, String dataTypeId) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("dataId", dataId));
			params.add(new BasicNameValuePair("dataTypeId", dataTypeId));
			new MyTask().execute(params);
		}

		class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
			String url =com.superdata.soho.config.InterfaceConfig.SHOWAUDIT;
			
			@Override
	        protected void onPreExecute() {
	        	super.onPreExecute();
	        }
			@Override
			protected String doInBackground(List<NameValuePair>... params) {
			 String result = null;
			try {
				result = new SDHttpClient().post_session(url, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return result;
			}
			
			@Override
			protected void onPostExecute(String result) {
				 if(result!=null){
						try {
	                       JSONArray jaresultList =new JSONArray(result);
							for(int i=0;i<jaresultList.length();i++){
						           String auditName =(jaresultList.getJSONObject(i).get("auditName")).equals(null)?"没有"+i:(String) jaresultList.getJSONObject(i).get("auditName");
						           String auditResultStr =(jaresultList.getJSONObject(i).get("auditResultName")).equals(null)?"没有"+i:(String) jaresultList.getJSONObject(i).get("auditResultName");
						           String auditRemark =(jaresultList.getJSONObject(i).get("auditRemark")).equals(null)?"没有"+i:(String) jaresultList.getJSONObject(i).get("auditRemark");
						           AuditListDetail auditDetail = new  AuditListDetail(auditName, auditResultStr, auditRemark);
						           list.add(auditDetail);
							}
						      Message msg =new Message();
						      adapter =new MyAdapter();
						      msg.what =1;
							   handler.sendMessage(msg);
						} catch (Exception e) {
							e.printStackTrace();
						      Message msg =new Message();
						      adapter =new MyAdapter();
						      msg.what =2;
							   handler.sendMessage(msg);
						}
					}
				super.onPostExecute(result);
			}
		
		
	}
    private Handler handler =new Handler(){
    	@Override
		public void handleMessage(Message msg) {
    		switch (msg.what) {
			case 1:
				listview.setAdapter(adapter);
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "暂无数据", 1).show();
				break;

			default:
				break;
			}
    		
    	};
    };
	private Button audit_listlookmore_app_bt_back;
		
		
	private void initView() {
		listview =(ListView)this.findViewById(R.id.audit_listlookmore_lv);		
		audit_listlookmore_app_bt_back =(Button)this.findViewById(R.id.audit_listlookmore_app_bt_back);
		audit_listlookmore_app_bt_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
	
	
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
				convertView = LayoutInflater.from(AuditListLookMore.this).inflate(R.layout.item_audit_list_lookdetail, null);
			    holder.name=(TextView) convertView.findViewById(R.id.tv_cb_item_audit_list_lookdetail_name);
                holder.result=(TextView)convertView.findViewById(R.id.tv_cb_item_audit_list_lookdetail_result);
                holder.remark=(TextView)convertView.findViewById(R.id.tv_cb_item_audit_list_lookdetail_remark);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
		
		    holder.name.setText(list.get(position).getAuditName());
            holder.result.setText(list.get(position).getAuditResultStr());
            holder.remark.setText(list.get(position).getAuditRemark());
          
            return convertView;
		}

	}

	class ViewHolder {
		TextView name,result,remark;
	}

}
