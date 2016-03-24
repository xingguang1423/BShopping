package com.xing.bshopping.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.xing.bshopping.R;
import com.xing.bshopping.entity.CustomInfo;
import com.xing.bshopping.fragment.FragmentController;
import com.xing.bshopping.utils.ToastUtils;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	private RadioGroup rg_tab;
	private FragmentController controller;
	public CustomInfo customInfo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		// setTranslucentStatus(true);
		// SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// tintManager.setStatusBarTintEnabled(true);
		// tintManager.setStatusBarTintResource(R.color.bshoppingcolor);//通知栏所需颜色
		// }

		setContentView(R.layout.activity_main);

		controller = FragmentController.getInstance(MainActivity.this,
				R.id.fl_content);
		controller.showFragment(0);

		initView();

		// 得到登陆的用户信息
		getUserCostomInfo();
	}

	private void getUserCostomInfo() {
		
		customInfo =  (CustomInfo) getIntent().getSerializableExtra("userCustomInfo");
		if (customInfo!=null) {
//			ToastUtils.showToast(this,"1111"+customInfo.toString(), 1);
			System.out.println("用户信息：----->"+customInfo.toString());
			
		}else{
//			ToastUtils.showToast(this,"用户请登陆", 1);
		}
		
	}

	// @TargetApi(19)
	// private void setTranslucentStatus(boolean on) {
	// Window win = getWindow();
	// WindowManager.LayoutParams winParams = win.getAttributes();
	// final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
	// if (on) {
	// winParams.flags |= bits;
	// } else {
	// winParams.flags &= ~bits;
	// }
	// win.setAttributes(winParams);
	// }

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
