package com.superdata.soho.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.superdata.soho.R;
import com.superdata.soho.activity.approvalcenter.AttentionNotificationActivity;
import com.superdata.soho.activity.approvalcenter.AuditListActivity;
import com.superdata.soho.activity.approvalcenter.PublicNotificationActivity;
import com.superdata.soho.activity.entrance.MyPhoneAppActivity;
import com.superdata.soho.activity.workattendance.PunchCardActivity;
/**
 * 
* @ClassName: HomeActivity
* @Description: TODO(首页)
* @author 刘定富
* @date 2014年6月10日 上午10:13:06
*
 */
public class HomeActivity extends Activity {
	private GridView gridView;//首页菜单gridview
	List<HashMap<String, Object>> gridViewList=null;//菜单数据list
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);//加载
		gridView=(GridView) findViewById(R.id.home_gridView);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消点击效果
		
		init();//初始化数据  
		gridView.setAdapter(new MyAdapter());//添加适配器
		
		gridView.setOnItemClickListener(new OnItemClickListener() {//添加gridView监听事件

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {	
				Intent  intent=new Intent();
				switch (position) {
				case 0://公告
					intent.setClass(getApplicationContext(), PublicNotificationActivity.class);
					break;
				case 1://消息提醒
					intent.setClass(getApplicationContext(), AttentionNotificationActivity.class);
					break;
				case 2://待审批
					intent.setClass(getApplicationContext(), AuditListActivity.class);
					break;
				case 3:
					intent.setClass(getApplicationContext(), MyPhoneAppActivity.class);//我的手机
					break;
				default:
					intent.setClass(getApplicationContext(), PunchCardActivity.class);//默认打卡页面
					break;
				}
				startActivity(intent);//跳转
			}
		});
		
	}
	/**
	 * 初始化数据方法
	 */
	public void init(){
		gridViewList=new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object>map=new HashMap<String, Object>();
		    map.put("title", "公告");
		    map.put("image",R.drawable.announcement_icon);
		    gridViewList.add(map);
		    map=new HashMap<String, Object>();
		    map.put("title", "消息提醒");
		    map.put("image",R.drawable.messagetoast_icon);
		    gridViewList.add(map);
		    map=new HashMap<String, Object>();
		    map.put("title", "待审批");
		    map.put("image",R.drawable.unapp_icon);
		    gridViewList.add(map);
		    map=new HashMap<String, Object>();
		    map.put("title", "我的手机");
		    map.put("image",R.drawable.myphone_icon);
		    gridViewList.add(map);	    	    
	}
	/**
	 * 
	* @ClassName: MyAdapter
	* @Description: TODO(自定义适配器类)
	* @author 刘定富
	* @date 2014年6月10日 上午10:14:25
	*
	 */
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return gridViewList.size();//数据大小
		}

		@Override
		public Object getItem(int position) {
			return gridViewList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {//返回自定义view
			ViewHolder holder;
			if (convertView==null) {
				holder=new ViewHolder();
				convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.home_gridview_item, null);//加载自定义view布局
				holder.textView=(TextView) convertView.findViewById(R.id.home_gitem_textView);
				holder.imageView=(ImageView) convertView.findViewById(R.id.home_gitem_img);
				convertView.setTag(holder);
			}else {
				holder=(ViewHolder) convertView.getTag();
			}
			  holder.textView.setText(gridViewList.get(position).get("title").toString());
			  holder.imageView.setImageResource((Integer)(gridViewList.get(position).get("image")));
			return convertView;
		}
		
	}
	/**
	 * 
	* @ClassName: ViewHolder
	* @Description: TODO(封装控件类)
	* @author 刘定富
	* @date 2014年6月10日 上午10:15:50
	*
	 */
   class ViewHolder{
	   TextView  textView;//标题
	   ImageView imageView ;//图标
   }
}
