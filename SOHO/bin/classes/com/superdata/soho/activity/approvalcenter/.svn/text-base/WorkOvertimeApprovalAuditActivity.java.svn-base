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
 * 加班审批
 * @author lj
 *
 * 2014年9月11日
 */
public class WorkOvertimeApprovalAuditActivity extends BaseActivity {
	private boolean flag1;
	private Button work_overtime_app_audit_bt_back;
	private TextView work_overtime_app_audit_tv_name;
	private TextView work_overtime_app_audit_tv_time;
	private TextView work_overtime_app_audit_tv_type;
	private TextView work_overtime_app_audit_tv_starttime;
	private TextView work_overtime_app_audit_tv_endtime;
	private TextView work_overtime_app_audit_tv_timelong;
	private TextView work_overtime_app_audit_tv_reason;
	private TextView work_overtime_app_audit_tv_auditstatus;
	private TextView work_overtime_app_audit_tv_auditpeople;
	private Button work_overtime_app_audit_btncancel;
	private Button work_overtime_app_audit_btnback;
	private Button work_overtime_app_audit_btnpass;
	private EditText work_overtime_app_audit_tv_auditremark;
	private LinearLayout work_overtime_app_audit_ll_lookmore;
	
	
	private SDProgressDialog sdDialog;
	private String Position;
	private String name;
	private String time;
	private String starttime;
	private String endtime;
	private String timelong;
	private String type;
	private String reason;
	private String auditstatus;
	private String auditpeople;
	private String DataId;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work_overtime_approvalaudit_activity);
		initSharePreferences();
		initData();
		initView();
	}
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(WorkOvertimeApprovalAuditActivity.this);
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
	     DataId =getIntent().getExtras().getString("ID");
	}
	private void initView() {
		work_overtime_app_audit_bt_back =(Button)this.findViewById(R.id.work_overtime_app_audit_bt_back);
		work_overtime_app_audit_bt_back.setOnClickListener(listener);
		work_overtime_app_audit_btncancel =(Button)this.findViewById(R.id.work_overtime_app_audit_btncancel);
		work_overtime_app_audit_btncancel.setOnClickListener(listener);
		work_overtime_app_audit_btnback =(Button)this.findViewById(R.id.work_overtime_app_audit_btnback);
		work_overtime_app_audit_btnback.setOnClickListener(listener);
		work_overtime_app_audit_btnpass =(Button)this.findViewById(R.id.work_overtime_app_audit_btnpass);
		work_overtime_app_audit_btnpass.setOnClickListener(listener);
		work_overtime_app_audit_tv_name =(TextView)this.findViewById(R.id.work_overtime_app_audit_tv_name);
		work_overtime_app_audit_tv_time =(TextView)this.findViewById(R.id.work_overtime_app_audit_tv_time);
		work_overtime_app_audit_tv_type =(TextView)this.findViewById(R.id.work_overtime_app_audit_tv_type);
		work_overtime_app_audit_tv_starttime =(TextView)this.findViewById(R.id.work_overtime_app_audit_tv_starttime);
		work_overtime_app_audit_tv_endtime =(TextView)this.findViewById(R.id.work_overtime_app_audit_tv_endtime);
		work_overtime_app_audit_tv_timelong =(TextView)this.findViewById(R.id.work_overtime_app_audit_tv_timelong);
		work_overtime_app_audit_tv_reason =(TextView)this.findViewById(R.id.work_overtime_app_audit_tv_reason);
		work_overtime_app_audit_tv_auditstatus =(TextView)this.findViewById(R.id.work_overtime_app_audit_tv_auditstatus);
		work_overtime_app_audit_tv_auditpeople =(TextView)this.findViewById(R.id.work_overtime_app_audit_tv_auditpeople);
		work_overtime_app_audit_tv_auditremark=(EditText)this.findViewById(R.id.work_overtime_app_audit_tv_auditremark);
		work_overtime_app_audit_ll_lookmore =(LinearLayout)this.findViewById(R.id.work_overtime_app_audit_ll_lookmore);
		work_overtime_app_audit_ll_lookmore.setOnClickListener(listener);
		work_overtime_app_audit_tv_name.setText(name);
		work_overtime_app_audit_tv_time.setText(time);
		work_overtime_app_audit_tv_type.setText(type);
		work_overtime_app_audit_tv_starttime.setText(starttime);
		work_overtime_app_audit_tv_endtime.setText(endtime);
		work_overtime_app_audit_tv_timelong.setText(timelong);
		work_overtime_app_audit_tv_reason.setText(reason);
		if(auditstatus.equals("0")){
			work_overtime_app_audit_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			work_overtime_app_audit_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
		}
		work_overtime_app_audit_tv_auditpeople.setText(auditpeople);
	}
	OnClickListener listener =new OnClickListener() {		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.work_overtime_app_audit_bt_back:
				onBackPressed();
				break;
			//取消直接返回
			case R.id.work_overtime_app_audit_btncancel:
				onBackPressed();
				break;
			//审批终止
			case R.id.work_overtime_app_audit_btnback:
				flag1=false;
				initdata(DataId, "25", empId,0+"", work_overtime_app_audit_tv_auditremark.getText().toString());
				break;
			//审批通过
			case R.id.work_overtime_app_audit_btnpass:
				flag1 =true;
				initdata(DataId, "25", empId, 1+"", work_overtime_app_audit_tv_auditremark.getText().toString());
				break;
			case R.id.work_overtime_app_audit_ll_lookmore:
				Intent intent =new Intent(getApplicationContext(), AuditListLookMore.class);
				intent.putExtra("DATAID", DataId);
				intent.putExtra("DATATYPEID", 25+"");
				startActivity(intent);
            break;
			default:
				break;
			}
		}	
	};
	/**
	 * 审批 通过 终止
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
			sdDialog = new SDProgressDialog(WorkOvertimeApprovalAuditActivity.this);
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
