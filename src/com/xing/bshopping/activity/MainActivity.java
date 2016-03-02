package com.xing.bshopping.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.xing.bshopping.R;
import com.xing.bshopping.fragment.FragmentController;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	private RadioGroup rg_tab;
	private FragmentController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		controller = FragmentController.getInstance(MainActivity.this, R.id.fl_content);
		controller.showFragment(0);

		initView();
	}

	private void initView() {
		rg_tab = (RadioGroup) findViewById(R.id.rg_tab);

		rg_tab.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_home:
			controller.showFragment(0);
			break;
		case R.id.rb_poi:
			controller.showFragment(1);
			break;
		case R.id.rb_user:
			controller.showFragment(2);
			break;
		case R.id.rb_more:
			controller.showFragment(3);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		FragmentController.onDestroy();
	}

}
