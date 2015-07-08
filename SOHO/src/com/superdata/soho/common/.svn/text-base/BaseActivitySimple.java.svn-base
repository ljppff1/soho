package com.superdata.soho.common;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 基类
 * 
 * @author 谭树林 
 * 
 */
public class BaseActivitySimple extends Activity  implements OnClickListener{

	public static final int EXIT_APPLICATION = 0x0001;
    @Override
	public void onClick(View v) {}
    public int getMetricsWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return  dm.widthPixels;
	}
}
