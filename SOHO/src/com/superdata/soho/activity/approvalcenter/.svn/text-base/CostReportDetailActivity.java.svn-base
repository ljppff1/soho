package com.superdata.soho.activity.approvalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;

     /**
      * 费用支出详情
      * @author lj
      *
      * 2014年9月11日
      */
public class CostReportDetailActivity extends BaseActivity {

	private Button cost_report_app_detail_bt_back;
	private TextView cost_report_app_detail_tv_name;
	private TextView cost_report_app_detail_tv_time;
	private TextView cost_report_app_detail_tv_auditstatus;
	private TextView cost_report_app_detail_tv_auditpeople;
	private TextView cost_report_app_detail_tv_code;
	private TextView cost_report_app_detail_tv_money;
	private TextView cost_report_app_detail_tv_theme;
	private TextView cost_report_app_detail_tv_remark;
	private TextView cost_report_app_detail_tv_namea;
	private LinearLayout cost_report_app_detail_ll_expdetail;
	private LinearLayout cost_report_app_detail_ll_lookmore;

	private String empname;
	private String name;
	private String time;
	private String auditstatus;
	private String auditpeople;
	private String Code;
	private String money;
	private String theme;
	private String remark;
	private String remarkaudit;
	private String id;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_report_detail_activity);
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
       remarkaudit ="";
	}
	
	private void initView() {
		cost_report_app_detail_bt_back =(Button)this.findViewById(R.id.cost_report_app_detail_bt_back);
		cost_report_app_detail_bt_back.setOnClickListener(listener);
		cost_report_app_detail_tv_namea =(TextView)this.findViewById(R.id.cost_report_app_detail_tv_namea);
		cost_report_app_detail_tv_namea.setText(empname);
		cost_report_app_detail_tv_name =(TextView)this.findViewById(R.id.cost_report_app_detail_tv_name);
		cost_report_app_detail_tv_time =(TextView)this.findViewById(R.id.cost_report_app_detail_tv_time);
		cost_report_app_detail_tv_code =(TextView)this.findViewById(R.id.cost_report_app_detail_tv_code);
	
		cost_report_app_detail_tv_money =(TextView)this.findViewById(R.id.cost_report_app_detail_tv_money);
		cost_report_app_detail_tv_theme =(TextView)this.findViewById(R.id.cost_report_app_detail_tv_theme);
		cost_report_app_detail_tv_remark =(TextView)this.findViewById(R.id.cost_report_app_detail_tv_remark);
		cost_report_app_detail_tv_auditstatus =(TextView)this.findViewById(R.id.cost_report_app_detail_tv_auditstatus);
		cost_report_app_detail_tv_auditpeople =(TextView)this.findViewById(R.id.cost_report_app_detail_tv_auditpeople);
		
		cost_report_app_detail_ll_expdetail =(LinearLayout)this.findViewById(R.id.cost_report_app_detail_ll_expdetail);
		cost_report_app_detail_ll_expdetail.setOnClickListener(listener);
		cost_report_app_detail_ll_lookmore =(LinearLayout)this.findViewById(R.id.cost_report_app_detail_ll_lookmore);
		cost_report_app_detail_ll_lookmore.setOnClickListener(listener);
		cost_report_app_detail_tv_name.setText(name);
		cost_report_app_detail_tv_time.setText(time);
		cost_report_app_detail_tv_code.setText(Code);
		cost_report_app_detail_tv_money.setText(money);
		cost_report_app_detail_tv_theme.setText(theme);
		cost_report_app_detail_tv_remark.setText(remark);
		if(cost_report_app_detail_tv_auditstatus.equals("0")){
			cost_report_app_detail_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			cost_report_app_detail_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
		}
		cost_report_app_detail_tv_auditpeople.setText(auditpeople);
		
		
	}
	
	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cost_report_app_detail_bt_back:
				onBackPressed();
				break;
				//费用支出的具体的费用Items
			case R.id.cost_report_app_detail_ll_expdetail:
				Intent intent =new Intent(CostReportDetailActivity.this, CostReportlookdetailActivity.class);
				intent.putExtra("ID", id);
				startActivity(intent);
				break;
				//审批流程信息详情
			case R.id.cost_report_app_detail_ll_lookmore:
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
	
	
}
