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
    * 请假详情
    * @author lj
    *
    * 2014年9月11日
    */
public class LeaveApprovalDetailActivity extends BaseActivity {

	private Button approval_detail_bt_back;
	private TextView approval_detail_tv_name;
	private TextView approval_detail_tv_time;
	private TextView approval_detail_tv_type;
	private TextView approval_detail_tv_starttime;
	private TextView approval_detail_tv_endtime;
	private TextView approval_detail_tv_timelong;
	private TextView approval_detail_tv_reason;
	private TextView approval_detail_tv_auditstatus;
	private TextView approval_detail_tv_auditpeople;
	private LinearLayout approval_detail_ll_lookmore;

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
		setContentView(R.layout.leaveapprovaldetail_activity);
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
		 DataId =getIntent().getExtras().getString("ID");
		 auditpeople =	"inthere";
	}

	private void initView() {
		approval_detail_bt_back =(Button)this.findViewById(R.id.approval_detail_bt_back);
		approval_detail_bt_back.setOnClickListener(listener);
		approval_detail_tv_name =(TextView)this.findViewById(R.id.approval_detail_tv_name);
		approval_detail_tv_time =(TextView)this.findViewById(R.id.approval_detail_tv_time);
		approval_detail_tv_type =(TextView)this.findViewById(R.id.approval_detail_tv_type);
		approval_detail_tv_starttime =(TextView)this.findViewById(R.id.approval_detail_tv_starttime);
		approval_detail_tv_endtime =(TextView)this.findViewById(R.id.approval_detail_tv_endtime);
		approval_detail_tv_timelong =(TextView)this.findViewById(R.id.approval_detail_tv_timelong);
		approval_detail_tv_reason =(TextView)this.findViewById(R.id.approval_detail_tv_reason);
		approval_detail_tv_auditstatus =(TextView)this.findViewById(R.id.approval_detail_tv_auditstatus);
		approval_detail_tv_auditpeople =(TextView)this.findViewById(R.id.approval_detail_tv_auditpeople);
		approval_detail_ll_lookmore =(LinearLayout)this.findViewById(R.id.approval_detail_ll_lookmore);
		approval_detail_ll_lookmore.setOnClickListener(listener);
		approval_detail_tv_name.setText(name);
		approval_detail_tv_time.setText(time);
		approval_detail_tv_type.setText(type);
		approval_detail_tv_starttime.setText(starttime);
		approval_detail_tv_endtime.setText(endtime);
		approval_detail_tv_timelong.setText(timelong);
		approval_detail_tv_reason.setText(reason);

        if(auditstatus.equals("0")){
        	approval_detail_tv_auditstatus.setText(R.string.AUDIT_STATUS_0);
        }else if(auditstatus.equals("1")){
        	approval_detail_tv_auditstatus.setText(R.string.AUDIT_STATUS_1);
        }
		approval_detail_tv_auditpeople.setText(auditpeople);
	}
	
	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.approval_detail_bt_back:
				onBackPressed();
				break;
				//查看审批列表信息
			case R.id.approval_detail_ll_lookmore:
				Intent intent =new Intent(getApplicationContext(), AuditListLookMore.class);
				intent.putExtra("DATAID", DataId);
				intent.putExtra("DATATYPEID", 24+"");
				startActivity(intent);
               break;
			default:
				break;
			}
		}
	};
	
	
}
