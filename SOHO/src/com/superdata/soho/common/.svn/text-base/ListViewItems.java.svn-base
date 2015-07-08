package com.superdata.soho.common;


import android.view.View;
import android.widget.RadioButton;

import com.superdata.soho.R;


/**
 * 通用ListView 列
 * @author 谭树林 
 *
 */
public class ListViewItems {
	private View convertView;
	
	private int heights=70;
	public ListViewItems(View convertView){
		this.convertView = convertView;
	}
	public RadioButton getRadio(){
		RadioButton radio = (RadioButton) convertView.findViewById(R.id.radio);
		radio.setVisibility(View.VISIBLE);
		return radio;
	}
	public TextViewUtils getRn(){
		TextViewUtils rn = (TextViewUtils) convertView
				.findViewById(R.id.rn);
		rn.setVisibility(View.VISIBLE);
		rn.setWidth(1);
		return rn;
	}
	public TextViewUtils getText1(int width) {
		TextViewUtils text1 = (TextViewUtils) convertView
				.findViewById(R.id.text1);
		text1.setWidth(width);
		//text1.setHeight(heights);
		text1.setMinHeight(heights);
		text1.setVisibility(true);
		text1.setLayoutParams(5, 0, 5,0);
		return text1;
	}

	public TextViewUtils getText2(int width) {
		TextViewUtils text2 = (TextViewUtils) convertView
				.findViewById(R.id.text2);
		text2.setWidth(width);
		text2.setVisibility(true);
		text2.setMinHeight(heights);
		text2.setLayoutParams(5, 0, 5,0);
		return text2;
	}
 
	public TextViewUtils getText3(int width) {
		TextViewUtils text3 = (TextViewUtils) convertView
				.findViewById(R.id.text3);
		text3.setWidth(width);
		text3.setVisibility(true);
		text3.setMinHeight(heights);
		text3.setLayoutParams(5, 0, 5,0);
		return text3;
	}
//	public ImageButton getImage1(){
//		ImageButton image1 = (ImageButton) convertView.findViewById(R.id.image1);
//		image1.setVisibility(View.VISIBLE);
////		image1.layout(5, 5, 5, 5);
//		return image1;
//	}
}
