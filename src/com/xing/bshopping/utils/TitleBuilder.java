package com.xing.bshopping.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.xing.bshopping.R;

public class TitleBuilder {

	private View viewTitle;

	private TextView titlebar_tv_left;
	private ImageView titlebar_lv;
	private ImageView titlebar_iv1_right;
	private ImageView titlebar_iv2_right;
	private TextView titlebar_tv_right;

	public TitleBuilder(Activity context) {
		viewTitle = context.findViewById(R.id.rl_titlebar);

		titlebar_tv_left = (TextView) viewTitle
				.findViewById(R.id.titlebar_tv_left);
		titlebar_tv_right = (TextView) viewTitle
				.findViewById(R.id.titlebar_tv_right);
		titlebar_lv = (ImageView) viewTitle.findViewById(R.id.titlebar_lv);
		titlebar_iv1_right = (ImageView) viewTitle
				.findViewById(R.id.titlebar_iv1_right);
		titlebar_tv_right = (TextView) viewTitle
				.findViewById(R.id.titlebar_tv_right);

	}

	public TitleBuilder(View context) {
		viewTitle = context.findViewById(R.id.rl_titlebar);

		titlebar_tv_left = (TextView) viewTitle
				.findViewById(R.id.titlebar_tv_left);
		titlebar_lv = (ImageView) viewTitle.findViewById(R.id.titlebar_lv);
		titlebar_iv1_right = (ImageView) viewTitle
				.findViewById(R.id.titlebar_iv1_right);
		titlebar_iv2_right = (ImageView) viewTitle
				.findViewById(R.id.titlebar_iv2_right);
		titlebar_tv_right = (TextView) viewTitle
				.findViewById(R.id.titlebar_tv_right);

	}

	public TitleBuilder setBgColor( int color){
		
		viewTitle.setBackgroundResource(color);
		
		return this;
	}
	
	// left
	public TitleBuilder setLeftText(String text) {
		titlebar_tv_left.setVisibility(TextUtils.isEmpty(text) ? View.GONE
				: View.VISIBLE);
		titlebar_tv_left.setText(text);
		return this;
	}
	
	public TitleBuilder setLeftOnClickListener(OnClickListener listener) {
		if (titlebar_tv_left.getVisibility() == View.VISIBLE) {
			titlebar_tv_left.setOnClickListener(listener);
		} 
		return this;
	}
	
	
	public TitleBuilder setLeftTextColor(int color) {
		titlebar_tv_left.setTextColor(color);
		
		return this;
	}
	

	// title
	public TitleBuilder setTitleBgRes(int resid) {
		viewTitle.setBackgroundResource(resid);
		return this;
	}

	public TitleBuilder setTitleImage(int resid) {
		titlebar_lv.setVisibility(resid > 0 ? View.VISIBLE : View.GONE);
		titlebar_lv.setImageResource(resid);
		return this;
	}
	
	public TitleBuilder setTitleOnClickListener(OnClickListener listener) {
		if (titlebar_lv.getVisibility() == View.VISIBLE) {
			titlebar_lv.setOnClickListener(listener);
		}
		return this;
	}

	// right
	
	public TitleBuilder setRightText(String text) {
		titlebar_tv_right.setVisibility(TextUtils.isEmpty(text) ? View.GONE
				: View.VISIBLE);
		titlebar_tv_right.setText(text);
		return this;
	}
	
	public TitleBuilder setRightTextOnClickListener(OnClickListener listener) {
		if (titlebar_tv_right.getVisibility() == View.VISIBLE) {
			titlebar_tv_right.setOnClickListener(listener);
		} 
		return this;
	}
	
	
	
	public TitleBuilder setRightImage1(int resid) {
		titlebar_iv1_right.setVisibility(resid > 0 ? View.VISIBLE : View.GONE);
		titlebar_iv1_right.setImageResource(resid);
		return this;
	}
	
	public TitleBuilder setRightImage2(int resid) {
		titlebar_iv2_right.setVisibility(resid > 0 ? View.VISIBLE : View.GONE);
		titlebar_iv2_right.setImageResource(resid);
		return this;
	}
	
	
	public TitleBuilder setRightOnClickListener(OnClickListener listener,int resid) {
		
		if (titlebar_iv1_right.getId() == resid) {
			titlebar_iv1_right.setOnClickListener(listener);
		}else if (titlebar_iv2_right.getId() == resid) {
			titlebar_iv2_right.setOnClickListener(listener);
		}		
		return this;
	}
	
	public View build() {
		return viewTitle;
	}
	
}
