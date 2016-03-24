package com.xing.bshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.xing.bshopping.R;
import com.xing.bshopping.dao.CustomInfoDao;
import com.xing.bshopping.entity.CustomInfo;
import com.xing.bshopping.utils.ToastUtils;

public class LoginActivity extends Activity {

	private Button login_btn;
	private TextView et_login_username;
	private TextView et_login_pwd;
	
	private CustomInfo userCustomInfo;
	
//	private List<CustomInfo> customInfoList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		et_login_username = (TextView) findViewById(R.id.et_login_username);
		et_login_pwd = (TextView) findViewById(R.id.et_login_pwd);
		
		
//		customInfoList = new ArrayList<CustomInfo>();
//		customInfoList.add(new CustomInfo(1, "dandan", "123456", "13106843179", null));	
		
		final CustomInfoDao customInfoDao = new CustomInfoDao(LoginActivity.this);
//		customInfoDao.addCustomInfos(customInfoList);
		
		login_btn = (Button) findViewById(R.id.login_btn);
		login_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userName = et_login_username.getText().toString();
				String password = et_login_pwd.getText().toString();
				userCustomInfo = customInfoDao.loginCustomInfo(userName, password);
				
				if (userCustomInfo!=null) {
//					ToastUtils.showToast(LoginActivity.this, userCustomInfo.toString(), 1);
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					intent.putExtra("userCustomInfo", userCustomInfo);
					LoginActivity.this.startActivity(intent);
					
//					finish();
				}else{
					ToastUtils.showToast(LoginActivity.this, "用户或密码错误", 1);
				}
			}
		});
		
		
		
	}
	
	
}
