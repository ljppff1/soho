package com.superdata.soho.common;

import java.util.Calendar;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.TimePicker;


/**
 * 通用弹出框  采用回调函数
 * @author 谭树林 
 *
 */
public class MyAlertDialog {

	Builder builder;
	private static ProgressDialog mSaveDialog = null;

	/**
	 * 确定 取消 两个按钮
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param callBackYes
	 *            确定按钮 返回方法
	 * @param callBackNo
	 *            取消按钮 返回方法
	 */
	public MyAlertDialog(Context context, String title, String message,
			DialogYesCallBack callBackYes, DialogNoCallBack callBackNo) {
		builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		DialogYes(callBackYes);
		DialogNo(callBackNo);
		builder.create().show();
	}

	/**
	 * 只有一个确定按钮
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param callBackYes
	 */
	public MyAlertDialog(Context context, String title, String message,
			DialogYesCallBack callBackYes) {
		builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		DialogYes(callBackYes);
		builder.create().show();
	}

	public void DialogYes(final DialogYesCallBack callBackYes) {
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				callBackYes.yes();
			}
		});
	}

	public void DialogNo(final DialogNoCallBack callBackNo) {
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (null != callBackNo)
					callBackNo.no();
			}
		});
	}

	public static void dialogShow(Context context) {
		mSaveDialog = ProgressDialog.show(context, "正在处理", "请稍等...", true);
	}

	public static void dialogDismiss() {
		mSaveDialog.dismiss();
	}

	public static void showTimeDialog(Context context,final TimeDialogCallBack timeCallback) {
		TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker timerPicker, int hourOfDay,
					int minute) {
				timeCallback.time(hourOfDay,minute);
			}
		};
		// 用来获取日期和时间的
		Calendar calendar = Calendar.getInstance();
		Dialog dialog = null;
		dialog = new TimePickerDialog(context, timeListener,
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true); // 是否为二十四制
		dialog.show();
	}

	public interface DialogYesCallBack {
		public void yes();
	}

	public interface DialogNoCallBack {
		public void no();
	}
	public interface TimeDialogCallBack {
		public void time(int hourOfDay, int minute);
	}
}
