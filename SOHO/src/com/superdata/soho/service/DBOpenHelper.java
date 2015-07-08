package com.superdata.soho.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
/**
 * SQLite管理器，实现创建数据库和表，但版本变化时实现对表的数据库表的操作
 * @ClassName DBOpenHelper
 * @author Administrator
 * @date 2014年8月22日 上午10:54:41
 *
 */
public class DBOpenHelper extends SQLiteOpenHelper {

	private final static int VERSION = 1;
	private static final String DBNAME = "downloader.db";
	
	/**
	 * 通过构造方法
	 * 
	 * @param context    应用程序的上下文对象
	 *          
	 */
	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}
	
	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public DBOpenHelper(Context context, String name, CursorFactory factory){
		this(context,name,null,VERSION);
	}
	
	public DBOpenHelper(Context context,String name){
		this(context,name,null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE IF NOT EXISTS schedule(scheduleID integer primary key autoincrement,scheduleTypeID integer,remindID integer,scheduleContent text,scheduleDate text,time text,alartime integer)");
		db.execSQL("CREATE TABLE IF NOT EXISTS scheduletagdate(tagID integer primary key autoincrement,year integer,month integer,day integer,scheduleID integer)");
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog (id integer primary key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS schedule");
		db.execSQL("DROP TABLE IF EXISTS scheduletagdate");
		db.execSQL("DROP TABLE IF EXISTS filedownlog");
		onCreate(db);
	}
	

}
