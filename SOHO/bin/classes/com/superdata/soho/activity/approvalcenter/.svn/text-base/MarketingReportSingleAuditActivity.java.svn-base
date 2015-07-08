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
 * 不走流程的审批
 * @author lj
 *
 * 2014年9月11日
 */
public class MarketingReportSingleAuditActivity extends BaseActivity {
	private String name;
	private String time;
	private String auditbackstatus;
	private String auditbackpeople;
	private String Position;
	private String Code;
	private String money;
	private String theme;
	private String remark;
	private String remarkauditback;
	private String prob;
	private String type;
	private String id;

	
	private Button marketing_report_app_singleaudit_bt_back;
	private TextView marketing_report_app_singleaudit_tv_name;
	private TextView marketing_report_app_singleaudit_tv_time;
	private TextView marketing_report_app_singleaudit_tv_code;
	private TextView marketing_report_app_singleaudit_tv_money;
	private TextView marketing_report_app_singleaudit_tv_theme;
	private TextView marketing_report_app_singleaudit_tv_remark;
	private TextView marketing_report_app_singleaudit_tv_auditbackremarkbefore;
	private TextView marketing_report_app_singleaudit_tv_auditbackstatus;
	private TextView marketing_report_app_singleaudit_tv_auditbackpeople;
	private TextView marketing_report_app_singleaudit_tv_prob;
	private TextView marketing_report_app_singleaudit_tv_type;
	private LinearLayout marketing_report_app_singleaudit_ll_bottom;
	private Button marketing_report_app_singleaudit_btncancel;
	private Button marketing_report_app_singleaudit_btnreturn;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marketing_report_singleaudit_activity);
		initSharePreferences();
		initData();
		initView();
		
		
	}
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(MarketingReportSingleAuditActivity.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
	}

	private void initData() {
		 id =	getIntent().getExtras().getString("ID");
		 name =	getIntent().getExtras().getString("NAME");
		 time =	getIntent().getExtras().getString("TIME");
		 Code = getIntent().getExtras().getString("CODE");
		 money =	getIntent().getExtras().getString("MONEY");
		 theme =	getIntent().getExtras().getString("THEME");
		 remark =	getIntent().getExtras().getString("REMARK");
		 auditbackstatus =	getIntent().getExtras().getString("AUDITSTATUS");
		 auditbackpeople =	getIntent().getExtras().getString("auditbackPEOPLE");
	     Position =getIntent().getExtras().getString("POSITION");
	     remarkauditback =getIntent().getExtras().getString("REMARKauditback");
       	 prob =getIntent().getExtras().getString("PROB");
	     type =getIntent().getExtras().getString("TYPE");

	}
	
	private void initView() {
		marketing_report_app_singleaudit_bt_back =(Button)this.findViewById(R.id.marketing_report_app_singleaudit_bt_back);
		marketing_report_app_singleaudit_bt_back.setOnClickListener(listener);
		marketing_report_app_singleaudit_btncancel =(Button)this.findViewById(R.id.marketing_report_app_singleaudit_btncancel);
		marketing_report_app_singleaudit_btncancel.setOnClickListener(listener);
		marketing_report_app_singleaudit_btnreturn =(Button)this.findViewById(R.id.marketing_report_app_singleaudit_btnreturn);
		marketing_report_app_singleaudit_btnreturn.setOnClickListener(listener);
	
		marketing_report_app_singleaudit_tv_name =(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_name);
		marketing_report_app_singleaudit_tv_time =(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_time);
		marketing_report_app_singleaudit_tv_code =(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_code);
	
		marketing_report_app_singleaudit_tv_money =(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_money);
		marketing_report_app_singleaudit_tv_theme =(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_theme);
		marketing_report_app_singleaudit_tv_remark =(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_remark);
		marketing_report_app_singleaudit_ll_bottom =(LinearLayout)this.findViewById(R.id.marketing_report_app_singleaudit_ll_bottom);																								
		marketing_report_app_singleaudit_tv_auditbackremarkbefore =(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_auditremarkbefore);
		marketing_report_app_singleaudit_tv_auditbackstatus =(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_auditstatus);
		marketing_report_app_singleaudit_tv_auditbackpeople =(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_auditpeople);
	
		marketing_report_app_singleaudit_tv_prob =(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_prob);
		marketing_report_app_singleaudit_tv_type=(TextView)this.findViewById(R.id.marketing_report_app_singleaudit_tv_type);
	
		
		marketing_report_app_singleaudit_tv_prob.setText(prob);
		marketing_report_app_singleaudit_tv_type.setText(type);
		marketing_report_app_singleaudit_tv_name.setText(name);
		marketing_report_app_singleaudit_tv_time.setText(time);
		marketing_report_app_singleaudit_tv_code.setText(Code);
		marketing_report_app_singleaudit_tv_money.setText(money);
		marketing_report_app_singleaudit_tv_theme.setText(theme);
		marketing_report_app_singleaudit_tv_remark.setText(remark);
		marketing_report_app_singleaudit_tv_auditbackremarkbefore.setText(remarkauditback);
		
		if(auditbackstatus.equals("0")){
			marketing_report_app_singleaudit_tv_auditbackstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			marketing_report_app_singleaudit_ll_bottom.setVisibility(View.GONE);
			marketing_report_app_singleaudit_tv_auditbackstatus.setText(R.string.AUDIT_STATUS_1);
		}

		marketing_report_app_singleaudit_tv_auditbackpeople.setText(auditbackpeople);
		
		
	}
	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.marketing_report_app_singleaudit_bt_back:
				onBackPressed();
				break;
			case R.id.marketing_report_app_singleaudit_btncancel:
				onBackPressed();
				break;
			case R.id.marketing_report_app_singleaudit_btnreturn:
				initdata1(id, "33",empId);
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
			sdDialog = new SDProgressDialog(MarketingReportSingleAuditActivity.this);
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
