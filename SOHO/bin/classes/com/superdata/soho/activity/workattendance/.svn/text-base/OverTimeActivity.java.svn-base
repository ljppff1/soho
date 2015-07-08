/**  
 * @Title: OverTimeActivity.java
 * @Package com.superdata.soho.activity
 * @Description: TODO(用一句话描述该文件做什么)
 * @author A18ccms A18ccms_gmail_com  
 * @date 2014年6月11日 下午4:19:50
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
import com.superdata.soho.entity.OverTime;
import com.superdata.soho.entity.User;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.NetCheckUtil;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.SDProgressDialog;
/**
 * @ClassName: OverTimeActivity
 * @Description: 加班页面
 * @author luolei
 * @date 2014年6月11日 下午4:19:50
 * 
 */
public class OverTimeActivity extends BaseActivity implements OnClickListener {

	private List<String> list = new ArrayList<String>();
	Button overtime_bt_back, overtime_bt_pc_records;
	ArrayAdapter<String> adapter;
	List<User> userList;
	SDProgressDialog sdDialog;
	Map<String, String> mapconfig;
	Message msg;
	View myView;
	List<NameValuePair> params;
	Intent intent, getIntent;
	User user;
	EditText department_overtime_id, position_overtime_id, overtime_num_id,
			total_time_overtime_id, reason_overtime_id;
	private String askId;
	boolean updateFlag = false;
	String[] listStr;

