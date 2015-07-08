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
 * 加班详情
 * @author lj
 *
 * 2014年9月11日
 */
public class WorkOvertimeApprovalDetailActivity extends BaseActivity {

	private Button work_overtime_app_detail_bt_back;
	private TextView work_overtime_app_detail_tv_name;
	private TextView work_overtime_app_detail_tv_time;
	private TextView work_overtime_app_detail_tv_type;
	private TextView work_overtime_app_detail_tv_starttime;
	private TextView work_overtime_app_detail_tv_endtime;
	private TextView work_overtime_app_detail_tv_timelong;
	private TextView work_overtime_app_detail_tv_reason;
	private TextView work_overtime_app_detail_tv_auditstatus;
	private TextView work_overtime_app_detail_tv_auditpeople;
	private LinearLayout work_overtime_app_detail_ll_lookmore;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work_overtime_approvaldetail_activity);
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
		 auditpeople =	"保留";
		 DataId =getIntent().getExtras().getString("ID");
	}

	private void initView() {
		work_overtime_app_detail_bt_back =(Button)this.findViewById(R.id.work_overtime_app_detail_bt_back);
		work_overtime_app_detail_bt_back.setOnClickListener(listener);
		work_overtime_app_detail_tv_name =(TextView)this.findViewById(R.id.work_overtime_app_detail_tv_name);
		work_overtime_app_detail_tv_time =(TextView)this.findViewById(R.id.work_overtime_app_detail_tv_time);
		work_overtime_app_detail_tv_type =(TextView)this.findViewById(R.id.work_overtime_app_detail_tv_type);
		work_overtime_app_detail_tv_starttime =(TextView)this.findViewById(R.id.work_overtime_app_detail_tv_starttime);
		work_overtime_app_detail_tv_endtime =(TextView)this.findViewById(R.id.work_overtime_app_detail_tv_endtime);
		work_overtime_app_detail_tv_timelong =(TextView)this.findViewById(R.id.work_overtime_app_detail_tv_timelong);
		work_overtime_app_detail_tv_reason =(TextView)this.findViewById(R.id.work_overtime_app_detail_tv_reason);
		work_overtime_app_detail_tv_auditstatus =(TextView)this.findViewById(R.id.work_overtime_app_detail_tv_auditstatus);
		work_overtime_app_detail_tv_auditpeople =(TextView)this.findViewById(R.id.work_overtime_app_detail_tv_auditpeople);
		work_overtime_app_detail_ll_lookmore =(LinearLayout)this.findViewById(R.id.work_overtime_app_detail_ll_lookmore);
		work_overtime_app_detail_ll_lookmore.setOnClickListener(listener);
		work_overtime_app_detail_tv_name.setText(name);
		work_overtime_app_detail_tv_time.setText(time);
		work_overtime_app_detail_tv_type.setText(type);
		work_overtime_app_detail_tv_starttime.setText(starttime);
		work_overtime_app_detail_tv_endtime.setText(endtime);
		work_overtime_app_detail_tv_timelong.setText(timelong);
		work_overtime_app_detail_tv_reason.setText(reason);
		if(auditstatus.equals("0")){
			work_overtime_app_detail_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
		}else{
			work_overtime_app_detail_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
		}
		work_overtime_app_detail_tv_auditpeople.setText(auditpeople);	
	}
	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.work_overtime_app_detail_bt_back:
				onBackPressed();
				break;
			case R.id.work_overtime_app_detail_ll_lookmore:
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
}