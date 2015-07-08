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
import com.superdata.soho.activity.approvalcenter.CostReportActivity;
import com.superdata.soho.activity.approvalcenter.LeaveApprovalActivity;
import com.superdata.soho.activity.approvalcenter.MarketingReportActivity;
import com.superdata.soho.activity.approvalcenter.TravelApprovalActivity;
import com.superdata.soho.activity.approvalcenter.WorkOvertimeApprovalActivity;
import com.superdata.soho.activity.workattendance.PunchCardActivity;

/**
 * 
* @ClassName: ApprovalActivity
* @Description: 审批中心
* @author 刘定富
* @date 2014年6月10日 上午9:52:24
*
 */
public class ApprovalActivity extends Activity {
	private GridView gridView;//菜单gridview
	List<HashMap<String, Object>> gridViewList=null;//菜单数据list
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		gridView=(GridView) findViewById(R.id.home_gridView);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消点击效果
		
		init();//初始化数据
		gridView.setAdapter(new MyAdapter());//添加适配器
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent  intent=new Intent();
				switch (position) {
				case 0://请假审批
					intent.setClass(getApplicationContext(), LeaveApprovalActivity.class);//跳到请假审批页面
					break;
				case 1://加班审批
					intent.setClass(getApplicationContext(), WorkOvertimeApprovalActivity.class);//跳到加班审批页面
					break;
				case 2://出差审批
					intent.setClass(getApplicationContext(), TravelApprovalActivity.class);//跳到出差审批页面
					break;
				case 3:
					intent.setClass(getApplicationContext(), CostReportActivity.class);//跳到费用支出页面
					break;
				case 4:
					intent.setClass(getApplicationContext(), MarketingReportActivity.class);//跳到营销报告页面
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
	 * @Title init 数据初始化
	 */
	public void init(){
		gridViewList=new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object>map=new HashMap<String, Object>();
		    map.put("title", "请假审批");
		    map.put("image",R.drawable.askforleaveapp_icon);
		    gridViewList.add(map);
		    map=new HashMap<String, Object>();
		    map.put("title", "加班审批");
		    map.put("image",R.drawable.overtimeapp_icon);
		    gridViewList.add(map);
		    map=new HashMap<String, Object>();
		    map.put("title", "出差审批");
		    map.put("image",R.drawable.leaveapp_icon);
		    gridViewList.add(map);	    	    
		    map=new HashMap<String, Object>();
		    map.put("title", "费用支出");
		    map.put("image",R.drawable.pay_icon);
		    gridViewList.add(map);	    	    
		    map=new HashMap<String, Object>();
		    map.put("title", "营销报告");
		    map.put("image",R.drawable.marketingreport_icon);
		    gridViewList.add(map);	    	    
	}
	
	/**
	 * 自定义适配器用来显示菜单数据
	 * @ClassName MyAdapter
	 * @author Administrator
	 * @date 2014年6月27日 下午4:05:56
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
				holder=new ViewHolder();//初始化控件类
				convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.home_gridview_item, null);
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
	 * 用来封装控件的类
	 * @ClassName ViewHolder
	 * @author luolei
	 * @date 2014年6月27日 下午4:05:18
	 *
	 */
   class ViewHolder{
	   TextView  textView;//标题
	   ImageView imageView ;//图片
   }
}