	RelativeLayout people_overtime_id, type_overtime_id;
	List<Map<String, String>> listMapPeople = new ArrayList<Map<String, String>>();
	List<Map<String, String>> listMapType = new ArrayList<Map<String, String>>();
	TextView people_overtime_tv, people_overtime_code, type_overtime_tv,
			type_overtime_code;
	Button start_time_overtime_id, end_time_overtime_id;
	String userId, username;
	String updateName,updateType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overtime_activity);
		init();

	}

	public void init() {
		sdDialog = new SDProgressDialog(OverTimeActivity.this);
		sdDialog.show();
		userList = new ArrayList<User>();
		mapconfig = SharedPreferencesConfig.config(OverTimeActivity.this);
		people_overtime_id = (RelativeLayout) findViewById(R.id.people_overtime_id);
		type_overtime_id = (RelativeLayout) findViewById(R.id.type_overtime_id);
		people_overtime_id.setOnTouchListener(touch);
		type_overtime_id.setOnTouchListener(touch);
		department_overtime_id = (EditText) findViewById(R.id.department_overtime_id);
		position_overtime_id = (EditText) findViewById(R.id.position_overtime_id);
		overtime_num_id = (EditText) findViewById(R.id.overtime_num_id);
		start_time_overtime_id = (Button) findViewById(R.id.start_time_overtime_id);
		end_time_overtime_id = (Button) findViewById(R.id.end_time_overtime_id);
		total_time_overtime_id = (EditText) findViewById(R.id.total_time_overtime_id);
		reason_overtime_id = (EditText) findViewById(R.id.reason_overtime_id);
		overtime_bt_back = (Button) findViewById(R.id.overtime_bt_back);
		overtime_bt_pc_records = (Button) findViewById(R.id.overtime_bt_pc_records);
		people_overtime_tv = (TextView) findViewById(R.id.people_overtime_tv);
		people_overtime_code = (TextView) findViewById(R.id.people_overtime_code);
		type_overtime_tv = (TextView) findViewById(R.id.type_overtime_tv);
		type_overtime_code = (TextView) findViewById(R.id.type_overtime_code);
		list.add("平时加班");
		list.add("休息日加班");
		list.add("节日加班");

		start_time_overtime_id.setOnClickListener(this);
		end_time_overtime_id.setOnClickListener(this);

		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("text1", "39");
		map1.put("text2", "平时加班");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("text1", "42");
		map2.put("text2", "周末加班");
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("text1", "43");
		map3.put("text2", "周末加班1");
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("text1", "44");
		map4.put("text2", "周末加班2");
		listMapType.add(map1);
		listMapType.add(map2);
		listMapType.add(map3);
		listMapType.add(map4);

		overtime_bt_back.setOnClickListener(this);
		overtime_bt_pc_records.setOnClickListener(this);

		OverTime overTime = new OverTime();
		getIntent = getIntent();
		overTime = (OverTime) getIntent.getSerializableExtra("update");
		if (overTime != null) {
			askId = overTime.getCode();
			overtime_num_id.setText(overTime.getCode());

			String startStr = overTime.getStartTime();
			String[] ymd = startStr.substring(0, startStr.indexOf(" ")).split(
					"-");
			String[] hm = startStr.substring(startStr.indexOf(" ")).trim()
					.split(":");
			int y = Integer.parseInt(ymd[0]);
			int m = Integer.parseInt(ymd[1]);
			int d = Integer.parseInt(ymd[2]);
			int h = Integer.parseInt(hm[0]);
			int mi = Integer.parseInt(hm[1]);

			String endStr = overTime.getEndTime();
			String[] ymdend = endStr.substring(0, endStr.indexOf(" ")).split(
					"-");
			String[] hmend = endStr.substring(endStr.indexOf(" ")).trim()
					.split(":");
			int ye = Integer.parseInt(ymdend[0]);
			int me = Integer.parseInt(ymdend[1]);
			int de = Integer.parseInt(ymdend[2]);
			int he = Integer.parseInt(hmend[0]);
			int mie = Integer.parseInt(hmend[1]);
			start_time_overtime_id.setText(String.format("%d-%d-%d %02d:%02d",
					y, m, d, h, mi));
			end_time_overtime_id.setText(String.format("%d-%d-%d %02d:%02d",
					ye, me, de, he, mie));
			total_time_overtime_id.setText(overTime.getTotalTime() + "");
			reason_overtime_id.setText(overTime.getRecordReason());
			updateFlag = true;
			updateName = overTime.getUserName();
			updateType = overTime.getTypeName();
		} else {
			// 设置默认值
			type_overtime_tv.setText("平时加班");
			type_overtime_code.setText("39");
			Map<String, String> mapconfig = SharedPreferencesConfig
					.config(OverTimeActivity.this);
			userId = mapconfig.get(InterfaceConfig.ID);
			username = mapconfig.get(InterfaceConfig.LOGIN_USER_NAME);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			int y = cal.get(Calendar.YEAR);
			int m = cal.get(Calendar.MONTH) + 1;
			int d = cal.get(Calendar.DAY_OF_MONTH);
			start_time_overtime_id.setText(String.format("%d-%d-%d %02d:%02d",
					y, m, d, InterfaceConfig.STARTHOUR,
					InterfaceConfig.STARTMIN));
			end_time_overtime_id.setText(String.format("%d-%d-%d %02d:%02d", y,
					m, d, InterfaceConfig.ENDHOUR, InterfaceConfig.ENDMIN));

			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();
			calStart.set(y, m, d, InterfaceConfig.STARTHOUR,
					InterfaceConfig.STARTMIN);
			calEnd.set(y, m, d, InterfaceConfig.ENDHOUR, InterfaceConfig.ENDMIN);
			long milliseconds1 = calStart.getTimeInMillis();
			long milliseconds2 = calEnd.getTimeInMillis();
			long diff = milliseconds2 - milliseconds1;
			long diffHours = diff / (60 * 60 * 1000);
			total_time_overtime_id.setText(diffHours + "");
			overtime_num_id.setText(getIntent().getStringExtra("num"));
		}
		getJsonList(companyIdJson);
	}

	private OnTouchListener touch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			switch (v.getId()) {
			case R.id.people_overtime_id:
				switch (action) {
				case MotionEvent.ACTION_UP:
					people_overtime_id
							.setBackgroundResource(android.R.drawable.editbox_background);
					Gson gson = new Gson();
					Intent intent = new Intent(OverTimeActivity.this,
							GridRadio.class);
					intent.putExtra("data", gson.toJson(listMapPeople));
					intent.putExtra("resultCode", 1);
					String checkCode = people_overtime_code.getText()
							.toString().trim().equals("") ? null
							: people_overtime_code.getText().toString();
					intent.putExtra("checkCode", checkCode);
					intent.putExtra("titleBar", 1);

					startActivityForResult(intent, 1);
					break;
				case MotionEvent.ACTION_DOWN:
					people_overtime_id.setBackgroundColor(Color.YELLOW);
					break;
				}
				break;
			case R.id.type_overtime_id:
				switch (action) {
				case MotionEvent.ACTION_UP:
					type_overtime_id
							.setBackgroundResource(android.R.drawable.editbox_background);
					Gson gson = new Gson();
					Intent intent = new Intent(OverTimeActivity.this,
							GridRadio.class);
					intent.putExtra("data", gson.toJson(listMapType));
					intent.putExtra("resultCode", 2);
					String checkCode = type_overtime_code.getText().toString()
							.trim().equals("") ? null : type_overtime_code
							.getText().toString();
					intent.putExtra("checkCode", checkCode);

					startActivityForResult(intent, 1);
					break;
				case MotionEvent.ACTION_DOWN:
					type_overtime_id.setBackgroundColor(Color.YELLOW);
					break;
				}
				break;
			}
			return true;
		}
	};

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				intent = new Intent();
				intent.setClass(OverTimeActivity.this,
						OverTimeRecordActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				break;
			case 4:
				Toast.makeText(OverTimeActivity.this, "查询失败！",Toast.LENGTH_LONG).show();
				break;
			case 5:
				String json = (String) msg.obj;
				getJsonList(json);

				break;
			case 404:
				Toast.makeText(OverTimeActivity.this, "请输入起止时间！", Toast.LENGTH_LONG).show();
				break;
			case 405:
				Toast.makeText(OverTimeActivity.this, "请输入请假总时长！", Toast.LENGTH_LONG).show();
				break;
			case 500:
				Toast.makeText(OverTimeActivity.this, "网络异常！", Toast.LENGTH_LONG).show();
				break;
			}
			if (sdDialog.isShow()) {
				sdDialog.cancel();
			}
		}
	};

	public void getJsonList(String json) {
		try {
			JSONArray jsonArray = new JSONArray(json);
			int len = jsonArray.length();
			listStr = new String[len];
			for (int i = 0; i < jsonArray.length(); i++) {
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
						people_overtime_code.setText(user.getId()+"");
						people_overtime_tv.setText(updateName);
						department_overtime_id.setText(user.getDepartmentName());
						position_overtime_id.setText(user.getPositionName());
						
						for(Map<String,String> m:listMapType){
							if(m.get("text2").equals(updateType)){
								type_overtime_tv.setText(m.get("text2"));
								type_overtime_code.setText(m.get("text1"));
							}
						}
					}
				}else{
					if (user.getId() == Integer.parseInt(userId)) {
						people_overtime_code.setText(userId);
						people_overtime_tv.setText(username);
						department_overtime_id.setText(user.getDepartmentName());
						position_overtime_id.setText(user.getPositionName());
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

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.overtime_bt_back:

			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
			break;
		case R.id.overtime_bt_pc_records:

			if (chechIsNull(start_time_overtime_id)
					|| chechIsNull(end_time_overtime_id)) {
				msg = new Message();
				msg.what = 404;
				handler.sendMessage(msg);
				return;
			} else if (chechIsNull(total_time_overtime_id)) {
				msg = new Message();
				msg.what = 405;
				handler.sendMessage(msg);
				return;
			}
			sdDialog.show();
			upLoadThread upLoadThread = new upLoadThread();
			new Thread(upLoadThread).start();
			break;
		case R.id.start_time_overtime_id:
			setTimeText(start_time_overtime_id, end_time_overtime_id,
					total_time_overtime_id, false);
			break;
		case R.id.end_time_overtime_id:
			setTimeText(end_time_overtime_id, start_time_overtime_id,
					total_time_overtime_id, true);
			break;
		default:
			break;
		}
	}

	class upLoadThread implements Runnable {
		@Override
		public void run() {
			if (!NetCheckUtil.isNetworkAvailable(OverTimeActivity.this)) {
				Toast.makeText(OverTimeActivity.this, "网络有点不给力,请稍后重试!",
						Toast.LENGTH_SHORT).show();
				return;
			}

			String url = InterfaceConfig.OVERTIME_ADD_URL;
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

	public List<NameValuePair> getParamList() {
		params = new ArrayList<NameValuePair>();
//		String code = overtime_num_id.getText().toString().trim();

		String startDate = start_time_overtime_id.getText().toString().trim();
		String endDate = end_time_overtime_id.getText().toString().trim();
		String length = total_time_overtime_id.getText().toString().trim();
		String remark = reason_overtime_id.getText().toString().trim();
		String createId = mapconfig.get(InterfaceConfig.ID);
		String companyId = mapconfig.get(InterfaceConfig.COMPANYID);
		String typeId = type_overtime_code.getText().toString();
		String empId = people_overtime_code.getText().toString();
		if (updateFlag) {// 修改
			params.add(new BasicNameValuePair("id", askId));
		} else {// 新增
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

	public boolean chechIsNull(Button btnText) {
		boolean flag = false;
		String str = btnText.getText().toString().trim();
		if (str == null || str.equals("")) {
			flag = true;
		}
		return flag;
	}

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
		case 1://申请人
			int c = Integer.parseInt(code);
			if (c != -1) {
				people_overtime_code.setText(code);
				people_overtime_tv.setText(name);
				department_overtime_id.setText(userList.get(c)
						.getDepartmentName());
				position_overtime_id.setText(userList.get(c).getPositionName());
			}

			break;
		case 2://加班类型
			int cc = Integer.parseInt(code);
			if(cc !=-1){
				type_overtime_code.setText(code);
				type_overtime_tv.setText(name);
			}
			break;
		default:
			break;
		}
	}
}
