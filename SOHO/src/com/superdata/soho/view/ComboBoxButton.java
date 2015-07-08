package com.superdata.soho.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.superdata.soho.R;
import com.superdata.soho.entity.User;

public class ComboBoxButton extends LinearLayout {
	public int HEIGHT=520;
	private Context mContext;
	private PopupWindow popupwindow = null;
	private Button myButton;
	private int position=0;//涓轰簡鏍规嵁鏁版嵁鑾峰彇鍒板搴旀暟鎹殑ID
	private ListView listView;
	ImageView imageview;
	View linear_view;
	List<User> userList=new ArrayList<User>();
	int pos=0;
	IDecodeDataBack idDataBack;
	
	public void setIDecodeDataBack(IDecodeDataBack id){
		 idDataBack=id;
	}
	
	//鑾峰彇褰撳墠閫夋嫨鐨勬枃鏈�
	private String text = "";
	//鑾峰彇褰撳墠閫夋嫨鐨処D銆傝ID浠� 1 寮�濮�
	private String cid = "";
	private List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	public ComboBoxButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		linear_view = inflater.inflate(R.layout.popwindow_listview, null);
		listView = (ListView)linear_view.findViewById(R.id.combobox_listview);
//		listView.setDividerHeight(0);
//		listView.setFocusable(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(popupwindow!=null&&popupwindow.isShowing()){
					popupwindow.dismiss();
				}
				position=arg2;
				text = list.get(arg2).get("text");
				cid = list.get(arg2).get("id");
				myButton.setText(text);
				idDataBack.dataDecodeCallback(position);
			}
		});
		myButton = new Button(mContext);
		myButton.setText("璇风偣鍑婚�夋嫨..");
		myButton.setGravity(Gravity.CENTER|Gravity.LEFT);
		myButton.setPadding(4, 0, 33, 0);
		myButton.setMaxLines(1);
		myButton.setBackgroundResource(R.drawable.xialakuang_btn);
		myButton.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		ShowPopupwindowListviewOnClickListener clicklistener = new ShowPopupwindowListviewOnClickListener();
		myButton.setOnClickListener(clicklistener);
		/*imageview = new ImageView(mContext);
		imageview.setBackgroundResource(R.drawable.xialakuang_btn);
		imageview.setOnClickListener(clicklistener);
		imageview.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));*/
		addView(myButton);
		//addView(imageview);
	}
	
	/**
	 * 鐐瑰嚮鏄剧ず
	 * @ClassName ShowPopupwindowListviewOnClickListener
	 * @author Administrator
	 * @date 2014骞�8鏈�12鏃� 涓嬪崍5:18:58
	 *
	 */
	class ShowPopupwindowListviewOnClickListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
				
				if(popupwindow==null){
					//璁剧疆涓嬫媺妗嗗搴﹂珮搴�
					popupwindow = new PopupWindow(linear_view,(myButton.getWidth()),android.view.ViewGroup.LayoutParams.FILL_PARENT,true);
					popupwindow.setTouchable(true);
					popupwindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap)null));
					//popupwindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.combobox_list));
					popupwindow.setOutsideTouchable(true);
					popupwindow.setTouchInterceptor(new OnTouchListener(){
						@Override
						public boolean onTouch(View arg0, MotionEvent arg1) {
							if(arg1.getAction() == MotionEvent.ACTION_OUTSIDE){
								if(popupwindow!=null&&popupwindow.isShowing()){
									popupwindow.dismiss();
									return true;
								}
							}
							return false;
						}
					});
				}
				if(!popupwindow.isShowing()){
					//璁剧疆寮瑰嚭妗嗙殑浣嶇疆
					popupwindow.showAsDropDown(myButton, 0,1);
				}
		}
	}
	/**
	 * 璁剧疆鏁版嵁鍒颁笅鎷夊垪琛ㄤ腑
	 * @Title setDataToListView
	 * @param data 鏂规硶娉ㄩ噴
	 */
	public void setDataToListView(String [] data){
		
		if(this.list.size()>0){
			this.list.clear();
		}
		if(data.length>0){
			for(int i=0;i<data.length;i++){
				HashMap <String,String>map = new HashMap<String,String>();
				map.put("text", data[i]);
				map.put("id", String.valueOf((i+1)));
				this.list.add(map);
			}
		}
		ComboBoxadapter adapter = new ComboBoxadapter(mContext);
		listView.setAdapter(adapter);
		
	}
	
	//榛樿閫変腑涓�椤�
	public void setSelect(int position){
		text = list.get(position).get("text");
		cid = String.valueOf(position+1);
		myButton.setText(text);
	}
	//鑾峰彇褰撳墠鏄剧ず鐨刢ombobox鎸夐挳
	public Button getMyButton() {
		return myButton;
	}
	
	public String getText() {
		return text;
	}
	public String getCid() {
		return cid;
	}
	//涓轰簡鏍规嵁鏁版嵁鑾峰彇鍒板搴旀暟鎹殑ID
	public int getPosition() {
		return position;
	}
	class ViewHolder{
		
		TextView contentTextview;

		public TextView getContentTextview() {
			return contentTextview;
		}

		public void setContentTextview(TextView contentTextview) {
			this.contentTextview = contentTextview;
		}
	}
	class ComboBoxadapter extends BaseAdapter{
		Context context;
		public ComboBoxadapter(Context context){
			this.context = context;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			ViewHolder holder = null;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.popwindow_listview_item, null);
				holder.contentTextview = (TextView)convertView.findViewById(R.id.popwindow_listview_text);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder)convertView.getTag();
			}
/*			if((position % 2) == 0){
				convertView.setBackgroundColor(Color.parseColor("#d6ebf9"));
			}
			else{
				convertView.setBackgroundColor(Color.parseColor("#c1e3f4"));
			}*/
			holder.contentTextview.setText(list.get(arg0).get("text"));
			return convertView;
		}
	}
	
	public interface IDecodeDataBack {

		public void dataDecodeCallback(int postion);
	}
	
}
