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
 * 营销报告详情
 * @author lj
 *
 * 2014年9月11日
 */
public class MarketingReportDetailActivity extends BaseActivity {

	private Button marketing_report_app_detail_bt_back;
	private TextView marketing_report_app_detail_tv_name;
	private TextView marketing_report_app_detail_tv_time;
	private TextView marketing_report_app_detail_tv_code;
	private TextView marketing_report_app_detail_tv_money;
	private TextView marketing_report_app_detail_tv_theme;
	private TextView marketing_report_app_detail_tv_remark;
	private TextView marketing_report_app_detail_tv_auditbackremarkbefore;
	private TextView marketing_report_app_detail_tv_auditbackstatus;
	private TextView marketing_report_app_detail_tv_auditbackpeople;
	private TextView marketing_report_app_detail_tv_prob;
	private TextView marketing_report_app_detail_tv_type;
	private TextView marketing_report_app_detail_tv_createname;
	private LinearLayout marketing_report_app_detail_ll_lookmore;

	
	private String auditbackstatus;
	private String auditbackpeople;
	private String remarkauditback;
	private String prob;
	private String type;
	private String createname;
	private String name;
	private String time;
	private String Code;
	private String money;
	private String theme;
	private String remark;
	private String DataId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marketing_report_detail_activity);
		initData();
		initView();
	}
	private void initData() {
		 DataId =	getIntent().getExtras().getString("ID");
		 name =	getIntent().getExtras().getString("NAME");
		 time =	getIntent().getExtras().getString("TIME");
		 Code = getIntent().getExtras().getString("CODE");
		 money =	getIntent().getExtras().getString("MONEY");
		 theme =	getIntent().getExtras().getString("THEME");
		 createname =getIntent().getExtras().getString("CREATENAME");
		 remark =	getIntent().getExtras().getString("REMARK");
		 auditbackstatus =	getIntent().getExtras().getString("AUDITSTATUS");
		 auditbackpeople =	getIntent().getExtras().getString("auditbackPEOPLE");
	     remarkauditback =getIntent().getExtras().getString("REMARKauditback");
	     prob =getIntent().getExtras().getString("PROB");
	     type =getIntent().getExtras().getString("TYPE");
	}
	
	private void initView() {
		marketing_report_app_detail_bt_back =(Button)this.findViewById(R.id.marketing_report_app_detail_bt_back);
		marketing_report_app_detail_bt_back.setOnClickListener(listener);
		marketing_report_app_detail_tv_name =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_name);
		marketing_report_app_detail_tv_time =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_time);
		marketing_report_app_detail_tv_code =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_code);
		marketing_report_app_detail_tv_money =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_money);
		marketing_report_app_detail_tv_theme =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_theme);
		marketing_report_app_detail_tv_remark =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_remark);
		marketing_report_app_detail_tv_auditbackremarkbefore =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_auditremarkbefore);
		marketing_report_app_detail_tv_auditbackstatus =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_auditstatus);
		marketing_report_app_detail_tv_auditbackpeople =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_auditpeople);
		marketing_report_app_detail_tv_prob =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_prob);
		marketing_report_app_detail_tv_type=(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_type);
		marketing_report_app_detail_tv_createname =(TextView)this.findViewById(R.id.marketing_report_app_detail_tv_createname);
		marketing_report_app_detail_tv_createname.setText(createname);
		marketing_report_app_detail_ll_lookmore =(LinearLayout)this.findViewById(R.id.marketing_report_app_detail_ll_lookmore);
		marketing_report_app_detail_ll_lookmore.setOnClickListener(listener);
		marketing_report_app_detail_tv_prob.setText(prob);
		marketing_report_app_detail_tv_type.setText(type);
		marketing_report_app_detail_tv_name.setText(name);
		marketing_report_app_detail_tv_time.setText(time);
		marketing_report_app_detail_tv_code.setText(Code);
		marketing_report_app_detail_tv_money.setText(money);
		marketing_report_app_detail_tv_theme.setText(theme);
		marketing_report_app_detail_tv_remark.setText(remark);
		marketing_report_app_detail_tv_auditbackremarkbefore.setText(remarkauditback);
		if(auditbackstatus.equals("0")){
			marketing_report_app_detail_tv_auditbackstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			marketing_report_app_detail_tv_auditbackstatus.setText(R.string.AUDIT_STATUS_1);
		}
		marketing_report_app_detail_tv_auditbackpeople.setText(auditbackpeople);
	}
	
	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			//返回
			case R.id.marketing_report_app_detail_bt_back:
				onBackPressed();
				break;
		    //查看审批列表信息
			case R.id.marketing_report_app_detail_ll_lookmore:
				Intent intent =new Intent(getApplicationContext(), AuditListLookMore.class);
				intent.putExtra("DATAID", DataId);
				intent.putExtra("DATATYPEID", 33+"");
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
	
	
}
