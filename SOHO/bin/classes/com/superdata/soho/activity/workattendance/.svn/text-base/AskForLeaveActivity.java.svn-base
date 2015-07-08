/**  
 * @Title: AskForLeaveActivity.java
 * @Package com.superdata.soho.activity
 * @Description: TODO(用一句话描述该文件做什么)
 * @author A18ccms A18ccms_gmail_com  
 * @date 2014年6月11日 上午10:22:17
 * @version V1.0  
 */
package com.superdata.soho.activity.workattendance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.common.GridRadio;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.LeaveRecord;
import com.superdata.soho.entity.User;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.NetCheckUtil;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;

/**
 * @ClassName: AskForLeaveActivity
 * @Description: 请假页面
 * @author Administrator
 * @date 2014年6月11日 上午10:22:17
 * 
 */
public class AskForLeaveActivity extends BaseActivity implements
		OnClickListener {

	ArrayAdapter<String> adapter;
	private Button leave_bt_back, leave_bt_pc_records, pick_up_startTime,
			pick_up_endTime;
	Intent intent, getIntent;
	int requestCode = 12;
	EditText num_leave_id, department_leave_id, position_leave_id,
			total_time_leave_id, reason_leave_id;
	View myView;
	List<NameValuePair> params;
	Message msg;
	SDProgressDialog sdDialog;
	boolean updateFlag = false;
	private String code, askId;
	Map<String, String> mapconfig;
	User user;
	List<User> userList;
	String[] listStr;
	String userId, username;

	// tsl
	RelativeLayout type_leave_id, people_leave_id;
	List<Map<String, String>> listMapType = new ArrayList<Map<String, String>>();
	List<Map<String, String>> listMapPeople = new ArrayList<Map<String, String>>();
	TextView type_leave_tv, type_leave_code, people_leave_tv,
			people_leave_code;

	String updateName,updateType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.askforleave_activity);
		init();

	}

	/**
	 * 
	 * @Title: init
	 * @Description: 初始化方法
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void init() {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("text1", "35");
		map1.put("text2", "年假");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("text1", "33");
		map2.put("text2", "病假");
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("text1", "34");
		map3.put("text2", "事假");
		listMapType.add(map1);
		listMapType.add(map2);
		listMapType.add(map3);
		sdDialog = new SDProgressDialog(AskForLeaveActivity.this);
		userList = new ArrayList<User>();
		mapconfig = SharedPreferencesConfig.config(AskForLeaveActivity.this);
		leave_bt_back = (Button) findViewById(R.id.leave_bt_back);
		leave_bt_pc_records = (Button) findViewById(R.id.leave_bt_pc_records);
		pick_up_startTime = (Button) findViewById(R.id.pick_up_startTime);
		pick_up_endTime = (Button) findViewById(R.id.pick_up_endTime);
		num_leave_id = (EditText) findViewById(R.id.num_leave_id);
		department_leave_id = (EditText) findViewById(R.id.department_leave_id);
		position_leave_id = (EditText) findViewById(R.id.position_leave_id);
		total_time_leave_id = (EditText) findViewById(R.id.total_time_leave_id);
		reason_leave_id = (EditText) findViewById(R.id.reason_leave_id);
		type_leave_tv = (TextView) findViewById(R.id.type_leave_tv);
		type_leave_code = (TextView) findViewById(R.id.type_leave_code);
		people_leave_tv = (TextView) findViewById(R.id.people_leave_tv);
		people_leave_code = (TextView) findViewById(R.id.people_leave_code);
		type_leave_id = (RelativeLayout) findViewById(R.id.type_leave_id);
		people_leave_id = (RelativeLayout) findViewById(R.id.people_leave_id);
		type_leave_id.setOnTouchListener(touch);
		people_leave_id.setOnTouchListener(touch);
		leave_bt_back.setOnClickListener(this);
		leave_bt_pc_records.setOnClickListener(this);
		pick_up_startTime.setOnClickListener(this);
		pick_up_endTime.setOnClickListener(this);
		getIntent = getIntent();
		LeaveRecord leaveRecord = new LeaveRecord();
		leaveRecord = (LeaveRecord) getIntent.getSerializableExtra("update");// 判断是否是更新
		if (leaveRecord != null) {
			String startStr = leaveRecord.getLeaveRecordStartTime();
			String[] ymd = startStr.substring(0, startStr.indexOf(" "))
					.split("-");
			String[] hm = startStr.substring(startStr.indexOf(" ")).trim()
					.split(":");
			int y = Integer.parseInt(ymd[0]);
			int m = Integer.parseInt(ymd[1]);
			int d = Integer.parseInt(ymd[2]);
			int h = Integer.parseInt(hm[0]);
			int mi = Integer.parseInt(hm[1]);
			
			String endStr = leaveRecord.getLeaveRecordEndTime();
			String[] ymdend = endStr.substring(0, endStr.indexOf(" "))
					.split("-");
			String[] hmend = endStr.substring(endStr.indexOf(" ")).trim()
					.split(":");
			int ye = Integer.parseInt(ymdend[0]);
			int me = Integer.parseInt(ymdend[1]);
			int de = Integer.parseInt(ymdend[2]);
			int he = Integer.parseInt(hmend[0]);
			int mie = Integer.parseInt(hmend[1]);
			
			pick_up_startTime.setText(String.format("%d-%d-%d %02d:%02d",y,m,d,h,mi));
			pick_up_endTime.setText(String.format("%d-%d-%d %02d:%02d",ye,me,de,he,mie));
			num_leave_id.setText(leaveRecord.getCode());
			total_time_leave_id.setText(leaveRecord.getLeaveRecordTotalTime()
					+ "");
			reason_leave_id.setText(leaveRecord.getLeaveRecordReason());
			updateFlag = true;
			code = leaveRecord.getCode();
			askId = leaveRecord.getId();
			updateName = leaveRecord.getUserName();
			updateType = leaveRecord.getLeaveRecordType();
		}else{
			// 设置默认值
			type_leave_tv.setText("事假");
			type_leave_code.setText("34");
			Map<String, String> mapconfig = SharedPreferencesConfig
					.config(AskForLeaveActivity.this);
			userId = mapconfig.get(InterfaceConfig.ID);
			username = mapconfig.get(InterfaceConfig.LOGIN_USER_NAME);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			int y = cal.get(Calendar.YEAR);
			int m = cal.get(Calendar.MONTH) + 1;
			int d = cal.get(Calendar.DAY_OF_MONTH);

			pick_up_startTime.setText(String.format("%d-%d-%d %02d:%02d",y ,m, d,InterfaceConfig.STARTHOUR , InterfaceConfig.STARTMIN));
			pick_up_endTime.setText(String.format("%d-%d-%d %02d:%02d",y ,m, d,InterfaceConfig.ENDHOUR ,InterfaceConfig.ENDMIN));

			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();
			calStart.set(y, m, d, InterfaceConfig.STARTHOUR,
					InterfaceConfig.STARTMIN);
			calEnd.set(y, m, d, InterfaceConfig.ENDHOUR, InterfaceConfig.ENDMIN);
			long milliseconds1 = calStart.getTimeInMillis();
			long milliseconds2 = calEnd.getTimeInMillis();
			long diff = milliseconds2 - milliseconds1;
			long diffHours = diff / (60 * 60 * 1000);

			total_time_leave_id.setText(diffHours+"");

			num_leave_id.setText(getIntent().getStringExtra("num"));
		}
		getJsonList(companyIdJson);
	}

	private OnTouchListener touch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			switch (v.getId()) {
			case R.id.type_leave_id:
				switch (action) {
				case MotionEvent.ACTION_UP:
					type_leave_id
							.setBackgroundResource(android.R.drawable.editbox_background);
					Gson gson = new Gson();
					Intent intent = new Intent(AskForLeaveActivity.this,
							GridRadio.class);
					intent.putExtra("data", gson.toJson(listMapType));
					intent.putExtra("resultCode", 2);
					String checkCode = type_leave_code.getText().toString()
							.trim().equals("") ? null : type_leave_code
							.getText().toString();
					intent.putExtra("checkCode", checkCode);

					startActivityForResult(intent, 1);
					break;
				case MotionEvent.ACTION_DOWN:
					type_leave_id.setBackgroundColor(Color.YELLOW);
					break;
				}
				break;
			case R.id.people_leave_id:
				switch (action) {
				case MotionEvent.ACTION_UP:
					people_leave_id
							.setBackgroundResource(android.R.drawable.editbox_background);
					Gson gson = new Gson();
					Intent intent = new Intent(AskForLeaveActivity.this,
							GridRadio.class);
					intent.putExtra("data", gson.toJson(listMapPeople));
					intent.putExtra("resultCode", 1);
					String checkCode = people_leave_code.getText().toString()
							.trim().equals("") ? null : people_leave_code
							.getText().toString();
					intent.putExtra("checkCode", checkCode);
					intent.putExtra("titleBar", 1);
					startActivityForResult(intent, 1);
					break;
				case MotionEvent.ACTION_DOWN:
					people_leave_id.setBackgroundColor(Color.YELLOW);
					break;
				}
				break;
			}
			return true;
		}
	};
 

	public void getJsonList(String json) {
		try {
			JSONArray jsonArray = new JSONArray(json);
			int len = jsonArray.length();
			listStr = new String[len];
			for (int i = 0; i < len; i++) {
				JSONObject jRoot = (JSONObject) jsonArray.get(i);
				user = new User();
				user.setName(jRoot.getString("name"));
				user.setId(jRoot.getInt("id"));
				user.setPositionID(jRoot.getInt("positionId"));
				user.setPositionName(jRoot.getString("positionName"));
				user.setDepartmentID(jRoot.getInt("deptId"));
				user.setDepartmentName(jRoot.getString("deptName"));
				userList.add(user);
				if(updateFlag){
					if(updateName.equals(user.getName())){
						people_leave_code.setText(user.getId()+"");
						people_leave_tv.setText(updateName);
						department_leave_id.setText(user.getDepartmentName());
						position_leave_id.setText(user.getPositionName());
						
						for(Map<String,String> m:listMapType){
							if(m.get("text2").equals(updateType)){
								type_leave_tv.setText(m.get("text2"));
								type_leave_code.setText(m.get("text1"));
							}
						}
					}
				}else{
					if (user.getId() == Integer.parseInt(userId)) {
						people_leave_code.setText(userId);
						people_leave_tv.setText(username);
						department_leave_id.setText(user.getDepartmentName());
						position_leave_id.setText(user.getPositionName());
					}
				}
				listStr[i] = jRoot.getString("name");
				Map<String, String> m = new HashMap<String, String>();
				m.put("text1", user.getId() + "");
				m.put("text2", jRoot.getString("name"));
				listMapPeople.add(m);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (sdDialog.isShow()) {
			sdDialog.cancel();
		}

	}

	/**
	 * 得到请求数据
	 * 
	 * @Title getParamList
	 * @return 方法注释
	 */
	public List<NameValuePair> getParamList() {
		params = new ArrayList<NameValuePair>();
		String code = num_leave_id.getText().toString().trim();

		String startDate = pick_up_startTime.getText().toString().trim();
		String endDate = pick_up_endTime.getText().toString().trim();
		String length = total_time_leave_id
				.getText()
				.toString()
				.trim();
		String remark = reason_leave_id.getText().toString().trim();
		String empId = people_leave_code.getText().toString();
		String companyId = mapconfig.get(InterfaceConfig.COMPANYID);
		String typeId = type_leave_code.getText().toString();
		
		//tls
		String createId = mapconfig.get(InterfaceConfig.ID);
		
		if (updateFlag) {
			params.add(new BasicNameValuePair("id", askId));
		} else {
			double i = Math.random();
			double j = Math.random();
			int in = (int) (i * 1000);
			int out = (int) (j * 1000);

			params.add(new BasicNameValuePair("code", in + "" + out));
		}

		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("createId", createId));// 当前登录ID
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("startDate", startDate));
		params.add(new BasicNameValuePair("endDate", endDate));
		params.add(new BasicNameValuePair("length", length));
		params.add(new BasicNameValuePair("remark", remark));
		params.add(new BasicNameValuePair("typeId", typeId));
		return params;
	}

	/*
	 * (非 Javadoc) <p>Title: onClick</p> <p>Description: 点击事件处理</p>
	 * 
	 * @param v
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View inview) {
		switch (inview.getId()) {
		case R.id.leave_bt_back:
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
			break;
		case R.id.leave_bt_pc_records:
			if (chechIsNull(pick_up_endTime) || chechIsNull(pick_up_startTime)) {
				msg = new Message();
				msg.what = 404;
				handler.sendMessage(msg);
				return;
			} else if (chechIsNull(total_time_leave_id)) {
				msg = new Message();
				msg.what = 405;
				handler.sendMessage(msg);
				return;
			}
			sdDialog.show();
			upLoadThread upLoadThread = new upLoadThread();
			new Thread(upLoadThread).start();

			break;
		case R.id.pick_up_startTime:// 选择开始时间
			setTimeText(pick_up_startTime,
					pick_up_endTime, total_time_leave_id, false);
			break;
		case R.id.pick_up_endTime:// 选择结束时间
			setTimeText(pick_up_endTime,
					pick_up_startTime, total_time_leave_id, true);
			break;
		default:
			break;
		}
	}

	/**
	 * 验证输入框中是否为空
	 * 
	 * @Title chechIsNull
	 * @param editText
	 * @return 方法注释
	 */
	public boolean chechIsNull(EditText editText) {
		boolean flag = false;
		String str = editText.getText().toString().trim();
		if (str == null || str.equals("")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 验证输入框中是否为空
	 * 
	 * @Title chechIsNull
	 * @param editText
	 * @return 方法注释
	 */
	public boolean chechIsNull(Button btnText) {
		boolean flag = false;
		String str = btnText.getText().toString().trim();
		if (str == null || str.equals("")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 请假记录上传
	 * 
	 * @ClassName upLoadThread
	 * @author Administrator
	 * @date 2014年8月5日 上午9:58:11
	 * 
	 */
	class upLoadThread implements Runnable {
		@Override
		public void run() {
			if (!NetCheckUtil.isNetworkAvailable(AskForLeaveActivity.this)) {
				Toast.makeText(AskForLeaveActivity.this, "网络有点不给力,请稍后重试!",
						Toast.LENGTH_SHORT).show();
				return;
			}

			String url = InterfaceConfig.ASKFORLEAVE_ADD_URL;
			SDHttpClient sdClient = new SDHttpClient();
			getParamList();
			msg = new Message();
			try {
				String json = sdClient.post_session(url, params);
				Log.d("json++", json);

				JSONObject jRoot = new JSONObject(json);
				String ResultCode = jRoot.getString("resultCode");
				if (ResultCode.equals("200")) {
					msg.what = 1;
					msg.obj = json;
					handler.sendMessage(msg);
				} else {
					msg.what = 4;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				msg.what = 500;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				intent = new Intent();
				intent.setClass(AskForLeaveActivity.this,
						LeaveRecodeActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				break;
			case 2:
				break;
			case 3:
				// updateUI3(msg.obj.toString());
				break;
			case 4:
				Toast.makeText(AskForLeaveActivity.this, "查询失败！", 1).show();
				break;
			case 5:
				String json = (String) msg.obj;
				getJsonList(json);

				break;
			case 404:
				Toast.makeText(AskForLeaveActivity.this, "选择起止时间！", 1).show();
				break;
			case 405:
				Toast.makeText(AskForLeaveActivity.this, "请输入总时长！", 1).show();
				break;
			case 500:
				Toast.makeText(AskForLeaveActivity.this, "网络异常！", 1).show();
				break;
			}
			if (sdDialog.isShow()) {
				sdDialog.cancel();
			}
		}
	};

	/**
	 * 
	 * @Title setTimeText
	 * @param t1
	 *            选中控件
	 * @param t2
	 *            比较控件
	 * @param t3
	 *            结果控件
	 * @param flag
	 *            计算结果时，判断减数与被减数 true 前者，false 后者
	 * 
	 */
	public void setTimeText(final TextView t1, final TextView t2,
			final TextView t3, final boolean flag) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		myView = View.inflate(this, R.layout.date_time_dialog, null);
		final DatePicker datePicker = (DatePicker) myView
				.findViewById(R.id.date_picker);
		final TimePicker timePicker = (android.widget.TimePicker) myView
				.findViewById(R.id.time_picker);
		builder.setView(myView);
		String str1 = "";
		str1 = t1.getText().toString();
		String[] ymd1 = str1.substring(0, str1.indexOf(" "))
				.split("-");
		String[] hm1 = str1.substring(str1.indexOf(" ")).trim()
				.split(":");
		int y1 = Integer.parseInt(ymd1[0]);
		int m1 = Integer.parseInt(ymd1[1]);
		int d1 = Integer.parseInt(ymd1[2]);
		int h1 = Integer.parseInt(hm1[0]);
		int mi1 = Integer.parseInt(hm1[1]);
		
		
		datePicker.init(y1, (m1-1),d1, null);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(h1);
		timePicker.setCurrentMinute(mi1);
		builder.setTitle("选取起始时间");

		builder.setPositiveButton("确  定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						StringBuffer sb = new StringBuffer();

						sb.append(String.format("%d-%d-%d %02d:%02d",

						datePicker.getYear(),

						datePicker.getMonth() + 1,

						datePicker.getDayOfMonth(),timePicker.getCurrentHour(),timePicker.getCurrentMinute()));

						Calendar calend = Calendar.getInstance();
						String str = "";
						str = t2.getText().toString();
						String[] ymd = str.substring(0, str.indexOf(" "))
								.split("-");
						String[] hm = str.substring(str.indexOf(" ")).trim()
								.split(":");
						int y = Integer.parseInt(ymd[0]);
						int m = Integer.parseInt(ymd[1]);
						int d = Integer.parseInt(ymd[2]);
						int h = Integer.parseInt(hm[0]);
						int mi = Integer.parseInt(hm[1]);
						calend.set(y, m, d, h, mi);

						Calendar calendar = Calendar.getInstance();
						calendar.set(datePicker.getYear(),
								datePicker.getMonth() + 1,
								datePicker.getDayOfMonth(),
								timePicker.getCurrentHour(),
								timePicker.getCurrentMinute());
						long milliseconds1 = calendar.getTimeInMillis();
						long milliseconds2 = calend.getTimeInMillis();

						int ry = 0;
						int rm = 0;
						int rd = 0;
						int rh = 0;
						int rmi = 0;

						long diff = 0;
						if (!flag) {
							diff = milliseconds2 - milliseconds1;
							ry = y - datePicker.getYear();
							rm = m - (datePicker.getMonth() + 1);
							rd = d - datePicker.getDayOfMonth();
							rh = h - timePicker.getCurrentHour();
							rmi = mi - timePicker.getCurrentMinute();
						} else {
							diff = milliseconds1 - milliseconds2;
							ry = datePicker.getYear() - y;
							rm = datePicker.getMonth() + 1 - m;
							rd = datePicker.getDayOfMonth() - d;
							rh = timePicker.getCurrentHour() - h;
							rmi = timePicker.getCurrentMinute() - mi;
						}

						//1.年 -->天-->小时
						/**
						 * 1年*12月*21天*8 = 小时
						 * 1天=8小时
						 */
						int ny = ry*12*21*8;//请N年假的小时数
						int nm = rm*21*8;//请N个月假的小时数
						int nd = rd*8;//请N天的小时数
						BigDecimal nmi = new BigDecimal(rmi).divide(new BigDecimal("60"), 1,
								BigDecimal.ROUND_UP);
						Double nh = ny+nm+nd+rh+Double.parseDouble(String.valueOf(nmi));
						if (diff < 0) {
							Toast.makeText(getApplicationContext(),
									"开始时间不能大于结束时间", Toast.LENGTH_LONG).show();
							return;
						}
						t1.setText(sb.toString());
						t3.setText(nh+"");
						dialog.cancel();

					}

				});

		Dialog dialog = builder.create();
		InputMethodManager imm = (InputMethodManager) getApplicationContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		dialog.show();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		String code = data.getStringExtra("text1");
		String name = data.getStringExtra("text2");
		switch (resultCode) {
		case 1:
			int c = Integer.parseInt(code);
			if (c != -1) {
				people_leave_code.setText(code);
				people_leave_tv.setText(name);
				department_leave_id
						.setText(userList.get(c).getDepartmentName());
				position_leave_id.setText(userList.get(c).getPositionName());
			}

			break;
		case 2: 
			int cc = Integer.parseInt(code);
			if (cc != -1) {
				type_leave_code.setText(code);
				type_leave_tv.setText(name);
			}
			break;
		default:
			break;
		}
	}
}
