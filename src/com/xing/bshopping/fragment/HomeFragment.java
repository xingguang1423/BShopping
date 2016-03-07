package com.xing.bshopping.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;
import com.xing.bshopping.activity.SearchActivity;
import com.xing.bshopping.activity.TuanDailActivity;
import com.xing.bshopping.adapter.ClassesAdapter;
import com.xing.bshopping.adapter.ClassesPageAdapter;
import com.xing.bshopping.adapter.GoodsInfoAdapter;
import com.xing.bshopping.entity.GoodsInfo;
import com.xing.bshopping.utils.TitleBuilder;
import com.xing.bshopping.utils.ToastUtils;
import com.xing.bshopping.widget.MeiTuanListView;
import com.xing.bshopping.widget.MeiTuanListView.OnMeiTuanRefreshListener;

public class HomeFragment extends BaseFragment implements
		OnMeiTuanRefreshListener {

	private View view;

	// ==================分类的tab================
	private LinearLayout view_pager_content;
	private LinearLayout viewGroup;

	private List<Map<String, Object>> listView;

	private int next = 0;
	private ViewPager adViewPager;
	private ClassesPageAdapter classesPageAdapter;
	private ImageView[] imageViews;
	private ImageView imageView;
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private List<View> gridViewlist = new ArrayList<View>();

	// ==================下拉刷新的动画==================
	private static MeiTuanListView mListView;

	// =======================商品列表的listview======================
	private static GoodsInfoAdapter goodsInfoAdapter;
	private List<GoodsInfo> goodsInfoList = null;

	// private List<String> mDatas;
	// private static ArrayAdapter<String> mAdapter;
	private final static int REFRESH_COMPLETE = 0;

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
				goodsInfoAdapter.notifyDataSetChanged();
				mListView.setSelection(0);
				break;

			default:
				break;
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = View.inflate(activity, R.layout.frag_home, null);

		// initCategory();
		initView();

		new TitleBuilder(view).setLeftText("珠海")
				.setLeftOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ToastUtils.showToast(activity, "珠海", 1);
					}
				}).setTitleImage(R.drawable.home_top_search)
				.setRightImage1(R.drawable.actionbar_icon_msg)
				.setRightImage2(R.drawable.actionbar_icon_scan)
				.setRightOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ToastUtils.showToast(activity, "Geek!!", 1);

					}
				}, R.id.titlebar_iv2_right)
				.setRightOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ToastUtils.showToast(activity, "Dan!!", 1);
					}
				}, R.id.titlebar_iv1_right)
				.setTitleOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						Intent intent = new Intent(activity, SearchActivity.class);
						startActivity(intent);
						
					}
				}).build();

		return view;
	}

	private void initView() {

		// =================下拉刷新的动画=================
		mListView = (MeiTuanListView) view
				.findViewById(R.id.listview_like_shop);

		View HeaderView = View.inflate(activity, R.layout.home_gridview_class,
				null);
		View FooterView = View.inflate(activity, R.layout.include_footer_view,
				null);
		mListView.addHeaderView(HeaderView);
		mListView.addFooterView(FooterView);

		// String[] data = new String[] { "hello world", "hello world",
		// "hello world", "hello world", "hello world", "hello world",
		// "hello world", "hello world", "hello world", "hello world",
		// "hello world", "hello world", "hello world", "hello world", };
		// mDatas = new ArrayList<String>(Arrays.asList(data));
		goodsInfoList = new ArrayList<GoodsInfo>();

		goodsInfoList.add(new GoodsInfo(1, "五洲佳肴自助美食汇",
				"【井岸镇】自助晚餐/韩式烤肉2选1，提供免费wifi，免预约", 44.8f, 58.0f, true,
				"http://pic10.nipic.com/20101010/5713677_174509363484_2.jpg"));
		goodsInfoList.add(new GoodsInfo(2, "【东莞】尼罗河酒店",
				"美尼斯西餐厅单人自助晚餐+巴比伦国际水会单人净桑券", 118f, 158f, false,
				"http://p0.meituan.net/320.0.a/deal/__49603001__8471795.jpg.webp"));
		goodsInfoList.add(new GoodsInfo(3, "【东莞】铂尔曼酒店",
				"东莞旗峰山铂尔曼酒店莱斯西餐厅自助午餐", 178f, 220.5f, true,
				"http://p0.meituan.net/320.0.a/deal/__44947301__3591238.jpg.webp"));
		goodsInfoList.add(new GoodsInfo(3, "【东莞等】汤响自助回转火锅百汇",
				"单人自助火锅午餐，提供免费WiFi", 39f, 49f, true,
				"http://p1.meituan.net/320.0.a/deal/4f55d98401c512c36a9870dc3c0dafd3369013.jpg.webp"));
		
		// mAdapter = new ArrayAdapter<String>(activity,
		// android.R.layout.simple_list_item_1, mDatas);
		goodsInfoAdapter = new GoodsInfoAdapter(activity, goodsInfoList);
		mListView.setAdapter(goodsInfoAdapter);
		mListView.setOnMeiTuanRefreshListener(this);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				GoodsInfo goodsInfo = goodsInfoAdapter.getItem(position-2);
				Intent intent = new Intent(activity, TuanDailActivity.class);
				intent.putExtra("goodsInfo", goodsInfo);
				startActivity(intent);
				
//				ToastUtils.showToast(activity, goodsInfo+"", 1);
			}
			
		});

		// ================左右滑动的分类tab================

		view_pager_content = (LinearLayout) view
				.findViewById(R.id.view_pager_content);
		viewGroup = (LinearLayout) view.findViewById(R.id.viewGroup);

		listView = new ArrayList<Map<String, Object>>();
		// 创建ViewPager
		adViewPager = new ViewPager(activity);

		// 获取屏幕像素相关信息
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 根据屏幕信息设置ViewPager容器的宽高
		adViewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
				dm.heightPixels));

		// 将ViewPager容器设置到布局文件父容器中
		view_pager_content.addView(adViewPager);

		getClassesView();

		// 小圆点
		initCirclePoint();

		adViewPager.setAdapter(classesPageAdapter);
		adViewPager.setOnPageChangeListener(new AdPageChangeListener());

	}

	/**
	 * ViewPager 页面改变监听器
	 */
	private final class AdPageChangeListener implements OnPageChangeListener {

		/**
		 * 页面滚动状态发生改变的时候触发
		 */
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		/**
		 * 页面滚动的时候触发
		 */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		/**
		 * 页面选中的时候触发
		 */
		@Override
		public void onPageSelected(int arg0) {

			// 获取当前显示的页面是哪个页面
			System.out.println("onPageSelected");
			atomicInteger.getAndSet(arg0);

			// 重新设置原点布局集合
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.mtadvert_indicator_selected);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.mtadvert_indicator_normal);
				}
			}

		}

	}

	/**
	 * 小圆点
	 */
	private void initCirclePoint() {

		imageViews = new ImageView[gridViewlist.size()];
		for (int i = 0; i < gridViewlist.size(); i++) {
			// 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
			imageView = new ImageView(activity);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			layoutParams.setMargins(10, 0, 10, 0);
			imageView.setLayoutParams(layoutParams);
			imageViews[i] = imageView;

			// 初始值, 默认第0个选中
			if (i == 0) {
				imageViews[i]
						.setBackgroundResource(R.drawable.mtadvert_indicator_selected);
			} else {
				imageViews[i]
						.setBackgroundResource(R.drawable.mtadvert_indicator_normal);
			}
			// 将小圆点放入到布局中
			viewGroup.addView(imageViews[i]);

		}

	}

	private void getClassesView() {

		int[] drawableView = { R.drawable.ic_category_0,
				R.drawable.ic_category_1, R.drawable.ic_category_2,
				R.drawable.ic_category_3, R.drawable.ic_category_4,
				R.drawable.ic_category_6, R.drawable.ic_category_5,
				R.drawable.ic_category_10, R.drawable.ic_category_11,
				R.drawable.ic_category_14, R.drawable.ic_category_7,
				R.drawable.ic_category_9, R.drawable.ic_category_12,
				R.drawable.ic_category_13, R.drawable.ic_category_8,
				R.drawable.ic_category_16

		};

		String[] titleView = { "美食", "电影", "酒店", "KTV", "今日新单", "周边游", "代金券",
				"休闲娱乐", "丽人", "购物", "小吃快餐", "生活服务", "足疗按摩", "旅游", "蛋糕甜点",
				"全部分类"

		};

		for (int i = 0; i < drawableView.length; i++) {
			Map<String, Object> mapView = new HashMap<String, Object>();
			mapView.put("image", drawableView[i]);
			mapView.put("title", titleView[i]);
			listView.add(mapView);
		}

		getGridView();

	}

	private void getGridView() {

		boolean bool = true;

		while (bool) {
			int result = next + 10;

			if (listView.size() != 0 && result < listView.size()) {

				GridView gridView = new GridView(activity);
				gridView.setNumColumns(5);
				gridView.setSelector(R.color.bg_gray);
				gridView.setPadding(0, 10, 0, 0);
				gridView.setHorizontalSpacing(10);
				gridView.setVerticalSpacing(10);

				List<Map<String, Object>> gridlist = new ArrayList<Map<String, Object>>();
				for (int i = next; i < result; i++) {
					gridlist.add(listView.get(i));
				}

				ClassesAdapter classesAdapter = new ClassesAdapter(activity,
						gridlist);
				gridView.setAdapter(classesAdapter);
				next = result;
				gridViewlist.add(gridView);

			} else if (result - listView.size() < 10) {

				System.out.println("剩余多少" + (result - listView.size()));
				List<Map<String, Object>> gridlist = new ArrayList<Map<String, Object>>();
				for (int i = next; i < listView.size(); i++) {
					gridlist.add(listView.get(i));
				}

				GridView gridView = new GridView(activity);
				gridView.setNumColumns(5);
				gridView.setSelector(R.color.bg_gray);
				gridView.setPadding(0, 10, 0, 0);

				ClassesAdapter myAdapter = new ClassesAdapter(activity,
						gridlist);
				gridView.setAdapter(myAdapter);
				next = listView.size() - 1;
				gridViewlist.add(gridView);
				bool = false;

			} else {
				System.out.println("执行了这句话");
				bool = false;
			}
			classesPageAdapter = new ClassesPageAdapter(gridViewlist);
		}
	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					goodsInfoList
							.add(0,
									new GoodsInfo(4, "【东莞】汇美酒店", "单人自助晚餐88元，提供免费WiFi",
											88f, 108f, false,
											"http://p1.meituan.net/320.0.a/deal/__29406384__9346162.jpg.webp"));

					mInterHandler.sendEmptyMessage(REFRESH_COMPLETE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

}
