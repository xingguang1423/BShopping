package com.xing.bshopping.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.xing.bshopping.R;
import com.xing.bshopping.constants.AccessTokenKeeper;
import com.xing.bshopping.constants.WeiboConstants;
import com.xing.bshopping.dao.CustomInfoDao;
import com.xing.bshopping.entity.CustomInfo;
import com.xing.bshopping.utils.ToastUtils;

public class LoginActivity extends Activity {

	private Button login_btn;
	private TextView et_login_username;
	private TextView et_login_pwd;
	
	private CustomInfo userCustomInfo;
	
//	private List<CustomInfo> customInfoList = null;
	private ImageView ic_oauth_sina;
	
	
	private WeiboAuth mAuthInfo;
	
	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		et_login_username = (TextView) findViewById(R.id.et_login_username);
		et_login_pwd = (TextView) findViewById(R.id.et_login_pwd);
		ic_oauth_sina =  (ImageView) findViewById(R.id.ic_oauth_sina);
		
		
//		customInfoList = new ArrayList<CustomInfo>();
//		customInfoList.add(new CustomInfo(1, "dandan", "123456", "13106843179", null));
//		customInfoList.add(new CustomInfo(2, "xing", "123456", "13106975707", null));
//		customInfoList.add(new CustomInfo(3, "laokong", "123456", "13267971441", null));

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
		
		
		// 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
		mAuthInfo = new WeiboAuth(this, WeiboConstants.APP_KEY, WeiboConstants.REDIRECT_URL, WeiboConstants.SCOPE);
		mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
				
		
		ic_oauth_sina.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSsoHandler.authorize(new AuthListener());				
			}
		});
		
	}
	
	 /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
                Toast.makeText(LoginActivity.this, "auth_success", Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = "auth_failed";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "cancel auth", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this, 
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
