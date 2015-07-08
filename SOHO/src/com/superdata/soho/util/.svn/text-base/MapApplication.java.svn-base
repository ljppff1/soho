package com.superdata.soho.util;

import android.app.Application;
import android.graphics.Bitmap;

import com.baidu.mapapi.SDKInitializer;

public class MapApplication extends Application {
	public static MapApplication app;// this

	private Bitmap bitmap;
	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
	}
	/**
	 * @return bitmap
	 */
	public Bitmap getBitmap() {
		return bitmap;
	}
	/**
	 * @param bitmap 要设置的 bitmap
	 */
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
 
}
