package com.xing.bshopping.activity;

import android.os.Bundle;
import android.os.Handler;

import com.xing.bshopping.BaseActivity;
import com.xing.bshopping.R;

public class SplashActivity extends BaseActivity {

	private static final int WHAT_INTENT2LOGIN = 1;
	private static final int WHAT_INTENT2MAIN = 2;
	private static final long SPLASH_DUR_TIME = 3000;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case WHAT_INTENT2LOGIN:

				break;
			case WHAT_INTENT2MAIN:
				finish();
				intent2Activity(MainActivity.class);
				break;
			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		handler.sendEmptyMessageDelayed(WHAT_INTENT2MAIN, SPLASH_DUR_TIME);
	}

}
