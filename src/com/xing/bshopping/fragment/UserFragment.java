package com.xing.bshopping.fragment;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;
import com.xing.bshopping.activity.LoginActivity;
import com.xing.bshopping.activity.UserDailActivity;
import com.xing.bshopping.adapter.UserItemAdapter;
import com.xing.bshopping.entity.UserItem;
import com.xing.bshopping.utils.TitleBuilder;
import com.xing.bshopping.utils.ToastUtils;
import com.xing.bshopping.widget.CircleImageView;
import com.xing.bshopping.widget.WrapHeightListView;

@SuppressLint("ResourceAsColor")
public class UserFragment extends BaseFragment implements OnClickListener {

	private View view;

	private WrapHeightListView lv_user_items;

	private UserItemAdapter adapter;
	private List<UserItem> userItems;

	private TextView tv_username;
	private CircleImageView iv_user_touphoto;

	Bitmap myBitmap;
	private byte[] mContent = null;
	ContentResolver resolver = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = View.inflate(activity, R.layout.frag_user, null);

		resolver = activity.getContentResolver();

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

		// 用户名
		tv_username = (TextView) view.findViewById(R.id.tv_username);
		tv_username.setOnClickListener(this);

		// 用户头像
		iv_user_touphoto = (CircleImageView) view.findViewById(R.id.iv_user_touphoto);

		iv_user_touphoto.setOnClickListener(this);

		if (activity.customInfo != null) {
			System.out
					.println("UserFrag:--->" + activity.customInfo.toString());
			tv_username.setText(activity.customInfo.getcName());

			// Uri imgUri = Uri.parse(activity.customInfo.getcImgUrl());
			// Uri imgUri =
			// Uri.parse("content://com.android.providers.media.documents/document/image%3A51383");
			// System.out.println(imgUri+"----");
			// iv_user_touphoto.setImageURI(imgUri);

			if (activity.customInfo.getcImgUrl() != null) {

				try {

					// 将图片内容解析成字节数组
					mContent = readStream(resolver.openInputStream(Uri
							.parse(activity.customInfo.getcImgUrl())));
					// 将字节数组转换为ImageView可调用的Bitmap对象
					myBitmap = getPicFromBytes(mContent, null);
					// //把得到的图片绑定在控件上显示
					iv_user_touphoto.setImageBitmap(myBitmap);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}

		}

		// 设置栏列表
		lv_user_items = (WrapHeightListView) view
				.findViewById(R.id.lv_user_items);
		userItems = new ArrayList<UserItem>();
		adapter = new UserItemAdapter(activity, userItems);
		lv_user_items.setAdapter(adapter);
	}

	private void setItem() {

		userItems.add(new UserItem(false, R.drawable.ic_global_user_wallet,
				"高B钱包", "账户余额：￥0.00"));
		userItems.add(new UserItem(false, R.drawable.ic_global_user_voucher,
				"抵用券", "0"));

		userItems.add(new UserItem(true, R.drawable.ic_global_user_wallet,
				"积分商城", "好礼已上线"));
		userItems.add(new UserItem(false, R.drawable.ic_global_user_voucher,
				"免费福利", "80万霸王免费抢"));

		userItems.add(new UserItem(true, R.drawable.ic_global_user_recommend,
				"每日推荐", "New"));
		userItems.add(new UserItem(true, R.drawable.ic_global_user_cooperation,
				"我要合作", "轻松开店，招财进宝"));
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_user_touphoto:
			/**
			 * 跳转到详细用户页
			 */
			if (activity.customInfo != null) {
				Intent photointent = new Intent(activity,
						UserDailActivity.class);
				System.out.println("UserFrag:--->"
						+ activity.customInfo.toString());

				photointent.putExtra("toUserdailCustomInfo",
						activity.customInfo);
				startActivity(photointent);
			}
			break;
		case R.id.tv_username:
			/**
			 * 跳转到登陆页
			 */
			Intent userintent = new Intent(activity, LoginActivity.class);
			startActivity(userintent);
			activity.finish();

			break;

		default:
			break;
		}
	}
	
	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}


}
