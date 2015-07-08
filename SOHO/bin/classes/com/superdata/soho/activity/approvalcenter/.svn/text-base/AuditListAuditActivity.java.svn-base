package com.superdata.soho.activity.approvalcenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.AuditListDetail;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;

/**
 * 费用支出审批列表
 * @author lj
 *
 * 2014年8月14日
 */
public class AuditListAuditActivity extends BaseActivity  {
	//进度
	private SDProgressDialog sdDialog;
	private ListView listview;
	private MyAdapter adapter;
	//返回
	private TextView audit_list_lookdetail_bt_back;
	//反审批权限
	private boolean reAuditOpt =false;
	//审批权限
	private boolean auditOpt = false;
	//是否有流程
	private boolean hasProcess =false;
    private List<AuditListDetail> list =new ArrayList<AuditListDetail>();
    //取消
	private Button audit_list_lookdetail_app_audit_btncancel;
	//终止 
	private Button audit_list_lookdetail_app_audit_btnback;
	//通过
	private Button audit_list_lookdetail_app_audit_btnpass;
	//反审批
	private Button audit_list_lookdetail_app_audit_btnreturn;
	//审批 单步的
	private Button audit_list_lookdetail_app_audit_btnaudit;
    //控制我的意见是否显示出来
	private LinearLayout audit_list_lookdetail_app_audit_ll_auditremark;
	//用来刷新某条的，但由于现在当审批有流程且通过时，会在其后生成一条新的，所以暂时无法刷新当前条，保留字段处理用！
	private String Position;
	//是否通过，true为通过
	private Boolean flag1;
	//单据ID
	private String DataId;
	//单据类型类型ID
	private String DataTypeId;
	//审批中心的所有记录都有一个固定的ID
	private String Id;
	//标识一下解析Json时是否出异常
	private boolean flag;
	//审批是否完成
	private boolean flagisover =false;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audit_list_audit_activity);
		initSharePreferences();
		initData();
		initView();
		isAudit();		
	}
	
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(AuditListAuditActivity.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
	}

  private void initData() {
	Position =  getIntent().getExtras().getString("POSITION");
	DataId =getIntent().getExtras().get("DATAID")+"";
	DataTypeId =getIntent().getExtras().get("DATATYPEID")+"";
	Id =getIntent().getExtras().get("ID")+"";
	}


