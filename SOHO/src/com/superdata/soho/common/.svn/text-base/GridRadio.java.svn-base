package com.superdata.soho.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.superdata.soho.R;

/**
 * 通用的一个“ 下拉框”
 * 
 * @author 谭树林 text1 code text2 name
 * 
 */
public class GridRadio extends BaseActivitySimple {

	ListView cxcitylv;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	List<Map<String, String>> listInit;
	int resultCode;
	String checkCode;
	ImageButton queryBtn, delBtn;
	EditText queryEditor;
	View plant;
	ListViewAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cx_city);

		int titleBar = getIntent().getIntExtra("titleBar", 0);
		
		if(1== titleBar){
			View v = findViewById(R.id.includeLayout);
			v.setVisibility(View.VISIBLE);
			queryBtn = (ImageButton) v.findViewById(R.id.queryBtn);
			delBtn = (ImageButton) v.findViewById(R.id.delBtn);
			queryEditor = (EditText) v.findViewById(R.id.queryEdit);
			plant = v.findViewById(R.id.plant);
			queryBtn.setOnClickListener(this);
			delBtn.setOnClickListener(this);
			queryEditor.addTextChangedListener(watcher);
		}
		

		cxcitylv = (ListView) findViewById(R.id.cxcitylv);
		// 设置数据
		if (0 == data.size()) {
			Intent intent = getIntent();
			resultCode = intent.getIntExtra("resultCode", 1);
			checkCode = intent.getStringExtra("checkCode");
			listInit = Utils.string2Liststr(intent.getStringExtra("data"));
			for (Map<String, String> map : listInit) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("text1", map.get("text1"));
				m.put("text2", map.get("text2"));
				data.add(m);
			}
		}
		 adapter = new ListViewAdapter(this, data,
				R.layout.listviewshow, new String[] { "text1", "text2" },
				new int[] { R.id.text1, R.id.text2 }, "addListenerCity",
				checkCode);
		cxcitylv.setAdapter(adapter);

		// 设置点击
		cxcitylv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("text1", data.get(position).get("text1")
						.toString());
				intent.putExtra("text2", data.get(position).get("text2")
						.toString());
				setResult(resultCode, intent);
				finish();
			}
		});
	}

	/**
	 * 返回键 控制
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.putExtra("text1", "-1");
			intent.putExtra("text2", " ");
			setResult(resultCode, intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.queryBtn:
			Log.i("tanshulin","查询事件。。。。。。。。。。。。。。。。。。。");
				// 按条件加载
				if (!"".equals(queryEditor.getText().toString().trim())) {
					String text = queryEditor.getText().toString().trim();
					data.clear();
					for (Map<String, String> map : listInit) {
						if (map.get("text2").contains(text)) {
							Map<String, Object> m = new HashMap<String, Object>();
							m.put("text1", map.get("text1"));
							m.put("text2", map.get("text2"));
							data.add(m);
						}
					}
					Log.i("tanshulin","按条件查找，条件："+text+" 结果数："+data.size());
					adapter.notifyDataSetInvalidated();
				}else{
				Log.i("tanshulin","是否准备加载全部？ data.size:"+data.size()+"  listInit.size:"+listInit.size());
				// 加载全部
				if(data.size() != listInit.size()){
					Log.i("tanshulin","加载全部");
					data.clear();
					for (Map<String, String> map : listInit) {
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("text1", map.get("text1"));
						m.put("text2", map.get("text2"));
						data.add(m);
					}
					adapter.notifyDataSetInvalidated();
				}else{
					Log.i("tanshulin","不加载全部");
				}
			}
			
			break;
		case R.id.delBtn:
			queryEditor.setText(null);
			break;
		default:
			break;
		}
	}

	/** 文本域改变事件处理 **/
	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			String content = s.toString().trim();
			if (content.length() > 0) {
				plant.setVisibility(View.VISIBLE);
				delBtn.setVisibility(View.VISIBLE);
			} else {
				plant.setVisibility(View.GONE);
				delBtn.setVisibility(View.GONE);

				// 重新加载
				// new Thread(resetListViewRun).start();

			}
		}
	};
}
