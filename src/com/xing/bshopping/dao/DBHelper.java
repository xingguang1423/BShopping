package com.xing.bshopping.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static String name = "bshopping.db";
	private static int version = 1;

	public DBHelper(Context context) {
		super(context, name, null, version);
	}

	// 创建数据库使用
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql_GoodsInfo = "create table t_goodsInfo("+
					"goodsId integer primary key,"+
					"goodsName text not null,"+
					"goodsContent text,"+
					"goodsValidity text,"+
					"goodsPrice real,"+
					"goodsShopPrice real,"+
					"isGoodsBooking integer not null default '0',"+
					"goodsImgUrl text,"+
					"goodsNotes text"+
					");";
		
		String sql_CustomInfo = "create table t_customInfo( "+
				"cId integer primary key, "+
				"cName text not null, "+
				"cPassword text not null, "+
				"cPhone text, "+
				"cImgUrl text "+
				");";
		
		
		
		db.execSQL(sql_GoodsInfo);
		db.execSQL(sql_CustomInfo);
		
		
	}

	// 升级数据库使用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//		if( oldVersion ==1 ){
//			db.execSQL("alter table CharacterTab add internal_id integer");
			
//		}
		
		
		
	}

	
}
