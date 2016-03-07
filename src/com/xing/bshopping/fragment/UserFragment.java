package com.xing.bshopping.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;
import com.xing.bshopping.adapter.UserItemAdapter;
import com.xing.bshopping.entity.UserItem;
import com.xing.bshopping.utils.TitleBuilder;
import com.xing.bshopping.utils.ToastUtils;
import com.xing.bshopping.widget.WrapHeightListView;

@SuppressLint("ResourceAsColor")
public class UserFragment extends BaseFragment {

	private View view;
	
	private WrapHeightListView lv_user_items;
	
	private UserItemAdapter adapter;
	private List<UserItem> userItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = View.inflate(activity, R.layout.frag_user, null);

		
		initView();

		setItem();

		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		// show/hide方法不会走生命周期
		System.out.print("user frag onStart()");
	}
	

	private void initView() {

		new TitleBuilder(view).setLeftText("我的")
		.setLeftTextColor(R.color.black)
		.setRightImage1(R.drawable.group_actionbar_message_icon)
		.setRightImage2(R.drawable.wallet__home_setting)
		.setRightOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToastUtils.showToast(activity, "设置!!", 1);

			}
		}, R.id.titlebar_iv2_right)
		.setRightOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToastUtils.showToast(activity, "消息!!", 1);
			}
		}, R.id.titlebar_iv1_right).setBgColor(R.color.white).build();

		
		// 设置栏列表
		lv_user_items = (WrapHeightListView) view
				.findViewById(R.id.lv_user_items);
		userItems = new ArrayList<UserItem>();
		adapter = new UserItemAdapter(activity, userItems);
		lv_user_items.setAdapter(adapter);
		
	}

	private void setItem() {
		
		userItems.add(new UserItem(false, R.drawable.ic_global_user_wallet, "美团钱包", "账户余额：￥0.00"));
		userItems.add(new UserItem(false, R.drawable.ic_global_user_voucher, "抵用券", "0"));
		
		userItems.add(new UserItem(true, R.drawable.ic_global_user_wallet, "积分商城", "好礼已上线"));
		userItems.add(new UserItem(false, R.drawable.ic_global_user_voucher, "免费福利", "80万霸王免费抢"));
		
		userItems.add(new UserItem(true, R.drawable.ic_global_user_recommend, "每日推荐", "New"));
		userItems.add(new UserItem(true, R.drawable.ic_global_user_cooperation, "我要合作", "轻松开店，招财进宝"));
		adapter.notifyDataSetChanged();

	}

}
