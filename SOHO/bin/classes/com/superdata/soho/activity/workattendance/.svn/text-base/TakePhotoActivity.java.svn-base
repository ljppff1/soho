/**
 * @Title TakePhotoActivity.java
 * @Package com.superdata.soho.activity
 * @author Administrator
 * @date 2014年7月1日 上午9:11:41
 * @version V1.0
 */
package com.superdata.soho.activity.workattendance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.util.SocketHttpRequester;

/**
 * 拍照上传
 * 自定义拍照可扩张
 * @ClassName TakePhotoActivity
 * @author Administrator
 * @date 2014年7月1日 上午9:11:41
 * 
 */
public class TakePhotoActivity extends BaseActivity implements
		SurfaceHolder.Callback, OnClickListener {

	Button bt_takephoto_back, bt_takephoto_confirm, bt_takephoto_upload,
			bt_takephoto_continue;
	SurfaceView mSurfaceView;
	private SurfaceHolder holder;
	private Camera mCamera;
	private int count;
	private AutoFocusCallback mAutoFocusCallback = new AutoFocusCallback();
	private Bitmap bmp = null;
	private String path = "SDApp";
	SimpleDateFormat sdf;
	File n;
	FileOutputStream bos;
	SocketHttpRequester socketHttp;

	/*
	 * (非 Javadoc)
	 * 
	 * @see com.superdata.soho.common.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.takephoto_activity);
		init();
	}

	public void init() {
		bt_takephoto_back = (Button) findViewById(R.id.bt_takephoto_back);
		bt_takephoto_back.setOnClickListener(this);
		bt_takephoto_confirm = (Button) findViewById(R.id.bt_takephoto_confirm);
		bt_takephoto_confirm.setOnClickListener(this);
		bt_takephoto_upload = (Button) findViewById(R.id.bt_takephoto_upload);
		bt_takephoto_upload.setOnClickListener(this);
		bt_takephoto_continue = (Button) findViewById(R.id.bt_takephoto_continue);
		bt_takephoto_continue.setOnClickListener(this);
		socketHttp=new SocketHttpRequester(TakePhotoActivity.this);
		mSurfaceView = (SurfaceView) findViewById(R.id.mSurfaceView);
		holder = mSurfaceView.getHolder();
		holder.addCallback(TakePhotoActivity.this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_takephoto_back://返回
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
			break;
		case R.id.bt_takephoto_confirm: //拍照
			mCamera.autoFocus(mAutoFocusCallback);

			bt_takephoto_confirm.setVisibility(View.GONE);
			bt_takephoto_upload.setVisibility(View.VISIBLE);
			bt_takephoto_continue.setVisibility(View.VISIBLE);

			break;
		case R.id.bt_takephoto_continue://继续
			initCamera();
			bt_takephoto_confirm.setVisibility(View.VISIBLE);
			bt_takephoto_upload.setVisibility(View.GONE);
			bt_takephoto_continue.setVisibility(View.GONE);
			break;
		case R.id.bt_takephoto_upload: //上传

			if (bmp != null) {
				/* 检查SDCard是否存在 */
				if (!Environment.MEDIA_MOUNTED.equals(Environment
						.getExternalStorageState())) {
					/* SD卡不存在，显示Toast信息 */
					Toast.makeText(TakePhotoActivity.this, "SD卡不存在!上传失败！",
							Toast.LENGTH_LONG).show();
				} else {
					try {
						/* 文件不存在就创建 */
						File f = new File(
								Environment.getExternalStorageDirectory(), path);

						if (!f.exists()) {
							
							f.mkdir();
						}

						sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						String takephoto_input_str = sdf.format(new java.util.Date());
						n = new File(f, takephoto_input_str+".jpg");

						bos = new FileOutputStream(n.getPath());
						/* 文件转换 */
						bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
						/* 调用flush()方法，更新BufferStream */
						bos.flush();
						/* 结束OutputStream */
						bos.close();
						Toast.makeText(TakePhotoActivity.this,
								takephoto_input_str + ".jpg保存成功!", Toast.LENGTH_LONG).show();
						
//						uploadThread uThread=new uploadThread();
//						new Thread(uThread).start();
						

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (!bmp.isRecycled())
				bmp.recycle();

			break;
		default:
			break;
		}

	}
/**
 * 开始上传
 * @ClassName uploadThread
 * @author Administrator
 * @date 2014年8月15日 下午4:42:16
 *
 */
	class uploadThread implements Runnable{

		/* (非 Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			
			try {
				socketHttp.upoloadFile(n.getAbsolutePath());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public final class AutoFocusCallback implements
			android.hardware.Camera.AutoFocusCallback {
		@Override
		public void onAutoFocus(boolean focused, Camera camera) {
			/* 对到焦点拍照 */
			takePicture();

		};

		private void takePicture() {
			if (mCamera != null) {
				mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
			}
		}

		private ShutterCallback shutterCallback = new ShutterCallback() {
			@Override
			public void onShutter() {
				/* 按兀快门瞬间会呼?这里的程序 */
			}
		};

		private PictureCallback rawCallback = new PictureCallback() {
			@Override
			public void onPictureTaken(byte[] _data, Camera _camera) {
				/* 要处理raw data?写?否 */
			}
		};

		private PictureCallback jpegCallback = new PictureCallback() {
			@Override
			public void onPictureTaken(byte[] _data, Camera _camera) {
				/* 取得相片 */
				try {
					/*
					 * 设定Button可视性 mButton.setVisibility(View.GONE);
					 * mButton1.setVisibility(View.VISIBLE);
					 * mButton2.setVisibility(View.VISIBLE);
					 */
					/* 取得相片Bitmap对象 */
					bmp = BitmapFactory.decodeByteArray(_data, 0, _data.length);
					stopCamera();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}

	/* 相机初始化的method */
	private void initCamera() {
		if (mCamera != null) {
			try {
				mCamera.setDisplayOrientation(90);
				Camera.Parameters parameters = mCamera.getParameters();
				parameters.setPictureFormat(PixelFormat.JPEG);
				// parameters.setPictureSize(2560, 1920);
				parameters.setPictureSize(1024, 768);
				// parameters.setPictureSize(720, 480);

				mCamera.setParameters(parameters);
				mCamera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* 停止相机的method */
	private void stopCamera() {
		if (mCamera != null) {
			try {
				/* 停止预览 */
				mCamera.stopPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		initCamera();
		count++;
		Log.i("changed", count + "times");

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			/* 打开相机 */
			mCamera = Camera.open();
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopCamera();
		mCamera.release();
		mCamera = null;

	}
}
