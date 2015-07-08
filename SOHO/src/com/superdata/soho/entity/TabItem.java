package com.superdata.soho.entity;

import android.content.Intent;

/**
 * 
 * @ClassName: TabItem
 * @Description: TODO(tabhost的item类)
 * @author 刘定富
 * @date 2014年6月10日 上午9:45:33
 * 
 */
public class TabItem {
	private String title; // 标题
	private int icon; // 图标
	private int bg; // 背景
	private Intent intent; // 跳转事件

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:构造方法
	 * </p>
	 * 
	 * @param title
	 * @param icon
	 * @param bg
	 * @param intent
	 */
	public TabItem(String title, int icon, int bg, Intent intent) {
		super();
		this.title = title;
		this.icon = icon;
		this.bg = bg;
		this.intent = intent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getBg() {
		return bg;
	}

	public void setBg(int bg) {
		this.bg = bg;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}
}
