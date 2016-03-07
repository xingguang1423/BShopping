package com.xing.bshopping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xing.bshopping.R;
import com.xing.bshopping.entity.GoodsInfo;
import com.xing.bshopping.utils.ToastUtils;
import com.xing.bshopping.widget.TuanDailScrollView;
import com.xing.bshopping.widget.TuanDailScrollView.OnScrollListener;

public class TuanDailActivity extends Activity implements OnScrollListener {

	private ImageView iv_tuandail_head_bgimg;
	private ImageView tuandail_iv_nobooking_img;
	private TextView tuandail_head_goodsName;
	private TextView tuandail_head_goodsContent;

	private TextView tuandail_buy_goodsPrice;
	private TextView tuandail_buy_goodsShopPrice;
	private TextView tuandail_buy_goodsPrice_top;
	private TextView tuandail_buy_goodsShopPrice_top;

	private ImageLoader imageLoader;

	GoodsInfo goodsInfo = null;

	// 自定义的MyScrollView
	private TuanDailScrollView tuanDailScrollView;

	// 在TuanDailScrollView里面的购买布局
	private LinearLayout rl_tuandail_buy;

	// 位于顶部的购买布局
	private LinearLayout rl_tuandail_topbuy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tuandail);

		goodsInfo = (GoodsInfo) getIntent().getSerializableExtra("goodsInfo");
		imageLoader = ImageLoader.getInstance();

		initView();

	}

	private void initView() {

		iv_tuandail_head_bgimg = (ImageView) findViewById(R.id.iv_tuandail_head_bgimg);
		tuandail_iv_nobooking_img = (ImageView) findViewById(R.id.tuandail_iv_nobooking_img);
		tuandail_head_goodsName = (TextView) findViewById(R.id.tuandail_head_goodsName);
		tuandail_head_goodsContent = (TextView) findViewById(R.id.tuandail_head_goodsContent);

		tuandail_buy_goodsPrice = (TextView) findViewById(R.id.tuandail_buy_goodsPrice);
		tuandail_buy_goodsShopPrice = (TextView) findViewById(R.id.tuandail_buy_goodsShopPrice);
		tuandail_buy_goodsPrice_top = (TextView) findViewById(R.id.tuandail_buy_goodsPrice_top);
		tuandail_buy_goodsShopPrice_top = (TextView) findViewById(R.id.tuandail_buy_goodsShopPrice_top);

		imageLoader.displayImage(goodsInfo.getGoodsImgUrl(),
				iv_tuandail_head_bgimg);
		if (goodsInfo.getIsGoodsBooking()) {
			tuandail_iv_nobooking_img.setVisibility(View.VISIBLE);
		} else {
			tuandail_iv_nobooking_img.setVisibility(View.GONE);
		}
		tuandail_head_goodsName.setText(goodsInfo.getGoodsName());
		tuandail_head_goodsContent.setText(goodsInfo.getGoodsContent());
		tuandail_buy_goodsPrice.setText(goodsInfo.getGoodsPrice() + "");
		tuandail_buy_goodsShopPrice.setText(goodsInfo.getGoodsShopPrice() + "");
		tuandail_buy_goodsPrice_top.setText(goodsInfo.getGoodsPrice() + "");
		tuandail_buy_goodsShopPrice_top.setText(goodsInfo.getGoodsShopPrice()
				+ "");

		tuanDailScrollView = (TuanDailScrollView) findViewById(R.id.tuandail_scrollview);
		rl_tuandail_buy = (LinearLayout) findViewById(R.id.rl_tuandail_buy);
		rl_tuandail_topbuy = (LinearLayout) findViewById(R.id.rl_tuandail_topbuy);

		tuanDailScrollView.setOnScrollListener(this);

		// 当布局的状态或者控件的可见性发生改变回调的接口
		findViewById(R.id.ll_tuandail).getViewTreeObserver()
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// 这一步很重要，使得上面的购买布局和下面的购买布局重合
						onScroll(tuanDailScrollView.getScrollY());

						System.out.println(tuanDailScrollView.getScrollY());
					}
				});

	}

	@Override
	public void onScroll(int scrollY) {
		int mBuyLayout2ParentTop = Math.max(scrollY, rl_tuandail_buy.getTop());
		rl_tuandail_topbuy.layout(0, mBuyLayout2ParentTop,
				rl_tuandail_topbuy.getWidth(), mBuyLayout2ParentTop
						+ rl_tuandail_topbuy.getHeight());
	}

}
