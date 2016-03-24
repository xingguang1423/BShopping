package com.xing.bshopping.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;
import com.xing.bshopping.adapter.MoreItemAdapter;
import com.xing.bshopping.entity.MoreItem;
import com.xing.bshopping.utils.TitleBuilder;
import com.xing.bshopping.widget.WrapHeightListView;

@SuppressLint("ResourceAsColor")
public class MoreFragment extends BaseFragment {

	private View view;

	private WrapHeightListView lv_more_items;

	private MoreItemAdapter adapter;
	private List<MoreItem> moreItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = View.inflate(activity, R.layout.frag_more, null);

		initView();

		setItem();

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		// show/hide方法不会走生命周期
		System.out.print("more frag onStart()");
	}

	private void initView() {

		new TitleBuilder(view).setLeftText("更多")
				.setLeftTextColor(R.color.black)
				.setBgColor(R.color.bg_gray_bar).setRightText("精品应用").build();
		// 设置栏列表
		lv_more_items = (WrapHeightListView) view
				.findViewById(R.id.lv_more_items);
		moreItems = new ArrayList<MoreItem>();
		adapter = new MoreItemAdapter(activity, moreItems);
		lv_more_items.setAdapter(adapter);

	}

	private void setItem() {

		moreItems.add(new MoreItem(true, "非wifi下使用省流量模式", R.drawable.ic_global_uikit_switch_off, ""));
		moreItems.add(new MoreItem(false, "邀请好友使用高B", R.drawable.ic_webview_bar_forward_normal, ""));
		moreItems.add(new MoreItem(false, "字号大小", R.drawable.ic_webview_bar_forward_normal, "中字号(默认)"));
		moreItems.add(new MoreItem(false, "清空缓存", R.drawable.ic_webview_bar_forward_normal, "0K"));
		
		moreItems.add(new MoreItem(true, "扫一扫", R.drawable.ic_webview_bar_forward_normal, ""));
		moreItems.add(new MoreItem(false, "意见反馈", R.drawable.ic_webview_bar_forward_normal, ""));
		moreItems.add(new MoreItem(false, "问卷调查", R.drawable.ic_webview_bar_forward_normal, ""));
		moreItems.add(new MoreItem(false, "支付帮助", R.drawable.ic_webview_bar_forward_normal, ""));
		moreItems.add(new MoreItem(false, "检查更新", R.drawable.ic_webview_bar_forward_normal, "好赞，当前已是最新版本"));
		moreItems.add(new MoreItem(false, "关于高B", R.drawable.ic_webview_bar_forward_normal, ""));
		moreItems.add(new MoreItem(false, "加入我们", R.drawable.ic_webview_bar_forward_normal, ""));
		moreItems.add(new MoreItem(false, "诊断网络", R.drawable.ic_webview_bar_forward_normal, ""));
		moreItems.add(new MoreItem(false, "版权信息", R.drawable.ic_webview_bar_forward_normal, ""));
		
		adapter.notifyDataSetChanged();
		
	}

}
