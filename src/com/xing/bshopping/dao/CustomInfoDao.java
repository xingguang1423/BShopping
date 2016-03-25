package com.xing.bshopping.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xing.bshopping.entity.CustomInfo;

public class CustomInfoDao {

	private DBHelper helper;
	private SQLiteDatabase database;

	public CustomInfoDao(Context context) {
		helper = new DBHelper(context);
	}

	public void addCustomInfos(List<CustomInfo> customInfoList) {
		database = helper.getWritableDatabase();

		for (int i = 0; i < customInfoList.size(); i++) {
			CustomInfo customInfo = customInfoList.get(i);

			ContentValues contentValues = new ContentValues();
			contentValues.put("cName", customInfo.getcName());
			contentValues.put("cPassword", customInfo.getcPassword());
			contentValues.put("cPhone", customInfo.getcPhone());
			contentValues.put("cImgUrl", customInfo.getcImgUrl());

			database.insert("t_customInfo", null, contentValues);
		}
		database.close();
	}

	public void clearData() {
		database = helper.getWritableDatabase();

		database.execSQL("DELETE FROM t_customInfo");
		database.close();
	}

	public void showAllCustomInfos(List<CustomInfo> customInfoList) {
		database = helper.getWritableDatabase();
		CustomInfo customInfo = null;

		Cursor cursor = database.rawQuery("select * from t_customInfo", null);
		while (cursor.moveToNext()) {

			customInfo = new CustomInfo();
			customInfo.setcId(cursor.getInt(cursor.getColumnIndex("cId")));
			customInfo.setcName(cursor.getString(cursor.getColumnIndex("cName")));
			customInfo.setcPassword(cursor.getString(cursor.getColumnIndex("cPassword")));
			customInfo.setcPhone(cursor.getString(cursor.getColumnIndex("cPhone")));
			customInfo.setcImgUrl(cursor.getString(cursor.getColumnIndex("cImgUrl")));

			customInfoList.add(customInfo);
		}

		cursor.close();
		database.close();
	}
	
	public CustomInfo loginCustomInfo(String cPhone, String cPassword) {
		database = helper.getWritableDatabase();
		CustomInfo customInfo = null;

		Cursor cursor = database.rawQuery("select cId,cName,cPassword,cPhone,cImgUrl from t_customInfo where cPhone='"+cPhone + "' and cPassword='"+cPassword+"'", null);
		
		while (cursor.moveToNext()) {

			customInfo = new CustomInfo();
			customInfo.setcId(cursor.getInt(cursor.getColumnIndex("cId")));
			customInfo.setcName(cursor.getString(cursor.getColumnIndex("cName")));
			customInfo.setcPassword(cursor.getString(cursor.getColumnIndex("cPassword")));
			customInfo.setcPhone(cursor.getString(cursor.getColumnIndex("cPhone")));
			customInfo.setcImgUrl(cursor.getString(cursor.getColumnIndex("cImgUrl")));

		}

		cursor.close();
		database.close();
		
		return customInfo;
	}
	
	
	/**
	 * 使用update更新头像imageURL记录
	 * @param customInfo
	 */
	public void updateImageUrl(CustomInfo customInfo){
		database = helper.getWritableDatabase();
		
		//update(String table, ContentValues values, String whereClause, String[] whereArgs)
		//update(表的名称, ContentValues值集合, where关键字后的内容, ?参数)
		ContentValues values = new ContentValues();
		values.put("cImgUrl", customInfo.getcImgUrl());
		database.update("t_customInfo", values, "cPhone=?", new String[]{customInfo.getcPhone()});
		
		database.close();
	}
	
	
//	public void update(Person person){
//		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//		db.execSQL("update person set name=?,phone=?,amount=? where personid=?", 
//				new Object[]{person.getName(),person.getPhone(), person.getAmount(),person.getId()});
//	}
	
}
