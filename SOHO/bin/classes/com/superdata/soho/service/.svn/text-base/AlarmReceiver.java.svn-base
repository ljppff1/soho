/**
 * @Title AlarmReceiver.java
 * @Package com.superdata.soho.service
 * @author Administrator
 * @date 2014年7月24日 上午10:02:49
 * @version V1.0
 */
package com.superdata.soho.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @ClassName AlarmReceiver
 * @author Administrator
 * @date 2014年7月24日 上午10:02:49
 *
 */
public class AlarmReceiver extends BroadcastReceiver{

	/* (非 Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i=new Intent(context, UploadPostionService.class);
		context.startService(i);
		
	}

}
