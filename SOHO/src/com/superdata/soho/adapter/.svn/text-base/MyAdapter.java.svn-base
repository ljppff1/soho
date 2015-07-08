/**
 * @Title MyAdapter.java
 * @Package com.superdata.soho.adapter
 * @author Administrator
 * @date 2014年7月2日 下午4:08:30
 * @version V1.0
 */
package com.superdata.soho.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.superdata.soho.R;
import com.superdata.soho.entity.LeaveRecord;

/**
 * @ClassName MyAdapter
 * @author Administrator
 * @date 2014年7月2日 下午4:08:30
 *
 */
public class MyAdapter extends BaseAdapter{
	List<LeaveRecord> leaveList = new ArrayList<LeaveRecord>();
	private Context context;
	LayoutInflater inflater = null;
	
	public MyAdapter(Context context,List<LeaveRecord> leaveList){
		this.inflater = LayoutInflater.from(context);
		this.context=context;
		this.leaveList=leaveList;
	}
	@Override
	public int getCount() {
		return leaveList.size();
	}

	@Override
	public Object getItem(int position) {
		return leaveList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.leave_record_list_item, null);
			holder.leave_user_name = (TextView) convertView
					.findViewById(R.id.leave_user_name);
			holder.leave_record_start_time = (TextView) convertView
					.findViewById(R.id.leave_record_start_time);
			holder.leave_record_end_time = (TextView) convertView
					.findViewById(R.id.leave_record_end_time);
			holder.leave_record_type = (TextView) convertView
					.findViewById(R.id.leave_record_type);
			holder.leave_record_total_time = (TextView) convertView
					.findViewById(R.id.leave_record_total_time);
			holder.leave_record_approval = (TextView) convertView
					.findViewById(R.id.leave_record_approval);
			holder.leave_record_reason = (TextView) convertView
					.findViewById(R.id.leave_record_reason);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		System.out.println(position+"---------------");
		holder.leave_user_name.setText(leaveList.get(position)
				.getUserName());
		holder.leave_record_start_time.setText(leaveList.get(position)
				.getLeaveRecordStartTime());
		holder.leave_record_end_time.setText(leaveList.get(position)
				.getLeaveRecordEndTime());
		holder.leave_record_type.setText(leaveList.get(position)
				.getLeaveRecordType());
		holder.leave_record_total_time.setText("请假总长"
				+ leaveList.get(position).getLeaveRecordTotalTime() + "小时");
		holder.leave_record_approval.setText(leaveList.get(position)
				.getLeaveRecordApproval());
		holder.leave_record_reason.setText(leaveList.get(position)
				.getLeaveRecordReason());
		return convertView;
	}

	class ViewHolder {
		TextView leave_user_name, leave_record_start_time,
				leave_record_end_time, leave_record_type,
				leave_record_total_time, leave_record_approval,
				leave_record_reason;
	}
}
