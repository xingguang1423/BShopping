package com.xing.bshopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;
import com.xing.bshopping.utils.TitleBuilder;
import com.xing.bshopping.utils.ToastUtils;

public class PoiFragment extends BaseFragment {

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = View.inflate(activity, R.layout.frag_poi, null);

		new TitleBuilder(view).setTitleText("Message")
				.setRightImage(R.drawable.ic_launcher)
				.setRightOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ToastUtils.showToast(activity, "right click~",
								Toast.LENGTH_SHORT);
					}
				});

		return view;
	}
}
