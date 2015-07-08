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
import com.superdata.soho.view.SDProgressDialog;

   /**
    * 费用支出反审批
    * @author lj
    *
    * 2014年9月11日
    */
public class CostReportBackAuditActivity extends BaseActivity {

	private String name;
	private String time;
	private String auditstatus;
	private String auditpeople;
	private String Position;
    private String code="10";
	private String Code;
	private String money;
	private String theme;
	private String remark;
	private String remarkaudit;
	private String empname;
	private String id;
	//取消
	private Button cost_report_app_auditbackback_btncancel;
	//反审批
	private Button cost_report_app_auditbackback_btnreturn;
    //返回
	private Button cost_report_app_auditback_bt_back;
	
	
	/**
	 * 费用支出字段的View
	 */
	private TextView cost_report_app_auditback_tv_name;
	private TextView cost_report_app_auditback_tv_time;
	private TextView cost_report_app_auditback_tv_code;
	private TextView cost_report_app_auditback_tv_money;
	private TextView cost_report_app_auditback_tv_theme;
	private TextView cost_report_app_auditback_tv_remark;
	private TextView cost_report_app_auditback_tv_auditstatus;
	private TextView cost_report_app_auditback_tv_auditpeople;
	private LinearLayout cost_report_app_auditback_ll_expdetail;
	private TextView cost_report_app_auditback_tv_namea;
	//查看审批列表信息
	private LinearLayout cost_report_app_auditback_ll_lookmore;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_report_auditback_activity);
		initData();
		initView();
		
		
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
       remarkaudit ="";
	}
	
	private void initView() {
		cost_report_app_auditback_bt_back =(Button)this.findViewById(R.id.cost_report_app_auditback_bt_back);
		cost_report_app_auditback_bt_back.setOnClickListener(listener);
		cost_report_app_auditbackback_btncancel =(Button)this.findViewById(R.id.cost_report_app_auditbackback_btncancel);
		cost_report_app_auditbackback_btncancel.setOnClickListener(listener);
		cost_report_app_auditbackback_btnreturn =(Button)this.findViewById(R.id.cost_report_app_auditbackback_btnreturn);
		cost_report_app_auditbackback_btnreturn.setOnClickListener(listener);
	
		cost_report_app_auditback_tv_name =(TextView)this.findViewById(R.id.cost_report_app_auditback_tv_name);
		cost_report_app_auditback_tv_time =(TextView)this.findViewById(R.id.cost_report_app_auditback_tv_time);
		cost_report_app_auditback_tv_code =(TextView)this.findViewById(R.id.cost_report_app_auditback_tv_code);
		cost_report_app_auditback_tv_namea =(TextView)this.findViewById(R.id.cost_report_app_auditback_tv_namea);
		cost_report_app_auditback_tv_namea.setText(empname);
		cost_report_app_auditback_tv_money =(TextView)this.findViewById(R.id.cost_report_app_auditback_tv_money);
		cost_report_app_auditback_tv_theme =(TextView)this.findViewById(R.id.cost_report_app_auditback_tv_theme);
		cost_report_app_auditback_tv_remark =(TextView)this.findViewById(R.id.cost_report_app_auditback_tv_remark);
		cost_report_app_auditback_tv_auditstatus =(TextView)this.findViewById(R.id.cost_report_app_auditback_tv_auditstatus);
		cost_report_app_auditback_tv_auditpeople =(TextView)this.findViewById(R.id.cost_report_app_auditback_tv_auditpeople);
		cost_report_app_auditback_ll_lookmore =(LinearLayout)this.findViewById(R.id.cost_report_app_auditback_ll_lookmore);
		cost_report_app_auditback_ll_lookmore.setOnClickListener(listener);
		cost_report_app_auditback_ll_expdetail =(LinearLayout)this.findViewById(R.id.cost_report_app_auditback_ll_expdetail);
		cost_report_app_auditback_ll_expdetail.setOnClickListener(listener);
		
		cost_report_app_auditback_tv_name.setText(name);
		cost_report_app_auditback_tv_time.setText(time);
		cost_report_app_auditback_tv_code.setText(Code);
		cost_report_app_auditback_tv_money.setText(money);
		cost_report_app_auditback_tv_theme.setText(theme);
		cost_report_app_auditback_tv_remark.setText(remark);
		if(auditstatus.equals("0")){
			cost_report_app_auditback_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			cost_report_app_auditback_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
		}

		cost_report_app_auditback_tv_auditpeople.setText(auditpeople);
		
		
	}
	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cost_report_app_auditback_bt_back:
				onBackPressed();
				break;
			case R.id.cost_report_app_auditbackback_btncancel:
				onBackPressed();
				break;
				//反审批
			case R.id.cost_report_app_auditbackback_btnreturn:
				initdata1(id,34+"");
				break;
				//费用支出的具体的费用Items
			case R.id.cost_report_app_auditback_ll_expdetail:
				Intent intent =new Intent(CostReportBackAuditActivity.this, CostReportlookdetailActivity.class);
				intent.putExtra("ID", id);
				startActivity(intent);
				break;
				//审批流程信息详情
			case R.id.cost_report_app_auditback_ll_lookmore:
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
			sdDialog = new SDProgressDialog(CostReportBackAuditActivity.this);
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
