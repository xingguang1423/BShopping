package com.xing.bshopping.dao;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xing.bshopping.entity.BusinessInfo;

public class BusinessInfoDao {

	private DBHelper helper;
	private SQLiteDatabase database;

	private Activity activity;

	public BusinessInfoDao(Activity activity) {
		this.activity = activity;
		helper = new DBHelper(activity);
	}
	
	public void addBusinessInfos(List<BusinessInfo>  businessInfoList){
		database = helper.getWritableDatabase();
		
		for (int i = 0; i < businessInfoList.size(); i++) {
			BusinessInfo businessInfo = businessInfoList.get(i);
			
			ContentValues contentValues = new ContentValues();
			contentValues.put("bName", businessInfo.getbName());
			contentValues.put("bPhone", businessInfo.getbPhone());
			contentValues.put("bContentInfo", businessInfo.getbContentInfo());
			contentValues.put("bAddress", businessInfo.getbAddress());
			contentValues.put("bType", businessInfo.getbType());
			contentValues.put("bImgUrl", businessInfo.getbImgUrl());
			
			database.insert("t_businessInfo", null, contentValues);
		}
		System.out.println("数据表创建成功!!");
		database.close();
	}
	
	public void clearData(){
		database = helper.getWritableDatabase();
		
		database.execSQL("DELETE FROM t_businessInfo");
		database.close();
	}
	
	public void showAllBusinessInfos(List<BusinessInfo> businessInfoList){
		database = helper.getWritableDatabase();
		BusinessInfo businessInfo = null;
		
		Cursor cursor = database.rawQuery("select * from t_businessInfo", null);
		while( cursor.moveToNext() ){
			
			businessInfo = new BusinessInfo();
			businessInfo.setbId(cursor.getInt(cursor.getColumnIndex("bId")));
			businessInfo.setbName(cursor.getString(cursor.getColumnIndex("bName")));
			businessInfo.setbPhone(cursor.getString(cursor.getColumnIndex("bPhone")));
			businessInfo.setbContentInfo(cursor.getString(cursor.getColumnIndex("bContentInfo")));
			businessInfo.setbAddress(cursor.getString(cursor.getColumnIndex("bAddress")));
			businessInfo.setbType(cursor.getString(cursor.getColumnIndex("bType")));
			businessInfo.setbImgUrl(cursor.getString(cursor.getColumnIndex("bImgUrl")));
			
			businessInfoList.add(businessInfo);
		}
		
		cursor.close();
		database.close();
	}
	/**
	 * 搜索商家信息  -- t_businessInfo
	 * @param goodsName
	 * @return
	 */
	public List<BusinessInfo> selectBusinessInfosByType(String bType){
		database = helper.getWritableDatabase();
		BusinessInfo businessInfo = null;
		List<BusinessInfo> businessInfoList = new ArrayList<BusinessInfo>();
		
		
		Cursor cursor = database.rawQuery("select * from t_businessInfo t where t.bType like '%"+bType+"%'", null);
//		Cursor cursor = database.rawQuery("select * from t_businessInfo t where t.goodsName like '%珠海%'", null);
		while( cursor.moveToNext() ){
		
			businessInfo = new BusinessInfo();

			businessInfo.setbId(cursor.getInt(cursor.getColumnIndex("bId")));
			businessInfo.setbName(cursor.getString(cursor.getColumnIndex("bName")));
			businessInfo.setbPhone(cursor.getString(cursor.getColumnIndex("bPhone")));
			businessInfo.setbContentInfo(cursor.getString(cursor.getColumnIndex("bContentInfo")));
			businessInfo.setbAddress(cursor.getString(cursor.getColumnIndex("bAddress")));
			businessInfo.setbType(cursor.getString(cursor.getColumnIndex("bType")));
			businessInfo.setbImgUrl(cursor.getString(cursor.getColumnIndex("bImgUrl")));
			businessInfoList.add(businessInfo);
		}
		
		cursor.close();
		database.close();
		
		return businessInfoList;
	}
	
	
	
	
}
