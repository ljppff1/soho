package com.superdata.soho.activity.approvalcenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivity;
import com.superdata.soho.config.InterfaceConfig;
import com.superdata.soho.entity.AuditList;
import com.superdata.soho.service.SDHttpClient;
import com.superdata.soho.util.SharedPreferencesConfig;
import com.superdata.soho.view.ComboBoxButton;
import com.superdata.soho.view.ComboBoxButton.IDecodeDataBack;
import com.superdata.soho.view.SDProgressDialog;
import com.superdata.soho.view.XListView.IXListViewListener;

/**
 *所有审批列表
 * @author lj
 *
 * 2014年8月14日
 */
public class AuditListActivity extends BaseActivity implements IXListViewListener {
	private MyAdapter adapter;
	Button audit_list_app_bt_back;
	private com.superdata.soho.view.XListView listView;
	//搜索按钮
	private Button btn_search;
    //Adapter里面放的List，
    private List<AuditList> listcost_report =new ArrayList<AuditList>();
    //临时的List，用于加载更多 的逻辑处理
    private List<AuditList> listcost_report1 =new ArrayList<AuditList>();
	private SDProgressDialog sdDialog;
	//获取的是第几页的数据
    private int page =1;
    //获取的这页的数量
    private int pagesize =12;
    //用于判断是否修改了值
    private boolean flag2=true;
	//标识是否加载更多 
    private boolean flag =true; 
    //用于标识是否为第一次加载Json
    private boolean flag3 =true;
    //只加载一次下拉的那个数据，以后不初始化下拉的按钮和重置数据
    private boolean flag4 =true;
    //是否未页
	private boolean isLastPage=false;
    private int position_value;
    private int showToast =0;
    /**
     * 三个ComboBoxButton自定义的控件，用于下拉显示
     */
	private ComboBoxButton audit_list_app_topitems_cb1;
	private ComboBoxButton audit_list_app_topitems_cb2;
	private ComboBoxButton audit_list_app_topitems_cb3;
	//存的是类型
    Map<Integer, List<String>> map =new HashMap<Integer, List<String>>();
    //存的是部门
    Map<Integer, List<String>> map1 =new HashMap<Integer, List<String>>();
    //用于控制下拉控件的显示
	private LinearLayout audit_list_app_topitems;
	private LinearLayout audit_list_app_linner_spinner;
	//"" all  0 me ing  1 me ed
    private String auditStatus ="";
    // deptment  
    private String deptId ="";
    private String keyWord ="";
    private String dataTypeId ="";
    //关键字
	private EditText audit_list_app_etsearch;
	private Map<String, String> mapconfig;
	private String empId;
	private String companyId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audit_list_activity);
		initSharePreferences();
		//初始化View
		initView();
		//初始化数据
		initdata();
	}
	
	private void initSharePreferences() {
		mapconfig = SharedPreferencesConfig.config(AuditListActivity.this);
		 empId=mapconfig.get(InterfaceConfig.ID);
		 companyId=mapconfig.get(InterfaceConfig.COMPANYID);
	}

	private void initView() {
		audit_list_app_etsearch =(EditText)this.findViewById(R.id.audit_list_app_etsearch);
		keyWord =audit_list_app_etsearch.getText().toString();
		audit_list_app_bt_back=(Button) findViewById(R.id.audit_list_lookdetail1_bt_back);
		audit_list_app_bt_back.setOnClickListener(listener);
		listView=	(com.superdata.soho.view.XListView)this.findViewById(R.id.audit_list_app_listview);
		listView.setOnItemClickListener(listener1);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		btn_search=(Button)this.findViewById(R.id.audit_list_app_search);
		audit_list_app_etsearch.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
			   if(keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_UP){
				   initSearch();
			   }
			return false;
			}

		});

		btn_search.setOnClickListener(listener);
		audit_list_app_topitems =(LinearLayout)this.findViewById(R.id.audit_list_app_topitems);
		audit_list_app_linner_spinner =(LinearLayout)this.findViewById(R.id.audit_list_app_linner_spinner);
	}
	/**
	 * 三个下拉控件的初始化和设置监听事件
	 */
	private void initItems() {
		audit_list_app_topitems_cb1 =(ComboBoxButton)this.findViewById(R.id.audit_list_app_topitems_cb1);
		audit_list_app_topitems_cb2 =(ComboBoxButton)this.findViewById(R.id.audit_list_app_topitems_cb2);
		audit_list_app_topitems_cb3 =(ComboBoxButton)this.findViewById(R.id.audit_list_app_topitems_cb3);
		final String[] c = new String[4];
		c[0] ="";
		c[1] ="2";
		c[2] ="1";
		c[3] ="0";
		//类型
		String[] a = new String[map.size()];
		for(int i=0;i<map.size();i++){
		     if(i==0){
		    	 a[0] ="业务类型                                   ";
		     }else{
			a[i] =map.get(i-1).get(1)+"                     ";
		     }
		}
		//部门
		String[] b = new String[map1.size()];
		for(int i=0;i<map1.size();i++){    
			b[i] =map1.get(i).get(1)+"                      ";
		}

		audit_list_app_topitems_cb1.setDataToListView(new String [] {"所有的                    ","我提交的                      ","我已审批                         ","待我审批                         "} );
		audit_list_app_topitems_cb1.setSelect(0);
		audit_list_app_topitems_cb1.setIDecodeDataBack(new IDecodeDataBack() {
			
			@Override
			public void dataDecodeCallback(int postion) {
				auditStatus =c[postion];
				audit_list_app_topitems_cb1.setSelect(postion);
				initdata(1+"", empId, companyId, page+"", pagesize+"",dataTypeId,auditStatus,deptId,keyWord);
			}
		});

		audit_list_app_topitems_cb2.setDataToListView(a);
		audit_list_app_topitems_cb2.setSelect(0);
		audit_list_app_topitems_cb2.setIDecodeDataBack(new IDecodeDataBack() {
			@Override
			public void dataDecodeCallback(int postion) {
				audit_list_app_topitems_cb2.setSelect(postion);
				if(postion==0){
					dataTypeId ="";
				}else{
				dataTypeId =map.get(postion-1).get(0)+"";
				}
				initdata(1+"",  empId, companyId, page+"", pagesize+"",dataTypeId,auditStatus,deptId,keyWord);
			}
		});
		audit_list_app_topitems_cb3.setDataToListView(b);
        audit_list_app_topitems_cb3.setSelect(0);
		audit_list_app_topitems_cb3.setIDecodeDataBack(new IDecodeDataBack() {
			@Override
			public void dataDecodeCallback(int postion) {
				audit_list_app_topitems_cb3.setSelect(postion);
				if(postion==0){
					deptId ="";
				}else{
					deptId =map1.get(postion).get(0)+"";
				}
				initdata(1+"", empId, companyId, page+"", pagesize+"",dataTypeId,auditStatus,deptId,keyWord);
			}
		});
		audit_list_app_topitems.setVisibility(View.VISIBLE);;
		audit_list_app_linner_spinner.setVisibility(View.VISIBLE);
	}
    /* 
     * 刷新当前条
     * (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode!=0){
		switch (resultCode) {
		//通过
		case 1:
			showToast = 1;
			break;
		//终止
		case 2:
			showToast = 2;
			break;
		//反审批
		case 3:
			showToast = 3;
			break;

		default:
			break;
		}
		position_value = Integer.valueOf(data.getExtras().getString("POSITION"));
		flag2 = false;
		initdata(1+"",  empId, companyId, (position_value)+"", 1+"",dataTypeId,auditStatus,deptId,keyWord);

		}
		super.onActivityResult(requestCode, resultCode, data);
	}


    /**
     * 第一次加载的时候
     */
	public void initdata(){
		initdata(0+"",  empId, companyId, page+"", pagesize+"",dataTypeId,auditStatus,deptId,keyWord);
		
	}
	/**
	 * 最开始获取审批列表 所传参数status==0，全部返回，包括业务类型和部门信息
	 * @param status  为0时全部返回 
	 * @param auditId  审批人ID
	 * @param companyId  公司 ID
	 * @param page  当前页码 
	 * @param rows   每页条数
	 * @param dataTypeId   单据类型ID，条件查询的
	 * @param auditStatus    所有的 “” 我已审批 1 ，待我审批 0
	 * @param deptId    部门id，为”“返回所有 ，，
	 * @param keyWord     关键字
	 */
	private void initdata(String status, String auditId, String  companyId,String page,
			String rows,String dataTypeId,String auditStatus,String deptId,String keyWord) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("status", status));
		params.add(new BasicNameValuePair("auditId", auditId));
		params.add(new BasicNameValuePair("companyId",  companyId));
		params.add(new BasicNameValuePair("page",  page));
		params.add(new BasicNameValuePair("rows",  rows));
		params.add(new BasicNameValuePair("dataTypeId",  dataTypeId));
		params.add(new BasicNameValuePair("auditStatus",  auditStatus));
		params.add(new BasicNameValuePair("deptId",  deptId));
		params.add(new BasicNameValuePair("keyWord",  keyWord));
		new MyTask().execute(params);
	}

	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =InterfaceConfig.AUDITINFO;
	private ArrayList<String> list;
		@Override
        protected void onPreExecute() {
     		if(page==1){
        	sdDialog = new SDProgressDialog(AuditListActivity.this);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return aa;
		}
		
		@Override
		protected void onPostExecute(String result) {
		     Message msg =new Message();
			 if(result!=null){
					try {
					    JSONObject jRoot = new JSONObject(result);
						JSONArray jaresultList =jRoot.getJSONArray("rows");
				      	Integer total=	jRoot.getInt("total");
				  
					//如果是单独的审批跳转过来的，那么处理一条数据时，不做页面的东西处理。
					if(flag2){
					if(total<=page*pagesize){
						isLastPage =true;
					}else{
						isLastPage =false;
					}
				     }
					//当第一次获取Json时，会得到这个全部的，里面包括部门和业务类型
					if (flag3) {
					    JSONArray jaresultList1 =jRoot.getJSONArray("dataTypeTree");
						for(int i=0;i<jaresultList1.length();i++){
					list = new ArrayList<String>();
				   Integer id =(Integer) (((jaresultList1.getJSONObject(i).get("id")).equals(null))?1: jaresultList1.getJSONObject(i).get("id"));
		           String name =(jaresultList1.getJSONObject(i).get("name")).equals(null)?"姓名没有"+i:(String) jaresultList1.getJSONObject(i).get("name");
					 list.add(id+"");
					list.add(name);
					map.put(i,list);
						}
					    JSONArray jaresultList2 =jRoot.getJSONArray("department");
						for(int i=0;i<jaresultList2.length();i++){
							 list = new ArrayList<String>();
				   Integer id =(Integer) (((jaresultList2.getJSONObject(i).get("id")).equals(null))?1: jaresultList2.getJSONObject(i).get("id"));
		           String name =(jaresultList2.getJSONObject(i).get("name")).equals(null)?"姓名没有"+i:(String) jaresultList2.getJSONObject(i).get("name");
							 list.add(id+"");
							list.add(name);
							map1.put(i,list);
						}
						flag3 =false;
					}
						listcost_report1.clear();
					
						for(int i=0;i<jaresultList.length();i++){
				            Integer id =(Integer) (((jaresultList.getJSONObject(i).get("id")).equals(null))?1: jaresultList.getJSONObject(i).get("id"));
				            Integer dataId =(Integer) (((jaresultList.getJSONObject(i).get("dataId")).equals(null))?1: jaresultList.getJSONObject(i).get("dataId"));
				            Integer dataTypeId =(Integer) (((jaresultList.getJSONObject(i).get("dataTypeId")).equals(null))?1: jaresultList.getJSONObject(i).get("dataTypeId"));
					           String theme =(jaresultList.getJSONObject(i).get("theme")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("theme");
					            Integer step =(Integer) (((jaresultList.getJSONObject(i).get("step")).equals(null))?1: jaresultList.getJSONObject(i).get("step"));
					            Integer totalStep =(Integer) (((jaresultList.getJSONObject(i).get("totalStep")).equals(null))?0: jaresultList.getJSONObject(i).get("totalStep"));
						           String auditName =(jaresultList.getJSONObject(i).get("auditName")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("auditName");
						           String statusStr =(jaresultList.getJSONObject(i).get("statusStr")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("statusStr");
						           String auditRemark =(jaresultList.getJSONObject(i).get("auditRemark")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("auditRemark");
						           String postMan =(jaresultList.getJSONObject(i).get("postMan")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("postMan");
						           String postDate =(jaresultList.getJSONObject(i).get("postDate")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("postDate");
						           String auditResultStr =(jaresultList.getJSONObject(i).get("auditResultStr")).equals(null)?"":(String) jaresultList.getJSONObject(i).get("auditResultStr");
						           AuditList epp=new AuditList(id+"", dataTypeId+"",  theme, step+"", auditName, totalStep+"", auditRemark, postDate, postMan,dataId+"",auditResultStr);
		            listcost_report1.add(epp);	
						}
						
					     //用于判断是否修改了值
					     if(flag2){
					     //加载更多 时
					     if(!flag){
					    	 listcost_report.addAll(listcost_report1);
					    	 listcost_report1.clear();
							adapter = new MyAdapter();
					        msg.what =2;
					     }else{
					    	 //第一次
					    	 //重新加载全部的
						    	 listcost_report.clear();
						    	 listcost_report.addAll(listcost_report1);
								 adapter = new MyAdapter();
					           	 msg.what =1;
					     }
					     }else{
					    	 listcost_report.get(position_value).setAuditName(listcost_report1.get(0).getAuditName());
					    	 listcost_report.get(position_value).setAuditRemark(listcost_report1.get(0).getAuditRemark());
					    	 listcost_report.get(position_value).setAuditResult(listcost_report1.get(0).getAuditResult());		    	    flag2 =true;
					    	 adapter = new MyAdapter();
				           	 msg.what =4;
					     }
						     if(sdDialog.isShow())
							sdDialog.cancel();
						
					} catch (Exception e) {
						msg.what=5;
						e.printStackTrace();
					}
				}
			 

			     handler.sendMessage(msg);
			     
			     
			super.onPostExecute(result);
		}
	}
	
	private Handler handler =new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				//只初始化头部的Items一次
				if(flag4){
				initItems();
				flag4=false;
				}
				listView.setAdapter(adapter);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				
				adapter.notifyDataSetChanged();
			
				break;
			case 2:
				flag =true;
			    listView.setAdapter(adapter);
			    listView.setSelection(listcost_report.size()-pagesize);
				adapter.notifyDataSetChanged();// 数据变化刷新
				if(sdDialog.isShow())
				sdDialog.cancel();
				onLoad();
				break;
			case 3:
				onLoad();
				Toast.makeText(getApplicationContext(), "已全部加载完成", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				listView.setAdapter(adapter);
				listView.setSelection(position_value);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				switch (showToast) {
				//input
				case 1:
					Toast.makeText(AuditListActivity.this, "审批通过成功提交",Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(AuditListActivity.this, "审批终止成功提交",Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(AuditListActivity.this, "反审批成功提交",Toast.LENGTH_SHORT).show();
	                 break;
	                
				default:
					break;
				}

				
				break;
			case 5:
				Toast.makeText(getApplicationContext(), "访问出现问题", Toast.LENGTH_SHORT).show();
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
			case R.id.audit_list_lookdetail1_bt_back:
				onBackPressed();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;
				//搜索监听
			case R.id.audit_list_app_search:
                initSearch();
				break;
			default:
				break;
			}
			
		}
	};
	public void initSearch() {
		keyWord =audit_list_app_etsearch.getText().toString();
		initdata(1+"",  empId, companyId, page+"", pagesize+"",dataTypeId,auditStatus,deptId,keyWord);
	}

	OnItemClickListener listener1 =new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		Intent intent =	new Intent(getApplicationContext(), AuditListAuditActivity.class);
		intent.putExtra("ID", listcost_report.get(position-1).getId());
		intent.putExtra("DATATYPEID", listcost_report.get(position-1).getDataTypeId());
		intent.putExtra("DATAID", listcost_report.get(position-1).getDataId());
		intent.putExtra("POSITION", position+"");
	    startActivityForResult(intent, 1);
		}
	};

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return listcost_report.size();
		}
		@Override
		public Object getItem(int position) {
			return listcost_report.get(position);
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
				convertView = LayoutInflater.from(AuditListActivity.this).inflate(R.layout.item_audit_list_approve, null);
				//提交人
			    holder.name=(TextView) convertView.findViewById(R.id.tv_cb_item_audit_list_approve_name);
			    //主题，什么请假申请
                holder.theme=(TextView)convertView.findViewById(R.id.tv_cb_item_audit_list_approve_theme);
                //步骤
                holder.step =(TextView)convertView.findViewById(R.id.tv_cb_item_audit_list_approve_move);
                holder.approvetime=(TextView)convertView.findViewById(R.id.tv_cb_item_audit_list_approve_time);
                holder.auditresult =(TextView)convertView.findViewById(R.id.tv_cb_item_audit_list_approve_auditresult);
                convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
		    holder.name.setText(listcost_report.get(position).getPostMan());
            holder.theme.setText(listcost_report.get(position).getTheme());
            holder.auditresult.setText(listcost_report.get(position).getAuditResultStr());
            holder.approvetime.setText(listcost_report.get(position).getPostDate());
            holder.step.setText(listcost_report.get(position).getStep());
            return convertView;
		}
	}
	class ViewHolder {
		TextView name,theme,auditresult,approvetime,step;
	}
	@Override
	public void onRefresh() {
		page = 1;
		initdata(1+"", empId, companyId, page+"", pagesize+"",dataTypeId,auditStatus,deptId,keyWord);
		onLoad();
	}

	@Override
	public void onLoadMore() {
		if(!isLastPage){
		if(flag){
		flag =false;
		++page;
		initdata(1+"",  empId, companyId, page+"", pagesize+"",dataTypeId,auditStatus,deptId,keyWord);
		}
		}else{
			handler.sendEmptyMessage(3);
		}
	}

}
