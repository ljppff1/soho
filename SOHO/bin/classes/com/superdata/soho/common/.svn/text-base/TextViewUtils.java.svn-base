package com.superdata.soho.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 
 * @author 谭树林 
 *
 */
public class TextViewUtils extends TextView {
	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	public TextViewUtils(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public void setLayoutParams(int left,int top,int right,int bottom){
		params.setMargins(left,top, right, bottom);
		this.setLayoutParams(params);
	}
	
	public void setVisibility(boolean boo){
		this.setVisibility(boo==true?View.VISIBLE:View.GONE);
	}
}
