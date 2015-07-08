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
import com.superdata.soho.entity.publicnotification;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.PatternUtil;
import com.superdata.soho.view.SDProgressDialog;
import com.superdata.soho.view.XListView.IXListViewListener;

/**
 * 公告页面
 * @author lj
 *
 * 2014年9月11日
 */
public class PublicNotificationActivity extends BaseActivity implements IXListViewListener {
	private MyAdapter adapter;
	Button public_notification_bt_back_bt_back;
	private com.superdata.soho.view.XListView listView;
	private Button btn_search;
    //Adapter里面放的List，
    private List<publicnotification> listpublicnotification =new ArrayList<publicnotification>();
    //临时的List，用于加载更多 的逻辑处理
    private List<publicnotification> listpublicnotification1 =new ArrayList<publicnotification>();
	private SDProgressDialog sdDialog;
	//获取的是第几页的数据
    private int page =1;
    //获取的这页的数量
    private int pagesize =15;
	//标识是否加载更多 
    private boolean flag =true; 
	private boolean isLastPage=false;
	private int total=0;
	private String searchtext="";
	private EditText costjob_etsearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.public_notification_activity);
		initView();
		initdata();		
	}
	
	private void initView() {
     	public_notification_bt_back_bt_back=(Button) findViewById(R.id.public_notification_bt_back);
		public_notification_bt_back_bt_back.setOnClickListener(listener);
		listView=	(com.superdata.soho.view.XListView)this.findViewById(R.id.public_notification_listview);
		listView.setOnItemClickListener(listener1);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		btn_search=(Button)this.findViewById(R.id.public_notification_search);
		btn_search.setOnClickListener(listener);
		costjob_etsearch =(EditText)this.findViewById(R.id.costjob_etsearch);
		costjob_etsearch.setOnKeyListener(new OnKeyListener() {
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
		initdata("",1+"",pagesize+"");	
	}
	
	private void initdata(String keyWord, String page, String rows) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("keyWord", keyWord));
		params.add(new BasicNameValuePair("page", page));
		params.add(new BasicNameValuePair("rows",  rows));
		new MyTask().execute(params);
	}

	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =com.superdata.soho.config.InterfaceConfig.PUBLICNOTIFICATION;
		@Override
        protected void onPreExecute() {
     		if(page==1){
        	sdDialog = new SDProgressDialog(PublicNotificationActivity.this);
    	 	sdDialog.show();
     		}
        	super.onPreExecute();
        }
		@Override
		protected String doInBackground(List<NameValuePair>... params) {
		 String aa = null;
		try {
			aa = new SDHttpClient().post_session(url, params[0]);
		} catch (Exception e) {
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
						listpublicnotification1.clear();
						for(int i=0;i<jaresultList.length();i++){
				            String theme =(jaresultList.getJSONObject(i).get("name")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("name");
				            String createDate = (jaresultList.getJSONObject(i).get("createDate")).equals(null)?"":((String) jaresultList.getJSONObject(i).get("createDate")).substring(0, 10);
				            String createName =((jaresultList.getJSONObject(i).get("createName")).equals(null))?"":(String) jaresultList.getJSONObject(i).get("createName");
				            String remark1  =((jaresultList.getJSONObject(i).get("content")).equals(null))?"":(String) jaresultList.getJSONObject(i).get("content");
				            String remark =PatternUtil.clearHtml(remark1);
				            publicnotification pn = new publicnotification(createName, createDate, theme, remark);
		                    listpublicnotification1.add(pn);	
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		     Message msg =new Message();
		     //加载更多 时
		     if(!flag){
		    	 listpublicnotification.addAll(listpublicnotification1);
		    	 listpublicnotification1.clear();
				adapter = new MyAdapter();
		        msg.what =2;
		     }else{
		    	 //第一次
		    	 //重新加载全部的
			    	 listpublicnotification.clear();
			    	 listpublicnotification.addAll(listpublicnotification1);
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
			    listView.setSelection(listpublicnotification.size()-pagesize);
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
			case R.id.public_notification_bt_back:
				onBackPressed();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;
				//搜索监听
			case R.id.public_notification_search:
				initSearch();
				break;
			default:
				break;
			}
			
		}
	};
	public void initSearch(){
		isLastPage =false;
		searchtext = costjob_etsearch.getText().toString();
		initdata(searchtext, 1+"", pagesize+"");
	}
	OnItemClickListener listener1 =new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		Intent intent =	new Intent(getApplicationContext(), PublicNotificationDetailActivity.class);
		    intent.putExtra("TITLE", listpublicnotification.get(position-1).getTheme());
			intent.putExtra("REMARK", listpublicnotification.get(position-1).getRemark());
			intent.putExtra("PEOPLE", listpublicnotification.get(position-1).getName());
			intent.putExtra("DATE", listpublicnotification.get(position-1).getTime());
		startActivity(intent);
		}
	};

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return listpublicnotification.size();
		}

		@Override
		public Object getItem(int position) {
			return listpublicnotification.get(position);
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
				convertView = LayoutInflater.from(PublicNotificationActivity.this).inflate(R.layout.item_public_notification, null);
			    holder.title=(TextView) convertView.findViewById(R.id.tv_cb_item_public_notification_title);
                holder.remark=(TextView)convertView.findViewById(R.id.tv_cb_item_public_notification_remark);
                holder.people=(TextView)convertView.findViewById(R.id.tv_cb_item_public_notification_people);
                holder.date=(TextView)convertView.findViewById(R.id.tv_cb_item_public_notification_time);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
		       holder.title.setText(listpublicnotification.get(position).getTheme());
		       holder.remark.setText(listpublicnotification.get(position).getRemark());
		       holder.people.setText(listpublicnotification.get(position).getName());
		       holder.date.setText(listpublicnotification.get(position).getTime());
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
		initdata(searchtext,"1",pagesize+"");
		onLoad();
	}
	@Override
	public void onLoadMore() {
		if(!isLastPage){
		if(flag){
			flag =false;
		   ++page;
		   initdata(searchtext,page+"", pagesize+"");
		}
		}else{
			handler.sendEmptyMessage(3);
		}
	}
}