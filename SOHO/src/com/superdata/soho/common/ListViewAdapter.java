package com.superdata.soho.common;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author 谭树林
 * 
 */
public class ListViewAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private int layoutID;
	private String flag[];
	private int ItemIDs[];
	private Context context;
	private String methodName;
	private String checkCode;

	public ListViewAdapter(Context context, List<Map<String, Object>> list,
			int layoutID, String flag[], int ItemIDs[], String methodName) {
		this.context = context;
		this.list = list;
		this.layoutID = layoutID;
		this.flag = flag;
		this.ItemIDs = ItemIDs;
		this.methodName = methodName;
	}

	public ListViewAdapter(Context context, List<Map<String, Object>> list,
			int layoutID, String flag[], int ItemIDs[], String methodName,
			String checkCode) {
		this.context = context;
		this.list = list;
		this.layoutID = layoutID;
		this.flag = flag;
		this.ItemIDs = ItemIDs;
		this.methodName = methodName;
		this.checkCode = checkCode;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(layoutID, null);
		for (int i = 0; i < flag.length; i++) {
			View view = convertView.findViewById(ItemIDs[i]);
			Object data = list.get(position).get(flag[i]);
			if (view instanceof ImageButton) {
				ImageButton ib = (ImageButton) view;
				System.out.println("ib...................................");
			} else if (view instanceof TextView) {
				TextView tv = (TextView) view;
				tv.setText(null == data ? "" : String.valueOf(data));
			} else if (view instanceof ImageView) {
				ImageView iv = (ImageView) view;
				iv.setBackgroundResource(Integer.parseInt(String.valueOf(data)));
			}
		}
		new ListViewAdapterListener(this, context, convertView, list, position,
				methodName, checkCode);
		return convertView;
	}
}