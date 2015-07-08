package com.superdata.soho.activity.approvalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;

   /**
    * 消息详情类
    * @author lj
    *
    * 2014年9月10日
    */
public class AttentionNotificationDetailActivity extends BaseActivity {
    //返回按钮
    private Button attention_notification_detail_bt_back;
    //主题
	private TextView attention_notification_detail_tv_title;
	//内容
	private TextView attention_notification_detail_tv_remark;
	//发送人
	private TextView attention_notification_detail_tv_name;
	//发送时间
	private TextView attention_notification_detail_tv_time;
	//回复
	private Button attention_notification_detail_bt_send;

	private String title;
	private String remark;
	private String people;
	private String date;
	private String receiverID;
	private String sendID;
	private String preContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attention_notificationdetail_activity);
		//初始化数据
		initData();
		//布局View的初始化和赋值
		initView();
		
		
		
	}

	private void initData() {

		 title =	getIntent().getExtras().getString("TITLE");
		 remark =	getIntent().getExtras().getString("REMARK");
		 people =	getIntent().getExtras().getString("PEOPLE");
		 date =	getIntent().getExtras().getString("DATE");
		 receiverID =getIntent().getExtras().getString("RECEIVEID");
		 sendID =getIntent().getExtras().getString("sendID");
         preContent =getIntent().getExtras().getString("PRECONTENT");
	}

	private void initView() {
		attention_notification_detail_bt_back =(Button)this.findViewById(R.id.attention_notification_detail_bt_back);
		attention_notification_detail_bt_back.setOnClickListener(listener);
	
		attention_notification_detail_tv_name =(TextView)this.findViewById(R.id.attention_notification_detail_tv_name);
		attention_notification_detail_tv_time =(TextView)this.findViewById(R.id.attention_notification_detail_tv_time);
		attention_notification_detail_tv_title =(TextView)this.findViewById(R.id.attention_notification_detail_tv_title);
		attention_notification_detail_tv_remark =(TextView)this.findViewById(R.id.attention_notification_detail_tv_remark);
		attention_notification_detail_bt_send =(Button)this.findViewById(R.id.attention_notification_detail_bt_send);
		attention_notification_detail_bt_send.setOnClickListener(listener);
		attention_notification_detail_tv_name.setText(people);
		attention_notification_detail_tv_time.setText(date);
		attention_notification_detail_tv_title.setText(title);
		attention_notification_detail_tv_remark.setText(remark);
		
		
	}
	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			//返回监听
			case R.id.attention_notification_detail_bt_back:
				onBackPressed();
				break;
			//发送监听
			case R.id.attention_notification_detail_bt_send:
				Intent intent =new Intent(getApplicationContext(), AttentionNotificationSendActivity.class);
				intent.putExtra("PEOPLE",people);
				intent.putExtra("RECEIVEID", receiverID);
				intent.putExtra("sendID", sendID);
				intent.putExtra("PRECONTENT", title);
				intent.putExtra("PREREMARK", preContent);
			    startActivity(intent);
			    finish();
				break;

			default:
				break;
			}
		}
	};
	
	
}
