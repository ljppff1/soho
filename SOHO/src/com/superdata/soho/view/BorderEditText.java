package com.superdata.soho.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 自定义EditText
 * @ClassName BorderEditText
 * @author Administrator
 * @date 2014年8月12日 上午9:20:14
 *
 */
public class BorderEditText extends EditText{

	public BorderEditText(Context context,AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStrokeWidth(1);
		paint.setStyle(Style.STROKE);
		paint.setColor(android.graphics.Color.GRAY);
		paint.setAntiAlias(true);
		RectF rectF = new RectF(2,0,this.getWidth()-2,this.getHeight()-2);
		canvas.drawRoundRect(rectF, 8, 8, paint);
	}
}
