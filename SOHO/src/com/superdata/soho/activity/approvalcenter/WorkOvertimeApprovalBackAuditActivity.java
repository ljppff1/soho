package com.superdata.soho.activity.approvalcenter;

import java.util.ArrayList;
import java.util.List;

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
import com.superdata.soho.view.SDProgressDialog;

/**
 * 加班反审批
 * @author lj
 *
 * 2014年9月11日
 */
public class WorkOvertimeApprovalBackAuditActivity extends BaseActivity {
	private Button work_overtime_app_auditback_bt_back;
	private TextView work_overtime_app_auditback_tv_name;
	private TextView work_overtime_app_auditback_tv_time;
	private TextView work_overtime_app_auditback_tv_type;
	private TextView work_overtime_app_auditback_tv_starttime;
	private TextView work_overtime_app_auditback_tv_endtime;
	private TextView work_overtime_app_auditback_tv_timelong;
	private TextView work_overtime_app_auditback_tv_reason;
	private TextView work_overtime_app_auditback_tv_auditstatus;
	private TextView work_overtime_app_auditback_tv_auditpeople;
	private Button approval_audit_btncancel;
	private Button approval_audit_btnback;
	private LinearLayout work_overtime_app_audit_ll_lookmore;

	
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
	private String DataId;
	private LinearLayout work_overtime_app_auditback_ll_lookmore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work_overtime_approvalauditback_activity);
		initData();
		initView();
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
		work_overtime_app_auditback_bt_back =(Button)this.findViewById(R.id.work_overtime_app_auditback_bt_back);
		work_overtime_app_auditback_bt_back.setOnClickListener(listener);
		approval_audit_btncancel =(Button)this.findViewById(R.id.work_overtime_app_auditback_btncancel);
		approval_audit_btncancel.setOnClickListener(listener);
		approval_audit_btnback =(Button)this.findViewById(R.id.work_overtime_app_auditback_btnreturn);
		approval_audit_btnback.setOnClickListener(listener);
		work_overtime_app_auditback_tv_name =(TextView)this.findViewById(R.id.work_overtime_app_auditback_tv_name);
		work_overtime_app_auditback_tv_time =(TextView)this.findViewById(R.id.work_overtime_app_auditback_tv_time);
		work_overtime_app_auditback_tv_type =(TextView)this.findViewById(R.id.work_overtime_app_auditback_tv_type);
		work_overtime_app_auditback_tv_starttime =(TextView)this.findViewById(R.id.work_overtime_app_auditback_tv_starttime);
		work_overtime_app_auditback_tv_endtime =(TextView)this.findViewById(R.id.work_overtime_app_auditback_tv_endtime);
		work_overtime_app_auditback_tv_timelong =(TextView)this.findViewById(R.id.work_overtime_app_auditback_tv_timelong);
		work_overtime_app_auditback_tv_reason =(TextView)this.findViewById(R.id.work_overtime_app_auditback_tv_reason);
		work_overtime_app_auditback_tv_auditstatus =(TextView)this.findViewById(R.id.work_overtime_app_auditback_tv_auditstatus);
		work_overtime_app_auditback_tv_auditpeople =(TextView)this.findViewById(R.id.work_overtime_app_auditback_tv_auditpeople);
		work_overtime_app_auditback_ll_lookmore =(LinearLayout)this.findViewById(R.id.work_overtime_app_auditback_ll_lookmore);
		work_overtime_app_auditback_ll_lookmore.setOnClickListener(listener);
		work_overtime_app_auditback_tv_name.setText(name);
		work_overtime_app_auditback_tv_time.setText(time);
		work_overtime_app_auditback_tv_type.setText(type);
		work_overtime_app_auditback_tv_starttime.setText(starttime);
		work_overtime_app_auditback_tv_endtime.setText(endtime);
		work_overtime_app_auditback_tv_timelong.setText(timelong);
		work_overtime_app_auditback_tv_reason.setText(reason);
		if(auditstatus.equals("0")){
			work_overtime_app_auditback_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			work_overtime_app_auditback_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
		}
		work_overtime_app_auditback_tv_auditpeople.setText(auditpeople);			
	}
	
	OnClickListener listener =new OnClickListener() {		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.work_overtime_app_auditback_bt_back:
				onBackPressed();
				break;
			case R.id.work_overtime_app_auditback_btncancel:
				onBackPressed();
				break;
			case R.id.work_overtime_app_auditback_btnreturn:
				initdata1(DataId, "25");
				break;
			case R.id.work_overtime_app_auditback_ll_lookmore:
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
	private SDProgressDialog sdDialog;
	class MyTask4 extends AsyncTask<List<NameValuePair>, Integer, String> {
		String url = InterfaceConfig.REAUDIT;
		private String code;
		
		@Override
		protected void onPreExecute() {
			sdDialog = new SDProgressDialog(WorkOvertimeApprovalBackAuditActivity.this);
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
				setResult(3, intent2);
				finish();
				break;
			case 5:
				Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
}