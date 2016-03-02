package com.xing.bshopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;

public class UserFragment extends BaseFragment{

	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = View.inflate(activity, R.layout.frag_search, null);
		
		return view;
	}
	
}
