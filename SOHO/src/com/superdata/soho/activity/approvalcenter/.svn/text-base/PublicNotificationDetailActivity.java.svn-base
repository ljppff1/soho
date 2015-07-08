package com.superdata.soho.activity.approvalcenter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;

/**
 * 公告详情
 * @author lj
 *
 * 2014年9月11日
 */
public class PublicNotificationDetailActivity extends BaseActivity {
	private Button public_notification_detail_bt_back;
	private TextView public_notification_detail_tv_name;
	private TextView public_notification_detail_tv_time;
	private TextView public_notification_detail_tv_title;
	private TextView public_notification_detail_tv_remark;

	private String title;
	private String remark;
	private String people;
	private String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.public_notificationdetail_activity);
		initData();
		initView();
	}
	private void initData() {
		 title =	getIntent().getExtras().getString("TITLE");
		 remark =	getIntent().getExtras().getString("REMARK");
		 people =	getIntent().getExtras().getString("PEOPLE");
		 date =	getIntent().getExtras().getString("DATE");
	}
	private void initView() {
		public_notification_detail_bt_back =(Button)this.findViewById(R.id.public_notification_detail_bt_back);
		public_notification_detail_bt_back.setOnClickListener(listener);
		public_notification_detail_tv_name =(TextView)this.findViewById(R.id.public_notification_detail_tv_name);
		public_notification_detail_tv_time =(TextView)this.findViewById(R.id.public_notification_detail_tv_time);
		public_notification_detail_tv_title =(TextView)this.findViewById(R.id.public_notification_detail_tv_title);
		public_notification_detail_tv_remark =(TextView)this.findViewById(R.id.public_notification_detail_tv_remark);  
		public_notification_detail_tv_name.setText(people);
		public_notification_detail_tv_time.setText(date);
		public_notification_detail_tv_title.setText(title);
		public_notification_detail_tv_remark.setText(remark);
	}
	
	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.public_notification_detail_bt_back:
				onBackPressed();
				break;
			default:
				break;
			}
		}
	};
}