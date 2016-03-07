package com.xing.bshopping.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xing.bshopping.R;
import com.xing.bshopping.adapter.ClassesPageAdapter;
import com.xing.bshopping.adapter.SearchHotAdapter;

public class SearchActivity extends Activity {

	// =====================热门搜索的tab=================

	private LinearLayout ll_hotsearch_pager;
	private LinearLayout ll_search_viewGroup;
	private ImageView titlebar_iv_back;
	
	private List<Map<String, Object>> searchPagerList;

	private int next = 0;
	private ViewPager searchViewPager;
	private ClassesPageAdapter classesPageAdapter;
	private ImageView[] imageViews;
	private ImageView imageView;
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private List<View> gridViewlist = new ArrayList<View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		initView();

	}

	private void initView() {

		ll_hotsearch_pager = (LinearLayout) findViewById(R.id.ll_hotsearch_pager);
		ll_search_viewGroup = (LinearLayout) findViewById(R.id.ll_search_viewGroup);
		titlebar_iv_back = (ImageView) findViewById(R.id.titlebar_iv_back);
		
		titlebar_iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		searchPagerList = new ArrayList<Map<String, Object>>();
		// 创建ViewPager
		searchViewPager = new ViewPager(SearchActivity.this);

		// 获取屏幕像素相关信息
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 根据屏幕信息设置ViewPager容器的宽高
		searchViewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
				dm.heightPixels));

		// 将ViewPager容器设置到布局文件父容器中
		ll_hotsearch_pager.addView(searchViewPager);

		 getSearchView();

		// 小圆点
		initCirclePoint();

		searchViewPager.setAdapter(classesPageAdapter);
		searchViewPager.setOnPageChangeListener(new AdPageChangeListener());

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
			System.out.println("SearchActivity");
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
			imageView = new ImageView(SearchActivity.this);
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
			ll_search_viewGroup.addView(imageViews[i]);

		}

	}
	
	
	private void getSearchView() {
		
		String[] titleView = {
				"自助餐", "回家湘", "华莱士",       "汉堡王", "真功夫", "必胜客",      "厨秀才","蛋糕","马得利",
				"牛扒", "诚丰影城", "肯得基",        "火锅", "服务", "足疗按摩",        "机票", "外卖","KTV"
		};
		
		for (int i = 0; i < titleView.length; i++) {
			Map<String, Object> mapView = new HashMap<String, Object>();
			mapView.put("hotSearchTitle", titleView[i]);
			searchPagerList.add(mapView);
		}
		
		
		getGridView();
	}
	
	private void getGridView() {

		boolean bool = true;

		while (bool) {
			int result = next + 9;

			if (searchPagerList.size() != 0 && result < searchPagerList.size()) {

				GridView gridView = new GridView(SearchActivity.this);
				gridView.setNumColumns(3);
				gridView.setSelector(R.color.bg_gray);
				gridView.setPadding(10, 10, 10, 10);
				gridView.setHorizontalSpacing(15);
				gridView.setVerticalSpacing(15);

				List<Map<String, Object>> gridlist = new ArrayList<Map<String, Object>>();
				for (int i = next; i < result; i++) {
					gridlist.add(searchPagerList.get(i));
				}

				SearchHotAdapter classesAdapter = new SearchHotAdapter(SearchActivity.this,gridlist);
				gridView.setAdapter(classesAdapter);
				next = result;
				gridViewlist.add(gridView);

			} else if (result - searchPagerList.size() < 9) {

				System.out.println("剩余多少" + (result - searchPagerList.size()));
				List<Map<String, Object>> gridlist = new ArrayList<Map<String, Object>>();
				for (int i = next; i < searchPagerList.size(); i++) {
					gridlist.add(searchPagerList.get(i));
				}

				GridView gridView = new GridView(SearchActivity.this);
				gridView.setNumColumns(3);
				gridView.setSelector(R.color.bg_gray);
				gridView.setPadding(10, 10, 10, 10);
				gridView.setHorizontalSpacing(15);
				gridView.setVerticalSpacing(15);
				

				SearchHotAdapter myAdapter = new SearchHotAdapter(SearchActivity.this,
						gridlist);
				gridView.setAdapter(myAdapter);
				next = searchPagerList.size() - 1;
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
