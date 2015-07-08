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
 * 出差审批
 * @author lj
 *
 * 2014年9月11日
 */
public class TravelApprovalAuditActivity extends BaseActivity {
	private Button travel_app_audit_bt_back;
	private TextView travel_app_audit_tv_name;
	private TextView travel_app_audit_tv_time;
	private TextView travel_app_audit_tv_type;
	private TextView travel_app_audit_tv_starttime;
	private TextView travel_app_audit_tv_endtime;
	private TextView travel_app_audit_tv_timelong;
	private TextView travel_app_audit_tv_reason;
	private TextView travel_app_audit_tv_auditstatus;
	private TextView travel_app_audit_tv_auditpeople;
	private Button travel_app_audit_btncancel;
	private Button travel_app_audit_btnback;
	private Button travel_app_audit_btnpass;
	private TextView travel_app_audit_tv_route;
	private EditText travel_app_audit_tv_auditremark;
	private LinearLayout travel_app_audit_ll_lookmore;

	
	private String name;
	private String time;
	private String starttime;
	private String endtime;
	private String timelong;
	private String type;
	private String reason;
	private String auditstatus;
	private String auditpeople;
	private String Position;
	private String route;
	private String DataId;
	private SDProgressDialog sdDialog;
	private boolean flag1;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travel_approvalaudit_activity);
		initSharePreferences();
		initData();
		initView();
	}
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(TravelApprovalAuditActivity.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
	}

	private void initData() {
		 name =	getIntent().getExtras().getString("NAME");
		 time =	getIntent().getExtras().getString("TIME");
		 type =	getIntent().getExtras().getString("TYPE");
		 starttime =	getIntent().getExtras().getString("STARTTIME");
		 endtime =	getIntent().getExtras().getString("ENDTIME");
		 timelong =	getIntent().getExtras().getString("TIMELONG");
		 reason =	getIntent().getExtras().getString("REASON");
		 auditstatus =	getIntent().getExtras().getString("AUDITSTATUS");
		 auditpeople =	getIntent().getExtras().getString("AUDITPEOPLE");
	     Position =getIntent().getExtras().getString("POSITION");
	     route =getIntent().getExtras().getString("ROUTE");
	     DataId =getIntent().getExtras().getString("ID");
	}
	private void initView() {
		travel_app_audit_bt_back =(Button)this.findViewById(R.id.travel_app_audit_bt_back);
		travel_app_audit_bt_back.setOnClickListener(listener);
		travel_app_audit_btncancel =(Button)this.findViewById(R.id.travel_app_audit_btncancel);
		travel_app_audit_btncancel.setOnClickListener(listener);
		travel_app_audit_btnback =(Button)this.findViewById(R.id.travel_app_audit_btnback);
		travel_app_audit_btnback.setOnClickListener(listener);
		travel_app_audit_btnpass =(Button)this.findViewById(R.id.travel_app_audit_btnpass);
		travel_app_audit_btnpass.setOnClickListener(listener);
		travel_app_audit_tv_name =(TextView)this.findViewById(R.id.travel_app_audit_tv_name);
		travel_app_audit_tv_time =(TextView)this.findViewById(R.id.travel_app_audit_tv_time);
		travel_app_audit_tv_type =(TextView)this.findViewById(R.id.travel_app_audit_tv_type);
		travel_app_audit_tv_starttime =(TextView)this.findViewById(R.id.travel_app_audit_tv_starttime);
		travel_app_audit_tv_endtime =(TextView)this.findViewById(R.id.travel_app_audit_tv_endtime);
		travel_app_audit_tv_timelong =(TextView)this.findViewById(R.id.travel_app_audit_tv_timelong);
		travel_app_audit_tv_reason =(TextView)this.findViewById(R.id.travel_app_audit_tv_reason);
		travel_app_audit_tv_auditstatus =(TextView)this.findViewById(R.id.travel_app_audit_tv_auditstatus);
		travel_app_audit_tv_auditpeople =(TextView)this.findViewById(R.id.travel_app_audit_tv_auditpeople);
		travel_app_audit_tv_route =(TextView)this.findViewById(R.id.travel_app_audit_tv_route);
		travel_app_audit_tv_auditremark =(EditText)this.findViewById(R.id.travel_app_audit_tv_auditremark);
		travel_app_audit_ll_lookmore =(LinearLayout)this.findViewById(R.id.travel_app_audit_ll_lookmore);
		travel_app_audit_ll_lookmore.setOnClickListener(listener);
		travel_app_audit_tv_route.setText(route);
		travel_app_audit_tv_name.setText(name);
		travel_app_audit_tv_time.setText(time);
		travel_app_audit_tv_type.setText(type);
		travel_app_audit_tv_starttime.setText(starttime);
		travel_app_audit_tv_endtime.setText(endtime);
		travel_app_audit_tv_timelong.setText(timelong);
		travel_app_audit_tv_reason.setText(reason);
		if(auditstatus.equals("0")){
		travel_app_audit_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			travel_app_audit_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
		}
		travel_app_audit_tv_auditpeople.setText(auditpeople);
	}
	
	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.travel_app_audit_bt_back:
				onBackPressed();
				break;
			//取消直接返回
			case R.id.travel_app_audit_btncancel:
				onBackPressed();
				break;
			//审批终止
			case R.id.travel_app_audit_btnback:
				flag1=false;
				initdata(DataId, "26", empId,0+"", travel_app_audit_tv_auditremark.getText().toString());
				break;
			//审批通过
			case R.id.travel_app_audit_btnpass:
				flag1 =true;
				initdata(DataId, "26", empId, 1+"", travel_app_audit_tv_auditremark.getText().toString());
				break;
			//想看审批信息列表
			case R.id.travel_app_audit_ll_lookmore:
				Intent intent =new Intent(getApplicationContext(), AuditListLookMore.class);
				intent.putExtra("DATAID", DataId);
				intent.putExtra("DATATYPEID", 26+"");
				startActivity(intent);
			default:
				break;
			}
		}

	
	};
	/**
	 * 审批 通过 终止的
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

			sdDialog = new SDProgressDialog(TravelApprovalAuditActivity.this);
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
