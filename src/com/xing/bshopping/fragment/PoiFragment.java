package com.xing.bshopping.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;
import com.xing.bshopping.adapter.GoodsInfoAdapter;
import com.xing.bshopping.entity.GoodsInfo;
import com.xing.bshopping.utils.TitleBuilder;
import com.xing.bshopping.widget.MeiTuanListView;
import com.xing.bshopping.widget.MeiTuanListView.OnMeiTuanRefreshListener;

@SuppressLint("ResourceAsColor")
public class PoiFragment extends BaseFragment implements
		OnMeiTuanRefreshListener {

	private View view;

	private static MeiTuanListView mListView;
	private final static int REFRESH_COMPLETE = 0;
	private static GoodsInfoAdapter mAdapter;
	private ArrayList<GoodsInfo> goodsInfos = new ArrayList<GoodsInfo>();
	/**
	 * 判断是否到达底部
	 */
	private boolean isLastRow = false;

	private static View footView;

	private final static int REFRESH_FOOTER = 1;
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
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(0);
				break;
			case REFRESH_FOOTER:
				mListView.setOnRefreshComplete();
				mAdapter.notifyDataSetChanged();
				removeFootView(mListView, footView);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		initView();

		mListView.setOnScrollListener(new OnScrollListener() {

			/**
			 * 正在滚动时回调，回调2-3次，手指没抛则回调2次，scrollState=2的这次不回调 回调顺序如下：
			 * 第一次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1)正在滚动
			 * 第二次：scrollState = SCROLL_STATE_FLING(2)手指做了抛的动作（手指离开屏幕前，用力滑了一下）
			 * 第三次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
			 * 
			 * 当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1； 由于用户的操作，屏幕产生惯性滑动时为2
			 */
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (isLastRow
						&& scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					addFootView(mListView, footView);
					loadData();
					// 执行加载代码
					isLastRow = false;
				}
			}

			private void loadData() {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							mAdapter.addItem(new GoodsInfo(2, "Nice1",
									"Nice出品 ，必是良品", 50.2f, 65f, false,
									"http://img4.imgtn.bdimg.com/it/u=289791729,3485011159&fm=21&gp=0.jpg"));
							mAdapter.addItem(new GoodsInfo(2, "Nice2",
									"Nice出品 ，必是良品", 50.2f, 65f, false,
									"http://img4.imgtn.bdimg.com/it/u=289791729,3485011159&fm=21&gp=0.jpg"));
							mAdapter.addItem(new GoodsInfo(2, "Nice3",
									"Nice出品 ，必是良品", 50.2f, 65f, false,
									"http://img4.imgtn.bdimg.com/it/u=289791729,3485011159&fm=21&gp=0.jpg"));
							mAdapter.addItem(new GoodsInfo(2, "Nice4",
									"Nice出品 ，必是良品", 50.2f, 65f, false,
									"http://img4.imgtn.bdimg.com/it/u=289791729,3485011159&fm=21&gp=0.jpg"));
							mInterHandler.sendEmptyMessage(REFRESH_FOOTER);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}

			/**
			 * 滚动的时候一直回调，直到停止滚动时才停止回调，单击时回调一次
			 * firstVisibleItem:当前嫩看见的第一个列表项ID(从0开始,小半个也算)
			 * visibleItemCount：当前能看见的列表项个数(小半个也算) totalItemCount：列表项总共数
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem + visibleItemCount == totalItemCount
						&& totalItemCount > 0) {
					isLastRow = true;
				}
			}
		});

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
		

		View headerView = View.inflate(activity,
				R.layout.include_seller_header, null);

		mListView = (MeiTuanListView) view.findViewById(R.id.listview);

		mListView.addHeaderView(headerView);

		goodsInfos
				.add(new GoodsInfo(1, "五洲佳肴自助美食汇",
						"【井岸镇】自助晚餐/韩式烤肉2选1，提供免费wifi，免预约", 44.8f, 58f, true,
						"http://p0.meituan.net/460.280/deal/0599c13a4d3e820f35cd7034f8640597100266.jpg"));
		goodsInfos
				.add(new GoodsInfo(
						1,
						"猫婆重庆小面",
						"【兰埔】仅售9.9元！价值14元的超值单人套餐，提供免费WiFi，提供免费停车位。",
						9.9f,
						14f,
						false,
						"http://p0.meituan.net/460.280/deal/3fc3f41350ff2c1b03b14d8653cca7c794596.jpg@PC"));
		goodsInfos
				.add(new GoodsInfo(
						1,
						"CoCo都可",
						"【井岸镇等】奶茶饮品6选1,免预约",
						6.9f,
						10f,
						true,
						"http://p0.meituan.net/460.280/deal/a1bc66c750059379788eb5b1c51ed02b45057.jpg@PC"));
		goodsInfos
				.add(new GoodsInfo(
						1,
						"金品轩猪肚鸡",
						"【前山】胡椒猪肚鸡(中堡)1份,包间免费",
						44.8f,
						58f,
						true,
						"http://p1.meituan.net/460.280/deal/6498401e15db68705fb584375e71f86a83118.jpg@PC"));

		mAdapter = new GoodsInfoAdapter(activity, goodsInfos);

		mListView.setAdapter(mAdapter);
		mListView.setOnMeiTuanRefreshListener(this);

		footView = View.inflate(activity, R.layout.include_footview_loading,
				null);
	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					goodsInfos
							.add(0,
									new GoodsInfo(2, "Nice！", "Nice出品 ，必是良品",
											50f, 65f, false,
											"http://img4.imgtn.bdimg.com/it/u=289791729,3485011159&fm=21&gp=0.jpg"));
					mInterHandler.sendEmptyMessage(REFRESH_COMPLETE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private static void addFootView(MeiTuanListView mtlv, View footView) {
		mtlv.addFooterView(footView);
		mtlv.setSelection(mtlv.getCount() - 1);
	}

	private static void removeFootView(MeiTuanListView mtlv, View footView) {
		mtlv.removeFooterView(footView);
	}

}
