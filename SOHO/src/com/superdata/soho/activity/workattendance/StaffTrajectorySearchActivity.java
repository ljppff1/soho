/**
 * @Title StaffTrajectorySearchActivity.java
 * @Package com.superdata.soho.activity.workattendance
 * @author Administrator
 * @date 2014年9月11日 下午2:24:50
 * @version V1.0
 */
package com.superdata.soho.activity.workattendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivitySimple;
import com.superdata.soho.common.GridRadio;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.util.SharedPreferencesConfig;
import android.app.AlertDialog;
/**
 * @ClassName StaffTrajectorySearchActivity
 * @author Administrator
 * @date 2014年9月11日 下午2:24:50
 *
 */
public class StaffTrajectorySearchActivity extends BaseActivitySimple {
	View myView;
	Button search_starttime_btn,search_endtime_btn,bt_staff_search,bt_upload_postion,bt_back,bt_staff_cancel;
	RelativeLayout search_username_rl;
	TextView search_username_tv,search_username_code,title_staff;
	public static PendingIntent pendingIntent;
	List<Map<String, String>> listMapPeople = new ArrayList<Map<String, String>>();
	Map<String, String> mapconfig;
	String userId, username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staff_traje__search_activity);
		init();
	}
	/**
	初始化控件
	 */
	private void init() {
		mapconfig = SharedPreferencesConfig.config(StaffTrajectorySearchActivity.this);
		bt_staff_cancel = (Button) findViewById(R.id.bt_staff_cancel);
		bt_staff_cancel.setOnClickListener(this);
		bt_back = (Button) findViewById(R.id.bt_back);
		bt_back.setOnClickListener(this);
		bt_staff_search = (Button) findViewById(R.id.bt_staff_search);
		bt_staff_search.setOnClickListener(this);
		bt_upload_postion = (Button) findViewById(R.id.bt_upload_postion);
		bt_upload_postion.setOnClickListener(this);
		search_starttime_btn = (Button) findViewById(R.id.search_starttime_btn);
		search_starttime_btn.setOnClickListener(this);
		search_endtime_btn = (Button) findViewById(R.id.search_endtime_btn);
		search_endtime_btn.setOnClickListener(this);
		search_username_rl = (RelativeLayout) findViewById(R.id.search_username_rl);
		search_username_rl.setOnTouchListener(touch);
		search_username_tv = (TextView) findViewById(R.id.search_username_tv);
		search_username_code = (TextView) findViewById(R.id.search_username_code);
		title_staff = (TextView) findViewById(R.id.title_staff);
		String title = getIntent().getStringExtra("title");
		if(title!= null){
			title_staff.setText(title);
			bt_upload_postion.setVisibility(View.GONE);
		}
		
		userId = mapconfig.get(InterfaceConfig.ID);
		username = mapconfig.get(InterfaceConfig.LOGIN_USER_NAME);

		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DAY_OF_MONTH);

		search_starttime_btn.setText(String.format("%d-%d-%d %02d:%02d",y ,m, d,InterfaceConfig.STARTHOUR , InterfaceConfig.STARTMIN));
		search_endtime_btn.setText(String.format("%d-%d-%d %02d:%02d",y ,m, d,InterfaceConfig.ENDHOUR ,InterfaceConfig.ENDMIN));
		
		
		getJsonList();
	}
	
	
	/**
	 * 初始化用户名列表
	 * @Title getJsonList 方法注释
	 */
	public void getJsonList(){
		String json = getIntent().getStringExtra("data");
		try {
			JSONArray jsonArray=new JSONArray(json);
			int len=jsonArray.length();
			for (int i = 0; i < len; i++) {
				JSONObject jRoot=(JSONObject) jsonArray.get(i);
				Map<String,String> m = new HashMap<String, String>();
				m.put("text1", jRoot.getString("id"));
				m.put("text2", jRoot.getString("name"));
				if (Integer.parseInt(jRoot.getString("id").trim()) == Integer.parseInt(userId)) {
					search_username_code.setText(userId);
					search_username_tv.setText(username);
				}
				listMapPeople.add(m);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private OnTouchListener touch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			switch (v.getId()) {
			case R.id.search_username_rl:
				switch (action) {
				case MotionEvent.ACTION_UP:
					search_username_rl
							.setBackgroundResource(android.R.drawable.editbox_background);
					Gson gson = new Gson();
					Intent intent = new Intent(StaffTrajectorySearchActivity.this,
							GridRadio.class);
					intent.putExtra("data", gson.toJson(listMapPeople));
					intent.putExtra("resultCode", 1);
					String checkCode = search_username_code.getText().toString()
							.trim().equals("") ? null : search_username_code
							.getText().toString();
					intent.putExtra("checkCode", checkCode);
					intent.putExtra("titleBar", 1);
					startActivityForResult(intent, 1);
					break;
				case MotionEvent.ACTION_DOWN:
					search_username_rl.setBackgroundColor(Color.YELLOW);
					break;
				}
				break;
			}
			return true;
		}
	};
	 
	@Override
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_starttime_btn://开始时间
			setTimeText(R.id.search_starttime_btn);
			break;
		case R.id.search_endtime_btn://结束时间
			setTimeText(R.id.search_endtime_btn);
			break;
		case R.id.bt_staff_search://搜索按钮
			if(chechIsNull(search_username_code)){
				Toast.makeText(getApplicationContext(), "请选择用户名", Toast.LENGTH_LONG).show();
				return ;
			}
			
			 String startTime = search_starttime_btn.getText().toString();
			 String endTime = search_endtime_btn.getText().toString();
			 String usernamecode = search_username_code.getText().toString();
			 Intent intent = new Intent();
				intent.putExtra("startTime", startTime.equals(null)?" ":startTime);
				intent.putExtra("endTime", endTime.equals(null)?" ":endTime);
				intent.putExtra("usernamecode", usernamecode);
				setResult(1,intent);
				finish();
			break;
		case R.id.bt_upload_postion://定时上传
			if(!InterfaceConfig.IS_ALARMMANAGE_WORK){
				Toast.makeText(StaffTrajectorySearchActivity.this, "系统将会定时上传位置！", 2).show();
				InterfaceConfig.IS_ALARMMANAGE_WORK=true;
				Intent myintent = new Intent("com.superdata.soho.service.UploadPostionService"); //创建Intent对象
				pendingIntent = PendingIntent.getService(StaffTrajectorySearchActivity.this, 0, myintent, 0); //创建PendingIntent
				AlarmManager am = (AlarmManager) StaffTrajectorySearchActivity.this.getSystemService(ALARM_SERVICE);
			    am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 6*1000, pendingIntent);
			    
			}else{
				Toast.makeText(StaffTrajectorySearchActivity.this, "系统已经开始上传位置！", 1).show();
			}
			break;
		case R.id.bt_back:
			setResult(2);
			finish();
			break;
		case R.id.bt_staff_cancel:
			setResult(2);
			finish();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 时间选择
	 * @Title setTimeText
	 * @param id 方法注释
	 */
	public void setTimeText(final int id){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		myView = View.inflate(this, R.layout.date_time_dialog, null);
		final DatePicker datePicker = (DatePicker) myView
				.findViewById(R.id.date_picker);
		final TimePicker timePicker = (android.widget.TimePicker) myView
				.findViewById(R.id.time_picker);
		builder.setView(myView);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), null);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(Calendar.MINUTE);
		builder.setTitle("选取起始时间");

		builder.setPositiveButton("确  定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						StringBuffer sb = new StringBuffer();

						sb.append(String.format("%d-%02d-%02d",

						datePicker.getYear(),

						datePicker.getMonth() + 1,

						datePicker.getDayOfMonth()));

						sb.append("  ");

						sb.append(timePicker.getCurrentHour()).append(":")
								.append(timePicker.getCurrentMinute());

						if(id==R.id.search_starttime_btn){
							search_starttime_btn.setText(sb);
						}else {
							search_endtime_btn.setText(sb);
						}

						dialog.cancel();

					}

				});

		Dialog dialog = builder.create();
		InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		dialog.show();
	}


	public boolean chechIsNull(Button btnText){
		boolean flag=false;
		String str=btnText.getText().toString().trim();
		if(str==null||str.equals("")){
			flag=true;
		}
		return flag;
	}
	public boolean chechIsNull(TextView textText){
		boolean flag=false;
		String str=textText.getText().toString().trim();
		if(str==null||str.equals("")){
			flag=true;
		}
		return flag;
	}
	
	/* (非 Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String code = data.getStringExtra("text1");
		String name = data.getStringExtra("text2");
		switch (resultCode) {
		case 1:
			int c = Integer.parseInt(code);
			if (c != -1) {
				search_username_code.setText(code);
				search_username_tv.setText(name);
			}
			break;
		default:
			break;
		}
	}

}
