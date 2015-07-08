package com.superdata.soho.activity.workattendance;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.superdata.soho.R;
import com.superdata.soho.common.BaseActivitySimple;
import com.superdata.soho.common.FileUtils;
import com.superdata.soho.common.MyAlertDialog;
import com.superdata.soho.common.MyAlertDialog.DialogYesCallBack;
import com.superdata.soho.entity.PicInfo;
import android.view.View.OnClickListener;
/**
 * 查看本地图片
 * @author 谭树林
 *
 */
public class XcqzLocalImage extends BaseActivitySimple {
	private GridView gv;
	Button editCheckbox, delCheckbox;
	XcqzAdapter adapter;
	// 准备要添加的数据条目
	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	String path = null;
	FileUtils fileUtils;
	PicInfo picInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xcqz_local_image);
		editCheckbox = (Button) findViewById(R.id.editCheckbox);
		delCheckbox = (Button) findViewById(R.id.delCheckbox);
		editCheckbox.setOnClickListener(this);
		delCheckbox.setOnClickListener(this);
		fileUtils = new FileUtils(this);
		picInfo = (PicInfo) getIntent().getSerializableExtra("picInfo");
		path = FileUtils.getStorageDirectory();
		initFiles(false);
		// 实例化一个适配器
		adapter = new XcqzAdapter(this, data, R.layout.xcqz_show_pic_grid,
				new String[] { "imageItem", "textItem" }, new int[] {
						R.id.image_item, R.id.text_item });
		// 获得GridView实例
		gv = (GridView) findViewById(R.id.mygridview);
		// 为GridView设置适配器
		gv.setAdapter(adapter);
	}

	private void initFiles(boolean boo) {
		data.clear();
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (file.list().length > 0) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (!f.isFile())
					continue;
				Bitmap bitmap = BitmapFactory.decodeFile(f.getPath());
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("imageItem", bitmap);// 添加图像资源的ID
				item.put("sendName", f.getName());// 按序号添加ItemText
				item.put("checkbox", boo);//
				item.put(
						"textItem",
						f.getName().substring(f.getName().indexOf("_") + 1,
								f.getName().length() - 4));// 文件全名字
				data.add(item);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editCheckbox:
			boolean boo = false;
			if (editCheckbox.getText().equals("编 辑")) {
				editCheckbox.setText("取 消");
				delCheckbox.setEnabled(true);
				boo = true;
			} else {
				editCheckbox.setText("编 辑");
				delCheckbox.setEnabled(false);
			}

			initFiles(boo);

			adapter.notifyDataSetInvalidated();
			break;
		case R.id.delCheckbox:
			new MyAlertDialog(this, "提示", "确认删除?", new DialogYesCallBack() {

				@Override
				public void yes() {
					if (null != listCheckbox) {
						for (String str : listCheckbox) {
							fileUtils.deleteFile(str);
						}
					}
					initFiles(true);
					adapter.notifyDataSetInvalidated();
				};
			}, null);
			break;
		default:
			break;
		}
	}

	List<String> listCheckbox = new ArrayList<String>();

	class XcqzAdapter extends BaseAdapter {
		private List<Map<String, Object>> list;
		private int layoutID;
		private String flag[];
		private int ItemIDs[];
		private Context context;

		public XcqzAdapter(Context context, List<Map<String, Object>> list,
				int layoutID, String flag[], int ItemIDs[]) {
			this.context = context;
			this.list = list;
			this.layoutID = layoutID;
			this.flag = flag;
			this.ItemIDs = ItemIDs;
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = LayoutInflater.from(context).inflate(layoutID, null);
			Boolean boo = (Boolean) list.get(position).get("checkbox");
			final CheckBox box = (CheckBox) convertView
					.findViewById(R.id.checkbox_item);

			if (boo) {
				box.setVisibility(View.VISIBLE);
				box.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (box.isChecked()) {
							listCheckbox.add(list.get(position).get("sendName")
									.toString());
						} else {
							String removeObj = null;
							for (String s : listCheckbox) {
								if (s.equals(list.get(position).get("sendName")
										.toString())) {
									removeObj = s;
								}
							}
							listCheckbox.remove(removeObj);
						}
					}
				});
			} else {
				if (0 != listCheckbox.size())
					listCheckbox.clear();

				box.setVisibility(View.GONE);
			}
			for (int i = 0; i < flag.length; i++) {
				View view = convertView.findViewById(ItemIDs[i]);
				final Object data = list.get(position).get(flag[i]);
				if (view instanceof ImageButton) {
					ImageButton ib = (ImageButton) view;
//					ib.setBottom(ItemIDs[i]);
					System.out.println("ib...................................");
				} else if (view instanceof TextView) {
					TextView tv = (TextView) view;
					tv.setText(null == data ? "" : String.valueOf(data));
					tv.setOnClickListener(null);
				} else if (view instanceof ImageView) {
					final ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
					if (!boo) {
						iv.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								String text = list.get(position)
										.get("sendName").toString();
								Intent intent = new Intent(XcqzLocalImage.this,
										XcqzShowPicUpload.class);
								intent.putExtra("picInfo", picInfo);
								intent.putExtra("fileName", text);
								intent.putExtra("flag", "display");
								startActivity(intent);
							}
						});
					}
				}
			}
			return convertView;
		}
	}
}
