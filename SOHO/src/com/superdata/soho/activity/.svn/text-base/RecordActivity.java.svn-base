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
import com.superdata.soho.activity.workattendance.DayPunchCardRecordActivity;
import com.superdata.soho.activity.workattendance.LeaveRecodeActivity;
import com.superdata.soho.activity.workattendance.OverTimeRecordActivity;
import com.superdata.soho.activity.workattendance.PunchCardActivity;
import com.superdata.soho.activity.workattendance.TravelRecordActivity;
/**
 * 
* @ClassName: RecordActivity
* @Description: TODO(考勤汇总)
* @author 刘定富
* @date 2014年6月10日 上午10:34:04
*
 */
public class RecordActivity extends Activity {
	private GridView gridView;//菜单gridview
	List<HashMap<String, Object>> gridViewList=null;//菜单数据list
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		gridView=(GridView) findViewById(R.id.home_gridView);//初始化gridview
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消点击效果
		
		init();//初始化数据
		
	}
	/**
	 * 
	* @Title: init
	* @Description: TODO(数据初始化)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void init(){
		gridViewList=new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object>map=new HashMap<String, Object>();
		    map.put("title", "打卡记录");
		    map.put("image",R.drawable.punchcardrecod_icon);
		    gridViewList.add(map);
		    map=new HashMap<String, Object>();
		    map.put("title", "请假记录");
		    map.put("image",R.drawable.askleaverecord_icon);
		    gridViewList.add(map);
		    map=new HashMap<String, Object>();
		    map.put("title", "加班记录");
		    map.put("image",R.drawable.overtimerecord_icon);
		    gridViewList.add(map);	    	    
		    map=new HashMap<String, Object>();
		    map.put("title", "出差记录");
		    map.put("image",R.drawable.leaverecode_icon);
		    gridViewList.add(map);	    	    
		    gridView.setAdapter(new MyAdapter());//添加适配器
		    gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent  intent=new Intent();
					switch (position) {
					case 0://打卡记录
						intent.setClass(RecordActivity.this, DayPunchCardRecordActivity.class);//跳到打卡页面
						break;
					case 1://请假记录
						intent.setClass(RecordActivity.this, LeaveRecodeActivity.class);//跳到请假页面
						break;
					case 2://加班记录
						intent.setClass(RecordActivity.this, OverTimeRecordActivity.class);//跳到加班页面
						break;
					case 3://出差记录
						intent.setClass(RecordActivity.this, TravelRecordActivity.class);//跳到出差页面
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
	 * 
	* @ClassName: MyAdapter
	* @Description: TODO(自定义适配器类)
	* @author 刘定富
	* @date 2014年6月10日 上午10:30:42
	*
	 */
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return gridViewList.size();//数据大小
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return gridViewList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;//当前位置
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {//返回自定义view
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView==null) {
				holder=new ViewHolder();//初始viewholder
				convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.home_gridview_item, null);//加载自定义布局
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
* @Description: TODO(封装组件类)
* @author Administrator
* @date 2014年6月10日 上午10:32:48
*
 */
   class ViewHolder{
	   TextView  textView;//标题
	   ImageView imageView ;//图标
   }
}
