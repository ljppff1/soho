package com.superdata.soho.activity.approvalcenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.entity.AttentionNotification;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.PatternUtil;
import com.superdata.soho.view.SDProgressDialog;
import com.superdata.soho.view.XListView.IXListViewListener;

/**
 * 消息提醒页面
 * @ClassName LeaveApprovalActivity
 * @author Administrator
 * @date 2014年7月8日 下午1:42:35
 *
 */
public class AttentionNotificationActivity extends BaseActivity implements IXListViewListener {
	//适配器
	private MyAdapter adapter;
	//返回按钮
	Button attention_notification_bt_back_bt_back;
	private com.superdata.soho.view.XListView listView;
	//搜索按钮
	private Button btn_search;
    //Adapter里面放的List，
    private List<AttentionNotification> listattentionnotification =new ArrayList<AttentionNotification>();
    //临时的List，用于加载更多 的逻辑处理
    private List<AttentionNotification> listattentionnotification1 =new ArrayList<AttentionNotification>();
	private SDProgressDialog sdDialog;
	//获取的是第几页的数据
    private int page =1;
    //获取的这页的数量
    private int pagesize =15;
	//标识是否加载更多 
    private boolean flag =true; 
    //是否最后一页
	private boolean isLastPage=false;
	//总页数
	private int total=0;
	//关键字
    private String searchText="";
    //搜索框
	private EditText attention_notification_etsearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attention_notification_activity);
		//初始化View控件
		initView();
		//获取数据
		initdata();
		
		
	}
	
	private void initView() {
     	attention_notification_bt_back_bt_back=(Button) findViewById(R.id.attention_notification_audit_bt_back);
		attention_notification_bt_back_bt_back.setOnClickListener(listener);
		listView=	(com.superdata.soho.view.XListView)this.findViewById(R.id.attention_notification_listview);
		listView.setOnItemClickListener(listener1);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		btn_search=(Button)this.findViewById(R.id.attention_notification_search);
		btn_search.setOnClickListener(listener);
		attention_notification_etsearch =(EditText)this.findViewById(R.id.attention_notification_etsearch);
		attention_notification_etsearch.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
			   if(keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_UP){
				   initSearch();
			   }
			return false;
			}
		});
	}
	


	public void initdata(){
		initdata(page+"",pagesize+"","");	
	}
	
	private void initdata(String page, String rows, String keyWord) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("keyWord", keyWord));
		params.add(new BasicNameValuePair("page", page));
		params.add(new BasicNameValuePair("rows",rows));
		new MyTask().execute(params);
	}

	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =com.superdata.soho.config.InterfaceConfig.ATTENTIONNOTIFICATION;
		@Override
        protected void onPreExecute() {
     		if(page==1){
        	sdDialog = new SDProgressDialog(AttentionNotificationActivity.this);
    	 	sdDialog.show();
     		}
        	super.onPreExecute();
        }
		@Override
		protected String doInBackground(List<NameValuePair>... params) {
		 String aa = null;
		try {
			aa = new SDHttpClient().post_session(url, params[0]);
			Log.i("TAG", aa.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return aa;
		}
		
		@Override
		protected void onPostExecute(String result) {
			 if(result!=null){
					try {
						  JSONObject jRoot = new JSONObject(result);
							JSONArray jaresultList =jRoot.getJSONArray("rows");
							total =(jRoot.getInt("total"));
							if(((page*pagesize)-total)>=0)
								isLastPage=true;
							listattentionnotification1.clear();
							for(int i=0;i<jaresultList.length();i++){
					            String theme =(jaresultList.getJSONObject(i).get("name")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("name");
					            String receiverId = (jaresultList.getJSONObject(i).get("receiveID")).equals(null)?"1":((String) jaresultList.getJSONObject(i).get("receiveID"));
					            String createName =((jaresultList.getJSONObject(i).get("sendName")).equals(null))?"":(String) jaresultList.getJSONObject(i).get("sendName");
					            String preContent1 =((jaresultList.getJSONObject(i).get("content")).equals(null))?"":(String) jaresultList.getJSONObject(i).get("content");
					      
					            String remark  =PatternUtil.clearHtml1(((jaresultList.getJSONObject(i).get("content")).equals(null))?"":(String) jaresultList.getJSONObject(i).get("content"));
					            String sendID  =(String.valueOf(jaresultList.getJSONObject(i).getInt("sendID")).equals(null))?"1":String.valueOf(jaresultList.getJSONObject(i).getInt("sendID"));
					            String data =((jaresultList.getJSONObject(i).get("createDate")).equals(null))?"":(String) jaresultList.getJSONObject(i).get("createDate");
					            String  preContent="<br />\r\n<br />\r\n<br />\r\n※※※※※※※※※&nbsp;"+createName+"于&nbsp;&nbsp;"+data+"&nbsp;&nbsp;写道：<br />\r\n"+preContent1+"<br />\r\n<br />";
					            AttentionNotification an = new AttentionNotification(theme,remark,createName,receiverId,sendID,data,preContent);
					            listattentionnotification1.add(an);
							}
						}
					 catch (Exception e) {
						e.printStackTrace();
					}
				}
		     Message msg =new Message();
		     //加载更多 时
		     if(!flag){
		    	 listattentionnotification.addAll(listattentionnotification1);
		    	 listattentionnotification1.clear();
				adapter = new MyAdapter();
		        msg.what =2;
		     }else{
		    	 //第一次
		    	 //重新加载全部的
			    	 listattentionnotification.clear();
			    	 listattentionnotification.addAll(listattentionnotification1);
					 adapter = new MyAdapter();
		           	 msg.what =1;
		     }
			     if(sdDialog.isShow())
				sdDialog.cancel();
			     handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	
	private Handler handler =new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				listView.setAdapter(adapter);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
			
				break;
			case 2:
				flag =true;
			    listView.setAdapter(adapter);
			    listView.setSelection(listattentionnotification.size()-pagesize);
				adapter.notifyDataSetChanged();// 数据变化刷新
				if(sdDialog.isShow())
				sdDialog.cancel();
				onLoad();
				break;
			case 3:
				onLoad();
				Toast.makeText(getApplicationContext(), "已全部加载完成", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	
	
	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			   //返回监听
			case R.id.attention_notification_audit_bt_back:
				onBackPressed();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;
				//搜索监听
			case R.id.attention_notification_search:
				initSearch();
				break;
			default:
				break;
			}
		}
	};
  public void 	initSearch(){
		searchText =attention_notification_etsearch.getText().toString();
		isLastPage =false;
		initdata(1+"", pagesize+"", searchText);
	}
	OnItemClickListener listener1 =new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//跳转到消息详情界面
		    Intent intent =	new Intent(getApplicationContext(), AttentionNotificationDetailActivity.class);
			intent.putExtra("TITLE", listattentionnotification.get(position-1).getTheme());
			intent.putExtra("REMARK", listattentionnotification.get(position-1).getRemark());
			intent.putExtra("PEOPLE", listattentionnotification.get(position-1).getSendName());
			intent.putExtra("RECEIVEID", listattentionnotification.get(position-1).getReceiveId());
			intent.putExtra("sendID", listattentionnotification.get(position-1).getSendID());
			intent.putExtra("DATE", listattentionnotification.get(position-1).getData());
		    intent.putExtra("PRECONTENT", listattentionnotification.get(position-1).getPreContent());
			startActivity(intent);
		}
	};

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return listattentionnotification.size();
		}

		@Override
		public Object getItem(int position) {
			return listattentionnotification.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(AttentionNotificationActivity.this).inflate(R.layout.item_attention_notification, null);
			    holder.title=(TextView) convertView.findViewById(R.id.tv_cb_item_attention_notification_title);
                holder.remark=(TextView)convertView.findViewById(R.id.tv_cb_item_attention_notification_remark);
                holder.people=(TextView)convertView.findViewById(R.id.tv_cb_item_attention_notification_people);
                holder.date =(TextView)convertView.findViewById(R.id.tv_cb_item_attention_notification_time);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			   holder.title.setText(listattentionnotification.get(position).getTheme());
		       holder.remark.setText(listattentionnotification.get(position).getRemark());
		       holder.people.setText(listattentionnotification.get(position).getSendName());
               holder.date.setText(listattentionnotification.get(position).getData());
              return convertView;
		}
	}

	class ViewHolder {
		TextView title,remark,people,date;
	}

	@Override
	public void onRefresh() {
		page = 1;
		isLastPage =false;
		initdata("1",pagesize+"",searchText);
		onLoad();
	}

	@Override
	public void onLoadMore() {
		if(!isLastPage){
		if(flag){
			flag =false;
		++page;
		initdata(page+"", pagesize+"",searchText);
		}
		}else{
			handler.sendEmptyMessage(3);
		}
	}
}