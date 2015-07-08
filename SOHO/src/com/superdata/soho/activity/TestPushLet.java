/**  
* @Title: TestPushLet.java
* @Package com.example.smooth
* @Description: TODO(用一句话描述该文件做什么)
* @author A18ccms A18ccms_gmail_com  
* @date 2014年6月25日 上午9:42:22
* @version V1.0  
*/ 
package com.superdata.soho.activity;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Protocol;

import com.superdata.soho.R;
import com.superdata.soho.entity.MessagePush;

import android.app.Activity;
import android.os.Bundle;

/**
 * @ClassName: TestPushLet
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2014年6月25日 上午9:42:22
 *
 */
public class TestPushLet extends Activity{

	public static final String PROTOCOL = "http://192.168.0.101:8080";
	String strUrl;
	MessagePush message;
	/* (非 Javadoc)
	* <p>Title: onCreate</p>
	* <p>Description: </p>
	* @param savedInstanceState
	* @see android.app.Activity#onCreate(android.os.Bundle)
	*/ 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leave_record_activity);
		
		strUrl = "/common/pushlet.srv?p_event=join-listen&userId=abc&p_format=ser&p_mode=pull&p_subject=1";
		message=new MessagePush();
//		message.setCompanyId("1");
//		message.setDeptId("1");
		message.setMsgName("1239");
		message.setMsgType("1");
		//message.setUserId("");
		
		cast(message);
		
		/*FileThread ft=new FileThread();
		ft.start();*/
		
	}
	/**
	 * 
	* @ClassName: FileThread
	* @Description: 开启线程
	* @author Administrator
	* @date 2014年6月25日 下午3:15:53
	*
	 */
	public class FileThread extends Thread{
		/* (非 Javadoc)
		* <p>Title: run</p>
		* <p>Description: </p>
		* @see java.lang.Thread#run()
		*/ 
		@Override
		public void run() {
			try {
				request(strUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	* @Title: request
	* @Description: 轮训获取消息
	* @param @param strUrl
	* @param @throws Exception    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void request(String strUrl) throws Exception {
		// 建立与服务器的URL对像
		URL url = new URL(PROTOCOL + strUrl);
		// 打开连接
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// 获取服务器的输入流
		InputStream in = con.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(in);
		Event event = null;
		while ((event = (Event) ois.readObject()) != null) {
			System.out.println(event.getField("messagePush", "返回数据为空"));
			if (Protocol.E_REFRESH.equals(event.getEventType())) {
				Thread.sleep(new Long(event.getField(Protocol.P_WAIT)));
				request(event.getField(Protocol.P_URL));
			}
		}
	}
	
	/**
	 * 消息推送
	 * 
	 * @param messagePush
	 */
	public void cast(MessagePush messagePush) {
		// 事件名称格式：消息类型[.公司ID][.部门ID][.用户ID]
		StringBuffer eventName = new StringBuffer(messagePush.getMsgType());
		if (messagePush.getCompanyId() != null
				&& messagePush.getCompanyId().trim().length() > 0) {
			eventName.append(".");
			eventName.append(messagePush.getCompanyId());
		}
		if (messagePush.getDeptId() != null
				&& messagePush.getDeptId().trim().length() > 0) {
			eventName.append(".");
			eventName.append(messagePush.getDeptId());
		}
		if (messagePush.getUserId() != null
				&& messagePush.getUserId().trim().length() > 0) {
			eventName.append(".");
			eventName.append(messagePush.getUserId());
		}
		Event event = Event.createDataEvent(eventName.toString());
		/*JSONObject json = null;
		try {
			json = new JSONObject(messagePush.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		// 设置推送消息到客户端
		event.setField("messagePush", messagePush.toString());
		if (messagePush.getUserId() != null
				&& messagePush.getUserId().trim().length() > 0) {// 如果有用户ID则向指定用户推送
			Dispatcher.getInstance().unicast(event, messagePush.getUserId());
		} else {// 如果没有用户ID，则向公司或者部门或者所有公司推送
			Dispatcher.getInstance().broadcast(event);
		}
	}
	
}
