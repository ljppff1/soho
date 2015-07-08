package com.superdata.soho.activity.workattendance;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivitySimple;
import com.superdata.soho.common.FileUtils;
import com.superdata.soho.common.MyAlertDialog;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.PicInfo;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.MapApplication;
import com.superdata.soho.util.SharedPreferencesConfig;

import java.util.Date;
import android.os.Handler;
public class XcqzShowImage extends BaseActivitySimple {

	ImageView imageshow;
	Button uploadBtn, saveBtn, backBtn, delBtn;
	MapApplication app;
	Bitmap bitmap;
	FileUtils fileUtils;
	String imagename = null;
	Map<String, String> mapconfig;
	PicInfo picInfo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xcqz_show_excute_image);
		findViewById();
		app = (MapApplication) getApplication();
		bitmap = app.getBitmap();
		imageshow.setImageBitmap(bitmap);
		uploadBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
		saveBtn.setOnClickListener(this);
		delBtn.setOnClickListener(this);
		fileUtils = new FileUtils(this);
		mapconfig = SharedPreferencesConfig.config(XcqzShowImage.this);
		picInfo = (PicInfo) getIntent().getSerializableExtra("picInfo");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backBtn:
			finish();
			break;
		case R.id.saveBtn:
			String userId = mapconfig.get(InterfaceConfig.ID);
			imagename = new SimpleDateFormat("yyyyMMddHHmmss")
					.format(new Date());
			imagename = userId + "_" + imagename + ".png";
			try {
				fileUtils.savaBitmap(imagename, bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
			delBtn.setEnabled(true);
			Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
			saveBtn.setEnabled(false);
			break;
		case R.id.uploadBtn:
			uploadBtn.setEnabled(false);
			MyAlertDialog.dialogShow(this);
			new Thread(new Runnable() {

				@Override
				public void run() {
					SDHttpClient sdClient = new SDHttpClient();
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					try {
						// 1.先保存图片到SD卡
						String userId = mapconfig.get(InterfaceConfig.ID);
						String imagename = new SimpleDateFormat(
								"yyyyMMddHHmmss").format(new Date());
						imagename = userId + "_" + imagename + ".png";
						getParaments(params,imagename);
						//2.上传图片
						fileUtils.savaBitmap(imagename, bitmap);
//						File uploadFile = fileUtils.getFile(imagename);
						
//						String json = sdClient.post_upload(InterfaceConfig.UPLOADFILE,
//								params,fileUtils.getFile(imagename));
						String json = "";//sdClient.post_upload(InterfaceConfig.UPLOADFILE,
//								params,fileUtils.getFile(imagename));
//						//3.删除图片
//						fileUtils.deleteFile(imagename);
						
						Message msg = new Message();
						msg.obj =json;
						handlerImage.sendMessage(msg);
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				/**
				 * @Title getParaments
				 * @param params 方法注释
				 * @param imagename 
				 */
				private void getParaments(List<NameValuePair> params, String imagename) {
					params.add(new BasicNameValuePair("companyId", mapconfig.get(InterfaceConfig.COMPANYID)));
					params.add(new BasicNameValuePair("empId",mapconfig.get(InterfaceConfig.ID)));
					params.add(new BasicNameValuePair("latitude", picInfo.getLatitude()));
					params.add(new BasicNameValuePair("longitude", picInfo.getLongitude()));
					params.add(new BasicNameValuePair("address",picInfo.getPostion()));
					params.add(new BasicNameValuePair("customerId", picInfo.getCustomer()));
					params.add(new BasicNameValuePair("remark", picInfo.getInfo()));
					params.add(new BasicNameValuePair("picture",imagename));
				}
			}).start();
			break;
		case R.id.delBtn:
			if (null != imagename) {
				delBtn.setEnabled(false);
				fileUtils.deleteFile(imagename);
				Toast.makeText(XcqzShowImage.this, "删除成功", Toast.LENGTH_LONG)
						.show();
				saveBtn.setEnabled(true);
			}
			break;
		default:
			break;
		}
	}

	private Handler handlerImage = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			MyAlertDialog.dialogDismiss();
			Toast.makeText(XcqzShowImage.this, (String)msg.obj,Toast.LENGTH_SHORT).show();
		};
	};

	private void findViewById() {
		imageshow = (ImageView) findViewById(R.id.imageshow);
		uploadBtn = (Button) findViewById(R.id.uploadBtn);
		saveBtn = (Button) findViewById(R.id.saveBtn);
		backBtn = (Button) findViewById(R.id.backBtn);
		delBtn = (Button) findViewById(R.id.delBtn);
	}

	@Override
	protected void onDestroy() {
		app.getBitmap().recycle();
		super.onDestroy();
	}
}
