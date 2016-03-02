package com.xing.bshopping.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;
import com.xing.bshopping.adapter.ClassesAdapter;
import com.xing.bshopping.adapter.ClassesPageAdapter;

public class HomeFragment extends BaseFragment {

	private View view;

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

	// ====================================

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// initCategory();
		initView();

		
		return view;
	}

	private void initView() {
		view = View.inflate(activity, R.layout.frag_home, null);

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

	/*
	 * 每隔固定时间切换广告栏图片
	 */
	// private final Handler handler = new Handler() {
	//
	// public void handleMessage(android.os.Message msg) {
	// System.out.println("handleMessage");
	// adViewPager.setCurrentItem(msg.what);
	// super.handleMessage(msg);
	// };
	//
	// };

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
						.setBackgroundResource(R.drawable.point_focused);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.point_unfocused);
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
				imageViews[i].setBackgroundResource(R.drawable.point_focused);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.point_unfocused);
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
				R.drawable.ic_category_13, R.drawable.ic_category_8 ,
				R.drawable.ic_category_16};

		String[] titleView = { 
				"美食", "电影", "酒店", "KTV", "今日新单", 
				"周边游", "代金券","休闲娱乐", "丽人", "购物", 
				"小吃快餐", "生活服务", "足疗按摩", "旅游", "蛋糕甜点",
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

}
