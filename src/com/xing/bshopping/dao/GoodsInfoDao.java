package com.xing.bshopping.dao;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xing.bshopping.entity.GoodsInfo;

public class GoodsInfoDao {

	private DBHelper helper;
	private SQLiteDatabase database;

	private Activity activity;

	public GoodsInfoDao(Activity activity) {
		this.activity = activity;
		helper = new DBHelper(activity);
	}
	
	public void addGoodsInfos(List<GoodsInfo>  goodsInfoList){
		database = helper.getWritableDatabase();
		
		for (int i = 0; i < goodsInfoList.size(); i++) {
			GoodsInfo goodsInfo = goodsInfoList.get(i);
			
			ContentValues contentValues = new ContentValues();
			contentValues.put("goodsName", goodsInfo.getGoodsName());
			contentValues.put("goodsContent", goodsInfo.getGoodsContent());
			contentValues.put("goodsPrice", goodsInfo.getGoodsPrice());
			contentValues.put("goodsShopPrice", goodsInfo.getGoodsShopPrice());
			contentValues.put("isGoodsBooking", goodsInfo.getIsGoodsBooking());
			contentValues.put("goodsImgUrl", goodsInfo.getGoodsImgUrl());
			contentValues.put("goodsNotes", goodsInfo.getGoodsNotes());
			
			database.insert("t_goodsInfo", null, contentValues);
		}
		database.close();
	}
	
	public void clearData(){
		database = helper.getWritableDatabase();
		
		database.execSQL("DELETE FROM t_goodsInfo");
		database.close();
	}
	
	public void showAllGoodsInfos(List<GoodsInfo> goodsInfoList){
		database = helper.getWritableDatabase();
		GoodsInfo goodsInfo = null;
		
		Cursor cursor = database.rawQuery("select * from t_goodsInfo", null);
		while( cursor.moveToNext() ){
			
			goodsInfo = new GoodsInfo();
			goodsInfo.setGoodsId(cursor.getInt(cursor.getColumnIndex("goodsId")));
			goodsInfo.setGoodsName(cursor.getString(cursor.getColumnIndex("goodsName")));
			goodsInfo.setGoodsContent(cursor.getString(cursor.getColumnIndex("goodsContent")));
			goodsInfo.setGoodsPrice(cursor.getFloat(cursor.getColumnIndex("goodsPrice")));
			goodsInfo.setGoodsShopPrice(cursor.getFloat(cursor.getColumnIndex("goodsShopPrice")));
			goodsInfo.setIsGoodsBooking(cursor.getInt(cursor.getColumnIndex("isGoodsBooking")));
			goodsInfo.setGoodsImgUrl(cursor.getString(cursor.getColumnIndex("goodsImgUrl")));
			goodsInfo.setGoodsNotes(cursor.getString(cursor.getColumnIndex("goodsNotes")));
			
			goodsInfoList.add(goodsInfo);
		}
		
		cursor.close();
		database.close();
	}
	/**
	 * 搜索goodsInfo
	 * @param goodsName
	 * @return
	 */
	public List<GoodsInfo> selectGoodsInfosByName(String goodsName){
		database = helper.getWritableDatabase();
		GoodsInfo goodsInfo = null;
		List<GoodsInfo> goodsInfoList = new ArrayList<GoodsInfo>();
		
		
		Cursor cursor = database.rawQuery("select * from t_goodsInfo t where t.goodsName like '%"+goodsName+"%'", null);
//		Cursor cursor = database.rawQuery("select * from t_goodsInfo t where t.goodsName like '%珠海%'", null);
		while( cursor.moveToNext() ){
		
			goodsInfo = new GoodsInfo();
			goodsInfo.setGoodsId(cursor.getInt(cursor.getColumnIndex("goodsId")));
			goodsInfo.setGoodsName(cursor.getString(cursor.getColumnIndex("goodsName")));
			goodsInfo.setGoodsContent(cursor.getString(cursor.getColumnIndex("goodsContent")));
			goodsInfo.setGoodsPrice(cursor.getFloat(cursor.getColumnIndex("goodsPrice")));
			goodsInfo.setGoodsShopPrice(cursor.getFloat(cursor.getColumnIndex("goodsShopPrice")));
			goodsInfo.setIsGoodsBooking(cursor.getInt(cursor.getColumnIndex("isGoodsBooking")));
			goodsInfo.setGoodsImgUrl(cursor.getString(cursor.getColumnIndex("goodsImgUrl")));
			goodsInfo.setGoodsNotes(cursor.getString(cursor.getColumnIndex("goodsNotes")));
			
			goodsInfoList.add(goodsInfo);
		}
		
		cursor.close();
		database.close();
		
		return goodsInfoList;
	}
	
	
	
	
	
	
}
