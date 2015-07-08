package com.superdata.soho.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
 

public class ListViewAdapterListener {
	private Context context;
	private Activity activity;
	private List<Map<String, Object>> list;

	private View convertView;
	private int position;
	int width;
	int height;
	private BaseAdapter adapter;
	private ListViewItems listItems;
	private String checkCode;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ListViewAdapterListener(BaseAdapter adapter, Context mcontext,
			View convertView, List<Map<String, Object>> mlist, int position,
			String methodName, String checkCode) {
		this.context = mcontext;
		this.activity = (Activity) mcontext;
		this.list = mlist;
		this.convertView = convertView;
		this.position = position;
		this.adapter = adapter;
		listItems = new ListViewItems(convertView);
		this.checkCode = checkCode;
		// 初始化屏幕宽度\高度
		getMetricsWidth();
		Class clazz = this.getClass();
		try {
			Method method = clazz.getDeclaredMethod(methodName);
			method.invoke(this);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}
 

	/**
	 * 屏幕宽度
	 * 
	 * @return
	 */
	private void getMetricsWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}

	// 行政区划、质监局 列表
	public void addListenerCity() {
		if (list.size() == 0)
			return;
		int w = width / 16;
		RadioButton radio = listItems.getRadio();
		radio.setWidth(w * 2);
		TextViewUtils t2 = listItems.getText2(w * 14);
		t2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		t2.setMinHeight(height / 12);

		TextViewUtils t1 = listItems.getText1(0);
		t1.setVisibility(false);
		if (t1.getText().equals(checkCode)) {
			radio.setChecked(true);
		}

	}
}
