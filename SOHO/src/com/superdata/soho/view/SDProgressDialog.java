/**
 * @Title SDProgressDialog.java
 * @Package com.superdata.soho.view
 * @author Administrator
 * @date 2014年6月27日 上午10:12:12
 * @version V1.0
 */
package com.superdata.soho.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.superdata.soho.R;

/**
 * 圆形进度条
 * @ClassName SDProgressDialog
 * @author luolei
 * @date 2014年6月27日 上午10:12:12
 *
 */
public class SDProgressDialog {

	private Dialog dialog;
	private TextView txt_message;
	
	public SDProgressDialog(Context context){
		this.dialog = new Dialog(context, R.style.dialog);
		this.dialog.setCanceledOnTouchOutside(false);
		this.dialog.setContentView(R.layout.propress_dialog);
	}
	
	public void setMessage(int id) {
		txt_message.setText(id);
	}

	public void setMessage(String msg) {
		txt_message.setText(msg);
	}

	public void show() {
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	public void cancel() {
		if (dialog.isShowing()) {
			dialog.cancel();
		}
	}
	/**
	 * 判断是否显示
	 * @Title isShow
	 * @return 方法注释
	 */
	public boolean isShow(){
		if(dialog.isShowing()){
			return true;
		}else{
			return false;
		}
	}
}
