package com.xing.bshopping.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;
import com.xing.bshopping.adapter.BusinessInfoAdapter;
import com.xing.bshopping.entity.BusinessInfo;
import com.xing.bshopping.utils.TitleBuilder;
import com.xing.bshopping.widget.ExpandTabView;
import com.xing.bshopping.widget.MeiTuanListView;
import com.xing.bshopping.widget.MeiTuanListView.OnMeiTuanRefreshListener;
import com.xing.bshopping.widget.ViewCity;
import com.xing.bshopping.widget.ViewClassify;
import com.xing.bshopping.widget.ViewSort;

@SuppressLint("ResourceAsColor")
public class PoiFragment extends BaseFragment implements
		OnMeiTuanRefreshListener {

	private View view;

	// ==================下拉刷新的动画==================
	private static MeiTuanListView mListView;
	
	// =======================商家列表的listview======================
	private static BusinessInfoAdapter bAdapter;
	private ArrayList<BusinessInfo> businessInfos = new ArrayList<BusinessInfo>();
	
	//刷新
	private final static int REFRESH_COMPLETE = 0;
	
	
	
	/**
	 * 判断是否到达底部
	 */
//	private boolean isLastRow = false;
//
//	private static View footView;
//
//	private final static int REFRESH_FOOTER = 1;
	/**
	 * mInterHandler运行在主线程，因为setOnRefreshComplete需要改变ui，必须在主线程去改变ui
	 * 所以在handleMessage中调用mListView.setOnRefreshComplete();
	 */
	
	private InterHandler mInterHandler = new InterHandler();

	private static class InterHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case REFRESH_COMPLETE:
				mListView.setOnRefreshComplete();
				bAdapter.notifyDataSetChanged();
				mListView.setSelection(0);
				break;
			default:
				break;
			}
		}
	}

	
	
	//===================二级菜单================
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewClassify viewClassify;
	private ViewCity viewCity;
	private ViewSort viewRight;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		initView();
		
		initVaule();
		initListener();


		return view;
	}
	

	private void initView() {
		view = View.inflate(activity, R.layout.frag_poi, null);
		
		new TitleBuilder(view)
		.setLeftText("全部商家")
		.setRightImage2(R.drawable.food_ic_actionbar_ic_map)
		.setRightImage1(R.drawable.food_ic_actionbar_search)
		.setBgColor(R.color.white)
		.setLeftTextColor(R.color.black)
		.build();
		

		//===================二级菜单================
		expandTabView = (ExpandTabView) view.findViewById(R.id.expandtab_view);
		viewClassify = new ViewClassify(activity);
		viewCity = new ViewCity(activity);
		viewRight = new ViewSort(activity);
		
		
		
		//================================================
		View headerView = View.inflate(activity,
				R.layout.include_seller_header, null);
		
		
		// =================下拉刷新的动画=================
		mListView = (MeiTuanListView) view.findViewById(R.id.listview);

		mListView.addHeaderView(headerView);

		businessInfos
				.add(new BusinessInfo(1, "超好味餐厅",
						"123456789 ","", "斗门区江湾中路223号", "快餐", 
						"http://p0.meituan.net/460.280/deal/629b21db99e466eb8d04e34699d8f1b439327.jpg@PC_0_6_640_387a%7C388h_640w_2e_100q"));
		businessInfos
			.add(new BusinessInfo(2, "茶语小站",
				"123456789 ","", "斗门区中兴中路219号", "甜点饮品", 
				"http://p0.meituan.net/460.280/deal/bfcd43ccc8d0030f327df2e8ae8e0f3c102115.jpg@PC"));

		businessInfos
		.add(new BusinessInfo(3, "随变",
			"123456789 ","", "香洲区吉大街道93号", "其他美食", 
			"http://p0.meituan.net/460.280/deal/63bd580a0816cd6205007bf674f5e2e064656.jpg@PC"));

		bAdapter = new BusinessInfoAdapter(activity, businessInfos);

		mListView.setAdapter(bAdapter);
		mListView.setOnMeiTuanRefreshListener(this);
		
		

	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					
					bAdapter.addItem(new BusinessInfo(101, "老鸭粉丝馆",
							"123456789","", "斗门区井岸镇江湾二路51号", "其他美食", 
							"http://p1.meituan.net/460.280/deal/0c509884e84ec9a0ba56c1186fb3f81973346.jpg@PC"));
					
					bAdapter.addItem(new BusinessInfo(102, "哈宝三文鱼",
							"123456789","", "斗门区南江路178号", "其他美食", 
							"http://p1.meituan.net/320.0.a/deal/eca389a787b1434869457d59fb8a8f36125144.jpg"));
					
					mInterHandler.sendEmptyMessage(REFRESH_COMPLETE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	
private void initVaule() {
		
		mViewArray.add(viewClassify);
		mViewArray.add(viewCity);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("全部分类");
		mTextArray.add("全城");
		mTextArray.add("智能排序");
		
		expandTabView.setValue(mTextArray, mViewArray);
		expandTabView.setTitle(viewClassify.getShowText(), 0);
		expandTabView.setTitle(viewCity.getShowText(), 1);
		expandTabView.setTitle(viewRight.getShowText(), 2);
		
	}

	private void initListener() {
		
		viewClassify.setOnSelectListener(new ViewClassify.OnSelectListener() {


			@Override
			public void getValue(String showText) {
				onRefresh(viewClassify, showText);
				
			}
		});
		
		viewCity.setOnSelectListener(new ViewCity.OnSelectListener() {
			
			@Override
			public void getValue(String showText) {
				
				onRefresh(viewCity,showText);
				
			}
		});
		
		viewRight.setOnSelectListener(new ViewSort.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewRight, showText);
			}
		});
		
	}
	
	private void onRefresh(View view, String showText) {
		
		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		Toast.makeText(activity, showText, Toast.LENGTH_SHORT).show();

	}
	
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}
	
//	@Override
//	public void onBackPressed() {
//		
//		if (!expandTabView.onPressBack()) {
//			finish();
//		}
//		
//	}

}
