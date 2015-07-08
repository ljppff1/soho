package com.superdata.soho.activity.approvalcenter;

import java.text.DecimalFormat;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.AuditCostReportApproval;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;
import com.superdata.soho.view.XListView.IXListViewListener;

/**
 * 费用支出列表
 * @author lj
 *
 * 2014年8月14日
 */
public class CostReportActivity extends BaseActivity implements IXListViewListener {
	private MyAdapter adapter;
	Button cost_report_app_bt_back;
	private PopupWindow popupwindow;
    private int Kind=0;
	private com.superdata.soho.view.XListView listView;
	private Button btn_search;
    //Adapter里面放的List，
    private List<AuditCostReportApproval> listcost_report =new ArrayList<AuditCostReportApproval>();
    //临时的List，用于加载更多 的逻辑处理
    private List<AuditCostReportApproval> listcost_report1 =new ArrayList<AuditCostReportApproval>();
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
    private int position_value;
    //用来回显从审批界面跳过来后，相关的审批结果的提示
    private int showToast =0;
    //记录的一个位置，用于刷新当前条目 
	private int Position;
	//搜索框
	private EditText cost_report_app_etsearch;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_report_activity);
		initSharePreferences();
		initView();
		initdata();
		
		
	}
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(CostReportActivity.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
	}

	private void initView() {
		cost_report_app_bt_back=(Button) findViewById(R.id.cost_report_app_bt_back);
		cost_report_app_bt_back.setOnClickListener(listener);
		listView=	(com.superdata.soho.view.XListView)this.findViewById(R.id.cost_report_app_listview);
		listView.setOnItemClickListener(listener1);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		btn_search=(Button)this.findViewById(R.id.cost_report_app_search);
		btn_search.setOnClickListener(listener);
		cost_report_app_etsearch =(EditText)this.findViewById(R.id.cost_report_app_etsearch);
		cost_report_app_etsearch.setOnKeyListener(new OnKeyListener() {
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
//		params.add(new BasicNameValuePair("keyword",  search));
//		params.add(new BasicNameValuePair("type",  type));
		new MyTask().execute(params);
	}

	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =com.superdata.soho.config.InterfaceConfig.COSTREPORTAPPROVAL;
		@Override
        protected void onPreExecute() {
     		if(page==1){
        	sdDialog = new SDProgressDialog(CostReportActivity.this);
    	 	sdDialog.show();
     		}
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
						listcost_report1.clear();
						DecimalFormat df = new DecimalFormat("0.00");
						for(int i=0;i<jaresultList.length();i++){
				            Integer id =(Integer) (((jaresultList.getJSONObject(i).get("id")).equals(null))?1: jaresultList.getJSONObject(i).get("id"));
					           String code =(jaresultList.getJSONObject(i).get("code")).equals(null)?"aaaa"+i:(String) jaresultList.getJSONObject(i).get("code");
					           String createname =(jaresultList.getJSONObject(i).get("createName")).equals(null)?""+i:(String) jaresultList.getJSONObject(i).get("createName");
					           String empname =(jaresultList.getJSONObject(i).get("empName")).equals(null)?""+i:(String) jaresultList.getJSONObject(i).get("empName");
					           String applydate = (jaresultList.getJSONObject(i).get("applyDate")).equals(null)?"":((String) jaresultList.getJSONObject(i).get("applyDate"));
					           Double amount1 =(Double) (((jaresultList.getJSONObject(i).get("amount")).equals(null))?"": jaresultList.getJSONObject(i).get("amount"));
					           String amount = df.format(amount1)+"元";
						       String name =(jaresultList.getJSONObject(i).get("name")).equals(null)?""+i:(String) jaresultList.getJSONObject(i).get("name");
						       String remark =(jaresultList.getJSONObject(i).get("remark")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("remark");
						       Integer status =(Integer) (((jaresultList.getJSONObject(i).get("status")).equals(null))?"0": jaresultList.getJSONObject(i).get("status"));
							   String auditname =(jaresultList.getJSONObject(i).get("auditName")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("auditName");

                                    AuditCostReportApproval acra =new AuditCostReportApproval(id+"", code, createname, empname, applydate, amount+"", name, remark, status+"",auditname);
                                    		listcost_report1.add(acra);	
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
		    	 listcost_report.addAll(listcost_report1);
		    	 listcost_report1.clear();
				adapter = new MyAdapter();
		        msg.what =2;
		     }else{
		    	 //第一次
		    	 //重新加载全部的
			    	 listcost_report.clear();
			    	 listcost_report.addAll(listcost_report1);
					 adapter = new MyAdapter();
		           	 msg.what =1;
		     }
		     }else{
			    	 listcost_report.get(position_value-1).setAuditname(listcost_report1.get(0).getAuditname());
			    	 listcost_report.get(position_value-1).setStatus(listcost_report1.get(0).getStatus());
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
			    listView.setSelection(listcost_report.size()-pagesize);
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
					Toast.makeText(CostReportActivity.this, "审批通过成功提交",Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(CostReportActivity.this, "审批终止成功提交",
							Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(CostReportActivity.this, "反审批成功提交",
							Toast.LENGTH_SHORT).show();
	                 break;
				case 4:
					Toast.makeText(CostReportActivity.this, "审批成功提交",
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
			case R.id.cost_report_app_bt_back:
				onBackPressed();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;
				//搜索监听
			case R.id.cost_report_app_search:
			    initSearch();
				break;
			default:
				break;
			}
			
		}
	};
	public void initSearch(){
		
	}
	OnItemClickListener listener1 =new OnItemClickListener() {


		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Position =position;
			initDate(listcost_report.get(position-1).getId(),"34",empId,"",companyId);
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
	        	sdDialog = new SDProgressDialog(CostReportActivity.this);
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
				 //有流程
				 if(hasProcess){
				//有审批权限
				 if(auditOpt){
					 intentAuditView();
				 }else{
					 //有反审批权限
					 if(reAuditOpt){
				     intentReAuditView();
					 }else{
					//都没有
					 intentDetailView();
					 }
				 }
				 }else{
					 //单步流程
					 intentsingleView(auditOpt);
				 }

	
			}
			private void intentsingleView(Boolean auditOpt) {
				this.auditOpt =auditOpt;
				Intent intent =new Intent(getApplicationContext(), CostReportSingleAuditActivity.class);
				intent.putExtra("ID", listcost_report.get(Position-1).getId());
				intent.putExtra("CODE", listcost_report.get(Position-1).getCode());
				intent.putExtra("CREATENAME",listcost_report.get(Position-1).getCreatename());
				intent.putExtra("EMPNAME",listcost_report.get(Position-1).getEmpname());
				intent.putExtra("APPLYDATE",listcost_report.get(Position-1).getApplydate());
				intent.putExtra("AMOUNT",listcost_report.get(Position-1).getAmount());
				intent.putExtra("NAME",listcost_report.get(Position-1).getName());
				intent.putExtra("REMARK",listcost_report.get(Position-1).getRemark());
				intent.putExtra("STATUS",listcost_report.get(Position-1).getStatus());
				intent.putExtra("AUDIT",listcost_report.get(Position-1).getAuditname());
				intent.putExtra("FLAG", auditOpt);
				intent.putExtra("POSITION", Position+"");
			    startActivityForResult(intent, 1);

			}
			private void intentDetailView() {
				Intent intent =	new Intent(getApplicationContext(), CostReportDetailActivity.class);
				intent.putExtra("ID", listcost_report.get(Position-1).getId());
				intent.putExtra("CODE", listcost_report.get(Position-1).getCode());
				intent.putExtra("CREATENAME",listcost_report.get(Position-1).getCreatename());
				intent.putExtra("EMPNAME",listcost_report.get(Position-1).getEmpname());
				intent.putExtra("APPLYDATE",listcost_report.get(Position-1).getApplydate());
				intent.putExtra("AMOUNT",listcost_report.get(Position-1).getAmount());
				intent.putExtra("NAME",listcost_report.get(Position-1).getName());
				intent.putExtra("REMARK",listcost_report.get(Position-1).getRemark());
				intent.putExtra("AUDIT",listcost_report.get(Position-1).getAuditname());
				intent.putExtra("STATUS",listcost_report.get(Position-1).getStatus());

				startActivity(intent);
			}
			private void intentReAuditView() {
				Intent intent =new Intent(getApplicationContext(), CostReportBackAuditActivity.class);
				intent.putExtra("ID", listcost_report.get(Position-1).getId());
				intent.putExtra("CODE", listcost_report.get(Position-1).getCode());
				intent.putExtra("CREATENAME",listcost_report.get(Position-1).getCreatename());
				intent.putExtra("EMPNAME",listcost_report.get(Position-1).getEmpname());
				intent.putExtra("APPLYDATE",listcost_report.get(Position-1).getApplydate());
				intent.putExtra("AMOUNT",listcost_report.get(Position-1).getAmount());
				intent.putExtra("NAME",listcost_report.get(Position-1).getName());
				intent.putExtra("REMARK",listcost_report.get(Position-1).getRemark());
				intent.putExtra("STATUS",listcost_report.get(Position-1).getStatus());
				intent.putExtra("AUDIT",listcost_report.get(Position-1).getAuditname());
				intent.putExtra("POSITION", Position+"");
			    startActivityForResult(intent, 1);
			}
			private void intentAuditView() {
				Intent intent =new Intent(getApplicationContext(), CostReportAuditActivity.class);
				intent.putExtra("ID", listcost_report.get(Position-1).getId());
				intent.putExtra("CODE", listcost_report.get(Position-1).getCode());
				intent.putExtra("CREATENAME",listcost_report.get(Position-1).getCreatename());
				intent.putExtra("EMPNAME",listcost_report.get(Position-1).getEmpname());
				intent.putExtra("APPLYDATE",listcost_report.get(Position-1).getApplydate());
				intent.putExtra("AMOUNT",listcost_report.get(Position-1).getAmount());
				intent.putExtra("NAME",listcost_report.get(Position-1).getName());
				intent.putExtra("AUDIT",listcost_report.get(Position-1).getAuditname());
				intent.putExtra("REMARK",listcost_report.get(Position-1).getRemark());
				intent.putExtra("STATUS",listcost_report.get(Position-1).getStatus());
				intent.putExtra("POSITION", Position+"");
			    startActivityForResult(intent, 1);
			}
		}
	

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return listcost_report.size();
		}

		@Override
		public Object getItem(int position) {
			return listcost_report.get(position);
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
				convertView = LayoutInflater.from(CostReportActivity.this).inflate(R.layout.item_cost_report_approve, null);
			    holder.name=(TextView) convertView.findViewById(R.id.tv_cb_item_cost_report_approve_name);
                holder.money=(TextView)convertView.findViewById(R.id.tv_cb_item_cost_report_approve_money);
                holder.theme=(TextView)convertView.findViewById(R.id.tv_cb_item_cost_report_approve_theme);
                holder.remark=(TextView)convertView.findViewById(R.id.tv_cb_item_cost_report_approve_remark);
                holder.status=(TextView)convertView.findViewById(R.id.tv_cb_item_cost_report_approve_status);
                holder.approvetime=(TextView)convertView.findViewById(R.id.tv_cb_item_cost_report_approve_time);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
		    holder.name.setText(listcost_report.get(position).getCreatename());
            holder.money.setText(listcost_report.get(position).getAmount());
            holder.theme.setText(listcost_report.get(position).getName());
            holder.remark.setText(listcost_report.get(position).getRemark());
            if(listcost_report.get(position).getStatus().equals("0")){
            holder.status.setText(R.string.AUDIT_STATUS_0);
            }else{
            	 holder.status.setText(R.string.AUDIT_STATUS_1);
            }
            holder.approvetime.setText(listcost_report.get(position).getApplydate());
        return convertView;
		}

	}

	class ViewHolder {
		TextView name,money,theme,remark,status,approvetime;
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
