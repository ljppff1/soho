package com.superdata.soho.activity.approvalcenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;

   /**
    * 消息发送类
    * @author lj
    *
    * 2014年9月10日
    */
public class AttentionNotificationSendActivity extends BaseActivity {
    //返回按钮
	private Button attention_notification_send_bt_back;
	//回复谁谁
	private TextView attention_notification_send_tv_people;
	//清空，用于删除所写
	private Button attention_notification_send_btndelete;
	//回复按钮
	private Button attention_notification_send_btnsure;
	//取消
	private Button attention_notification_send_btncancel;
	//主题
	private EditText attention_notification_send_et_theme;
	//内容
	private EditText attention_notification_send_et_content;
	private String people;
	private String receiverID;
	private SDProgressDialog sdDialog;
	private String sendID;
	private String code="0";
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;
	private String preContent;
	private String userName;
	private String preRemark;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attention_notificationsend_activity);
		initSharePreferences();
		//初始化从信息详情表传过来的数据
		initData();
		//初始化View
		initView();
	}
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(AttentionNotificationSendActivity.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
		 userName =mapconfig.get(InterfaceConfig.LAST_USER_NAME);
	}

	private void initData() {
		 people =	getIntent().getExtras().getString("PEOPLE");
		 receiverID =getIntent().getExtras().getString("sendID");
		 preContent="回复："+getIntent().getExtras().getString("PRECONTENT");
		 sendID =empId;
		 preRemark =getIntent().getExtras().getString("PREREMARK");
	}

	private void initView() {
		attention_notification_send_bt_back =(Button)this.findViewById(R.id.attention_notification_send_bt_back);
		attention_notification_send_bt_back.setOnClickListener(listener);
	
		attention_notification_send_tv_people =(TextView)this.findViewById(R.id.attention_notification_send_tv_people);
		attention_notification_send_btndelete =(Button)this.findViewById(R.id.attention_notification_send_btndelete);
		attention_notification_send_btnsure =(Button)this.findViewById(R.id.attention_notification_send_btnsure);
		attention_notification_send_btncancel =(Button)this.findViewById(R.id.attention_notification_send_btncancel);
		attention_notification_send_et_theme =(EditText)this.findViewById(R.id.attention_notification_send_et_theme); 
		attention_notification_send_et_theme.setText(preContent);
		attention_notification_send_et_content=(EditText)this.findViewById(R.id.attention_notification_send_et_content);
		attention_notification_send_btndelete.setOnClickListener(listener);
		attention_notification_send_btnsure.setOnClickListener(listener);
		attention_notification_send_btncancel.setOnClickListener(listener);
          
		attention_notification_send_tv_people.setText(people);
		
	}
	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			//返回
			case R.id.attention_notification_send_bt_back:
				onBackPressed();
				break;
			//清空
			case R.id.attention_notification_send_btndelete:
				attention_notification_send_et_theme.setText("");
				attention_notification_send_et_content.setText("");
				break;
			//取消
			case R.id.attention_notification_send_btncancel:
				onBackPressed();
				break;
			//回复
			case R.id.attention_notification_send_btnsure:
				String a1 =attention_notification_send_et_theme.getText().toString();
				String a2 =attention_notification_send_et_content.getText().toString();
			  if(TextUtils.isEmpty(a1)||TextUtils.isEmpty(a2)){
					  Toast.makeText(getApplicationContext(), "请填充完整", Toast.LENGTH_SHORT).show();
			  }else{
				  initdata();
			  }
				break;

			default:
				break;
			}
		}
	};
	
   //确认提交信息
	public void initdata(){
	   String Format_DateTime = "yyyy-MM-dd HH:mm:ss";
		Date date =new Date();
      String time =  new  SimpleDateFormat(Format_DateTime).format(date.getTime());
        
		String aa =attention_notification_send_et_content.getText().toString()+preRemark;
		initdata(attention_notification_send_et_theme.getText().toString()+"",aa,sendID,empId);	
	}

	/**
	 * 发送信息到后台
	 * @param name      主题
	 * @param content   内容
	 * @param receiveId 接收人ID
	 * @param sendID    发送人ID
	 */
	private void initdata(String name, String content, String receiveId,String sendID) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("receiveId",receiveId));
		params.add(new BasicNameValuePair("sendId",sendID));
		new MyTask().execute(params);
	}
	
	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =com.superdata.soho.config.InterfaceConfig.ATTENTIONNOTIFICATIONSEND;
		@Override
        protected void onPreExecute() {
        	sdDialog = new SDProgressDialog(AttentionNotificationSendActivity.this);
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
		   if(result!=null){
					try {
						  JSONObject jRoot = new JSONObject(result);
						  code =jRoot.getString("resultCode");
						}
					 catch (Exception e) {
						e.printStackTrace();
					}
				}
		     Message msg =new Message();
		     //返回code==200成功提交
		     if(code.equals("200")){
		    	 msg.what =0;
		     }
		     
			    if(sdDialog.isShow())
				sdDialog.cancel();
			    handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	
	private Handler handler =new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
			    Toast.makeText(getApplicationContext(), "回复成功", Toast.LENGTH_SHORT).show();
				break;
		
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	
}