/**
   * 调用接口是否有审批权限
   */
	private void isAudit() {
	initDate(DataId,DataTypeId,empId,Id,companyId);
	}
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
		@Override
        protected void onPreExecute() {
        	sdDialog = new SDProgressDialog(AuditListAuditActivity.this);
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
					}
				}
			 //有流程
			 if(hasProcess){
		initdata();
		     //没有流程
			 }else{
				 otherView();
			 }
		
		}
	}
    
	private void otherView() {
		//没有流程
		audit_list_lookdetail_app_audit_btnaudit.setVisibility(View.VISIBLE);
		audit_list_lookdetail_app_audit_btncancel.setVisibility(View.VISIBLE);
         
	}

    /**
     * 底端显示哪些
     */
	private void showBottom() {
		
		//是否审批结束
		if(!flagisover){
		

			if(flag){
			
		//有反审批
		if(reAuditOpt){
			audit_list_lookdetail_app_audit_btnreturn.setVisibility(View.VISIBLE);
			audit_list_lookdetail_app_audit_btncancel.setVisibility(View.VISIBLE);
		}
		//有审批
		if(auditOpt){
			audit_list_lookdetail_app_audit_ll_auditremark.setVisibility(View.VISIBLE);
			audit_list_lookdetail_app_audit_btncancel.setVisibility(View.VISIBLE);
			audit_list_lookdetail_app_audit_btnpass.setVisibility(View.VISIBLE);
			audit_list_lookdetail_app_audit_btnback.setVisibility(View.VISIBLE);
		}
		}else{
			audit_list_lookdetail_app_audit_ll_auditremark.setVisibility(View.VISIBLE);
			audit_list_lookdetail_app_audit_btncancel.setVisibility(View.VISIBLE);
			audit_list_lookdetail_app_audit_btnpass.setVisibility(View.VISIBLE);
			audit_list_lookdetail_app_audit_btnback.setVisibility(View.VISIBLE);
		}
		
		
		}
	}
	

    
	private void initView() {
		initView_Bottom();
		audit_list_lookdetail_bt_back =(TextView)this.findViewById(R.id.audit_list_lookdetail_bt_back);
		audit_list_lookdetail_bt_back.setOnClickListener(listener);
		listview =(ListView)this.findViewById(R.id.audit_list_lookdetail_app_listview);
	}

    private void initView_Bottom() {
    	audit_list_lookdetail_app_audit_ll_auditremark =(LinearLayout)this.findViewById(R.id.audit_list_lookdetail_app_audit_ll_auditremark);
    	audit_list_lookdetail_app_audit_btncancel =(Button)this.findViewById(R.id.audit_list_lookdetail_app_audit_btncancel);
    	audit_list_lookdetail_app_audit_btnback =(Button)this.findViewById(R.id.audit_list_lookdetail_app_audit_btnback);
    	audit_list_lookdetail_app_audit_btnpass =(Button)this.findViewById(R.id.audit_list_lookdetail_app_audit_btnpass);
    	audit_list_lookdetail_app_audit_btnreturn =(Button)this.findViewById(R.id.audit_list_lookdetail_app_audit_btnreturn);
    	audit_list_lookdetail_app_audit_btnaudit =(Button)this.findViewById(R.id.audit_list_lookdetail_app_audit_btnaudit);
    	audit_list_lookdetail_app_audit_btnaudit.setOnClickListener(listener);
    	audit_list_lookdetail_app_audit_btncancel.setOnClickListener(listener);
    	audit_list_lookdetail_app_audit_btnback.setOnClickListener(listener);
    	audit_list_lookdetail_app_audit_btnpass.setOnClickListener(listener);
    	audit_list_lookdetail_app_audit_btnreturn.setOnClickListener(listener);
    	
    }

	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			//返回
			case R.id.audit_list_lookdetail_bt_back:
				onBackPressed();
				break;
			//取消
			case R.id.audit_list_lookdetail_app_audit_btncancel:
				onBackPressed();
				break;
			//终止
			case R.id.audit_list_lookdetail_app_audit_btnback:
				flag1 =false;
				initdata(DataId, DataTypeId, empId,0+"", "asdfawoefjapwef");
				break;
			//通过
			case R.id.audit_list_lookdetail_app_audit_btnpass:
				flag1=true;
				initdata(DataId, DataTypeId, empId, 1+"", "asdfawoefjapwef");
				break;
			//反审批
			case R.id.audit_list_lookdetail_app_audit_btnreturn:
				initdata1(DataId, DataTypeId);
				break;
			//不走流程的审批
			case R.id.audit_list_lookdetail_app_audit_btnaudit:
				initdata3(DataId, DataTypeId,empId);
				break;

				
			default:
				break;
			}
			
		}
	};
	/**
	 * 单步审批 
	 * @param dataId    单据id
	 * @param dataTypeId    单据类型id
	 */
	private void initdata3(String dataId, String dataTypeId,String auditId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("dataId", dataId));
		params.add(new BasicNameValuePair("dataTypeId",dataTypeId));
		params.add(new BasicNameValuePair("auditId",auditId));
		new MyTask5().execute(params);
	}
	class MyTask5 extends AsyncTask<List<NameValuePair>, Integer, String> {
		String url = InterfaceConfig.SINGLEAUDIT;
		private String code;
		
		@Override
		protected void onPreExecute() {
			sdDialog = new SDProgressDialog(AuditListAuditActivity.this);
			sdDialog.show();
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
			if (result != null) {
				try {
					JSONObject jo = new JSONObject(result);
					code = jo.getInt("resultCode") + "";
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Message msg = new Message();
			if (code.equals("200")) {
				msg.what =10;
			} else {
				msg.what =11;
			}
			if(sdDialog.isShow())
				sdDialog.cancel();

			handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}

	/**
	 * 反审批 
	 * @param dataId    单据id
	 * @param dataTypeId    单据类型id
	 */
	private void initdata1(String dataId, String dataTypeId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("dataId", dataId));
		params.add(new BasicNameValuePair("dataTypeId",dataTypeId));
		new MyTask4().execute(params);
	}

	class MyTask4 extends AsyncTask<List<NameValuePair>, Integer, String> {
		String url = InterfaceConfig.REAUDIT;
		private String code;
		@Override
		protected void onPreExecute() {
			sdDialog = new SDProgressDialog(AuditListAuditActivity.this);
			sdDialog.show();
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
			if (result != null) {
				try {
					JSONObject jo = new JSONObject(result);
					code = jo.getInt("resultCode") + "";
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Message msg = new Message();
			if (code.equals("200")) {
				msg.what =3;
			} else {
				msg.what =5;
			}
			handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	
	

	/**
	 *  审批 通过 终止 的 0为终止，1为通过
 	 * @param dataId    单据id
	 * @param dataTypeId    单据类型id
	 * @param auditId      审批人
	 * @param auditResult    审批结果
	 * @param auditRemark    审批意见
	 */
	private void initdata(String dataId, String dataTypeId, String auditId,
			String auditResult, String auditRemark) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("dataId", dataId));
		params.add(new BasicNameValuePair("dataTypeId",dataTypeId));
		params.add(new BasicNameValuePair("auditId",auditId));
		params.add(new BasicNameValuePair("auditResult", auditResult));
		params.add(new BasicNameValuePair("auditRemark", auditRemark));
		new MyTask2().execute(params);
	}

	class MyTask2 extends AsyncTask<List<NameValuePair>, Integer, String> {
		String url = com.superdata.soho.config.InterfaceConfig.AUDIT;
		private String code;
		@Override
		protected void onPreExecute() {

			sdDialog = new SDProgressDialog(AuditListAuditActivity.this);
			sdDialog.show();
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
			if (result != null) {
				try {
					JSONObject jo = new JSONObject(result);
					code = jo.getInt("resultCode")+"";
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Message msg = new Message();
			if (code.equals("200")) {
				if(flag1){
				msg.what = 1;
				}else{
					msg.what =2;
				}
			} else {
				msg.what =5;
			}
			
			
			handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Intent intent =new Intent();
				intent.putExtra("POSITION", Position);
				setResult(1, intent);
				finish();
				break;
			case 2:
				Intent intent1 =new Intent();
				intent1.putExtra("POSITION", Position);
				setResult(2, intent1);
				finish();
				break;
			case 3:
				Intent intent2 =new Intent();
				intent2.putExtra("POSITION", Position);
				setResult(3, intent2);
				finish();
				break;
			case 5:
				Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT);
				break;
				//审批没有完成时
			case 6:
				flag =true;
				listview.setAdapter(adapter);
				showBottom();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}

				adapter.notifyDataSetChanged();
				break;
				//当出现Json解析异常时，或者其它异常时，底部Button全部不显示出来
			case 7:
				flag =false;
				listview.setAdapter(adapter);
				showBottom();		
				if(sdDialog.isShow())
					sdDialog.cancel();
				break;
				//审批完成了
			case 8:
				flagisover =true;
				listview.setAdapter(adapter);
				showBottom();
				if(sdDialog.isShow())
				sdDialog.cancel();
				break;
			case 9:
				Toast.makeText(getApplicationContext(), "获取数据出错",1).show();
				break;
			case 10:
				Toast.makeText(getApplicationContext(), "审批成功",1).show();
				break;
			case 11:
				Toast.makeText(getApplicationContext(), "审批失败",1).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	
	

	
	
	
	
    /**
     * 查看审批信息列表
     */
	public void initdata(){
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
		private int status;
		private int totalStep;
		private int step;
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
					           String auditResultStr =(jaresultList.getJSONObject(i).get("auditResultStr")).equals(null)?"没有"+i:(String) jaresultList.getJSONObject(i).get("auditResultStr");
					           String auditRemark =(jaresultList.getJSONObject(i).get("auditRemark")).equals(null)?"没有"+i:(String) jaresultList.getJSONObject(i).get("auditRemark");
					           AuditListDetail auditDetail = new  AuditListDetail(auditName, auditResultStr, auditRemark);
					          list.add(auditDetail);
					          status =jaresultList.getJSONObject(i).getInt("status");
					          totalStep =jaresultList.getJSONObject(i).getInt("totalStep");
					          step =jaresultList.getJSONObject(i).getInt("step");
						}
						//审批完成时
						if(status==1&&totalStep==step){
							   Message msg =new Message();
							      adapter =new MyAdapter();
							      msg.what =8;
								  handler.sendMessage(msg);

						}else{
							   Message msg =new Message();
							      adapter =new MyAdapter();
							      msg.what =6;
								  handler.sendMessage(msg);
						}
					} catch (Exception e) {
						e.printStackTrace();
					      Message msg =new Message();
					      msg.what =7;
						   handler.sendMessage(msg);
					}
				}
			super.onPostExecute(result);
		}
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
				convertView = LayoutInflater.from(AuditListAuditActivity.this).inflate(R.layout.item_audit_list_lookdetail, null);
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
