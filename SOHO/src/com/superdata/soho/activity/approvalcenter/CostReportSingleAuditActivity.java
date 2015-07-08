package com.superdata.soho.activity.approvalcenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;

 /**
  * 费用支出不走流程的审批
  * @author lj
  *
  * 2014年9月11日
  */
public class CostReportSingleAuditActivity extends BaseActivity {

	private String name;
	private String time;
	private String auditstatus;
	private String auditpeople;
	private String Position;
	private String Code;
	private String money;
	private String theme;
	private String remark;
	private String empname;
	private String id;
 
	
	private Button cost_report_app_singleauditback_btncancel;
	private Button cost_report_app_singleauditback_btnreturn;
	private Button cost_report_app_singleaudit_bt_back;
	private TextView cost_report_app_singleaudit_tv_name;
	private TextView cost_report_app_singleaudit_tv_time;
	private TextView cost_report_app_singleaudit_tv_code;
	private TextView cost_report_app_singleaudit_tv_money;
	private TextView cost_report_app_singleaudit_tv_theme;
	private TextView cost_report_app_singleaudit_tv_remark;
	private TextView cost_report_app_singleaudit_tv_auditstatus;
	private TextView cost_report_app_singleaudit_tv_auditpeople;
	private LinearLayout cost_report_app_singleaudit_ll_expdetail;
	private TextView cost_report_app_singleaudit_tv_namea;
	private LinearLayout cost_report_app_singleauditback_ll_bottom;
	private boolean flag;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_report_singleaudit_activity);
		initSharePreferences();
		initData();
		initView();
		
		
	}
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(CostReportSingleAuditActivity.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
	}

	private void initData() {
		 id =	getIntent().getExtras().getString("ID");
		 name =	getIntent().getExtras().getString("CREATENAME");
		 time =	getIntent().getExtras().getString("APPLYDATE");
		  Code = getIntent().getExtras().getString("CODE");
		 money =	getIntent().getExtras().getString("AMOUNT");
		 theme =	getIntent().getExtras().getString("NAME");
		 remark =	getIntent().getExtras().getString("REMARK");
		 auditstatus =	getIntent().getExtras().getString("STATUS");
		 auditpeople =	getIntent().getExtras().getString("AUDIT");
		 empname =	getIntent().getExtras().getString("EMPNAME");
		 Position =getIntent().getExtras().getString("POSITION");
		 flag =getIntent().getExtras().getBoolean("FLAG");
	}
	
	private void initView() {
		cost_report_app_singleaudit_bt_back =(Button)this.findViewById(R.id.cost_report_app_singleaudit_bt_back);
		cost_report_app_singleaudit_bt_back.setOnClickListener(listener);
		cost_report_app_singleauditback_btncancel =(Button)this.findViewById(R.id.cost_report_app_singleauditback_btncancel);
		cost_report_app_singleauditback_btncancel.setOnClickListener(listener);
		cost_report_app_singleauditback_btnreturn =(Button)this.findViewById(R.id.cost_report_app_singleauditback_btnreturn);
		cost_report_app_singleauditback_btnreturn.setOnClickListener(listener);
		cost_report_app_singleauditback_ll_bottom =(LinearLayout)this.findViewById(R.id.cost_report_app_singleauditback_ll_bottom);
		cost_report_app_singleaudit_tv_name =(TextView)this.findViewById(R.id.cost_report_app_singleaudit_tv_name);
		cost_report_app_singleaudit_tv_time =(TextView)this.findViewById(R.id.cost_report_app_singleaudit_tv_time);
		cost_report_app_singleaudit_tv_code =(TextView)this.findViewById(R.id.cost_report_app_singleaudit_tv_code);
		cost_report_app_singleaudit_tv_namea =(TextView)this.findViewById(R.id.cost_report_app_singleaudit_tv_namea);
		cost_report_app_singleaudit_tv_namea.setText(empname);
		cost_report_app_singleaudit_tv_money =(TextView)this.findViewById(R.id.cost_report_app_singleaudit_tv_money);
		cost_report_app_singleaudit_tv_theme =(TextView)this.findViewById(R.id.cost_report_app_singleaudit_tv_theme);
		cost_report_app_singleaudit_tv_remark =(TextView)this.findViewById(R.id.cost_report_app_singleaudit_tv_remark);
		cost_report_app_singleaudit_tv_auditstatus =(TextView)this.findViewById(R.id.cost_report_app_singleaudit_tv_auditstatus);
		cost_report_app_singleaudit_tv_auditpeople =(TextView)this.findViewById(R.id.cost_report_app_singleaudit_tv_auditpeople);
		cost_report_app_singleaudit_ll_expdetail =(LinearLayout)this.findViewById(R.id.cost_report_app_singleaudit_ll_expdetail);
		cost_report_app_singleaudit_ll_expdetail.setOnClickListener(listener);
		
		cost_report_app_singleaudit_tv_name.setText(name);
		cost_report_app_singleaudit_tv_time.setText(time);
		cost_report_app_singleaudit_tv_code.setText(Code);
		cost_report_app_singleaudit_tv_money.setText(money);
		cost_report_app_singleaudit_tv_theme.setText(theme);
		cost_report_app_singleaudit_tv_remark.setText(remark);
		
		if(auditstatus.equals("0")){
			cost_report_app_singleaudit_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
			if(!flag){
			cost_report_app_singleauditback_ll_bottom.setVisibility(View.GONE);
			}
		}else{
			cost_report_app_singleaudit_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
			cost_report_app_singleauditback_ll_bottom.setVisibility(View.GONE);
		}

		cost_report_app_singleaudit_tv_auditpeople.setText(auditpeople);
	}
	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cost_report_app_singleaudit_bt_back:
				onBackPressed();
				break;
			case R.id.cost_report_app_singleauditback_btncancel:
				onBackPressed();
				break;
				//单审批
			case R.id.cost_report_app_singleauditback_btnreturn:
				initdata1(id, "34",empId);
				break;
				//费用支出的具体的费用Items
			case R.id.cost_report_app_singleaudit_ll_expdetail:
				Intent intent =new Intent(CostReportSingleAuditActivity.this, CostReportlookdetailActivity.class);
				intent.putExtra("ID", id);
				startActivity(intent);

				break;
			default:
				break;
			}
		}
	};
	/**
	 * 单条记录审批 
	 * @param dataId    单据id
	 * @param dataTypeId    单据类型id
	 */
	private void initdata1(String dataId, String dataTypeId,String auditId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("dataId", dataId));
		params.add(new BasicNameValuePair("dataTypeId",dataTypeId));
		params.add(new BasicNameValuePair("auditId",auditId));
		new MyTask4().execute(params);
	}
	private SDProgressDialog sdDialog;
	class MyTask4 extends AsyncTask<List<NameValuePair>, Integer, String> {
		String url = InterfaceConfig.SINGLEAUDIT;
		private String code;
		
		@Override
		protected void onPreExecute() {
			sdDialog = new SDProgressDialog(CostReportSingleAuditActivity.this);
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
			if(sdDialog.isShow())
				sdDialog.cancel();

			handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 3:
				Intent intent2 =new Intent();
				intent2.putExtra("POSITION", Position);
				setResult(4, intent2);
				finish();
				break;
			case 5:
				Toast.makeText(getApplicationContext(), "审批失败", Toast.LENGTH_SHORT);
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
}
