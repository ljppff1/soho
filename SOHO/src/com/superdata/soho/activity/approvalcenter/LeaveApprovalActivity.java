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
import com.superdata.soho.entity.AuditLeaveApproval;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;
import com.superdata.soho.view.XListView.IXListViewListener;

/**
 * 请假列表
 * @ClassName LeaveApprovalActivity
 * @author Administrator
 * @date 2014年7月8日 下午1:42:35
 *
 */
public class LeaveApprovalActivity extends BaseActivity implements IXListViewListener {
	private MyAdapter adapter;
	Button leave_app_bt_back;
	private com.superdata.soho.view.XListView listView;
	private Button btn_search;
    //Adapter里面放的List，
    private List<AuditLeaveApproval> listleaveapproval =new ArrayList<AuditLeaveApproval>();
    //临时的List，用于加载更多 的逻辑处理
    private List<AuditLeaveApproval> listleaveapproval1 =new ArrayList<AuditLeaveApproval>();
	private SDProgressDialog sdDialog;
	//获取的是第几页的数据
    private int page =1;
    //获取的这页的数量
    private int pagesize =8;
    //用于判断是否修改了值
    private boolean flag2=true;
	//标识是否加载更多 
    private boolean flag =true; 
    //是否最后一页
	private boolean isLastPage=false;
	//用于刷新审批后的当前的这条的对应Position的位置的记录
    private int position_value;
    //显示审批后的具体的某某提示，
    private int showToast =0;
    //搜索框
	private EditText costjob_etsearch;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveapproval_activity);
		initSharePreferences();
		initView();
		initdata();
	}
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(LeaveApprovalActivity.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
	}

	private void initView() {
		leave_app_bt_back=(Button) findViewById(R.id.leave_app_bt_back);
		leave_app_bt_back.setOnClickListener(listener);
		listView=	(com.superdata.soho.view.XListView)this.findViewById(R.id.leave_app_listview);
		listView.setOnItemClickListener(listener1);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		btn_search=(Button)this.findViewById(R.id.leave_app_search);
		btn_search.setOnClickListener(listener);
		costjob_etsearch =(EditText)this.findViewById(R.id.costjob_etsearch);
		costjob_etsearch.setOnKeyListener(new OnKeyListener() {
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
			showToast =4;

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
	/**
	 * 请假
	 * @param start
	 * @param size
	 * @param search
	 * @param type
	 */
	private void initdata(String start, String size, String search,
			String type) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("page", start));
		params.add(new BasicNameValuePair("rows", size));
	//	params.add(new BasicNameValuePair("search",  search));
		//params.add(new BasicNameValuePair("type",  type));
		new MyTask().execute(params);
	}

	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =com.superdata.soho.config.InterfaceConfig.LEAVERECORD;
		@Override
        protected void onPreExecute() {
     		if(page==1){
        	sdDialog = new SDProgressDialog(LeaveApprovalActivity.this);
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
			// TODO Auto-generated catch block
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
						listleaveapproval1.clear();
						for(int i=0;i<jaresultList.length();i++){
				            String id =(String.valueOf(jaresultList.getJSONObject(i).get("id")).equals(null))?1+"": String.valueOf(jaresultList.getJSONObject(i).get("id"));
							String code =(jaresultList.getJSONObject(i).get("code")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("code");
							String userName =(jaresultList.getJSONObject(i).get("empName")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("empName");
				            String leaveRecordStartTime = (jaresultList.getJSONObject(i).get("startDate")).equals(null)?"":((String) jaresultList.getJSONObject(i).get("startDate")).substring(0, 19);
				            String leaveRecordEndTime =(jaresultList.getJSONObject(i).get("endDate")).equals(null)?"":((String) jaresultList.getJSONObject(i).get("endDate")).substring(0, 19);			             
							String leaveRecordType =(jaresultList.getJSONObject(i).get("typeName")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("typeName");
				            String leaveRecordTotalTime =(String.valueOf(jaresultList.getJSONObject(i).get("length")).equals(null))?"": String.valueOf(jaresultList.getJSONObject(i).get("length"))+"小时";
				            String leaveRecordapprovetime = (jaresultList.getJSONObject(i).get("applyDate")).equals(null)?"":((String) jaresultList.getJSONObject(i).get("applyDate")).substring(0, 19);
							String leaveRecordReason =(jaresultList.getJSONObject(i).get("remark")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("remark");
				            String leaveRecordStatus = (String.valueOf(jaresultList.getJSONObject(i).get("status")).equals(null))?2+"": String.valueOf(jaresultList.getJSONObject(i).get("status"));
                            AuditLeaveApproval ala =new AuditLeaveApproval(id, code, userName, leaveRecordStartTime, leaveRecordEndTime, leaveRecordType, leaveRecordTotalTime, leaveRecordapprovetime, leaveRecordReason, leaveRecordStatus,"");
		                    listleaveapproval1.add(ala);	
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
		    	 listleaveapproval.addAll(listleaveapproval1);
		    	 listleaveapproval1.clear();
				adapter = new MyAdapter();
		        msg.what =2;
		     }else{
		    	 //第一次
		    	 //重新加载全部的
			    	 listleaveapproval.clear();
			    	 listleaveapproval.addAll(listleaveapproval1);
					 adapter = new MyAdapter();
		           	 msg.what =1;
		     }
		     }else{
		    	 //刷新审批成功后的当前条的这个状态
			    listleaveapproval.get(position_value-1).setLeaveRecordStatus(listleaveapproval1.get(0).getLeaveRecordStatus());
		    	  flag2 =true;
		    	 adapter = new MyAdapter();
	           	 msg.what =4;
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
			    listView.setSelection(listleaveapproval.size()-pagesize);
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
					Toast.makeText(LeaveApprovalActivity.this, "审批通过成功提交",Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(LeaveApprovalActivity.this, "审批终止成功提交",
							Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(LeaveApprovalActivity.this, "反审批成功提交",
							Toast.LENGTH_SHORT).show();
	                 break;
				case 4:
					Toast.makeText(LeaveApprovalActivity.this, "审批成功提交",
							Toast.LENGTH_SHORT).show();
	                 break;

				default:
					break;
				}
				onLoad();
				
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
			case R.id.leave_app_bt_back:
				onBackPressed();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;
				//搜索监听
			case R.id.leave_app_search:
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
			initDate(listleaveapproval.get(position-1).getId(),"24",empId,"",companyId);
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
	        	sdDialog = new SDProgressDialog(LeaveApprovalActivity.this);
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
				Intent intent =new Intent(LeaveApprovalActivity.this, LeaveApprovalSingleAuditActivity.class);
				intent.putExtra("NAME", listleaveapproval.get(Position-1).getUserName());
				intent.putExtra("TIME", listleaveapproval.get(Position-1).getLeaveRecordapprovetime());
				intent.putExtra("TYPE", listleaveapproval.get(Position-1).getLeaveRecordType());
				intent.putExtra("STARTTIME", listleaveapproval.get(Position-1).getLeaveRecordStartTime());
				intent.putExtra("ENDTIME", listleaveapproval.get(Position-1).getLeaveRecordEndTime());
				intent.putExtra("TIMELONG", listleaveapproval.get(Position-1).getLeaveRecordTotalTime());
				intent.putExtra("REASON", listleaveapproval.get(Position-1).getLeaveRecordReason());
				intent.putExtra("AUDITSTATUS", listleaveapproval.get(Position-1).getLeaveRecordStatus());
				intent.putExtra("FLAG", auditOpt);
				intent.putExtra("AUDITPEOPLE", "aa");
				intent.putExtra("POSITION", Position+"");
				intent.putExtra("ID",listleaveapproval.get(Position-1).getId() );
			    startActivityForResult(intent, 1);
			}
			
			private void intentDetailView() {
				Intent intent =	new Intent(getApplicationContext(), LeaveApprovalDetailActivity.class);
				intent.putExtra("NAME", listleaveapproval.get(Position-1).getUserName());
				intent.putExtra("TIME", listleaveapproval.get(Position-1).getLeaveRecordapprovetime());
				intent.putExtra("TYPE", listleaveapproval.get(Position-1).getLeaveRecordType());
				intent.putExtra("STARTTIME", listleaveapproval.get(Position-1).getLeaveRecordStartTime());
				intent.putExtra("ENDTIME", listleaveapproval.get(Position-1).getLeaveRecordEndTime());
				intent.putExtra("TIMELONG", listleaveapproval.get(Position-1).getLeaveRecordTotalTime());
				intent.putExtra("REASON", listleaveapproval.get(Position-1).getLeaveRecordReason());
				intent.putExtra("AUDITSTATUS", listleaveapproval.get(Position-1).getLeaveRecordStatus());
				intent.putExtra("ID",listleaveapproval.get(Position-1).getId() );
				startActivity(intent);
			}
			
			private void intentReAuditView() {
				Intent intent =new Intent(LeaveApprovalActivity.this, LeaveApprovalBackAuditActivity.class);
				intent.putExtra("NAME", listleaveapproval.get(Position-1).getUserName());
				intent.putExtra("TIME", listleaveapproval.get(Position-1).getLeaveRecordapprovetime());
				intent.putExtra("TYPE", listleaveapproval.get(Position-1).getLeaveRecordType());
				intent.putExtra("STARTTIME", listleaveapproval.get(Position-1).getLeaveRecordStartTime());
				intent.putExtra("ENDTIME", listleaveapproval.get(Position-1).getLeaveRecordEndTime());
				intent.putExtra("TIMELONG", listleaveapproval.get(Position-1).getLeaveRecordTotalTime());
				intent.putExtra("REASON", listleaveapproval.get(Position-1).getLeaveRecordReason());
				intent.putExtra("AUDITSTATUS", listleaveapproval.get(Position-1).getLeaveRecordStatus());
				intent.putExtra("AUDITPEOPLE", "aa");
				intent.putExtra("POSITION", Position+"");
		     	intent.putExtra("ID",listleaveapproval.get(Position-1).getId() );
			    startActivityForResult(intent, 1);
			}
			private void intentAuditView() {
				Intent intent =new Intent(LeaveApprovalActivity.this, LeaveApprovalAuditActivity.class);
				intent.putExtra("NAME", listleaveapproval.get(Position-1).getUserName());
				intent.putExtra("TIME", listleaveapproval.get(Position-1).getLeaveRecordapprovetime());
				intent.putExtra("TYPE", listleaveapproval.get(Position-1).getLeaveRecordType());
				intent.putExtra("STARTTIME", listleaveapproval.get(Position-1).getLeaveRecordStartTime());
				intent.putExtra("ENDTIME", listleaveapproval.get(Position-1).getLeaveRecordEndTime());
				intent.putExtra("TIMELONG", listleaveapproval.get(Position-1).getLeaveRecordTotalTime());
				intent.putExtra("REASON", listleaveapproval.get(Position-1).getLeaveRecordReason());
				intent.putExtra("AUDITSTATUS", listleaveapproval.get(Position-1).getLeaveRecordStatus());
				intent.putExtra("AUDITPEOPLE", "aa");
				intent.putExtra("POSITION", Position+"");
				intent.putExtra("ID",listleaveapproval.get(Position-1).getId() );
			    startActivityForResult(intent, 1);
			}
		}

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return listleaveapproval.size();
		}

		@Override
		public Object getItem(int position) {
			return listleaveapproval.get(position);
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
				convertView = LayoutInflater.from(LeaveApprovalActivity.this).inflate(R.layout.item_leave_approve, null);
			    holder.name=(TextView) convertView.findViewById(R.id.tv_cb_item_leave_approve_name);
                holder.timelong=(TextView)convertView.findViewById(R.id.tv_cb_item_leave_approve_timelong);
                holder.starttime=(TextView)convertView.findViewById(R.id.tv_cb_item_leave_approve_startdate);
                holder.reason=(TextView)convertView.findViewById(R.id.tv_cb_item_leave_approve_reason);
                holder.status=(TextView)convertView.findViewById(R.id.tv_cb_item_leave_approve_status);
                holder.approvetime=(TextView)convertView.findViewById(R.id.tv_cb_item_leave_approve_time);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
		    holder.name.setText(listleaveapproval.get(position).getUserName());
            holder.timelong.setText(listleaveapproval.get(position).getLeaveRecordTotalTime());
            holder.starttime.setText(listleaveapproval.get(position).getLeaveRecordStartTime());
            holder.reason.setText(listleaveapproval.get(position).getLeaveRecordReason());
            if(listleaveapproval.get(position).getLeaveRecordStatus().equals("0")){
            holder.status.setText(R.string.AUDIT_STATUS_0);
            }else if(listleaveapproval.get(position).getLeaveRecordStatus().equals("1")){
            holder.status.setText(R.string.AUDIT_STATUS_1);
            }
            holder.approvetime.setText(listleaveapproval.get(position).getLeaveRecordapprovetime());
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
