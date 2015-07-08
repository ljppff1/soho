package com.superdata.soho.activity.workattendance;

import java.io.IOException;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivitySimple;
import com.superdata.soho.entity.PicInfo;
import com.superdata.soho.util.MapApplication;
/**
 * 拍照
 * 
 * @author 谭树林
 * 
 */
public class XcqzTakeImage extends BaseActivitySimple {
	private SurfaceView surface;
	private SurfaceHolder holder;
	private Camera camera;// 声明相机
	GestureDetector gestureDetector;

	MapApplication app;

	PicInfo picInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
		this.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 拍照过程屏幕一直处于高亮

		// 设置手机屏幕朝向，一共有7种
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.xcqz_take_image);
		app = (MapApplication) getApplication();
		surface = (SurfaceView) findViewById(R.id.camera_surface);
		picInfo = (PicInfo) getIntent().getSerializableExtra("picInfo");
		holder = surface.getHolder();// 获得句柄
		holder.addCallback(new Callback() {
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// 当surfaceview关闭时，关闭预览并释放资源
				camera.stopPreview();
				camera.release();
				camera = null;
				holder = null;
				surface = null;
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				if (camera == null) {
					camera = Camera.open();
					camera.setDisplayOrientation(90);
					try {
						camera.setPreviewDisplay(holder);// 通过surfaceview显示取景画面
						camera.startPreview();// 开始预览
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});
		gestureDetector = new GestureDetector(this, new MyGestureListener());
	}

	// 创建jpeg图片回调数据对象
	PictureCallback jpeg = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);
				camera.stopPreview();// 关闭预览 处理数据
				app.setBitmap(bitmap);
				Intent intent = new Intent(XcqzTakeImage.this,
						XcqzShowImage.class);
				intent.putExtra("picInfo", picInfo);
				XcqzTakeImage.this.startActivity(intent);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	class MyGestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			camera.autoFocus(new AutoFocusCallback() {// 自动对焦
				@Override
				public void onAutoFocus(boolean success, Camera camera) {
					if (success) {
						// 设置参数，并拍照
						Parameters params = camera.getParameters();
						params.setPictureFormat(PixelFormat.JPEG);// 图片格式
						params.setPreviewSize(800, 480);// 图片大小
						params.set("rotation", 90);
						camera.setParameters(params);// 将参数设置到我的camera

						camera.takePicture(null, null, jpeg);// 将拍摄到的照片给自定义的对象
					}
				}
			});
			return super.onSingleTapConfirmed(e);
		}
	}
}
