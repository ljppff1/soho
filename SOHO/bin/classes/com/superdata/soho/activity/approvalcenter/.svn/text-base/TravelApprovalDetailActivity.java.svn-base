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
   * 出差详情
   * @author lj
   *
   * 2014年9月11日
   */
public class TravelApprovalDetailActivity extends BaseActivity {
	private Button travel_app_detail_bt_back;
	private TextView travel_app_detail_tv_name;
	private TextView travel_app_detail_tv_time;
	private TextView travel_app_detail_tv_type;
	private TextView travel_app_detail_tv_starttime;
	private TextView travel_app_detail_tv_endtime;
	private TextView travel_app_detail_tv_timelong;
	private TextView travel_app_detail_tv_reason;
	private TextView travel_app_detail_tv_auditstatus;
	private TextView travel_app_detail_tv_auditpeople;
	private TextView travel_app_detail_tv_route;
	private LinearLayout travel_app_detail_ll_lookmore;
	private String DataId;
	private String name;
	private String time;
	private String starttime;
	private String endtime;
	private String timelong;
	private String type;
	private String reason;
	private String auditstatus;
	private String auditpeople;
	private String route;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travel_approvaldetail_activity);
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
	     route =getIntent().getExtras().getString("ROUTE");
	     DataId =getIntent().getExtras().getString("ID");
	}
	private void initView() {
		travel_app_detail_bt_back =(Button)this.findViewById(R.id.travel_app_detail_bt_back);
		travel_app_detail_bt_back.setOnClickListener(listener);
		travel_app_detail_tv_name =(TextView)this.findViewById(R.id.travel_app_detail_tv_name);
		travel_app_detail_tv_time =(TextView)this.findViewById(R.id.travel_app_detail_tv_time);
		travel_app_detail_tv_type =(TextView)this.findViewById(R.id.travel_app_detail_tv_type);
		travel_app_detail_tv_starttime =(TextView)this.findViewById(R.id.travel_app_detail_tv_starttime);
		travel_app_detail_tv_endtime =(TextView)this.findViewById(R.id.travel_app_detail_tv_endtime);
		travel_app_detail_tv_timelong =(TextView)this.findViewById(R.id.travel_app_detail_tv_timelong);
		travel_app_detail_tv_reason =(TextView)this.findViewById(R.id.travel_app_detail_tv_reason);
		travel_app_detail_tv_auditstatus =(TextView)this.findViewById(R.id.travel_app_detail_tv_auditstatus);
		travel_app_detail_tv_auditpeople =(TextView)this.findViewById(R.id.travel_app_detail_tv_auditpeople);
		travel_app_detail_tv_route =(TextView)this.findViewById(R.id.travel_app_detail_tv_route);
		travel_app_detail_ll_lookmore =(LinearLayout)this.findViewById(R.id.travel_app_detail_ll_lookmore);
		travel_app_detail_ll_lookmore.setOnClickListener(listener);
		travel_app_detail_tv_name.setText(name);
		travel_app_detail_tv_time.setText(time);
		travel_app_detail_tv_type.setText(type);
		travel_app_detail_tv_starttime.setText(starttime);
		travel_app_detail_tv_endtime.setText(endtime);
		travel_app_detail_tv_timelong.setText(timelong);
		travel_app_detail_tv_reason.setText(reason);
		if(auditstatus.equals("0")){
			travel_app_detail_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			travel_app_detail_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
		}
		travel_app_detail_tv_auditpeople.setText(auditpeople);
		travel_app_detail_tv_route.setText(route);
	}
	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.travel_app_detail_bt_back:
				onBackPressed();
				break;
			case R.id.travel_app_detail_ll_lookmore:
				Intent intent =new Intent(getApplicationContext(), AuditListLookMore.class);
				intent.putExtra("DATAID", DataId);
				intent.putExtra("DATATYPEID", 26+"");
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
}