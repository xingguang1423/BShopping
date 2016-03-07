package com.xing.bshopping.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class WrapHeightListView extends ListView {

	public WrapHeightListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public WrapHeightListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WrapHeightListView(Context context) {
		super(context);
	}

	/**
	 * 计算控件的高度 width
	 * MeasureSpec:宽的期望值 
	 * heightMeasureSpec :高的期望值
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int heightSpec;
		
		if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
			
			// int数值的最高两位代表了 期望类型
			heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
					MeasureSpec.AT_MOST);
			//int类型是32位的，高2位代表期望类型值，低30位代表具体的数值
			// >>2 把数值右移两位，就能代表了宽高在数值方面能达到的一个最大值

			// MeasureSpec.EXACTLY明确期望有个具体的值
			// MeasureSpec.AT_MOST能多大有多大，不超过这个数值
			// MeasureSpec.UNSPECIFIED不做任何的限制
		} else {
			heightSpec = heightMeasureSpec;
		}

		super.onMeasure(widthMeasureSpec, heightSpec);

	}

}
