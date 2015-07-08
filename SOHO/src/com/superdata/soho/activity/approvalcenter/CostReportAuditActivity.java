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
 * 费用支出审批
 * @author lj
 *
 * 2014年8月14日
 */
public class CostReportAuditActivity extends BaseActivity {
	//终止false ,通过true
	private boolean flag1;
	private Button cost_report_app_audit_bt_back;
	private Button cost_report_app_audit_btncancel;
	private Button cost_report_app_audit_btnback;
	private Button cost_report_app_audit_btnpass;
	private EditText cost_report_app_audit_tv_auditremark;
	private TextView cost_report_app_audit_tv_code;
	private TextView cost_report_app_audit_tv_money;
	private TextView cost_report_app_audit_tv_theme;
	private TextView cost_report_app_audit_tv_remark;
	private TextView cost_report_app_audit_tv_name;
	private TextView cost_report_app_audit_tv_time;
	private TextView cost_report_app_audit_tv_auditstatus;
	private TextView cost_report_app_audit_tv_auditpeople;
	private TextView cost_report_app_audit_tv_namea;
	private LinearLayout cost_report_app_audit_ll_expdetail;
	private LinearLayout cost_report_app_audit_ll_lookmore;

	private String name;
	private String time;
	private String money;
	private String theme;
	private String remark;
	private String Code;
	private String auditstatus;
	private String auditpeople;
	private SDProgressDialog sdDialog;
	private String Position;
	private String id;
	private String empname;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_report_audit_activity);
		initSharePreferences();
		initData();
		initView();
	}
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(CostReportAuditActivity.this);
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
	}
	
	private void initView() {
		cost_report_app_audit_bt_back =(Button)this.findViewById(R.id.cost_report_app_audit_bt_back);
		cost_report_app_audit_bt_back.setOnClickListener(listener);
		cost_report_app_audit_btncancel =(Button)this.findViewById(R.id.cost_report_app_audit_btncancel);
		cost_report_app_audit_btncancel.setOnClickListener(listener);
		cost_report_app_audit_btnback =(Button)this.findViewById(R.id.cost_report_app_audit_btnback);
		cost_report_app_audit_btnback.setOnClickListener(listener);
		cost_report_app_audit_btnpass =(Button)this.findViewById(R.id.cost_report_app_audit_btnpass);
		cost_report_app_audit_btnpass.setOnClickListener(listener);
		cost_report_app_audit_tv_namea =(TextView)this.findViewById(R.id.cost_report_app_audit_tv_namea);
		cost_report_app_audit_tv_namea.setText(empname);
		cost_report_app_audit_tv_name =(TextView)this.findViewById(R.id.cost_report_app_audit_tv_name);
		cost_report_app_audit_tv_time =(TextView)this.findViewById(R.id.cost_report_app_audit_tv_time);
		cost_report_app_audit_tv_code =(TextView)this.findViewById(R.id.cost_report_app_audit_tv_code);
	
		cost_report_app_audit_tv_money =(TextView)this.findViewById(R.id.cost_report_app_audit_tv_money);
		cost_report_app_audit_tv_theme =(TextView)this.findViewById(R.id.cost_report_app_audit_tv_theme);
		cost_report_app_audit_tv_remark =(TextView)this.findViewById(R.id.cost_report_app_audit_tv_remark);
		cost_report_app_audit_tv_auditstatus =(TextView)this.findViewById(R.id.cost_report_app_audit_tv_auditstatus);
		cost_report_app_audit_tv_auditpeople =(TextView)this.findViewById(R.id.cost_report_app_audit_tv_auditpeople);
		
		cost_report_app_audit_tv_auditremark =(EditText)this.findViewById(R.id.cost_report_app_audit_tv_auditremark);
		cost_report_app_audit_ll_expdetail =(LinearLayout)this.findViewById(R.id.cost_report_app_audit_ll_expdetail);
		cost_report_app_audit_ll_expdetail.setOnClickListener(listener);
		cost_report_app_audit_ll_lookmore =(LinearLayout)this.findViewById(R.id.cost_report_app_audit_ll_lookmore);
		cost_report_app_audit_ll_lookmore.setOnClickListener(listener);
		cost_report_app_audit_tv_name.setText(name);
		cost_report_app_audit_tv_time.setText(time);
		cost_report_app_audit_tv_code.setText(Code);
		cost_report_app_audit_tv_money.setText(money);
		cost_report_app_audit_tv_theme.setText(theme);
		cost_report_app_audit_tv_remark.setText(remark);
		if(auditstatus.equals("0")){
			cost_report_app_audit_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			cost_report_app_audit_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
		}			
		cost_report_app_audit_tv_auditpeople.setText(auditpeople);
		
		
	}
	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cost_report_app_audit_bt_back:
				onBackPressed();
				break;
			//取消直接返回
			case R.id.cost_report_app_audit_btncancel:
				onBackPressed();
				break;
			//审批终止
			case R.id.cost_report_app_audit_btnback:
				flag1=false;
				initdata(id, "34", empId,0+"", cost_report_app_audit_tv_auditremark.getText().toString());
				break;
			//审批通过
			case R.id.cost_report_app_audit_btnpass:
				flag1=true;
				initdata(id, "34", empId, 1+"", cost_report_app_audit_tv_auditremark.getText().toString());
				break;
				//费用支出的具体的费用Items
			case R.id.cost_report_app_audit_ll_expdetail:
				Intent intent =new Intent(CostReportAuditActivity.this, CostReportlookdetailActivity.class);
				intent.putExtra("ID", id);
				startActivity(intent);
                break;
			case R.id.cost_report_app_audit_ll_lookmore:
				Intent intent2 =new Intent(getApplicationContext(), AuditListLookMore.class);
				intent2.putExtra("DATAID", id);
				intent2.putExtra("DATATYPEID", 34+"");
				startActivity(intent2);
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

			sdDialog = new SDProgressDialog(CostReportAuditActivity.this);
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
			if(result != null) {
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
