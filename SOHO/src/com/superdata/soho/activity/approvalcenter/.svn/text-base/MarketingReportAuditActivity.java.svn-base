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
import android.widget.EditText;
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
 * 营销报告审批
 * @author lj
 *
 * 2014年8月14日
 */
public class MarketingReportAuditActivity extends BaseActivity {

	private Button marketing_report_app_audit_bt_back;
	private TextView marketing_report_app_audit_tv_name;
	private TextView marketing_report_app_audit_tv_time;
	private TextView marketing_report_app_audit_tv_type;
	private TextView marketing_report_app_audit_tv_auditstatus;
	private TextView marketing_report_app_audit_tv_auditpeople;
	private Button marketing_report_app_audit_btncancel;
	private Button marketing_report_app_audit_btnback;
	private Button marketing_report_app_audit_btnpass;
	private LinearLayout marketing_report_app_audit_ll_lookmore;
	//我的意见
	private EditText marketing_report_app_audit_tv_auditremark;
	private TextView marketing_report_app_audit_tv_code;
	private TextView marketing_report_app_audit_tv_money;
	private TextView marketing_report_app_audit_tv_theme;
	private TextView marketing_report_app_audit_tv_remark;
	private TextView marketing_report_app_audit_tv_auditremarkbefore;
	private TextView marketing_report_app_audit_tv_prob;
	
	private String name;
	private String time;
	private String money;
	private String theme;
	private String remark;
	private String Code;
	private String auditstatus;
	private String auditpeople;
	private String Position;
	private String prob;
	private String remarkaudit;
	private String type;
	private String id;
	private boolean flag1;
	private SDProgressDialog sdDialog;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marketing_report_audit_activity);
		initSharePreferences();
		initData();
		initView();
	}
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(MarketingReportAuditActivity.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
	}

	private void initData() {
		 id =	getIntent().getExtras().getString("ID");
		 name =	getIntent().getExtras().getString("NAME");
		 time =	getIntent().getExtras().getString("TIME");
		 Code =	getIntent().getExtras().getString("CODE");
		 money =	getIntent().getExtras().getString("MONEY");
		 theme =	getIntent().getExtras().getString("THEME");
		 remark =	getIntent().getExtras().getString("REMARK");
		 auditstatus =	getIntent().getExtras().getString("AUDITSTATUS");
		 auditpeople =	getIntent().getExtras().getString("AUDITPEOPLE");
	     Position =getIntent().getExtras().getString("POSITION");
         remarkaudit =getIntent().getExtras().getString("REMARKAUDIT");
	     prob =getIntent().getExtras().getString("PROB");
	     type =getIntent().getExtras().getString("TYPE");
	}
	
	private void initView() {
		marketing_report_app_audit_bt_back =(Button)this.findViewById(R.id.marketing_report_app_audit_bt_back);
		marketing_report_app_audit_bt_back.setOnClickListener(listener);
		marketing_report_app_audit_btncancel =(Button)this.findViewById(R.id.marketing_report_app_audit_btncancel);
		marketing_report_app_audit_btncancel.setOnClickListener(listener);
		marketing_report_app_audit_btnback =(Button)this.findViewById(R.id.marketing_report_app_audit_btnback);
		marketing_report_app_audit_btnback.setOnClickListener(listener);
		marketing_report_app_audit_btnpass =(Button)this.findViewById(R.id.marketing_report_app_audit_btnpass);
		marketing_report_app_audit_btnpass.setOnClickListener(listener);
		marketing_report_app_audit_ll_lookmore =(LinearLayout)this.findViewById(R.id.marketing_report_app_audit_ll_lookmore);
		marketing_report_app_audit_ll_lookmore.setOnClickListener(listener);
		marketing_report_app_audit_tv_name =(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_name);
		marketing_report_app_audit_tv_time =(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_time);
		marketing_report_app_audit_tv_code =(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_code);
		marketing_report_app_audit_tv_money =(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_money);
		marketing_report_app_audit_tv_theme =(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_theme);
		marketing_report_app_audit_tv_remark =(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_remark);
		marketing_report_app_audit_tv_auditremarkbefore =(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_auditremarkbefore);
		marketing_report_app_audit_tv_auditstatus =(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_auditstatus);
		marketing_report_app_audit_tv_auditpeople =(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_auditpeople);
		marketing_report_app_audit_tv_prob =(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_prob);
		marketing_report_app_audit_tv_type=(TextView)this.findViewById(R.id.marketing_report_app_audit_tv_type);
		marketing_report_app_audit_tv_auditremark =(EditText)this.findViewById(R.id.marketing_report_app_audit_tv_auditremark);
		marketing_report_app_audit_tv_prob.setText(prob);
		marketing_report_app_audit_tv_type.setText(type);
		marketing_report_app_audit_tv_name.setText(name);
		marketing_report_app_audit_tv_time.setText(time);
		marketing_report_app_audit_tv_code.setText(Code);
		marketing_report_app_audit_tv_money.setText(money);
		marketing_report_app_audit_tv_theme.setText(theme);
		marketing_report_app_audit_tv_remark.setText(remark);
		marketing_report_app_audit_tv_auditremarkbefore.setText(remarkaudit);
		if(auditstatus.equals("0")){
			marketing_report_app_audit_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			marketing_report_app_audit_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
		}
		marketing_report_app_audit_tv_auditpeople.setText(auditpeople);
	}
	
	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.marketing_report_app_audit_bt_back:
				onBackPressed();
				break;
			//取消直接返回
			case R.id.marketing_report_app_audit_btncancel:
				onBackPressed();
				break;
			//审批终止
			case R.id.marketing_report_app_audit_btnback:
				flag1=false;
				initdata(id, "33",empId,0+"", marketing_report_app_audit_tv_auditremark.getText().toString());
				break;
			//审批通过
			case R.id.marketing_report_app_audit_btnpass:
				flag1 =true;
				initdata(id, "33",empId, 1+"", marketing_report_app_audit_tv_auditremark.getText().toString());
				break;
			//查看审批流程信息
			case R.id.marketing_report_app_audit_ll_lookmore:
				Intent intent =new Intent(getApplicationContext(), AuditListLookMore.class);
				intent.putExtra("DATAID", id);
				intent.putExtra("DATATYPEID", 33+"");
				startActivity(intent);
              break;
			default:
				break;
			}
		}
	};
	/**
	 * 审批 通过 不通过的
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
			sdDialog = new SDProgressDialog(MarketingReportAuditActivity.this);
			sdDialog.show();
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
			case 5:
				Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
}
