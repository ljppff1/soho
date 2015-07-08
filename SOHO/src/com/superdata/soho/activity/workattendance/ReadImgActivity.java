package com.superdata.soho.activity.workattendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.superdata.soho.R;

import android.graphics.BitmapFactory;
import android.widget.ImageView;
/**
 * 查看图片
 * @ClassName ReadImgActivity
 * @author Administrator
 * @date 2014年7月14日 上午10:05:54
 *
 */
public class ReadImgActivity extends Activity {

	public ImageView imgView;
	public Button pic_bt_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.img_view);
		imgView = (ImageView) findViewById(R.id.img);
		Intent intent = getIntent();
		String filepath = intent.getStringExtra("path");
		pic_bt_back=(Button) findViewById(R.id.pic_bt_back);
		pic_bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				
			}
		});
		// openFileInput(filepath);
		imgView.setImageBitmap(BitmapFactory.decodeFile(filepath));
		imgView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
