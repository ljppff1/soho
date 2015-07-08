package com.superdata.soho.activity.workattendance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivitySimple;
import com.superdata.soho.common.FileUtils;
import com.superdata.soho.common.MyAlertDialog;
import com.superdata.soho.common.MyAlertDialog.DialogYesCallBack;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.PicInfo;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
import android.os.Handler;
import android.view.View.OnClickListener;
/**
 * 图片上传
 * @author 谭树林
 *
 */
public class XcqzShowPicUpload extends BaseActivitySimple {

	private String result = "";
	Map<String, String> mapconfig;
	PicInfo picInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xcqz_upload_pic);
		Intent intent = getIntent();
		ImageView img = (ImageView) findViewById(R.id.photoContent);
		Button uploadBtn = (Button) findViewById(R.id.uploadBtn);
		picInfo = (PicInfo) getIntent().getSerializableExtra("picInfo");
		String flag = intent.getStringExtra("flag");
		mapconfig = SharedPreferencesConfig.config(XcqzShowPicUpload.this);
		if(flag.equals("none")){
			uploadBtn.setVisibility(View.GONE);
		}
		final String fileName = intent.getStringExtra("fileName");
		final FileUtils fileUtils = new FileUtils(this);
		String path = FileUtils.getStorageDirectory();
		final File file = new File(path);
		String filepath = null;
		if (file.list().length > 0) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (fileName.equals(f.getName())) {
					filepath = f.getPath();
				}
			}
		}
		if (null != filepath) {
			Bitmap bitmap = BitmapFactory.decodeFile(filepath);
			img.setImageBitmap(bitmap);
		}
		uploadBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						String uploadFile = null;
						if (file.list().length > 0) {
							File[] files = file.listFiles();
							for (File f : files) {
								if (fileName.equals(f.getName())) {
									uploadFile = f.getPath();
								}
							}
						}
						File file = new File(uploadFile);
						InputStream is = null;
						try {
							is = new FileInputStream(file);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						if(null != is){
							SDHttpClient sdClient = new SDHttpClient();
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							getParaments(params,file.getName());
							try {
//								result = sdClient.post_upload(InterfaceConfig.UPLOADFILE,
//										params,fileUtils.getFile(file.getName()));
							} catch (Exception e) {
								e.printStackTrace();
							}
							handlerImage.sendEmptyMessage(0);
						}
					}

					private void getParaments(List<NameValuePair> params,
							String imagename) {
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

			}
		});
	}

	private Handler handlerImage = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			new MyAlertDialog(XcqzShowPicUpload.this, "提示", result,new DialogYesCallBack() {
				@Override
				public void yes() {
					XcqzShowPicUpload.this.finish();
				};
			});
		};
	};
}
