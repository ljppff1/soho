package com.superdata.soho.activity.approvalcenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.AuditTravelapproval;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;
import com.superdata.soho.view.XListView.IXListViewListener;

/**
 * 出差审批列表
 * @author lj
 *
 * 2014年8月14日
 */
public class TravelApprovalActivity extends BaseActivity implements IXListViewListener {
	private MyAdapter adapter;
	Button travel_app_bt_back;
	private com.superdata.soho.view.XListView listView;
	private Button btn_search;
    //Adapter里面放的List，
    private List<AuditTravelapproval> listtravel =new ArrayList<AuditTravelapproval>();
    //临时的List，用于加载更多 的逻辑处理
    private List<AuditTravelapproval> listtravel1 =new ArrayList<AuditTravelapproval>();
	private SDProgressDialog sdDialog;
	//获取的是第几页的数据
    private int page =1;
    //获取的这页的数量
    private int pagesize =8;
    //用于判断是否修改了值
    private boolean flag2=true;
	//标识是否加载更多 
    private boolean flag =true; 
	private boolean isLastPage=false;
    private int position_value;
    private int showToast =0;
	private EditText travel_app_etsearch;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travel_approval_activity);
		initSharePreferences();
		initView();
		initdata();
	}
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(TravelApprovalActivity.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
	}

	private void initView() {
		travel_app_bt_back=(Button) findViewById(R.id.travel_app_bt_back);
		travel_app_bt_back.setOnClickListener(listener);
		listView=	(com.superdata.soho.view.XListView)this.findViewById(R.id.travel_app_listview);
		listView.setOnItemClickListener(listener1);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		btn_search=(Button)this.findViewById(R.id.travel_app_search);
		btn_search.setOnClickListener(listener);
		travel_app_etsearch =(EditText)this.findViewById(R.id.travel_app_etsearch);
		travel_app_etsearch.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
			   if(keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_UP){
				   initSearch();
			   }
			return false;
			}
		});

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode!=0){
		switch (resultCode) {
		// input
		case 1:
			showToast = 1;
			break;
		case 2:
			showToast = 2;
			break;
		case 3:
			showToast = 3;
			break;
		case 4:
			showToast = 4;
			break;

		default:
			break;
		}
		position_value = Integer.valueOf(data.getExtras().getString("POSITION"));
		flag2 = false;
		initdata( (position_value ) + "", 1 + "", 0 + "",1 + "");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	public void initdata(){
		initdata(page+"",pagesize+"","关键字","1");	
	}
	
	private void initdata(String start, String size, String search,
			String type) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("page", start));
		params.add(new BasicNameValuePair("rows", size));
	//	params.add(new BasicNameValuePair("search",  search));
	//	params.add(new BasicNameValuePair("type",  type));
		new MyTask().execute(params);
	}

	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =com.superdata.soho.config.InterfaceConfig.TRAVELAPPROVAL;
		@Override
        protected void onPreExecute() {
     		if(page==1){
        	sdDialog = new SDProgressDialog(TravelApprovalActivity.this);
    	 	sdDialog.show();
     		}
        	super.onPreExecute();
        }
		@Override
		protected String doInBackground(List<NameValuePair>... params) {
		 String aa = null;
		try {
			aa = new SDHttpClient().post_session(url, params[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return aa;
		}
		
		@Override
		protected void onPostExecute(String result) {
			 if(result!=null){
					try {
						  JSONObject jRoot = new JSONObject(result);
							JSONArray jaresultList =jRoot.getJSONArray("rows");
							if(flag2){
								int total = jRoot.getInt("total");
								if(total>page*pagesize){
									isLastPage =false;
								}else{
									isLastPage =true;
								}
							}
							listtravel1.clear();
							for(int i=0;i<jaresultList.length();i++){
					            String id =(String.valueOf(jaresultList.getJSONObject(i).get("id")).equals(null))?1+"": String.valueOf(jaresultList.getJSONObject(i).get("id"));
								String code =(jaresultList.getJSONObject(i).get("code")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("code");
								String userName =(jaresultList.getJSONObject(i).get("empName")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("empName");
					            String travelStartTime = (jaresultList.getJSONObject(i).get("startDate")).equals(null)?"":((String) jaresultList.getJSONObject(i).get("startDate")).substring(0, 19);
					            String travelEndTime =(jaresultList.getJSONObject(i).get("endDate")).equals(null)?"":((String) jaresultList.getJSONObject(i).get("endDate")).substring(0, 19);			             
								String travelType =(jaresultList.getJSONObject(i).get("typeName")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("typeName");
					            String travelTotalTime =(String.valueOf(jaresultList.getJSONObject(i).get("length")).equals(null))?"": String.valueOf(jaresultList.getJSONObject(i).get("length"))+"小时";
					            String travelapprovetime = (jaresultList.getJSONObject(i).get("applyDate")).equals(null)?"":((String) jaresultList.getJSONObject(i).get("applyDate")).substring(0, 19);
					            String travelapproveroute =(jaresultList.getJSONObject(i).get("route")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("route");
					            String travelReason =(jaresultList.getJSONObject(i).get("remark")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("remark");
					            String travelStatus =(String.valueOf(jaresultList.getJSONObject(i).get("status")).equals(null))?1+"": String.valueOf(jaresultList.getJSONObject(i).get("status"));
                                AuditTravelapproval ata =new AuditTravelapproval(id, code, userName, travelStartTime, travelEndTime, travelType, travelTotalTime, travelapprovetime, travelapproveroute, travelReason, travelStatus);
	                            listtravel1.add(ata);	
							}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		     Message msg =new Message();
		     //用于判断是否修改了值
		     if(flag2){
		     //加载更多 时
		     if(!flag){
		    	 listtravel.addAll(listtravel1);
		    	 listtravel1.clear();
				adapter = new MyAdapter();
		        msg.what =2;
		     }else{
		    	 //第一次
		    	 //重新加载全部的
			    	 listtravel.clear();
			    	 listtravel.addAll(listtravel1);
					 adapter = new MyAdapter();
		           	 msg.what =1;
		     }
		     }else{
			     listtravel.get(position_value-1).settravelStatus(listtravel1.get(0).gettravelStatus());
		    	 flag2 =true;
		    	 adapter =new MyAdapter();
		    	 msg.what=4;
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
				listView.setAdapter(adapter);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				break;
			case 2:
				flag =true;
			    listView.setAdapter(adapter);
			    listView.setSelection(listtravel.size()-pagesize);
				adapter.notifyDataSetChanged();// 数据变化刷新
				if(sdDialog.isShow())
				sdDialog.cancel();
				onLoad();
				break;
			case 3:
				onLoad();
				Toast.makeText(getApplicationContext(), "已全部加载完成", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				listView.setAdapter(adapter);
				listView.setSelection(position_value);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				switch (showToast) {
				//input
				case 1:
					Toast.makeText(TravelApprovalActivity.this, "审批通过成功提交",Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(TravelApprovalActivity.this, "审批终止成功提交",
							Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(TravelApprovalActivity.this, "反审批成功提交",
							Toast.LENGTH_SHORT).show();
	                 break;
				case 4:
					Toast.makeText(TravelApprovalActivity.this, "审批成功提交",
							Toast.LENGTH_SHORT).show();
	                 break;
	                
				default:
					break;
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.travel_app_bt_back:
				onBackPressed();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;
				//搜索监听
			case R.id.travel_app_search:
			   initSearch();
				break;
			default:
				break;
			}
			
		}
	};
	public void initSearch(){
		
	}
	private static int Position =1;
	OnItemClickListener listener1 =new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Position =position;
			initDate(listtravel.get(position-1).getId(),"24",empId,"",companyId);
		}
	};
    public void initDate(String dataId,String dataTypeId,String auditId,String Id,String companyId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("dataId", dataId));
		params.add(new BasicNameValuePair("dataTypeId",dataTypeId));
		params.add(new BasicNameValuePair("auditId",auditId));
		params.add(new BasicNameValuePair("dProcessId",Id));
		params.add(new BasicNameValuePair("companyId",companyId));
		new MyTask1().execute(params);
    }
	class MyTask1 extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =com.superdata.soho.config.InterfaceConfig.HAVINGAUDIT;
		private boolean reAuditOpt;
		private boolean auditOpt;
		private boolean hasProcess;
		@Override
        protected void onPreExecute() {
        	sdDialog = new SDProgressDialog(TravelApprovalActivity.this);
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
					  reAuditOpt =jRoot.getBoolean("reAuditOpt");
					  auditOpt =jRoot.getBoolean("auditOpt");
					  hasProcess =jRoot.getBoolean("hasProcess");

					} catch (Exception e) {
						e.printStackTrace();
						  reAuditOpt =false;
						  auditOpt =false;
					}
				}
			 if(sdDialog.isShow())
				 sdDialog.cancel();
			 
			 if(hasProcess){
			 if(auditOpt){
				 intentAuditView();
			 }else{
				 if(reAuditOpt){
			     intentReAuditView();
				 }else{
				 intentDetailView();
				 }
			 }
			 }else{
				 intentsingleView();
			 }

		}
		private void intentsingleView() {
			Intent intent =	new Intent(getApplicationContext(), TravelApprovalSingleAuditActivity.class);
			intent.putExtra("NAME", listtravel.get(Position-1).getUserName());
			intent.putExtra("TIME", listtravel.get(Position-1).gettravelapprovetime());
			intent.putExtra("TYPE", listtravel.get(Position-1).gettravelType());
			intent.putExtra("STARTTIME", listtravel.get(Position-1).gettravelStartTime());
			intent.putExtra("ENDTIME", listtravel.get(Position-1).gettravelEndTime());
			intent.putExtra("TIMELONG", listtravel.get(Position-1).gettravelTotalTime());
			intent.putExtra("REASON", listtravel.get(Position-1).gettravelReason());
			intent.putExtra("AUDITSTATUS", listtravel.get(Position-1).gettravelStatus());
			intent.putExtra("AUDITPEOPLE", "lj");
			intent.putExtra("FLAG", auditOpt);
			intent.putExtra("ROUTE", listtravel.get(Position-1).gettravelapproveroute());
			intent.putExtra("POSITION", Position);
			intent.putExtra("ID",listtravel.get(Position-1).getId() );
		    startActivityForResult(intent, 1);

			
		}
		private void intentDetailView() {
			Intent intent =	new Intent(getApplicationContext(), TravelApprovalDetailActivity.class);
			intent.putExtra("NAME", listtravel.get(Position-1).getUserName());
			intent.putExtra("TIME", listtravel.get(Position-1).gettravelapprovetime());
			intent.putExtra("TYPE", listtravel.get(Position-1).gettravelType());
			intent.putExtra("STARTTIME", listtravel.get(Position-1).gettravelStartTime());
			intent.putExtra("ENDTIME", listtravel.get(Position-1).gettravelEndTime());
			intent.putExtra("TIMELONG", listtravel.get(Position-1).gettravelTotalTime());
			intent.putExtra("REASON", listtravel.get(Position-1).gettravelReason());
			intent.putExtra("AUDITSTATUS", listtravel.get(Position-1).gettravelStatus());
			intent.putExtra("AUDITPEOPLE", "lj");
			intent.putExtra("ROUTE", listtravel.get(Position-1).gettravelapproveroute());
			intent.putExtra("POSITION", Position);
			intent.putExtra("ID",listtravel.get(Position-1).getId() );

			startActivity(intent);
		}
		private void intentReAuditView() {
			Intent intent =	new Intent(getApplicationContext(), TravelApprovalBackAuditActivity.class);
			intent.putExtra("NAME", listtravel.get(Position-1).getUserName());
			intent.putExtra("TIME", listtravel.get(Position-1).gettravelapprovetime());
			intent.putExtra("TYPE", listtravel.get(Position-1).gettravelType());
			intent.putExtra("STARTTIME", listtravel.get(Position-1).gettravelStartTime());
			intent.putExtra("ENDTIME", listtravel.get(Position-1).gettravelEndTime());
			intent.putExtra("TIMELONG", listtravel.get(Position-1).gettravelTotalTime());
			intent.putExtra("REASON", listtravel.get(Position-1).gettravelReason());
			intent.putExtra("AUDITSTATUS", listtravel.get(Position-1).gettravelStatus());
			intent.putExtra("AUDITPEOPLE", "lj");
			intent.putExtra("ROUTE", listtravel.get(Position-1).gettravelapproveroute());
			intent.putExtra("POSITION", Position);
			intent.putExtra("ID",listtravel.get(Position-1).getId() );
		    startActivityForResult(intent, 1);
		}
		private void intentAuditView() {
			Intent intent =	new Intent(getApplicationContext(), TravelApprovalAuditActivity.class);
			intent.putExtra("NAME", listtravel.get(Position-1).getUserName());
			intent.putExtra("TIME", listtravel.get(Position-1).gettravelapprovetime());
			intent.putExtra("TYPE", listtravel.get(Position-1).gettravelType());
			intent.putExtra("STARTTIME", listtravel.get(Position-1).gettravelStartTime());
			intent.putExtra("ENDTIME", listtravel.get(Position-1).gettravelEndTime());
			intent.putExtra("TIMELONG", listtravel.get(Position-1).gettravelTotalTime());
			intent.putExtra("REASON", listtravel.get(Position-1).gettravelReason());
			intent.putExtra("AUDITSTATUS", listtravel.get(Position-1).gettravelStatus());
			intent.putExtra("AUDITPEOPLE", "lj");
			intent.putExtra("ROUTE", listtravel.get(Position-1).gettravelapproveroute());
			intent.putExtra("POSITION", Position);
			intent.putExtra("ID",listtravel.get(Position-1).getId() );
		    startActivityForResult(intent, 1);
		}
	}


	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return listtravel.size();
		}

		@Override
		public Object getItem(int position) {
			return listtravel.get(position);
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
				convertView = LayoutInflater.from(TravelApprovalActivity.this).inflate(R.layout.item_travel_approve, null);
			    holder.name=(TextView) convertView.findViewById(R.id.tv_cb_item_travel_approve_name);
                holder.timelong=(TextView)convertView.findViewById(R.id.tv_cb_item_travel_approve_timelong);
                holder.starttime=(TextView)convertView.findViewById(R.id.tv_cb_item_travel_approve_startdate);
                holder.reason=(TextView)convertView.findViewById(R.id.tv_cb_item_travel_approve_reason);
                holder.status=(TextView)convertView.findViewById(R.id.tv_cb_item_travel_approve_status);
                holder.approvetime=(TextView)convertView.findViewById(R.id.tv_cb_item_travel_approve_time);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
		    holder.name.setText(listtravel.get(position).getUserName());
            holder.timelong.setText(listtravel.get(position).gettravelTotalTime());
            holder.starttime.setText(listtravel.get(position).gettravelStartTime());
            holder.reason.setText(listtravel.get(position).gettravelReason());
            if(listtravel.get(position).gettravelStatus().equals("0")){
            holder.status.setText(R.string.AUDIT_STATUS_0);
            }else{
            holder.status.setText(R.string.AUDIT_STATUS_1);
            }
            holder.approvetime.setText(listtravel.get(position).gettravelapprovetime());

            return convertView;
		}

	}

	class ViewHolder {
		TextView name,timelong,starttime,reason,status,approvetime;
	}

	@Override
	public void onRefresh() {
		page = 1;
		initdata("1",pagesize+"","关键字","1");
		onLoad();
	}

	@Override
	public void onLoadMore() {
		if(!isLastPage){
		if(flag){
			flag =false;
		++page;
		initdata(page+"", pagesize+"","关键字","1");
		}
		}else{
			handler.sendEmptyMessage(3);
		}
	}

}
